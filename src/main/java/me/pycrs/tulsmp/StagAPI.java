package me.pycrs.tulsmp;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StagAPI {
    private CloseableHttpClient client;
    private String baseUrl;

    public StagAPI(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClients.custom().setDefaultHeaders(Arrays.asList(
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic " + Reference.AUTH)
        )).build();
    }

    public CloseableHttpResponse get(String s) throws IOException {
        return client.execute(new HttpGet(baseUrl + s));
    }

    public CloseableHttpResponse get(String s, NameValuePair parameters) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(baseUrl + s);
        builder.setParameters(parameters);
        return client.execute(new HttpGet(builder.build()));
    }

    public static String asText(HttpResponse response) throws IOException {
        return asText(response.getEntity().getContent());
    }

    public static String asText(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder s = new StringBuilder();
        try {
            int data = reader.read();
            while (data != -1) {
                s.append((char) data);
                data = reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
