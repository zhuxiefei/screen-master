package com.betel.estatemgmt.business.web.document.controller;

import com.betel.estatemgmt.business.web.document.code.DocumentCode;
import com.betel.estatemgmt.business.web.document.constant.DocumentDataValidation;
import com.betel.estatemgmt.business.web.document.model.*;
import com.betel.estatemgmt.business.web.document.service.DocumentService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.property.PropertyDoc;
import com.betel.estatemgmt.common.model.property.PropertyDocType;
import com.betel.estatemgmt.common.model.system.SystemFile;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import com.betel.estatemgmt.business.web.login.service.WebLoginService;
import com.betel.estatemgmt.shiro.ActiveUserInfo;

/**
 * <p>
 * 资料管理接口
 * </p>
 * ClassName: DocumentController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/12 15:35 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/document")
public class DocumentController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private WebLoginService webLoginService;

    /**
     * <p>
     * 查询所有资料类别
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @return response
     */
    @RequiresPermissions(value = "documentManage-findTypes")
    @RequestMapping(value = "v1/findTypes", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<DocType>> findTypes() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findTypes start========");
        }
        Response<List<DocType>> response = new Response<>();
        try {
            //查询
            List<DocType> list = documentService.findTypes();
            response.setData(list);
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/findTypes error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findTypes end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 根据资料类别查询所有资料
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param docsReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-findDocs")
    @RequestMapping(value = "v1/findDocs", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<Document>> findDocs(@RequestBody FindDocsReq docsReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findDocs start========docsReq=" + docsReq);
        }
        Response<List<Document>> response = new Response<>();
        String typeId = docsReq.getTypeId();
        if (StringUtil.isBlank(typeId)) {
            response.setCode(DocumentCode.TYPE_ID_NULL);
        } else if (null == documentService.findByTypeId(typeId)) {
            response.setCode(DocumentCode.TYPE_IS_DELETE);
        } else {
            try {
                //查询
                List<Document> list = documentService.findDocByTypeId(typeId);
                if (list != null && list.size() > 0) {
                    for (Document doc :
                            list) {
                        if (StringUtil.isBlank(doc.getIsDownload())) {
                            doc.setIsDownload(DocumentDataValidation.NOT_DOWNLOAD.toString());
                        } else {
                            doc.setIsDownload(DocumentDataValidation.IS_DOWNLOAD.toString());
                        }
                    }
                }
                response.setData(list);
            } catch (Exception e) {
                LOG.error("========web/documentManage/v1/findDocs error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findDocs end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询资料详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param docReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-findDoc")
    @RequestMapping(value = "v1/findDoc", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<DocInfo> findDoc(@RequestBody FindDocReq docReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findDoc start========docReq=" + docReq);
        }
        Response<DocInfo> response = new Response<>();
        String docId = docReq.getDocId();
        if (StringUtil.isBlank(docId)) {
            response.setCode(DocumentCode.DOC_ID_NULL);
        } else {
            try {
                PropertyDoc doc = documentService.findByDocId(docId);
                if (null == doc) {
                    response.setCode(DocumentCode.DOC_IS_DELETE);
                } else {
                    //查询类别
                    PropertyDocType type = documentService.findByTypeId(doc.getTypeId());
                    DocInfo info = new DocInfo();
                    info.setDocContent(doc.getDocContent());
                    info.setDocId(docId);
                    info.setDocName(doc.getDocTitle());
                    info.setTypeName(type.getTypeName());
                    if (StringUtil.isBlank(doc.getAttachment())) {
                        info.setIsDownload(DocumentDataValidation.NOT_DOWNLOAD);
                    } else {
                        info.setIsDownload(DocumentDataValidation.IS_DOWNLOAD);
                    }
                    //查询
                    response.setData(info);
                }
            } catch (Exception e) {
                LOG.error("========web/documentManage/v1/findDoc error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findDoc end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询资料类别详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param typeReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-findDocType")
    @RequestMapping(value = "v1/findDocType", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<TypeInfo> findDocType(@RequestBody FindDocsReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findDocType start========typeReq=" + typeReq);
        }
        Response<TypeInfo> response = new Response<>();
        String typeId = typeReq.getTypeId();
        if (StringUtil.isBlank(typeId)) {
            response.setCode(DocumentCode.TYPE_ID_NULL);
        } else {
            try {
                PropertyDocType type = documentService.findByTypeId(typeId);
                if (null == type) {
                    response.setCode(DocumentCode.TYPE_IS_DELETE);
                } else {
                    TypeInfo info = new TypeInfo();
                    info.setTypeId(typeId);
                    info.setTypeName(type.getTypeName());
                    response.setData(info);
                }
            } catch (Exception e) {
                LOG.error("========web/documentManage/v1/findDocType error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/findDocType end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加资料
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param docReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-addDoc")
    @RequestMapping(value = "v1/addDoc", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addDoc(@RequestBody AddDocReq docReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/addDoc start========docReq=" + docReq);
        }
        Response<String> response = new Response<>();
        try {
            //前后去空格
            trimDoc(docReq);
            //校验
            if (!StringUtil.isBlank(validateDoc(docReq))) {
                response.setCode(validateDoc(docReq));
            } else {
                PropertyDoc doc = new PropertyDoc();
                doc.setCreateUser(AESUtil.decrypt(request.getHeader("userId")));
                doc.setDocContent(docReq.getDocContent());
                doc.setCreateTime(new Date(System.currentTimeMillis()));
                doc.setUpdateTime(new Date(System.currentTimeMillis()));
                doc.setTypeId(docReq.getTypeId());
                doc.setDocTitle(docReq.getDocName());
                documentService.addDoc(doc);
            }
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/addDoc error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/addDoc end========response" + response);
        }
        return response;
    }

    /**
     * 添加资料校验
     *
     * @param docReq
     * @return
     */
    private String validateDoc(AddDocReq docReq) {
        String response = null;
        if (StringUtil.isBlank(docReq.getDocName())) {
            response = DocumentCode.DOC_NAME_NULL;
        } else if (!docReq.getDocName().matches(DocumentDataValidation.DOC_NAME_RULE)) {
            response = DocumentCode.DOCNAME_RULE_ERROR;
        } else if (null != documentService.findByDocNameAndTypeId(docReq.getDocName(), docReq.getTypeId())) {
            response = DocumentCode.DOC_NAME_SAME;
        } else if (null == documentService.findByTypeId(docReq.getTypeId())) {
            response = DocumentCode.TYPE_IS_DELETE;
        } else if (StringUtil.isBlank(docReq.getDocContent())) {
            response = DocumentCode.DOC_CONTENT_NULL;
        } else if (DocumentDataValidation.DOC_CONTENT_SIZE < docReq.getDocContent().length()) {
            response = DocumentCode.DOC_CONTENT_MAX;
        }
        return response;
    }

    /**
     * 添加资料去空格
     *
     * @param docReq
     */
    private void trimDoc(AddDocReq docReq) {
        if (!StringUtil.isEmpty(docReq.getDocContent())) {
            docReq.setDocContent(docReq.getDocContent().trim());
        }
        if (!StringUtil.isEmpty(docReq.getDocName())) {
            docReq.setDocName(docReq.getDocName().trim());
        }
    }

    /**
     * <p>
     * 添加资料类别
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param typeReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-addDocType")
    @RequestMapping(value = "v1/addDocType", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addDocType(@RequestBody AddTypeReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/addDocType start========doctypeReqReq=" + typeReq);
        }
        Response<String> response = new Response<>();
        try {
            //去空格
            if (!StringUtil.isEmpty(typeReq.getTypeName())) {
                typeReq.setTypeName(typeReq.getTypeName().trim());
            }
            //校验
            if (!StringUtil.isBlank(validateType(typeReq))) {
                response.setCode(validateType(typeReq));
            } else {
                PropertyDocType type = new PropertyDocType();
                type.setUpdateTime(new Date(System.currentTimeMillis()));
                type.setCreateTime(new Date(System.currentTimeMillis()));
                type.setTypeName(typeReq.getTypeName());
                documentService.addType(type);
            }
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/addDocType error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/addDocType end========response" + response);
        }
        return response;
    }

    /**
     * 添加资料类别校验
     *
     * @param typeReq
     * @return
     */
    private String validateType(AddTypeReq typeReq) {
        String response = null;
        if (StringUtil.isBlank(typeReq.getTypeName())) {
            response = DocumentCode.TYPE_NAME_NULL;
        } else if (!typeReq.getTypeName().matches(DocumentDataValidation.TYPE_NAME_RULE)) {
            response = DocumentCode.TYPENAME_RULE_ERROR;
        } else if (null != documentService.findByTypeName(typeReq.getTypeName())) {
            response = DocumentCode.TYPE_NAME_SAME;
        }
        return response;
    }

    /**
     * <p>
     * 删除资料类别
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param typeReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-deleteDocType")
    @RequestMapping(value = "v1/deleteDocType", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteDocType(@RequestBody FindDocsReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/deleteDocType start========typeReq=" + typeReq);
        }
        Response<String> response = new Response<>();
        try {
            //校验
            if (StringUtil.isBlank(typeReq.getTypeId())) {
                response.setCode(DocumentCode.TYPE_ID_NULL);
            } else if (null != documentService.findByTypeId(typeReq.getTypeId())) {
                documentService.deleteType(typeReq.getTypeId());
            }
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/deleteDocType error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/deleteDocType end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除资料
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param docReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-deleteDoc")
    @RequestMapping(value = "v1/deleteDoc", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteDoc(@RequestBody FindDocReq docReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/deleteDoc start========docReq=" + docReq);
        }
        Response<String> response = new Response<>();
        try {
            //校验
            if (StringUtil.isBlank(docReq.getDocId())) {
                response.setCode(DocumentCode.DOC_ID_NULL);
            } else if (null != documentService.findByDocId(docReq.getDocId())) {
                documentService.deleteDoc(docReq.getDocId());
            }
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/deleteDoc error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/deleteDoc end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 编辑资料
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param docReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-updateDoc")
    @RequestMapping(value = "v1/updateDoc", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateDoc(@RequestBody UpdateDocReq docReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/updateDoc start========docReq=" + docReq);
        }
        Response<String> response = new Response<>();
        try {
            //去空格
            if (!StringUtil.isEmpty(docReq.getDocContent())) {
                docReq.setDocContent(docReq.getDocContent().trim());
            }
            if (!StringUtil.isEmpty(docReq.getDocName())) {
                docReq.setDocName(docReq.getDocName().trim());
            }
            //校验
            if (!StringUtil.isBlank(validateUpdateDoc(docReq))) {
                response.setCode(validateUpdateDoc(docReq));
            } else {
                PropertyDoc doc = new PropertyDoc();
                doc.setCreateUser(ActiveUserInfo.getActiveUserId());
                doc.setCreateUser(ActiveUserInfo.getActiveUserId());
                doc.setDocContent(docReq.getDocContent());
                doc.setUpdateTime(new Date(System.currentTimeMillis()));
                doc.setDocTitle(docReq.getDocName());
                doc.setDocId(docReq.getDocId());
                documentService.updateDoc(doc);
            }
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/updateDoc error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/updateDoc end========response" + response);
        }
        return response;
    }

    /**
     * 校验编辑资料
     *
     * @param docReq
     * @return
     */
    private String validateUpdateDoc(UpdateDocReq docReq) {
        if (StringUtil.isBlank(docReq.getDocId())) {
            return DocumentCode.DOC_ID_NULL;
        }
        PropertyDoc doc = documentService.findByDocId(docReq.getDocId());
        if (null == doc) {
            return DocumentCode.DOC_IS_DELETE;
        }
        if (StringUtil.isBlank(docReq.getTypeId())) {
            return DocumentCode.TYPE_ID_NULL;
        }
        if (null == documentService.findByTypeId(docReq.getTypeId())) {
            return DocumentCode.TYPE_IS_DELETE;
        }
        if (StringUtil.isBlank(docReq.getDocName())) {
            return DocumentCode.DOC_NAME_NULL;
        }
        if (!docReq.getDocName().matches(DocumentDataValidation.DOC_NAME_RULE)) {
            return DocumentCode.DOCNAME_RULE_ERROR;
        }
        PropertyDoc doc1 = documentService.findByDocNameAndTypeId(docReq.getDocName(), docReq.getTypeId());
        if (null != doc1 && !doc1.getDocTitle().equals(doc.getDocTitle())) {
            return DocumentCode.DOC_NAME_SAME;
        }
        if (StringUtil.isBlank(docReq.getDocContent())) {
            return DocumentCode.DOC_CONTENT_NULL;
        }
        if (DocumentDataValidation.DOC_CONTENT_SIZE < docReq.getDocContent().length()) {
            return DocumentCode.DOC_CONTENT_MAX;
        }
        return null;
    }

    /**
     * <p>
     * 编辑资料类别
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     *
     * @param typeReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-updateDocType")
    @RequestMapping(value = "v1/updateDocType", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateDocType(@RequestBody UpdateTypeReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/updateDocType start========typeReq=" + typeReq);
        }
        Response<String> response = new Response<>();
        try {
            //去空格
            if (!StringUtil.isEmpty(typeReq.getTypeName())) {
                typeReq.setTypeName(typeReq.getTypeName().trim());
            }
            //校验
            if (!StringUtil.isBlank(validateUpdateType(typeReq))) {
                response.setCode(validateUpdateType(typeReq));
            } else {
                PropertyDocType type = new PropertyDocType();
                type.setTypeId(typeReq.getTypeId());
                type.setTypeName(typeReq.getTypeName());
                type.setUpdateTime(new Date(System.currentTimeMillis()));
                documentService.updateType(type);
            }
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/updateDocType error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/updateDocType end========response" + response);
        }
        return response;
    }

    /**
     * 校验编辑资料类别
     *
     * @param typeReq
     * @return
     */
    private String validateUpdateType(UpdateTypeReq typeReq) {
        if (StringUtil.isBlank(typeReq.getTypeId())) {
            return DocumentCode.TYPE_ID_NULL;
        }
        PropertyDocType type = documentService.findByTypeId(typeReq.getTypeId());
        if (null == type) {
            return DocumentCode.TYPE_IS_DELETE;
        }
        if (StringUtil.isBlank(typeReq.getTypeName())) {
            return DocumentCode.TYPE_NAME_NULL;
        }
        if (!typeReq.getTypeName().matches(DocumentDataValidation.TYPE_NAME_RULE)) {
            return DocumentCode.TYPENAME_RULE_ERROR;
        }
        PropertyDocType type1 = documentService.findByTypeName(typeReq.getTypeName());
        if (type1 != null && !type1.getTypeId().equals(type.getTypeId())) {
            return DocumentCode.TYPE_NAME_SAME;
        }
        return null;
    }


    /**
     * <p>
     * 删除文件
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 19:25
     *
     * @param docReq
     * @return response
     */
    @RequiresPermissions(value = "documentManage-deleteFile")
    @RequestMapping(value = "v1/deleteFile", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteFile(@RequestBody FindDocReq docReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/deleteFile start========docReq=" + docReq);
        }
        Response<String> response = new Response<>();
        //校验入参
        if (StringUtil.isBlank(docReq.getDocId())) {
            response.setCode(DocumentCode.DOC_ID_NULL);
        } else {
            try {
                PropertyDoc doc = documentService.findByDocId(docReq.getDocId());
                if (doc != null && !StringUtil.isBlank(doc.getAttachment())) {
                    //删除
                    documentService.deleteFile(doc.getAttachment(), doc.getDocId());
                }
            } catch (Exception e) {
                LOG.error("========web/documentManage/v1/deleteFile error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/deleteFile end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 上传(修改)文件
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 19:53
     *
     * @param multipartFile
     * @return response
     */
    @RequiresPermissions(value = "documentManage-uploadFile")
    @RequestMapping(value = "v1/uploadFile")
    public Response<String> uploadFile(FindDocReq docReq, MultipartFile multipartFile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/uploadFile start========multipartFile=" + multipartFile);
        }
        Response<String> response = new Response<>();
        //校验文件
        if (StringUtil.isBlank(docReq.getDocId())) {
            response.setCode(DocumentCode.DOC_ID_NULL);
        } else if (null == documentService.findByDocId(docReq.getDocId())) {
            response.setCode(DocumentCode.DOC_IS_DELETE);
        } else if (null == multipartFile) {
            response.setCode(DocumentCode.FILE_IS_NULL);
        } else {
            //获取原始文件名（包括类型）
            String fileName = multipartFile.getOriginalFilename();
            if (LOG.isDebugEnabled()) {
                LOG.debug("========originalFilename=========" + fileName);
            }
            //获取文件类型
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========originalFileType=========" + fileType);
            }
            //判断文件格式和大小
            if (fileName.length() > DocumentDataValidation.FILE_NAME_SIZE) {
                response.setCode(DocumentCode.FILE_NAME_MAX);
            } else if (multipartFile.getSize() > DocumentDataValidation.FILE_SIZE) {
                response.setCode(DocumentCode.FILE_SIZE_MAX);
            } else if (!fileType.toLowerCase().matches(DocumentDataValidation.PIC_TYPE_RULE)) {
                response.setCode(DocumentCode.FILE_RULE_ERROR);
            } else if (fileType.toLowerCase().matches(DocumentDataValidation.PIC_TYPE_RULE)
                    && multipartFile.getSize() <= DocumentDataValidation.FILE_SIZE) {
                //设置文件保存路径
                String path = DocumentDataValidation.FILE_PATH + "document/";
                String name = "";
                try {
                    //上传后的新文件名
                    name = FileUtil.uploadFile(multipartFile, path);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========newFilename=========" + name);
                    }
                    //设置对象,插入数据库
                    SystemFile file = new SystemFile();
                    file.setCreateTime(new Date(System.currentTimeMillis()));
                    file.setFileName(fileName);
                    file.setFileUrl("document/" + name);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========File=========" + file);
                    }
                    //返回主键
                    String fileId = documentService.addFile(file);
                    PropertyDoc doc = new PropertyDoc();
                    doc.setAttachment(fileId);
                    doc.setDocId(docReq.getDocId());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========PropertyDoc=========" + doc);
                    }
                    documentService.updateDoc(doc);
                } catch (Exception e) {
                    LOG.error("========/web/documentManage/v1/uploadFile error!=========", e);
                    response.setCode(StatusCode.FAILURE);
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/uploadFile end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 下载文件
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/13 8:58
     *
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(value = "v1/downloadFile")
    public ModelAndView downloadFile(FindDocReq docReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/downloadFile start========request=" + request + ",docReq=" + docReq);
        }
        String userId = null;
        try {
            userId = AESUtil.decrypt(request.getParameter("userId"));
        } catch (Exception e) {
            LOG.error("========web/documentManage/v1/downloadFile error!========", e);
            return null;
        }
        String token = request.getParameter("token");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========request token=========" + token);
        }
        boolean isExist = webLoginService.checkWebToken(token, userId);
        if (null == token || null == userId || !isExist) {
            ModelAndView mav = new ModelAndView("redirect:/login.html");
            return mav;
        }
        if (StringUtil.isBlank(docReq.getDocId())) {
            if (LOG.isInfoEnabled()) {
                LOG.info("========web/documentManage/v1/downloadFile end========");
            }
            return null;
        }
        PropertyDoc doc = documentService.findByDocId(docReq.getDocId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("========PropertyDoc=========" + doc);
        }
        if (null == doc || (null != doc && StringUtil.isBlank(doc.getAttachment()))) {
            if (LOG.isInfoEnabled()) {
                LOG.info("========web/documentManage/v1/downloadFile end========");
            }
            return null;
        }
        SystemFile file = documentService.findByFileId(doc.getAttachment());
        if (file == null) {
            if (LOG.isInfoEnabled()) {
                LOG.info("========web/documentManage/v1/downloadFile end========");
            }
            return null;
        }
        ModelAndView mav = new ModelAndView("redirect:./download");
        mav.addObject("fileName", file.getFileName());
        mav.addObject("fileUrl", file.getFileUrl());
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/documentManage/v1/downloadFile end========");
        }
        return mav;
    }

    @RequestMapping("v1/download")
    public void download(@ModelAttribute("fileName") String fileName, @ModelAttribute("fileUrl") String fileUrl, HttpServletResponse response, HttpServletRequest request) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========v1/download start=========fileUrl=" + fileUrl + ",fileName=" + fileName);
        }
        File downloadFile = new File(DocumentDataValidation.FILE_PATH + fileUrl);
        InputStream in = null;
        OutputStream os = null;
        try {
            in = new FileInputStream(downloadFile);
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                // win10 ie edge 浏览器 和其他系统的ie
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                // 非IE
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + fileName);
            os = response.getOutputStream();
            byte[] b = new byte[6 * 1024];
            int length;
            while ((length = in.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭io流
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(os);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========v1/download end=========");
        }
    }
}
