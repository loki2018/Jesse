package com.gb.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.gb.bean.Userstateinfo;
import com.gb.dao.UserstateinfoDAO;

@SuppressWarnings("unchecked")
public class UserstateinfoDAOImpl extends HibernateDaoSupport implements UserstateinfoDAO {

	public List<Userstateinfo> findAllUserstateinfos() {
		String hql = "from Userstateinfo userstateinfo  ";
		return (List<Userstateinfo>) this.getHibernateTemplate().find(hql);
	}

	public Userstateinfo findUserstateinfoById(String id) {
		Userstateinfo userstateinfo = (Userstateinfo) this.getHibernateTemplate().get(
				Userstateinfo.class, id);
		return userstateinfo;
	}

	public void removeUserstateinfo(Userstateinfo userstateinfo) {
		this.getHibernateTemplate().delete(userstateinfo);
	}

	public void saveUserstateinfo(Userstateinfo userstateinfo) {
		this.getHibernateTemplate().save(userstateinfo);
	}

	public void updateUserstateinfo(Userstateinfo userstateinfo) {
		this.getHibernateTemplate().update(userstateinfo);
	}

	public List<Userstateinfo> findBizinfoByPhone(String phone) {
		String hql = "from Userstateinfo userstateinfo where userstateinfo.MSISDN='"+ phone + "'";
		return (List<Userstateinfo>) this.getHibernateTemplate().find(hql);
	}

	public void AddUserstateinfo(Userstateinfo userstateinfo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(userstateinfo);
	}

}
