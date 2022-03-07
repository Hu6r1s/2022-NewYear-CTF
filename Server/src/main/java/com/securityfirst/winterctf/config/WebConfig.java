package com.securityfirst.winterctf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
CORS 문제 때문에 임시로 생성
*/
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedMethods(
              HttpMethod.GET.name(),
              HttpMethod.HEAD.name(),
              HttpMethod.POST.name(),
              HttpMethod.PUT.name(),
              HttpMethod.DELETE.name());
  }

}
