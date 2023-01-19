package com.secmngsys.domain.certification.saga;

import com.secmngsys.domain.certification.model.dto.CertificationDto;
import com.secmngsys.domain.certification.model.dto.OrderDto;
import com.secmngsys.domain.certification.service.CertificationService;
import com.secmngsys.domain.certification.service.CreditService;
import com.secmngsys.domain.certification.service.OrderManagerService;
import com.secmngsys.domain.user.model.dto.UserDto;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.InMemorySagaService;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class CertificationSagaRoute extends RouteBuilder {

    private final CertificationService certificationService;

    public CertificationSagaRoute(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @Override
    public void configure() throws Exception {
        getContext().addService(new InMemorySagaService());
        errorHandler(noErrorHandler());

        from("direct:smsSendSaga")
                .log(ERROR, "Id: ${header.id}, Order Received: ${body}")
                .process(exchange ->
                    {
                        exchange.getMessage().setHeader("id", UUID.randomUUID().toString());
                        UserDto userDto = exchange.getMessage().getBody(UserDto.class);
                        userDto.setId(exchange.getMessage().getHeader("id", String.class));
                        exchange.getMessage().setBody(userDto);
                    }
                )
                .log(INFO, "Id: ${header.id}, User Received: ${body}")
                .saga()
                .to("direct:smsSends")
                .to("direct:smsDbSends")
        ;

        from("direct:smsSends")
                .bean(certificationService, "smsSends")
                .log(INFO, "Id: ${header.id}, User Received: ${body}")
        ;

        from("direct:smsDbSends")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .option("id", header("id"))
                .option("body", body())
                .log(ERROR, "Id: ${header.id}, User Received: ${body}")
                .compensation("direct:cancelSmsSends")
                .bean(certificationService, "smsDbSends")
                .log(INFO, "Id: ${header.id}, User Received: ${body}")
        ;

        from("direct:cancelSmsSends")
                //.setHeader("kafkaExeUrl", "/")
                .transform(header("body")) // body 값을 같이 넘겨줌
                //.log(ERROR, "cancelSmsSend Id: ${header.id}, User Received: ${body}")
                .to("direct:kafka-sms-topic")
        ;
    }
}
