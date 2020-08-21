package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String dbCreationRequest = "CREATE DATABASE IF NOT EXISTS test";
        String tableCreationRequest = "CREATE TABLE IF NOT EXISTS test.persons (" +
                "user_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(255), " +
                "lastname varchar(255), " +
                "age int)";
        try {
            Connection connection = Util.getConnection();
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(dbCreationRequest);
                    statement.execute(tableCreationRequest);
                }
            }
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String dbDropRequest = "DROP DATABASE IF EXISTS test";
        try {
            Connection connection = Util.getConnection();
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(dbDropRequest);
                }
            }
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveRequest = "INSERT INTO test.persons (name, lastname, age) VALUES (?, ?, ?)";
        try {
            Connection connection = Util.getConnection();
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(saveRequest)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setByte(3, age);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при записи пользователя в БД");
        }
    }

    public void removeUserById(long id) {
        String removeRequest = "DELETE FROM test.persons WHERE user_id = ?";
        try {
            Connection connection = Util.getConnection();
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(removeRequest)) {
                    preparedStatement.setLong(1, id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении пользователя из БД");
        }
    }

    public List<User> getAllUsers() {
        String getAllRequest = "SELECT * FROM test.persons";
        List<User> userList = new ArrayList<>();
        try {
            Connection connection = Util.getConnection();
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(getAllRequest)) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String lastname = resultSet.getString("lastname");
                        Byte age = resultSet.getByte("age");
                        userList.add(new User(name, lastname, age));
                    }
                }
            }
            Util.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка всех пользователей.");
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanTableRequest = "DELETE FROM test.persons";
        try {
            Connection connection = Util.getConnection();
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(cleanTableRequest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении всех записей из БД");
        }
    }
}
