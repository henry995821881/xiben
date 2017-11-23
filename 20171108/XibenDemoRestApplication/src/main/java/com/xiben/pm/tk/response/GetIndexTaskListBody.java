package com.xiben.pm.tk.response;

import java.util.List;

import com.xiben.pm.tk.response.sub.GetIndexTaskData;
import com.xiben.pm.tk.response.sub.Page;

public class GetIndexTaskListBody {

	
	private Page page;
	private List<GetIndexTaskData> data;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<GetIndexTaskData> getData() {
		return data;
	}
	public void setData(List<GetIndexTaskData> data) {
		this.data = data;
	}
	
	
	
}
