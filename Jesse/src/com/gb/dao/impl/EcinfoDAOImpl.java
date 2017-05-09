package com.gb.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.gb.bean.Ecinfo;
import com.gb.dao.EcinfoDAO;

@SuppressWarnings("unchecked")
public class EcinfoDAOImpl extends HibernateDaoSupport implements EcinfoDAO {

	public List<Ecinfo> findAllEcinfos() {
		String hql = "from Ecinfo ecinfo";
		return (List<Ecinfo>) this.getHibernateTemplate().find(hql);
	}

	public Ecinfo findEcinfoById(String id) {
		Ecinfo ecinfo = (Ecinfo) this.getHibernateTemplate().get(Ecinfo.class,
				id);
		return ecinfo;
	}

	public void removeEcinfo(Ecinfo ecinfo) {
		this.getHibernateTemplate().delete(ecinfo);
	}

	public void saveEcinfo(Ecinfo ecinfo) {
		this.getHibernateTemplate().save(ecinfo);
	}

	public void updateEcinfo(Ecinfo ecinfo) {
		this.getHibernateTemplate().update(ecinfo);
	}

	public List<Ecinfo> findEcinfoByEcid(String ecid) {
		String hql = "from Ecinfo ecinfo where ecinfo.ECID='" + ecid + "'";
		return (List<Ecinfo>) this.getHibernateTemplate().find(hql);
	}

	public List<Ecinfo> findEcinfoByEcname() {
		// TODO Auto-generated method stub
		String hql = "from Ecinfo ecinfo";
		return (List<Ecinfo>) this.getHibernateTemplate().find(hql);
	}

}
