package kr.co.solbipos.pos.confg.vermanage.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.vermanage.service.ApplcStoreVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerInfoVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerManageService;
import kr.co.solbipos.pos.confg.verrecv.enums.VerRecvFg;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
* @Class Name : VerManageServiceImpl.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("verManageService")
public class VerManageServiceImpl implements VerManageService {

    private final VerManageMapper verManageMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public VerManageServiceImpl(VerManageMapper verManageMapper, PopupMapper popupMapper, MessageService messageService) {
        this.verManageMapper = verManageMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 프로그램 상세구분 코드 조회(콤보박스용) */
    @Override
    public List<DefaultMap<String>> getNmcodeCdList(){

        return verManageMapper.getNmcodeCdList();
    }

    /** 포스버전 목록 조회 */
    @Override
    public List<DefaultMap<String>> list(VerInfoVO verInfo, SessionInfoVO sessionInfoVO) {

        verInfo.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        return verManageMapper.getList(verInfo);
    }

    /** 포스버전정보 상세 조회 */
    @Override
    public DefaultMap<String> dtlInfo(VerInfoVO verInfo) {
        return verManageMapper.dtlInfo(verInfo);
    }

    /** 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> storeList(VerInfoVO verInfo) {
        return verManageMapper.storeList(verInfo);
    }

    /** 버전 삭제 */
    @Override
    public int verDelete(VerInfoVO verInfo) {
        return verManageMapper.verDelete(verInfo);
    }

    /** 버전 시리얼넘버 중복 체크 */
    @Override
    public int chkVerSerNo(VerInfoVO verInfo) {
        return verManageMapper.chkVerSerNo(verInfo);
    }

    /** 버전 등록 */
    @Override
    public boolean regist(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        boolean isSuccess = false;

        try{

            VerInfoVO verInfo = uploadFile(multi);

            String insertDt = currentDateTimeString();
           /* String pgmYn = (String) multi.getParameter("pgmYn") == "true" ? "Y": "N";
            String imgYn = (String)multi.getParameter("imgYn")== "true" ? "Y": "N";
            String dbYn = (String)multi.getParameter("dbYn")== "true" ? "Y": "N";*/


            verInfo.setVerSerNo((String)multi.getParameter("verSerNo"));
            verInfo.setVerSerNm((String)multi.getParameter("verSerNm"));
            verInfo.setProgFg((String)multi.getParameter("progFg"));
            verInfo.setPgmYn((String)multi.getParameter("pgmYn"));
            verInfo.setImgYn((String)multi.getParameter("imgYn"));
            verInfo.setDbYn((String)multi.getParameter("dbYn"));
            verInfo.setDbYn((String)multi.getParameter("dbYn"));
            verInfo.setOrgnCds((String)multi.getParameter("orgnCds"));
            verInfo.setProgDetailFg((String)multi.getParameter("progDetailFg"));
            verInfo.setSystemTypeFg((String)multi.getParameter("systemTypeFg"));
            verInfo.setAgencyDispYn((String)multi.getParameter("agencyDispYn"));
            verInfo.setDelYn("N");

            String fileDesc = HtmlUtils.htmlUnescape((String)multi.getParameter("fileDesc"));
            verInfo.setFileDesc(fileDesc);
            String verSerPatchInfo = HtmlUtils.htmlUnescape((String)multi.getParameter("verSerPatchInfo"));
            verInfo.setVerSerPatchInfo(verSerPatchInfo);

            if(String.valueOf(UseYn.Y).equals(multi.getParameter("useYn"))){
                verInfo.setUseYn(UseYn.Y);
            } else {
                verInfo.setUseYn(UseYn.N);
            }

            verInfo.setRegDt(insertDt);
            verInfo.setRegId(sessionInfo.getUserId());
            verInfo.setModDt(insertDt);
            verInfo.setModId(sessionInfo.getUserId());

            // 버전등록
            if(verManageMapper.verRegist(verInfo) > 0) {
                // 상세내역 등록
                if(verManageMapper.verPatchInfoRegist(verInfo) > 0) {
                    isSuccess = true;
                }
            } else {
                isSuccess = false;
            }

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;
    }

    /** 버전 수정 */
    @Override
    public boolean modify(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        boolean isSuccess = false;

        try{
            VerInfoVO verInfo = uploadFile(multi);

            String insertDt = currentDateTimeString();

            /*String pgmYn = "N";
            String imgYn = "N";
            String dbYn = "N";

            if(Boolean.valueOf(multi.getParameter("pgmYn"))  == true) {
                pgmYn = "Y";
            }
            if(Boolean.valueOf(multi.getParameter("imgYn")) == true) {
                imgYn = "Y";
            }
            if(Boolean.valueOf(multi.getParameter("dbYn")) == true) {
                dbYn = "Y";
            }*/

            verInfo.setVerSerNo((String)multi.getParameter("verSerNo"));
            verInfo.setVerSerNm((String)multi.getParameter("verSerNm"));
            verInfo.setProgFg((String)multi.getParameter("progFg"));
            verInfo.setPgmYn((String)multi.getParameter("pgmYn"));
            verInfo.setImgYn((String)multi.getParameter("imgYn"));
            verInfo.setDbYn((String)multi.getParameter("dbYn"));
            verInfo.setOrgnCds((String)multi.getParameter("orgnCds"));
            verInfo.setProgDetailFg((String)multi.getParameter("progDetailFg"));
            verInfo.setSystemTypeFg((String)multi.getParameter("systemTypeFg"));
            verInfo.setAgencyDispYn((String)multi.getParameter("agencyDispYn"));

            verInfo.setDelYn("N");

            String fileDesc = HtmlUtils.htmlUnescape((String)multi.getParameter("fileDesc"));
            verInfo.setFileDesc(fileDesc);

            String verSerPatchInfo = HtmlUtils.htmlUnescape((String)multi.getParameter("verSerPatchInfo"));
            verInfo.setVerSerPatchInfo(verSerPatchInfo);

            if(String.valueOf(UseYn.Y).equals(multi.getParameter("useYn"))){
                verInfo.setUseYn(UseYn.Y);
            } else {
                verInfo.setUseYn(UseYn.N);
            }

            verInfo.setRegDt(insertDt);
            verInfo.setRegId(sessionInfo.getUserId());
            verInfo.setModDt(insertDt);
            verInfo.setModId(sessionInfo.getUserId());

            verManageMapper.verModify(verInfo);
            // 상세내역 수정
            verManageMapper.verPatchInfoRegist(verInfo);

            isSuccess = true;

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;

    }

    /** 파일 업로드 (등록, 수정 )  */
    private VerInfoVO uploadFile(MultipartHttpServletRequest multi) {
        VerInfoVO verInfo = new VerInfoVO();

        // 저장 경로 설정 (개발시 로컬)
//        String root = multi.getSession().getServletContext().getRealPath("/");
//        String path = root+"resources/upload/";

        // 파일서버 대응 경로 지정 (운영)
        String path = BaseEnv.FILE_UPLOAD_DIR + "posVer/";
        // 업로드 되는 파일명
        String newFileName = "";

        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }

        Iterator<String> files = multi.getFileNames();

        while(files.hasNext()){

            String uploadFile = files.next();
            newFileName = String.valueOf(System.currentTimeMillis());

            MultipartFile mFile = multi.getFile(uploadFile);
            String orgFileName = mFile.getOriginalFilename();
            String fileExt = FilenameUtils.getExtension(orgFileName);

            if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                orgFileName = mFile.getOriginalFilename().substring(0, mFile.getOriginalFilename().lastIndexOf('.'));
                // 파일경로
                verInfo.setFileDir(path);
                // 파일명 (물리적으로 저장되는 파일명)
                verInfo.setFileNm(newFileName);
                // 파일확장자
                verInfo.setFileExt(fileExt);
                // 파일사이즈
                Long fileSize = mFile.getSize();
                verInfo.setFileSize(fileSize.intValue());
                // 파일 MIME_TYPE
                verInfo.setFileMimeType(mFile.getContentType());
                // 원본 파일명
                verInfo.setFileOrgNm(orgFileName);
            }

            try {
                mFile.transferTo(new File(path+newFileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return verInfo;
    }


    /** 매장검색 (매장추가용) */
    @Override
    public List<DefaultMap<String>> srchStoreList(ApplcStoreVO applcStore, SessionInfoVO sessionInfoVO) {

        applcStore.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 매장코드 복수검색 추가(미적용매장 리스트 검색시에만 사용)
        if(applcStore.getSearchSatus() != null && "N".equals(applcStore.getSearchSatus())){
            if(applcStore.getChkMulti() != null && "Y".equals(applcStore.getChkMulti())){
                if(!StringUtil.getOrBlank(applcStore.getStoreCd()).equals("")) {
                    StoreVO storeVO = new StoreVO();
                    storeVO.setArrSplitStoreCd(CmmUtil.splitText(applcStore.getStoreCd(), 3900));
                    applcStore.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
                    applcStore.setStoreCd("");
                }
            }
        }

        return verManageMapper.srchStoreList(applcStore);
    }

    /** 버전 적용 매장 등록 */
    @Override
    public int registStore(ApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {

        int procCnt = 0;

        String dt = currentDateTimeString();

        for(ApplcStoreVO applcStore : applcStores) {
            applcStore.setRegDt(dt);
            applcStore.setModDt(dt);
            applcStore.setRegId(sessionInfo.getUserId());
            applcStore.setModId(sessionInfo.getUserId());
            applcStore.setVerRecvFg(VerRecvFg.REG);
            applcStore.setVerRecvDt(dt);;

            String result = verManageMapper.registStore(applcStore);
            verManageMapper.adverStoreRegInfo(applcStore);
            procCnt++;
        }

        return procCnt;
    }

    /** 버전 적용 매장 삭제 */
    @Override
    public int removeStore(ApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {

        int procCnt = 0;

        for(ApplcStoreVO applcStore : applcStores) {
            applcStore.setRegId(sessionInfo.getUserId());
            procCnt = verManageMapper.removeStore(applcStore);

            verManageMapper.adverStoreRegInfo(applcStore);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
//
//        if(procCnt == applcStores.length) {
//            return procCnt;
//        } else {
//            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }
        return procCnt;
    }

    /** 포스버전정보 상세내역 조회 */
    @Override
    public String getPatchInfo(VerInfoVO verInfo) {
        return  verManageMapper.getPatchInfo(verInfo);
    }

    /** 버전관리 삭제정보 팝업 - 조회 */
    @Override
    public DefaultMap<Object> getVerDelInfoList(VerInfoVO verInfoVO, SessionInfoVO sessionInfoVO) {

        return verManageMapper.getVerDelInfoList(verInfoVO);
    }

    /** 버전관리 삭제정보 팝업 - 삭제 */
    @Override
    public int getVerDelInfoDelete(VerInfoVO verInfoVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        verInfoVO.setUserId(sessionInfoVO.getUserId());

        // TB_CM_POS_VERSN
        procCnt = verManageMapper.getVerDelInfoPosVersnDelete(verInfoVO);

           // TB_CM_POS_VERSN_INFO
        procCnt = verManageMapper.getVerDelInfoPosVersnInfoDelete(verInfoVO);

        // TB_CM_POS_VERSN_STORE
        procCnt = verManageMapper.getVerDelInfoPosVersnStoreDelete(verInfoVO);

        return procCnt;
    }
}