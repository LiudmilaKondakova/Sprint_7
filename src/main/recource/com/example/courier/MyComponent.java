package recource.com.example.courier;

public interface MyComponent {
    String FIELD_MESSAGE = "message";
    String FIELD_OK = "ok";
    String MESSAGE_NOT_FOUND = "Курьера с таким id нет.";
    String RESPONSE = "Получен ответ от сервера: {}";
    String DELETE = "Удаление курьера с id = {}";
    String ROOT = "/courier";
    String CREATE_COURIER = "Создание курьера: {}";
    String COURIER_AUTHORIZATION = "Авторизация курьера: {}";

    String LOGIN = "/login";
    String MESSAGE_NO_ENOUGH_DATA = "Недостаточно данных для входа";
    String MESSAGE_COURIER_NOT_FOUND = "Учетная запись не найдена";


    String MESSAGE_EXISTING_LOGIN = "Этот логин уже используется. Попробуйте другой.";
    String MESSAGE_WITHOUT_REQUIRED_FIELDS = "Недостаточно данных для создания учетной записи";
    String FIELD_TRACK = "track";

    String ORDERS = "/orders";

    String MESSAGE_ORDER_NOT_FOUND = "Заказ не найден";
    String MESSAGE_NOT_ENOUGH_DATA = "Недостаточно данных для поиска";
    String FIELDS_ORDER = "orders";
    String ACCEPT = "/accept";
}
