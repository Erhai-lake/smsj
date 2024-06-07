# 学校要求我们写的构思项目

## 步骤拆解

- [x] 需求分析
- [x] 数据库设计
- [x] 数据库数据录入
- [ ] 项目开发
- [ ] 答辩PPT

## 需求分析

1. 门诊挂号收费管理
   1. 门诊挂号(挂号同时登记病人基本信息)
   2. 退号办理
   3. 病人信息维护和改正
2. 收费站
   1. 药品收费
3. 药品管理
   1. 药品信息
   2. 价格
4. 职员管理
   1. 职员增添
   2. 删除
   3. 分派岗位
5. 科室管理
   1. 增添
   2. 删除
   3. 主治医师配置

## 数据库设计

1. 挂号表:Registration(RegistrationId, UserName, Cell, SectionId)
2. 收费表:Charges(ChargesId, RegistrationId, Charges, Status)
3. 药品表:Drugs(DrugsId, DrugsName, DrugsCharges)
4. 职员表:Staff(StaffId, StaffName, Time)
5. 科室表:Section(SectionId, SectionName, StaffId)
6. SQL表见`MySQL.sql`文件

