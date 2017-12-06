package tests.API;

import api.rest.RestAPIMethods;
import com.jayway.jsonpath.ReadContext;
import helpers.ExtentReportFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class MapsDistanceMatrixTestSuite extends RestAPIMethods {

    private SoftAssert softAssert;
    private HashMap<String, String> parametersMap;

    @BeforeSuite
    public void setupReport() {
        ExtentReportFactory.initExtentReports();
    }

    @Parameters({"apiBaseURL", "apiMethodBasePath", "apiType"})
    @BeforeClass
    public void testSuitePrepare(String apiBaseURL, String apiMethodBasePath, String apiType) {
        RestAssured.baseURI = apiBaseURL;
        RestAssured.basePath = apiMethodBasePath + apiType;
    }

    @BeforeMethod
    public void testPrepare() {
        softAssert = new SoftAssert();
    }

    @Test(priority = 1,
            enabled = true,
            description = "Successful response on basic request")
    public void statusSuccessTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        parametersMap = setParametersMap(
                "units", "imperial",
                "origins", "Washington,DC",
                "destinations", "Dallas,TX");

        given().params(parametersMap)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .body("status", equalTo("OK"));
    }

    /**
     * This piece of code represents RESTAssured approach without using Response object or JsonPath library
     */
    @Test(priority = 1,
            enabled = true,
            dependsOnMethods = {"statusSuccessTest"},
            description = "Structure of JSON response - pure Rest-Assured approach")
    public void basicResponseStructureTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        parametersMap = setParametersMap(
                "units", "imperial",
                "origins", "Washington, DC",
                "destinations", "Dallas, TX");
        given()
                .params(parametersMap)
                .when()
                    .get()
                .then()
                    .body("origin_addresses", hasSize(1))
                    .body("destination_addresses", hasSize(1))
                    .body("rows", hasSize(1))
                    .body("rows.elements", hasSize(1))
                    .body("rows.elements.distance", hasSize(1))
                    .body("rows.elements.distance.text", hasSize(1))
                    .body("rows.elements.distance.value", hasSize(1))
                    .body("rows.elements.duration", hasSize(1))
                    .body("rows.elements.duration.text", hasSize(1))
                    .body("rows.elements.duration.value", hasSize(1))
                    .body("status", equalTo("OK"))
                    .body("rows.elements.status", equalTo(Arrays.asList(Arrays.asList("OK"))));
    }

    /**
     * This piece of code represents approach with using JsonPath library
     */
    @Test(priority = 1,
            enabled = true,
            dependsOnMethods = {"statusSuccessTest"},
            description = "Structure of JSON response - with the usage of JsonPath library and SoftAssert")
    public void structureForTwoDestinationsTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        parametersMap = setParametersMap(
                "units", "imperial",
                "origins", "New York, NY",
                "destinations", "Washington, DC|Dallas, TX");

        ReadContext context = getJsonContext(parametersMap);

        softAssert.assertEquals(((ArrayList<String>) context.read("$.origin_addresses")).size(), 1,
                "Only one value of 'origins_addresses' parameter is expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.destination_addresses")).size(), 2,
                "Two values of 'destinations_addresses' parameter are expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.rows[*]")).size(), 1,
                "One place of origin, hence only one row (one object in 'rows' array) is expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.rows[0].elements")).size(), 2,
                "Two destinations, hence two elements in the row are expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.rows[0].elements[0].[*]")).size(), 3,
                "Three sub-elements (distance, duration, status) for the 1st element are expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.rows[0].elements[1].[*]")).size(), 3,
                "Three sub-elements (distance, duration, status) for the 2nd element are expected");
        softAssert.assertEquals(((HashMap<String, String>) context.read("$.rows[0].elements[0].distance")).size(), 2,
                "'distance' object is a map containing 2 key-value pairs");
        softAssert.assertEquals(((HashMap<String, String>) context.read("$.rows[0].elements[0].duration")).size(), 2,
                "'duration' object is a map containing 2 key-value pairs");
        softAssert.assertAll();
    }

    @Test(priority = 2,
            enabled = true,
            dependsOnMethods = {"statusSuccessTest"},
            description = "Types of returned values")
    public void typesOfReturnedValuesTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        parametersMap = setParametersMap(
                "units", "imperial",
                "origins", "New York, NY",
                "destinations", "Dallas, TX");

        ReadContext context = getJsonContext(parametersMap);

        softAssert.assertEquals(context.read("$.rows[0].elements[0].distance.text").getClass(), String.class,
                "'distance.text' value is a string");
        softAssert.assertEquals(context.read("$.rows[0].elements[0].distance.value").getClass(), Integer.class,
                "'distance.value' value is an integer number");
        softAssert.assertEquals(context.read("$.rows[0].elements[0].duration.text").getClass(), String.class,
                "'duration.text' value is a string");
        softAssert.assertEquals(context.read("$.rows[0].elements[0].duration.value").getClass(), Integer.class,
                "'duration.value' value is an integer number");
        softAssert.assertEquals(context.read("$.rows[0].elements[0].status").getClass(), String.class,
                "'status' value is an not an integer number");
        softAssert.assertAll();
    }

    @Test(priority = 3,
            enabled = true,
            dependsOnMethods = {"statusSuccessTest"},
            description = "Structure of JSON response - when 2 origins are in request")
    public void structureForTwoOriginsTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        parametersMap = setParametersMap(
                "units", "imperial",
                "origins", "New York, NY|Chicago, IL",
                "destinations", "Dallas, TX");

        ReadContext context = getJsonContext(parametersMap);

        softAssert.assertEquals(((ArrayList<String>) context.read("$.origin_addresses")).size(), 2,
                "Two values of origins addresses parameter are expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.destination_addresses")).size(), 1,
                "Only one value of destinations addresses parameter is expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.rows[*]")).size(), 2,
                "Two rows (2 objects in 'rows' array) are expected - two places of origin");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.rows[0].elements")).size(), 1,
                "Only one element (because of 1 destination) in first row is expected");
        softAssert.assertEquals(((ArrayList<String>) context.read("$.rows[1].elements")).size(), 1,
                "Only one element (because of 1 destination) in second row is expected");
        softAssert.assertAll();
    }

    @Test(priority = 3,
            enabled = true,
            description = "Unsuccessful response on invalid request")
    public void checkStatusAfterInvalidRequestTest(Method methodInstance) {
        ExtentReportFactory.startTest(methodInstance.getAnnotation(Test.class).description());
        parametersMap = setParametersMap(
                "units", "imperial",
                "origins", "New York, NY",
                "destinations", "");
        given()
                .params(parametersMap)
                .when()
                    .get()
                .then().assertThat()
                    .statusCode(200)
                    .and()
                    .body("status", equalTo("INVALID_REQUEST"));
    }

    @AfterMethod
    public void generateReport(ITestResult result) {
        ExtentReportFactory.generateExtentReport(result);
        ExtentReportFactory.flush();
    }

    @AfterClass
    public void afterClass() { }

    @AfterSuite
    public void closeReport() {
        ExtentReportFactory.close();
    }
}
