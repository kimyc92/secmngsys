package com.secmngsys.global.configuration.context;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ConfigureCamelContextHolder implements CamelContextConfiguration {

//    RestConfiguration restConfiguration;
//
//    @Autowired
//    public ConfigureCamelContextHolder(RestConfiguration restConfiguration){
//        this.restConfiguration = restConfiguration;
//    }

    @Override
    public void beforeApplicationStart(CamelContext camelContext) {
        log.error("ConfigureCamelContextHolder.beforeApplicationStart()");
//        restConfiguration()
//                .apiComponent("openapi")
//                .apiContextPath("/api-doc")
//                .apiProperty("api.title", "Secmngsys.KR Camel Rest APIs")
//                .apiProperty("api.version", "1.0")
//        ;
//        restConfiguration.setApiComponent("openapi");
//        restConfiguration.setApiContextPath("/api-doc");
//        restConfiguration.setApiProperties("/api-doc");
//        restConfiguration.setApiContextPath("/api-doc");
//        RestConfiguration restConfiguration = new RestConfiguration();
//        //restConfiguration.setApiComponent("jetty");
//        restConfiguration.setApiHost("localhost");
//        restConfiguration.setPort(8081);
  //      camelContext.setRestConfiguration(restConfiguration);
    }

    @Override
    public void afterApplicationStart(CamelContext camelContext) {
    }
}
