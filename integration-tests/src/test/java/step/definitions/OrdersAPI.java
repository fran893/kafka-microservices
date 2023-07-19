package step.definitions;

import com.kafka.example.events.domain.Order;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OrdersAPI {

    private final String BASE_URL = "http://localhost:8090";
    private Response response;
    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("Get call to {string}")
    public void getCallTo(String url) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
    }

    @Then("Response is {string}")
    public void responseIs(String statusCode) {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(statusCode, String.valueOf(actualResponseStatusCode));
    }

    @Given("Post call to {string}")
    public void postCallTo(String url) throws URISyntaxException {
        InputStream order_body = this.getClass().getClassLoader().getResourceAsStream("requestsBodies/order_body.json");

        RestAssured.baseURI = BASE_URL;
        Header header = new Header("Content-Type", "application/json");
        RequestSpecification requestSpecification = RestAssured.given()
                .accept("*/*")
                .header(header)
                .body(order_body);
        response = requestSpecification.when()
                .post(new URI(url));
    }

    @Then("Response contains")
    public void responseContains() {
        List<Order> bodyResponse = response.then().extract().body().as(new TypeRef<List<Order>>(){});

        Assert.assertFalse(bodyResponse.isEmpty());
        Assert.assertEquals(1, bodyResponse.get(0).getCustomerId());
    }
}
