package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/myDbTest";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "12345678";
    private static Connection conn;

    private static StandardServiceRegistry registry;
    private static SessionFactory sf;

    public Util() {
    }

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            if (!conn.isClosed()) {
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static SessionFactory getSessionFactory() {
        if (sf == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                Map<String, String> settings = new HashMap<>();
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/myDbTest");
                settings.put("hibernate.connection.username", "root");
                settings.put("hibernate.connection.password", "12345678");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.hbm2ddl.auto", "update");
                registryBuilder.applySettings(settings);
                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(User.class);
                sf = sources.buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                System.out.println("SessionFactory creation failed");
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sf;
    }
}
