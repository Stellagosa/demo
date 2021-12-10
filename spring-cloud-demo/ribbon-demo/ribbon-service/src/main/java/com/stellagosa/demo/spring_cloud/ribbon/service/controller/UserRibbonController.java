package com.stellagosa.demo.spring_cloud.ribbon.service.controller;

import com.stellagosa.demo.spring_cloud.ribbon.service.common.CommonResult;
import com.stellagosa.demo.spring_cloud.ribbon.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 16:32
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserRibbonController {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @GetMapping("/{id}")
    public CommonResult getUser(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @GetMapping("/getByUsername")
    public CommonResult getByUsername(String username) {
        return restTemplate.getForObject(userServiceUrl + "/getByUsername?username={1}", CommonResult.class, username);
    }

    @GetMapping("/getEntityByUsername")
    public CommonResult getEntityByUsername(String username) {
        ResponseEntity<CommonResult> entity = restTemplate
                .getForEntity(userServiceUrl + "/getByUsername?username={1}", CommonResult.class, username);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult(500, "操作失败");
        }
    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/create", user, CommonResult.class);
    }

    @PutMapping("/update")
    public CommonResult update(@RequestBody User user) {
        restTemplate.put(userServiceUrl + "/user/update", user);
        return new CommonResult(200, "操作成功");
    }
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Integer id) {
        restTemplate.delete(userServiceUrl + "/user/{1}", id);
        return new CommonResult(200, "操作成功");
    }

}
