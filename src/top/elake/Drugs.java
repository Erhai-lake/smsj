package top.elake;

import java.sql.*;
import java.util.*;

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
        List<Object[]> Result;
        do {
            System.out.println("*** 药品管理 ***");
            System.out.println("1. 新增");
            System.out.println("2. 查询");
            System.out.println("3. 列出所有");
            System.out.println("4. 返回");
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
                        Result = Query(DrugsName);
                        if (Result.isEmpty()) {
                            System.out.println("没有找到匹配的数据");
                        } else {
                            System.out.println("编号\t名称\t价格");
                            for (Object[] Row : Result) {
                                int DrugsIdRow = (int) Row[0];
                                String DrugsNameRow = (String) Row[1];
                                String DrugsChargesRow = (String) Row[2];
                                System.out.println(DrugsIdRow + "\t" + DrugsNameRow + "\t" + DrugsChargesRow);
                            }
                            System.out.print("请选择要操作的编号: ");
                            int ID = Scanner.nextInt();
                            SelectedMenu(ID);
                        }
                        break;
                    case 3:
                        // 列出所有
                        Result = QueryAll();
                        if (Result.isEmpty()) {
                            System.out.println("没有数据");
                        } else {
                            System.out.println("编号\t名称\t价格");
                            for (Object[] Row : Result) {
                                int DrugsIdRow = (int) Row[0];
                                String DrugsNameRow = (String) Row[1];
                                String DrugsChargesRow = (String) Row[2];
                                System.out.println(DrugsIdRow + "\t" + DrugsNameRow + "\t" + DrugsChargesRow);
                            }
                            System.out.print("请选择要操作的编号: ");
                            int ID = Scanner.nextInt();
                            SelectedMenu(ID);
                        }
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

    // 选中菜单
    public void SelectedMenu(int ID) {
        boolean Status = true;
        do {
            System.out.println("*** 操作 ***");
            System.out.println("1. 删除");
            System.out.println("2. 开药");
            System.out.println("3. 修改");
            System.out.println("4. 重新选择");
            System.out.println("5. 返回");
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
                        Status = PrescribeMedicine(ID);
                        break;
                    case 3:
                        // 修改
                        System.out.print("请输入药品名: ");
                        String DrugsName = Scanner.next();
                        System.out.print("请输入药品价格: ");
                        String DrugsCharges = Scanner.next();
                        if (DrugsName.isEmpty() || DrugsCharges.isEmpty()) {
                            System.out.println("药品名或药品价格不能为空");
                            break;
                        }
                        Status = Modify(DrugsName, DrugsCharges, ID);
                        break;
                    case 4:
                        // 重新选择
                        System.out.print("请选择要操作的编号: ");
                        ID = Scanner.nextInt();
                        break;
                    case 5:
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
    public Boolean PrescribeMedicine(int DrugsId) {
        System.out.print("请输入你要查询的名称或电话号码(支持模糊搜索): ");
        String Value = Scanner.next();
        if (Value.isEmpty()) {
            System.out.println("名称或电话号码不能为空");
        } else {
            Registration Registration = new Registration();
            List<Object[]> Result = Registration.Query(Value, !Value.matches("[0-9]+"));
            if (Result.isEmpty()) {
                System.out.println("没有找到匹配的数据");
            } else {
                System.out.println("编号\t名字\t电话号码\t科室号");
                for (Object[] Row : Result) {
                    int RegistrationIdRow = (int) Row[0];
                    String UserNameRow = (String) Row[1];
                    String CellRow = (String) Row[2];
                    String SectionIdRow = (String) Row[3];
                    System.out.println(RegistrationIdRow + "\t" + UserNameRow + "\t" + CellRow + "\t" + SectionIdRow);
                }
                System.out.print("请选择要操作的编号: ");
                int ID = Scanner.nextInt();
                Charges Charges = new Charges();
                Charges.Modify(ID, DrugsId);
                System.out.println("开药成功");
                return false;
            }
        }
        return true;
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

    // 修改
    public Boolean Modify(String DrugsName, String DrugsCharges, int DrugsId) {
        try {
            String SQL = "UPDATE Drugs SET DrugsName = ?, DrugsCharges = ? WHERE DrugsId = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, DrugsName);
            PreparedStatement.setString(2, DrugsCharges);
            PreparedStatement.setInt(3, DrugsId);
            PreparedStatement.executeUpdate();
            System.out.println("药品修改成功");
            return false;
        } catch (SQLException e) {
            System.out.println("修改药品时出现错误:" + e.getMessage());
            return true;
        }
    }

    // 查询
    public List<Object[]> Query(String Value) {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL = "SELECT * FROM Drugs WHERE DrugsName LIKE ?";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, "%" + Value + "%");
            ResultSet ResultSet = PreparedStatement.executeQuery();
            if (!ResultSet.next()) {
                System.out.println("没有找到匹配的数据");
            } else {
                while (ResultSet.next()) {
                    Object[] Data = new Object[3];
                    Data[0] = ResultSet.getInt("DrugsId");
                    Data[1] = ResultSet.getString("DrugsName");
                    Data[2] = ResultSet.getString("DrugsCharges");
                    ResultData.add(Data);
                }
            }
        } catch (SQLException e) {
            System.out.println("查询药品时出现错误:" + e.getMessage());
        }
        return ResultData;
    }

    public List<Object[]> QueryAll() {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL = "SELECT * FROM Drugs";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            ResultSet ResultSet = PreparedStatement.executeQuery();
            while (ResultSet.next()) {
                Object[] Data = new Object[3];
                Data[0] = ResultSet.getInt("DrugsId");
                Data[1] = ResultSet.getString("DrugsName");
                Data[2] = ResultSet.getString("DrugsCharges");
                ResultData.add(Data);
            }
        } catch (SQLException e) {
            System.out.println("列出药品时出现错误:" + e.getMessage());
        }
        return ResultData;
    }
}
