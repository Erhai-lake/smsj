package top.elake;

import java.util.Scanner;

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
    do {
      System.out.println("*** 收费管理 ***");
      System.out.println("1. 查询");
      System.out.println("2. 返回");
      System.out.print("请输入对应的编号: ");
      int Input = Scanner.nextInt();
      switch (Input) {
        case 1:
          // 查询
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
}
