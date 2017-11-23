package api.rest;

import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAPIMethods {

	protected String basePath;
	protected String baseURL;
	protected ContentType contentType;

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public ContentType getContentType(ContentType contentType) {
		this.contentType = contentType;
		return contentType;
	}

	/**
	 * @method Sets HashMap object containing request parameters, key and value should follow one after another
	 * e.g. setParametersMap("key1", "value1", "key2", "value2")
	 * @param keyOrValue
	 */
	public HashMap<String, String> setParametersMap(String... keyOrValue) {
		HashMap<String, String> params = new HashMap<String, String>();
		for (int i = 0; i < keyOrValue.length; i += 2) {
			params.put(keyOrValue[i], keyOrValue[i + 1]);
		}
		return params;
	}

	public ReadContext getJsonContext(HashMap<String, String> params) {
		String json = given()
							.params(params)
						.when()
							.get()
						.then()
							.extract().response().asString();
		return JsonPath.parse(json);
	}

	/**
	 * @method: Returns JsonPath object
	 * First convert the API's response to String type with "asString()" method.
	 * Then, send this String formatted json response to the JsonPath class and return the JsonPath
	 * @param response
	 * @return JsonPath object
	 */
//	public JsonPath getJsonResponse (Response response) {
//		String json = response.asString();
//		return new JsonPath(json);
//	}

	/**
	 * @method Returns response for GET request with parameters
	 * @param parameters
	 */
	public Response getResponseWithParameters(Map<String, String> parameters) {
		RestAssured.baseURI = baseURL;
		return  given()
				.contentType(ContentType.JSON)
				.params(parameters)
				.get(basePath);
	}

	/**
	 * @method Returns response for GET request with cookies set
	 * @param cookies
	 */
	public Response getResponseWithCookies(Map<String, String> cookies) {
		RestAssured.baseURI = baseURL;
		return  given()
				.contentType(ContentType.JSON)
				.cookies(cookies)
				.get(basePath);
	}

	/**
	 * @method Returns response for POST request having body and cookies set
	 * @param baseURL
	 * @param basePath
	 * @param cookies
	 * @param body
	 */
	public Response postDataWithBody(String baseURL,
									 String basePath,
									 Map<String, String> cookies,
									 Object body) {
		RestAssured.baseURI = baseURL;
		return  given()
				.contentType(ContentType.JSON)
				.cookies(cookies)
				.body(body)
				.post(basePath);
	}

	/***
	 * @method Returns response for POST request having query params and cookies set
	 * @param baseURL
	 * @param basePath
	 * @param cookies
	 * @param queryParams
	 */
	public Response postDataWithQueryParams(String baseURL,
											String basePath,
											Map<String, String> cookies,
											Map<String, String> queryParams) {
		RestAssured.baseURI=baseURL;
		return  given()
				.contentType(ContentType.JSON)
				.cookies(cookies)
				.queryParams(queryParams)
				.post(basePath);
	}
	/***
	 * @method Returns Response for POST request having only query params
	 * @param baseURL
	 * @param basePath
	 * @param queryParams
	 */
	public static Response postDataWithQueryParams(String baseURL,
												   String basePath,
												   Map<String, String> queryParams) {
		RestAssured.baseURI = baseURL;
		return  given()
				.contentType(ContentType.JSON)
				.queryParams(queryParams)
				.post(basePath);
	}
}
