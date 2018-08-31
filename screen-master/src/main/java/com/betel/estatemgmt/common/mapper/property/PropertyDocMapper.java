package com.betel.estatemgmt.common.mapper.property;

import com.betel.estatemgmt.business.web.document.model.Document;
import com.betel.estatemgmt.common.model.property.PropertyDoc;

import java.util.List;

public interface PropertyDocMapper {
    int deleteByPrimaryKey(String docId);

    int insert(PropertyDoc record);

    int insertSelective(PropertyDoc record);

    PropertyDoc selectByPrimaryKey(String docId);

    int updateByPrimaryKeySelective(PropertyDoc record);

    int updateByPrimaryKeyWithBLOBs(PropertyDoc record);

    int updateByPrimaryKey(PropertyDoc record);

    List<Document> selectByTypeId(String typeId);

    PropertyDoc selectByDocNameAndTypeId(String docName, String typeId);

    List<PropertyDoc> findByTypeId(String typeId);

    void deleteDocs(List<String> docIds);
}