
#  Author:连晋    (暂未开发完毕,等后续更新吧 有兴趣一起开发的可以 互加 weChat 搜索 832192 或者扫码)

![](/mdFile/img/weChat.jpg ){:height="300px" width="389px"}

## 基于 springBoot/mybatis/mysql/redis 开发的 一个 权限校验系统 支持 分布式 系统

- 项目暂未开发完毕 暂时先上传部分代码


- 有任何可以优化或者修改的地方 欢迎诸位提出宝贵的意见

## 主动日志 监控插入数据库  使用方法

- 适用范围 
    在所有需要 进行日志监控 以便后续可复查 是 何人进行 操作的  敏感功能 所做日志访问记录
    
- 使用方法
    在所需监控的 Controller 下的 接口方法上  写明 @SystemControllerLog() 注解
    
    其中  actionType 为接口功能描述  descrption 为 操作类型  新增/修改/删除/打印/导出 


## 在线日志系统 

- 访问地址 部署好 ELK 服务器以后  配置 logback-spring.xml 中 redis的服务器信息

- 并且打开 name为 REDIS_CONSOLE 的  appender     

- ELK服务的搭建  之前 本人有出过视频教程  

 [点我传送到腾讯云下载观看](https://share.weiyun.com/5aY4ErT)
  
- 然后访问 ELK服务器ip地址:5601  即可

[点我快速打开http://127.0.0.1:5600](http://127.0.0.1:5600)


## Swagger2 在线api文档使用说明 

- swagger2 的UI 本人已经做过修改
    - 汉化UI界面
    
    - 增加 api地址一键复制
    
    - 修复 js解析node节点与项目中 bean或dto 字段冲突时 出现的 bug

## api接口 swagger2 常用注解的使用


### 本地预览接口地址

- 如何访问 api接口地址 

    `http://域名:端口/swagger-ui.html`

    [点我快速打开 http://localhost:8083/swagger-ui.html ](http://localhost:8083/swagger-ui.html)
    
    前端人员开发对接的时候 请把 域名改成 开发人员电脑的IP
 
- 如何配置 自动生成 的 api文档包扫描 范围

  打开 `com.easy.auth.Swagger2`  类
  修改 `RequestHandlerSelectors.withClassAnnotation(io.swagger.annotations.Api.class)` 
  中的  `io.swagger.annotations.Api.class` 
  为你需要开放给前端对接人员访问的控制层 所在包 的字符串 即可
  
  也可 改成  `io.swagger.annotations.Api.class` 但是这样会全局扫描   需要时间很久  不推荐这样做 
  
--------

- 常用注解类型

    ![](/mdFile/img/APILIST.png)



--------


- 更多 详细的 图文描述 教程

[点我阅读](http://note.youdao.com/noteshare?id=d453d4d095b4efd0f7eeb0b650332a0f)

--------

 **列子** 
``` java

@RestController
@Api("测试api")
public class UserController {

    // 创建线程安全的Map
    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<Integer, User>());

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserById (@PathVariable(value = "id") Integer id){
       ...
        return ResponseEntity.ok(r);
    }

    /**
     * 查询用户列表
     * @return
     */
    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList (){
  ...
        return ResponseEntity.ok(r);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add (@RequestBody 
     user){
        JsonResult r = new JsonResult();
...
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Integer id){
...
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @ApiOperation(value="更新信息", notes="根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update (@PathVariable("id") Integer id, @RequestBody User user){
...
        return ResponseEntity.ok(r);
    }

    @ApiIgnore//使用该注解忽略这个API
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String  jsonTest() {
        return " hi you!";
    }



```




### Guides
The following guides illustrates how to use certain features concretely:

* [Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)





