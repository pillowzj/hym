package com.hym.project.mapper;

import com.hym.project.domain.Banner;

import java.util.List;

public interface BannerMapper {

    Banner selectByPrimaryKey(Integer id);
    List<Banner> selectByStatus(Integer status);
}