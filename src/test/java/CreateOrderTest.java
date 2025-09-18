import client.ApiClient;
import io.restassured.response.Response;
import model.Order;
import model.OrderListRequest;
import model.OrderTrack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTest {
    ApiClient apiClient = new ApiClient();
    OrderTrack track;



    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BLACK", "GREY"}),
                Arguments.of((Object) new String[]{"BLACK"}),
                Arguments.of((Object) new String[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    @DisplayName("Проверка создания заказа")
    public void createOrder(String[] color) {
        Order order = new Order("Andrey", "Kunaev", "Pushkina, 10", "4", "5",
                "88005553535", "2020.05.04", color, "No comments");
        Response response = apiClient.createOrder(order);
        track = response.as(OrderTrack.class);
        assertEquals(201, response.getStatusCode(), "Не удалось создать заказ");
        assertTrue(response.getBody().asString().contains("track"));
        apiClient.deleteOrder(track);
    }

    @Test
    @DisplayName("Проверка возвращения списка заказов")
    public void canGetOrderList(){
        OrderListRequest orderListRequest = new OrderListRequest();
        Response response = apiClient.getOrdersList(orderListRequest);
        assertEquals(200, response.getStatusCode(),"Не получилось получить список заказов");
        assertFalse(response.getBody().asPrettyString().isEmpty());
    }



}