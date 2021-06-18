package kr.co.solbipos.base.store.media.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.media.service.MediaApplcStoreVO;
import kr.co.solbipos.base.store.media.service.MediaService;
import kr.co.solbipos.base.store.media.service.MediaVO;
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
import static org.apache.commons.lang3.time.DateUtils.isSameInstant;

/**
* @Class Name : MediaServiceImpl.java
* @Description : 기초관리 > 매장관리 > 미디어관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.06.09  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021.06.09
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("MediaService")
public class MediaServiceImpl implements MediaService {

    private final MediaMapper mediaMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public MediaServiceImpl(MediaMapper mediaMapper, MessageService messageService) {
        this.mediaMapper = mediaMapper;
        this.messageService = messageService;
    }

    /** 포스버전 목록 조회 */
    @Override
    public List<DefaultMap<String>> list(SessionInfoVO sessionInfo, MediaVO mediaVO) {
        mediaVO.setOrgnFg(sessionInfo.getOrgnFg().getCode());
        mediaVO.setHqOfficeCd(sessionInfo.getHqOfficeCd());
        mediaVO.setStoreCd(sessionInfo.getStoreCd());

        return mediaMapper.getList(mediaVO);
    }

    /** 포스버전정보 상세 조회 */
    @Override
    public DefaultMap<String> dtlInfo(SessionInfoVO sessionInfo, MediaVO mediaVO) {
        mediaVO.setOrgnFg(sessionInfo.getOrgnFg().getCode());
        mediaVO.setHqOfficeCd(sessionInfo.getHqOfficeCd());
        mediaVO.setStoreCd(sessionInfo.getStoreCd());

        return mediaMapper.dtlInfo(mediaVO);
    }

    /** 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> storeList(MediaVO mediaVO) {
        return mediaMapper.storeList(mediaVO);
    }

    /** 버전 삭제 */
    @Override
    public int verDelete(MediaVO mediaVO) {
        return mediaMapper.verDelete(mediaVO);
    }

    @Override
    public String chkFileType(SessionInfoVO sessionInfoVO, MediaVO mediaVO) {
        return mediaMapper.chkFileType(mediaVO);
    }

    /** 날짜 체크 */
    @Override
    public String chkDate(SessionInfoVO sessionInfoVO, MediaVO mediaVO) {
        if("H".equals(sessionInfoVO.getOrgnFg().getCode())){
            mediaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        } else {
            mediaVO.setHqOfficeCd("");
            mediaVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mediaMapper.chkDate(mediaVO);
    }

    /** 버전 등록 */
    @Override
    public boolean regist(MultipartHttpServletRequest multi, SessionInfoVO sessionInfoVO) {

        boolean isSuccess = false;

        try{
            MediaVO mediaVO = uploadFile(multi);

            String insertDt = currentDateTimeString();
            mediaVO.setHqOfficeCd((String) multi.getParameter("hqOfficeCd"));
            mediaVO.setStoreCd((String)multi.getParameter("storeCd"));

            mediaVO.setVerSerNo(mediaMapper.getFileCd(mediaVO));

            mediaVO.setVerSerNm((String)multi.getParameter("verSerNm"));

            mediaVO.setStartDate((String)multi.getParameter("startDate"));
            mediaVO.setEndDate((String)multi.getParameter("endDate"));
            mediaVO.setFileType((String)multi.getParameter("fileType"));

            if(String.valueOf(UseYn.Y).equals(multi.getParameter("useYn"))){
                mediaVO.setUseYn(UseYn.Y);
            } else {
                mediaVO.setUseYn(UseYn.N);
            }

            mediaVO.setRegDt(insertDt);
            mediaVO.setRegId((String)multi.getParameter("userId"));
            mediaVO.setModDt(insertDt);
            mediaVO.setModId((String)multi.getParameter("userId"));

            if(mediaMapper.verRegist(mediaVO) > 0) {
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
            MediaVO mediaVO = uploadFile(multi);

            String insertDt = currentDateTimeString();

            mediaVO.setHqOfficeCd((String)multi.getParameter("hqOfficeCd"));
            mediaVO.setStoreCd((String)multi.getParameter("storeCd"));

            mediaVO.setVerSerNo((String)multi.getParameter("verSerNo"));
            mediaVO.setVerSerNm((String)multi.getParameter("verSerNm"));
            mediaVO.setStartDate((String)multi.getParameter("startDate"));
            mediaVO.setEndDate((String)multi.getParameter("endDate"));
            mediaVO.setFileType((String)multi.getParameter("fileType"));
            mediaVO.setDelYn("N");

            if(String.valueOf(UseYn.Y).equals(multi.getParameter("useYn"))){
                mediaVO.setUseYn(UseYn.Y);
            } else {
                mediaVO.setUseYn(UseYn.N);
            }

            mediaVO.setModDt(insertDt);
            mediaVO.setModId((String)multi.getParameter("userId"));

            mediaMapper.verModify(mediaVO);

            isSuccess = true;

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;

    }

    /** 파일 업로드 (등록, 수정 )  */
    private MediaVO uploadFile(MultipartHttpServletRequest multi) {
        MediaVO mediaVO = new MediaVO();

        // 저장 경로 설정 (개발시 로컬)
        String root = multi.getSession().getServletContext().getRealPath("/");
        String path = root+"resources/upload/";

        // 파일서버 대응 경로 지정 (운영)
//        String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";
        // 업로드 되는 파일명
        String newFileName = "";

        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }

        Iterator<String> files = multi.getFileNames();

        while(files.hasNext()){

            String uploadFile = files.next();

            String rndNm = "";

            for(int i=0; i<8; i++) {
                int rndVal = (int)(Math.random() * 62);
                if(rndVal < 10) {
                    rndNm += rndVal;
                }
            }

            // 파일명 orgnCd + 기존 + 8자리 난수
            newFileName = (String)multi.getParameter("orgnCd")+ String.valueOf(System.currentTimeMillis() + rndNm);

            MultipartFile mFile = multi.getFile(uploadFile);
            String orgFileName = mFile.getOriginalFilename();
            String fileExt = FilenameUtils.getExtension(orgFileName);

            if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                orgFileName = mFile.getOriginalFilename().substring(0, mFile.getOriginalFilename().lastIndexOf('.'));
                // 파일경로
                mediaVO.setFileDir(path);
                // 파일명 (물리적으로 저장되는 파일명)
                mediaVO.setFileNm(newFileName);
                // 파일확장자
                mediaVO.setFileExt(fileExt);
                // 파일사이즈
                Long fileSize = mFile.getSize();
                mediaVO.setFileSize(fileSize.intValue());
                // 파일 MIME_TYPE
                mediaVO.setFileMimeType(mFile.getContentType());
                // 원본 파일명
                mediaVO.setFileOrgNm(orgFileName);
            }

            try {
                mFile.transferTo(new File(path+newFileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mediaVO;
    }

    /** 매장검색 (매장추가용) */
    @Override
    public List<DefaultMap<String>> srchStoreList(MediaApplcStoreVO applcStore, SessionInfoVO sessionInfoVO) {

        applcStore.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 멀티 선택
        if(!StringUtil.getOrBlank(applcStore.getStoreCd()).equals("")) {
            String[] arrStoreCd = applcStore.getStoreCd().split(",");
            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    applcStore.setArrStoreCd(arrStoreCd);
                    applcStore.setStoreCd("");
                }
            }
        }

        return mediaMapper.srchStoreList(applcStore);
    }

    /** 버전 적용 매장 등록 */
    @Override
    public int registStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {

        int procCnt = 0;

        String dt = currentDateTimeString();

        for(MediaApplcStoreVO applcStore : applcStores) {

            applcStore.setRegDt(dt);
            applcStore.setModDt(dt);
            applcStore.setRegId(sessionInfo.getUserId());
            applcStore.setModId(sessionInfo.getUserId());
            applcStore.setVerRecvFg(VerRecvFg.REG);
            applcStore.setVerRecvDt(dt);;

            int result = mediaMapper.registStore(applcStore);
            procCnt++;
        }

        return procCnt;
    }

    /** 버전 적용 매장 삭제 */
    @Override
    public int removeStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {

        int procCnt = 0;

        for(MediaApplcStoreVO applcStore : applcStores) {
            procCnt = mediaMapper.removeStore(applcStore);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return procCnt;
    }
}
