package com.hym.project.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: koulijun
 * @Date: 2018/12/3 18:49
 * @Description:
 */
public class Page<T> {
    private int totalpage;//总页数

    private int totalrecord; // 总条数

    private List<T> list = new ArrayList<T>();//每页显示集合

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalrecord() {
        return totalrecord;
    }

    public void setTotalrecord(int totalrecord) {
        this.totalrecord = totalrecord;
    }
}

