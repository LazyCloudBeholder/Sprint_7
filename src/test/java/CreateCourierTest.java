import client.ApiClient;
import io.restassured.response.Response;
import model.Courier;
import model.CourierId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCourierTest {
    private String id;
    ApiClient apiClient = new ApiClient();


    @Test
    @DisplayName("Проверка возможности создать курьера")
    public void createCourier(){
        Courier courier = new Courier().setLogin("aKAdaqwdsahdjaskd").setPassword("12345").setFirstName("Pain");
        Courier courierForLogin = new Courier().setLogin("aKAdaqwdsahdjaskd").setPassword("12345");
        Response response = apiClient.createCourier(courier);
        id = apiClient.loginCourier(courierForLogin).as(CourierId.class).getId();
        assertEquals(201, response.getStatusCode(),"Не получилось создать курьера");
        response.then().assertThat().body("ok", equalTo(true));

    }
    @Test
    @DisplayName("Проверка ошибки создания двух одинаковых курьеров")
    public void createTwoSameCouriers(){
        Courier courier = new Courier().setLogin("aKAdaqwdsahdjaskd").setPassword("12345").setFirstName("Pain");
        Courier courierForLogin = new Courier().setLogin("aKAdaqwdsahdjaskd").setPassword("12345");
        Response response1 = apiClient.createCourier(courier);
        id = apiClient.loginCourier(courierForLogin).as(CourierId.class).getId();
        Response response2 = apiClient.createCourier(courier);
        assertEquals(201, response1.getStatusCode(),"Не получилось создать первого курьера");
        assertEquals(409, response2.getStatusCode(),"Ошибка 409 не выявлена");
        response2.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @ParameterizedTest
    @CsvSource({
            " ,password, ",
            "login, ,",
            ", , pain",
            " , , "
    })
    @DisplayName("Проверка ошибки создания курьера без определённых полей")
    public void createCourierWithoutFields(String login, String password, String firstName){
        Courier courier = new Courier().setLogin(login).setPassword(password).setFirstName(firstName);
        Response response = apiClient.createCourier(courier);
        assertEquals(400, response.getStatusCode(),"Неправильный статус код");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @AfterEach
    public void tearDown(){
        apiClient.delete(id);
    }



}
