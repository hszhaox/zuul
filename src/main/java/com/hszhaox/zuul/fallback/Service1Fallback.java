package com.hszhaox.zuul.fallback;
//
//import com.netflix.hystrix.exception.HystrixTimeoutException;
//import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Service1Fallback extends AbstractFallback implements FallbackProvider {
//
//    @Override
//    public String getRoute() {
//        return "Service-1";
//    }
//
//    @Override
//    public ClientHttpResponse fallbackResponse(String route, final Throwable cause) {
//        if (cause instanceof HystrixTimeoutException) {
//            return response(HttpStatus.GATEWAY_TIMEOUT);
//        } else {
//            return response(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//}
