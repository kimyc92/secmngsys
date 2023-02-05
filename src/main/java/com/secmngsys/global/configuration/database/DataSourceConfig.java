package com.secmngsys.global.configuration.database;

import com.secmngsys.global.configuration.code.DatabaseTypeCode;
import com.secmngsys.global.configuration.redis.RedisProperties;
import com.secmngsys.global.exception.RoutingDataSourceException;
import com.secmngsys.global.util.AES256Util;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class DataSourceConfig {

	private static final String key = "my_key";

	/*
	public static PoolingDataSource createBitronixDataSource(DatabaseTypeCode type, Environment env, String db) {
		PoolingDataSource dataSource = new PoolingDataSource();
		try {
			dataSource.setUniqueName(env.getProperty(db+".uniqueName"));
			dataSource.setClassName(bitronix.tm.resource.jdbc.lrc.LrcXADataSource.class.getName());
			Properties properties = new Properties();
			properties.setProperty("user", AES256Util.decryptAES256(env.getProperty(db+".username"), key));
			properties.setProperty("password", AES256Util.decryptAES256(env.getProperty(db+".password"), key));
			properties.setProperty("url", env.getProperty(db+".jdbcUrl"));
			//properties.setProperty("testQuery", "SELECT 1 FROM DUAL");

			// MariaDB
			if(type.equals(DatabaseTypeCode.Master))
				dataSource.setClassName(env.getProperty(db+".xaDataSourceClassName"));

			// Oracle
			if(type.equals(DatabaseTypeCode.Sms)) {
				dataSource.setClassName(bitronix.tm.resource.jdbc.lrc.LrcXADataSource.class.getName());
				//dataSource.setClassName(oracle.jdbc.xa.OracleXADataSource.class.getName());
				properties.setProperty("driverClassName", env.getProperty(db+".driverClassName"));
			}
			dataSource.setMinPoolSize(5);
			dataSource.setMaxPoolSize(10);
			System.out.println(properties.toString());
			dataSource.setDriverProperties(properties);
			dataSource.setAllowLocalTransactions(true);

		} catch (NullPointerException e) {
			log.error("[Default DataSource] DB Environment is NULL !!");
			throw new RoutingDataSourceException("type : "+type+" 데이터 베이스 정보가 비어 있습니다.");
		} catch (Exception e) {
			throw new RoutingDataSourceException("type : "+type+" 데이터 베이스 정보가 정상적이지 않습니다.");
		}
		return dataSource;
	}
	 */
//	public static DataSource createBitronixDataSource(DatabaseTypeCode type, Environment env, String db) throws Exception {
//		PoolingDataSource bitronixDataSourceBean = new PoolingDataSource();
//		try {
//
//			bitronixDataSourceBean.setMaxPoolSize(5);
//			bitronixDataSourceBean.setUniqueName(env.getProperty(db+".uniqueName"));
//
//
//
//			Properties properties = new Properties();
//			bitronixDataSourceBean.setClassName(env.getProperty(db+".xaDataSourceClassName"));
//			if(type.equals(DatabaseTypeCode.Sms)) {
//				bitronixDataSourceBean.setClassName(bitronix.tm.resource.jdbc.lrc.LrcXADataSource.class.getName());
//				//dataSource.setClassName(oracle.jdbc.xa.OracleXADataSource.class.getName());
//				properties.setProperty("driverClassName", env.getProperty(db+".driverClassName"));
//			}
//
//			properties.setProperty("user", AES256Util.decryptAES256(env.getProperty(db+".username"), key));
//			properties.setProperty("password", AES256Util.decryptAES256(env.getProperty(db+".password"), key));
//			properties.setProperty("url", env.getProperty(db+".jdbcUrl"));
//			bitronixDataSourceBean.setDriverProperties(properties);
//
//		} catch (NullPointerException e) {
//			log.error("[Default DataSource] DB Environment is NULL !!");
//			throw new RoutingDataSourceException("type : "+type+" 데이터 베이스 정보가 비어 있습니다.");
//
//		} catch (Exception e) {
//			throw new RoutingDataSourceException("type : "+type+" 데이터 베이스 정보가 정상적이지 않습니다.");
//		}
//
//		return bitronixDataSourceBean;
//	}


	public static DataSource createHikariDataSource(DatabaseTypeCode type
			, DataSourceProperties.DataSource ds, String db) {
		HikariDataSource dataSource = new HikariDataSource();
		DataSourceProperties.DataSource.DataSourceDef dsDef = ds.getDatasource();
		try {
//			System.out.println(AES256Util.decryptAES256(env.getProperty(db+".username"), key));
//			System.out.println(AES256Util.decryptAES256(env.getProperty(db+".password"), key));
//			dataSource.setJdbcUrl(env.getProperty(db+".jdbcUrl"));
//			dataSource.setDriverClassName(env.getProperty(db+".driverClassName"));
//			dataSource.setUsername(AES256Util.decryptAES256(env.getProperty(db+".username"), key));
//			dataSource.setPassword(AES256Util.decryptAES256(env.getProperty(db+".password"), key));
			dataSource.setJdbcUrl(dsDef.getJdbcUrl());
			dataSource.setDriverClassName(dsDef.getDriverClassName());
			dataSource.setUsername(AES256Util.decryptAES256(dsDef.getUsername(), key));
			dataSource.setPassword(AES256Util.decryptAES256(dsDef.getPassword(), key));
			dataSource.setValidationTimeout(Long.parseLong(dsDef.getValidationTimeout()));
			dataSource.setMaximumPoolSize(Integer.parseInt(dsDef.getMaximumPoolSize()));
			//dataSource.setConnectionInitSql("SELECT 1 FROM DUAL");
			//dataSource.setAutoCommit(false);
			//dataSource.setReadOnly(true);

		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("[Custom DataSource] DB Environment is NULL !!");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Custom DataSource] DB ID/PW Decrypt or Connect Fail !!");
		}
		return dataSource;
	}
}