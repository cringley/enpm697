package com.umd.sdlc.example.sdlc_project.utils;

import org.springframework.web.reactive.function.client.WebClient;

public class HttpClient {
    
    public static String makeHttpRequestSimple(String url) {
        try {
            WebClient webClient = WebClient.create(url);
            WebClient.ResponseSpec responseSpec = webClient.get().retrieve();
            String response = responseSpec.bodyToMono(String.class).block();
            return response;
        } catch (Exception exception) {
            return exception.getLocalizedMessage();
        }
    }
}
