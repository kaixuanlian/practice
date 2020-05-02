package com.liankaixuan.sqlSession;



import com.liankaixuan.pojo.Configuration;
import com.liankaixuan.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

        //将要去完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }

    }

    public int update(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExecutor.update(configuration, mappedStatement, params);
    }

    public int insert(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExecutor.update(configuration, mappedStatement, params);
    }

    public int delete(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExecutor.update(configuration, mappedStatement, params);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理来为Dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都还是去执行JDBC代码 //根据不同情况，来调用selectList或者selectOne
                // 准备参数 1：statementId :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className+"."+methodName;
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                switch (mappedStatement.getSqlType()){
                    case SELECT:
                        return doSelect(method, args, statementId);
                    case INSERT:
                        return insert(statementId, args);
                    case UPDATE:
                        return update(statementId, args);
                    case DELETE:
                        return delete(statementId, args);
                }
                return 1;
            }
        });

        return (T) proxyInstance;
    }


    private Object doSelect(Method method, Object[] args, String statementId) throws Exception {
        // 获取被调用方法的返回值类型
        Type genericReturnType = method.getGenericReturnType();
        // 判断是否进行了 泛型类型参数化
        if(genericReturnType instanceof ParameterizedType){
            List<Object> objects = selectList(statementId, args);
            return objects;
        }
        return selectOne(statementId,args);
    }


}
