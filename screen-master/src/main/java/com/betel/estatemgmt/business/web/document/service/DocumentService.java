package com.betel.estatemgmt.business.web.document.service;

import com.betel.estatemgmt.business.web.document.model.DocType;
import com.betel.estatemgmt.business.web.document.model.Document;
import com.betel.estatemgmt.common.model.property.PropertyDoc;
import com.betel.estatemgmt.common.model.property.PropertyDocType;
import com.betel.estatemgmt.common.model.system.SystemFile;

import java.util.List;

/**
 * <p>
 * 资料管理接口实现
 * </p>
 * ClassName: DocumentService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/12 15:37 <br/>
 * Version: 1.0 <br/>
 */
public interface DocumentService {
    List<DocType> findTypes();

    PropertyDocType findByTypeId(String typeId);

    List<Document> findDocByTypeId(String typeId);

    PropertyDoc findByDocId(String docId);

    PropertyDoc findByDocNameAndTypeId(String docName, String tyepId);

    void addDoc(PropertyDoc doc);

    PropertyDocType findByTypeName(String typeName);

    void addType(PropertyDocType type);

    void deleteType(String typeId);

    void deleteDoc(String docId);

    void updateDoc(PropertyDoc doc);

    void updateType(PropertyDocType type);

    void deleteFile(String fileId, String docId);

    String addFile(SystemFile file);

    SystemFile findByFileId(String fileId);
}
