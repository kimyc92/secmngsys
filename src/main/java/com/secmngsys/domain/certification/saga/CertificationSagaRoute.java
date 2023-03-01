package com.secmngsys.domain.certification.saga;

import com.secmngsys.domain.certification.service.CertificationService;
import com.secmngsys.domain.user.model.dto.UserDto;
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
                .setHeader("id", simple(UUID.randomUUID().toString()))
                .setHeader("base_url", header("CamelHttpUrl"))
                .setHeader("base_method", header("CamelHttpMethod"))
                .process(exchange ->
                    {
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
                .option("base_url", header("base_url"))
                .option("base_method", header("base_method"))
                .option("body", body())
                .log(ERROR, "Id: ${header.id}, User Received: ${body}")
                .compensation("direct:cancelSmsSends")
                .completion("direct:completeSmsSends")
                //.transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                .bean(certificationService, "smsDbSends")
                .log(INFO, "Id: ${header.id}, User Received: ${body}")

        ;

        from("direct:cancelSmsSends")
                //.transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                .log(ERROR, "cancelSmsSends")
                .transform(header("body")) // body 값을 같이 넘겨줌
                /*
                .setHeader("dest_url",
                        constant("http://localhost:8081/api/v1/certification/cancel-sms-sends"))
                .setHeader("dest_method", constant("delete"))
                .transform(header("body")) // body 값을 같이 넘겨줌
                .to("direct:kafka-sms-topic")
                 */
                .bean(certificationService, "cancelSmsSends")
        ;

        from("direct:completeSmsSends")
                .log(INFO, "completeSmsSends")
                .transform(header("body")) // body 값을 같이 넘겨줌
                .bean(certificationService, "completeSmsSends")
        ;
    }
}
