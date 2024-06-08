package top.elake;

import java.util.Scanner;

public class Menu {
  java.util.Scanner Scanner = new Scanner(System.in);

  public void MainMenu() {
    System.out.println("*** XXX医院管理系统 ***");
    System.out.println("1. 挂号管理");
    System.out.println("2. 收费管理");
    System.out.println("3. 药品管理");
    System.out.println("4. 职员管理");
    System.out.println("4. 科室管理");
    System.out.println("5. 退出");
    System.out.print("请输入对应的编号: ");
    int Input = Scanner.nextInt();
    switch (Input) {
      case 1:
        RegistrationMenu();
        break;
      case 2:
        ChargesMenu();
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        System.out.println("已退出!");
        System.exit(0);
        break;
      default:
        System.out.println("输入有误,请重新输入");
        MainMenu();
    }
  }

  public void RegistrationMenu() {
    System.out.println("*** 挂号管理 ***");
    System.out.println("1. 新增");
    System.out.println("2. 删除");
    System.out.println("3. 查询");
    System.out.println("4. 返回");
    int Input = Scanner.nextInt();
    switch (Input) {
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        MainMenu();
        break;
      default:
        System.out.println("输入有误,请重新输入");
        RegistrationMenu();
    }
  }

  public void ChargesMenu() {
    System.out.println("*** 收费管理 ***");
    System.out.println("1. 查询");
    System.out.println("2. 返回");
    int Input = Scanner.nextInt();
    switch (Input) {
      case 1:
        break;
      case 2:
        MainMenu();
        break;
      default:
        System.out.println("输入有误,请重新输入");
        ChargesMenu();
    }
  }
}
