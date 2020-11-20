package com.bj.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PhoenixTest {
    /**
     * 使用phoenix提供的api操作hbase中读取数据
     */
    public static void main(String[] args) throws Throwable {
        try {
            //下面的驱动为Phoenix老版本使用2.11使用，对应hbase0.94+
            //Class.forName("com.salesforce.phoenix.jdbc.PhoenixDriver");
            //phoenix4.3用下面的驱动对应hbase0.98+
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //这里配置zk的地址，可单个，也可多个。可以是域名或者ip
        String url = "jdbc:phoenix:hadoop01,hadoop02,hadoop03:2181";
        Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();
        String sql = "select *  from \"test1\" where \"ROW\" = '2' ";
        long time = System.currentTimeMillis();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            //获取core字段值
            String id = rs.getString("id");
            //获取core字段值
            String name = rs.getString("name");
            //获取domain字段值
            String sex = rs.getString("sex");
            //获取feature字段值
            String age = rs.getString("age");
            System.out.println("id:"+id+"\tname:"+name+"\tsex:"+sex+"\tage:"+age);
        }
        long timeUsed = System.currentTimeMillis() - time;
        System.out.println("time " + timeUsed + "mm");
        //关闭连接
        rs.close();
        statement.close();
        conn.close();
    }
}