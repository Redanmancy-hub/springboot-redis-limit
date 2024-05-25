使用Redis+Lua+AOP+反射+自定义注解实现自定义注解限流框架组件


一：自定义限流注解：
 * 1.使用@interface关键字
 * 2.定义注解包含的类型元素
 * 3.@Target：用来限定自定义注解能够被应用在哪些Java元素上面的（METHOD：方法上）
 * 4.@Retention：用来修饰自定义注解的生命力
 * 5.@Documented：是被用来指定自定义注解是否能随着被定义的java文件生成到JavaDoc文档当中

二：编写Lua脚本（保证原子性）

![image](https://github.com/Redanmancy-hub/springboot-redis-limit/assets/120354668/f55b25fb-417d-42ff-a81d-427b9e49f3e2)


三：将lua脚本注入到容器

![image](https://github.com/Redanmancy-hub/springboot-redis-limit/assets/120354668/b1629a87-7313-4966-b0c8-0ec7e1d4e652)


四：AOP配置：
 * 1.@Aspect:把当前类标识为一个切面来供容器读取
 * 2.配置前置增强：@Before：使用redisTemplate来执行Lua脚本
 * 3.生成一个用于限流的唯一键（key）

五：反射：对于任意一个对象，都能够调用它的任意一个方法和属性
![image](https://github.com/Redanmancy-hub/springboot-redis-limit/assets/120354668/3e66f8d5-1e02-4b6d-913a-c4c430fdd13d)

