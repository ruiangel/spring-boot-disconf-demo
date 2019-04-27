package com.rui.demo.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.baidu.disconf.client.config.DisClientConfig;
import com.rui.demo.dto.DisconfProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * disconf 配置类
 *
 * @author rui
 * @datetime 2019-04-27 16:29
 */
@Configuration
public class DisconfConfig implements EnvironmentAware {
    private DisconfProperties disconfProperties;

    @Override
    public void setEnvironment(Environment environment) {
        // 在DisconfMgrBean实例化之前，初始化其所需的所有配置信息
        disconfProperties = new DisconfProperties(environment);
        initDisClientConfig();
    }

    @Bean(name = "disconfMgrBean", destroyMethod = "destroy")
    public DisconfMgrBean disconfMgrBean() {
        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        disconfMgrBean.setScanPackage(disconfProperties.getScanPackage());
        return disconfMgrBean;
    }

    @Bean(name = "disconfMgrBean2", initMethod = "init", destroyMethod = "destroy")
    public DisconfMgrBeanSecond disconfMgrBeanSecond() {
        return new DisconfMgrBeanSecond();
    }

    public void initDisClientConfig() {
        DisClientConfig disClientConfig = DisClientConfig.getInstance();
        disClientConfig.ENABLE_DISCONF = disconfProperties.isEnableDisconf();
        disClientConfig.CONF_SERVER_HOST = disconfProperties.getConfServerHost();
        disClientConfig.VERSION = disconfProperties.getVersion();
        disClientConfig.APP = disconfProperties.getApp();
        disClientConfig.ENV = disconfProperties.getEnv();
        disClientConfig.DEBUG = disconfProperties.isDebug();
        disClientConfig.IGNORE_DISCONF_LIST = disconfProperties.getIgnoreDisconfList();
        disClientConfig.CONF_SERVER_URL_RETRY_TIMES = disconfProperties.getConfServerUrlRetryTimes();
        disClientConfig.confServerUrlRetrySleepSeconds = disconfProperties.getConfServerUrlRetrySleepSeconds();
    }
}
