package top.elake;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @作者 Erhai_lake
 * @时间 07 6月 2024 08:42
 * @描述 入口
 */

// 入口
public class Main {
    static Scanner Scanner = new Scanner(System.in);
    static Connection MySQLConnection;

    public static void main(String[] args) {
        System.out.println("正在连接数据库,请稍后...");
        MySQLConnection = ConnectToMySQL();
        System.out.println("连接成功");
        // 主菜单
        boolean Status = true;
        do {
            System.out.println("*** XXX医院管理系统 ***");
            System.out.println("1. 挂号管理");
            System.out.println("2. 收费管理");
            System.out.println("3. 药品管理");
            System.out.println("4. 职员管理");
            System.out.println("5. 科室管理");
            System.out.println("6. 退出");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 挂号管理
                        Registration Registration = new Registration();
                        Registration.Menu();
                        break;
                    case 2:
                        // 收费管理
                        Charges Charges = new Charges();
                        Charges.Menu();
                        break;
                    case 3:
                        // 药品管理
                        Drugs Drugs = new Drugs();
                        Drugs.Menu();
                        break;
                    case 4:
                        // 职员管理
                        Staff Staff = new Staff();
                        Staff.Menu();
                        break;
                    case 5:
                        // 科室管理
                        Section Section = new Section();
                        Section.Menu();
                        break;
                    case 6:
                        // 退出
                        Status = false;
                        break;
                    default:
                        System.out.println("输入有误,请重新输入");
                }
            } catch (InputMismatchException e) {
                System.out.println("输入有误,请重新输入");
                Scanner.nextLine();
            }
        } while (Status);
        CloseConnection();
        System.out.println("已退出!");
    }

    // 读取配置项
    public static String Config(String Name) {
        Properties Properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            Properties.load(fis);
            return Properties.getProperty(Name);
        } catch (IOException e) {
            System.out.println("无法读取配置文件:" + e.getMessage());
        }
        return "";
    }

    // 连接数据库
    public static Connection ConnectToMySQL() {
        Connection Connection = null;
        try {
            Connection = DriverManager.getConnection("jdbc:mysql://" + Config("MySQLHostName") + ":" + Config("MySQLPort") + "/" + Config("MySQLDatabase"), Config("MySQLUserName"), Config("MySQLPassword"));
        } catch (SQLException e) {
            System.out.println("无法连接到MySQL数据库:" + e.getMessage());
            System.out.println("系统已终止");
            System.exit(0);
        }
        return Connection;
    }

    // 关闭数据库连接
    public static void CloseConnection() {
        if (MySQLConnection != null) {
            try {
                MySQLConnection.close();
            } catch (SQLException e) {
                System.out.println("关闭数据库连接时出现错误:" + e.getMessage());
            }
        }
    }
}
