package files;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import java.io.File;

import static io.restassured.RestAssured.*;
public class JiraTest {
    public static void main(String[] args){
        RestAssured.baseURI = "http://localhost:8080";
     //Login scenario to get session cookie necessary to create new issue
        SessionFilter session = new SessionFilter();
        String response = given().log().all().header("Content-Type","application/json").
                body("{\"username\":\"bpechersky@gmail.com\",\"password\":\"Budman1967\"}")
                        .filter(session).
                when().post("/rest/auth/1/session").then().log().all().extract().response().asString();


        //Adding comment
        given().log().all().pathParam("id","10009").header("Content-Type","application/json").body("{\n" +
                "    \"body\": \"Adding third comment\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(session).when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201);
        //Adding attachment
        given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id","10009").
                header("Content-type","multipart/form-data")
                .multiPart("file",new File("java.text")).when()
        .post("/rest/api/2/issue/{id}/attachments").
                then().log().all().assertThat().statusCode(200);
        //Get issue
    }
}
