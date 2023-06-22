package com.sxdzsoft.easyresource.config;

import com.sxdzsoft.easyresource.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author wujian
 * @Date 2022/4/25 15:19
 * @Version 1.0
 **/
@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private FailureHandler failuerHandler;
    @Autowired
    private MyUserDetailsService userSecurityDetailService;
    @Autowired
    private MySecurityFilter mySecurityFilter;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private PasswordEncoder myPasswordEncoder;
    @Autowired
    private SessionOutHandler sessionOutHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityDetailService).passwordEncoder(myPasswordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置访问控制
        http.formLogin().loginPage("/signIn").loginProcessingUrl("/login").failureHandler(failuerHandler).successHandler(loginSuccessHandler).permitAll()
                .and().logout().logoutSuccessUrl("/signIn").deleteCookies("JSESSIONID").permitAll()
                .and().exceptionHandling().accessDeniedHandler(myAccessDeniedHandler).authenticationEntryPoint(new AjaxAuthenticationEntryPoint("/signIn"))
                .and().sessionManagement().invalidSessionStrategy(sessionOutHandler).maximumSessions(1).expiredUrl("/signIn").and()
                .and().csrf().and().cors().and().headers().frameOptions().sameOrigin().httpStrictTransportSecurity().disable()
                .and().authorizeRequests().antMatchers("/third/**").permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
        //配置过滤器
        http.addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class);

    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**/*.js","/**/*.css","/**/*.txt","/**/*.png","/**/*.gif","/**/*.jpg"
                        ,"/**/*.font","/**/*.json","/**/*.html","/**/*.woff2","/**/*.mp4","/**/*.swf","/websocket/**"
                        , "/queryCampusNewsHandlerAll","/queryClazzById","/getphoto","/readImage","/getNews","/getNewsOne","/queryByClazzId"
                        ,"/getCoursePresentations","/schoolNoticeFirst","/getDutyRosterOption","/queryAllClazzByShow","/queryAllDeviceByShow"
                        ,"/queryAllNotice");
    }


}
