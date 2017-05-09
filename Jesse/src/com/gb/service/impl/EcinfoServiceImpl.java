package com.gb.service.impl;

import java.util.List;

import com.gb.bean.Ecinfo;
import com.gb.bean.Userinfo;
import com.gb.dao.EcinfoDAO;
import com.gb.service.EcinfoService;

public class EcinfoServiceImpl implements EcinfoService {
	private EcinfoDAO ecinfoDao;

	public List<Ecinfo> findAllEcinfos() {
		return ecinfoDao.findAllEcinfos();
	}

	public Ecinfo findEcinfoById(String id) {
		return ecinfoDao.findEcinfoById(id);
	}
	
	

	public void removeEcinfo(Ecinfo oaDepartment) {
		ecinfoDao.removeEcinfo(oaDepartment);
	}

	public void saveEcinfo(Ecinfo oaDepartment) {
		ecinfoDao.saveEcinfo(oaDepartment);
	}

	public void updateEcinfo(Ecinfo oaDepartment) {
		ecinfoDao.updateEcinfo(oaDepartment);
	}

	public List<Ecinfo> findEcinfoByEcid(String ecid) {
		return this.ecinfoDao.findEcinfoByEcid(ecid);
	}

	public EcinfoDAO getEcinfoDao() {
		return ecinfoDao;
	}

	public void setEcinfoDao(EcinfoDAO ecinfoDao) {
		this.ecinfoDao = ecinfoDao;
	}

	public List<Ecinfo> findEcinfoByEcname() {
		// TODO Auto-generated method stub
		return this.ecinfoDao.findEcinfoByEcname();
	}

	

}