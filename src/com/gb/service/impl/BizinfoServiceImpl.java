package com.gb.service.impl;

import java.util.List;

import com.gb.bean.Bizinfo;
import com.gb.dao.BizinfoDAO;
import com.gb.service.BizinfoService;

public class BizinfoServiceImpl implements BizinfoService {
	private BizinfoDAO bizinfoDao;

	public List<Bizinfo> findAllBizinfos() {
		return bizinfoDao.findAllBizinfos();
	}

	public Bizinfo findBizinfoById(String id) {
		return bizinfoDao.findBizinfoById(id);
	}

	public void removeBizinfo(Bizinfo oaDepartment) {
		bizinfoDao.removeBizinfo(oaDepartment);
	}

	public void saveBizinfo(Bizinfo oaDepartment) {
		bizinfoDao.saveBizinfo(oaDepartment);
	}

	public void updateBizinfo(Bizinfo oaDepartment) {
		bizinfoDao.updateBizinfo(oaDepartment);
	}
	
	public List<Bizinfo> findBizinfoByOrdid(String ordid) {
		return bizinfoDao.findBizinfoByOrdid(ordid);
	}

	public BizinfoDAO getBizinfoDao() {
		return bizinfoDao;
	}

	public void setBizinfoDao(BizinfoDAO bizinfoDao) {
		this.bizinfoDao = bizinfoDao;
	}

	

}