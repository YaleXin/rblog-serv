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
        registry.addInterceptor(new AdminLoginInterceptor()).
                addPathPatterns("/admin/**").
                excludePathPatterns("/admin/verifyCode").
                excludePathPatterns("/admin/login");
        registry.addInterceptor(new TestSessionInterceptor()).
                addPathPatterns("/**");

        registry.addInterceptor(new AdminTryLoginLogInterceptor()).
                addPathPatterns("/admin/verifyCode").
                addPathPatterns("/admin/login");
    }
}
