package com.will.framework.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页pojo
 * @author will
 */
public class Pager<T> extends BasePojo {
	private static final long serialVersionUID = 1L;
	//当前页码
	private int pageNum=1;
	//每页数量
	private int numPerPage=10;
	//总页数
	private int totalPage;
	//数据总量
	private long totalCount;
	//数据列表
	private List<T> dataList=new ArrayList<T>();
	public Pager(){
		
	}
	/**
	 * 获取当前页码
	 * @return
	 */
	public int getPageNum() {
		return pageNum;
	}
	/**
	 * 设置当前页码
	 * @param pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * 获取每页数量
	 * @return 每页数量
	 */
	public int getNumPerPage() {
		return numPerPage;
	}
	/**
	 * 设置每页显示数量
	 * @param numPerPage
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	/**
	 * 获取总数据量
	 * @return
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 * 获取总页数
	 * @param totalItem
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 设置中页数
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * 获取总页数
	 * @return 总页数
	 */
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * 获取数据列表
	 * @return {@link List<T>}
	 */
	public List<T> getDataList() {
		return dataList;
	}
	/**
	 * 设置数据
	 * @param itemList {@link List<T>}
	 */
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	/**
	 * 计算中页数
	 * @param totalCount  数据总量
	 * @param numPerPage   每页数量
	 * @return 总页数
	 */
	public static int calculateTotalPage(long totalCount,int numPerPage){
		return (int)(totalCount%numPerPage==0?totalCount/numPerPage:totalCount/numPerPage+1);
	}
}
