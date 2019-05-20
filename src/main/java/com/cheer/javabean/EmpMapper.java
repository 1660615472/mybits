package com.cheer.javabean;

import com.cheer.javabean.model.Emp;

/**定义sql语句的接口
 * */
public interface EmpMapper {
    public Emp getEmp(Integer empno);

}
