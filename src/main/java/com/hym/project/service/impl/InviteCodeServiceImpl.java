package com.hym.project.service.impl;

import com.hym.project.domain.InviteCode;
import com.hym.project.mapper.InviteCodeMapper;
import com.hym.project.service.InviteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InviteCodeServiceImpl implements InviteCodeService {

   @Autowired
   private InviteCodeMapper inviteCodeMapper;

    @Override
    public int add(InviteCode record) {
        return inviteCodeMapper.insert(record);
    }


    @Override
    public Integer getByInviteCode() {
        InviteCode inviteCode = inviteCodeMapper.selectMaxInviteCode();
        if(inviteCodeMapper.updateByInviteCode(inviteCode.getInviteCode())==1){
            return inviteCode.getInviteCode();
        }
        return 0;
    }
}
