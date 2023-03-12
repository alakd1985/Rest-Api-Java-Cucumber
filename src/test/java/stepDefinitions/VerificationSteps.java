package stepDefinitions;

import apiEngine.model.responses.UserAccount;
import apiTests.IRestResponse;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;

public class VerificationSteps extends BaseStep{

    public VerificationSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("the book is removed")
    public void the_book_is_removed() {
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);
        Response response = (Response) getScenarioContext().getContext(Context.BOOK_REMOVE_RESPONSE);
        Assert.assertEquals(200, response.getStatusCode());
        IRestResponse<UserAccount> userAccountResponse = getEndPoints().getUserAccount(userId);
        Assert.assertEquals(200, userAccountResponse.getStatusCode());
        Assert.assertEquals(0, userAccountResponse.getBody().books.size());
    }
}
