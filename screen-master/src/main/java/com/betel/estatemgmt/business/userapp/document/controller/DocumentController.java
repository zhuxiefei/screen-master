package com.betel.estatemgmt.business.userapp.document.controller;


import com.betel.estatemgmt.business.userapp.document.model.DocUrl;
import com.betel.estatemgmt.business.web.document.code.DocumentCode;
import com.betel.estatemgmt.business.web.document.constant.DocumentDataValidation;
import com.betel.estatemgmt.business.web.document.model.*;
import com.betel.estatemgmt.business.web.document.service.DocumentService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.property.PropertyDoc;
import com.betel.estatemgmt.common.model.property.PropertyDocType;
import com.betel.estatemgmt.common.model.system.SystemFile;
import com.betel.estatemgmt.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 资料接口
 * </p>
 * ClassName: DocumentController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/16 19:29 <br/>
 * Version: 1.0 <br/>
 */
@RestController("appDocumentController")
@RequestMapping("userApp/document")
public class DocumentController {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    /**
     * <p>
     * 查询所有资料类别
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     * @return response
     *
     * */
    @RequestMapping(value = "v1/findTypes", method = RequestMethod.GET)
    public Response<List<DocType>> findTypes() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/findTypes start========");
        }
        Response<List<DocType>> response = new Response<>();
        try {
            //查询
            List<DocType> list = documentService.findTypes();
            response.setData(list);
        } catch (Exception e) {
            LOG.error("========app/documentManage/v1/findTypes error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/findTypes end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 根据资料类别查询所有资料
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     * @param docsReq
     * @return response
     *
     * */
    @RequestMapping(value = "v1/findDocs", method = RequestMethod.GET)
    public Response<List<Document>> findDocs(FindDocsReq docsReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/findDocs start========docsReq="+docsReq);
        }
        Response<List<Document>> response = new Response<>();
        String typeId = docsReq.getTypeId();
        if (StringUtil.isBlank(typeId)){
            response.setCode(DocumentCode.TYPE_ID_NULL);
        }else if (null == documentService.findByTypeId(typeId)){
            response.setCode(DocumentCode.TYPE_IS_DELETE);
        }else {
            try {
                //查询
                List<Document> list = documentService.findDocByTypeId(typeId);
                response.setData(list);
            } catch (Exception e) {
                LOG.error("========app/documentManage/v1/findDocs error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/findDocs end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询资料详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     * @param docReq
     * @return response
     *
     * */
    @RequestMapping(value = "v1/findDoc", method = RequestMethod.GET)
    public Response<DocInfo> findDoc(FindDocReq docReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/findDoc start========docReq="+docReq);
        }
        Response<DocInfo> response = new Response<>();
        String docId = docReq.getDocId();
        if (StringUtil.isBlank(docId)){
            response.setCode(DocumentCode.DOC_ID_NULL);
        }else {
            try {
                PropertyDoc doc = documentService.findByDocId(docId);
                if (null == doc){
                    response.setCode(DocumentCode.DOC_IS_DELETE);
                }else {
                    //查询类别
                    PropertyDocType type = documentService.findByTypeId(doc.getTypeId());
                    DocInfo info = new DocInfo();
                    info.setDocContent(doc.getDocContent());
                    info.setDocId(docId);
                    info.setDocName(doc.getDocTitle());
                    info.setTypeName(type.getTypeName());
                    if (StringUtil.isBlank(doc.getAttachment())){
                        info.setIsDownload(DocumentDataValidation.NOT_DOWNLOAD);
                    }else {
                        info.setIsDownload(DocumentDataValidation.IS_DOWNLOAD);
                    }
                    //查询
                    response.setData(info);
                }
            } catch (Exception e) {
                LOG.error("========app/documentManage/v1/findDoc error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/findDoc end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 下载文件
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     * @param docReq
     * @return response
     *
     * */
    @RequestMapping(value = "v1/downloadDoc", method = RequestMethod.GET)
    public Response<DocUrl> downloadDoc(FindDocReq docReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/downloadDoc start========docReq="+docReq);
        }
        Response<DocUrl> response = new Response<>();
        if (StringUtil.isBlank(docReq.getDocId())){
            response.setCode(DocumentCode.DOC_ID_NULL);
        }else {
            try {
                PropertyDoc doc = documentService.findByDocId(docReq.getDocId());
                if (null == doc){
                    response.setCode(DocumentCode.DOC_IS_DELETE);
                }else {
                    DocUrl url = new DocUrl();
                    SystemFile file = documentService.findByFileId(doc.getAttachment());
                    if (file!=null){
                        url.setFileUrl(DocumentDataValidation.FILE_SERVER_PATH+file.getFileUrl());
                        url.setFileName(file.getFileName());
                        response.setData(url);
                    }
                }
            } catch (Exception e) {
                LOG.error("========app/documentManage/v1/downloadDoc error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/documentManage/v1/downloadDoc end========response" + response);
        }
        return response;
    }
}
