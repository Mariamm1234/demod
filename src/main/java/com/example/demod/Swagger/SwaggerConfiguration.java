package com.example.demod.Swagger;

import java.net.URI;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.awt.Desktop;
import java.io.IOException;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public CommandLineRunner openSwaggerUiOnStart() {
        return args -> {
            String swaggerUrl = "http://localhost:8080/swagger-ui.html"; // Adjust the URL to match your application's context
            
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI(swaggerUrl));
                } else {
                    // Platform does not support Desktop, fallback to system command or log
                    String os = System.getProperty("os.name").toLowerCase();
                    
                    if (os.contains("win")) {
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + swaggerUrl);
                    } else if (os.contains("mac")) {
                        Runtime.getRuntime().exec("open " + swaggerUrl);
                    } else if (os.contains("nix") || os.contains("nux")) {
                        Runtime.getRuntime().exec("xdg-open " + swaggerUrl);
                    } else {
                        throw new UnsupportedOperationException("Cannot open URL automatically. Unsupported OS: " + os);
                    }
                }
            } catch (UnsupportedOperationException | IOException e) {
                // Log a message if the automatic opening fails
                System.err.println("Desktop is not supported. Open the following URL manually: " + swaggerUrl);
            } catch (Exception e) {
                // Log any unexpected exceptions
                System.err.println("Failed to open Swagger UI: " + e.getMessage());
            }
        };
    }
}
