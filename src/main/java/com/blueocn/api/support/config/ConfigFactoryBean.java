/*
 * Copyright (c) 2008, 2015, OneAPM and/or its affiliates. All rights reserved.
 */
package com.blueocn.api.support.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * Title: ConfigFactoryBean
 * Description: Config类初始化工厂Bean
 *
 * @author Yufan
 * @version 1.0.0
 * @since 2015-12-16 11:40
 */
public class ConfigFactoryBean implements FactoryBean<Config>, InitializingBean {

    private Config config;

    @Override
    public Config getObject() {
        return config;
    }

    @Override
    public Class<?> getObjectType() {
        return Config.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        String kongAddress = ConfigLoader.getProperty("kongAddress");
        if (StringUtils.isBlank(kongAddress)) {
            throw new IllegalArgumentException("kongAddress in api-config.cfg can't be null.");
        }
        config = new Config();
        config.setKongAddress(kongAddress);
        config.setListenPort(Integer.parseInt(ConfigLoader.getProperty("listenPort")));
        config.setInvokeTimeoutMillis(Integer.parseInt(ConfigLoader.getProperty("invokeTimeoutMillis")));

        for (Map.Entry<String, String> entry : ConfigLoader.allConfig().entrySet()) {
            // 将 config. 开头的配置都加入到config中
            if (entry.getKey().startsWith("configs.")) {
                config.setParameter(entry.getKey().replaceFirst("configs.", ""), entry.getValue());
            }
        }
    }
}