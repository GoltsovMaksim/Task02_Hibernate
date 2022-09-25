package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sf = Util.getSessionFactory();
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age SMALLINT NOT NULL)").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Table has not been created!\n");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS myDbTest.users").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Table has not been deleted!\n");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.printf("User with name - %s, has been saved!", user.getName());
        } catch (Exception e) {
            System.out.println("Unable to save person to the table Users");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            User userTemp = session.get(User.class, id);
            session.delete(userTemp);
            session.getTransaction().commit();
            System.out.printf("User with id - %d, has been deleted", id);
        } catch (Exception e) {
            System.out.printf("Unable to delete user with ID - %d", id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) sf.openSession().createQuery("From User").list();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sf.openSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery("Truncate table users");
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
