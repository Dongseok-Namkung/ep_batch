package com.nkds.batch.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 *============================================================================
 * @PROEJCT  : 스프링 배치
 * @NAME     : DatabaseConfig
 * @DESC     : DB 접속 설정파일 (DataSource 설정 정보는 application.properties에 있음: Hikari CP 사용)
 * @DESC     : 두개 이상의 데이터 소스가 필요할 경우
 * @author   : NKDS
 *============================================================================
 *    DATE     		AUTHOR              DESCRIPTION
 *============================================================================
 * 2018. 3. 8.   	NKDS          	최초작성
 *
 *============================================================================
 */

@Configuration
@Lazy
@EnableTransactionManagement
public class DatabaseConfig {

	@Autowired
    private ApplicationContext applicationContext;
	
    @Bean(destroyMethod="close")
    @Primary
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(destroyMethod="close")
    @ConfigurationProperties(prefix = "datasource.second")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(primaryDataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        
        return transactionManager;
    }
    
    @Bean
    @Primary
    public SqlSessionFactory primarySqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(primaryDataSource());
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        
        return sessionFactoryBean.getObject();
    }
    
    @Bean
    public SqlSessionFactory secondSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(secondDataSource());
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        
        return sessionFactoryBean.getObject();
    }
}
