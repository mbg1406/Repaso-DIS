package es.ufv.dis.final2022.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class API
{
    private static int backendPort=8888;
    private static String backendUrlPrefix="http://localhost:"+ backendPort+"%s";

    public void guardarmedicamento(String nombre , String categoria, String precio, String EAN13) throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/guardarmedicamento?nombre="+nombre+"&categoria="+categoria+"&precio="+precio+"&EAN13="+EAN13);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
    }

    public List<Medicamento> cogermedicamento() throws URISyntaxException, IOException, InterruptedException {
        String urlFull = "http://localhost:8888/ObtenerMedicamento";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlFull))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Type tipo = new TypeToken<List<Medicamento>>(){}.getType();
        return gson.fromJson(response.body(),tipo);
    }

    public Medicamento putMedicamento(String nombre, String  categoria, String precio, String ean13) throws IOException, InterruptedException {
        String urlFull = backendUrlPrefix + "/addProducto";

        var objectMapper = new ObjectMapper();

        String requestBody = objectMapper
                .writeValueAsString(new Medicamento(nombre, categoria, precio, ean13));


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlFull))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();


        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Type tipo = new TypeToken<Medicamento>(){}.getType();

        return gson.fromJson(response.body(),tipo);
    }


}
