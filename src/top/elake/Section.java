package top.elake;

import java.util.InputMismatchException;
import java.util.Scanner;

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
                        break;
                    case 2:
                        // 查询
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
}
