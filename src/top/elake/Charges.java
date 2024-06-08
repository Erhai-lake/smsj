package top.elake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static top.elake.Main.MySQLConnection;

/**
 * @作者 Erhai_lake
 * @时间 08 6月 2024 12:46
 * @描述 收费管理
 */
public class Charges {
  Scanner Scanner = new Scanner(System.in);

  // 菜单
  public void Menu() {
    boolean Status = true;
    String UserName;
    do {
      System.out.println("*** 收费管理 ***");
      System.out.println("1. 查询");
      System.out.println("2. 返回");
      System.out.print("请输入对应的操作编号: ");
      int Input = Scanner.nextInt();
      switch (Input) {
        case 1:
          // 查询
          System.out.print("请输入你要查询的名称或电话号码: ");
          UserName = Scanner.next();
          if (UserName.isEmpty()) {
            System.out.println("名称或电话号码不能为空");
            break;
          }
          Query(UserName, !UserName.matches("[0-9]+"));
          break;
        case 2:
          // 返回
          Status = false;
          break;
        default:
          System.out.println("输入有误,请重新输入");
      }
    } while (Status);
  }

  // 新增
  public void New(int RegistrationId) {
    try {
      String SQL = "INSERT INTO Charges (RegistrationId, Charges, Status) VALUES (?, ?, ?)";
      PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
      PreparedStatement.setInt(1, RegistrationId);
      PreparedStatement.setString(2, "13");
      PreparedStatement.setInt(3, 1);
      PreparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("挂号费记录时出现错误:" + e.getMessage());
    }
  }

  // 修改
//  public void Modify(){}

  // 查询
  public void Query(String Value, Boolean Type) {
    Registration Registration = new Registration();
    Registration.Query(Value, Type);
    System.out.print("请选择要查询的编号: ");
    int ID = Scanner.nextInt();
    String SQL = "SELECT * FROM Charges WHERE RegistrationId = ?";
    try {
      PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
      PreparedStatement.setInt(1, ID);
      ResultSet ResultSet = PreparedStatement.executeQuery();
      if (!ResultSet.next()) {
        System.out.println("没有找到匹配的数据");
      } else {
        System.out.println("编号\t费用\t缴费状态");
        do {
          if (ResultSet.getString("Status").equals("0")) {
            System.out.println(ResultSet.getInt("ChargesId") + "\t" + ResultSet.getString("Charges") + "\t" + "已缴费");
          } else {
            System.out.println(ResultSet.getInt("ChargesId") + "\t" + ResultSet.getString("Charges") + "\t" +  "未缴费");
          }
        } while (ResultSet.next());
      }
    } catch (SQLException e) {
      System.out.println("查询费用时出现错误:" + e.getMessage());
    }
  }
}
