package com.betel.estatemgmt.common.mapper.property;

import com.betel.estatemgmt.business.web.document.model.DocType;
import com.betel.estatemgmt.common.model.property.PropertyDocType;

import java.util.List;

public interface PropertyDocTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(PropertyDocType record);

    int insertSelective(PropertyDocType record);

    PropertyDocType selectByPrimaryKey(String typeId);

    int updateByPrimaryKeySelective(PropertyDocType record);

    int updateByPrimaryKey(PropertyDocType record);

    List<DocType> selectTypes();

    PropertyDocType selectByTypeName(String typeName);
}