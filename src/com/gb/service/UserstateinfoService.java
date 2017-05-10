package com.gb.service;

import java.util.List;

import com.gb.bean.Userstateinfo;

public interface UserstateinfoService {
	public void saveUserstateinfo(Userstateinfo userstateinfo);

	public void removeUserstateinfo(Userstateinfo userstateinfo);

	public void AddUserstateinfo(Userstateinfo userstateinfo);
	
	public Userstateinfo findUserstateinfoById(String id);

	public List<Userstateinfo> findAllUserstateinfos();

	public void updateUserstateinfo(Userstateinfo userstateinfo);
	
 
}
