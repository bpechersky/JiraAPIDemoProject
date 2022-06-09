package demo;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class oAuthTest {
    public static void main(String[] args){


        String accessTokenRespons = given()
                .queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type","authorization_code")
                .when().post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenRespons);
        String accessToken = js.getString("access_token");


       String response=  given().queryParam("access_token",accessToken)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();
       System.out.println(response);

    }
}
