package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        List<User> users = new ArrayList<>();
        users.add(new User("Ivan", "Ivanov", (byte) 23));
        users.add(new User("Petr", "Petrov", (byte) 19));
        users.add(new User("John", "Malcovic", (byte) 56));
        users.add(new User("Alex", "Aldonin", (byte) 34));

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        for (User user : users) {
            System.out.println("Добавление в БД записи " + user);
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        List<User> usersFromDb = userService.getAllUsers();
        System.out.println("Список всех записей в БД: " + usersFromDb);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
