package com.aventurier.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "aventurier-rest-app")
public class Application implements AppShellConfigurator {

    private static final long serialVersionUID = 6647640078227029077L;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
