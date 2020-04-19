package com.mybatis.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcHelloWord {

    /**
     * 入口函数
     * @param arg
     */
    public static void main(String arg[]) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://120.79.4.13:3306/zsj?useSSL=false&useUnicode=true", "root", "admin123"); //链接本地MYSQL

            //更新一条数据
            String updateSql = "UPDATE user SET userName = 'mybatis' WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, "1");
            long updateRes = pstmt.executeUpdate();
            System.out.print("UPDATE:" + updateRes);

            //查询数据并输出
            String sql = "select id,userName from user where id = ?";
            PreparedStatement pstmt2 = con.prepareStatement(sql);
            pstmt2.setString(1, "1");
            ResultSet rs = pstmt2.executeQuery();
            while (rs.next()) { //循环输出结果集
                String id = rs.getString("id");
                String userName = rs.getString("userName");
                System.out.print("\r\n\r\n");
                System.out.print("Id:" + id + "userName:" + userName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
