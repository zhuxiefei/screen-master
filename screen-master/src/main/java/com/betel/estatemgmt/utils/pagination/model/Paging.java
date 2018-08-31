package com.betel.estatemgmt.utils.pagination.model;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @ClassName:Paging
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public class Paging<E> {
    private List<E> rows = null;
    private int total = 0;
    /*-?|ibsmp|张成政|c10|?*/

    protected int curPage = 1;
    protected int maxPage = 0;
    protected int pageSize = 10;

    protected int firstRow = 0;

    protected int lastRow = 0;
    private static final ThreadLocal<Integer> PAGINATION_TOTAL = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void setPaginationTotal(int count) {
        PAGINATION_TOTAL.set(count);
    }

    private int getPaginationTotal() {
        return PAGINATION_TOTAL.get();
    }

    public Paging() {

    }

    public Paging(int curPage) {
        if (curPage < 1) {
            this.curPage = 1;
        } else {
            this.curPage = curPage;
        }
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public Paging(int curPage, int pageSize) {
        if (curPage < 1) {
            this.curPage = 1;
        } else {
            this.curPage = curPage;
        }
        if (pageSize < 1) {
            this.pageSize = 1;
        } else {
            this.pageSize = pageSize;
        }

    }

    public RowBounds getRowBounds() {
        return new RowBounds((this.curPage - 1) * this.pageSize, this.pageSize);
    }


    public void result(List<E> rows) {
        this.rows = rows;
        this.setTotal(getPaginationTotal());
    }

    public void pages(List<E> rows,int curPage,int pageSize,int total){
        this.rows = rows;
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.total = total;
    }

    public List<E> getRows() {
        return rows;
    }

    public int getTotal() {
        return total;
    }

    public void countMaxPage() {

        if (this.total % this.pageSize == 0) {
            this.maxPage = this.total / this.pageSize;
        } else {
            this.maxPage = this.total / this.pageSize + 1;
        }
        if (this.getMaxPage() < this.getCurPage()) {
            this.curPage = this.getMaxPage();
        }
        if(this.curPage!=0){
            this.firstRow = (this.curPage - 1) * this.pageSize + 1;
        }

        int temp = this.curPage * this.pageSize;
        this.lastRow = this.total > temp ? temp : this.total;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Paging [rows=");
		builder.append(rows);
		builder.append(", total=");
		builder.append(total);
		builder.append(", curPage=");
		builder.append(curPage);
		builder.append(", maxPage=");
		builder.append(maxPage);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", firstRow=");
		builder.append(firstRow);
		builder.append(", lastRow=");
		builder.append(lastRow);
		builder.append("]");
		return builder.toString();
	}

    
    public int getCurPage() {
        return curPage;
    }

	public int getMaxPage() {
        return maxPage;
    }


    public int getRowsPerPage() {
        return pageSize;
    }

    public void setTotal(int total) {
        this.total = total;
        this.countMaxPage();// 计算其他值
    }


    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getPageSize() {
        return this.pageSize;
    }
}
