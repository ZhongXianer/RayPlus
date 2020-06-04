# RayPlus 项目介绍

## 开发环境

Android Studio 

[下载地址](https://developer.android.com/studio)



## 开发语言

Kotlin，这是一种和Java一样可以运行在JVM上的语言。不过相比与Java来说，增加了很多现代语言的特性，解决的一些Java里开发时遇到的痛点。

[语言教程](https://developer.android.com/studio)

[Kotlin开发优势](https://www.viseator.com/2018/07/11/introduce_kotlin/)



## 项目架构

整个项目是基于MVVM的架构来进行构建的，以下会做一个概要介绍和每个包的详细介绍：

![MVVM架构图](https://cdn.journaldev.com/wp-content/uploads/2018/04/android-mvvm-pattern.png)

以上就是MVVM的架构图，总体来说分为三个部分，View，ViewModel，和Model。他们的分工明确，View层主要控制整个视图的显示，接收用户的各种触摸点击事件并触发响应行为，以及监听来自ViewModel层的数据流，当数据流发生变化时进行页面的刷新以保证展示最新的数据。Model层主要负责数据的请求和发送，即跟数据库或者是网络请求直接交换数据。ViewModel层主要是负责连接Model层和View层，这样可以让View层和Model层进行解耦。Model获取的数据会传递给ViewModel然后经过处理传递给View层，View层的数据也要经过ViewModel层才能传递给Model。这样无论是视图还是数据结构发生变化，只需要对ViewModel层进行简单的修改就可以适配，增强了代码的健壮性。



下面会详细介绍架构里的每个包里的代码类的详细功能和职责：

![代码整体架构](https://ftp.bmp.ovh/imgs/2020/06/b0c9b6ff376eec90.png)

整个项目的包一共分为以下这几个：

#### data

##### bean

这个包里包含的就是一些基础的数据类，即Java的POJO类，主要用于承载与服务端交换时的数据。可以使用框架来进行类对象和json数据之间的互转。

##### source

这个包里主要是一些我们的数据来源。即数据请求的源头，通常分为两类，本地数据库的数据，和网络服务端的数据。

###### local

这个主要是我们本地数据库的数据，但是因为本项目不涉及本地数据库。所以这个类里没有文件。

###### remote

这个主要是远程网络数据的请求类，他调用网络库来进行请求，并把返回的JSON数据转换成为Bean类对象，提供给我们获取和使用。



#### network

这个包里主要是我们网络框架的引入和基本配置，例如超时时间，日志打印。主要提供给data-source-remote里的类进行调用进行网络请求。



#### ui

这个包里主要就是和我们的视图相关的类

##### activity

这是Android开发里的页面，每一个界面都需要一个Activity来进行数据展示和各种触摸点击事件的监听，它和我们的界面是一一对应的，每一个界面都有一个对应的Activity。

##### adapter

这个包里的内容主要和列表相关，我们的项目里包含着大量的列表类。而这个包里的各种类就是负责把一个List列表对象中的各种数据都转化并填充到我们的ListView的列表视图中去。

##### fragment

我们很多界面都包含着子界面，我们可以在界面中通过滑动或者是点击Tab来进行子界面的切换，这个包里的类就是这些子界面的实现。

##### widget

这个包里是我们自定义的一些控件，这些控件SDK和第三方库并没有提供，需要我们根据业务内容来进行定制。例如我们的可多选的弹窗。



#### util

这个包里主要是一些工具类，用于常用字符串的获取，或者是当前日期的获取等操作。



#### viewmodel

这个包里的代码类主要是我们之前在概要设计中提到的ViewModel层，主要负责将我们data-source-remote中请求返回的数据接收并传递给响应的activity或者是fragment。然后让视图来进行这些数据的展示。