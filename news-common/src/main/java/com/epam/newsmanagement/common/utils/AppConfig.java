package com.epam.newsmanagement.common.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *
 */
@Configuration
@ImportResource({"classpath:/springContext.xml"})
@ComponentScan(basePackages = "com.epam.newsmanagement.common")
public class AppConfig {
}