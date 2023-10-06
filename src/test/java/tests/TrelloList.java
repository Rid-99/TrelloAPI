package tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import restclient.RestClient;

public class TrelloList extends BaseTest {

	protected String boardName;
	protected String listName;
	protected String boardId;
	protected String listId;
	protected String createdListName;
	protected String updatedListName;
	protected String archiveListName;
	protected String moveToBoardListName;

	public TrelloList() {
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
	public void createList() {

		logger.info("****************** Create a new List *******************");

		listName = "RestAPIList" + RandomStringUtils.randomNumeric(2);

		JSONObject requestBody = new JSONObject();
		requestBody.put("idBoard", boardId);
		requestBody.put("name", listName);
		requestBody.put("key", key);
		requestBody.put("token", token);

		Response createListDataResponse = restClient.post(Create_List_Endpoint, requestBody, "json", true)
				                         .then()
				                              .log().all()
				                         .assertThat()
				                              .statusCode(200)
				                              .statusLine("HTTP/1.1 200 OK")
				                              .contentType("application/json")
				                         .and()
				                              .extract().response();
		
		listId = createListDataResponse.path("id");

		System.out.println("Created list id: " + listId);
	}

	@Test (priority = 3)
	public void getList() {
		
		logger.info("****************** Get a List *******************");
		
		JSONObject queryParams = new JSONObject();
		queryParams.put("key", key);
		queryParams.put("token", token);
		
		Response getListDataResponse = restClient.get(Get_List_Endpoint + listId, queryParams, "json", false)
				                      .then()
				                           .log().all()
				                      .assertThat()
				                           .statusCode(200)
				                           .statusLine("HTTP/1.1 200 OK")
				                           .contentType("application/json")
				                      .and()
				                           .extract().response();
		
		createdListName = getListDataResponse.path("name");
		System.out.println("Created List Name: " + createdListName);

	}
	
	@Test (priority = 4)
	public void updateList() {
		
		logger.info("****************** Update List *******************");

		
		JSONObject queryParams = new JSONObject();
		queryParams.put("key", key);
		queryParams.put("token", token);
		queryParams.put("name", "UpdatedRestAPIList");
		queryParams.put("pos", "bottom");
		
		Response updateListResponse = restClient.put(Update_List_Endpoint + listId, queryParams, "json", false)
				                     .then()
                                          .log().all()
                                     .assertThat()
                                          .statusCode(200)
                                          .statusLine("HTTP/1.1 200 OK")
                                          .contentType("application/json")
                                     .and()
                                          .extract().response();
		  
		updatedListName = updateListResponse.path("name");
		System.out.println("Updated List Name: " + updatedListName);		
	}
	
	@Test (priority = 5)
	public void archivelist() {
		
		logger.info("****************** Archive or unarchive a list *******************");

		
		JSONObject queryParams = new JSONObject();
		queryParams.put("key", key);
		queryParams.put("token", token);
		queryParams.put("name", "UpdatedRestAPIList");
		queryParams.put("closed", true);
		queryParams.put("pos", "bottom");
		
		Response archiveListResponse = restClient.put(Update_List_Endpoint + listId, queryParams, "json", false)
				                     .then()
                                          .log().all()
                                     .assertThat()
                                          .statusCode(200)
                                          .statusLine("HTTP/1.1 200 OK")
                                          .contentType("application/json")
                                     .and()
                                          .extract().response();
		  
		archiveListName = archiveListResponse.path("name");
		System.out.println("Archive List Name: " + archiveListName);		
	}
	
	@Test (priority = 6)
	public void moveToBoard() {
		
		logger.info("****************** Move List to Board *******************");

		
		JSONObject queryParams = new JSONObject();
		queryParams.put("value", boardId);
		queryParams.put("key", key);
		queryParams.put("token", token);
		
		Response movetoBoardResponse = restClient.put(MoveTo_Board_firstEndpoint + listId + MoveTo_Board_secondEndpoint, queryParams, "json", false)
				                     .then()
                                          .log().all()
                                     .assertThat()
                                          .statusCode(200)
                                          .statusLine("HTTP/1.1 200 OK")
                                          .contentType("application/json")
                                     .and()
                                          .extract().response();
		  
		moveToBoardListName = movetoBoardResponse.path("name");
		System.out.println("Archive List Name: " + moveToBoardListName);		
	}
	
	@Test (priority = 7)
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
