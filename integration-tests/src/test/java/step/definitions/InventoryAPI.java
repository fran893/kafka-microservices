package step.definitions;

import com.kafka.example.events.domain.Inventory;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class InventoryAPI {

    private final String BASE_URL = "http://localhost:8083";
    private Response response;
    private static final String HTTP_STATUS_OK = "200";
    private int previousQuantityInventory;
    private Scenario scenario;
    private static final String UPDATE_SCENARIO_NAME = "Update inventory for given productId in Order";

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("Get inventories {string}")
    @Given("Get inventory by productId {string}")
    public void getInventories(String url) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
        if(scenario.getName().equals(UPDATE_SCENARIO_NAME)) {
            previousQuantityInventory = response.then().extract().body().as(Inventory.class).getQuantity();
        }
    }

    @Then("Response contains inventories")
    public void responseContainsInventories() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        List<Inventory> inventories = response.then().extract().body().as(new TypeRef<List<Inventory>>(){});

        Assert.assertFalse(inventories.isEmpty());
    }

    @Then("Response contains inventory")
    public void responseContainsInventory() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        Inventory inventory = response.then().extract().body().as(Inventory.class);

        Assert.assertNotNull(inventory);
        Assert.assertEquals(2, inventory.getProductId());
    }

    @Given("Send order to {string}")
    public void sendOrderTo(String url) throws URISyntaxException {
        InputStream order_body = this.getClass().getClassLoader().getResourceAsStream("requestsBodies/order_body.json");

        RestAssured.baseURI = BASE_URL;
        Header header = new Header("Content-Type", "application/json");
        RequestSpecification requestSpecification = RestAssured.given()
                .accept("*/*")
                .header(header)
                .body(order_body);
        response = requestSpecification.when()
                .put(new URI(url));
    }

    @Then("Inventory is updated")
    public void inventoryIsUpdated() {
        int actualResponseStatusCode = response.then().extract().statusCode();
        Assert.assertEquals(HTTP_STATUS_OK, String.valueOf(actualResponseStatusCode));

        int newQuantity = response.then().extract().body().as(Inventory.class).getQuantity();

        Assert.assertNotEquals(previousQuantityInventory, newQuantity);
        Assert.assertEquals(previousQuantityInventory - 1, newQuantity);
    }
}
