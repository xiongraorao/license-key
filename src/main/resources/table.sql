# 创建用户表
CREATE TABLE t_device (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  owner VARCHAR(255) NOT NULL ,
  boardSn VARCHAR(255) NOT NULL ,
  macAdd VARCHAR(255) NOT NULL ,
  hddSn VARCHAR(255) NOT NULL ,
  validDate DATE ,
  privateKey VARCHAR(1024),
  publicKey VARCHAR(1024) ,
  sha256 VARCHAR(64),
  create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

# test for insert

insert into t_device(owner, boardSn, macAdd, hddSn, validDate) VALUES (
    'cloud05', 'board123456', 'mac123456', 'hd123456', '2018-05-07 19:00:00'
)