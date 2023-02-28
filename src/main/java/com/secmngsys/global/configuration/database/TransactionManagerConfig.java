package com.secmngsys.global.configuration.database;

import com.secmngsys.global.configuration.code.DatabaseTypeCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableTransactionManagement
@Configuration
public class TransactionManagerConfig {

    @Bean("lazyRoutingDataSource")
    public DataSource lazyRoutingDataSource(
            @Qualifier(value = "routeDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

//    @Bean(name = "bitronixTransactionManager")
//    @DependsOn
//    public BitronixTransactionManager bitronixTransactionManager() throws Throwable {
//        BitronixTransactionManager bitronixTransactionManager = TransactionManagerServices.getTransactionManager();
//        bitronixTransactionManager.setTransactionTimeout(10000);
//        //bitronixTransactionManager.
//        //bitronixTransactionManager.setUserTransaction(bitronixTransactionManager);
//        //CustomJtaPlatform.setTransactionManager(bitronixTransactionManager);
//        return bitronixTransactionManager;
//    }

//    @Bean(name = "transactionManager")
//    @DependsOn({"bitronixTransactionManager"})
//    public PlatformTransactionManager transactionManager(TransactionManager bitronixTransactionManager) throws Throwable {
//        return new JtaTransactionManager(bitronixTransactionManager);
//    }
//
//
    @Bean//("transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier(value = "lazyRoutingDataSource") DataSource lazyRoutingDataSource) throws SQLException {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(lazyRoutingDataSource);
        return new DataSourceTransactionManager(lazyRoutingDataSource);
    }

//    @Bean(name = ["chainedTransactionManager"])
//    fun chainedTransactionManager(
//            @Qualifier("transactionManager") transactionManager: PlatformTransactionManager,
//            @Qualifier("riderTransactionManager") riderTransactionManager: PlatformTransactionManager,
//            ): PlatformTransactionManager {
//        return ChainedTransactionManager(transactionManager, riderTransactionManager);
//    }
}
