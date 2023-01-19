package com.secmngsys.global.exception.camel;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.secmngsys.global.model.ResponseError;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintViolationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class GlobalCamelException {

    private AtomicInteger numExceptions = new AtomicInteger(0);
    private String lastBody;
    private Throwable lastException;

    GenericCamalErrorCustomException errorException;
    GenericCamalSucessCustomException sucessException;

    private CountDownLatch latchOne;
    private CountDownLatch latchTwo;
    private CountDownLatch latchThree;

    @Autowired
    public void GlobalCamelException(GenericCamalErrorCustomException errorException
            ,GenericCamalSucessCustomException sucessException) {
        this.errorException = errorException;
        this.sucessException = sucessException;
    }

    public ResponseError GlobalHttpErrorException(Exchange exchange) {
        HttpServletRequestImpl test2 = (HttpServletRequestImpl) exchange.getIn().getHeader("CamelHttpServletRequest");
        return errorException.handleHttpErrorException(test2.getExchange().getStatusCode());
    }

    /**
     * GlobalCamalException : Camel 사용시 발생하는 Global ErrorHandler Method
     * @Headers Map headers, @Body String payload 값 사용 가능
     */
    public ResponseError GlobalCamelException(Exchange exchange) {
        this.lastBody = exchange.getMessage().getBody(String.class);

        //Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        this.lastException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        log.error("GlobalCamelException() - "+lastException.getClass());

        if (latchOne != null) {
            System.out.println("latchOne - "+latchOne);
            latchOne.countDown();
        }

        if (latchTwo != null) {
            System.out.println("latchTwo - "+latchTwo);
            latchTwo.countDown();
        }

        if (latchThree != null) {
            System.out.println("latchThree - "+latchThree);
            latchThree.countDown();
        }

        if(lastException instanceof HttpOperationFailedException)
            return errorException.handleHttpOperationFailedException(lastException);

        if(lastException instanceof ConstraintViolationException)
            return errorException.handleConstraintViolationException(lastException);

        if(lastException instanceof UnrecognizedPropertyException)
            return errorException.handleUnrecognizedPropertyException(lastException);

        if(lastException instanceof CamelException)
            return errorException.handleCamelException(lastException);

        // Default Global Camel Exception, errorException.handleException(cause, exchange)
        return errorException.handleException(lastException);
        // headers.put("customerid", headers.get("customerid"));
        // headers.put(Exchange.HTTP_RESPONSE_CODE, HttpStatus.BAD_REQUEST.value());
        // headers.put("orderid", "failed");
    }

    public void setCountDownOne(CountDownLatch latch) {
        this.latchOne = latch;
    }

    public void setCountDownTwo(CountDownLatch latch) {
        this.latchTwo = latch;
    }

    public void setCountDownThree(CountDownLatch latch) {
        this.latchThree = latch;
    }

    public String getMessageBody() {
        return lastBody;
    }

    public Throwable getException() {
        return lastException;
    }

    public int numberOfExceptions() {
        return numExceptions.get();
    }
}
