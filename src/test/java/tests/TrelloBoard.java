package tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import restclient.RestClient;

public class TrelloBoard extends BaseTest {

	protected String boardName;
	protected static String boardId;
	protected String createdBoardName;
	protected String updateddBoardName;



	public TrelloBoard() {
		super();
	}

	@BeforeClass
	public void setup() {
		restClient = new RestClient(prop, baseURI);
	}

	@Test (priority = 1)
	public void createBoard() {

		logger.info("****************** Create Board *******************");

		boardName = "RestAPI" + RandomStringUtils.randomNumeric(2);

		JSONObject requestBody = new JSONObject();
		requestBody.put("name", boardName);
		requestBody.put("defaultLabels", true);
		requestBody.put("defaultLists", true);
		requestBody.put("key", key);
		requestBody.put("token", token);

		Response createBoardDataResponse = restClient.post(Create_board_Endpoint, requestBody, "json", true)
				                         .then()
				                              .log().all()
				                         .assertThat()
				                              .statusCode(200)
				                              .statusLine("HTTP/1.1 200 OK")
				                              .contentType("application/json")
				                         .and()
				                              .extract().response();
		
		boardId = createBoardDataResponse.path("id");

		System.out.println("Created board ID: " + boardId);
	}

	@Test (priority = 2)
	public void getBoard() {
		
		logger.info("****************** Get Board *******************");
		
		JSONObject queryParams = new JSONObject();
		queryParams.put("key", key);
		queryParams.put("token", token);
		
		Response getBoardDataResponse = restClient.get(Get_board_Endpoint + boardId, queryParams, "json", false)
				                      .then()
				                           .log().all()
				                      .assertThat()
				                           .statusCode(200)
				                           .statusLine("HTTP/1.1 200 OK")
				                           .contentType("application/json")
				                      .and()
				                           .extract().response();
		
		createdBoardName = getBoardDataResponse.path("name");
		System.out.println("Created board Name: " + createdBoardName);

	}
	
	@Test (priority = 3)
	public void updateBoard() {
		
		logger.info("****************** Update Board *******************");

		
		JSONObject queryParams = new JSONObject();
		queryParams.put("key", key);
		queryParams.put("token", token);
		queryParams.put("name", "UpdatedRestAPIBoard");
		
		Response updateBoardResponse = restClient.put(Update_board_Endpoint + boardId, queryParams, "json", false)
				                     .then()
                                          .log().all()
                                     .assertThat()
                                          .statusCode(200)
                                          .statusLine("HTTP/1.1 200 OK")
                                          .contentType("application/json")
                                     .and()
                                          .extract().response();
		  
		updateddBoardName = updateBoardResponse.path("name");
		System.out.println("Updated board Name: " + updateddBoardName);		
	}
	
	@Test (priority = 4)
	public void deleteBoard() {
		
		logger.info("****************** Delete Board *******************");

		
		JSONObject queryParams = new JSONObject();
		queryParams.put("key", key);
		queryParams.put("token", token);
		
		Response deleteBoardDataResponse = restClient.delete(Delete_board_Endpoint + boardId, queryParams, true)
				                          .then()
				                               .log().all()
				                          .assertThat()
				                               .statusCode(200)
                                               .statusLine("HTTP/1.1 200 OK")
                                               .contentType("application/json")
                                          .and()
                                               .extract().response();
		
		System.out.println("Board Deleted Successfully");
		
		
	}


}
