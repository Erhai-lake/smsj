# 学校要求我们写的构思项目

## 步骤拆解

- [x] 需求分析
- [ ] 数据库设计
- [ ] 数据库数据录入
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
   2. 数量
   3. 有效时间
4. 职员管理
   1. 职员增添
   2. 删除
   3. 分派岗位
5. 科室管理
   1. 增添
   2. 删除
   3. 主治医师配置

## 数据库设计

挂号表(RegistrationId, UserName, Cell, SectionId)
收费表(ChargesId, Registration, Charges, Status)
药品表(DrugsId, ChargesId, Registration)
职员表(StaffId, StaffName, Time)
科室表(SectionId, SectionName, StaffId)
