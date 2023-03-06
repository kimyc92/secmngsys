package com.secmngsys.domain.certification.route;

import com.secmngsys.domain.certification.model.dto.CertificationDto;
import com.secmngsys.domain.certification.service.CertificationService;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.service.UserService;
import com.secmngsys.global.model.ResponseSuccess;
import com.secmngsys.global.route.GlobalRouteBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;

@Slf4j
@Component
public class CertificationRoute extends GlobalRouteBuilder {

    private final CertificationService certificationService;

    public CertificationRoute(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        //getCamelContext().setLogMask(true);
//        ExtendedCamelContext ecc = (ExtendedCamelContext) getCamelContext();
//        ecc.addLogListener(new CamelLogListener());
        //getContext().getTracer().setEnabled(false);

        rest("/v1/certification").tag("Certification")
            .description("휴대폰 번호를 이용한 본인 인증 API")
            .consumes("application/json").produces("application/json")
            .put("/sms-sends").routeId("V1-CERTIFICATION-001")
                .description("본인인증을 위한 인증번호 생성 성공 요청")
                .responseMessage("200", "Request Completed")
                .param()
                    .name("userDto").type(body).required(true).description("본인인증을 위한 인증번호 생성을 위한 Param")
                .endParam()
                .type(UserDto.class).outType(ResponseSuccess.class)
                .to("direct:smsSendSaga")
            .post("/sms-confirms").routeId("V1-CERTIFICATION-002")
                .description("인증번호를 확인합니다.")
                .responseMessage("200", "Request Completed")
                .param()
                    .name("userDto").type(body).required(true).description("인증번호 확인을 위한 Param")
                .endParam()
                .type(CertificationDto.class).outType(ResponseSuccess.class)
                .to("bean:certificationService?method=smsConfirms")
            .delete("/cancel-sms-sends").routeId("V1-CERTIFICATION-003")
                .description("본인인증을 위한 인증번호 생성 실패 요청")
                .responseMessage("200", "Request Completed")
                .param()
                    .name("certificationDto").type(body).required(true).description("본인인증을 위한 인증번호 삭제를 위한 Param")
                .endParam()
                .type(CertificationDto.class).outType(ResponseSuccess.class)
                .to("bean:certificationService?method=cancelSmsSends");

        rest("/v1/certification").tag("Certification")
                .post("/user-info").routeId("asdasdaSD?")//throwExceptionOnFailure
                .type(UserDto.class).outType(ResponseSuccess.class)
                // .param().name("userDto").type(body).description("Get Sponge version request").endParam()
                .to("direct:user-info2");

        from("direct:user-info2")
                //.unmarshal().json(JsonLibrary.Jackson, UserDto.class)
//                .process(exchange -> {
//                    getContext().getTracer().setEnabled(true);
//                })
                .choice()
                .when(header(Exchange.CONTENT_TYPE).isEqualTo("application/json; version=1.0"))
                .bean(UserService.class, "selectOneUserInfo")
                .when(header(Exchange.CONTENT_TYPE).isEqualTo("application/json; version=1.1"))
                .bean(UserService.class, "selectOneUserInfo2")
                .otherwise()
                .bean(UserService.class, "selectOneUserInfo");
        // .marshal().json();

//
//        from("direct:smsSends")
//            .process(exchange -> {
//                exchange.getMessage().setHeader("id", UUID.randomUUID().toString());
//                UserDto userDto = exchange.getMessage().getBody(UserDto.class);
//                exchange.getMessage().setBody(userDto);
//            })
//            .log(INFO, "Id: ${header.id}, Received body ${body}")
//            .saga()
//                .to("direct:newOrder")
//                .to("direct:makePayment")
//                .to("direct:shipOrder");
//
////            .propagation(SagaPropagation.MANDATORY)
////                .option("id", header("id"))
////            .compensation("direct:cancel-sms-sends")
////                //.transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
////                .bean(CertificationService.class, "smsSends")
////                .log("Order ${body} created").end();
//
//        from("direct:newOrder")
//                .saga()
//                .propagation(SagaPropagation.MANDATORY)
//                .option("id", header("id"))
//                .setBody(body())
//                .compensation("direct:cancelOrder")
//                .bean(CertificationService.class, "smsSends")
//                .log(INFO, "Id: ${header.id}, Received body ${body}");
//
//        from("direct:cancelOrder")
//                .log(ERROR, "cancelOrder");
//
//
//        from("direct:makePayment")
//                .saga()
//                .propagation(SagaPropagation.MANDATORY)
//                .option("id", header("id"))
//                .option("body", body())
//              //  .option("customerId", simple("${body.customId}"))
//                .setBody(body())
//                .compensation("direct:refundPayment")
//                .bean(CertificationService.class, "smsSends")
//                .log(INFO, "Id: ${header.id}, Received body ${body}");
//
//        from("direct:refundPayment")
//                .log(ERROR, "refundPayment");
//
//        from("direct:shipOrder")
//                .saga()
//                .propagation(SagaPropagation.MANDATORY)
//                .option("id", header("id"))
//                .option("body", body())
//                //.option("customerId", simple("${body.customId}"))
//                .setBody(body())
//                .compensation("direct:cancelShipping")
//                .completion("direct:completeShipping")
//                .bean(CertificationService.class, "smsSends")
//                .log(INFO, "Id: ${header.id}, Received body ${body}");
//
//        from("direct:cancelShipping")
//                .log(ERROR, "cancelShipping");
//
//        from("direct:completeShipping")
//                .log(ERROR, "completeShipping");
//
//
//
//
//
//
//        from("direct:newSmsSends")
//                .saga()
//                .propagation(SagaPropagation.MANDATORY)
//                .option("id", header("id"))
//                .setBody(body())
//                .compensation("direct:cancel-sms-sends")
//                .bean(CertificationService.class, "smsSends")
//                .log(INFO, "Id: ${header.id}, Received body ${body}");
//
//        from("direct:cancel-sms-sends")
//            .log(ERROR, "cancel-sms-sends")
//            .bean(CertificationService.class, "smsDbSends");

//        from("direct:user-info")
//                .log(INFO, "Received body ${body}")
//                .choice()
//                .when(header(Exchange.CONTENT_TYPE).isEqualTo("application/json; version=1.0"))
//                .bean(UserService.class, "selectOneUserInfo")
//                .when(header(Exchange.CONTENT_TYPE).isEqualTo("application/json; version=1.1"))
//                .bean(UserService.class, "selectOneUserInfo2")
//                .otherwise()
//                .bean(UserService.class, "selectOneUserInfo");

    }

}
