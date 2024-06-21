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
INSERT INTO Registration (RegistrationId, UserName, Cell, SectionId)
VALUES (1, '小本', '12345678900', 1),
       (2, '小天', '114514', 2),
       (3, '牢大', '01110010', 1),
       (4, '大佐', '000101010101', 1),
       (5, 'Xekr', '0110', 2),
       (6, 'fufu', '010', 1),
       (7, '柒月', '1919810', 1);

-- 收费表
CREATE TABLE `Charges`
(
    `ChargesId`      INT          NOT NULL AUTO_INCREMENT COMMENT '收费ID',
    `RegistrationId` INT          NOT NULL COMMENT '挂号ID',
    `Charges`        VARCHAR(255) NOT NULL COMMENT '费用',
    `Status`         INT COMMENT '状态(0:已缴费,1:未缴费)',
    PRIMARY KEY (`ChargesId`),
    CONSTRAINT `Charges_RegistrationId` FOREIGN KEY (`RegistrationId`) REFERENCES `Registration` (`RegistrationId`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT = '收费表';
INSERT INTO Charges (ChargesId, RegistrationId, Charges, Status)
VALUES (1, 1, '16', 1),
       (2, 2, '16', 1),
       (3, 3, '16', 1),
       (4, 4, '16', 1),
       (5, 5, '16', 2),
       (6, 6, '16', 1),
       (7, 7, '16', 1);

-- 药品表
CREATE TABLE `Drugs`
(
    `DrugsId`      INT          NOT NULL AUTO_INCREMENT COMMENT '药品ID',
    `DrugsName`    VARCHAR(255) NOT NULL COMMENT '药品名称',
    `DrugsCharges` VARCHAR(255) NOT NULL COMMENT '药品价格',
    PRIMARY KEY (`DrugsId`)
) COMMENT = '药品表';

INSERT INTO Drugs (DrugsName, DrugsCharges)
VALUES ('999抗病毒口服液', '19.9'),
       ('999感冒灵颗粒', '27.9'),
       ('小柴胡颗粒', '29.9');


-- 职员表
CREATE TABLE `Staff`
(
    `StaffId`   INT          NOT NULL AUTO_INCREMENT COMMENT '职员ID',
    `StaffName` VARCHAR(255) NOT NULL COMMENT '职员名称',
    PRIMARY KEY (`StaffId`)
) COMMENT = '职员表';

INSERT INTO Staff (StaffName)
VALUES ('张三'),
       ('李四');

-- 科室表
CREATE TABLE `Section`
(
    `SectionId`   INT          NOT NULL AUTO_INCREMENT COMMENT '科室ID',
    `SectionName` VARCHAR(255) NOT NULL COMMENT '科室名称',
    `StaffId`     INT          NOT NULL COMMENT '职员ID',
    PRIMARY KEY (`SectionId`),
    CONSTRAINT `Section_StaffId` FOREIGN KEY (`StaffId`) REFERENCES `Staff` (`StaffId`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT = '科室表';

INSERT INTO Section (SectionName, StaffId)
VALUES ('诊室1', 1),
       ('诊室2', 2);
