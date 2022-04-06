package arep.controllers;

import arep.utils.Login;
import spark.Filter;
import static spark.Spark.*;
import com.google.gson.Gson;

import arep.utils.MathResponse;
import arep.utils.URLReader;

public class SparkWebApp {

    private static boolean isLogged = false;
    private static final String badLoginMessage  = "No se ha iniciado sesión :(\n"
    +"Para iniciar sesión use el recurso /login?username=USER&password=PASSWORD";

    public static void main(String[] args) {
        // Set up the truststore and the url reader
        URLReader urlReader = new URLReader();
        // Set up the index
        staticFiles.location("/public");
        // SSL Certificate
        secure("keystores/ecikeystore.p12", "ecistore", null, null);
        // Set up the port
        port(getPort());
        init();
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });
        // GET mapping
        get("/login", ((request, response) -> {
            response.type("text/plain;charset=utf-8");
            String loginResult;
            isLogged = Login.getInstance().authenticate(request.queryParams("username"), request.queryParams("password"));
            if(isLogged){
                loginResult = "Autenticación exitosa :)\nAhora puede usar el servicio de operaciones matemáticas!";
            }
            else{
                loginResult = "Autenticación fallida :(";
            }
            return loginResult;
        }));
        get("/cos", ((request, response) -> {
            if(isLogged){
                response.type("application/json");
                String result = urlReader.readURL("https://localhost:35001/cos?value="+request.queryParams("value"));
                return new Gson().toJson(new MathResponse("Coseno(x)", request.queryParams("value"), result));
            }
            else{
                response.type("text/plain;charset=utf-8");
                return badLoginMessage;
            }
        }));
        get("/sen", ((request, response) -> {
            if(isLogged){
                response.type("application/json");
                String result = urlReader.readURL("https://localhost:35001/sen?value="+request.queryParams("value"));
                return new Gson().toJson(new MathResponse("Seno(x)", request.queryParams("value"), result));
            }
            else{
                response.type("text/plain;charset=utf-8");
                return badLoginMessage;
            }
        }));
    }

    /**
     * Set up the port
     * 
     * @return the port number
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

}
