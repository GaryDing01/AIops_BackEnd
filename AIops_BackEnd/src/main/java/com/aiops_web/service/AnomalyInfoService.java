package com.aiops_web.service;

import com.aiops_web.dto.AnomalyInfoUserDTO;
import com.aiops_web.dto.AnomalyInfoUserKGDTO;
import com.aiops_web.dto.RootCauseKGDTO;
import com.aiops_web.entity.sql.AnomalyInfo;
import com.aiops_web.entity.sql.WorkflowExec;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
public interface AnomalyInfoService extends IService<AnomalyInfo> {

    // Yuran
    List<AnomalyInfoUserDTO> getAnomalyInfos(AnomalyInfoUserDTO info, int pageNum, int pageSize);

    boolean deleteByAnoId(int anoId);

    AnomalyInfo updateStatusById(int anoId, int statusId);

    boolean updateInfo(AnomalyInfo info);

    // 根据故障检测的执行结果(此时workflowExec信息应该完成)来保存故障信息
    boolean saveAnoInfoByExec(WorkflowExec workflowExec);

    interface RoleEnumService {
    }

    // 根据故障id获取对应的KG
    RootCauseKGDTO getKGByAno(Integer anoId);

    // 检查该故障是否有知识图谱可以查看
    int checkAnoKG(Integer anoId);

    // DTO相关
    // 根据algId获取AlgUserDTO
    AnomalyInfoUserKGDTO getAnomalyInfoUserDTOById(Integer anoId);
    // 获取所有的AlgUserDTO
    List<AnomalyInfoUserKGDTO> getAllAnomalyInfoUserDTO();
}
