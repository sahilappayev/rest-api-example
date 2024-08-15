package com.example.rest.scheduler;

import com.example.rest.config.DbConfig;
import com.example.rest.entity.Category;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.hibernate.Session;

public class JavaJobScheduler {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Session session = DbConfig.getSession();
                session.beginTransaction();
                Query query = session.createQuery("from Category", Category.class);

                List<?> resultList = query.getResultList();

                System.out.println(Thread.currentThread().getName()  +  "  " + new Date() + " : " + resultList);
                session.getTransaction().commit();
                session.close();
            }
        };

        System.out.println(Thread.currentThread().getName()  +  "  started: " + new Date());
        timer.scheduleAtFixedRate(task, 1000, 5000);
        System.out.println(Thread.currentThread().getName()  +  "  Ended: " + new Date());
    }
}
