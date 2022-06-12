package demo;

import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class oAuthTest {
    public static void main(String[] args) throws InterruptedException {
/*        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("bpechersky@gmail.com");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Budman1967!!!");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        String url = driver.getCurrentUrl();*/
/*        String url  = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWjhr-YD_mh16oWWcwlSIp83qQlno6-AiJjAwn3s5vEdJZQPI9rZgxw0qvBrf1lKQw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
        String partialString  =  url.split("code=")[1];
        String code = partialString.split("&scope")[0];
        System.out.println(code);*/
        String code = "4%2F0AX4XfWhBXmeIVsScuvtFv5hFTyaOVx7hrbmfpUF_nNJ3fiGLUeBIeDwY4IV9SqhnWIPsoA";


        String accessTokenRespons = given().urlEncodingEnabled(false)
                .queryParam("code",code)
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
