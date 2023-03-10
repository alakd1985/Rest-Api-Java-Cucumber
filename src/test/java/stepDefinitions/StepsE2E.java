package stepDefinitions;

import apiEngine.AddBooksRequest;
import apiEngine.AuthorizationRequest;
import apiEngine.ISBN;
import apiEngine.RemoveBookRequest;
import apiEngine.model.Book;
import apiEngine.model.responses.Books;
import apiEngine.model.responses.Token;
import apiEngine.model.responses.UserAccount;
import apiTests.IRestResponse;
import org.junit.Assert;
import apiTests.Endpoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StepsE2E {
    private static final String USER_ID = "5cfbfc9f-8315-4b5b-83ba-05ae69db1001";
    private Response response;
    private IRestResponse<UserAccount> userAccountResponse;
    private Book book;
    private final String BaseUrl = "https://bookstore.toolsqa.com";
    private Endpoints endPoints;

    @Given("I am an authorized user")
    public void i_am_an_authorized_user() {
        endPoints = new Endpoints(BaseUrl);
        AuthorizationRequest authRequest = new AuthorizationRequest("Alak", "Toma*1996");
    }

    @Given("A list of books are available")
    public void a_list_of_books_are_available() {
        IRestResponse<Books> booksResponse = endPoints.getBooks();
        book = booksResponse.getBody().books.get(0);
    }

    @When("I add a book to my reading list")
    public void i_add_a_book_to_my_reading_list() {
        ISBN isbn = new ISBN(book.isbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, isbn);
        userAccountResponse = endPoints.addBook(addBooksRequest);
    }

    @When("I remove a book from my reading list")
    public void i_remove_a_book_from_my_reading_list() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, book.isbn);
                response = endPoints.removeBook(removeBookRequest);
    }

    @Then("the book is removed")
    public void the_book_is_removed() {
        Assert.assertEquals(204, response.getStatusCode());
        
                userAccountResponse = endPoints.getUserAccount(USER_ID);
                Assert.assertEquals(200, userAccountResponse.getStatusCode());
    }

}