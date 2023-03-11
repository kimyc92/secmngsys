package com.secmngsys;

import com.secmngsys.global.configuration.database.DataSourceProperties;
import com.secmngsys.global.configuration.kafka.KafkaProperties;
import com.secmngsys.global.configuration.redis.RedisProperties;
import com.secmngsys.global.configuration.swagger.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.Resource;

@MapperScan
@SpringBootApplication//(exclude = {ErrorMvcAutoConfiguration.class})
@EnableConfigurationProperties({KafkaProperties.class, SwaggerProperties.class
		, RedisProperties.class, DataSourceProperties.class})
@Slf4j
public class SecmngsysApplication {

	//public static final String APPLICATION_LOCATIONS = "spring.config.location="
	//		+ "classpath:application.yml";
			//+ "file:///Users/youngchangkim/Project/secmngsys/engn/profile/conf/prd/application.yml,"
			//+ "file:///Users/youngchangkim/Project/secmngsys/engn/profile/conf/prd/datasource.yml,"
			//+ "file:////Users/youngchangkim/Project/secmngsys/engn/profile/conf/prd/endpoint.yml";

	@Resource
	private ApplicationArguments applicationArguments;  // CLI 인수


	public static void main(String[] args) throws Exception {
		//String content = Files.readString(Paths.get(getClass().getResource("/profile/prd/key.txt").toURI()),StandardCharsets.UTF_8);
		//System.out.println("확인 -> "+content);
		//System.out.println("확인 "+ new AES256Util("sec_key","my_key"));
		System.setProperty("log4jdbc.log4j2.properties.file", "/log/log4jdbc.log4j2.properties");
		new SpringApplicationBuilder(SecmngsysApplication.class)   	// SpringApplication.run(MyBatchApplication.class, args);
		//		.properties("spring.config.location=classpath:application.yml")
				.run(args);

	}

}
