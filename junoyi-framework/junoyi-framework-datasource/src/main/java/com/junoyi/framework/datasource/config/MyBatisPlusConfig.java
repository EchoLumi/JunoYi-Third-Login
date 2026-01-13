package com.junoyi.framework.datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.junoyi.framework.datasource.datascope.interceptor.DataScopeInterceptor;
import com.junoyi.framework.datasource.interceptor.SqlBeautifyInterceptor;
import com.junoyi.framework.datasource.interceptor.SlowSqlInterceptor;
import com.junoyi.framework.datasource.properties.DataSourceProperties;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis-Plus 配置类
 *
 * 功能：
 * - 分页插件
 * - 乐观锁插件
 * - 防止全表更新删除插件
 * - SQL 美化输出
 * - 慢 SQL 监控
 *
 * @author Fan
 */
@AutoConfiguration
@EnableTransactionManagement
@EnableConfigurationProperties(DataSourceProperties.class)
@MapperScan("com.junoyi.**.mapper")
public class MyBatisPlusConfig {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(MyBatisPlusConfig.class);

    /**
     * 创建并配置 MyBatis-Plus 拦截器实例。
     * <p>
     * 包含以下功能插件：
     * <ul>
     *   <li>分页插件：支持数据库分页查询</li>
     *   <li>乐观锁插件：用于处理并发更新场景下的版本控制</li>
     *   <li>防全表更新/删除插件：防止误操作导致的数据批量变更</li>
     * </ul>
     *
     * @return 初始化完成的 MybatisPlusInterceptor 实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("Start initializing MyBatis-Plus interceptor.");

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 添加分页插件，并设置相关参数
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(1000L); // 单页最大数量限制
        paginationInnerInterceptor.setOverflow(false); // 溢出总页数后是否进行处理
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 添加防止全表更新删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        log.info("Initialization MyBatis-Plus interceptor completed.");
        return interceptor;
    }

    /**
     * 创建 SQL 美化输出拦截器 Bean。
     * <p>
     * 该拦截器用于美化打印的 SQL 语句，便于开发调试时查看执行的 SQL 内容。
     *
     * @param properties 数据源配置属性
     * @return SqlBeautifyInterceptor 实例
     */
    @Bean
    public SqlBeautifyInterceptor sqlBeautifyInterceptor(DataSourceProperties properties) {
        return new SqlBeautifyInterceptor(properties);
    }

    /**
     * 创建慢 SQL 监控拦截器 Bean。
     * <p>
     * 用于监控执行时间较长的 SQL 语句，帮助识别性能瓶颈。
     *
     * @param properties 数据源配置属性
     * @return SlowSqlInterceptor 实例
     */
    @Bean
    public SlowSqlInterceptor slowSqlInterceptor(DataSourceProperties properties) {
        return new SlowSqlInterceptor(properties);
    }

    /**
     * 创建数据范围拦截器 Bean。
     * <p>
     * 用于自动添加数据范围过滤条件，实现行级数据权限控制。
     * 通过配置 junoyi.datasource.data-scope.enabled=false 可禁用。
     *
     * @return DataScopeInterceptor 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "junoyi.datasource.data-scope", name = "enabled", havingValue = "true", matchIfMissing = true)
    public DataScopeInterceptor dataScopeInterceptor() {
        log.info("Initializing DataScope interceptor.");
        return new DataScopeInterceptor();
    }
}
