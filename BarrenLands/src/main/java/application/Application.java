package application;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import mapData.BoxParser;
import mapData.LandMap;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component 
    public class CommandLineAppStartupRunner implements CommandLineRunner {
        private final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
        @Override
        public void run(String...args) throws Exception {
        	BoxParser parser = new BoxParser(System.in);
        	LandMap map = new LandMap();
			parser.readMap(map );
			
        	
        }
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
