/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yalexin.rblog.intercepors.AdminLoginInterceptor;
import top.yalexin.rblog.intercepors.AdminTryLoginLogInterceptor;
import top.yalexin.rblog.intercepors.TestSessionInterceptor;

@Configuration
public class WebConfigure implements WebMvcConfigurer {


    // 配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 未登录时候，符合 /admin/** 的 url 都不能访问
        // 当然要排除一些情况，即登录页面可以访问，获取验证码和提交验证码的页面也可以访问
        registry.addInterceptor(new AdminLoginInterceptor()).
                addPathPatterns("/admin/**").
//                excludePathPatterns("/admin/verifyCode").
                excludePathPatterns("/admin/powConfig").
                excludePathPatterns("/admin/powVerify").
                excludePathPatterns("/admin/login");

        registry.addInterceptor(new TestSessionInterceptor()).
                addPathPatterns("/**");

        // 记录下尝试登录的情况
        registry.addInterceptor(new AdminTryLoginLogInterceptor()).
//                addPathPatterns("/admin/verifyCode").
                addPathPatterns("/admin/powConfig").
                addPathPatterns("/admin/powVerify").
                addPathPatterns("/admin/login");
    }
}
