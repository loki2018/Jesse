package com.gb.dao;

import java.util.List;

import com.gb.bean.Ecinfo;

public interface EcinfoDAO {
	public void saveEcinfo(Ecinfo ecinfo);

	public void removeEcinfo(Ecinfo ecinfo);

	public Ecinfo findEcinfoById(String id);

	public List<Ecinfo> findAllEcinfos();

	public void updateEcinfo(Ecinfo ecinfo);
	
	public List<Ecinfo> findEcinfoByEcid(String ecid);

	public List<Ecinfo> findEcinfoByEcname();
}