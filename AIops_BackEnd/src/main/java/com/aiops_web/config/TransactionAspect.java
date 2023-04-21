package com.aiops_web.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Aspect
@Configuration
@EnableTransactionManagement
public class TransactionAspect {

    ThreadLocal<TransactionStatus> transactionStatusThreadLocal = new ThreadLocal<>();

    /**
     * 定义mysql事务管理器，必须有transactionManager作为默认事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean("transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    // neo4j事务
//    @Bean(ReactiveNeo4jRepositoryConfigurationExtension.DEFAULT_TRANSACTION_MANAGER_BEAN_NAME)
//    public ReactiveTransactionManager reactiveTransactionManager(
//            Driver driver,
//            ReactiveDatabaseSelectionProvider databaseNameProvider) {
//        return new ReactiveNeo4jTransactionManager(driver, databaseNameProvider);
//    }

}
