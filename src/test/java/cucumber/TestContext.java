package cucumber;

import apiTests.Endpoints;
import dataProvider.ConfigReader;
import enums.Context;

import java.net.MalformedURLException;

public class TestContext {

    private Endpoints endPoints = new Endpoints(ConfigReader.getInstance().getBaseUrl());
    private ScenarioContext scenarioContext;

    public TestContext() throws MalformedURLException {
        scenarioContext = new ScenarioContext();
        scenarioContext.setContext(Context.USER_ID, ConfigReader.getInstance().getUserID());
    }

    public Endpoints getEndPoints() {
        return endPoints;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
