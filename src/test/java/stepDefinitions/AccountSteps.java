package stepDefinitions;
import apiEngine.AuthorizationRequest;
import cucumber.TestContext;
import io.cucumber.java.en.Given;

public class AccountSteps extends BaseStep {
    public AccountSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("I am an authorized user")
    public void i_am_an_authorized_user() {
        AuthorizationRequest authRequest = new AuthorizationRequest("Krish", "Alak*1985");
        //getEndPoints().authenticateUser(authRequest);
    }
}
