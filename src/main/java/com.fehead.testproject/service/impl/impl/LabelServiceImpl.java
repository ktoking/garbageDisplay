package com.fehead.testproject.service.impl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.testproject.dao.LabelDOMapper;
import com.fehead.testproject.dataobject.LabelDO;
import com.fehead.testproject.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelServiceImpl {

    @Autowired
    private LabelDOMapper labelDOMapper;

    public List<LabelDO> selectLabel(List<Integer> list){
        List<LabelDO> labelDOS=new ArrayList<>();
        for (Integer integer:list) {
            QueryWrapper<LabelDO> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("label",integer);
            LabelDO labelDO=labelDOMapper.selectOne(queryWrapper);
            String[] result=labelDO.getName().split("，");
            String[] result1=result[0].split("；");
            labelDO.setName(result1[0]);
            labelDOS.add(labelDO);
        }
        return labelDOS;
    }
}
