package main;

import com.google.gson.Gson;
import dbcontroller.DBController;
import orm.Contract;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@WebServlet("/test")
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String call = request.getParameter("request");
        String result = "error";

        if ("getAll".equals(call)) {
            result = new Gson().toJson(DBController.getObjects(Contract.class));
        }

        doSetResult(response, result);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String call = request.getParameter("request");
        String data = getData(request);
        String result = "error";

        switch (call) {
            case "delete":
                DBController.deleteObject(new Contract(data));
                result = "ok";
                break;
            case "create":
                DBController.createOrUpdateObject(new Gson().fromJson(data, Contract.class));
                result = "ok";
                break;
        }
        doSetResult(response, result);
    }

    protected void doSetResult(HttpServletResponse response, String result)
            throws IOException {
        response.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String getData(HttpServletRequest request) throws IOException {
        StringBuilder data = new StringBuilder();
        String line;
        BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        while ((line = rd.readLine()) != null) {
            data.append(line);
        }
        rd.close();
        return data.toString();
    }

}
