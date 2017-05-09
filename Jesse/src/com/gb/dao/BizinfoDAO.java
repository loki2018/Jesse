package com.gb.dao;

import java.util.List;

import com.gb.bean.Bizinfo;

public interface BizinfoDAO {
	public void saveBizinfo(Bizinfo bizinfo);

	public void removeBizinfo(Bizinfo bizinfo);

	public Bizinfo findBizinfoById(String id);

	public List<Bizinfo> findAllBizinfos();

	public void updateBizinfo(Bizinfo bizinfo);
	
	public List<Bizinfo> findBizinfoByOrdid(String ordid);
}
