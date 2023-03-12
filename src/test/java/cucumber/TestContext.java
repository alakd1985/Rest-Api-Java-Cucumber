package cucumber;

import apiTests.Endpoints;
import enums.Context;

public class TestContext {
    private final String BASE_URL = "https://bookstore.toolsqa.com";
    private final String USER_ID = "b8d7f02b-fe57-494f-b59d-0670027b1e1e";
    private Endpoints endPoints;
    private ScenarioContext scenarioContext;

    public TestContext() {
        endPoints = new Endpoints(BASE_URL);
        scenarioContext = new ScenarioContext();
        scenarioContext.setContext(Context.USER_ID, USER_ID);
    }

    public Endpoints getEndPoints() {
        return endPoints;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
