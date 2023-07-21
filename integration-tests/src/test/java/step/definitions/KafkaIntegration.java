package step.definitions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.example.events.domain.CreditCardBalance;
import com.kafka.example.events.domain.Inventory;
import com.kafka.example.events.domain.Order;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class KafkaIntegration {

    private static final String ORDER_BASE_URL = "http://localhost:8090";
    private static final String CREDIT_CARD_BASE_URL = "http://localhost:8082";
    private static final String INVENTORY_BASE_URL = "http://localhost:8083";
    private static final String HTTP_STATUS_OK = "200";
    private Response response;
    private Scenario scenario;
    private static Order orderCreated;
    private static Inventory currentInventory;
    private static CreditCardBalance currentBalance;
    private static Inventory updatedInventory;
    private static CreditCardBalance updatedBalance;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("Get current inventory by productId {string}")
    public void getCurrentInventoryByProductId(String url) throws URISyntaxException {
        RestAssured.baseURI = INVENTORY_BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
        currentInventory = response.then().extract().body().as(Inventory.class);
    }

    @Then("Validate current inventory")
    public void validateCurrentInventory() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        Assert.assertNotNull(currentInventory);
        Assert.assertEquals(2, currentInventory.getProductId());
    }

    @Given("Get current balance by customerId {string}")
    public void getCurrentBalanceByCustomerId(String url) throws URISyntaxException {
        RestAssured.baseURI = CREDIT_CARD_BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
        currentBalance = response.then().extract().body().as(CreditCardBalance.class);
    }

    @Then("Validate current balance")
    public void validateCurrentBalance() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        Assert.assertNotNull(currentBalance);
        Assert.assertEquals(1, currentBalance.getCustomerId());
    }

    @Given("Get new inventory after update {string}")
    public void getNewInventoryAfterUpdate(String url) throws URISyntaxException {
        RestAssured.baseURI = INVENTORY_BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
        updatedInventory = response.then().extract().body().as(Inventory.class);
    }

    @Then("Check new inventory")
    public void checkNewInventory() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        Assert.assertNotNull(updatedInventory);
        Assert.assertEquals(2, updatedInventory.getProductId());
        Assert.assertNotEquals(updatedInventory.getQuantity(), currentInventory.getQuantity());
    }

    @Given("Get new balance after update {string}")
    public void getNewBalanceAfterUpdate(String url) throws URISyntaxException {
        RestAssured.baseURI = CREDIT_CARD_BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
        updatedBalance = response.then().extract().body().as(CreditCardBalance.class);
    }

    @Then("Check new balance")
    public void checkNewBalance() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        Assert.assertNotNull(updatedBalance);
        Assert.assertEquals(1, updatedBalance.getCustomerId());
        Assert.assertNotEquals(currentBalance.getAmount(), updatedBalance.getAmount());
        Assert.assertEquals((updatedBalance.getAmount() + orderCreated.getPrice()), currentBalance.getAmount(), 0);
    }

    @Given("Create order {string}")
    public void createOrder(String url) throws URISyntaxException, IOException {
        InputStream order_body = this.getClass().getClassLoader().getResourceAsStream("requestsBodies/order_body.json");

        ObjectMapper mapper = new ObjectMapper();
        orderCreated = mapper.readValue(order_body, Order.class);

        RestAssured.baseURI = ORDER_BASE_URL;
        Header header = new Header("Content-Type", "application/json");
        RequestSpecification requestSpecification = RestAssured.given()
                .accept("*/*")
                .header(header)
                .body(orderCreated);
        response = requestSpecification.when()
                .post(new URI(url));
    }

    @Then("Validate order {string}")
    public void validateOrder(String status) {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(status, String.valueOf(actualResponseStatusCode));
    }
}
