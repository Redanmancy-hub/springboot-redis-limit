使用Redis+Lua+AOP+反射+自定义注解实现自定义注解限流框架组件


一：自定义限流注解：
 * 1.使用@interface关键字
 * 2.定义注解包含的类型元素
 * 3.@Target：用来限定自定义注解能够被应用在哪些Java元素上面的（METHOD：方法上）
 * 4.@Retention：用来修饰自定义注解的生命力
 * 5.@Documented：是被用来指定自定义注解是否能随着被定义的java文件生成到JavaDoc文档当中
