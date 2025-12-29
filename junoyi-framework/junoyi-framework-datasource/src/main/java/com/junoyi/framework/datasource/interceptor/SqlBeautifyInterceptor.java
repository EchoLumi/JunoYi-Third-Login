package com.junoyi.framework.datasource.interceptor;

import com.junoyi.framework.datasource.properties.DataSourceProperties;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * SQL 美化输出拦截器
 * 将 SQL 语句格式化后输出到日志，方便调试
 *
 * @author Fan
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class SqlBeautifyInterceptor implements Interceptor {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(SqlBeautifyInterceptor.class);
    private final DataSourceProperties properties;

    public SqlBeautifyInterceptor(DataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 检查是否启用 SQL 日志
        if (!properties.isSqlLogEnabled() && !properties.isSqlBeautifyEnabled()) {
            return invocation.proceed();
        }

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();

        if (properties.isSqlBeautifyEnabled()) {
            String beautifiedSql = beautifySql(sql);
            log.info("[SQL] \n{}", beautifiedSql);
        } else if (properties.isSqlLogEnabled()) {
            log.info("[SQL] {}", sql.replaceAll("\\s+", " ").trim());
        }

        Object params = boundSql.getParameterObject();
        if (params != null) {
            log.info("[Params] {}", params);
        }

        return invocation.proceed();
    }

    /**
     * 对原始 SQL 语句进行美化处理
     */
    private String beautifySql(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return sql;
        }
        sql = sql.replaceAll("\\s+", " ").trim();
        sql = sql.replaceAll("(?i)\\b(SELECT|FROM|WHERE|AND|OR|ORDER BY|GROUP BY|HAVING|LIMIT|OFFSET|JOIN|LEFT JOIN|RIGHT JOIN|INNER JOIN|ON|INSERT INTO|VALUES|UPDATE|SET|DELETE)\\b", "\n  $1");
        if (sql.startsWith("\n")) {
            sql = sql.substring(1);
        }
        return sql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
