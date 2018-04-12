package com.test.common;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面上分页查询结果
 * @param <T>
 * @author shuisheng 2018-02-11
 */
public class QueryResult<T> implements Serializable {


	//	time cost
	private long startTime;

	private long endTime;
	

	private Map<String, Object> param;//query param,

	private Map<String, Object> properties;//

	private List<T> list;

	private String message;

	
	public QueryResult(Map<String, Object> param) {
		this.param = param;
		if(this.param.get("curr_page") == null){
			this.param.put("curr_page", 1);
		}else {
			this.param.put("curr_page", Integer.parseInt(String.valueOf(param.get("curr_page"))));
		}
		if(this.param.get("page_size") == null){
			this.param.put("page_size", 10);
		}else {
			this.param.put("page_size", Integer.parseInt(String.valueOf(param.get("page_size"))));
		}

	}


	public void calculate(int totalRow){
		int currPage = Integer.parseInt(String.valueOf(param.get("curr_page")));
		int pageSize = Integer.parseInt(String.valueOf(param.get("page_size")));
		int startRow = 0;
		int endRow = 0;
		

		int totalPage;
		int prevPage;
		int nextPage;
		boolean existPrev;
		boolean existNext;
		

		int mod = totalRow % pageSize;
		if (totalRow == 0)
			totalPage = 1;
		else if (mod == 0)
			totalPage = totalRow/pageSize;
		else
			totalPage = totalRow/pageSize + 1;
	
		//	if currPage > totalPages, currPage = totalPages
		if (currPage > totalPage)
			currPage = totalPage;
		
		// 
		nextPage = currPage + 1;
		prevPage = currPage - 1;

		// 
		if(currPage * pageSize < totalRow)
		{
			endRow = currPage * pageSize;
			startRow = endRow - pageSize + 1;
		}
		else
		{
			endRow = totalRow;
			startRow = pageSize * (totalPage - 1) + 1;
		}
		

		if (nextPage > totalPage)
			existNext = false;					
		else					
			existNext = true;
		
		if(prevPage == 0)			
			existPrev = false;
		else
			existPrev = true;

		properties = new HashMap<String, Object>();
		properties.put("total_row", "" + totalRow);
		properties.put("total_page", "" + totalPage);
		properties.put("prev_page", "" + prevPage);
		properties.put("next_page", "" + nextPage);
		properties.put("exist_prev", "" + existPrev);
		properties.put("exist_next", "" + existNext);
		properties.put("start_row", "" + startRow);
		properties.put("end_row", "" + endRow);

	}	


	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}