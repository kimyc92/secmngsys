package com.secmngsys.domain.user.route;

import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.service.UserService;
import com.secmngsys.global.configuration.camel.CamelConfig;
import com.secmngsys.global.model.ResponseSuccess;
import com.secmngsys.global.route.GlobalRouteBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.apache.camel.LoggingLevel.INFO;

@Slf4j
@Component
public class UserRoute extends GlobalRouteBuilder { // RouteBuilder

    @Autowired
    CamelContext camelContext;

    @Autowired
    CamelConfig camelConfig;

    @Override
    public void configure() throws Exception {
        super.configure();
//        errorHandler(camelConfig.myErrorHandler());

        //intercept().log("gigigigigigi").to("log:hello");
//        interceptSendToEndpoint("http://*")
//                .skipSendToOriginalEndpoint()
//                .process(exchange ->
//                        System.out.println("interceptor called " + " times " + exchange.getIn().getBody())
//                );
//
//        interceptFrom("rest:*")
//                .process(exchange ->
//                        System.out.println("11111111111111interceptor called " + " times " + exchange.getIn().getBody())
//                )
//                .to("log:incoming");

//        interceptFrom("*").process(exchange ->
//                System.out.println("interceptor called " + " times " + exchange.getIn().getBody())
//        );
//        errorHandler(springTransactionErrorHandler());
        // if its a 404 then regard it as handled
//        onException(HttpOperationFailedException.class).onWhen(new Predicate() {
//
//            public boolean matches(Exchange exchange) {
//                System.out.println("asdasdasdasdd");
//                HttpOperationFailedException e = exchange.getException(HttpOperationFailedException.class);
//                return e != null && e.getStatusCode() == 404;
//            }
//        }).handled(true).to("mock:404").transform(constant("asdasdasdasd"));


       // from("log:incoming").log("zzzzzzzzzzzz");
//        onException(HttpOperationFailedException.class)
//                .handled(true)
//                .log("loglolgolog");

//        onException(HttpRequestMethodNotSupportedException.class)
//                .handled(true)
//                        .log("loglolgolog");



        from("direct:error223")
                .log("옵니까?????")
                .end();
        from("servlet:///activate?httpMethodRestrict=POST,OPTION")
                .doTry()
                    .log("dotray")
                .doCatch(IllegalArgumentException.class)
                    .log("IllegalArgumentException")
                .doCatch(IllegalStateException.class)
                    .log("IllegalStateException")
                .end();
//        restConfiguration().bindingMode(RestBindingMode.json);
       // CamelContext context = new DefaultCamelContext();
//        RestConfiguration restConfiguration = new RestConfiguration();
//        restConfiguration.setComponent("servlet");
//        restConfiguration.setBindingMode(RestConfiguration.RestBindingMode.json);
////        restConfiguration.setHost("localhost");
//        restConfiguration.setPort(8083);
//
//        context.setRestConfiguration(restConfiguration);
//        System.out.println("확인 - "+camelContext.getRestConfiguration().getBindingMode());
//        restConfiguration()
//                .component("servlet")
//                .bindingMode(RestBindingMode.auto);
//        camelContext.getRestConfiguration().setBindingMode(auto);
//        System.out.println("확인 - "+camelContext.getRestConfiguration().getBindingMode());
//        from("timer://foo?period=1000")
//                //.log("Message received from Kafka : ${body}")
//                .to("direct:kafka-my-consumer");
////
////        // Kafka Consumer
//        from("direct:kafka-my-consumer")
//                .log("여기까진 옴?ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ")
//                .to("kafka:quickstart-events?brokers=localhost:9092")
//              //    + "&groupId=secmngsys")  // groupId
//        //from("kafka:myTopic?brokers=localhost:9092")
//                .log("hihihihihi");
//                .log("Message received from Kafka : ${body}")
//                .log("    on the topic ${headers[kafka.TOPIC]}")
//                .log("    on the partition ${headers[kafka.PARTITION]}")
//                .log("    with the offset ${headers[kafka.OFFSET]}")
//                .log("    with the key ${headers[kafka.KEY]}");

               // .to("kafka:quickstart-events?brokers=localhost:9092");


                   //log.info("Type of incoming body:{}", exchange.getIn().getBody().getClass().getName());
                    //log.info("Incoming body:{}", exchange.getIn().getBody())
//                )
//                .process(exchange -> )
                ;

        rest("/v1/user").tag("/user").consumes("application/json").produces("application/json")
             //   .bindingMode(RestBindingMode.json)
                .description("User456 API")
                .post("/user-info").routeId("/v1/user")//throwExceptionOnFailure
                    .type(UserDto.class).outType(ResponseSuccess.class)
                    // .param().name("userDto").type(body).description("Get Sponge version request").endParam()
                    .to("direct:user-info")
                .post("/kafkatest")
                    .to("direct:kafka-test")

                ;

        rest("/v2/user").tag("/user").consumes("application/json").produces("application/json")
                //   .bindingMode(RestBindingMode.json)
                .description("User123 API")
                .post("/user-info").routeId("/v2/user")
                .type(UserDto.class).outType(ResponseSuccess.class)
                // .param().name("userDto").type(body).description("Get Sponge version request").endParam()
                .to("direct:user-info")
                .post("/kafkatest")
                .to("direct:kafka-test")

        ;

        from("direct:user-info").log(INFO, "Received body ${body}")
            //.unmarshal().json(JsonLibrary.Jackson, UserDto.class)
            .choice()
            .when(header(Exchange.CONTENT_TYPE).isEqualTo("application/json; version=1.0"))
            .bean(UserService.class, "selectOneUserInfo")
            .when(header(Exchange.CONTENT_TYPE).isEqualTo("application/json; version=1.1"))
            .bean(UserService.class, "selectOneUserInfo2")
                .otherwise()
                .bean(UserService.class, "selectOneUserInfo");
            // .marshal().json();


        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss"; //hhmmss로 시간,분,초만 뽑기도 가능
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                currentLocale);

        from("direct:kafka-test")
            .setBody(constant("Message from Camel"+formatter.format(today)))          // Message to send
          //  .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
            .to("kafka:quickstart-events?brokers=localhost:9092")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");


//        from("kafka:test?brokers=localhost:9092")


    }
}
