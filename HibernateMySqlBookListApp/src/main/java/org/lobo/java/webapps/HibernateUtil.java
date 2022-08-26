package org.lobo.java.webapps;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            String dbServer = System.getenv("MYSQL_SERVER");
            if (dbServer == null) {
                dbServer = "localhost";
            }
            settings.put(Environment.URL, "jdbc:mysql://" + dbServer + "/test");
            settings.put(Environment.USER, "smlobo");
            settings.put(Environment.PASS, "welcome");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

            //settings.put(Environment.SHOW_SQL, "true");

            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(Book.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Exception ex) {
            System.err.println("Session Factory could not be created." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
