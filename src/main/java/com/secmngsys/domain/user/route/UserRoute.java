package com.secmngsys.domain.user.route;

import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.service.UserService;
import com.secmngsys.global.model.ResponseSuccess;
import com.secmngsys.global.route.GlobalRouteBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.apache.camel.model.rest.RestParamType.body;

@Slf4j
@Component
public class UserRoute extends GlobalRouteBuilder { // RouteBuilder

//    @Autowired
//    CamelContext camelContext;

//    @Autowired
//    CamelConfig camelConfig;

    private final UserService userService;

    public UserRoute(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure() throws Exception {
        super.configure();
        //camelContext.add
        //camelContext.setLogMask(true);
//        ExtendedCamelContext ecc = (ExtendedCamelContext) getCamelContext();
//        ecc.addLogListener(new CamelLogListener());

        //getContext().addLogListener(new MyLogListener());
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

        rest("/v1/user").tag("/user")
                .description("사용자 정보를 위한 API")
                .consumes("application/json").produces("application/json")
                .post("/user-confirm").routeId("V1-USER-001")
                    .description("사용자의 정보를 확인합니다.")
                    .param()
                        .name("UserDto").type(body).required(true).description("사용자 정보를 확인하는 Param")
                    .endParam()
                    .type(UserDto.class).outType(ResponseSuccess.class)
                    .to("bean:userService?method=selectOneUserInfo")
                .post("/user-info").routeId("V1-USER-002")//throwExceptionOnFailure
                    .description("사용자의 정보를 조회합니다.")
                    .param()
                        .name("UserDto").type(body).required(true).description("사용자 정보를 조회하는 Param")
                    .endParam()
                    .type(UserDto.class).outType(ResponseSuccess.class)
                    // .param().name("userDto").type(body).description("Get Sponge version request").endParam()
                    .to("direct:user-info")
                .patch("/user-info").routeId("V1-USER-003")
                    .description("사용자의 정보를 변경합니다.")
                    .param()
                        .name("UserDto").type(body).required(true).description("사용자 정보를 변경하는 Param")
                    .endParam()
                    .type(UserDto.class).outType(ResponseSuccess.class)
                    .to("bean:userService?method=userInfoChange")
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

        from("direct:user-info")
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
            .to("kafka:quickstart-events?brokers=localhost:29092")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");


//        from("kafka:test?brokers=localhost:9092")


    }
}
