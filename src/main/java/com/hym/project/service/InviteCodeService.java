package com.hym.project.service;

import com.hym.project.domain.InviteCode;

public interface InviteCodeService {

    int add(InviteCode record);
    InviteCode getByInviteCode();
}
