package com.xiben.pm.tk.service;

import java.util.Date;
import java.util.List;

import com.xiben.pm.tk.bean.DeptTaskScore;
import com.xiben.pm.tk.pojo.TkTask;
import com.xiben.pm.tk.request.CheckTaskParam;
import com.xiben.pm.tk.request.CommitTaskParam;
import com.xiben.pm.tk.request.CreateTaskParam;
import com.xiben.pm.tk.request.GetIndexTaskListParam;
import com.xiben.pm.tk.response.GetIndexTaskListBody;
import com.xiben.pm.tk.response.GetMonthScoreListBody;
import com.xiben.pm.tk.response.GetTaskDetailBody;
import com.xiben.pm.tk.response.GetTaskListBody;

public interface TkTaskService {

	
	DeptTaskScore getDeptTaskScore(Integer deptid,Date publishdt);
	Integer getTaskNum(Integer deptid,Integer dutyid);
	List<TkTask> getNofinished(Integer deptid);
	public GetTaskListBody getTaskList(int userid,Integer dutyid,int curpageno,int pagesize);
	public void createTask(int userid ,CreateTaskParam param);
	public void commitTask(int userid,CommitTaskParam param);
	public void checkTask(int userid,CheckTaskParam param);
	public GetTaskDetailBody getTaskDetail(int userid,Integer taskid);
	public List<GetMonthScoreListBody> getMonthScoreList(int userid,Integer deptid,Integer year);
	public void TaskJobForMonthScore();
	public GetIndexTaskListBody getIndexTaskList(int userid,GetIndexTaskListParam param);
	
}
