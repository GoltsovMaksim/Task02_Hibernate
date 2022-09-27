package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection conn;

    public UserDaoJDBCImpl() {
        conn = Util.getConnection();
    }

    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS myDbTest.Users (ID int not null auto_increment key, name varchar(45), lastName varchar(45), age int)";
        try (Statement stat = conn.createStatement()) {
            stat.execute(sqlQuery);
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS myDbTest.Users";
        try (Statement stat = conn.createStatement()) {
            stat.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "insert into myDbTest.Users (name, lastName, age) values (?,?,?)";
        try (PreparedStatement pStat = conn.prepareStatement(sqlQuery)) {
            pStat.setString(1, name);
            pStat.setString(2, lastName);
            pStat.setString(3, String.valueOf(age));
            pStat.executeUpdate();
            System.out.println("User с именем " + name + " внесен в таблицу");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "DELETE FROM myDbTest.Users WHERE ID = ?";
        try (PreparedStatement pStat = conn.prepareStatement(sqlQuery)) {
            pStat.setString(1, String.valueOf(id));
            pStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> al = new ArrayList<>();
        String sqlQuery = "SELECT * FROM myDbTest.Users";
        try (Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(sqlQuery);
            while (rs.next()) {
                User user = new User();
                user.setId(Long.valueOf(rs.getString("ID")));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(Byte.valueOf(rs.getString("age")));
                al.add(user);
            }
            return al;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String sqlQuery = "DELETE FROM myDbTest.Users";
        try (Statement stat = conn.createStatement()) {
            stat.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
