# AI 高考志愿填报顾问系统

[![Java Version](https://img.shields.io/badge/Java-17+-blue)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-yellowgreen)](LICENSE)

## 🎯 项目简介

这是一个基于 LangChain4j + Ollama 的本地化高考志愿填报 AI 问答系统，提供以下功能：

- 📚 院校信息查询（简介、录取规则、奖学金、食宿、联系方式）
- 🔥 热门专业与天坑专业分析
- 🎯 智能推荐（根据分数和省份，按冲、稳、保逻辑推荐学校和专业）
- 📅 一对一志愿指导服务预约
- 💬 流式回复 + 会话记忆

## 🛠️ 技术栈

- **后端**: Spring Boot 3.2.0 + MyBatis
- **AI 框架**: LangChain4j 0.36.2
- **本地模型**: Ollama (qwen2.5:7b)
- **向量数据库**: Redis
- **数据库**: MySQL
- **前端**: Vue 3 + Tailwind CSS

## 🚀 快速开始

### 环境要求
- JDK 17+
- Maven 3.8+
- Ollama (安装 qwen2.5:7b 和 nomic-embed-text)
- Redis
- MySQL

### 运行步骤

1.克隆项目
```bash
git clone https://github.com/你的用户名/consultants.git
cd consultants

2.配置数据库
sql
CREATE DATABASE volunteer;

3.修改配置（可选）
yaml
 application.yml 中的数据库密码

4.启动项目
bash
mvn spring-boot:run

5访问
浏览器打开: http://localhost:8080
📁 项目结构
consultants/
├── src/main/java/com/example/consultants/
│   ├── aiservice/      # AI 服务接口
│   ├── config/         # 配置类
│   ├── controller/     # 控制器
│   ├── entity/         # 实体类
│   ├── mapper/         # MyBatis Mapper
│   ├── repository/    # Redis 仓储
│   └── service/       # 业务服务
├── src/main/resources/
│   ├── content/        # PDF 文档（院校信息）
│   ├── static/        # 前端页面
│   └── system.txt     # AI 系统提示词
└── pom.xml
⚠️ 注意事项
本项目使用本地 Ollama 模型，需提前安装并下载模型
推荐使用 qwen2.5:7b 或其他支持中文的模型
Redis 用于向量存储和会话记忆
