# 智能编程辅导系统

基于 **Spring Boot + Vue 3** 的前后端分离项目，集成 **智谱 AI（GLM）** 大模型，为编程初学者提供：

- 智能代码解释
- 错误诊断与修正建议
- 个性化练习题生成

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 4、RestClient、Validation |
| 前端 | Vue 3、Vite、Marked |
| 大模型 | [智谱 AI 开放平台](https://open.bigmodel.cn/)（OpenAI 兼容） |

## 项目结构

```
dazuoye/
├── src/main/java/          # Spring Boot 后端
├── frontend/               # Vue 3 前端
├── .env.example            # 环境变量示例
└── pom.xml
```

## 快速开始

### 1. 配置 API Key

在 [智谱开放平台](https://open.bigmodel.cn/) 获取 API Key，然后设置环境变量：

**Windows PowerShell：**

```powershell
$env:ZHIPUAI_API_KEY="你的API密钥"
```

**IDEA：** Run → Edit Configurations → Environment variables：

```
ZHIPUAI_API_KEY=你的API密钥
```

**Linux / macOS：**

```bash
export ZHIPUAI_API_KEY="你的API密钥"
```

> 请勿将 API Key 提交到 Git 仓库。

### 2. 启动后端

```bash
cd dazuoye
.\mvnw.cmd spring-boot:run
```

后端默认运行在 `http://localhost:8080`

### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

也可以在项目根目录执行（需先 `cd frontend && npm install` 安装依赖）：

```bash
npm run dev
```

前端默认运行在 `http://localhost:5173`，已通过 Vite 代理转发 `/api` 到后端。

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/health` | 健康检查 |
| POST | `/api/explain` | 代码解释 |
| POST | `/api/diagnose` | 错误诊断 |
| POST | `/api/practice/generate` | 生成练习题 |

### 示例：代码解释

```bash
curl -X POST http://localhost:8080/api/explain \
  -H "Content-Type: application/json" \
  -d "{\"language\":\"Java\",\"code\":\"System.out.println(1);\",\"level\":\"beginner\"}"
```

## 生产构建

```bash
# 前端
cd frontend && npm run build

# 后端
mvn -DskipTests package
```

可将 `frontend/dist` 部署到 Nginx，后端独立运行在 8080 端口。

## 配置项

`src/main/resources/application.properties`：

| 配置 | 默认值 | 说明 |
|------|--------|------|
| `zhipu.api.base-url` | `https://open.bigmodel.cn/api/paas/v4` | 智谱 API 地址 |
| `zhipu.api.key` | `${ZHIPUAI_API_KEY}` | API 密钥（环境变量） |
| `zhipu.api.model` | `glm-4-flash` | 模型名称（可改为 glm-4-plus 等） |
| `zhipu.api.max-tokens` | `2048` | 最大输出 token |
| `zhipu.api.timeout-seconds` | `90` | 请求超时秒数 |
