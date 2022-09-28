package com.Assignment5;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Tests {



    @Test
    public void TestingGET(){

        Response resp = given()
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users/2")
                .then().extract().response();

        Users obj = resp.body().as(Users.class);

        System.out.println(obj.getData().getFirstName());

        System.out.println(obj.getData().getEmail());

        System.out.println(obj.getData().getId());
        System.out.println("I am new test : " +resp.statusCode());
    }


    @Test
    public void CreateTest(){
        //update API test

        UpdateJson body = new UpdateJson();

        body.setName("Rehan Ejaz");
        body.setJob("Automation Engineer");
        body.setId("1232");



        Response response = given()
                .header("Content-Type","application/json")
                .when().body(body)  .post("https://reqres.in/api/users")
                .then().extract().response();
        System.out.println("Statu code " +response.statusCode());

        UpdateJson obj  = response.body().as(UpdateJson.class);
        System.out.println(obj.getId());
        System.out.println(obj.getName());
        System.out.println(obj.getJob());

        int statusCode = response.getStatusCode();
        String ContentType = response.getContentType();

        Allure.step("Status code : " + statusCode);
        Allure.step("Conntent type: " + ContentType);

        Assert.assertEquals(statusCode, 201);
        Assert.assertEquals(ContentType, "application/json; charset=utf-8");
        System.out.println("end");






    }


}
