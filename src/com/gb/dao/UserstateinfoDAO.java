package com.gb.dao;

import java.util.List;

import com.gb.bean.Userstateinfo;

public interface UserstateinfoDAO {
	public void saveUserstateinfo(Userstateinfo userstateinfo);

	public void removeUserstateinfo(Userstateinfo userstateinfo);

	public Userstateinfo findUserstateinfoById(String id);

	public void AddUserstateinfo(Userstateinfo userstateinfo);
	
	public List<Userstateinfo> findAllUserstateinfos();

	public void updateUserstateinfo(Userstateinfo userstateinfo);
	
 
}
