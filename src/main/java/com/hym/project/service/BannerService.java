package com.hym.project.service;

import com.hym.project.domain.Banner;

import java.util.List;

public interface BannerService {

    Banner getByPrimaryKey(Integer id);

    List<Banner> getByStatus(Integer status);
}
