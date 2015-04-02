# FastAndroid
这是一个集成了多个开源项目后,进行整合形成的Android快速开发框架。

主要功能有：网络访问、上传下载、数据库操作、图片加载、异常捕获、View注解等等

融入了MVP模式,将Activity或Fragment做为View层、抽象出Presenter用于处理业务逻辑、Model处理数据逻辑。
降低View和Model的耦合度

用到的开源项目有：

网络访问：Volley、Xutils网络模块(HttpUtils)

数据库：GreenDao

图片加载：Universal-image-loader

View注解：Butterknife

Json解析：Gson


项目说明：

1.本项目在Volley源码中添加了关于Cookie的管理,Xutils-HttpUtils暂不支持,如有需求请自行添加

2.在项目中自定义了Header视图,可自定义左右按钮,点击事件以及标题

关于作者Hunter：本项目的主旨只是为了给Android开发入门者提供一些开源项目的使用方法,指出一个大致的方向
如果有什么建议,可以发邮件到 hunter-android@163.com 或 QQ 381959281联系作者

