package top.elake;

import java.sql.*;
import java.util.*;

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
        String UserName;
        String Cell;
        int SectionId;
        do {
            System.out.println("*** 挂号管理 ***");
            System.out.println("1. 新增");
            System.out.println("2. 查询");
            System.out.println("3. 返回");
            System.out.print("请输入对应的操作编号: ");
            try {
                int Input = Scanner.nextInt();
                switch (Input) {
                    case 1:
                        // 新增
                        System.out.print("请输入用户名: ");
                        UserName = Scanner.next();
                        System.out.print("请输入电话号码: ");
                        Cell = Scanner.next();
                        if (UserName.isEmpty() || Cell.isEmpty()) {
                            System.out.println("用户名或电话号码不能为空");
                            break;
                        }
                        // 等科室写完,这里写一个选择科室的
                        SectionId = 1;
                        // 暂时使用1
                        New(UserName, Cell, SectionId);
                        break;
                    case 2:
                        // 查询
                        System.out.print("请输入你要查询的名称或电话号码: ");
                        UserName = Scanner.next();
                        if (UserName.isEmpty()) {
                            System.out.println("名称或电话号码不能为空");
                            break;
                        }
                        List<Object[]> Result = Query(UserName, !UserName.matches("[0-9]+"));
                        if (Result.isEmpty()) {
                            System.out.println("没有找到匹配的数据");
                        } else {
                            for (Object[] row : Result) {
                                int RegistrationIdRow = (int) row[0];
                                String UserNameRow = (String) row[1];
                                String CellRow = (String) row[2];
                                String SectionIdRow = (String) row[3];
                                System.out.println(RegistrationIdRow + "\t" + UserNameRow + "\t" + CellRow + "\t" + SectionIdRow);
                            }
                        }
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
    public void New(String UserName, String Cell, int SectionId) {
        try {
            String SQL = "INSERT INTO Registration (UserName, Cell, SectionId) VALUES (?, ?, ?)";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement.setString(1, UserName);
            PreparedStatement.setString(2, Cell);
            PreparedStatement.setInt(3, SectionId);
            PreparedStatement.executeUpdate();
            // 这里要收13块钱挂号费
            ResultSet GeneratedKeys = PreparedStatement.getGeneratedKeys();
            if (GeneratedKeys.next()) {
                int RegistrationId = GeneratedKeys.getInt(1);
                System.out.println("挂号成功,挂号号为: " + RegistrationId);
                Charges Charges = new Charges();
                Charges.New(RegistrationId);
            } else {
                System.out.println("无法获取挂号号");
            }
        } catch (SQLException e) {
            System.out.println("挂号时出现错误:" + e.getMessage());
        }
    }

    // 删除
    public Boolean Delete(int RegistrationId) {
        try {
            String SQL = "DELETE FROM Registration WHERE RegistrationId = ?";
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setInt(1, RegistrationId);
            PreparedStatement.executeUpdate();
            System.out.println("挂号删除成功");
            return false;
        } catch (SQLException e) {
            System.out.println("删除挂号时出现错误:" + e.getMessage());
            return true;
        }
    }

    // 查询
    public List<Object[]> Query(String Value, Boolean Type) {
        List<Object[]> ResultData = new ArrayList<>();
        String SQL;
        if (Type) {
            SQL = "SELECT * FROM Registration WHERE UserName = ?";
        } else {
            SQL = "SELECT * FROM Registration WHERE Cell = ?";
        }
        try {
            PreparedStatement PreparedStatement = MySQLConnection.prepareStatement(SQL);
            PreparedStatement.setString(1, Value);
            ResultSet ResultSet = PreparedStatement.executeQuery();
            while (ResultSet.next()) {
                Object[] Data = new Object[4];
                Data[0] = ResultSet.getInt("RegistrationId");
                Data[1] = ResultSet.getString("UserName");
                Data[2] = ResultSet.getString("Cell");
                Data[3] = ResultSet.getString("SectionId");
                ResultData.add(Data);
            }
        } catch (SQLException e) {
            System.out.println("查询挂号时出现错误:" + e.getMessage());
        }
        return ResultData;
    }
}
