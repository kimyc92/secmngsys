package com.secmngsys.global.configuration.database;

import com.secmngsys.domain.certification.dao.CertificationDao;
import com.secmngsys.domain.certification.dao.CertificationDao2;
import com.secmngsys.domain.user.dao.UserDao;
import com.secmngsys.global.configuration.code.DatabaseTypeCode;
import com.secmngsys.global.configuration.context.RoutingDatabaseContextHolder;
import com.secmngsys.global.exception.RoutingDataSourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    RoutingDatabaseConfig routingDatabaseConfig;

    public void RoutingDataSource(RoutingDatabaseConfig routingDatabaseConfig) {
        this.routingDatabaseConfig = routingDatabaseConfig;
    }

//    @Resource
//    private Environment env;

    public DataSource get(DatabaseTypeCode databaseTypeCode) {
        log.debug("RoutingDataSource.get()");
        return (DataSource) routingDatabaseConfig.dataSources.get(databaseTypeCode);
    }

    public void add(DatabaseTypeCode databaseTypeCode, DataSource ds) {
        log.debug("RoutingDataSource.add()");
        routingDatabaseConfig.dataSources.put(databaseTypeCode, ds);
    }

    private boolean contains(DatabaseTypeCode databaseTypeCode) {
        log.debug("RoutingDataSource.contains()");
        return routingDatabaseConfig.dataSources.containsKey(databaseTypeCode);
    }

    @Override
    protected DataSource determineTargetDataSource() { // 최초 DataBase 연결시 사용
        DatabaseTypeCode DatabaseTypeCode = determineCurrentLookupKey();
        if(DatabaseTypeCode == null){
            throw new RoutingDataSourceException("데이터베이스 정보가 없습니다.");
        }
//        System.out.println("RoutingDataSource.determineTargetDataSource()");
        try {

            if(DatabaseTypeCode == DatabaseTypeCode.Sms){
                // 비어 있을 때만 현재 routingDatabaseInfo 값으로 Setting
                if(!(contains(DatabaseTypeCode))){
                    throw new RoutingDataSourceException("SMS 데이터 베이스 정보가 비어 있습니다.");
                }
                log.info("SMS DB Dynamic Setting Completed");
                return get(DatabaseTypeCode.Sms);
            }

            if(DatabaseTypeCode == DatabaseTypeCode.Drm){
                if(!(contains(DatabaseTypeCode))){
                    throw new RoutingDataSourceException("DRM 데이터 베이스 정보가 비어 있습니다.");
                    /*
                    DataSource ds = createCustomDataSource(routingDatabaseInfo);
                    add(DatabaseTypeCode.Source, ds);
                    */
                }
                log.info("Source DB Dynamic Setting Completed");
                return get(DatabaseTypeCode.Drm);
            }
            // Default Database Config
            return get(DatabaseTypeCode.Master);

        } catch(Exception e){
            throw new RoutingDataSourceException(e);
        }
    }

    @Override
    protected DatabaseTypeCode determineCurrentLookupKey() {
        log.debug("RoutingDataSource.determineCurrentLookupKey()");
        log.debug("RoutingDatabaseContextHolder.get() : ", RoutingDatabaseContextHolder.get());
        return RoutingDatabaseContextHolder.get();
    }

}