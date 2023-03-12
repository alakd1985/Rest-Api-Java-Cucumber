package stepDefinitions;

import apiEngine.AddBooksRequest;
import apiEngine.ISBN;
import apiEngine.RemoveBookRequest;
import apiEngine.model.Book;
import apiEngine.model.responses.Books;
import apiEngine.model.responses.UserAccount;
import apiTests.IRestResponse;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;


public class BooksSteps extends BaseStep {
    public BooksSteps(TestContext testContext) {
        super(testContext);
    }

    @When("A list of books are available")
    public void a_list_of_books_are_available() {
        IRestResponse<Books> booksResponse = getEndPoints().getBooks();
        Book book = booksResponse.getBody().books.get(0);
        getScenarioContext().setContext(Context.BOOK, book);
    }

    @And("I add a book to my reading list")
    public void i_add_a_book_to_my_reading_list() {
        Book book = (Book) getScenarioContext().getContext(Context.BOOK);
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);
        ISBN isbn = new ISBN(book.isbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(userId, isbn);
        IRestResponse<UserAccount> userAccountResponse = getEndPoints().addBook(addBooksRequest);
        getScenarioContext().setContext(Context.USER_ACCOUNT_RESPONSE, userAccountResponse);
    }

    @And("I remove a book from my reading list")
    public void i_remove_a_book_from_my_reading_list() {
        Book book = (Book) getScenarioContext().getContext(Context.BOOK);
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(userId, book.isbn);
        Response response = getEndPoints().removeBook(removeBookRequest);
        getScenarioContext().setContext(Context.BOOK_REMOVE_RESPONSE, response);
    }
}
