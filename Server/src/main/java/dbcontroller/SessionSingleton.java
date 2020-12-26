package dbcontroller;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionSingleton {

	private static volatile SessionSingleton instance;
	private final SessionFactory sessionFactory;

	private SessionSingleton() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public static SessionSingleton getInstance() {
		if (instance == null) {
			synchronized (SessionSingleton.class) {
				if (instance == null) {
					instance = new SessionSingleton();
				}
			}
		}
		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}