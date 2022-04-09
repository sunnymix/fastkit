package net.fastkit.core.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import net.fastkit.core.common.Json;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author sunnymix
 */
public class Client {

    private final String host;

    private final HttpClient client;

    private HttpClient _newClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public Client(String host) {
        this.host = host;
        this.client = _newClient();
    }

    public <T> Response<T> get(String path) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(host + path))
                .build();
        try {
            String responseString = client
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
            return Json.fromJson(responseString, new TypeReference<Response<T>>() {
            });

        } catch (Throwable ignored) {
        }
        return Response.error(0, "Internal server error");
    }

}
