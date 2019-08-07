package com.haige.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.haige.db.*"}, sqlSessionFactoryRef = "haigeSqlSessionFactory")
public class HaigeDataSourceConfig {

    @Bean(name = "haigeDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.haige")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean(name = "haigeTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(druidDataSource());
        return dataSourceTransactionManager;
    }

    @Bean(name = "haigeTransactionTemplate")
    @Primary
    public TransactionTemplate transactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager());
        return transactionTemplate;
    }

    @Bean(name = "haigeSqlSessionFactory")
    @Primary
    public SqlSessionFactory haigeSqlSessionFactory(@Qualifier("haigeDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:sql/haige/*.xml"));
        return sessionFactory.getObject();
    }

}

