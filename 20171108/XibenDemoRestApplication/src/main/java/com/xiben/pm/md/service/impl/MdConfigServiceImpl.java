package com.xiben.pm.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.pm.md.mapper.MdConfigMapper;
import com.xiben.pm.md.service.MdConfigService;

@Service
public class MdConfigServiceImpl implements MdConfigService {

	
	@Autowired
	private MdConfigMapper mdConfigMapper;

	@Override
	public String getValue(String code) {
		
		return mdConfigMapper.getValue(code);
	}

}
