package top.elake;

import java.util.Scanner;

/**
 * @作者 Erhai_lake
 * @时间 07 6月 2024 08:42
 * @描述 主菜单
 */
public class MainMenu {
  Scanner Scanner = new Scanner(System.in);

  //  主菜单
  public void Menu() {
    System.out.println("*** XXX医院管理系统 ***");
    System.out.println("1. 挂号管理");
    System.out.println("2. 收费管理");
    System.out.println("3. 药品管理");
    System.out.println("4. 职员管理");
    System.out.println("5. 科室管理");
    System.out.println("6. 退出");
    System.out.print("请输入对应的编号: ");
    int Input = Scanner.nextInt();
    switch (Input) {
      case 1:
        Registration Registration = new Registration();
        Registration.Menu();
        break;
      case 2:
        Charges Charges = new Charges();
        Charges.Menu();
        break;
      case 3:
        Drugs Drugs = new Drugs();
        Drugs.Menu();
        break;
      case 4:
        Staff Staff = new Staff();
        Staff.Menu();
        break;
      case 5:
        Section Section = new Section();
        Section.Menu();
        break;
      case 6:
        System.out.println("已退出!");
        System.exit(0);
        break;
      default:
        System.out.println("输入有误,请重新输入");
        Menu();
    }
  }
}
