package com.stellagosa.customer.provider;

import com.stellagosa.customer.provider.fallBack.DemoProviderFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "provider", path = "/provider", fallback = DemoProviderFallBack.class)
public interface DemoProvider {

    @GetMapping("/hello")
    String hello();

}
