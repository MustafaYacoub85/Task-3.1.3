package web;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import web.model.User;

public class Main {
    private static final String REST_URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
// Получаем список
        ResponseEntity<String> response = restTemplate.getForEntity(REST_URL, String.class);
        String sessionId = response.getHeaders().get("set-cookie").get(0);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, sessionId);


// Добавляем юзера
        User user = new User(3L, "James", "Brown", (byte)22);
        HttpEntity<User> request= new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responseSave =
                restTemplate.exchange(REST_URL, HttpMethod.POST, request, String.class);


// Редактируем юзера
        user.setName("Sergey");
        user.setLastName("Petrovich");
        user.setAge((byte) 45);
        HttpEntity<User> updateRequest = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responsePut =
                restTemplate.exchange(REST_URL,HttpMethod.PUT, updateRequest, String.class);

// Удаляем юзера
        ResponseEntity<String> responseDelete =
                restTemplate.exchange(REST_URL +"/3", HttpMethod.DELETE, request, String.class);

// Получаем код

        System.out.println("SessionId: "+responseSave.getBody() + responsePut.getBody()+ responseDelete.getBody());


    }
}