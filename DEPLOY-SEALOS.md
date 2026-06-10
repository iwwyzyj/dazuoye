# Sealos 部署指南

推荐先看 **[DEPLOY-GITHUB.md](./DEPLOY-GITHUB.md)**：从 GitHub 推送代码，由 GitHub Actions 自动构建镜像，无需本地 Docker。

Sealos「应用部署」页面需要填写 **镜像地址**，不能直接填 GitHub 链接。

本项目需要部署 **2 个应用**：后端 + 前端。

---

## 方式 A：GitHub Actions 自动构建（推荐）

见 [DEPLOY-GITHUB.md](./DEPLOY-GITHUB.md)。

---

## 方式 B：本地 Docker 构建

安装 [Docker Desktop](https://www.docker.com/products/docker-desktop/) 后，在 PowerShell 执行：

```powershell
cd D:\程序\dazuoye

# 登录 Docker Hub（在 hub.docker.com 注册账号）
docker login

# 构建后端镜像（替换 iwwyzyj 为你的 Docker Hub 用户名）
docker build -t iwwyzyj/dazuoye-backend:latest .

# 构建前端镜像
docker build -t iwwyzyj/dazuoye-frontend:latest ./frontend

# 推送到 Docker Hub
docker push iwwyzyj/dazuoye-backend:latest
docker push iwwyzyj/dazuoye-frontend:latest
```

---

## 第二步：部署后端（先部署）

在 Sealos **应用部署** 新建应用：

### 基础配置

| 字段 | 填写 |
|------|------|
| 应用名称 | **`dazuoye-backend`**（必须与 nginx 配置一致） |
| 镜像源 | 公共 |
| 镜像名 | `iwwyzyj/dazuoye-backend:latest` |
| 部署模式 | 固定实例 |
| 实例数 | 1 |
| CPU | **0.5 ~ 1** 核 |
| 内存 | **512M ~ 1G** |

### 网络配置

| 字段 | 填写 |
|------|------|
| 容器暴露端口 | **8080** |
| 开启公网访问 | 关闭（前端通过内网访问） |

### 高级配置 → 环境变量

| 变量名 | 值 |
|--------|-----|
| `ZHIPUAI_API_KEY` | 你的智谱 API 密钥 |

点击 **部署应用**，等待 Running。

---

## 第三步：部署前端（网页入口）

再 **新建一个应用**：

### 基础配置

| 字段 | 填写 |
|------|------|
| 应用名称 | `dazuoye-frontend` |
| 镜像名 | `iwwyzyj/dazuoye-frontend:latest` |
| CPU | 0.1 ~ 0.2 核 |
| 内存 | 128M ~ 256M |

### 网络配置

| 字段 | 填写 |
|------|------|
| 容器暴露端口 | **80** |
| 开启公网访问 | **打开** |

点击 **部署应用**。

---

## 第四步：访问网站

在 `dazuoye-frontend` 应用详情里复制 **公网地址**，浏览器打开即可。

前端 Nginx 会把 `/api` 请求转发到内网 `dazuoye-backend:8080`。

---

## 当前截图里的错误配置

| 你现在的填法 | 问题 |
|-------------|------|
| 镜像名 `nginx` | 只是空 Nginx，没有你的代码 |
| 端口 `80` | 后端应使用 **8080** |
| 内存 `256M` | Spring Boot 不够用 |
| 未配环境变量 | AI 功能无法调用 |

---

## 没有 Docker Hub？

也可使用阿里云容器镜像服务、Harbor 等，把镜像名改成对应仓库地址即可。
