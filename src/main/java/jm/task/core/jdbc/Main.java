package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Nik", "Reed", (byte) 20);
        userService.saveUser("Mike", "Boom", (byte) 25);
        userService.saveUser("Black", "Rock", (byte) 31);
        userService.saveUser("Kid", "Maple", (byte) 38);

        userService.removeUserById(2);
        userService.getAllUsers().get(0);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
