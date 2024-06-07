package top.elake;

import java.util.Scanner;

public class Menu {
  public void Main1() {
    java.util.Scanner Scanner = new Scanner(System.in);
    System.out.println("1. 挂号管理");
    System.out.println("2. 收费管理");
    System.out.println("3. 药品管理");
    System.out.println("4. 职员管理");
    System.out.println("4. 科室管理");
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
    }
    System.out.println(Input);
  }
}
