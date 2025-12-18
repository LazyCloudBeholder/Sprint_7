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
    @Step("Отправляет запрос на создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }
    @Step("Отправляет запрос на логин курьера")
    public Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }
    @Step("Отправляет запрос на удаление курьера")
    public void delete(String id) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier" + "/" + id);
    }

    @Step("Отправляет запрос на создание заказа")
    public Response createOrder(Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
    @Step("Отправляет запрос на удаление заказа")
    public void deleteOrder(OrderTrack track){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(track)
                .when()
                .put("/api/v1/orders/cancel");
    }

    @Step("Отправляет запрос на получение списка заказов")
    public Response getOrdersList(OrderListRequest request){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .get("/api/v1/orders");
    }

}