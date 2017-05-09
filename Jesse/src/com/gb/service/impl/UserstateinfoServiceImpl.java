package com.gb.service.impl;

import java.util.List;

import com.gb.bean.Userinfo;
import com.gb.bean.Userstateinfo;
import com.gb.dao.UserstateinfoDAO;
import com.gb.service.UserstateinfoService;

public class UserstateinfoServiceImpl implements UserstateinfoService {
	private UserstateinfoDAO userstateinfoDao;

	public List<Userstateinfo> findAllUserstateinfos() {
		return userstateinfoDao.findAllUserstateinfos();
	}

	public Userstateinfo findUserstateinfoById(String id) {
		return userstateinfoDao.findUserstateinfoById(id);
	}

	public void removeUserstateinfo(Userstateinfo oaDepartment) {
		userstateinfoDao.removeUserstateinfo(oaDepartment);
	}

	public void saveUserstateinfo(Userstateinfo oaDepartment) {
		userstateinfoDao.saveUserstateinfo(oaDepartment);
	}

	public void updateUserstateinfo(Userstateinfo oaDepartment) {
		userstateinfoDao.updateUserstateinfo(oaDepartment);
	}
	
	public List<Userstateinfo> findBizinfoByPhone(String phone) {
		return userstateinfoDao.findBizinfoByPhone(phone);
	}

	public UserstateinfoDAO getUserstateinfoDao() {
		return userstateinfoDao;
	}

	public void setUserstateinfoDao(UserstateinfoDAO userstateinfoDao) {
		this.userstateinfoDao = userstateinfoDao;
	}


	public void AddUserstateinfo(Userstateinfo userstateinfo) {
		// TODO Auto-generated method stub
		userstateinfoDao.AddUserstateinfo(userstateinfo);
	}

	

}