package com.easy.auth.infrastructure.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * Druid的DataResource配置类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig implements EnvironmentAware {
    @Value("${spring.datasource.url}")
    private String myUrl;

    @Value("${spring.datasource.driverClassName}")
    private String driver_class_name;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.initial-size}")
    private String initial_size;

    @Value("${spring.datasource.min-idle}")
    private String min_idle;

    @Value("${spring.datasource.max-wait}")
    private String max_wait;

    @Value("${spring.datasource.max-active}")
    private String max_active;

    @Value("${spring.datasource.min-evictable-idle-time-millis}")
    private String min_evictable_idle_time_millis;

    @Override
    public void setEnvironment(Environment environment) {
        return;
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(myUrl);
        datasource.setDriverClassName(driver_class_name);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setInitialSize(Integer.valueOf(initial_size));
        datasource.setMinIdle(Integer.valueOf(min_idle));
        datasource.setMaxWait(Long.valueOf(max_wait));
        datasource.setMaxActive(Integer.valueOf(max_active));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(min_evictable_idle_time_millis));
        try {
            datasource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        /*
         * // 设置 ui页面用户名密码
         * initParameters.put("loginUsername", "admin");
         * initParameters.put("loginPassword", "admin");
         */
        Map<String, String> initParameters = new HashMap<String, String>();

        /*
         *  禁用HTML页面上的“Reset All”功能
         */
        initParameters.put("resetEnable", "false");
        /*
         *  IP白名单 (没有配置或者为空，则允许所有访问)
         */
        initParameters.put("allow", "");
        // initParameters.put("deny", "192.168.0.3");// IP黑名单
        // (存在共同时，deny优先于allow)
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter(
                "exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 按照BeanId来拦截配置 用来bean的监控
     *
     * @return
     */
    @Bean(value = "druid-stat-interceptor")
    public DruidStatInterceptor DruidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        /*
         * 设置要监控的bean的id
         */
        beanNameAutoProxyCreator.setBeanNames("sysRoleMapper", "loginController");
        beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor");
        return beanNameAutoProxyCreator;
    }
}
