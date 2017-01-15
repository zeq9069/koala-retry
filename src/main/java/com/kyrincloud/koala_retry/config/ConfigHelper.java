package com.kyrincloud.koala_retry.config;

import java.util.Properties;

import com.kyrincloud.koala_retry.utils.PropUtil;

public class ConfigHelper {
	
	private static final Properties pro = PropUtil.loadProps(ConfigConstant.CONFIG);
	
	public static String getBasePack(){
		return PropUtil.getString(pro, ConfigConstant.BASE_PACK);
	}

}
