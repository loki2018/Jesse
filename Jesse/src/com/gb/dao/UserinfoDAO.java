package com.gb.dao;

import java.util.List;

import com.gb.bean.Ecinfo;
import com.gb.bean.Userinfo;

public interface UserinfoDAO {
	public void saveUserinfo(Userinfo userinfo);

	public void removeUserinfo(Userinfo userinfo);

	public Userinfo findUserinfoById(String id);

	public List<Userinfo> findAllUserinfos();
	
	public void AddUserinfo(Userinfo userinfo);
	
	public List<Ecinfo> findAllEcinfos();
	
	public List<Userinfo> findAllUserinfosByEcid(String ecid);
	public List<Userinfo> findAllUserinfosByOrdid(String ecid);
	
	public List<Ecinfo> findEcinfoByEcname(); 

	public void updateUserinfo(Userinfo userinfo);
	
	public List<Userinfo> findBizinfoByEcidAndOrdidAndPhone(String ecid,String ordid,String phone);
	
	/**
     * 分页查询
     */
    @SuppressWarnings("unchecked")
	public List queryForPage(final String hql,final int offset,final int length);
    
    /** *//**
     * 查询所有记录数
     * @param hql 查询的条件
     * @return 总记录数
     */
    public int getAllRowCount(String hql);

	public List<Userinfo> findUserinfosByOk();


}
