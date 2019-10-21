# spring-zk-ha-demo

试验方法：
1. 启动一个SpringBootApplication实例，这时启动的实例是主服务，访问Controller中的amLeader方法返回true。
2. 修改application.yml中的端口号码，启动另一个SpringBootApplication实例，这时启动的服务是从服务，访问Controller中的amLeader方法返回false。
3. 直接关闭主服务，在从服务的日志中可以看到从服务监听到了这个事件并将自身转化为主服务，这时再访问其Controller中的amLeader方法返回true。
