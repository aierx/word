package icu.aierx.word;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@MapperScan("icu.aierx.word.dao")
@EnableElasticsearchRepositories
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
