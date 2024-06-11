package top.elake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static top.elake.Main.MySQLConnection;

/**
 * @作者 Erhai_lake
 * @时间 08 6月 2024 12:50
 * @描述 科室管理
 */
public class Section {
    java.util.Scanner Scanner = new Scanner(System.in);

    // 菜单
    public void Menu() {
        boolean Status = true;
        String SectionName;
        List<Object[]> Result;
        do {
            System.out.println("*** 科室管理 ***");
            System.out.println("1. 新增");
            System.out.println("2. 查询");
            System.out.println("3. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 新增
                        System.out.print("请输入科室名称: ");
                        SectionName = Scanner.next();
                        if (SectionName.isEmpty()) {
                            System.out.println("科室名称不能为空");
                            break;
                        }
                        System.out.print("请输入你要分配的职员名称(支持模糊搜索): ");
                        String StaffName = Scanner.next();
                        if (StaffName.isEmpty()) {
                            System.out.println("职员名称不能为空");
                            break;
                        }
                        Staff Staff = new Staff();
                        Result = Staff.Query(StaffName);
                        if (Result.isEmpty()) {
                            System.out.println("没有找到匹配的数据");
                        } else {
                            System.out.println("编号\t名字");
                            for (Object[] Row : Result) {
                                int StaffIdRow = (int) Row[0];
                                String StaffNameRow = (String) Row[1];
                                System.out.println(StaffIdRow + "\t" + StaffNameRow);
                            }
                            System.out.print("请选择要分配的编号: ");
                            int ID = Scanner.nextInt();
                            New(SectionName, ID);
                        }
                        break;
                    case 2:
                        // 查询
                        System.out.print("请输入你要查询的科室名称(支持模糊搜索): ");
                        SectionName = Scanner.next();
                        if (SectionName.isEmpty()) {
                            System.out.println("科室名称不能为空");
                            break;
                        }
                        Result = Query(SectionName);
                        if (Result.isEmpty()) {
                            System.out.println("没有找到匹配的数据");
                        } else {
                            System.out.println("编号\t名字\t职员");
                            for (Object[] Row : Result) {
                                int SectionIdRow = (int) Row[0];
                                String SectionNameRow = (String) Row[1];
                                String StaffNameRow = (String) Row[2];
                                System.out.println(SectionIdRow + "\t" + SectionNameRow + "\t" + StaffNameRow);
                            }
                            System.out.print("请选择要操作的编号: ");
                            int ID = Scanner.nextInt();
                            SelectedMenu(ID);
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
            System.out.println("1. 删除");
            System.out.println("2. 重新选择");
            System.out.println("3. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 删除
                        Status = Delete(ID);
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
    public void New(String StaffName, int StaffId) {
        try {
            String SQL = "INSERT INTO Section (SectionName, StaffId) VALUES (?, ?)";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, StaffName);
            PreparedStatement.setInt(2, StaffId);
            PreparedStatement.executeUpdate();
            System.out.println("新增科室成功");
        } catch (SQLException e) {
            System.out.println("新增科室时出现错误:" + e.getMessage());
        }
    }

    // 删除
    public Boolean Delete(int SectionId) {
        try {
            String SQL = "DELETE FROM Section WHERE SectionId = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setInt(1, SectionId);
            PreparedStatement.executeUpdate();
            System.out.println("科室删除成功");
            return false;
        } catch (SQLException e) {
            System.out.println("删除科室时出现错误:" + e.getMessage());
            return true;
        }
    }

    // 查询
    public List<Object[]> Query(String SectionName) {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL;
        SQL = "SELECT * FROM Section WHERE SectionName LIKE ?";
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, "%" + SectionName + "%");
            ResultSet ResultSet = PreparedStatement.executeQuery();
            while (ResultSet.next()) {
                Object[] Data = new Object[3];
                Data[0] = ResultSet.getInt("SectionId");
                Data[1] = ResultSet.getString("SectionName");
                SQL = "SELECT * FROM Staff WHERE StaffId = ?";
                PreparedStatement = MySQLConnection.prepareStatement(SQL);
                PreparedStatement.setInt(1, Integer.parseInt(ResultSet.getString("StaffId")));
                ResultSet ResultSet2 = PreparedStatement.executeQuery();
                ResultSet2.next();
                Data[2] = ResultSet2.getString("StaffName");
                ResultData.add(Data);
            }
        } catch (SQLException e) {
            System.out.println("查询科室时出现错误:" + e.getMessage());
        }
        return ResultData;
    }
}
