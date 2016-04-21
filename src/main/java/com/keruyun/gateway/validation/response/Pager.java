package com.keruyun.gateway.validation.response;


import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.utils.RegexUtils;
import com.keruyun.gateway.validation.type.RegexType;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 *
 * @author youngtan99@163.com
 */
public class Pager<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3348316697045554442L;

    @ValidateProperty(regexType = RegexType.INT, nullable = true, regexExpression = RegexUtils.NONNEGATIVE_NO_ZERO_INT_REGEX)
    @SerializedName("cur_page")
    private int curPage = 1; // 当前页

    @ValidateProperty(regexType = RegexType.INT, nullable = true, regexExpression = RegexUtils.NONNEGATIVE_NO_ZERO_INT_REGEX)
    @SerializedName("page_size")
    private int pageSize = 20; // 每页多少行

    @SerializedName("total_row")
    private int totalRow; // 共多少行

    private transient int start;// 当前页起始行

    private transient int end;// 结束行

    @SerializedName("total_page")
    private int totalPage; // 共多少页

    private List<T> data;


    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        if (curPage < 1) {
            curPage = 1;
        } else {
            start = pageSize * (curPage - 1);
        }
        end = start + pageSize > totalRow ? totalRow : start + pageSize;
        this.curPage = curPage;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {

        return end;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        totalPage = (totalRow + pageSize - 1) / pageSize;
        this.totalRow = totalRow;
        if (this.totalRow == 0) {
            this.start = 0;
            this.end = 0;
        } else {
            if (totalPage < curPage) {
                curPage = totalPage;
                start = pageSize * (curPage - 1);
                end = totalRow;
            }
            end = start + pageSize > totalRow ? totalRow : start + pageSize;
        }

    }

    public int getTotalPage() {
        return this.totalPage;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Pager() {
    }
}