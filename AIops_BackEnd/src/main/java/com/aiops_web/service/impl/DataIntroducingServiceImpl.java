package com.aiops_web.service.impl;

import com.aiops_web.dto.DataIntroUserDTO;
import com.aiops_web.entity.sql.DataIntroducing;
import com.aiops_web.dao.sql.DataIntroducingMapper;
import com.aiops_web.service.DataIntroducingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Service
public class DataIntroducingServiceImpl extends ServiceImpl<DataIntroducingMapper, DataIntroducing> implements DataIntroducingService {
    @Resource
    DataIntroducingMapper dataIntroducingMapper;

    // DTO相关

    // 增加
    @Override
    public DataIntroducing saveOne(DataIntroUserDTO dataIntroUserDTO) {
        DataIntroducing dataIntroducing = new DataIntroducing();
//        dataIntroducing.setBatchId(dataIntroUserDTO.getBatchId());
        dataIntroducing.setSource(dataIntroUserDTO.getSource());
        dataIntroducing.setFilePath(dataIntroUserDTO.getFilePath());
        dataIntroducing.setIntro(dataIntroUserDTO.getIntro());
        dataIntroducing.setName(dataIntroUserDTO.getName());
        dataIntroducing.setObjId(dataIntroUserDTO.getObjId());
        dataIntroducing.setDataNum(dataIntroUserDTO.getDataNum());
        dataIntroducing.setDataSample(dataIntroUserDTO.getDataSample());
        dataIntroducing.setUserId(dataIntroUserDTO.getUserId());
        dataIntroducing.setPlace("OriginalData");

        Date currentDate = new Date(System.currentTimeMillis());
        dataIntroducing.setTstamp(currentDate);

        System.out.println(dataIntroducing);

        dataIntroducingMapper.insert(dataIntroducing);
        return dataIntroducing;
    }

    // 修改
    @Override
    public boolean updateOne(DataIntroUserDTO dataIntroUserDTO) {
        DataIntroducing dataIntroducing = new DataIntroducing();
        dataIntroducing.setBatchId(dataIntroUserDTO.getBatchId());
        dataIntroducing.setSource(dataIntroUserDTO.getSource());
        dataIntroducing.setFilePath(dataIntroUserDTO.getFilePath());
        dataIntroducing.setIntro(dataIntroUserDTO.getIntro());
        dataIntroducing.setName(dataIntroUserDTO.getName());
        dataIntroducing.setObjId(dataIntroUserDTO.getObjId());
        dataIntroducing.setDataNum(dataIntroUserDTO.getDataNum());
        dataIntroducing.setDataSample(dataIntroUserDTO.getDataSample());
        dataIntroducing.setUserId(dataIntroUserDTO.getUserId());
        dataIntroducing.setPlace(dataIntroUserDTO.getPlace());

        Date currentDate = new Date(System.currentTimeMillis());
        dataIntroducing.setTstamp(currentDate);
        int saveResult = dataIntroducingMapper.updateById(dataIntroducing);
        return saveResult != 0;
    }

    // 根据batchId获取一个DataIntroUserDTO
    @Override
    public DataIntroUserDTO getDataIntroUserDTOById(Integer batchId) {
        return dataIntroducingMapper.getDataIntroUserDTOById(batchId);
    }

    @Override
    public List<DataIntroUserDTO> getAllDataIntroUserDTO() {
        return dataIntroducingMapper.getAllDataIntroUserDTO();
    }
}
