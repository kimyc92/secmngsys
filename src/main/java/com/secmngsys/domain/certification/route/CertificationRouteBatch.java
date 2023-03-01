package com.secmngsys.domain.certification.route;

import com.secmngsys.global.route.GlobalRouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.INFO;

@Component
public class CertificationRouteBatch extends GlobalRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        // 1h, 1m, 1s
        from("timer://batchSmsSendsInfo?fixedRate=true&period=1h")
            .log(INFO, "batchSmsSendsInfo()")
            .to("bean:certificationService?method=batchSmsSendsInfo")
        ;
    }
}
