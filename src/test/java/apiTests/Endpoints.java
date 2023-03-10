package apiTests;

import apiEngine.AddBooksRequest;
import apiEngine.AuthorizationRequest;
import apiEngine.RemoveBookRequest;
import apiEngine.Route;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
    private static final String BASE_URL = "https://bookstore.toolsqa.com";
    public static Response authenticateUser(AuthorizationRequest authRequest) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Response response = request.body(authRequest).post(Route.generateToken());
        return response;
    }

    public static Response getBooks() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Response response = request.get(Route.books());
        return response;
    }

    public static Response addBook(AddBooksRequest addBooksRequest, String token) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        Response response = request.body(addBooksRequest).post(Route.books());
        return response;
    }

    public static Response removeBook(RemoveBookRequest removeBookRequest, String token) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        Response response = request.body(removeBookRequest).delete(Route.book());
        return response;
    }

    public static Response getUserAccount(String userId, String token) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        Response response = request.get(Route.userAccount(userId));
        return response;
    }
}
