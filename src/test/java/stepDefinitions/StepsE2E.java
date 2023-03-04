package stepDefinitions;

import java.util.List;
import java.util.Map;

import apiEngine.AddBooksRequest;
import apiEngine.AuthorizationRequest;
import apiEngine.ISBN;
import apiEngine.RemoveBookRequest;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepsE2E {
    private static final String USER_ID = "5cfbfc9f-8315-4b5b-83ba-05ae69db1001";
    private static final String BASE_URL = "https://bookstore.toolsqa.com";
    private static String token;
    private static Response response;
    private static String jsonString;
    private static String bookId;

    @Given("I am an authorized user")
    public void i_am_an_authorized_user() {
        AuthorizationRequest authRequest = new AuthorizationRequest("Toma", "Alak*1985");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.body(authRequest).post("/Account/v1/GenerateToken");
        String jsonString = response.asString();
        token = JsonPath.from(jsonString).get("token");
        System.out.println(token);
    }

    @Given("A list of books are available")
    public void a_list_of_books_are_available() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get("/BookStore/v1/Books");
        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);
        System.out.println(books.size());
        bookId = books.get(0).get("isbn");
        System.out.println(bookId);
    }

    @When("I add a book to my reading list")
    public void i_add_a_book_to_my_reading_list() {
        AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, new ISBN(bookId));
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.body(addBooksRequest).post("/BookStore/v1/Books");
    }

    @When("I remove a book from my reading list")
    public void i_remove_a_book_from_my_reading_list() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, bookId);
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.body(removeBookRequest).delete("/BookStore/v1/Book");
    }

    @Then("the book is removed")
    public void the_book_is_removed() {
        //Assert.assertEquals(204, response.getStatusCode());
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
        response = request.get("/Account/v1/User/" + USER_ID);
        //Assert.assertEquals(200, response.getStatusCode());
        jsonString = response.asString();
        List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("books");
        System.out.println(booksOfUser);
//        Assert.assertEquals(0, booksOfUser.size());
    }

}
