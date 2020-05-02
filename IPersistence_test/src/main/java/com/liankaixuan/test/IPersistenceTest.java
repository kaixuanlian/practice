package com.liankaixuan.test;

import com.liankaixuan.dao.IUserDao;
import com.liankaixuan.io.Resources;
import com.liankaixuan.pojo.User;
import com.liankaixuan.sqlSession.SqlSession;
import com.liankaixuan.sqlSession.SqlSessionFactory;
import com.liankaixuan.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(3);
        user.setUsername("te mei pu");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//        userDao.insert(user);
        userDao.delete(2);
        for (User user1 : userDao.findAll()) {
            System.out.println(user1);
        }

    }



}
