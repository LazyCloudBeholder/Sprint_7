package client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import model.Order;
import model.OrderListRequest;
import model.OrderTrack;

import static io.restassured.RestAssured.given;

public class ApiClient {


    public ApiClient() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    @Step
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }
    @Step
    public Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }
    @Step
    public void delete(String id) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier" + "/" + id);
    }

    @Step
    public Response createOrder(Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
    @Step
    public void deleteOrder(OrderTrack track){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(track)
                .when()
                .put("/api/v1/orders/cancel");
    }

    @Step
    public Response getOrdersList(OrderListRequest request){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .get("/api/v1/orders");
    }

}