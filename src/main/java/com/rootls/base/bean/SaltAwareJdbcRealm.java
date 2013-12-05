package com.rootls.base.bean;

import com.rootls.base.model.Permission;
import com.rootls.base.model.Role;
import com.rootls.base.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * shiro认证与授权领域类
 * User: luowei
 * Date: 13-11-27
 * Time: 下午1:44
 * To change this template use File | Settings | File Templates.
 */
public class SaltAwareJdbcRealm extends AuthorizingRealm {

    protected static final String DEFAULT_AUTHENTICATION_QUERY = "select id,username,password from user where username = ?";
    protected static final String DEFAULT_USER_ROLES_QUERY = "select r.id, r.role_name from role r " +
            "left join user_role ur on r.id=ur.role_id where ur.user_id=?";
    protected static final String DEFAULT_PERMISSIONS_QUERY = "select p.id,p.permission,p.permission_group_id from permission p " +
            "left join roles_permission rp on p.id=rp.permission_id where rp.role_id = ?";

    protected String authenticationQuery = DEFAULT_AUTHENTICATION_QUERY;

    protected String userRolesQuery = DEFAULT_USER_ROLES_QUERY;

    protected String permissionsQuery = DEFAULT_PERMISSIONS_QUERY;

    protected boolean permissionsLookupEnabled = true;


    protected JdbcTemplate jdbcTemplate;
    //JdbcTemplate jdbcTemplate = new JdbcTemplate(BasicDataSource.class.cast(getCurrentWebApplicationContext().getBean("dataSource")));

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setAuthenticationQuery(String authenticationQuery) {
        this.authenticationQuery = authenticationQuery;
    }

    public void setUserRolesQuery(String userRolesQuery) {
        this.userRolesQuery = userRolesQuery;
    }

    public void setPermissionsQuery(String permissionsQuery) {
        this.permissionsQuery = permissionsQuery;
    }

    public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
        this.permissionsLookupEnabled = permissionsLookupEnabled;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = UsernamePasswordToken.class.cast(token);
        final String username = usernamePasswordToken.getUsername();

        User user = jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(authenticationQuery);
                        ps.setString(1, username);
                        return ps;
                    }
                },
                new ResultSetExtractor<User>() {
                    @Override
                    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                        User user = null;
                        if (rs.next()) {
                            user = new User(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("password")
                            );
                            // 检查根据用户名是否只找到了唯一一条记录
                            if (rs.next()) {
                                throw new AuthenticationException("More than one user row found for user [" + username + "]. Usernames must be unique.");
                            }
                        }
                        return user;
                    }
                }
        );
        AuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        final Integer userId = Integer.class.cast(getAvailablePrincipal(principals));

        //查找角色
        final Set<String> roleNames = new HashSet<String>();
        final Set<Role> roleSet = new HashSet<Role>();

        jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(userRolesQuery);
                        ps.setInt(1, userId.intValue());
                        return ps;
                    }
                },
                new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String name = rs.getString("name");
                        Role role = new Role(rs.getInt("id"), name);
                        roleNames.add(name);
                        roleSet.add(role);
                        return role;
                    }
                }
        );


        AuthorizationInfo authorizationInfo = null;

        //查找权限
        if (permissionsLookupEnabled && !roleSet.isEmpty()) {
            final Set<String> permissionNames = new HashSet<String>();
//            final Set<Permission> permissionSet = new HashSet<Permission>();

            for (Role role : roleSet) {
                final Integer roleId = role.getId();

                jdbcTemplate.query(
                        new PreparedStatementCreator() {
                            @Override
                            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                                PreparedStatement ps = con.prepareStatement(permissionsQuery);
                                ps.setInt(1, roleId.intValue());
                                return ps;
                            }
                        },
                        new RowMapper<Permission>() {
                            @Override
                            public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
                                String name = rs.getString("permission");
                                Permission permission = new Permission(
                                        rs.getInt("id"),
                                        name,
                                        rs.getInt("permission_group_id")
                                );
                                permissionNames.add(name);
//                                permissionSet.add(permission);
                                return permission;
                            }
                        }
                );
            }
            authorizationInfo = new SimpleAuthorizationInfo(permissionNames);
        }
        return authorizationInfo;
    }
}
