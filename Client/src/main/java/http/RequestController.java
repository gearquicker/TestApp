package http;

import com.google.gson.Gson;
import model.Contract;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestController {

    public static List<Contract> getAll() throws IOException {
        return new ArrayList<>(Arrays.asList(new Gson().fromJson(getRequest("getAll"), Contract[].class)));
    }

    public static void delete(Contract contract) throws IOException {
        if (contract != null) {
            postRequest("delete", contract.getId());
        } else {
            throw new IOException();
        }
    }

    public static void create(Contract contract) throws IOException {
        if (contract != null) {
            postRequest("create", new Gson().toJson(contract));
        } else {
            throw new IOException();
        }
    }

    private static String getRequest(String key) throws IOException {
        HttpClient httpClient = SessionSingleton.getInstance().getHttpClient();
        HttpGet httpget = new HttpGet(SessionSingleton.getInstance().getUri() + key);
        return readEntry(httpClient.execute(httpget));
    }

    private static void postRequest(String key, String data) throws IOException {
        HttpClient httpClient = SessionSingleton.getInstance().getHttpClient();
        HttpPost httppost = new HttpPost(SessionSingleton.getInstance().getUri() + key);
        httppost.setEntity(new ByteArrayEntity(data.getBytes(StandardCharsets.UTF_8)));
        readEntry(httpClient.execute(httppost));
    }

    private static String readEntry(HttpResponse response) throws IOException {
        if (response.getEntity() != null) {
            StringBuilder builder = new StringBuilder();
            String line;
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
            builder.append(rd.readLine());
            while ((line = rd.readLine()) != null) {
                builder.append("\n").append(line);
            }
            return builder.toString();
        }
        return null;
    }

}