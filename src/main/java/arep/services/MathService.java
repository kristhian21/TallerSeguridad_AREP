package arep.services;

import static spark.Spark.*;


public class MathService {

    public static void main(String[] args) {
        // SSL Certificate
        secure("keystores/ecikeystore.p12", "ecistore", null, null);
        // Set up the port
        port(getPort());
        System.out.println("Escuchando peticiones...");
        // GET Sen
        get("/cos", (request, response) -> {
            String number = request.queryParams("value");
            System.out.println("-------New Operation-------");
            System.out.println("Number: " + number);
            System.out.println("Operation: Cos(x)");
            Double result = Math.cos(Double.valueOf(number));
            System.out.println("Result: " + result);
            return String.valueOf(result);
        });
        // GET Sen
        get("/sen", (request, response) -> {
            String number = request.queryParams("value");
            System.out.println("-------New Operation-------");
            System.out.println("Number: " + number);
            System.out.println("Operation: Sen(x)");
            Double result = Math.sin(Double.valueOf(number));
            System.out.println("Result: " + result);
            return result;
        });
    }

    /**
     * Set up the port
     * @return the port number
     */
    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }
}
