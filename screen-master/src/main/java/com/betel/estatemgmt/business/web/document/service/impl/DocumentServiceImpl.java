package com.betel.estatemgmt.business.web.document.service.impl;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.document.constant.DocumentDataValidation;
import com.betel.estatemgmt.business.web.document.model.DocType;
import com.betel.estatemgmt.business.web.document.model.Document;
import com.betel.estatemgmt.business.web.document.service.DocumentService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.mapper.property.PropertyDocMapper;
import com.betel.estatemgmt.common.mapper.property.PropertyDocTypeMapper;
import com.betel.estatemgmt.common.mapper.system.SystemFileMapper;
import com.betel.estatemgmt.common.model.property.PropertyDoc;
import com.betel.estatemgmt.common.model.property.PropertyDocType;
import com.betel.estatemgmt.common.model.system.SystemFile;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资料管理接口实现类
 * </p>
 * ClassName: DocumentServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/12 15:37 <br/>
 * Version: 1.0 <br/>
 */
@Service("DocumentService")
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private PropertyDocMapper docMapper;

    @Autowired
    private PropertyDocTypeMapper typeMapper;

    @Autowired
    private SystemFileMapper fileMapper;

    @Override
    public List<DocType> findTypes() {
        return typeMapper.selectTypes();
    }

    @Override
    public PropertyDocType findByTypeId(String typeId) {
        return typeMapper.selectByPrimaryKey(typeId);
    }

    @Override
    public List<Document> findDocByTypeId(String typeId) {
        return docMapper.selectByTypeId(typeId);
    }

    @Override
    public PropertyDoc findByDocId(String docId) {
        return docMapper.selectByPrimaryKey(docId);
    }

    @Override
    public PropertyDoc findByDocNameAndTypeId(String docName, String tyepId) {
        return docMapper.selectByDocNameAndTypeId(docName,tyepId);
    }

    @Override
    public void addDoc(PropertyDoc doc) {
        docMapper.insert(doc);
    }

    @Override
    public PropertyDocType findByTypeName(String typeName) {
        return typeMapper.selectByTypeName(typeName);
    }

    @Override
    public void addType(PropertyDocType type) {
        typeMapper.insert(type);
    }

    @Override
    public void deleteType(String typeId) {
        List<PropertyDoc> docs = docMapper.findByTypeId(typeId);
        List<String> docIds = new ArrayList<>();
        List<String> fileIds = new ArrayList<>();
        if (docs!=null && docs.size()>0){
            for (PropertyDoc doc:
                 docs) {
                docIds.add(doc.getDocId());
                if (!StringUtil.isBlank(doc.getAttachment())){
                    //删除物理路径
                    SystemFile file = fileMapper.selectByFileId(doc.getAttachment());
                    if (file!=null){
                        fileIds.add(file.getFileId());
                        FileUtil.deletefile(DocumentDataValidation.FILE_PATH+file.getFileUrl());
                    }
                }
            }
        }
        //删除类别
        typeMapper.deleteByPrimaryKey(typeId);
        //删除资料
        if (docIds.size()>0){
            docMapper.deleteDocs(docIds);
        }
        //删除文件
        if (fileIds.size()>0){
            fileMapper.deleteFileList(fileIds);
        }
    }

    @Override
    public void deleteDoc(String docId) {
        PropertyDoc doc = docMapper.selectByPrimaryKey(docId);
        //删除文件
        if (!StringUtil.isBlank(doc.getAttachment())){
            SystemFile file = fileMapper.selectByFileId(doc.getAttachment());
            if (null != file){
                FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR)+file.getFileUrl());
                fileMapper.deleteFile(file.getFileId());
            }
        }
        //删除资料
        docMapper.deleteByPrimaryKey(docId);
    }

    @Override
    public void updateDoc(PropertyDoc doc) {
        docMapper.updateByPrimaryKeySelective(doc);
    }

    @Override
    public void updateType(PropertyDocType type) {
        typeMapper.updateByPrimaryKeySelective(type);
    }

    @Override
    public void deleteFile(String fileId,String docId) {
        SystemFile file = fileMapper.selectByFileId(fileId);
        if (file!=null){
            FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR)+file.getFileUrl());
            fileMapper.deleteFile(file.getFileId());
        }
        PropertyDoc doc = new PropertyDoc();
        doc.setDocId(docId);
        doc.setAttachment("");
        docMapper.updateByPrimaryKeySelective(doc);
    }

    @Override
    public String addFile(SystemFile file) {
        fileMapper.insert(file);
        return file.getFileId();
    }

    @Override
    public SystemFile findByFileId(String fileId) {
        return fileMapper.selectByFileId(fileId);
    }
}
