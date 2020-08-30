package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String tableCreationRequest = "CREATE TABLE IF NOT EXISTS persons (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(255), " +
                "lastName varchar(255), " +
                "age int)";
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        SQLQuery sqlQuery1 = session.createSQLQuery(tableCreationRequest);
        sqlQuery1.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String dbDropRequest = "DROP TABLE IF EXISTS persons";
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery(dbDropRequest);
        sqlQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        //Util.shutdown();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Query query = session.createQuery("delete from User where id = (:id)");
        session.beginTransaction();
        query.setLong("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Query query = session.createQuery("from User");
        List<User> users;
        users = query.list();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Query query = session.createQuery("delete from User");
        session.beginTransaction();
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
