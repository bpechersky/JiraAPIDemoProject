import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
public class Basics {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//validate if Add Place API is working
		//given-all input details - query params,headers,body
		//when- Submit the API - resource,http method
		//then - validate the response
		//content of file to String->content of file can conver to Byte->Byte data to String
		RestAssured.baseURI = "http://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo( "APP")).header("Server", "Apache/2.4.41 (Ubuntu)")
		.extract().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);// for parsing json
		String placeid = js.getString("place_id");
		System.out.println(placeid);
		//Update Place
		String newAddress = "33 summer walk, Russia";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		//Get Place
		String getPlaceResponse =  given().log().all().queryParam("key","qaclick123" )
		.queryParam("place_id",placeid)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress =  js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	}
}
