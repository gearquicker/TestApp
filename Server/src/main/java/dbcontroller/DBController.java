package dbcontroller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DBController {

    @SuppressWarnings("unchecked")
    public static <T> List<T> getObjects(Class<?> c) {
        List<T> objects;
        SessionFactory sessionFactory = SessionSingleton.getInstance().getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        objects = session.createQuery("from " + c.getSimpleName()).list();

        tx.commit();
        session.close();
        return objects;
    }

    public static void createOrUpdateObject(Object object) {
        SessionFactory sessionFactory = SessionSingleton.getInstance().getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(object);

        tx.commit();
        session.close();
    }

    public static void deleteObject(Object object) {
        SessionFactory sessionFactory = SessionSingleton.getInstance().getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.delete(object);

        tx.commit();
        session.close();
    }

}
