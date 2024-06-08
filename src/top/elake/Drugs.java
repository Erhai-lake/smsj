package top.elake;

import java.util.Scanner;

/**
 * @作者 Erhai_lake
 * @时间 08 6月 2024 12:53
 * @描述 药品管理
 */
public class Drugs {
  java.util.Scanner Scanner = new Scanner(System.in);

  // 菜单
  public void Menu() {
    System.out.println("*** 药品管理 ***");
    System.out.println("1. 新增");
    System.out.println("2. 删除");
    System.out.println("3. 查询");
    System.out.println("4. 返回");
    System.out.print("请输入对应的编号: ");
    int Input = Scanner.nextInt();
    switch (Input) {
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
      default:
        System.out.println("输入有误,请重新输入");
        Menu();
    }
  }
}
