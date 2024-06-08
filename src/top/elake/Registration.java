package top.elake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static top.elake.Main.MySQLConnection;

/**
 * @作者 Erhai_lake
 * @时间 08 6月 2024 12:16
 * @描述 挂号管理
 */
public class Registration {
  Scanner Scanner = new Scanner(System.in);

  // 菜单
  public void Menu() {
    boolean Status = true;
    do {
      System.out.println("*** 挂号管理 ***");
      System.out.println("1. 新增");
      System.out.println("2. 删除");
      System.out.println("3. 查询");
      System.out.println("4. 返回");
      System.out.print("请输入对应的编号: ");
      int Input = Scanner.nextInt();
      switch (Input) {
        case 1:
          // 新增
          break;
        case 2:
          // 删除
          break;
        case 3:
          // 查询
          System.out.print("请输入你要查询的用户名: ");
          String UserName = Scanner.next();
          try {
            String SQL = "SELECT * FROM Registration WHERE UserName = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, UserName);
            ResultSet ResultSet = PreparedStatement.executeQuery();
            if (!ResultSet.next()) {
              System.out.println("没有找到匹配的数据");
            } else {
              do {
                System.out.println("ID: " + ResultSet.getInt("RegistrationId") + ", Name: " + ResultSet.getString("UserName"));
              } while (ResultSet.next());
            }
          } catch (SQLException e) {
            System.out.println("查询数据时出现错误:" + e.getMessage());
          }
          break;
        case 4:
          // 返回
          Status = false;
          break;
        default:
          System.out.println("输入有误,请重新输入");
      }
    } while (Status);
  }
}
