package files;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.*;
public class JiraTest {
    public static void main(String[] args){
        RestAssured.baseURI = "http://localhost:8080";
     //Login scenario to get session cookie necessary to create new issue
        SessionFilter session = new SessionFilter();
        String response = given().log().all().relaxedHTTPSValidation().header("Content-Type","application/json").
                body("{\"username\":\"bpechersky@gmail.com\",\"password\":\"Budman1967\"}")
                        .filter(session).
                when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
        String expectedMessage = "HI, How are you!";

        //Adding comment
        String addCommentResponse = given().log().all().pathParam("id","10009").header("Content-Type","application/json").body("{\n" +
                "    \"body\": \""+expectedMessage+"\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(session).when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath js = new JsonPath(addCommentResponse);
        String commentId = js.getString("id");
        //Adding attachment
        given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id","10009").
                header("Content-type","multipart/form-data")
                .multiPart("file",new File("java.text")).when()
        .post("/rest/api/2/issue/{id}/attachments").
                then().log().all().assertThat().statusCode(200);
        //Get issue
        String issueDetails = given().filter(session).pathParam("id","10009")
                .queryParam("fields","comment")
                .log().all().when()
                .get("/rest/api/2/issue/{id}").then().log().all().extract().response().asString();
        JsonPath js1 = new JsonPath(issueDetails);
        int commentsCount = js1.getInt("fields.comment.comments.size");
        for (int i = 0;i < commentsCount; i++)
        {
            String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
            if(commentIdIssue.equals(commentId))
            {
                String message = js1.get("fields.comment.comments["+i+"].body").toString();
                System.out.println(message);
                Assert.assertEquals(message,expectedMessage);
            }
        }
    }
}
