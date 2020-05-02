package com.liankaixuan.sqlSession;


import com.liankaixuan.pojo.Configuration;
import com.liankaixuan.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}
