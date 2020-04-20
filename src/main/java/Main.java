

import static spark.Spark.*;

/**
 * @author leon
 */
public class Main {
    public static void main(String[] args) {
        get("/hello","application/json", (req, res) -> "aaas");
        get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));

        get("/news/:section", (request, response) -> {
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>" + request.params("section") + "</news>";
        });

        get("/protected", (request, response) -> {
            halt(403, "I don't think so!!!");
            return null;
        });

        get("/redirect", (request, response) -> {
            response.redirect("/news/world");
            return null;
        });

        get("/", (request, response) -> "root");

        get("/hello2", "application/json", (request, response) -> {
            return "{\"message\": \"Hello World\"}";
        });
    }

}