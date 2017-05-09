package com.gb.dao;

import java.util.List;

import com.gb.bean.Log;

public interface LogDAO {
	public void saveLog(Log log);

	public void removeLog(Log log);

	public Log findLogById(String id);

	public List<Log> findAllLogs();

	public void updateLog(Log log);

}
