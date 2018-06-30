# License-key

该项目采用RSA非对称式加密算法，生成软件的license-key

# 简介

1. 获取系统的硬件信息

- 主板序列号
- 硬盘序列号
- Mac地址

2. 生成RSA密钥对

使用SHA256对硬件信息等信息获取摘要，然后添加用户信息和授权时间, 然后使用RSA私钥对进行摘要进行加密，产生license-key。
中央数据库保存用户的信息和对应的硬件信息，授权时间，信息摘要，RSA秘钥对等信息。

3. 分发给用户

将用户对应的RSA公钥和加密后的license-key 分发给用户，在使用软件之前需要输入公钥和license-key。

4. 程序员工作

- 硬件授权： 启动程序前，读取硬件信息来计算SHA256，使用用户提供的公钥解密license-key得到SHA256摘要码，比较二者的差异。
- 时间授权： 启动程序前，读取解密后的时间信息，然后比较当前时间的值，确定license是否过期

# 安装

## 依赖

- JRE 1.8

``` bash

git clone ~
cd license-key
sh gradlew build
sh gradlew copyJar
sh gradlew copyRes
cd build/libs/
java -cp license-1.0-SNAPSHOT.jar com.oceanai.Main

```

## 访问

1. 网页访问

URL: http://localhost:8888

2. RestAPI

例如：
``` bash
curl 'http://localhost:8888/generate' --data '{"owner":"oceanai-cloud05","boardSn":"boardsn123","macAdd":"2c:4d:54:9e:a5:2a","hddSn":"WD-WCC4M7EYU65P","validDate":"2019-01-01"}' -H 'Content-Type:application/json'
```

## 参数解析

参数 | 类型 | 是否必选 | 说明
--- | --- | --- | ---
owner | string | 是 | license 拥有者
boardSn | String | 是 | 设备主板SN
macAdd | String | 是 | 设备eth0网卡 mac 地址
hddSn | String | 是 | 设备系统硬盘SN 
validDate | String | 是 | license 到期时间, 格式"yyyy-MM-dd"

   