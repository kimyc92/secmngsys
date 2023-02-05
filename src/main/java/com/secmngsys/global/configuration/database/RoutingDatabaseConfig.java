package com.secmngsys.global.configuration.database;

import com.secmngsys.global.configuration.code.DatabaseTypeCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.config.JtaTransactionManagerFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//@DependsOn(value={"initBitronix"})
@EnableTransactionManagement
@Configuration
public class RoutingDatabaseConfig { //implements DisposableBean {

    @Resource
    private Environment env;

    protected DataSourceProperties dataSourceProperties;

    public RoutingDatabaseConfig(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public static Map<Object, Object> dataSources = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;  // 스프링 IoC Container를 사용하기 위한 applicationContext 주입

    @Bean(name="routeDataSource")
    public DataSource clientDatasource() throws Exception {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();

        dataSources.put(DatabaseTypeCode.Master
                , DataSourceConfig.createHikariDataSource(DatabaseTypeCode.Master, dataSourceProperties.getMaster(), "prd.master.datasource"));
        dataSources.put(DatabaseTypeCode.Sms
                , DataSourceConfig.createHikariDataSource(DatabaseTypeCode.Sms, dataSourceProperties.getSms(), "prd.sms.datasource"));
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(dataSources.get(DatabaseTypeCode.Master));
        return routingDataSource;
    }

//    @Bean
//    public DataSource lazyRoutingDataSource(
//            @Qualifier(value = "routeDataSource") DataSource routingDataSource) {
//        return new LazyConnectionDataSourceProxy(routingDataSource);
//    }

//    @Bean
//    public DataSourceTransactionManager transactionManager(
//            @Qualifier(value = "lazyRoutingDataSource") DataSource lazyRoutingDataSource) {
////        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
////        transactionManager.setDataSource(lazyRoutingDataSource);
//
//        return new DataSourceTransactionManager(lazyRoutingDataSource);
//    }

    @Bean(name = "routeSqlSessionFactory")
    public SqlSessionFactory sqlSession(@Qualifier("routeDataSource") DataSource dataSource) throws Exception {
        log.info("routeSqlSessionFactory init");
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.getObject().getConfiguration().addMappers("com.secmngsys"); // 다음 패키지 아래에 모든 @Mapper 들을 등록 함
        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:**/sql/*.xml")); // 해당 경로를 mapper 스캔 설정
        //sessionFactoryBean.setTransactionFactory(new ManagedTransactionFactory());
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "routeSqlSessionTemplate", destroyMethod = "clearCache") // clearCache -> Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("routeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);   // BATCH  INSERT / UPDATE
    }

}
