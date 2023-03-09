package stepDefinitions;

import apiEngine.AddBooksRequest;
import apiEngine.AuthorizationRequest;
import apiEngine.ISBN;
import apiEngine.RemoveBookRequest;
import apiEngine.model.Book;
import apiEngine.model.responses.Books;
import apiEngine.model.responses.Token;
import apiEngine.model.responses.UserAccount;
import org.junit.Assert;
import apiTests.Endpoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StepsE2E {
    private static final String USER_ID = "5cfbfc9f-8315-4b5b-83ba-05ae69db1001";
    private static Response response;
    private static Token tokenResponse;
    private static Book book;

    @Given("I am an authorized user")
    public void i_am_an_authorized_user() {
        AuthorizationRequest authRequest = new AuthorizationRequest("Alak", "Toma*1996");
        response = Endpoints.authenticateUser(authRequest);
        tokenResponse = response.getBody().as(Token.class);
    }

    @Given("A list of books are available")
    public void a_list_of_books_are_available() {
        response = Endpoints.getBooks();
        Books books = response.getBody().as(Books.class);
        book = books.books.get(0);
    }

    @When("I add a book to my reading list")
    public void i_add_a_book_to_my_reading_list() {
        ISBN isbn = new ISBN(book.isbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, isbn);
        response = Endpoints.addBook(addBooksRequest, tokenResponse.token);
    }

    @When("I remove a book from my reading list")
    public void i_remove_a_book_from_my_reading_list() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, book.isbn);
        response = Endpoints.removeBook(removeBookRequest, tokenResponse.token);
    }

    @Then("the book is removed")
    public void the_book_is_removed() {
        response = Endpoints.getUserAccount(USER_ID, tokenResponse.token);
        Assert.assertEquals(200, response.getStatusCode());
        UserAccount userAccount = response.getBody().as(UserAccount.class);
        Assert.assertEquals(0, userAccount.books.size());
    }

}
