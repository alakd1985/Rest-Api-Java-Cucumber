package stepDefinitions;

import apiTests.Endpoints;
import cucumber.TestContext;

public class BaseStep {
    private static final String BASE_URL = "https://bookstore.toolsqa.com";
    private Endpoints endPoints;

    public BaseStep(TestContext testContext) {
        endPoints = testContext.getEndPoints();
    }

    public Endpoints getEndPoints() {
        return endPoints;
    }
}
