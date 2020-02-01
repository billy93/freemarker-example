package com.hellokoding.springboot.view;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Since Spring Framwork 5.0 & Java 8+
@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {

 @Override
 public void addFormatters(FormatterRegistry registry) {
     DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
     registrar.setUseIsoFormat(true);
     registrar.registerFormatters(registry);
 }
}
