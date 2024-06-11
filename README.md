# 学校要求我们写的构思项目

## 步骤拆解

- [x] 需求分析
- [x] 数据库设计
- [x] 数据库数据录入
- [ ] 项目开发
- [ ] 答辩PPT

## 需求分析

1. 门诊挂号收费管理
   1. - [x] 新增
   2. - [x] 删除
   3. - [x] 修改
   4. - [x] 查询
2. 收费
   1. - [x] 修改
   2. - [x] 查询
3. 药品管理
   1. - [x] 新增
   2. - [x] 删除
   3. - [x] 修改
   4. - [x] 查询
4. 职员管理
   1. - [x] 新增
   2. - [x] 删除
   3. - [x] 修改
   4. - [x] 科室
5. 科室管理
   1. - [x] 新增
   2. - [x] 修改
   3. - [x] 删除

## 数据库设计

1. 挂号表:Registration(RegistrationId, UserName, Cell, SectionId)
2. 收费表:Charges(ChargesId, RegistrationId, Charges, Status)
3. 药品表:Drugs(DrugsId, DrugsName, DrugsCharges)
4. 职员表:Staff(StaffId, StaffName, Time)
5. 科室表:Section(SectionId, SectionName, StaffId)
6. SQL表见`MySQL.sql`文件

