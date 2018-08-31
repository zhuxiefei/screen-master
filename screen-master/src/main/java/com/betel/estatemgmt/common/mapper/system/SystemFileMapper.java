package com.betel.estatemgmt.common.mapper.system;

import com.betel.estatemgmt.common.model.system.SystemFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemFileMapper {
    int deleteByPrimaryKey(String fileId);

    int insert(SystemFile record);

    int insertSelective(SystemFile record);

    SystemFile selectByPrimaryKey(String fileId);

    int updateByPrimaryKeySelective(SystemFile record);

    int updateByPrimaryKey(SystemFile record);

    void deleteFileList(List<String> fileIds);

    SystemFile selectByFileId(String fileId);
    void deleteFile(String fileId);

    /**
     * 查询二维码文件
     *
     * @param fileIds
     * @return
     */
    List<SystemFile> findBatchQRcodeByFileIds(List<String> fileIds);
}