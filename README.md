# 项目简介
基于SpringBoot实现的在线教育项目，采用B2C的商业模块，使用微服务架构，项目采用前后端分离技术；项目分为前台用户系统和后台管理系统。
# 主要技术
后端的主要技术架构是：SpringBoot + SpringCloud + MyBatis-Plus + HttpClient + MySQL + Maven+EasyExcel+ nginx
前端的架构是：Node.js + Vue.js +element-ui+NUXT+ECharts
其他涉及到的中间件包括Redis、阿里云OSS、阿里云视频点播
业务中使用了ECharts做图表展示，使用EasyExcel完成分类批量添加、注册分布式单点登录使用了JWT
项目前后端分离开发，后端采用SpringCloud微服务架构，持久层用的是MyBatis-Plus，使用Swagger生成接口文档
接入了阿里云视频点播、阿里云OSS。
系统分为前台用户系统和后台管理系统两部分。
前台用户系统包括：首页、课程、名师、问答、文章。
后台管理系统包括：讲师管理、课程分类管理、课程管理、统计分析、Banner管理、订单管理、权限管理等功能。
