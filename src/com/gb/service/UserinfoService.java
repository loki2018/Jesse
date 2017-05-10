package com.gb.service;

import java.util.List;

import com.gb.bean.PageBean;
import com.gb.bean.Userinfo;

public interface UserinfoService {
	public void saveUserinfo(Userinfo userinfo);

	public void removeUserinfo(Userinfo userinfo);

	public Userinfo findUserinfoById(String id);
	
	public void AddUserinfo(Userinfo userinfo);

	public List<Userinfo> findAllUserinfos();   
	
	public List<Userinfo> findUserinfosByOk();
	
	public void updateUserinfo(Userinfo userinfo);
	
 
	/**
     * 分页查询
     * @param currentPage 当前第几页
     * @param pageSize 每页大小
     * @return 封闭了分页信息(包括记录集list)的Bean
     */
    public PageBean queryForPage(int pageSize,int currentPage);
    
    public PageBean queryForPagebyPhoneno(int pageSize,int currentPage,String phoneno);
    public PageBean queryForPagebyEcname(int pageSize,int currentPage,String ecname);
    public PageBean queryForPagebyEcid(int pageSize,int currentPage,String ecid);
    
    public PageBean queryForPagebyOrdid(int pageSize,int currentPage,String ordid);

	public List<Userinfo> findUserinfoByEcid(String ecid);
	public List<Userinfo> findUserinfoByOrdid(String ordid);
	
}
