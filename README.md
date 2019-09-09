# 收款码合并

## 目标

需要三合一二维码，但是发现界面清爽的平台不免费，免费的平台有广告，所以决定自己写一个无广告二维码合并功能，顺便以目标为导向，实战springboot框架。

## 原理

​	将所需要合并的二维码url存入数据库，加盐拼接两个URL，计算MD5，与服务器url拼接生成合并后的二维码。

​	根据ua检测，跳转到对应平台付款url。

```java
String agent= request.getHeader("user-agent");
		if(agent.contains("Ali")){
			//...
		} else if(agent.contains("Micro")) {
			//...
		} else if(agent.contains("QQ")){
			//...
		}else {
			//...
		}
```

（支付宝可以直接跳转，微信与QQ不能直接调用，需要将检测后得到的url重新生成一个二维码让用户长按进入付款页面，如图）

<img src=".\README_img\TIM图片20190909144731.png" style="zoom:33%;" />



## 快速开始

- **requirements**

  bootdo框架

  它是SpringBoot基础上搭建的一个Java基础开发平台，MyBatis为数据访问层，ApacheShiro为权限授权层，Ehcahe对常用数据进行缓存。

  [bootdo下载](https://gitee.com/lcg0124/bootdo)

- **Build & Run**

  在sql中建立相应的表，并修改bootdo中的配置文件中数据库相关内容，运行BootdoApplication.java后，浏览器访问localhost:端口可以看到bootdo登陆页面。

- **License**

使用MIT license，[相关信息](https://github.com/adorablealice/xxQRcode/blob/master/LICENSE)