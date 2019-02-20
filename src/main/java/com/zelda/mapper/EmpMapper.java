package com.zelda.mapper;

import com.zelda.domain.Emp;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Link
 * @Company http://www.zelda.com
 */

public interface EmpMapper {

    @Select("select * from emp")
    @Results(id = "empMap", value = {
            @Result(id = true,column = "empno", property = "eid"),
            @Result(column = "ename", property = "ena"),
            @Result(column = "hiredate", property = "hireDate"),
            @Result(column = "deptno", property = "dept",
                    one = @One(select = "com.zelda.mapper.DeptMapper.findByDeptNo",fetchType = FetchType.LAZY))
    })
    List<Emp> findAll();

    /**
     * 调用存储过程
     * @param map
     */
    @Select("{call PROC_COUNTYSAL(#{eno,mode=IN,jdbcType=DOUBLE}," +
            "#{esal,mode=OUT,jdbcType=DOUBLE})}")
    @Options(statementType = StatementType.CALLABLE)
    void findYSalByEmp(Map<String, Object> map);


    /**
     * 调用存储函数
     * @param map
     */
    @Select("{#{esal,mode=INOUT,jdbcType=DOUBLE}=call emp_slary(#{eno,mode=IN,jdbcType=DOUBLE})}")
    @Options(statementType = StatementType.CALLABLE)
    void findYSalByFunc(Map<String, Object> map);
}
