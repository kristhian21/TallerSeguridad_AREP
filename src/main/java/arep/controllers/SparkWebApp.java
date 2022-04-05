package arep.controllers;

import spark.Filter;
import static spark.Spark.*;
import com.google.gson.Gson;

public class SparkWebApp {

    public static void main(String[] args) {
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
        // GET Operations
        get("/cos", ((request, response) -> {
            response.type("application/json");
            String result = urlReader.readURL("https://localhost:35001/cos?value="+request.queryParams("value"));
            return new Gson().toJson(new MathResponse("Coseno(x)", request.queryParams("value"), result));
        }));
        get("/sen", ((request, response) -> {
            response.type("application/json");
            String result = urlReader.readURL("https://localhost:35001/sen?value="+request.queryParams("value"));
            return new Gson().toJson(new MathResponse("Seno(x)", request.queryParams("value"), result));
        }));

        // We can now read this URL
        // urlReader.readURL("https://localhost:35001/cos?value=100");
        // This one can't be read because the Java default truststore has been 
        // changed.
        //urlReader.readURL("https://www.google.com");
    }

    /**
     * Set up the port
     * @return the port number
     */
    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

}
