package com.rui.demo.dto;

import com.rui.demo.annotation.DisconfConfigAnnotation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;

/**
 * disconf 属性类
 *
 * @author rui
 * @datetime 2019-04-27 16:20
 */
@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisconfProperties {

    /**
     * 扫描路径
     * 多路径间用","分隔
     */
    @DisconfConfigAnnotation
    String scanPackage;
    /**
     * 是否使用远程配置文件
     * true(默认)会从远程获取配置 false则直接获取本地配置
     */
    @DisconfConfigAnnotation
    boolean enableDisconf;
    /**
     * 配置服务器 HOST
     * 多个HOST，用逗号分隔
     */
    @DisconfConfigAnnotation
    String confServerHost;
    /**
     * 版本
     */
    @DisconfConfigAnnotation
    String version;
    /**
     * 应用名
     */
    @DisconfConfigAnnotation
    String app;
    /**
     * 环境
     */
    @DisconfConfigAnnotation
    String env;
    /**
     * 调试模式
     * 调试模式下，ZK超时或断开连接后不会重新连接（常用于client单步debug）
     * 非调试模式下，ZK超时或断开连接会自动重新连接
     */
    @DisconfConfigAnnotation
    boolean debug;
    /**
     * 忽略哪些分布式配置
     * 多个配置用逗号分隔
     */
    @DisconfConfigAnnotation
    String ignoreDisconfList;
    /**
     * 获取远程配置的重试次数
     * 默认是3次
     */
    @DisconfConfigAnnotation
    int confServerUrlRetryTimes;
    /**
     * 获取远程配置的重试时休眠时间
     * 默认是5秒
     */
    @DisconfConfigAnnotation
    int confServerUrlRetrySleepSeconds;

    private final static String DISCONF_PROPERTIES_PREFIX = "disconf.";

    public DisconfProperties(Environment environment) {
        loadConfig(environment);
    }

    /**
     * 通过反射机制，从yml文件中，获取disconf配置信息
     */
    private void loadConfig(Environment environment) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DisconfConfigAnnotation.class)) {
                continue;
            }

            String propertyName = DISCONF_PROPERTIES_PREFIX + field.getName();
            Object value = environment.getProperty(propertyName, field.getType());
            field.setAccessible(true);
            try {
                field.set(this, value);
            } catch (IllegalAccessException e) {
                log.error("初始化disconf配置失败，报错信息：{}", e);
            }
        }
    }
}
