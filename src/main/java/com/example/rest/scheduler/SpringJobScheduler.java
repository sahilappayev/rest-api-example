package com.example.rest.scheduler;

import com.example.rest.entity.Category;
import com.example.rest.repository.CategoryRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpringJobScheduler {

    private final CategoryRepository categoryRepository;

    // fixed delay end time
    // fixed rate start time
//    @Scheduled(initialDelay = 1000, fixedRate = 5000)
//    @Scheduled(cron = "30 * * * * TUE,MON", zone = "Asia/Bangkok") // 1 - san, 2 -deq, 3 - saat, 4 - ayin gunu, 5 - ay, 6 - heftenin gunu
//    @Scheduled(fixedRateString = "PT2S")
//    @Async
    public void printCategories(){
        System.err.println(Thread.currentThread().getName()  +  "  started: " + new Date());
        List<Category> categories = categoryRepository.findAll();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(categories);
        System.err.println(Thread.currentThread().getName()  +  "  Ended: " + new Date());
    }



}
