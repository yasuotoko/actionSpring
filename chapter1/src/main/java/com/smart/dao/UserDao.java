package com.smart.dao;

import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getMatchCount(String userName,String password){
        String strSql = "select Count(*) from t_user"+
                "where username=? and password=?";
        return jdbcTemplate.queryForInt(strSql,new Object[]{userName,password});
    }

    public User findUserBuUserName(final String userName) {
        String sqlStr = "SELECT user_id,user_nane " + "From t_user where user_name =?";
        final User user = new User();
        jdbcTemplate.query(sqlStr, new Object[]{userName}, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(userName);
            }
        });
        return user;
    }

    public void updateLoginInfo(User user) {
        String sqlStr = "update t_user set last_visit=?,last_ip=? where user_id=?";
        jdbcTemplate.update(sqlStr,new Object[] {user.getLastVisit(),user.getLastIp(),user.getUserId()});
    }
}
