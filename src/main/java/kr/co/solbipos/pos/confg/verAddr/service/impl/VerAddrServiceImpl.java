package kr.co.solbipos.pos.confg.verAddr.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.verAddr.service.AddrApplcStoreVO;
import kr.co.solbipos.pos.confg.verAddr.service.AddrVerInfoVO;
import kr.co.solbipos.pos.confg.verAddr.service.VerAddrService;
import kr.co.solbipos.pos.confg.verrecv.enums.VerRecvFg;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
* @Class Name : VerAddrServiceImpl.java
* @Description : 포스관리 > POS 설정관리 > 주소 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.05.10  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021.05.10
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("verAddrService")
public class VerAddrServiceImpl implements VerAddrService {

    private final VerAddrMapper verAddrMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public VerAddrServiceImpl(VerAddrMapper verAddrMapper, MessageService messageService) {
        this.verAddrMapper = verAddrMapper;
        this.messageService = messageService;
    }

    /** 포스버전 목록 조회 */
    @Override
    public List<DefaultMap<String>> list(AddrVerInfoVO verInfo) {
        return verAddrMapper.getList(verInfo);
    }

    /** 포스버전정보 상세 조회 */
    @Override
    public DefaultMap<String> dtlInfo(AddrVerInfoVO verInfo) {
        return verAddrMapper.dtlInfo(verInfo);
    }

    /** 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> storeList(AddrVerInfoVO verInfo) {
        return verAddrMapper.storeList(verInfo);
    }

    /** 버전 삭제 */
    @Override
    public int verDelete(AddrVerInfoVO verInfo) {
        return verAddrMapper.verDelete(verInfo);
    }

    /** 버전 시리얼넘버 중복 체크 */
    @Override
    public int chkVerSerNo(AddrVerInfoVO verInfo) {
        return verAddrMapper.chkVerSerNo(verInfo);
    }

    /** 버전 등록 */
    @Override
    public boolean regist(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        boolean isSuccess = false;

        try{

            AddrVerInfoVO verInfo = uploadFile(multi);

            String insertDt = currentDateTimeString();
            String pgmYn = (String) multi.getParameter("pgmYn") == "true" ? "Y": "N";
            String imgYn = (String)multi.getParameter("imgYn")== "true" ? "Y": "N";
            String dbYn = (String)multi.getParameter("dbYn")== "true" ? "Y": "N";


            verInfo.setVerSerNo((String)multi.getParameter("verSerNo"));
            verInfo.setVerSerNm((String)multi.getParameter("verSerNm"));
            verInfo.setFileDesc((String)multi.getParameter("fileDesc"));
            verInfo.setProgFg((String)multi.getParameter("progFg"));
            verInfo.setPgmYn(pgmYn);
            verInfo.setImgYn(imgYn);
            verInfo.setDbYn(dbYn);
            verInfo.setDelYn("N");

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
            if(verAddrMapper.verRegist(verInfo) > 0) {
                isSuccess = true;
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
            AddrVerInfoVO verInfo = uploadFile(multi);

            String insertDt = currentDateTimeString();

            String pgmYn = "N";
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
            }

            verInfo.setVerSerNo((String)multi.getParameter("verSerNo"));
            verInfo.setVerSerNm((String)multi.getParameter("verSerNm"));
            verInfo.setFileDesc((String)multi.getParameter("fileDesc"));
            verInfo.setProgFg((String)multi.getParameter("progFg"));
            verInfo.setPgmYn(pgmYn);
            verInfo.setImgYn(imgYn);
            verInfo.setDbYn(dbYn);
            verInfo.setDelYn("N");

            if(String.valueOf(UseYn.Y).equals(multi.getParameter("useYn"))){
                verInfo.setUseYn(UseYn.Y);
            } else {
                verInfo.setUseYn(UseYn.N);
            }

            verInfo.setRegDt(insertDt);
            verInfo.setRegId(sessionInfo.getUserId());
            verInfo.setModDt(insertDt);
            verInfo.setModId(sessionInfo.getUserId());

            verAddrMapper.verModify(verInfo);

            isSuccess = true;

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;

    }

    /** 파일 업로드 (등록, 수정 )  */
    private AddrVerInfoVO uploadFile(MultipartHttpServletRequest multi) {
        AddrVerInfoVO verInfo = new AddrVerInfoVO();

        // 저장 경로 설정 (개발시 로컬)
//        String root = multi.getSession().getServletContext().getRealPath("/");
//        String path = root+"resources/upload/";

        // 파일서버 대응 경로 지정 (운영)
        String path = BaseEnv.FILE_UPLOAD_DIR + "posAddrVer/";
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
    public List<DefaultMap<String>> srchStoreList(AddrApplcStoreVO applcStore) {

        // 매장코드 복수검색 추가(미적용매장 리스트 검색시에만 사용)
        if(applcStore.getSearchSatus() != null && "N".equals(applcStore.getSearchSatus())){
            if(applcStore.getChkMulti() != null && "Y".equals(applcStore.getChkMulti())){
                String[] arrStoreCd = applcStore.getStoreCd().split(",");
                if (arrStoreCd.length > 0) {
                    if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                        applcStore.setArrStoreCd(arrStoreCd);
                        applcStore.setStoreCd("");
                    }
                }
            }
        }

        return verAddrMapper.srchStoreList(applcStore);
    }

    /** 버전 적용 매장 등록 */
    @Override
    public int registStore(AddrApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {

        int procCnt = 0;

        String dt = currentDateTimeString();

        for(AddrApplcStoreVO applcStore : applcStores) {
            applcStore.setRegDt(dt);
            applcStore.setModDt(dt);
            applcStore.setRegId(sessionInfo.getUserId());
            applcStore.setModId(sessionInfo.getUserId());
            applcStore.setVerRecvFg(VerRecvFg.REG);
            applcStore.setVerRecvDt(dt);;

            String result = verAddrMapper.registStore(applcStore);
            procCnt++;
        }

        return procCnt;
    }

    /** 버전 적용 매장 삭제 */
    @Override
    public int removeStore(AddrApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {

        int procCnt = 0;

        for(AddrApplcStoreVO applcStore : applcStores) {
            procCnt = verAddrMapper.removeStore(applcStore);
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
}
