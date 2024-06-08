use smsj;

-- 挂号表
CREATE TABLE `Registration`
(
    `RegistrationId` INT          NOT NULL AUTO_INCREMENT COMMENT '挂号ID',
    `UserName`       VARCHAR(255) NOT NULL COMMENT '用户名',
    `Cell`           VARCHAR(255) NOT NULL COMMENT '用户电话号码',
    `SectionId`      INT(255) COMMENT '科室ID',
    PRIMARY KEY (`RegistrationId`)
) COMMENT = '挂号表';

-- 收费表
CREATE TABLE `Charges`
(
    `ChargesId`      INT          NOT NULL AUTO_INCREMENT COMMENT '收费ID',
    `RegistrationId` INT          NOT NULL COMMENT '挂号ID',
    `Charges`        VARCHAR(255) NOT NULL COMMENT '费用',
    `Status`         TINYINT(1) COMMENT '状态(0:已缴费,1:未缴费)',
    PRIMARY KEY (`ChargesId`),
    CONSTRAINT `Charges_RegistrationId` FOREIGN KEY (`RegistrationId`) REFERENCES `Registration` (`RegistrationId`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT = '收费表';

-- 药品表
CREATE TABLE `Drugs`
(
    `DrugsId`      INT          NOT NULL AUTO_INCREMENT COMMENT '药品ID',
    `DrugsName`    VARCHAR(255) NOT NULL COMMENT '药品名称',
    `DrugsCharges` VARCHAR(255) NOT NULL COMMENT '药品价格',
    PRIMARY KEY (`DrugsId`)
) COMMENT = '药品表';

-- 职员表
CREATE TABLE `Staff`
(
    `StaffId`   INT          NOT NULL AUTO_INCREMENT COMMENT '职员ID',
    `StaffName` VARCHAR(255) NOT NULL COMMENT '职员名称',
    `Time`      Time         NOT NULL COMMENT '职员上班时间',
    PRIMARY KEY (`StaffId`)
) COMMENT = '职员表';

-- 科室表
CREATE TABLE `Section`
(
    `SectionId`   INT          NOT NULL AUTO_INCREMENT COMMENT '科室ID',
    `SectionName` VARCHAR(255) NOT NULL COMMENT '科室名称',
    `StaffId`     INT          NOT NULL COMMENT '职员ID',
    PRIMARY KEY (`SectionId`),
    CONSTRAINT `Section_StaffId` FOREIGN KEY (`StaffId`) REFERENCES `Staff` (`StaffId`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT = '科室表';
