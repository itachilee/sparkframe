package controller;

import com.google.gson.Gson;
import com.sun.org.slf4j.internal.Logger;
import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.Spark.*;

/**
 * @author leon
 */
public class SparkRestExample {
    private static Map<String, User> userMap = new HashMap<String, User>();
    public static void main(String[] args) {
        final Random random = new Random();

//        port(8089);
        path("/api", () -> {
            before("/*", (q, a) -> q.session(true));
            post("/users", (request, response) -> {
                response.type("application/json");
                String firstName = request.queryParams("firstName");
                String lastName = request.queryParams("lastName");
                String email = request.queryParams("email");

                User user = new User(firstName, lastName, email);
                int id = random.nextInt(Integer.MAX_VALUE);
                userMap.put(String.valueOf(id), user);
                response.status(201); // 201 Created
                return id;
            });
            get("/users", (request, response) -> {
                response.type("application/json");
                return new Gson().toJson(userMap);
            });
            get("/users/:id", (request, response) -> {
                User user = userMap.get(request.params(":id"));
                if (user != null) {
                    return "fn: " + user.getFirstName() + ", ln: " + user.getLastName();
                } else {
                    response.status(404); // 404 Not found
                    return "user not found";
                }
            });
        });
        // Using Route
        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });
    }
}
