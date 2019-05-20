package com.cheer;





import com.cheer.javabean.EmpMapper;
import com.cheer.javabean.model.Emp;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ZjTest {
    //使用logj
   // private static final Logger LOGGER = LogManager.getLogger(ZjTest.class);

    /**
     * 1.根据xml配置文件（全局配置文件创建一个sqlsessionfactory对象）,有数据源一些运行环境信息
     * 2.sql映射文件：配置了每个sql，以及sql的封装规则等
     * 3.将sql映射文件注册再全局变量中
     * 4.编写代码
     *          1）根据全局配置文件得到sqlsessionfactory
     *          2）根据全局配置文件得到SqlSession对象使用他来执行增删改
     *           一个SqlSession就是代表和数据库的一次对话，用完需要关闭
     *          3）使用sql的唯一标志来告诉mybatis执行哪个sql语句，sql语句都是保存再sql映射文件中的
     */
    @Test
    public void getEmp()throws Exception {

        //导入mybaits的配置文件
         InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            //创建一个sqlsessionFactory对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            //获取sqlsession实例，能直接执行已经映射的sql语句（核心操作，与数据库会话）
            SqlSession sqlSession = sqlSessionFactory.openSession();
            //获取EMP对象com.cheer.javabean.EmpMapper
            Emp emp = sqlSession.selectOne("com.cheer.javabean.getEmp", 7369);
           // EmpMapper empMapper =sqlSession.getMapper(EmpMapper.class);
           System.out.println(emp);

            try {
                    if(inputStream!=null) {
                        inputStream.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }




     //返回SqlSessionFactory对象的方法
        public SqlSessionFactory getSqlSessionFactory()throws IOException{
        String resource="mybatis-config.xml";
        InputStream inputStream= Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }
 

    //1.接口式编程
    //原来的做法  Dao ===》DaoImpl  写操作sql接口方法，再写 实现类实现
    //mybatis：  Mapper===》xxMaper.xml

    //2.SqlSession代表和数据库的一次对话，用完也必须关闭
    //3.SqlSession和connection一样都是非线程安全的，每次使用都必须获取新的对象
    //4.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象
    //Emp emp=sqlSession.getMapper(EmpMapper.class); 将接口和XML文件进行绑定，mybatis就会生成一个代理对象
    //5.两个重要的配置文件：
    //      mybaits的全局配置文件，包含数据库连接池信息，事务管理信息等。。。系统运行环境信息
    //      sql映射文件：保存了每一个sql语句的映射信息，将sql抽取出来

    @Test
        public void test02()throws Exception{
        //1.获取sqlSessonFactory对象
       SqlSessionFactory sqlSessionFactory= getSqlSessionFactory();
       //2.使用工厂获取sqlsession对象
       SqlSession openSession= sqlSessionFactory.openSession();
       //3.获取接口的实现类对象
        //mybatis会为接口创建一个代理对象，代理对象会去执行增删改查操作
        EmpMapper mapper = openSession.getMapper(EmpMapper.class);
        Emp emp=mapper.getEmp(8888);
        System.out.println(emp);
        //关闭
        if(openSession!=null){
            openSession.close();
        }

    }
}