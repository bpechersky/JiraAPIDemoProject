import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DynamicJson {
	@Test(dataProvider="BooksData")

	public void addBook(String isbn, String aisle) {
		String bookID = isbn+aisle;
		 RestAssured.baseURI = "https://rahulshettyacademy.com";
		 String response = given()./*log().all().*/header("Content-Type","application/json")
		.body(payload.Addbook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then()./*log().all().*/assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println("Added Book id is " + id);

		String deleteBookResponse = given()./*log().all().*/header("Content-Type","application/json")
				.body(payload.Deletebook(isbn,aisle))
					.when().post("/Library/DeleteBook.php")
					.then()./*log().all().*/assertThat().statusCode(200)
					.extract().response().asString();
		JsonPath js1 = ReUsableMethods.rawToJson(deleteBookResponse);
		String deletedBookMsg = js.get("msg");
		System.out.println("deleted Book message is " + deletedBookMsg);





	}
//	@Test
//	public void deleteBook()
//	{
//		 RestAssured.baseURI = "https://rahulshettyacademy.com";
//		 String response = given().log().all().header("Content-Type","application/json")
//		.body(payload.Addbook("dkks","4433"))
//		.when().delete("Library/DeleteBook.php")
//		.then().log().all().assertThat().statusCode(200)
//		.extract().response().asString();
//		 JsonPath js = ReUsableMethods.rawToJson(response);
//		 String responseMessage = js.get("msg");
//		 Assert.assertEquals(response, responseMessage);
//		 
//	}
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		//array is collection of elements
		//multidimensional array is collection of arrays
		
		return new Object[][] {{"anton","1"},{"piter","1"},{"mike","1"}};
	}
	



}
	
