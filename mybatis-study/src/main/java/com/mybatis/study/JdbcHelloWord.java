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
            con = DriverManager.getConnection("jdbc:mysql://120.79.4.13:3306/wms_test?useSSL=false&useUnicode=true", "root", "admin123"); //链接本地MYSQL

            //更新一条数据
            String updateSql = "UPDATE LfParty SET remark1 = 'mybatis internal example' WHERE lfPartyId = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, "1");
            long updateRes = pstmt.executeUpdate();
            System.out.print("UPDATE:" + updateRes);

            //查询数据并输出
            String sql = "select lfPartyId,partyName from LfParty where lfPartyId = ?";
            PreparedStatement pstmt2 = con.prepareStatement(sql);
            pstmt2.setString(1, "1");
            ResultSet rs = pstmt2.executeQuery();
            while (rs.next()) { //循环输出结果集
                String lfPartyId = rs.getString("lfPartyId");
                String partyName = rs.getString("partyName");
                System.out.print("\r\n\r\n");
                System.out.print("lfPartyId:" + lfPartyId + "partyName:" + partyName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
