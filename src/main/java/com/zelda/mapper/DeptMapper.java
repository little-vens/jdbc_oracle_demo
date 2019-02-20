package com.zelda.mapper;

import com.zelda.domain.Dept;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author Link
 * @Company http://www.zelda.com
 */
public interface DeptMapper {

    @Select("select * from dept where deptno = #{dno}")
    @Results(id = "deptMap",value = {
            @Result(id = true,column = "deptno",property = "dno"),
            @Result(column = "dname",property = "dname"),
            @Result(column = "loc",property = "loc"),
    })
    Dept findByDeptNo(Integer dno);
}
