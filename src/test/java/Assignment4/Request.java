package Assignment4;

import Assignment3.base;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Request extends base {


    String id = "1";

    @Test
    public void Create_Enviroment() {

        String response = given().header("X-Api-Key", "PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type", "application/json")
                .when().body("{\n" +
                        "    \"environment\": {\n" +
                        "        \"name\": \"Reejaz\",\n" +
                        "        \"values\": [\n" +
                        "            {\n" +
                        "                \"key\":        \"283\",\n" +
                        "                \"value\": \"Value\",\n" +
                        "                \"enabled\": true,\n" +
                        "                \"type\": \"personal\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}")
                .log().all()
                .post(baseURI + basePath).then().assertThat().header("Content-Type", equalTo("application/json; charset=utf-8"))
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        id = jsonPath.get("environment.uid");
        System.out.println("id = " + id);

        try {
            FileWriter fileWriter = new FileWriter("src/test/java/Assignment3/ids.txt");
            fileWriter.write(id);
            fileWriter.close();


        } catch (IOException e) {
            System.out.println(e);
        }

    }


    @Test
    public void Get_Environment() {

        given().header("X-Api-Key", "PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type", "application/json")
                .pathParam("id", "21032804-eac3efdc-8757-4658-a9b3-e8987874aa0a")
                .when().get(baseURI + basePath + "/{id}")
                .then()
                .assertThat().body("environment.values[0].key", equalTo("283"))
                .log().all().statusCode(200);

    }




    @Test
    public void report() {

        Response resp = given()
                .header("X-Api-Key", "PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type", "application/json")
                .pathParam("id", "21032804-a39117a1-e012-4ae5-b469-127f4c35eaf4")
                .when()
                .get("https://api.getpostman.com/environments/{id}")
                .then().extract().response();

        System.out.println(resp.getStatusCode());
        int statusCode = resp.getStatusCode();
        String ContentType = resp.getContentType();

        Allure.step("Status code : " + statusCode);
        Allure.step("Conntent type: " + ContentType);

        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(ContentType, "application/json; charset=utf-8");


    }
}


