package step.definitions;

import com.kafka.example.events.domain.CreditCardBalance;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class BalancesAPI {

    private final String BASE_URL = "http://localhost:8082";
    private Response response;
    private Scenario scenario;
    private static final String HTTP_STATUS_OK = "200";

    @Given("Get balances {string}")
    public void getBalances(String url) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
    }

    @Then("Response contains balances")
    public void responseContainsBalances() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        List<CreditCardBalance> balances = response.then().extract().as(new TypeRef<List<CreditCardBalance>>(){});

        Assert.assertFalse(balances.isEmpty());
    }

    @Given("Get balance by customerId {string} {string}")
    public void getBalanceByCustomerId(String url, String customerId) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
    }

    @Then("Response contain balance")
    public void responseContainBalance() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        CreditCardBalance balance = response.then().extract().body().as(CreditCardBalance.class);

        Assert.assertNotNull(balance);
        Assert.assertEquals(1, balance.getCustomerId());
    }
}
