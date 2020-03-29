package com.audioapp.cms.controller;

import com.audioapp.cms.utils.IpUtil;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public abstract class BaseController {
	
	protected static final Snowflake snowflake = IdUtil.createSnowflake(IpUtil.getWorkerId(), IpUtil.getWorkerId());

}
