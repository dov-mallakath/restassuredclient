package com.test;

import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-08
 */
public class TestUtils {

    public static List<Person> getPeople() {
        List<Person> users = new ArrayList<>();
        users.add(new Person("Tim","Testerman",1,"test@hascode.com"));
        users.add(new Person("Sara","Stevens",20,"dev@hascode.com"));
        users.add(new Person("Mark","Mustache",11,"devnull@hascode.com"));
        return users;
    }

    public static List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("BMW",300));
        cars.add(new Car("Audi",150));
        return cars;
    }

    public static List<Article> getArticlesFromJson(String json) {

        JsonPath jsonPath = new JsonPath(json);
        List<Article> articles = jsonPath.getList("", Article.class);
        return articles;

    }



}
