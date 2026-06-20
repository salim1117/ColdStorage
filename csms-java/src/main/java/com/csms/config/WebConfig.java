package com.csms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final Path uploadDir;
    private final Path legacyRoot;

    public WebConfig(@Value("${app.upload.dir:../uploads}") String uploadDir,
                     @Value("${app.legacy.root:..}") String legacyRoot) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.legacyRoot = Paths.get(legacyRoot).toAbsolutePath().normalize();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**", "/uploads/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
        registry.addResourceHandler("/dist/**")
                .addResourceLocations("file:" + legacyRoot.resolve("dist").normalize() + "/");
        registry.addResourceHandler("/plugins/**")
                .addResourceLocations("file:" + legacyRoot.resolve("plugins").normalize() + "/");
        registry.addResourceHandler("/libs/**")
                .addResourceLocations("file:" + legacyRoot.resolve("libs").normalize() + "/");
    }
}
