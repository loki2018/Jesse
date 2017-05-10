package com.gb.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.gb.bean.Userinfo;
import com.gb.dao.UserinfoDAO;

@SuppressWarnings("unchecked")
public class UserinfoDAOImpl extends HibernateDaoSupport implements UserinfoDAO {

	public List<Userinfo> findAllUserinfos() {
		String hql = "from Userinfo userinfo";
		return (List<Userinfo>) this.getHibernateTemplate().find(hql);
	}
	public List<Userinfo> findUserinfosByOk() {
		String hql = "from Userinfo userinfo where OprCode = '03' ";
		return (List<Userinfo>) this.getHibernateTemplate().find(hql);
	}
	
	
	public List<Userinfo> findAllUserinfosByEcid(String ecid) {
		String hql = "from Userinfo  where ecid='"+ecid+"'";
		return (List<Userinfo>) this.getHibernateTemplate().find(hql);
	}
	
	public List<Userinfo> findAllUserinfosByOrdid(String ordid) {
		// TODO Auto-generated method stub
		String hql = "from Userinfo  where ordid='"+ordid+"'";
		return (List<Userinfo>) this.getHibernateTemplate().find(hql);
	}


	public Userinfo findUserinfoById(String id) {
		Userinfo userinfo = (Userinfo) this.getHibernateTemplate().get(
				Userinfo.class, id);
		return userinfo;
	}

	public void removeUserinfo(Userinfo userinfo) {
		this.getHibernateTemplate().delete(userinfo);
	}

	public void saveUserinfo(Userinfo userinfo) {
		this.getHibernateTemplate().save(userinfo);
	}

	public void updateUserinfo(Userinfo userinfo) {
		this.getHibernateTemplate().update(userinfo);
	}

	public void AddUserinfo(Userinfo userinfo) {  
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(userinfo);
	}
 
	
	public List queryForPage(final String hql,final int offset,final int length){
		
        List list = (List) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
                query.setFirstResult(offset);
                query.setMaxResults(length);
                List list = query.list();
                return list;
			}
           
        });
        return list; 
    }   
    /** *//**
     * 查询所有记录数
     * @return 总记录数
     */
    public int getAllRowCount(String hql){
        return getHibernateTemplate().find(hql).size();
    }
	
	

}
