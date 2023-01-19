package com.secmngsys.global.configuration.swagger;

import com.secmngsys.global.exception.MyHandlerExceptionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

//@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**", "*.ico", "/error", "/error-page/**", "/api/error");//오류 페이지 경로
//    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        //resolvers.add(new UserHand
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        System.out.println("들어오기는하니?");
//        registry.addResourceHandler("/swagger-ui/**")
//                //.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//                .addResourceLocations("classpath:/swagger/dist/");
//                //.addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
////        registry.addResourceHandler("/webjars/**")
////                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/swagger-ui/")
//                .setViewName("forward:/swagger-ui/index.html");
//    }

}
