# OpenRUM-collector-exporter
## 一、快速上手
collector的exporter支持插件化,使用java的SPI实现,支持自定义扩展exporter,步骤如下
### 1.工程引入依赖
自定义的exporter引入export接口依赖
```
<dependency>
<groupId>com.openrum.collector</groupId>
<artifactId>exporter-core</artifactId>
<version></version>
</dependency>
```
[源码位置](https://github.com/RealUserMonitoring/OpenRUM-collector)
### 2.实现接口
实现自己想扩展的接口，例如Exporter
### 3.增加services文件
resources目录下，META-INF/services/目录里同时创建一个以服务接口命名的文件。该文件里就是实现该服务接口的具体实现类，可多个
### 4.打包
将自定义的exporter打包,collector引入此依赖，或启动包放入此jar
### 5.修改配置
collector-core中application.yml文件，修改clients,增加自定义实现的完整类名
```
#导出的相关设置
exporter.batch:
  send:
    sendSize: 2
    sendTimeInMilliseconds: 20000
    retryTimes: 1
    url: 
    clients:
      - com.openrum.collector.exporter.impl.HttpExporter
```
