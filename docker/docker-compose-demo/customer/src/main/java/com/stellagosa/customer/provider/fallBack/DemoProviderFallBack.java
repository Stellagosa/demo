package com.stellagosa.customer.provider.fallBack;

import com.stellagosa.customer.provider.DemoProvider;
import org.springframework.stereotype.Component;

@Component
public class DemoProviderFallBack implements DemoProvider {

    @Override
    public String hello() {
        return "error!!!";
    }
}
