package com.gb.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.gb.bean.Log;
import com.gb.dao.LogDAO;

@SuppressWarnings("unchecked")
public class LogDAOImpl extends HibernateDaoSupport implements LogDAO {

	public List<Log> findAllLogs() {
		String hql = "from Log log";
		return (List<Log>) this.getHibernateTemplate().find(hql);
	}

	public Log findLogById(String id) {
		Log log = (Log) this.getHibernateTemplate().get(Log.class,
				id);
		return log;
	}

	public void removeLog(Log log) {
		this.getHibernateTemplate().delete(log);
	}

	public void saveLog(Log log) {
		this.getHibernateTemplate().save(log);
	}

	public void updateLog(Log log) {
		this.getHibernateTemplate().update(log);
	}

}
