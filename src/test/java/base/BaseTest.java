package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import restclient.RestClient;

public class BaseTest {

	protected static final String Create_board_Endpoint = "/boards";
	protected static final String Get_board_Endpoint = "/boards/";
	protected static final String Update_board_Endpoint = "/boards/";
	protected static final String Delete_board_Endpoint = "/boards/";
	protected static final String Create_List_Endpoint = "/lists";
	protected static final String Get_List_Endpoint = "/lists/";
	protected static final String Update_List_Endpoint = "/lists/";
	protected static final String Archive_List_Endpoint = "/lists/";
	protected static final String MoveTo_Board_firstEndpoint = "/lists/" ;
	protected static final String MoveTo_Board_secondEndpoint = "/idBoard";

	
	

	protected static RestClient restClient;
	private FileInputStream ip;
	protected Properties prop;
	public Logger logger;
	protected static String baseURI;
	protected static String key;
	protected static String token;

	public BaseTest() {

		RestAssured.filters(new AllureRestAssured());

		prop = new Properties();
		try {
			ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		baseURI = prop.getProperty("uri");
		key = prop.getProperty("key");
		token = prop.getProperty("token");
		
		logger=Logger.getLogger("TrelloRestAPI");//added Logger
		PropertyConfigurator.configure("./src/test/resources/config/Log4j.properties"); //added logger
		logger.setLevel(Level.DEBUG);	
	}

}
