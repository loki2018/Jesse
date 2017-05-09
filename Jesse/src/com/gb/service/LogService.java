package com.gb.service;

import java.util.List;

import com.gb.bean.Log;

public interface LogService {
	public void saveLog(Log log);

	public void removeLog(Log log);

	public Log findLogById(String id);

	public List<Log> findAllLogs();

	public void updateLog(Log log);
	
}
