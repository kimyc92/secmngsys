package com.secmngsys.domain.user.route;

import com.secmngsys.global.model.ResponseError;
import com.secmngsys.global.model.ResponseSuccess;
import com.secmngsys.global.route.GlobalRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spi.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
public class RestDslRouteBuilder extends GlobalRouteBuilder {

    @Autowired
    CamelContext camelContext;

    @Override
    public void configure() {
        super.configure(true);

//        Tracer tracer = camelContext.getTracer();
//        tracer.setEnabled(true);

        rest().id("rest-producer-route")//.bindingMode(RestBindingMode.auto)
                .path("/api2") // This makes the API available at http://host:port/$CONTEXT_ROOT/api
                //.consumes("application/json").produces("application/json")
                .get()
                .produces("application/json")
                .outType(ResponseType.class) // Setting the response type enables Camel to marshal the response to JSON
                .to("bean:getBean2") // This will invoke the Spring bean 'getBean'

                // HTTP: POST /api
                .post()
                .type(PostRequestType.class) // Setting the request type enables Camel to unmarshal the request to a Java object
                .outType(ResponseError.class) // Setting the response type enables Camel to marshal the response to JSON
                .to("bean:postBean2");

    }
}