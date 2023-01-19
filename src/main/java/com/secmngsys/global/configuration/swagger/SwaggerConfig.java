package com.secmngsys.global.configuration.swagger;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@EnableSwagger2
//@EnableWebMvc
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {
//
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("asdasdadsdsadsdsd");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/swagger/dist/");
    }

//    @Bean
//    public List<GroupedOpenApi> apis() {
//        return routeDefinitionLocator.getRouteDefinitions()
//                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service-docs|.*-adapter-docs"))
//                .map(routeDefinition -> routeDefinition.getId().replace("-docs", ""))
//                .map(serviceId -> GroupedOpenApi.builder()
//                        .pathsToMatch("/" + serviceId + "/**")
//                        .setGroup(serviceId).build())
//                .collectList() 				.block(); 	}
//
//
//    @Bean
//    public RestSwaggerComponent petstore(CamelContext camelContext, UndertowComponent undertow) {
//        RestSwaggerComponent petstore = new RestSwaggerComponent(camelContext);
//        petstore.setSpecificationUri("http://petstore.swagger.io/v2/swagger.json");
//        petstore.setDelegate(undertow);
//
//        return petstore;
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/swagger-ui/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//        //.addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//    }
//
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30) // open api spec 3.0
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }


//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .paths(PathSelectors.ant("/user/**"))
//                .build();
//    }
//
//    @Bean
//    public Docket apiGlobal() {
//        return new Docket(DocumentationType.OAS_30)
//                .select()
//                // 특정 패키지경로를 API문서화 한다. 1차 필터
//                //.apis(RequestHandlerSelectors.basePackage("com.secmngsys.global.route"))
//                //.apis(RequestHandlerSelectors.any())
//                // apis중에서 특정 path조건 API만 문서화 하는 2차 필터
//                // .paths(PathSelectors.ant("/api/**"))
//                //.paths(PathSelectors.any())
//                .build()
//                // 400,404,500 .. 표기를 ui에서 삭제한다.
//                .useDefaultResponseMessages(false)
//                //.pathMapping("/api")
//                .groupName("GLOBAL"); // group별 명칭을 주어야 한다.
//                //.apiInfo(apiInfo());
//    }
//
//    @Bean
//    public Docket apiUser() {
//        return new Docket(DocumentationType.OAS_30)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/user/**"))
//                .build()
//                .useDefaultResponseMessages(false)
//                .groupName("USER"); // group별 명칭을 주어야 한다.
//    }
//
//    @Bean
//    public Docket apiCertification() {
//        return new Docket(DocumentationType.OAS_30)
//                .select()
//                //.apis(RequestHandlerSelectors.basePackage("com.secmngsys.domain.user.certification"))
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/api2/**"))
//                .build()
//                .useDefaultResponseMessages(false)
//                .groupName("CERTIFICATION"); // group별 명칭을 주어야 한다.
//    }

    /*
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("fantoo_api_test 프로젝트")
                .description("API 호출 테스트용도.")
                .version("1.0.0")
                .termsOfServiceUrl("")
                //.contact()
                .license("")
                .licenseUrl("")
                .build();
    }
    */
}
