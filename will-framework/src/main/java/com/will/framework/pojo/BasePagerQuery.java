package com.will.framework.pojo;

/**
 * 列表查询器基类
 * 当前页默认1
 * 每页数量默认10
 * @author will
 *
 */
public class BasePagerQuery extends BaseRequestPojo {
	private static final long serialVersionUID = -4261877289213514200L;
	//页码
	private int pageNum=1;
	//每页显示数量
	private int numPerPage=10;
	public BasePagerQuery() {
		// TODO Auto-generated constructor stub
	}

	public int getStart() {
		return (getPageNum()-1)*numPerPage;
	}
	/**
	 * 获取页码
	 * @return 页码
	 */
	public int getPageNum() {
		return pageNum;
	}
	/**
	 * 设置页码
	 * @param pageNum 页码
	 */
	public void setPageNum(Integer pageNum) {
		if(null!=pageNum){
			this.pageNum = (pageNum<1?1:pageNum);
		}
		
	}
	/**
	 * 获取每页显示数量
	 * @return 每页显示数量
	 */
	public int getNumPerPage() {
		return numPerPage;
	}
	/**
	 * 设置每页显示数量
	 * @param numPerPage 每页显示数量
	 */
	public void setNumPerPage(Integer numPerPage) {
		if(null!=numPerPage){
			this.numPerPage = (numPerPage<1?1:numPerPage);
		}
	}
	/**
	 * 构造函数
	 * @param pageNum 页码
	 * @param numPerPage 每页显示数量
	 */
	public BasePagerQuery(int pageNum, int numPerPage) {
		super();
		this.pageNum = pageNum;
		this.numPerPage = numPerPage;
	}
	
}
