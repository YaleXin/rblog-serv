# 使用 Node.js 12 作为基础镜像
# FROM node:12.16.1
FROM node:12.16.1
MAINTAINER yalexin


# 设置工作目录
WORKDIR /app
ENV NODE_ENV=production
# 服务器公网 IP
ENV HOST_IP 39.100.87.216
ENV HOST 0.0.0.0
# 复制项目文件到容器中
COPY . .

# 安装项目依赖
RUN npm cache clean --force
RUN npm install --registry=http://registry.npm.taobao.org



ENV NODE_ENV=production
# 暴露应用所使用的端口（默认为 3000）
EXPOSE 3000

# 启动 Nuxt.js 应用
CMD ["npm", "run", "start" ]
