package com.hym.framework.config;

import com.hym.framework.security.handle.AuthenticationEntryPointImpl;
import com.hym.framework.security.handle.LogoutSuccessHandlerImpl;
import com.hym.framework.security.mobile.MobileAuthenticationFilter;
import com.hym.framework.security.mobile.MobileAuthenticationProvider;
import com.hym.framework.security.mobile.handler.MobileAuthenticationSuccessHandler;
import com.hym.framework.security.opeinid.WxAuthenticationFilter;
import com.hym.framework.security.opeinid.WxAuthenticationProvider;
import com.hym.framework.security.opeinid.handler.WxAuthenticationSuccessHandler;
import com.hym.framework.security.pwd.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security配置
 *
 * @author hym
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CRSF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                .antMatchers("/**/getVerifyCode").permitAll()
                .antMatchers("/**/mobile", "/**/getOpenid").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity
                .addFilterBefore(wxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(mobileAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        System.out.println("SecurityConfig----------------> JwtAuthenticationTokenFilter()");
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public MobileAuthenticationFilter mobileAuthenticationFilter() {
        System.out.println("SecurityConfig----------------> mobileAuthenticationFilter()");
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter("/**/mobile");
        mobileAuthenticationFilter.setAuthenticationManager(authenticationManager);
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(mobileAuthenticationSuccessHandler());
        return mobileAuthenticationFilter;
    }

    @Bean
    public WxAuthenticationFilter wxAuthenticationFilter() {
        System.out.println("SecurityConfig----------------> wxAuthenticationFilter()");
        WxAuthenticationFilter wxAuthenticationFilter = new WxAuthenticationFilter("/**/wx");
        wxAuthenticationFilter.setAuthenticationManager(authenticationManager);
        wxAuthenticationFilter.setAuthenticationSuccessHandler(wxAuthenticationSuccessHandler());
        return wxAuthenticationFilter;
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                authenticationProvider(wxAuthenticationProvider())
                .authenticationProvider(mobileAuthenticationProvider());
    }

    @Bean
    WxAuthenticationProvider wxAuthenticationProvider() {
        return new WxAuthenticationProvider();
    }

    @Bean
    MobileAuthenticationProvider mobileAuthenticationProvider() {
        return new MobileAuthenticationProvider();
    }

    @Bean
    public MobileAuthenticationSuccessHandler mobileAuthenticationSuccessHandler() {
        return new MobileAuthenticationSuccessHandler();
    }

    @Bean
    public WxAuthenticationSuccessHandler wxAuthenticationSuccessHandler() {
        return new WxAuthenticationSuccessHandler();
    }

}
