package com.chen.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 通常情况下进行网关自定义过滤器时，需要实现两个接口：global Filter，ordered(指定过滤器的执行顺序)
 */
@Component
public class BlackListFilter implements GlobalFilter, Ordered {
    /**
     * 加载黑名单列表
     * mysql-》redis-》加载到内存
     */
    private static List<String> blackList = new ArrayList<>();
    static {
        blackList.add("127.0.0.1");
    }

    /**
     * 过滤器核心逻辑
     * @param exchange:封装了request和response
     * @param chain 网关过滤器链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求和响应
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取来访者IP地址
        String clientIP = request.getRemoteAddress().getHostString();
        //判断是否在黑名单中
        if (blackList.contains(clientIP)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            String data = "request be denied";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
