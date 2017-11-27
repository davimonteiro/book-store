package br.com.davimonteiro.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("br.com.davimonteiro")
public class DatabaseConfiguration {

    @Bean
    public JPAQueryFactory queryFactory(EntityManager entityManager) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory;
    }

}
