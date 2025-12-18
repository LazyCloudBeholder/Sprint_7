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

public class LoginCourierTest {
    private String id;
    ApiClient apiClient = new ApiClient();


    @Test
    @DisplayName("Проверка возможности логина курьера ")
    public void loginCourier(){
        Courier courier = new Courier().setLogin("aKAdaqwdsahdjaskds").setPassword("12345").setFirstName("Pain");
        Courier courierForLogin = new Courier().setLogin("aKAdaqwdsahdjaskds").setPassword("12345");
        Response response = apiClient.createCourier(courier);
        id = apiClient.loginCourier(courierForLogin).as(CourierId.class).getId();
        assertEquals(201, response.getStatusCode(),"Не получилось создать курьера для входа");
        Response loginResponse = apiClient.loginCourier(courierForLogin);
        assertEquals(200, loginResponse.getStatusCode(),"Не удалось залогиниться");
        loginResponse.then().assertThat().body("id", equalTo(Integer.parseInt(id)));

    }

    @ParameterizedTest
    @CsvSource({
            "  ,password",
            " login,   ",
            "   ,   "
    })
    @DisplayName("Проверка ошибки логина курьера без определённых полей")
    public void enterWithoutLoginOrPassword(String login, String password){
        Courier courier = new Courier().setLogin(login).setPassword(password);
        Response loginResponse = apiClient.loginCourier(courier);
        assertEquals(400, loginResponse.getStatusCode(), "Неправильный статус код");
        loginResponse.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка ошибки логина курьера с неправильный паролем и логином")
    public void enterWithWrongLoginAndPassword(){
        Courier courier = new Courier().setLogin("aKAdaqwdsahdjaskd").setPassword("432145");
        Response loginResponse = apiClient.loginCourier(courier);
        assertEquals(404, loginResponse.getStatusCode(), "Неправильный статус код");
        loginResponse.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @AfterEach
    public void tearDown(){
        apiClient.delete(id);
    }


}
