package com.liankaixuan.config;

import com.liankaixuan.common.SqlTypeEnum;
import com.liankaixuan.pojo.Configuration;
import com.liankaixuan.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration =configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        List<Element> selectElementList = rootElement.selectNodes("//select");
        List<Element> insertElementList = rootElement.selectNodes("//insert");
        List<Element> updateElementList = rootElement.selectNodes("//update");
        List<Element> deleteElementList = rootElement.selectNodes("//delete");
        for (Element element : selectElementList) {
            MappedStatement mappedStatement = buildMapperStatement(element);
            mappedStatement.setSqlType(SqlTypeEnum.SELECT);
            String key = namespace + "." + mappedStatement.getId();
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

        for (Element element : insertElementList) {
            MappedStatement mappedStatement = buildMapperStatement(element);
            mappedStatement.setSqlType(SqlTypeEnum.INSERT);
            String key = namespace + "." + mappedStatement.getId();
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
        for (Element element : updateElementList) {
            MappedStatement mappedStatement = buildMapperStatement(element);
            mappedStatement.setSqlType(SqlTypeEnum.UPDATE);
            String key = namespace + "." + mappedStatement.getId();
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
        for (Element element : deleteElementList) {
            MappedStatement mappedStatement = buildMapperStatement(element);
            mappedStatement.setSqlType(SqlTypeEnum.DELETE);
            String key = namespace + "." + mappedStatement.getId();
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
    }

    MappedStatement buildMapperStatement(Element element){
        String id = element.attributeValue("id");
        String resultType = element.attributeValue("resultType");
        String parameterType = element.attributeValue("paramterType");
        String sqlText = element.getTextTrim();
        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.setId(id);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParameterType(parameterType);
        mappedStatement.setSql(sqlText);
        return mappedStatement;
    }

}
