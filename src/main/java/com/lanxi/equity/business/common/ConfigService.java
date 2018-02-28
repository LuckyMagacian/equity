package com.lanxi.equity.business.common;

/**
 * 配置管理接口
 * @author yangyuanjian
 */
public interface ConfigService {
	String getValue(String fileName, String keyName);
	String getValue(String keyName);
	void reload();
}
