# 基于注解、AOP实现的验证框架 Gateway-validation

## 说明
- 配置相关注解完成bean验证
- 支持字符串，整数，长整形，浮点型，枚举，对象，数字，电话号码，ip地址，日期格式，bool值，邮件校验，并支持基本数据类型的正则匹配
- 支持全局自定义配置签名，每个方法自定义签名校验
- 减少编码量
- 每个方法的入参不能重用
- 枚举校验需要按照一定规则实现，如：

```java
public enum GenderType  {

    FEMALE(0, "女"),
    MALE(1, "男"),
    UNKNOWN(-1, "未知");


    private int key;
    private String value;
    ...

```


## Spring AOP配置

```xml
<!--注入AOP的beans-->
<bean name="validatorAspect" class="com.keruyun.gateway.validation.annotation.aop.ValidatorAspect">
    <!-- 签名模式注入，全局模式，value可以配置属性文件-->
    <!--<property name="defaultSignModeType" value="{properties.signModel}"></property>-->
</bean>
```

## ResponseExceptionHandler实现

```java
/**
 * 重写ResponseEntityExceptionHandler异常处理返回Response对象
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Response<Object> response = new Response<>();
        if (ex instanceof ValidationException) { // 参数校验异常
            this.logger.warn("validate exception type: " + ex.getClass().getName());
            response = ResponseUtils.getResponse((ValidationException) ex, response);
        } else if (ex instanceof ServiceException) { // 业务处理异常
            this.logger.warn("service exception type: " + ex.getClass().getName());
            response = ResponseUtils.getResponse((ServiceException) ex, response);
        } else {
            return super.handleException(ex, request);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, response, headers, HttpStatus.OK, request);
    }
}
```

## javabean字段配置

```java
/**
 * 用户名字符校验，正则匹配
 */
@ValidateProperty(regexType = RegexType.CHARACTER,maxLength = 1,minLength = 100,regexExpression = "正则匹配")
private String username;
/**
 * 分数，RegexUtils 该类总结常用的正则表达式
 */
@ValidateProperty(regexType = RegexType.INT,regexExpression = RegexUtils.NONNEGATIVE_INT_REGEX)
private Integer sorce;
/**
 * 性别，枚举校验
 */
@ValidateProperty(regexType = RegexType.ENUM,clazz = GenderType.class)
//也可以正则匹配
//@ValidateProperty(regexType = RegexType.INT,lenth=1,regexExpression = "[0,1]|-1")
private Integer gender;
/**
 * 电话号码校验,length=13 定长校验
 */
@ValidateProperty(regexType = RegexType.CHARACTER,length = 13,regexExpression = RegexUtils.MOBILE_NO_REGEX)
private String phone;
/**
 * 对象校验，nullable=true 可空校验
 */
@ValidateProperty(nullable = true,regexType = RegexType.OBJECT,clazz = User.class)
private User user;
/**
 * 数组校验
 */
@ValidateProperty(regexType = RegexType.ARRAY,clazz = User.class)
private List<User> users;
```

## Controller配置实现

```java
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @CurlLogger // AOP 拦截日志输出
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    // Validator 验证服务 ，如果全局验证模式配置为DEBUG（不需要校验签名）模式，跳过isSign的配置。
    // 如果全局签名模式为RUN（需要校验签名）模式，isSign配置为true 需要签名，为false 不需要签名，便于联调
    @Validator(isSign = false)
    public Response user(@RequestBody
            @ValidateMapping Request<User> request//ValidateMapping 验证映射注解 Request 是我实现一个泛型入参，加入签名验证
            )throws ValidationException, ServiceException {// 异常抛出  ValidationException，ServiceException 被自定义ResponseExceptionHandler 拦截处理
        return userService.user(request);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(UserController.class, args);
    }
}

```




