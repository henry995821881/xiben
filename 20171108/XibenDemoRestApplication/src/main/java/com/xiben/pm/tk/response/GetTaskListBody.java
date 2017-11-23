package com.xiben.pm.tk.response;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.tk.response.sub.GetTaskListData;
import com.xiben.pm.tk.response.sub.Page;

public class GetTaskListBody {

	/**
	 * "data": [
            {
              
            }  "taskid": 1,
                "taskstatus": 1,
                "optype": 1,
                "title": "",
                "createdt": "",
                "enddt": "",
                "createusername": ""
        ],
        "page": {}

	 */
	
	private List<GetTaskListData> data;
	private Page page;
	public List<GetTaskListData> getData() {
		return data;
	}
	public void setData(List<GetTaskListData> data) {
		this.data = data;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
	
}
