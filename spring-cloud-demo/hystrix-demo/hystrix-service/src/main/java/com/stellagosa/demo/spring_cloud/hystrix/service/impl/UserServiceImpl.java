package com.stellagosa.demo.spring_cloud.hystrix.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.stellagosa.demo.spring_cloud.hystrix.common.CommonResult;
import com.stellagosa.demo.spring_cloud.hystrix.entity.User;
import com.stellagosa.demo.spring_cloud.hystrix.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 17:11
 * @Description:
 */

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 17:11
 * @Description: <p>
 * @HystrixCommand中的常用参数 fallbackMethod：指定服务降级处理方法；
 * ignoreExceptions：忽略某些异常，不发生服务降级；
 * commandKey：命令名称，用于区分不同的命令；
 * groupKey：分组名称，Hystrix会根据不同的分组来统计命令的告警及仪表盘信息；
 * threadPoolKey：线程池名称，用于划分线程池。
 * <p>
 * Hystrix的请求缓存
 * @CacheResult：开启缓存，默认所有参数作为缓存的key，cacheKeyMethod可以通过返回String类型的方法指定key；
 * @CacheKey：指定缓存的key，可以指定参数或指定参数中的属性值为缓存key，cacheKeyMethod还可以通过返回String类型的方法指定；
 * @CacheRemove：移除缓存，需要指定commandKey。 <p>
 * 请求合并
 * @HystrixCollapser的常用属性 batchMethod：用于设置请求合并的方法；
 * collapserProperties：请求合并属性，用于控制实例属性，有很多；
 * timerDelayInMilliseconds：collapserProperties中的属性，用于控制每隔多少时间合并一次请求；
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @HystrixCommand(fallbackMethod = "getDefaultUser")
    @Override
    public CommonResult getUser(Integer id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getUserCommand",
            groupKey = "getUserGroup", threadPoolKey = "getUserThreadPool")
    @Override
    public CommonResult getUserCommand(Integer id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser2", ignoreExceptions = {NullPointerException.class})
    @Override
    public CommonResult getUserException(Integer id) {
        if (id == 1) {
            throw new NullPointerException();
        } else if (id == 2) {
            throw new IndexOutOfBoundsException();
        } else {
            return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
        }
    }

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getUserCache")
    @Override
    public CommonResult getUserCache(Integer id) {
        LOGGER.info("getUserCache id:{}", id);
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    @Override
    public Future<User> GetUserFuture(Integer id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                CommonResult commonResult = restTemplate
                        .getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
                User user = ((User) commonResult.getData());
                LOGGER.info("getUserById username:{}", user.getUsername());
                return user;

            }
        };
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids) {
        CommonResult commonResult = restTemplate
                .getForObject(userServiceUrl + "/user/getUserByIds?ids={1}", CommonResult.class,
                        CollUtil.join(ids, ","));
        return ((List<User>) commonResult.getData());
    }

    public CommonResult getDefaultUser(@PathVariable Integer id) {
        User user = new User(-1, "defaultUser", "123456");
        return new CommonResult(200, "操作成功", user);
    }

    public CommonResult getDefaultUser2(@PathVariable Integer id, Throwable e) {
        LOGGER.info("getDefaultUser2, id:{}, throwable class:{}", id, e.getClass());
        User user = new User(-2, "defaultUser", "123456");
        return new CommonResult(200, "操作成功", user);
    }

    public String getCacheKey(Integer id) {
        return String.valueOf(id);
    }
}
