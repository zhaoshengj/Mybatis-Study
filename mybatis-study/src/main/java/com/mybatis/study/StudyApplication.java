package com.mybatis.study;

import com.mybatis.study.dao.UserDao;
import com.mybatis.study.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class StudyApplication {

    //一般来说，对于简单语句来说，使用注解代码会更加清晰，
    // 然而Java注解对于复杂语句比如同时包含了构造器、鉴别器、resultMap来说就会非常混乱，
    // 应该限制使用，此时应该使用XML文件，因为注解至少至今为止不像XML/Gradle一样能够很好的表示嵌套关系
    public static void main(String[] args) {

        //SpringApplication.run(StudyApplication.class, args);

        //mybatis的配置文件
        String resource = "mybatis-config.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sessionFactory.openSession();
        String statement = "com.mybatis.study.dao.UserDao.findUserById";


        //mybatis在执行期间，主要有四大核心接口对象：
        //执行器Executor，执行器负责整个SQL执行过程的总体控制。
        //参数处理器ParameterHandler，参数处理器负责PreparedStatement入参的具体设置。
        //语句处理器StatementHandler，语句处理器负责和JDBC层具体交互，包括prepare语句，执行语句，以及调用ParameterHandler.parameterize()设置参数。
        //结果集处理器ResultSetHandler，结果处理器负责将JDBC查询结果映射到java对象。
        UserDao mapper = session.getMapper(UserDao.class);
        User user = mapper.findUserById(2);
        session.commit();


        //mybatis提供了基本实现org.apache.ibatis.cache.impl.PerpetualCache，内部采用原始HashMap实现。
        // 第二个需要知道的方面是mybatis有一级缓存和二级缓存。
        // 一级缓存是SqlSession级别的缓存，不同SqlSession之间的缓存数据区域（HashMap）是互相不影响，
        // MyBatis默认支持一级缓存，不需要任何的配置，
        // 默认情况下(一级缓存的有效范围可通过参数localCacheScope参数修改，取值为SESSION或者STATEMENT)，
        // 在一个SqlSession的查询期间，只要没有发生commit/rollback或者调用close()方法，
        // 那么mybatis就会先根据当前执行语句的CacheKey到一级缓存中查找，如果找到了就直接返回，
        // 不到数据库中执行。其实现在代码BaseExecutor.query()中


        //二级缓存是mapper级别的缓存，多个SqlSession去操作同一个mapper的sql语句，
        // 多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession。
        // 二级缓存默认不启用，需要通过在Mapper中明确设置cache，它的实现在CachingExecutor的query()方法中

        //在缓存的设计上，Mybatis的所有Cache算法都是基于装饰器/Composite模式对PerpetualCache扩展增加功能。
        user = session.selectOne(statement,2);

        System.out.println(user);

    }

}
