package stepDefinitions;

import apiEngine.AddBooksRequest;
import apiEngine.ISBN;
import apiEngine.RemoveBookRequest;
import apiEngine.model.Book;
import apiEngine.model.responses.Books;
import apiEngine.model.responses.UserAccount;
import apiTests.IRestResponse;
import cucumber.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

public class BooksSteps extends BaseStep {
    private Response response;
    private IRestResponse<UserAccount> userAccountResponse;
    private Book book;
    private final String USER_ID = "b8d7f02b-fe57-494f-b59d-0670027b1e1e";

    public BooksSteps(TestContext testContext) {
        super(testContext);
    }

    @When("A list of books are available")
    public void a_list_of_books_are_available() {
        IRestResponse<Books> booksResponse = getEndPoints().getBooks();
        book = booksResponse.getBody().books.get(0);
    }

    @And("I add a book to my reading list")
    public void i_add_a_book_to_my_reading_list() {
        ISBN isbn = new ISBN(book.isbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, isbn);
        userAccountResponse = getEndPoints().addBook(addBooksRequest);
    }

    @And("I remove a book from my reading list")
    public void i_remove_a_book_from_my_reading_list() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, book.isbn);
        response = getEndPoints().removeBook(removeBookRequest);
    }

    @Then("the book is removed")
    public void the_book_is_removed() {
        Assert.assertEquals(204, response.getStatusCode());
        userAccountResponse = getEndPoints().getUserAccount(USER_ID);
        Assert.assertEquals(200, userAccountResponse.getStatusCode());
        Assert.assertEquals(0, userAccountResponse.getBody().books.size());
    }
}
