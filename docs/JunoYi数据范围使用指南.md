# JunoYi 数据范围使用指南

## 概述

数据范围（DataScope）是一种行级数据权限控制机制，基于 MyBatis 拦截器自动为 SQL 添加过滤条件。

## 数据范围类型

| 值 | 类型 | 说明 |
|---|------|------|
| 1 | ALL | 全部数据，不做过滤 |
| 2 | DEPT | 本部门数据 |
| 3 | DEPT_AND_CHILD | 本部门及下级部门数据 |
| 4 | SELF | 仅本人数据 |

优先级：ALL > DEPT_AND_CHILD > DEPT > SELF（多角色取最大权限）

## 架构说明

数据范围功能已完整实现，包含：

- **框架层**：`junoyi-framework-datasource` 模块提供 MyBatis 拦截器和 ThreadLocal 上下文
- **安全层**：`junoyi-framework-security` 模块在 Token 过滤器中自动设置/清理上下文
- **业务层**：`junoyi-module-system` 模块在登录时自动计算用户的数据范围

## 使用方式

### 1. 业务表设计

确保业务表包含以下字段（字段名可自定义）：

```sql
CREATE TABLE your_table (
    id BIGINT PRIMARY KEY,
    -- 业务字段
    dept_id BIGINT COMMENT '所属部门ID',
    create_by BIGINT COMMENT '创建人ID'
);
```

### 2. Mapper 方法添加注解

在需要数据范围过滤的 Mapper 方法上添加 `@DataScope` 注解：

```java
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    // 使用默认字段名（dept_id, create_by）
    @DataScope
    List<Order> selectOrderList(OrderQuery query);

    // 指定表别名（用于多表关联查询）
    @DataScope(tableAlias = "o")
    List<Order> selectOrderWithUser(OrderQuery query);

    // 自定义字段名
    @DataScope(deptField = "department_id", userField = "creator_id")
    List<Order> selectCustomOrder(OrderQuery query);
}
```

### 3. 角色配置数据范围

在角色管理中设置角色的 `dataScope` 字段：
- `1` - 全部数据
- `2` - 本部门数据
- `3` - 本部门及下级数据
- `4` - 仅本人数据

## 配置

```yaml
junoyi:
  datasource:
    data-scope:
      enabled: true  # 默认启用
```

## 测试方法

### 1. 准备测试数据

```sql
-- 创建测试部门（树形结构）
INSERT INTO sys_dept (id, parent_id, name) VALUES 
(1, 0, '总公司'),
(2, 1, '研发部'),
(3, 1, '市场部'),
(4, 2, '前端组'),
(5, 2, '后端组');

-- 创建测试角色（不同数据范围）
INSERT INTO sys_role (id, role_name, role_key, data_scope, status, del_flag) VALUES 
(1, '超级管理员', 'admin', '1', 0, 0),
(2, '部门经理', 'dept_manager', '3', 0, 0),  -- 本部门及下级
(3, '普通员工', 'employee', '4', 0, 0);       -- 仅本人

-- 创建测试用户并分配角色和部门
-- 用户A：研发部经理，数据范围=本部门及下级
-- 用户B：前端组员工，数据范围=仅本人
```

### 2. 测试步骤

1. **登录不同用户**，观察日志中的数据范围计算结果：
   ```
   [权限加载] 数据范围: 3 (本部门及下级数据)
   [权限加载] 可访问部门: [2, 4, 5]
   ```

2. **调用带 @DataScope 注解的接口**，观察 SQL 日志：
   - 超级管理员：无额外条件
   - 部门经理：`AND dept_id IN (2, 4, 5)`
   - 普通员工：`AND create_by = 123`

3. **验证返回数据**是否符合预期的数据范围

### 3. 调试技巧

开启 SQL 日志查看拦截器生成的条件：

```yaml
logging:
  level:
    com.junoyi.framework.datasource: DEBUG
```

## 注意事项

1. 超级管理员（userId=1 或拥有 `*` 权限）自动跳过数据范围过滤
2. 建议在 `dept_id`、`create_by` 字段上建立索引以提升查询性能
3. 多角色用户自动取权限最大的数据范围（并集）
4. 用户角色或部门变更后需要重新登录才能生效
5. 仅对添加了 `@DataScope` 注解的 Mapper 方法生效
