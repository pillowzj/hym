package com.hym.project.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: koulijun
 * @Date: 2018/12/3 18:49
 * @Description:
 */
public class Page  {
    public static int getPageNum(int pageNum, int pageSize, int total) {
        int totalPage = 1;
        try{
            totalPage = (total + pageSize -1) / pageSize;
        }catch (Exception e){
            totalPage =1;
        }
        pageNum = pageNum == 1 ?0:pageSize*(pageNum-1);
        return pageNum;
    }
}

