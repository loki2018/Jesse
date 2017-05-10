package com.gb.service.impl;

import java.util.List;

import com.gb.bean.PageBean;
import com.gb.bean.Userinfo;
import com.gb.dao.UserinfoDAO;
import com.gb.service.UserinfoService;

public class UserinfoServiceImpl implements UserinfoService {
	private UserinfoDAO userinfoDao;

	public List<Userinfo> findAllUserinfos() {
		return userinfoDao.findAllUserinfos();
	}
	
	public List<Userinfo> findUserinfosByOk() {
		// TODO Auto-generated method stub
		return userinfoDao.findUserinfosByOk();
	}
	
	public List<Userinfo>  findUserinfoByEcid(String Ecid) {
		return  userinfoDao.findAllUserinfosByEcid(Ecid);
	}
	public List<Userinfo> findUserinfoByOrdid(String ordid) {
		// TODO Auto-generated method stub
		return  userinfoDao.findAllUserinfosByOrdid(ordid);
	}

	
	public Userinfo findUserinfoById(String id) {
		return userinfoDao.findUserinfoById(id);
	}

	public void removeUserinfo(Userinfo oaDepartment) {
		userinfoDao.removeUserinfo(oaDepartment);
	}

	public void saveUserinfo(Userinfo oaDepartment) {
		userinfoDao.saveUserinfo(oaDepartment);
	}

	public void updateUserinfo(Userinfo oaDepartment) {
		userinfoDao.updateUserinfo(oaDepartment);
	}
	
 

	public UserinfoDAO getUserinfoDao() {
		return userinfoDao;
	}

	public void setUserinfoDao(UserinfoDAO userinfoDao) {
		this.userinfoDao = userinfoDao;
	}
	public void AddUserinfo(Userinfo userinfo) {
		// TODO Auto-generated method stub
		userinfoDao.AddUserinfo(userinfo);
	}
	/** */
	/**
	 * 分页查询
	 * 
	 * @param currentPage
	 *            当前第几页
	 * @param pageSize
	 *            每页大小
	 * @return 封闭了分页信息(包括记录集list)的Bean
	 */
	@SuppressWarnings("unchecked")
	public PageBean queryForPage(int pageSize, int page) {

		final String hql = "from Userinfo order by id desc"; // 查询语句
		int allRow = userinfoDao.getAllRowCount(hql); // 总记录数
		int totalPage = PageBean.countTotalPage(pageSize, allRow); // 总页数
		final int offset = PageBean.countOffset(pageSize, page); // 当前页开始记录
		final int length = pageSize; // 每页记录数
		final int currentPage = PageBean.countCurrentPage(page);
		List<Userinfo> list = userinfoDao.queryForPage(hql, offset, length); // "一页"的记录

		// 把分页信息保存到Bean中
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}

	@SuppressWarnings("unchecked")
	public PageBean queryForPagebyPhoneno(int pageSize, int page,String phoneno) {

		final String hql = "from Userinfo where MSISDN like '%"+phoneno+"%' order by id desc"; // 查询语句
		int allRow = userinfoDao.getAllRowCount(hql); // 总记录数
		int totalPage = PageBean.countTotalPage(pageSize, allRow); // 总页数
		final int offset = PageBean.countOffset(pageSize, page); // 当前页开始记录
		final int length = pageSize; // 每页记录数
		final int currentPage = PageBean.countCurrentPage(page);
		List<Userinfo> list = userinfoDao.queryForPage(hql, offset, length); // "一页"的记录

		// 把分页信息保存到Bean中
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}

	public PageBean queryForPagebyEcname(int pageSize, int page,String ecname) {

		final String hql = "FROM Ecinfo   WHERE ecname LIKE '%"+ecname+"%' ORDER BY id DESC"; // 查询语句
		int allRow = userinfoDao.getAllRowCount(hql); // 总记录数
		int totalPage = PageBean.countTotalPage(pageSize, allRow); // 总页数
		final int offset = PageBean.countOffset(pageSize, page); // 当前页开始记录
		final int length = pageSize; // 每页记录数
		final int currentPage = PageBean.countCurrentPage(page);
		List<Userinfo> list = userinfoDao.queryForPage(hql, offset, length); // "一页"的记录

		// 把分页信息保存到Bean中
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}
	
	public PageBean queryForPagebyEcid(int pageSize, int page,String ECID) {

		final String hql = "FROM  Userinfo   WHERE ecid = '"+ECID+"' and OprCode='03' ORDER BY id DESC"; // 查询语句
		int allRow = userinfoDao.getAllRowCount(hql); // 总记录数
		int totalPage = PageBean.countTotalPage(pageSize, allRow); // 总页数
		final int offset = PageBean.countOffset(pageSize, page); // 当前页开始记录
		final int length = pageSize; // 每页记录数
		final int currentPage = PageBean.countCurrentPage(page);
		List<Userinfo> list = userinfoDao.queryForPage(hql, offset, length); // "一页"的记录

		// 把分页信息保存到Bean中
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}
	public PageBean queryForPagebyOrdid(int pageSize, int page,String ordid) {
		// TODO Auto-generated method stub
		final String hql = "FROM  Userinfo   WHERE OrdId = '"+ordid+"'and OprCode='03' ORDER BY id DESC"; // 查询语句
		int allRow = userinfoDao.getAllRowCount(hql); // 总记录数
		int totalPage = PageBean.countTotalPage(pageSize, allRow); // 总页数
		final int offset = PageBean.countOffset(pageSize, page); // 当前页开始记录
		final int length = pageSize; // 每页记录数
		final int currentPage = PageBean.countCurrentPage(page);
		List<Userinfo> list = userinfoDao.queryForPage(hql, offset, length); // "一页"的记录

		// 把分页信息保存到Bean中
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}


	
	
	
	

}