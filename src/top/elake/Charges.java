package top.elake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
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
            try {
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
                        System.out.print("请选择要操作的编号: ");
                        int ID = Scanner.nextInt();
                        SelectedMenu(ID);
                        break;
                    case 2:
                        // 返回
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
    }

    // 选中菜单
    public void SelectedMenu(int ID) {
        boolean Status = true;
        do {
            System.out.println("*** 操作 ***");
            System.out.println("1. 缴费");
            System.out.println("2. 重新选择");
            System.out.println("3. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 删除
                        Status = Payment(ID);
                        break;
                    case 2:
                        // 重新选择
                        System.out.print("请选择要操作的编号: ");
                        ID = Scanner.nextInt();
                        break;
                    case 3:
                        // 返回
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
    public void Modify(int ChargesId, int Charges) {
        try {
            String SQL;
            SQL = "SELECT Charges FROM Charges WHERE ChargesId = ?";
            PreparedStatement selectStatement = MySQLConnection.prepareStatement(SQL);
            selectStatement.setInt(1, ChargesId);
            ResultSet ResultSet = selectStatement.executeQuery();
            if (ResultSet.next()) {
                double CurrentCharges = Double.parseDouble(ResultSet.getString("Charges"));
                SQL = "UPDATE Charges SET Charges = ? WHERE ChargesId = ?";
                PreparedStatement preparedStatement = MySQLConnection.prepareStatement(SQL);
                preparedStatement.setString(1, String.valueOf(CurrentCharges + Charges));
                preparedStatement.setInt(2, ChargesId);
                preparedStatement.executeUpdate();
            } else {
                System.out.println("未找到对应的费用记录");
            }
        } catch (SQLException e) {
            System.out.println("记录费用时出现错误:" + e.getMessage());
        }
    }

    // 缴费
    public boolean Payment(int ChargesId) {
        try {
            String SQL = "UPDATE Charges SET Status = ? WHERE ChargesId = ?";
            PreparedStatement preparedStatement = MySQLConnection.prepareStatement(SQL);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, ChargesId);
            preparedStatement.executeUpdate();
            System.out.println("缴费成功");
            return false;
        } catch (SQLException e) {
            System.out.println("缴费时出现错误:" + e.getMessage());
            return true;
        }
    }

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
                        System.out.println(ResultSet.getInt("ChargesId") + "\t" + ResultSet.getString("Charges") + "\t" + "未缴费");
                    }
                } while (ResultSet.next());
            }
        } catch (SQLException e) {
            System.out.println("查询费用时出现错误:" + e.getMessage());
        }
    }
}
