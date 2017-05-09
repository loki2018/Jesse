package com.gb.service.impl;

import java.util.List;

import com.gb.bean.Log;
import com.gb.dao.LogDAO;
import com.gb.service.LogService;

public class LogServiceImpl implements LogService {
	private LogDAO logDao;

	public List<Log> findAllLogs() {
		return logDao.findAllLogs();
	}

	public Log findLogById(String id) {
		return logDao.findLogById(id);
	}

	public void removeLog(Log oaDepartment) {
		logDao.removeLog(oaDepartment);
	}

	public void saveLog(Log oaDepartment) {
		logDao.saveLog(oaDepartment);
	}

	public void updateLog(Log oaDepartment) {
		logDao.updateLog(oaDepartment);
	}

	public LogDAO getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDAO logDao) {
		this.logDao = logDao;
	}

}