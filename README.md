## rblog
基于`Spring Boot`的前后端分离博客系统后端服务，前端[【仓库地址】](https://github.com/YaleXin/rblogv2)

## 特性
- 邮件通知，支持管理员后台登录邮件提醒、评论提醒和回复提醒。
- 后端渲染 Markdown ，安全输出 HTML
- 后台访问 IP 记录，便于事后排查。
- 基于 PoW 机制的验证码，用户无感验证，提高用户体验。
- 支持 Docker-compose 部署，快速启动。


## 创建管理员方式
由于本系统并没有考虑多人使用，因此也就没有开放用户注册功能，因此管理员要手动插入到数据库中，不过这里的密码是经过两次`md5`加密的，也就是说存到数据库中的密文是：
```
pass = md5(md5(pass))
```
`md5`加密的话网上有很多在线网站，这里就不演示了

## 开启 Sentinel 控制台
```shell
cd lib
java -Dserver.port=8085 -Dcsp.sentinel.dashboard.server=localhost:8085 -Dproject.name=blog-sentinel-dashboard -jar sentinel-dashboard-1.8.0.jar
```
## 涉及的技术或者项目

- [`Spring Booot 2.4.2 `]( https://spring.io/projects/spring-boot )
- [`MySql 5.6`]( https://www.mysql.com/ )
- [`MyBatis`](https://mybatis.net.cn/)
- [`Docker`](https://www.docker.com/)