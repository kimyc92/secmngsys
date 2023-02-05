package com.secmngsys;

import com.secmngsys.global.configuration.database.DataSourceProperties;
import com.secmngsys.global.configuration.kafka.KafkaProperties;
import com.secmngsys.global.configuration.redis.RedisProperties;
import com.secmngsys.global.configuration.swagger.SwaggerProperties;
import com.secmngsys.global.util.AES256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


import javax.annotation.Resource;

@SpringBootApplication//(exclude = {ErrorMvcAutoConfiguration.class})
@EnableConfigurationProperties({KafkaProperties.class, SwaggerProperties.class
		, RedisProperties.class, DataSourceProperties.class})
@Slf4j
public class SecmngsysApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:profile/prd/application.yml,"
			+ "classpath:profile/prd/datasource.yml,"
			+ "classpath:profile/prd/endpoint.yml";

	@Resource
	private ApplicationArguments applicationArguments;  // CLI 인수

	public static void main(String[] args) throws Exception {
		//System.out.println("확인 "+ new AES256Util("my_key","QHGHqhdks01!"));
		System.setProperty("log4jdbc.log4j2.properties.file", "/log/log4jdbc.log4j2.properties");
		new SpringApplicationBuilder(SecmngsysApplication.class)   	// SpringApplication.run(MyBatchApplication.class, args);
				.properties(APPLICATION_LOCATIONS)
				.run(args);

	}

}
