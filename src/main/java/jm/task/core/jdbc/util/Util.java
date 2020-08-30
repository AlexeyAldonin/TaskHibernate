package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/MySQL?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";


    public static Connection getConnection () throws SQLException {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                Map<String, Object> settings = new HashMap<>();
                settings.put("hibernate.connection.driver_class", DRIVER_CLASS);
                settings.put("hibernate.connection.url", URL);
                settings.put("hibernate.connection.username", USERNAME);
                settings.put("hibernate.connection.password", PASSWORD);
                settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                settings.put("hbm2ddl.auto", "update");

                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().
                        applySettings(settings).build();

                sessionFactory = configuration.buildSessionFactory(standardRegistry);
            }
            return sessionFactory;
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
