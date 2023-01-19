package com.secmngsys.global.process;

import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.model.ResponseError;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import io.undertow.servlet.spec.HttpServletResponseImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalHttpErrorProcess implements Processor {

    @Override
    public void process(Exchange exchange) {
        //HttpServletRequestImpl  req = (HttpServletRequestImpl) exchange.getIn().getHeader("CamelHttpServletRequest");
        HttpServletResponseImpl res = (HttpServletResponseImpl) exchange.getIn().getHeader("CamelHttpServletResponse");
        HttpStatus httpStatus = HttpStatus.valueOf(res.getStatus());
        if(HttpStatus.valueOf(res.getStatus()).equals(HttpStatus.OK))
            httpStatus = HttpStatus.BAD_REQUEST;
        ResponseError response = ResponseError.of(ErrorCode.HTTP_CLIENT_ERROR, String.valueOf(httpStatus));
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, httpStatus.value());
        exchange.getIn().setBody(new ResponseEntity<>(response, HttpStatus.valueOf(httpStatus.value())).getBody());
    }

}
