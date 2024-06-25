package top.elake;

import java.sql.*;
import java.util.*;

import static top.elake.Main.MySQLConnection;

/**
 * @作者 Erhai_lake
 * @时间 08 6月 2024 12:49
 * @描述 职员管理
 */
public class Staff {
    java.util.Scanner Scanner = new Scanner(System.in);

    // 菜单
    public void Menu() {
        boolean Status = true;
        String StaffName;
        List<Object[]> Result;
        do {
            System.out.println("*** 职员管理 ***");
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
                        System.out.print("请输入职员名称: ");
                        StaffName = Scanner.next();
                        if (StaffName.isEmpty()) {
                            System.out.println("职员名称不能为空");
                            break;
                        }
                        New(StaffName);
                        break;
                    case 2:
                        // 查询
                        System.out.print("请输入你要查询的职员名称(支持模糊搜索): ");
                        StaffName = Scanner.next();
                        if (StaffName.isEmpty()) {
                            System.out.println("职员名称不能为空");
                            break;
                        }
                        Result = Query(StaffName);
                        if (Result.isEmpty()) {
                            System.out.println("没有找到匹配的数据");
                        } else {
                            System.out.println("编号\t名字");
                            for (Object[] Row : Result) {
                                int StaffIdRow = (int) Row[0];
                                String StaffNameRow = (String) Row[1];
                                System.out.println(StaffIdRow + "\t" + StaffNameRow);
                            }
                            System.out.print("请选择要操作的编号: ");
                            int ID = Scanner.nextInt();
                            int Min = (int) Result.get(0)[0];
                            int Max = (int) Result.get(Result.size() - 1)[0];
                            if (ID >= Min && ID <= Max) {
                                SelectedMenu(ID);
                            } else {
                                System.out.println("没有数据");
                            }
                        }
                        break;
                    case 3:
                        // 列出所有
                        Result = QueryAll();
                        if (Result.isEmpty()) {
                            System.out.println("没有数据");
                        } else {
                            System.out.println("编号\t名字");
                            for (Object[] Row : Result) {
                                int StaffIdRow = (int) Row[0];
                                String StaffNameRow = (String) Row[1];
                                System.out.println(StaffIdRow + "\t" + StaffNameRow);
                            }
                            System.out.print("请选择要操作的编号: ");
                            int ID = Scanner.nextInt();
                            int Min = (int) Result.get(0)[0];
                            int Max = (int) Result.get(Result.size() - 1)[0];
                            if (ID >= Min && ID <= Max) {
                                SelectedMenu(ID);
                            } else {
                                System.out.println("没有数据");
                            }
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
    public void SelectedMenu(int Id) {
        boolean Status = true;
        do {
            System.out.println("*** 操作 ***");
            System.out.println("1. 删除");
            System.out.println("2. 修改");
            System.out.println("3. 重新选择");
            System.out.println("4. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 删除
                        Status = Delete(Id);
                        break;
                    case 2:
                        // 修改
                        System.out.print("请输入职员名称: ");
                        String StaffName = Scanner.next();
                        if (StaffName.isEmpty()) {
                            System.out.println("职员名称不能为空");
                            break;
                        }
                        Status = Modify(StaffName, Id);
                        break;
                    case 3:
                        // 重新选择
                        System.out.print("请选择要操作的编号: ");
                        Id = Scanner.nextInt();
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
    public void New(String StaffName) {
        try {
            String SQL = "INSERT INTO Staff (StaffName) VALUES (?)";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, StaffName);
            PreparedStatement.executeUpdate();
            System.out.println("新增职员成功");
        } catch (SQLException e) {
            System.out.println("新增职员时出现错误:" + e.getMessage());
        }
    }

    // 删除
    public Boolean Delete(int StaffId) {
        try {
            String SQL = "DELETE FROM Staff WHERE StaffId = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setInt(1, StaffId);
            PreparedStatement.executeUpdate();
            System.out.println("职员删除成功");
            return false;
        } catch (SQLException e) {
            System.out.println("删除职员时出现错误:" + e.getMessage());
            return true;
        }
    }

    // 修改
    public Boolean Modify(String StaffName, int StaffId) {
        try {
            String SQL = "UPDATE Staff SET StaffName = ? WHERE StaffId = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, StaffName);
            PreparedStatement.setInt(2, StaffId);
            PreparedStatement.executeUpdate();
            System.out.println("职员修改成功");
            return false;
        } catch (SQLException e) {
            System.out.println("修改职员时出现错误:" + e.getMessage());
            return true;
        }
    }

    // 查询
    public List<Object[]> Query(String StaffName) {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL = "SELECT * FROM Staff WHERE StaffName LIKE ?";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, "%" + StaffName + "%");
            ResultSet ResultSet = PreparedStatement.executeQuery();
            while (ResultSet.next()) {
                Object[] Data = new Object[2];
                Data[0] = ResultSet.getInt("StaffId");
                Data[1] = ResultSet.getString("StaffName");
                ResultData.add(Data);
            }
        } catch (SQLException e) {
            System.out.println("查询职员时出现错误:" + e.getMessage());
        }
        return ResultData;
    }

    public List<Object[]> QueryAll() {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL = "SELECT * FROM Staff";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            ResultSet ResultSet = PreparedStatement.executeQuery();
            while (ResultSet.next()) {
                Object[] Data = new Object[2];
                Data[0] = ResultSet.getInt("StaffId");
                Data[1] = ResultSet.getString("StaffName");
                ResultData.add(Data);
            }
        } catch (SQLException e) {
            System.out.println("列出职员时出现错误:" + e.getMessage());
        }
        return ResultData;
    }
}
