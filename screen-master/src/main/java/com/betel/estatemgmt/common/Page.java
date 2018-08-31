package com.betel.estatemgmt.common;

/**
 * <p>
 * 分页
 * </p>
 * ClassName: ActiveUser <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 10:06 <br/>
 * Version: 1.0 <br/>
 */
public class Page {

    private int curPage = 1;

    private int pageSize = 10;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [curPage=");
		builder.append(curPage);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append("]");
		return builder.toString();
	}
    
    
}
