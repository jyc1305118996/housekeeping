package com.haige.service;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : Aaron
 *
 * create at:  2019-12-19  23:24
 *
 * description: 数据源测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {

  @Resource
  DataSource dataSource;

  @Test
  public void contextLoads() throws SQLException {

    System.out.println("数据源>>>>>>" + dataSource.getClass());
    Connection connection = dataSource.getConnection();

    System.out.println("连接>>>>>>>>>" + connection);
    System.out.println("连接地址>>>>>" + connection.getMetaData().getURL());

    DruidDataSource druidDataSource = (DruidDataSource) dataSource;
    System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
    System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
   // System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getSqlStat());

    connection.close();
  }

}

