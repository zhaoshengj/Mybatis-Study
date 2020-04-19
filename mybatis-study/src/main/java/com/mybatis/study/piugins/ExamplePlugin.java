package com.mybatis.study.piugins;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;


/**
 * MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：
 *
 *          Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 *          ParameterHandler (getParameterObject, setParameters)
 *          ResultSetHandler (handleResultSets, handleOutputParameters)
 *          StatementHandler (prepare, parameterize, batch, update, query)
 */
@Intercepts({@Signature(
        type= StatementHandler.class,
        method = "query",
        args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {

    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("--------------------------------------");
        // implement pre processing if need
        Object returnObject = invocation.proceed();
        // implement post processing if need
        return returnObject;
    }

    @Override
    public Object plugin(Object target) {

        System.out.println("************************************");
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("++++++++++++++++++++++++++++++++++");

        this.properties = properties;
    }
}
