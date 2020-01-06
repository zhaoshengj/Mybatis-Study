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
       /* UserDao mapper = session.getMapper(UserDao.class);
        User user = mapper.findUserById(2);
        session.commit();*/
        User user = session.selectOne(statement,2);

        System.out.println(user);

    }

}
