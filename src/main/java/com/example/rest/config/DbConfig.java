package com.example.rest.config;

import com.example.rest.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class DbConfig {

    // ORM -> JPA
    // EntityManagerFactory -> SessionFactory, EntityManager -> Session, Transaction, Configuration

    private static SessionFactory sessionFactory = null;


    public static Session getSession() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres");
            properties.put(Environment.JAKARTA_JDBC_USER, "postgres");
            properties.put(Environment.JAKARTA_JDBC_PASSWORD, "postgres");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
            properties.put(Environment.HBM2DDL_AUTO, "update");
//            properties.put(Environment.POOL_SIZE)

            configuration.addProperties(properties);

            configuration.addAnnotatedClass(Category.class);

            sessionFactory = configuration.buildSessionFactory();
        }

        return sessionFactory.getCurrentSession();
    }


}
