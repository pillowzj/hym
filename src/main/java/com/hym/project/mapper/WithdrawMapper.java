package com.hym.project.mapper;

import com.hym.project.domain.Withdraw;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WithdrawMapper {
    int deleteByPrimaryKey(String id);

    int insert(Withdraw record);

    int insertSelective(Withdraw record);

    Withdraw selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Withdraw record);

    int updateByPrimaryKey(Withdraw record);

    /**
     * 分页
     * @param uid
     * @return
     *
     */
    @Select("select * from withdraw where uid = #{uid}")
    List<Withdraw> selectWithdrawPage(String uid );

    @Select("SELECT count(1) from  withdraw where  uid =#{uid} ")
    int selectCountByUid(String uid);
}