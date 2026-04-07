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

1. 克隆项目
```bash
git clone https://github.com/你的用户名/consultants.git
cd consultants
