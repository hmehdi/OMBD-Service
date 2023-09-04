package com.sky.ombdservice.bootstrap;

import com.sky.ombdservice.service.MovieDataLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/*
 DevBootstrap initializing or seeding data into your database

 */
@Component //spring bean
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final MovieDataLoader movieDataLoader;

    public DevBootstrap( MovieDataLoader movieDataLoader) {
        this.movieDataLoader = movieDataLoader;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {

       // movieDataLoader.loadCsvData();
    }
}
