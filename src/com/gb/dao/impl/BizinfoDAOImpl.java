package com.gb.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.gb.bean.Bizinfo;
import com.gb.dao.BizinfoDAO;

@SuppressWarnings("unchecked")
public class BizinfoDAOImpl extends HibernateDaoSupport implements BizinfoDAO {

	public List<Bizinfo> findAllBizinfos() {
		String hql = "from Bizinfo bizinfo";
		return (List<Bizinfo>) this.getHibernateTemplate().find(hql);
	}

	public Bizinfo findBizinfoById(String id) {
		Bizinfo bizinfo = (Bizinfo) this.getHibernateTemplate().get(Bizinfo.class,
				id);
		return bizinfo;
	}

	public void removeBizinfo(Bizinfo bizinfo) {
		this.getHibernateTemplate().delete(bizinfo);
	}

	public void saveBizinfo(Bizinfo bizinfo) {
		this.getHibernateTemplate().save(bizinfo);
	}

	public void updateBizinfo(Bizinfo bizinfo) {
		this.getHibernateTemplate().update(bizinfo);
	}

	public List<Bizinfo> findBizinfoByOrdid(String ordid) {
		String hql = "from Bizinfo bizinfo where bizinfo.OrdID='" + ordid + "'";
		return (List<Bizinfo>) this.getHibernateTemplate().find(hql);
	}

}
