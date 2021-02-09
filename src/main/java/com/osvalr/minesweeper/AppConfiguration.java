package com.osvalr.minesweeper;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@SpringBootConfiguration
public class AppConfiguration implements WebMvcConfigurer {


    private String databaseUrl;

    public AppConfiguration(@Value("game.databaseUrl") String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }

    @Bean
    public FirebaseOptions provideFirebaseOptions() throws IOException {
        return FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl(databaseUrl)
                .build();
    }

    @Bean
    public Firestore provideFirestore() {
        return FirestoreClient.getFirestore();
    }
}

