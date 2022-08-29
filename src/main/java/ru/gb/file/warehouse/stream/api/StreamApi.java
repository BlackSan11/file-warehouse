package ru.gb.file.warehouse.stream.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamApi {

    public static void main(String[] args) {
        User bogdan = new User("Bogdan", Arrays.asList(createOrder(101), createOrder(10)));
        User ivan = new User("Ivan", Arrays.asList(createOrder(10010), createOrder(77777)));
        User sergey = new User("Sergey", List.of(createOrder(10)));

        List<User> userList = new ArrayList<>();
        userList.add(bogdan);
        userList.add(ivan);
        userList.add(sergey);

        userList.stream()
                .filter(user -> user.getName().length() > 5)
                .peek(user -> {user.setName(user.getName() + " peek");})
                .parallel()
                .collect(Collectors.toMap(User::getName, u -> u.getOrders().size()))
                .forEach((key, value) -> {
                    System.out.println(key + " - " + value);
                });

        System.out.println("#######");
        List<String> userNameList = userList.stream()
                .filter(user -> user.getName().length() > 5)
                .map(User::getName)
                .collect(Collectors.toList());
        userNameList.forEach(System.out::println);

        BigDecimal totalAmount = userList.stream()
                .flatMap(user -> user.getOrders().stream())
                .map(order -> order.amount)
                .reduce(BigDecimal::add)
                .orElse(null);
        System.out.println("Total amount: " + totalAmount);

        long integerStream = userList.stream()
                .flatMap(user -> user.getOrders().stream())
                .map(order -> order.amount)
                .map(BigDecimal::intValue)
                .count();
        System.out.println(integerStream);

        IntStream.range(0, 10)
                .forEach(System.out::println);
    }

    public static Order createOrder(long value) {
        return new Order(BigDecimal.valueOf(value));
    }

}
