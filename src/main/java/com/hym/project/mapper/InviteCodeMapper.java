package com.hym.project.mapper;

import com.hym.project.domain.InviteCode;

public interface InviteCodeMapper {

    int insert(InviteCode record);

    InviteCode selectInviteCode();

    int updateByInviteCode(Integer inviteCode);

}