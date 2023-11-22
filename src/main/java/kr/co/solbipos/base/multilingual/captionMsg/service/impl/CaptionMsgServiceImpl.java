package kr.co.solbipos.base.multilingual.captionMsg.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.captionMsg.service.CaptionMsgService;
import kr.co.solbipos.base.multilingual.captionMsg.service.CaptionMsgVO;
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
 * @Class Name : CaptionMsgServiceImpl.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(기능키/메시지)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("CaptionMsgService")
public class CaptionMsgServiceImpl implements CaptionMsgService {

    private final CaptionMsgMapper captionMsgMapper;
    private final MessageService messageService;

    @Autowired
    public CaptionMsgServiceImpl(CaptionMsgMapper captionMsgMapper, MessageService messageService) {

        this.captionMsgMapper = captionMsgMapper;
        this.messageService = messageService;
    }


    /** 화면구분 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getCaptionMsgGrpComboList(CaptionMsgVO captionMsgVO, SessionInfoVO sessionInfoVO){

        captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return captionMsgMapper.getCaptionMsgGrpComboList(captionMsgVO);
    }

    /** 화면구분 선택에 따른 기능키/메시지 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCaptionMsgList(CaptionMsgVO captionMsgVO, SessionInfoVO sessionInfoVO) {

        captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return captionMsgMapper.getCaptionMsgList(captionMsgVO);
    }

    /** 기능키 or 메시지코드 중복체크 */
    @Override
    public String chkCaptionMsgId(CaptionMsgVO captionMsgVO, SessionInfoVO sessionInfoVO){
        captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        captionMsgVO.setArrCaptionMsgId(captionMsgVO.getCaptionMsgId().split(","));
        return captionMsgMapper.chkCaptionMsgId(captionMsgVO);
    }

    /** 기능키/메시지 저장 */
    @Override
    public int saveCaptionMsg(CaptionMsgVO[] captionMsgVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String dt = currentDateTimeString();

        for (CaptionMsgVO captionMsgVO : captionMsgVOs) {

            captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            captionMsgVO.setRegDt(dt);
            captionMsgVO.setRegId(sessionInfoVO.getUserId());
            captionMsgVO.setModDt(dt);
            captionMsgVO.setModId(sessionInfoVO.getUserId());

            result += captionMsgMapper.saveCaptionMsg(captionMsgVO);
        }

        if (result == captionMsgVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 기능키/메시지 삭제*/
    @Override
    public int deleteCaptionMsg(CaptionMsgVO[] captionMsgVOs, SessionInfoVO sessionInfoVO){

        int procCnt = 0;

        for(CaptionMsgVO captionMsgVO : captionMsgVOs) {

            captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = captionMsgMapper.deleteCaptionMsg(captionMsgVO);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return procCnt;
    }

    /** 화면구분등록 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCaptionMsgGrpList(CaptionMsgVO captionMsgVO, SessionInfoVO sessionInfoVO) {

        captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return captionMsgMapper.getCaptionMsgGrpList(captionMsgVO);
    }

    /** 화면구분 상세 조회 */
    @Override
    public DefaultMap<String> getCaptionMsgGrpDtl(CaptionMsgVO captionMsgVO, SessionInfoVO sessionInfoVO) {

        captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return captionMsgMapper.getCaptionMsgGrpDtl(captionMsgVO);
    }

    /** 화면구분 신규 등록 */
    @Override
    public String saveCaptionMsgGrp(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        String isSuccess;

        try{
            CaptionMsgVO captionMsgVO = uploadFile(multi);

            if(captionMsgVO.getResult() == null){
                isSuccess = "2";
                return isSuccess;
            }

            if (!captionMsgVO.getResult().equals("T")){
                isSuccess = "2";
                return isSuccess;
            }

            String insertDt = currentDateTimeString();
            captionMsgVO.setHqOfficeCd((String) multi.getParameter("hqOfficeCd"));
            captionMsgVO.setStoreCd((String)multi.getParameter("storeCd"));
            captionMsgVO.setCaptionImgCd(captionMsgMapper.getCaptionImgCd(captionMsgVO));
            captionMsgVO.setCaptionImgNm((String)multi.getParameter("captionImgNm"));
            captionMsgVO.setFileDesc((String)multi.getParameter("fileDesc"));
            captionMsgVO.setRegDt(insertDt);
            captionMsgVO.setRegId((String)multi.getParameter("userId"));
            captionMsgVO.setModDt(insertDt);
            captionMsgVO.setModId((String)multi.getParameter("userId"));

            if(captionMsgVO.getFileOrgNm().length() > 0){
                String orgFileNm[] = captionMsgVO.getFileOrgNm().split("\\\\");
                captionMsgVO.setFileOrgNm(orgFileNm[orgFileNm.length-1]);
            } else {
                captionMsgVO.setFileOrgNm(captionMsgVO.getCaptionImgCd());
            }

            if(captionMsgMapper.saveCaptionMsgGrp(captionMsgVO) > 0) {
                isSuccess = "0";
            } else {
                isSuccess = "1";
            }

        }catch(Exception e){
            isSuccess = "1";
        }
        return isSuccess;
    }

    /** 화면구분 수정*/
    @Override
    public String updateCaptionMsgGrp(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        String isSuccess;

        try{
            CaptionMsgVO captionMsgVO = uploadFile(multi);

            if(captionMsgVO.getResult() == null){
                isSuccess = "2";
                return isSuccess;
            }

            if (!captionMsgVO.getResult().equals("T")){
                isSuccess = "2";
                return isSuccess;
            }

            String insertDt = currentDateTimeString();
            captionMsgVO.setHqOfficeCd((String) multi.getParameter("hqOfficeCd"));
            captionMsgVO.setStoreCd((String)multi.getParameter("storeCd"));
            captionMsgVO.setCaptionImgCd((String)multi.getParameter("captionImgCd"));
            captionMsgVO.setCaptionImgNm((String)multi.getParameter("captionImgNm"));
            captionMsgVO.setFileDesc((String)multi.getParameter("fileDesc"));
            captionMsgVO.setRegDt(insertDt);
            captionMsgVO.setRegId((String)multi.getParameter("userId"));
            captionMsgVO.setModDt(insertDt);
            captionMsgVO.setModId((String)multi.getParameter("userId"));

            // 파일이 수정 된 경우
            if(captionMsgVO.getFileOrgNm() != null) {

                // 2. 기존에 등록된 파일 삭제
                // 저장 경로 설정 (개발시 로컬)
                //String path = "D:\\" + "Media/";

                // 파일서버 대응 경로 지정 (운영)
                String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";

                DefaultMap<String> fileInfo = captionMsgMapper.getCaptionMsgGrpDtl(captionMsgVO);
                if (fileInfo.size() > 0) {
                    // 서버 파일 삭제
                    File delFile = new File(path + fileInfo.get("fileNm"));
                    if (delFile.exists()) {
                        delFile.delete();
                    }
                }

                if(captionMsgVO.getFileOrgNm().length() > 0){
                    String orgFileNm[] = captionMsgVO.getFileOrgNm().split("\\\\");
                    captionMsgVO.setFileOrgNm(orgFileNm[orgFileNm.length-1]);
                } else {
                    captionMsgVO.setFileOrgNm(captionMsgVO.getCaptionImgCd());
                }
            }

            // 수정
            captionMsgMapper.updateCaptionMsgGrp(captionMsgVO);

            isSuccess = "0";

        }catch(Exception e){

            isSuccess = "1";
        }
        return isSuccess;

    }

    /** 파일 업로드 (등록, 수정)  */
    private CaptionMsgVO uploadFile(MultipartHttpServletRequest multi) {

        CaptionMsgVO captionMsgVO = new CaptionMsgVO();

        // 저장 경로 설정 (개발시 로컬)
        //String path = "D:\\" + "Media/";

        // 파일서버 대응 경로 지정 (운영)
        String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";

        // 업로드 되는 파일명
        String newFileName = "";

        File dir = new File(path);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        Iterator<String> files = multi.getFileNames();

        while (files.hasNext()) {

            String uploadFile = files.next();

            String rndNm = "";

            for (int i = 0; i < 8; i++) {
                int rndVal = (int) (Math.random() * 62);
                if (rndVal < 10) {
                    rndNm += rndVal;
                }
            }

            // 파일명 orgnCd + 기존 + 8자리 난수
            newFileName = (String) multi.getParameter("orgnCd") + String.valueOf(System.currentTimeMillis() + rndNm);

            MultipartFile mFile = multi.getFile(uploadFile);
            String orgFileName = mFile.getOriginalFilename();
            String fileExt = FilenameUtils.getExtension(orgFileName);

            // 수정 시 파일은 수정하지 않고 다른 정보만 수정 할 경우
            if (!orgFileName.equals("")) {

                String type = ".png,.PNG";                         // 확장자|확장자|확장자 를
                String[] typeList = type.split(",");         // |기준으로 잘라서 배열에 넣음

                for(int i = 0; i < typeList.length; i++){          // 잘린 확장자 확인
                    if(fileExt.equals(typeList[i].replace(".",""))){
                        captionMsgVO.setResult("T");
                    }
                }

                if (mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    orgFileName = mFile.getOriginalFilename().substring(0, mFile.getOriginalFilename().lastIndexOf('.'));
                    // 파일경로
                    captionMsgVO.setFileDir(path);
                    // 파일명 (물리적으로 저장되는 파일명)
                    captionMsgVO.setFileNm(newFileName);
                    // 파일확장자
                    captionMsgVO.setFileExt(fileExt);
                    // 파일사이즈
                    Long fileSize = mFile.getSize();
                    captionMsgVO.setFileSize(fileSize.intValue());
                    // 파일 MIME_TYPE
                    captionMsgVO.setFileMimeType(mFile.getContentType());
                    // 원본 파일명
                    captionMsgVO.setFileOrgNm(orgFileName);
                }

                try {
                    mFile.transferTo(new File(path + newFileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                captionMsgVO.setResult("T");
            }
        }

        return captionMsgVO;
    }

    /** 화면구분 삭제*/
    @Override
    public int deleteCaptionMsgGrp(CaptionMsgVO[] captionMsgVOs, SessionInfoVO sessionInfoVO){

        int procCnt = 0;

        for(CaptionMsgVO captionMsgVO : captionMsgVOs) {

            captionMsgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = captionMsgMapper.deleteCaptionMsgGrp(captionMsgVO);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));


            // 저장 경로 설정 (개발시 로컬)
            //String path = "D:\\" + "Media/";

            // 파일서버 대응 경로 지정 (운영)
            String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";

            String pathFull = path + captionMsgVO.getFileNm();
            File delFile = new File(pathFull);
            if(delFile.exists()) {
                delFile.delete();
            }
        }

        return procCnt;
    }

}
