//package fr.eql.matchingEngine.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created by BEN MALEK S. on 25/06/2020.
// */
//
//@Configuration
//@EnableSwagger2
//public class SpringFoxConfig extends WebMvcConfigurationSupport {
//
//    @Value("${project.wiki}")
//    private String wikiLink;
//
//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("fr.eql.matchingEngine.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .consumes(getAllConsumeContentTypes())
//                .produces(getAllProduceContentTypes())
//                .apiInfo(apiInfo());
//
//    }
//
//    @Bean
//    public InternalResourceViewResolver defaultViewResolver() {
//        return new InternalResourceViewResolver();
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
//        registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
//        registry.addRedirectViewController("/documentation/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
//        registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/documentation/**").addResourceLocations("classpath:/META-INF/resources/");
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("Example structure api")
//                .description("This page lists all rest apis of our example.\n").termsOfServiceUrl(wikiLink)
//                .build();
//    }
//
//
//
//    private Set<String> getAllConsumeContentTypes() {
//        Set<String> consumes = new HashSet<>();
//        // Add other media types if required in future
//        consumes.add("application/json");
//        consumes.add("application/xml;charset=UTF-8");
//        consumes.add("application/json;charset=UTF-8");
//        consumes.add("application/json;charset=UTF-16");
//        return consumes;
//    }
//
//    private Set<String> getAllProduceContentTypes() {
//        Set<String> produces = new HashSet<>();
//        // Add other media types if required in future
//        produces.add("application/json");
//        produces.add("application/json;charset=UTF-8");
//        return produces;
//    }
//}