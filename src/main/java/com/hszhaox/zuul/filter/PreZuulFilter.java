package com.hszhaox.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hszhaox.zuul.common.R;
import com.hszhaox.zuul.utils.ReturnUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PreZuulFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(PreZuulFilter.class);
    private ObjectMapper om = new ObjectMapper();

    public void goBack(R r) {
        log.error(r.getMsg());
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        // 设置字符集
        response.setCharacterEncoding("utf-8");
        // 设置相应格式
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(401);
        // 不进行路由
        ctx.setSendZuulResponse(false);
        try {
            // 响应体
            response.getWriter().write(om.writeValueAsString(r));
        } catch (IOException e) {
            log.error("response io异常");
            e.printStackTrace();
        }
        ctx.setResponse(response);
    }

    @Override
    public Object run() {
        RequestContext rtx = RequestContext.getCurrentContext();
        HttpServletRequest request = rtx.getRequest();

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            goBack(ReturnUtil.create(-110, "token不能为空"));
            return null;
        }
        String info = String.format("send %s request to %s", request.getMethod(), request.getRequestURL().toString());
        PreZuulFilter.log.info(info);
        return null;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext rtx = RequestContext.getCurrentContext();
        HttpServletRequest request = rtx.getRequest();
        String url = request.getRequestURL().toString();
        List<String> exceptUrls = new ArrayList<>();
        //登陆页面不拦截
        exceptUrls.add("/login");
        //上传的资源不拦截
        exceptUrls.add("/upload/");
        //静态资源不拦截
        exceptUrls.add("/static/");
        //token相关操作不拦截
        exceptUrls.add("/auth/token");
        exceptUrls.add("/auth/token/json");
        exceptUrls.add("/auth/token/username");
        exceptUrls.add("/auth/token/refresh");
        exceptUrls.add("/auth/token/validate");
        //获取验证码不拦截
        exceptUrls.add("/auth/captcha");
        // 暂时全通过
        exceptUrls.add("/ai-reading-token");
        exceptUrls.add("/ai-reading-token-dev");
        exceptUrls.add("/ai-reading-token-test");
        exceptUrls.add("/ai-reading-token-prod");
        exceptUrls.add("/ai-reading-user");
        exceptUrls.add("/ai-reading-user-dev");
        exceptUrls.add("/ai-reading-user-test");
        exceptUrls.add("/ai-reading-user-prod");
        exceptUrls.add("/ai-engine");
        exceptUrls.add("/ai-engine-dev");
        exceptUrls.add("/ai-engine-test");
        exceptUrls.add("/ai-engine-prod");
        exceptUrls.add("/ai-engine-recommend-dev");
        exceptUrls.add("/ai-engine-recommend-test");
        exceptUrls.add("/ai-engine-recommend-prod");
        exceptUrls.add("/ai-engine-correct-dev");
        exceptUrls.add("/ai-engine-correct-test");
        exceptUrls.add("/ai-engine-correct-prod");
        exceptUrls.add("/hello");
        Boolean hasMatched = false;
        for (String str : exceptUrls) {
            if (url.indexOf(str) > 0) {
                hasMatched = true;
                break;
            }
        }
        if (hasMatched) {
            return false;
        }
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return "pre";
    }

}
