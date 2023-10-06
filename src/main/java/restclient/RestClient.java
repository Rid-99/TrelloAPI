package restclient;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

	private String baseUri;
//    private static final String BEARER_TOKEN = Configuration.getProperty("token");
	private boolean isAuthorizationHeaderAdded = false;

	private RequestSpecBuilder specBuilder;

	Properties prop;

	public RestClient(Properties prop, String baseURI) {
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseUri = baseURI;
	}

	private void setRequestContentType(String contentType) {
		switch (contentType) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		default:
			break;
		}

	}

	private RequestSpecification creRequestSpec(JSONObject queryParams, String contentType) {
		specBuilder.setBaseUri(baseUri).setContentType(ContentType.JSON);

		setRequestContentType(contentType);

		if (queryParams != null) {
			specBuilder.setBody(queryParams);
		}
		return specBuilder.build();
	}

	private RequestSpecification creRequestSpec(JSONObject queryParams) {
		specBuilder.setBaseUri(baseUri).setContentType(ContentType.JSON);

		if (queryParams != null) {
			specBuilder.setBody(queryParams);
		}
		return specBuilder.build();
	}

	public Response post(String serviceUrl, JSONObject requestBody, String contentType, boolean log) {
		if (log) {
			return RestAssured.given(creRequestSpec(requestBody, contentType)).log().all().when().post(serviceUrl);
		}
		return RestAssured.given(creRequestSpec(requestBody, contentType)).when().post(serviceUrl);
	}

	public Response get(String serviceUrl, JSONObject queryParams, String contentType, boolean log) {
		if (log) {
			return RestAssured.given(creRequestSpec(queryParams, contentType)).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(creRequestSpec(queryParams, contentType)).when().get(serviceUrl);
	}
	
	public Response put(String serviceUrl, JSONObject queryParams, String contentType, boolean log) {

		if (log) {
			return RestAssured.given(creRequestSpec(queryParams, contentType)).log().all().when().put(serviceUrl);
		}
		return RestAssured.given(creRequestSpec(queryParams, contentType)).when().put(serviceUrl);
	}

	public Response delete(String serviceUrl, JSONObject queryParams, boolean log) {

		if (log) {
			return RestAssured.given(creRequestSpec(queryParams)).log().all().when().put(serviceUrl);
		}
		return RestAssured.given(creRequestSpec(queryParams)).when().put(serviceUrl);
	}

}
