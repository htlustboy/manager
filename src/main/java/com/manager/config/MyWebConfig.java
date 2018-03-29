package com.manager.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSourceFactory;

@Configuration
@MapperScan("com.manager.mapper")
public class MyWebConfig {
	
	private static Logger logger = LoggerFactory.getLogger(MyWebConfig.class);
	
	@Resource
	private Environment environment;
	
	/**
	 * 创建数据源
	 * @return
	 */
	@Bean
	public DataSource getDataSource(){
		Properties properties = new Properties();
		properties.put("driverClassName", environment.getProperty("jdbc.driverClassName"));
		properties.put("url", environment.getProperty("jdbc.url"));
		properties.put("username", environment.getProperty("jdbc.username"));
		properties.put("passowrd", environment.getProperty("jdbc.password"));
		DataSource ds = null;
		try {
			ds = DruidDataSourceFactory.createDataSource(properties);
			logger.info("加载数据源>>>>>>>>>>>>>>>>>>>>>>>>");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ds;
	}
	
	/**
	 * 创建SqlSessionFactory
	 * @param dataSource
	 * @return
	 */
	@Bean
	public SqlSessionFactory getSqlSessionFactory(DataSource dataSource){
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		//加载全局配置文件
		sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(environment.getProperty("mybatis.config")));
		//下边的配置仅仅用于*.xml文件，如果不需要用到xml文件，则不需要配置
		sqlSessionFactoryBean.setTypeAliasesPackage(environment.getProperty("mybatis.typeAliasesPackage"));
		try {
			//设置 *Mapper 文件路径
			sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(environment.getProperty("mybatis.mapperLocations")));
			logger.info("sqlSessionFactory创建完毕>>>>>>>>>>>>>>>>>>>");
			return sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
	
}
