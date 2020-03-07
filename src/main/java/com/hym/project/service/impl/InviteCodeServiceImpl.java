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
    public InviteCode getByInviteCode() {
        InviteCode inviteCode = inviteCodeMapper.selectInviteCode();
        inviteCodeMapper.updateByInviteCode(inviteCode.getInviteCode());
        return inviteCode;
    }
}
