package com.secmngsys.global.route;

import com.secmngsys.global.exception.camel.GenericCamalErrorCustomException;
import com.secmngsys.global.model.EndpointHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.ERROR;

@Slf4j
@DependsOn(value={"endpointHelper"})
@Component
public class RestAPI extends GlobalRouteBuilder {
//    @Autowired
//    MyBean myBean;
    //private final PinDataProvider pinDataProvider;
//    private final CamelEndpointHelper camelEndpointHelper;
//
//    public RestAPI(CamelEndpointHelper camelEndpointHelper) {
//        this.camelEndpointHelper = camelEndpointHelper;
//        //System.out.println(camelEndpointHelper.getApp());
//        //System.out.println(camelEndpointHelper.getApp().get(0));
//    }

//    @Autowired
//    CamelEndpointHelper camelEndpointHelper;
//
//    public RestAPI(){
////        System.out.println(camelEndpointHelper.getApp());
////        System.out.println(camelEndpointHelper.getApp().get(0));
//    }

    private final EndpointHelper endpointHelper;
    //ExceptionHandlerProcessBean exceptionHandlerProcessBean;

    @Autowired
    public RestAPI(EndpointHelper endpointHelper){
          //  , ExceptionHandlerProcessBean exceptionHandlerProcessBean) {
        this.endpointHelper = endpointHelper;
      //  this.exceptionHandlerProcessBean = exceptionHandlerProcessBean;
    }

    @Override
    public void configure() throws Exception {
        super.configure();

//        restConfiguration().component("undertow").host("localhost")
//                .port(8086).bindingMode(RestBindingMode.json);

//        restConfiguration()
//                .component("undertow")
//                .port(8888)
//                .contextPath("/api");

//        System.out.println("-------------------------------------");
//        System.out.println(restConfiguration().getContextPath());
//        System.out.println(restConfiguration().getComponent());
//        System.out.println(restConfiguration().getPort());
//        System.out.println("-------------------------------------");
        //undertow:http://hostname[:port][/resourceUri][?options]
        //undertow:http://localhost:8080/myapp/myservice
        //from("undertow:http://localhost:8090/test/test11?method=POST").to("direct:http://localhost:8081/naver/naver?method=POST");
//



        rest("/api")
                .description("Some description")
                .post()
                .outType(String.class)
                .to("direct:test");

            //.to("direct:hello");
        from("direct:test")
              //  .process(new ExceptionHandlerProcess())
                .throwException(new CamelException("exexexexex"))
                .transform()
                .constant("Hello Test");


        from("rest:post:hello")
                .transform().constant("Bye World");

        from("direct:hello").transform().constant("Hello World");
//       // onException(StepBackException.class).log(“error sent back to caller”);
//        from("undertow:http://localhost:8085/naver/naverzz?method=POST")  // http://localhost:8085/naver/naverzz?method=POST으로 요청이 오면 시작
////               .process(new ProcessorCustomException())
//                //.tracing()
//                .log("Route(processErrorAdminPost)1: ${body}")
//                //.process(new GenericCamelProcessorException())
//                .unmarshal().json()
//                .process(exchange -> {
//                    // json 으로 넘어온 데이터 body 값을 아에 변경해버림.
//                    //exchange.getMessage().setBody(("{'messageID':'" + UUID.randomUUID().toString() + "','ticketID':'1234'}"));
//                    log.info("Type of incoming body:{}", exchange.getIn().getBody().getClass().getName());
//                    log.info("Incoming body:{}", exchange.getIn().getBody());
//                })//.marshal().json()
//                //.process(new GenericCamelProcessorException())
//                .to("undertow:http://localhost:8090/test/test11?method=POST")
//                .unmarshal().json()
//                .process(exchange -> {
//                    // json 으로 넘어온 데이터 body 값을 아에 변경해버림.
//                    //exchange.getMessage().setBody(("{'messageID':'" + UUID.randomUUID().toString() + "','ticketID':'1234'}"));
//                    log.info("Type of incoming body2:{}", exchange.getIn().getBody().getClass().getName());
//                    log.info("Incoming body2:{}", exchange.getIn().getBody());
//                }).marshal().json()
//                .to("undertow:http://localhost:8081/naver/camel")
//                .process(new GenericCamelProcessorException());
//                .tracing()
//                .unmarshal().json()
//                .process(exchange -> {
//                    // json 으로 넘어온 데이터 body 값을 아에 변경해버림.
//                    //exchange.getMessage().setBody(("{'messageID':'" + UUID.randomUUID().toString() + "','ticketID':'1234'}"));
//                    log.info("Type of incoming body:{}", exchange.getIn().getBody().getClass().getName());
//                    log.info("Incoming body:{}", exchange.getIn().getBody());
//                }).marshal().json()
//                .to("undertow:http://localhost:8081/naver/camel?method=POST");


                //.marshal().json();//.transform().constant("{'httpResponse:500':'OK'}")
                //.marshal().json();
                //.log("Route(processErrorAdminPost): ${body}")
                //.to("undertow:http://localhost:8090/test/test11?method=POST");
//                .tracing()
//                .to("undertow:http://localhost:8081/naver/naver?method=POST");
        //from("direct:start")
               // .to("undertow:http://localhost:8081/naver/naver?method=POST");
        // from("undertow:http://localhost:8081/test/test11?method=POST").to("direct:hello");

//        // routes
//        rest("/hello")
//                .get()
//                .to("direct:hello");
//        from("direct:hello")
//                .setBody(constant("Hello"));




//        rest().path("/rest").consumes("application/json").produces("application/json")
//                .get().to("direct:hello")
//
//                .post().type(User.class).outType(User.class)
//                .to("bean:serviceBean");
//
//        from("direct:hello").transform().constant("Hello World");




//        rest()
//                .path("/api") // This makes the API available at http://host:port/$CONTEXT_ROOT/api
//
//                .consumes("application/json")
//                //.produces("application/json")
//
//                // HTTP: GET /api
//                .get()
//                //.outType(ResponseType.class) // Setting the response type enables Camel to marshal the response to JSON
//                .to("bean:getBean") // This will invoke the Spring bean 'getBean'
//
//                // HTTP: POST /api
//                .post()
//                //.type(PostRequestType.class) // Setting the request type enables Camel to unmarshal the request to a Java object
//                .outType(ResponseType.class) // Setting the response type enables Camel to marshal the response to JSON
//                .to("bean:postBean");

//        restConfiguration()
//                .enableCORS(true)
//                .apiContextPath("/api-doc")
//                .apiProperty("api.title", "Test REST API")
//                .apiProperty("api.version", "v1")
//                .apiContextRouteId("doc-api")
//                .component("servlet")
//                .bindingMode(RestBindingMode.json);
//
//        rest("/api/")
//                .id("api-route")
//                .consumes("application/json")
//                .post("/bean")
//                .bindingMode(RestBindingMode.json_xml)
//                .type(ServiceBean.class)
//                .to("direct:remoteService");
//
//        from("direct:remoteService")
//                .routeId("direct-route")
//                .tracing()
//                .log(">>> ${body.id}")
//                .log(">>> ${body.name}")
//                .transform().simple("Hello ${in.body.name}")
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

//        rest("/common")
//                .get("{id}")
//                .to("bean:orderService?method=getOrder(${header.id})")
//                .post()
//                .to("bean:orderService?method=createOrder")
//                .put()
//                .to("bean:orderService?method=updateOrder")
//                .delete("{id}")
//                .to("bean:orderService?method=cancelOrder(${header.id})");

        //.multicast().parallelProcessing()
        //                    .to("direct:payOrder")
        //                    .to("direct:purchaseOrderItems")
//          .multicast().parallelProcessing()
//                .serviceCall("credit/api/payments/${body.reference}")
//                .serviceCall("inventory/api/purchases/${body.reference}");
    }
//
//    private static final class IncrementProcessor implements Processor {
//        @Override
//        public void process(Exchange exchange) throws Exception {
//            IncrementRequest request = exchange.getIn().getBody(IncrementRequest.class);
//            IncrementResponse response = new IncrementResponse();
//            int result = request.getInput() + 1; // increment input value
//            response.setResult(result);
//            exchange.getIn().setBody(response);
//        }
//    }
//
//    private void savePinDataAndSetToExchange(Exchange exchange) {
//        System.out.println("저장함!!!");
//        PinCodeDto pinCodeDto = exchange.getMessage().getBody(PinCodeDto.class);
//        pinDataProvider.setUserPin(pinCodeDto);
//
//    }
//
//    private void getPinCodeData(Exchange exchange) {
//        String userPin = exchange.getMessage().getHeader("pinCode", String.class);
//        PinCodeDto pinCode = pinDataProvider.getUserPin(userPin);
//
//        if (Objects.nonNull(pinCode)) {
//            Message message = new DefaultMessage(exchange.getContext());
//            message.setBody(pinCode);
//            exchange.setMessage(message);
//        } else {
//            exchange.getMessage().setHeader(HTTP_RESPONSE_CODE, NOT_FOUND.value());
//        }
//    }
    /*
        1. CamelContext : 들어오고 나가는 메시지가 보관되는 핵심 구성 요소
        2. FROM : Consumer EndPoint, TO : Producer EndPoint
     */
//    @Override
//    public void configure() {
//        CamelContext context = new DefaultCamelContext();
//        context.setAutoStartup(false);
//        log.info("Hellow World Camel - "+context);
//
//        restConfiguration()
//                .component("servlet")
//                .contextPath("/")
//                .host("localhost")
//                .port("8080")
//                .bindingMode(RestBindingMode.auto);
//
//        rest("/secmngsys")
//                .post("/user-info")
//                .consumes("application/json")
//                .produces("application/json")
//                .to("direct:user-info");
////                .post()
////                .
////        rest("/customers")
////                .post().
////                .log("The body is ${body}!");
////
//////                .contextPath(contextPath)
//////
//////                .enableCORS(true)
//////                .apiContextPath("/secmngsys")
//////                .apiProperty("api.title", "Test REST API")
//////                .apiProperty("api.version", "v1")
//////                .apiContextRouteId("doc-api")
//////                .component("servlet")
//////                .bindingMode(RestBindingMode.json);
//    }
}
