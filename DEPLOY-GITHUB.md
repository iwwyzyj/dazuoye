# 用 GitHub 源码部署到 Sealos

Sealos「应用部署」页面**不能直接填 GitHub 链接**，但可以用这条链路：

```
GitHub 推送代码 → GitHub Actions 自动构建镜像 → Sealos 填写镜像地址部署
```

你**不需要在本地安装 Docker**。

---

## 第一步：触发 GitHub 自动构建

仓库已配置 `.github/workflows/docker-build.yml`。

1. 打开 https://github.com/iwwyzyj/dazuoye/actions
2. 若第一次使用，点 **I understand my workflows, go ahead and enable them**
3. 点 **Build Docker Images** → **Run workflow** → **Run workflow**

或随便改一行代码 push 到 `main` 分支，会自动构建。

构建成功后，镜像地址为：

| 应用 | 镜像地址 |
|------|----------|
| 后端 | `ghcr.io/iwwyzyj/dazuoye-backend:latest` |
| 前端 | `ghcr.io/iwwyzyj/dazuoye-frontend:latest` |

---

## 第二步：把镜像设为公开（重要）

GitHub 容器镜像默认可能是私有的，Sealos 拉取会失败。

1. 打开 https://github.com/iwwyzyj?tab=packages
2. 分别进入 `dazuoye-backend`、`dazuoye-frontend`
3. **Package settings** → **Change visibility** → **Public**

---

## 第三步：Sealos 部署后端

| 字段 | 填写 |
|------|------|
| 应用名称 | **`dazuoye-backend`** |
| 镜像源 | 公共 |
| 镜像名 | `ghcr.io/iwwyzyj/dazuoye-backend:latest` |
| CPU | 0.5 ~ 1 核 |
| 内存 | 512M ~ 1G |
| 容器暴露端口 | **8080** |
| 开启公网访问 | 关闭 |

**高级配置 → 环境变量：**

```
ZHIPUAI_API_KEY = 你的智谱密钥
```

部署应用。

---

## 第四步：Sealos 部署前端

| 字段 | 填写 |
|------|------|
| 应用名称 | `dazuoye-frontend` |
| 镜像名 | `ghcr.io/iwwyzyj/dazuoye-frontend:latest` |
| CPU | 0.1 ~ 0.2 核 |
| 内存 | 128M ~ 256M |
| 容器暴露端口 | **80** |
| 开启公网访问 | **打开** |

部署后，用公网地址访问网站。

---

## 以后更新代码

```powershell
git add .
git commit -m "update"
git push
```

GitHub Actions 会自动重新构建镜像。Sealos 里 **重启应用** 或 **重新部署** 即可拉取最新 `latest` 镜像。

---

## 故障排查

### 前端日志：`host not found in upstream "dazuoye-backend"`

原因：Nginx 启动时后端尚未部署，或后端应用名不是 `dazuoye-backend`。

处理：
1. 先部署并确保 **dazuoye-backend** 状态为 Running
2. 后端应用名必须 exactly 为 `dazuoye-backend`（与 nginx 配置一致）
3. 更新前端镜像后重新部署（已修复 nginx 启动依赖）


若你的 Sealos 控制台有 **DevBox** 入口：

1. 新建 DevBox，选择 **从 GitHub 导入**
2. 仓库填 `https://github.com/iwwyzyj/dazuoye`
3. 在云端开发环境运行，适合调试，不适合直接对外提供生产服务

日常对外访问，仍推荐上面的 **GitHub Actions + 应用部署** 方式。
