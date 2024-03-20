# PetBackend

2023-2024 ECNU SoftWare Development backend project

### 上手指南

###### 开发前的配置要求

###### **安装步骤**

```
# clone
git clone https://github.com/yy6768/PetBackend

```

### 文件夹说明

详情请阅读Architecture.md

```
PetBackend
│
├── web 
│
├── video (视频流传输)
│
├── chat (聊天)
│
├── 3d(3d模型微服务)
│
└── pom.xml -- 父POM文件，管理项目依赖和模块

```

```
backend
├─config（配置类）
├─controller （外观接口）
├─pojo （数据库表对象）
└─service （service接口）
  ├─impl  （service实现）
```



### 开发的架构

请阅读[ARCHITECTURE.md]() 查阅为该项目的架构。

### 部署



### 使用到的框架/依赖

- Spring  Cloud 微服务
- Spring Web
- Spring security + Jwt token实现
- Mybatis Plus
- Mysql 8.0

### 如何贡献

请阅读**CONTRIBUTING.md** 查阅为该项目做出贡献的开发者。

贡献使开源社区成为一个学习、激励和创造的绝佳场所。你所作的任何贡献都是**非常感谢**的。

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
