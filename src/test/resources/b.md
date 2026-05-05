
今天因为一些不可抗力因素重装了系统，发现`git`环境丢失了，就不得不重新配置一下`git`，顺便把过程写下来吧。

## git下载安装

安装过程直接默认，一直`next`就可以了。

## 代码托管平台账号注册

`git`下载安装期间到第三方代码托管平台注册账户，开启一个远程仓库，记住该远程仓库的链接。

以码云为例：

![仓库截图]( http://q78fmp2j4.bkt.clouddn.com/1586182061136.png )

## 设置用户名和邮箱

在`cmd`命令行或者用安装`git`时自带的`Git bash`输入下面的命令

```shell
git config --global user.name "根据自己喜好进行设置用户名" 
git config --global user.email "自己常用的邮箱"
```

## 生成密钥

```shell
ssh-keygen -t rsa -C "这里换上你的邮箱"
```

上面这条命令在`cmd`命令行下可能会失败，用安装`git`时自带的`Git bash`就可以成功了，然后按照提示按下三次回车。

![密钥图片]( http://q78fmp2j4.bkt.clouddn.com/1586182931481.png )

## 第三方平台添加密钥

将上述生成的`/c/Users/YaleXin/.ssh/id_rsa.pub`文件中的内容复制，打开码云，在设置 -->SSH公钥处粘贴所复制的内容。

## 初始化本地仓库

在需要的地方地方打开`Git bash`，输入：

```shell
git init
```



## 将本地修改提交到远程仓库

随便修改一个文件，如新建一个`test.txt`，然后在`Git bash`输入下面的命令：

暂存更改：

```shell
git add .
```

提交更改：

```shell
git commit -m "你的提交备注"
```

连接远程仓库

```
git remote add origin 你的远程仓库地址
```

可以使用`HTTPS`或者`SSH`的方式。

从远程仓库拉取（若远程仓库没有任何数据，可以跳过这个步骤，否则输入：）

```shell
git pull
```

推送到远端

```shell
git push -u origin master
```

上面的命令是第一次提交的时候使用的命令，假如是非第一次，直接使用`git push`即可