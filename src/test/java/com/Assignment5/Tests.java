package com.Assignment5;

import Assignment3.Rest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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


}
