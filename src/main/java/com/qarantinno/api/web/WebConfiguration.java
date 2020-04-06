package com.qarantinno.api.web;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
public class WebConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfiguration.class);

    @Bean
    public DozerBeanMapper dozerMapper(ResourceLoader resourceLoader) {
        Set<String> mappingUrls = recognizeDozerMappingUrls(resourceLoader, "classpath*:dozer/**/*.xml");
        DozerBeanMapper dozerMapper = new DozerBeanMapper();
        dozerMapper.setMappingFiles(new ArrayList<>(mappingUrls));
        return dozerMapper;
    }

    private Set<String> recognizeDozerMappingUrls(ResourceLoader resourceLoader, String mappingsLocationPattern) {
        Set<String> mappingsFileNames = null;
        try {
            Resource[] dozerMappings = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                                                           .getResources(mappingsLocationPattern);
            mappingsFileNames = Arrays.stream(dozerMappings)
                                      .map(this::resourceToUrl)
                                      .filter(Optional::isPresent)
                                      .map(Optional::get)
                                      .collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return mappingsFileNames;
    }

    private Optional<String> resourceToUrl(Resource resource) {
        String url = null;
        try {
            url = resource.getURL().toString();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Optional.ofNullable(url);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    .paths(PathSelectors.any())
                .build()
                    .apiInfo(apiEndPointsInfo())
                    .useDefaultResponseMessages(true)
                    .forCodeGeneration(true)
                    .enable(true);
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Qarantinno app Rest Api")
                                   .description("Qarantinno app Rest Api introduction")
                                   .build();
    }
}
