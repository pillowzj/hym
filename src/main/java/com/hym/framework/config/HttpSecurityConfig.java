package com.hym.framework.config;


import com.hym.framework.security.handle.AuthenticationEntryPointImpl;
import com.hym.framework.security.handle.LogoutSuccessHandlerImpl;
import com.hym.framework.security.mobile.MobileAuthenticationFilter;
import com.hym.framework.security.mobile.MobileAuthenticationManager;
import com.hym.framework.security.mobile.MobileJwtAuthenticationTokenFilter;
import com.hym.framework.security.mobile.handler.MobileAuthenticationSuccessHandler;
import com.hym.framework.security.opeinid.WxAuthenticationFilter;
import com.hym.framework.security.opeinid.WxAuthenticationManager;
import com.hym.framework.security.opeinid.WxJwtAuthenticationTokenFilter;
import com.hym.framework.security.opeinid.handler.WxAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

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

    @Configuration
    @Order(1)
    public class WxWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private WxJwtAuthenticationTokenFilter wxJwtAuthenticationTokenFilter;
        @Autowired
        private WxAuthenticationManager wxAppletAuthenticationManager;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // CRSF禁用，因为不使用session
            http.csrf().disable()
                    // 认证失败处理类
                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    // 基于token，所以不需要session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    // 过滤请求
                    .authorizeRequests()
                    // 对于登录login 验证码captchaImage 允许匿名访问
                    .antMatchers("/**/getVerifyCode").anonymous()
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
            http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
            // 使用wxAuthenticationFilter替换默认的认证过滤器UsernamePasswordAuthenticationFilter
            http.addFilterAt(wxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    // 在wxAuthenticationFilter前面添加用于验证jwt，识别用户是否登录的过滤器
                    .addFilterBefore(wxJwtAuthenticationTokenFilter, WxAuthenticationFilter.class);
        }

        @Bean
        public WxAuthenticationFilter wxAuthenticationFilter() {
            WxAuthenticationFilter wxAuthenticationFilter = new WxAuthenticationFilter("/**/getOpenid");
            wxAuthenticationFilter.setAuthenticationManager(wxAppletAuthenticationManager);
            wxAuthenticationFilter.setAuthenticationSuccessHandler(wxAuthenticationSuccessHandler());
            return wxAuthenticationFilter;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/**/*.js", "/**/*.html", "/**/*.css", "/fonts/**", "/images/**", "/index/**", "/medicineFile/**", "/validate/**");//释放静态资源
        }

        @Bean
        public WxAuthenticationSuccessHandler wxAuthenticationSuccessHandler() {
            return new WxAuthenticationSuccessHandler();
        }


        /**
         * 身份认证接口
         */
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }
    }

    @Configuration
    @Order(2)
    public class MobileSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private MobileJwtAuthenticationTokenFilter mobileJwtAuthenticationTokenFilter;
        @Autowired
        private MobileAuthenticationManager mobileAuthenticationManager;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // 使用wxAuthenticationFilter替换默认的认证过滤器UsernamePasswordAuthenticationFilter
            http.addFilterAt(mobileAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    // 在wxAuthenticationFilter前面添加用于验证jwt，识别用户是否登录的过滤器
                    .addFilterBefore(mobileJwtAuthenticationTokenFilter, MobileAuthenticationFilter.class);
        }

        @Bean
        public MobileAuthenticationFilter mobileAuthenticationFilter() {
            MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter("/**/mobile");
            mobileAuthenticationFilter.setAuthenticationManager(mobileAuthenticationManager);
            mobileAuthenticationFilter.setAuthenticationSuccessHandler(mobileAuthenticationSuccessHandler());
            return mobileAuthenticationFilter;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/**/*.js", "/**/*.html", "/**/*.css", "/fonts/**", "/images/**", "/index/**", "/medicineFile/**", "/validate/**");//释放静态资源
        }

        @Bean
        public MobileAuthenticationSuccessHandler mobileAuthenticationSuccessHandler() {
            return new MobileAuthenticationSuccessHandler();
        }


        /**
         * 身份认证接口
         */
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }
    }
}
