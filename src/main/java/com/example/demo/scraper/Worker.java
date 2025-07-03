package com.example.demo.scraper;

import com.example.demo.entity.JobContext;
import com.example.demo.entity.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Service
@RequiredArgsConstructor
public class Worker {

    private final Utils utils;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(60))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public void genericRequest(Scraper scraper, JobContext jobContext) throws Exception {

        String resolvedUrl = utils.resolveUrlTemplate(scraper.getUrlTemplate(), jobContext.getUrlParams());
        String resolvedBody = utils.resolveBodyTemplate(scraper.getBodyTemplate());

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(resolvedUrl));

        scraper.getHeaderTemplateJson().forEach(builder::header);

        if (scraper.getHttpMethod() == GET) {
            builder = builder.GET();
        } else if (scraper.getHttpMethod() == POST) {
            builder = builder.POST(scraper.getBodyTemplate() != null
                    ? HttpRequest.BodyPublishers.ofString(resolvedBody)
                    : HttpRequest.BodyPublishers.noBody());
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + scraper.getHttpMethod());
        }

        HttpRequest request = builder.build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

        System.out.println("Response Code : " + response.statusCode());
        System.out.println("Response Body : " + Arrays.toString(response.body()));

        //Save to sftp urørt.
    }
}
