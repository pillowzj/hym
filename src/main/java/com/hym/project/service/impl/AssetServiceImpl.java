package com.hym.project.service.impl;

import com.hym.project.domain.Asset
;
import com.hym.project.mapper.AssetMapper;
import com.hym.project.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetMapper assetMapper;

    @Override
    public Asset selectByPrimaryKey(String id) {
        return assetMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(Asset  record) {
        return assetMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Asset  record) {
        return assetMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Asset getUserHYMByPrimaryKey(String id) {
        return null;
    }
}
