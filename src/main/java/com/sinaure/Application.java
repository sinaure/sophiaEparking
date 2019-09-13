package com.sinaure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.sinaure.repository")
@ComponentScan("com.sinaure.service")
public class Application  {



	/*@Autowired
    private GlobalProperties globalProperties;
*/
    public static void main(String[] args) {
    	//System.out.println(globalProperties);
        SpringApplication.run(Application.class, args);
    }

    /*@Override
    public void run(String... args) {
        System.out.println(globalProperties);
    }*/
}
