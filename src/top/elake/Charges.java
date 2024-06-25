package top.elake;

import java.sql.*;
import java.util.*;

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
        int RegistrationId;
        List<Object[]> Result;
        do {
            System.out.println("*** 收费管理 ***");
            System.out.println("1. 查询");
            System.out.println("2. 列出所有");
            System.out.println("3. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 查询
                        System.out.print("请输入你要查询的名称或电话号码(支持模糊搜索): ");
                        String Value = Scanner.next();
                        if (Value.isEmpty()) {
                            System.out.println("名称或电话号码不能为空");
                            break;
                        }
                        Registration Registration = new Registration();
                        Result = Registration.Query(Value, !Value.matches("[0-9]+"));
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
                            RegistrationId = Scanner.nextInt();
                            Result = Query(RegistrationId);
                            if (Result.isEmpty()) {
                                System.out.println("没有找到匹配的数据");
                            } else {
                                System.out.println("编号\t名称\t费用\t状态");
                                for (Object[] Row : Result) {
                                    int ChargesIdRow = (int) Row[0];
                                    String UserNameRow = (String) Row[1];
                                    String ChargesRow = (String) Row[2];
                                    String StatusRow = (String) Row[3];
                                    System.out.println(ChargesIdRow + "\t" + UserNameRow + "\t" + ChargesRow + "\t" + StatusRow);
                                }
                                System.out.print("请选择要操作的编号: ");
                                RegistrationId = Scanner.nextInt();
                                int Min = (int) Result.get(0)[0];
                                int Max = (int) Result.get(Result.size() - 1)[0];
                                if (RegistrationId >= Min && RegistrationId <= Max) {
                                    SelectedMenu(RegistrationId);
                                } else {
                                    System.out.println("没有数据");
                                }
                            }
                        }
                        break;
                    case 2:
                        // 列出所有
                        Result = QueryAll();
                        if (Result.isEmpty()) {
                            System.out.println("没有数据");
                        } else {
                            System.out.println("编号\t名称\t费用\t状态");
                            for (Object[] Row : Result) {
                                int ChargesIdRow = (int) Row[0];
                                String UserNameRow = (String) Row[1];
                                String ChargesRow = (String) Row[2];
                                String StatusRow = (String) Row[3];
                                System.out.println(ChargesIdRow + "\t" + UserNameRow + "\t" + ChargesRow + "\t" + StatusRow);
                            }
                            System.out.print("请选择要操作的编号: ");
                            RegistrationId = Scanner.nextInt();
                            int Min = (int) Result.get(0)[0];
                            int Max = (int) Result.get(Result.size() - 1)[0];
                            if (RegistrationId >= Min && RegistrationId <= Max) {
                                SelectedMenu(RegistrationId);
                            } else {
                                System.out.println("没有数据");
                            }
                        }
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
    public void Modify(int RegistrationId, int DrugsId) {
        String SQL;
        try {
            SQL = "SELECT DrugsCharges FROM Drugs WHERE DrugsId = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setInt(1, DrugsId);
            ResultSet ResultSet = PreparedStatement.executeQuery();
            ResultSet.next();
            double Charges = Double.parseDouble(ResultSet.getString("DrugsCharges"));
            SQL = "SELECT Charges FROM Charges WHERE RegistrationId = ?";
            PreparedStatement selectStatement = MySQLConnection.prepareStatement(SQL);
            selectStatement.setInt(1, RegistrationId);
            ResultSet ResultSet2 = selectStatement.executeQuery();
            ResultSet2.next();
            double CurrentCharges = Double.parseDouble(ResultSet2.getString("Charges"));
            SQL = "UPDATE Charges SET Charges = ? WHERE RegistrationId = ?";
            PreparedStatement preparedStatement = MySQLConnection.prepareStatement(SQL);
            preparedStatement.setString(1, String.valueOf(CurrentCharges + Charges));
            preparedStatement.setInt(2, RegistrationId);
            preparedStatement.executeUpdate();
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
    public List<Object[]> Query(int RegistrationId) {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL;
        SQL = "SELECT * FROM Charges WHERE RegistrationId = ?";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setInt(1, RegistrationId);
            ResultSet ResultSet = PreparedStatement.executeQuery();
            while (ResultSet.next()) {
                SQL = "SELECT * FROM Registration WHERE RegistrationId = ?";
                PreparedStatement = MySQLConnection.prepareStatement(SQL);
                PreparedStatement.setString(1, ResultSet.getString("RegistrationId"));
                ResultSet ResultSetRegistration = PreparedStatement.executeQuery();
                ResultSetRegistration.next();
                Object[] Data = new Object[4];
                Data[0] = ResultSet.getInt("ChargesId");
                Data[1] = ResultSetRegistration.getString("UserName");
                Data[2] = ResultSet.getString("Charges");
                if (ResultSet.getString("Status").equals("0")) {
                    Data[3] = "已缴费";
                } else {
                    Data[3] = "未缴费";
                }
                ResultData.add(Data);
            }
        } catch (SQLException e) {
            System.out.println("查询费用时出现错误:" + e.getMessage());
        }
        return ResultData;
    }

    public List<Object[]> QueryAll() {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL;
        SQL = "SELECT * FROM Charges";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            ResultSet ResultSet = PreparedStatement.executeQuery();
            while (ResultSet.next()) {
                SQL = "SELECT * FROM Registration WHERE RegistrationId = ?";
                PreparedStatement = MySQLConnection.prepareStatement(SQL);
                PreparedStatement.setString(1, ResultSet.getString("RegistrationId"));
                ResultSet ResultSetRegistration = PreparedStatement.executeQuery();
                ResultSetRegistration.next();
                Object[] Data = new Object[4];
                Data[0] = ResultSet.getInt("ChargesId");
                Data[1] = ResultSetRegistration.getString("UserName");
                Data[2] = ResultSet.getString("Charges");
                if (ResultSet.getString("Status").equals("0")) {
                    Data[3] = "已缴费";
                } else {
                    Data[3] = "未缴费";
                }
                ResultData.add(Data);
            }
        } catch (SQLException e) {
            System.out.println("列出费用时出现错误:" + e.getMessage());
        }
        return ResultData;
    }
}
