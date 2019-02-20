package com.zelda.test;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zelda.mapper.DeptMapper;
import com.zelda.mapper.EmpMapper;
import oracle.jdbc.driver.OracleTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Link
 * @Company http://www.zelda.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class EmpMapperTest {

    @Autowired
    private EmpMapper empMapper;

    @Test
    public void testMapper(){
        empMapper.findAll().forEach(System.out::println);
//        System.out.println(dm.findByDeptNo(10));
    }

    @Test
    public void testProcedure(){
        Map<String, Object> map = new HashMap<>();
        map.put("eno",7788d);
        empMapper.findYSalByEmp(map);
        System.out.println(map.get("esal"));
//        System.out.println(dm.findByDeptNo(10));
    }

    @Test
    public void testFunc(){
        Map<String, Object> map = new HashMap<>();
        map.put("eno",7788d);
        empMapper.findYSalByFunc(map);
        System.out.println(map.get("esal"));
//        System.out.println(dm.findByDeptNo(10));
    }

    @Test
    public void testOrigin() throws Exception {
        Properties prop = new Properties();
        prop.load(EmpMapperTest.class.getClassLoader().getResourceAsStream("druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        Connection connection = dataSource.getConnection();
        CallableStatement cs = connection.prepareCall("{call PROC_COUNTYSAL(?,?)}");
        cs.setInt(1,7788);
        cs.registerOutParameter(2, OracleTypes.NUMBER);
        cs.execute();
        System.out.println(cs.getObject(2));
        cs.close();
        connection.close();
    }

    @Test
    public void testFun() throws Exception {
        Properties prop = new Properties();
        prop.load(EmpMapperTest.class.getClassLoader().getResourceAsStream("druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        Connection connection = dataSource.getConnection();
        CallableStatement cs = connection.prepareCall("{?=call emp_slary(?)}");
        cs.setInt(2,7788);
        cs.registerOutParameter(1, OracleTypes.NUMBER);
        cs.execute();
        System.out.println(cs.getObject(1));
        cs.close();
        connection.close();
    }

}
