package top.elake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static top.elake.Main.MySQLConnection;

/**
 * @作者 Erhai_lake
 * @时间 08 6月 2024 12:53
 * @描述 药品管理
 */
public class Drugs {
    java.util.Scanner Scanner = new Scanner(System.in);

    // 菜单
    public void Menu() {
        boolean Status = true;
        String DrugsName;
        String DrugsCharges;
        do {
            System.out.println("*** 药品管理 ***");
            System.out.println("1. 新增");
            System.out.println("2. 查询");
            System.out.println("3. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 新增
                        System.out.print("请输入药品名: ");
                        DrugsName = Scanner.next();
                        System.out.print("请输入药品价格: ");
                        DrugsCharges = Scanner.next();
                        if (DrugsName.isEmpty() || DrugsCharges.isEmpty()) {
                            System.out.println("药品名或药品价格不能为空");
                            break;
                        }
                        New(DrugsName, DrugsCharges);
                        break;
                    case 2:
                        // 查询
                        System.out.print("请输入药品名(支持模糊搜索): ");
                        DrugsName = Scanner.next();
                        if (DrugsName.isEmpty()) {
                            System.out.println("药品名不能为空");
                            break;
                        }
                        Query(DrugsName);
                        System.out.print("请选择要操作的编号: ");
                        int ID = Scanner.nextInt();
                        SelectedMenu(ID);
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

    // 选中菜单
    public void SelectedMenu(int ID) {
        boolean Status = true;
        do {
            System.out.println("*** 操作 ***");
            System.out.println("1. 删除");
            System.out.println("2. 开药");
            System.out.println("3. 重新选择");
            System.out.println("4. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 删除
                        Status = Delete(ID);
                        break;
                    case 2:
                        // 开药
                        System.out.print("请输入你要查询的名称或电话号码: ");
                        String Value = Scanner.next();
                        if (Value.isEmpty()) {
                            System.out.println("名称或电话号码不能为空");
                            break;
                        }
                        Status = PrescribeMedicine(Value, !Value.matches("[0-9]+"), ID);
                        break;
                    case 3:
                        // 重新选择
                        System.out.print("请选择要操作的编号: ");
                        ID = Scanner.nextInt();
                        break;
                    case 4:
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
    public void New(String DrugsName, String DrugsCharges) {
        try {
            String SQL = "INSERT INTO Drugs (DrugsName, DrugsCharges) VALUES (?, ?)";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, DrugsName);
            PreparedStatement.setString(2, DrugsCharges);
            PreparedStatement.executeUpdate();
            System.out.println("新增药品成功");
        } catch (SQLException e) {
            System.out.println("新增药品时出现错误:" + e.getMessage());
        }
    }

    // 开药
    public Boolean PrescribeMedicine(String Value, Boolean Type, int DrugsId) {
        Registration Registration = new Registration();
        Registration.Query(Value, Type);
        System.out.print("你要给谁开药: ");
        int ID = Scanner.nextInt();
        Charges Charges = new Charges();
        Charges.Modify(ID, DrugsId);
        System.out.println("开药成功");
        return false;
    }

    // 删除
    public Boolean Delete(int DrugsId) {
        try {
            String SQL = "DELETE FROM Drugs WHERE DrugsId = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setInt(1, DrugsId);
            PreparedStatement.executeUpdate();
            System.out.println("药品删除成功");
            return false;
        } catch (SQLException e) {
            System.out.println("删除药品时出现错误:" + e.getMessage());
            return true;
        }
    }

    // 查询
    public void Query(String Value) {
        String SQL = "SELECT * FROM Drugs WHERE DrugsName LIKE ?";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, "%" + Value + "%");
            ResultSet ResultSet = PreparedStatement.executeQuery();
            if (!ResultSet.next()) {
                System.out.println("没有找到匹配的数据");
            } else {
                System.out.println("编号\t名称\t价格");
                do {
                    System.out.println(ResultSet.getInt("DrugsId") + "\t" + ResultSet.getString("DrugsName") + "\t" + ResultSet.getString("DrugsCharges"));
                } while (ResultSet.next());
            }
        } catch (SQLException e) {
            System.out.println("查询药品时出现错误:" + e.getMessage());
        }
    }
}
