package kr.co.solbipos.adi.sms.smsSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendService;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsSendServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("smsSendService")
@Transactional
public class SmsSendServiceImpl implements SmsSendService {
    private final SmsSendMapper smsSendMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendServiceImpl(SmsSendMapper smsSendMapper) {
        this.smsSendMapper = smsSendMapper;
    }

    /** 발신번호 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsTelNoComboList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsSendMapper.getSmsTelNoComboList(smsSendVO);
    }

    /** 관리자/총판/본사/매장 명칭 조회 */
    @Override
    public DefaultMap<Object> getStoreNmList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setUserId(sessionInfoVO.getUserId());

        return smsSendMapper.getStoreNmList(smsSendVO);
    }

    /** 잔여금액 조회 */
    @Override
    public DefaultMap<Object> getSmsAmtList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsSendMapper.getSmsAmtList(smsSendVO);
    }

    /** 전송,예약 저장 */
    @Override
    public int getSmsSendReserveSave(SmsSendVO[] smsSendVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        int rowCount = 1; // 홀수,짝수

        // 전송이력시퀀스
        String smsSendSeq = "";
        // SMS전송 팝업
        if(("smsSend").equals(smsSendVOs[0].getPageGubun())) {
            smsSendSeq = smsSendMapper.getSmsSendSeq(sessionInfoVO);
        // 마케팅용 SMS전송
        } else {
            smsSendSeq = smsSendVOs[0].getSmsSendSeq();
        }
        LOGGER.info("WEB_SMS >>> SMS전송 >>> 전송이력시퀀스 : " + smsSendSeq);

        SmsSendVO smsSendChkVO = new SmsSendVO();
        smsSendChkVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsSendChkVO.setModDt(currentDt);
        smsSendChkVO.setModId(sessionInfoVO.getUserId());
        DefaultMap<Object> result = smsSendMapper.getSmsAmtList(smsSendChkVO);
        // 현재 잔여금액
        String smsAmt = result.getStr("smsAmt");
        // 현재 사용금액
        String useSmsAmt = String.valueOf( Integer.parseInt(smsSendVOs[0].getMsgOneAmt()) * Integer.parseInt(smsSendVOs[0].getSmsSendListCnt()) );
        LOGGER.info("WEB_SMS >>> SMS전송 >>> 현재 잔여금액 : " + smsAmt);
        LOGGER.info("WEB_SMS >>> SMS전송 >>> 현재 사용금액 : " + useSmsAmt);

        // SMS잔여금액 > SMS사용금액 체크
        if((Long.parseLong(smsAmt) - Long.parseLong(useSmsAmt)) > 0) {
            // 잔여금액 - 사용금액
            smsSendChkVO.setSmsAmt(String.valueOf( Long.parseLong(smsAmt) -  Long.parseLong(useSmsAmt) ));
            LOGGER.info("WEB_SMS >>> SMS전송 >>> 수정될 잔여금액 : " + smsSendChkVO.getSmsAmt());

            // 잔여금액 저장 update
            procCnt = smsSendMapper.getSmsAmtSaveUpdate(smsSendChkVO);

            // SMS전송
            for(SmsSendVO smsSendVO : smsSendVOs) {

                smsSendVO.setRegDt(currentDt);
                smsSendVO.setRegId(sessionInfoVO.getUserId());
                smsSendVO.setModDt(currentDt);
                smsSendVO.setModId(sessionInfoVO.getUserId());

                smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

                // 송신자
                smsSendVO.setSsOrgnCd(sessionInfoVO.getOrgnCd());
                smsSendVO.setSsOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                smsSendVO.setSsUserId(sessionInfoVO.getUserId());

                // 수신자
                if(smsSendVO.getRrOrgnFg().equals("C")) {
                    smsSendVO.setRrOrgnCd(sessionInfoVO.getOrgnCd());
                } else {
                    smsSendVO.setRrOrgnCd(smsSendVO.getRrOrgnCd());
                }
                smsSendVO.setRrOrgnFg(smsSendVO.getRrOrgnFg());
                smsSendVO.setRrUserId(smsSendVO.getRrUserId());

                // 송신주체 소속코드
                smsSendVO.setSmsOgnCd(sessionInfoVO.getOrgnCd());

                // 회원관리주체 소속코드
                if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ || sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    if(sessionInfoVO.getHqOfficeCd().equals("00000")){
                        smsSendVO.setCstOgnCd(sessionInfoVO.getStoreCd());
                    } else {
                        smsSendVO.setCstOgnCd(sessionInfoVO.getHqOfficeCd());
                    }
                } else {
                    smsSendVO.setCstOgnCd("*");
                }

                // 전송일시
                if(smsSendVO.getReserveYn().equals("1")) { // 예약
                    smsSendVO.setSendDate(smsSendVO.getSendDate());
                } else { // 즉시
                    smsSendVO.setSendDate(currentDt);
                }

                // 전송이력시퀀스
                smsSendVO.setSmsSendSeq(smsSendSeq);

                // 전송이력 저장
                if(rowCount == 1) {
                    // 전송건수
                    smsSendVO.setSmsSendCount("0");

                    // 전송이력 저장
                    LOGGER.info("WEB_SMS >>> SMS전송 >>> 전송이력 저장");
                    procCnt = smsSendMapper.getSmsSendSeqSaveInsert(smsSendVO);
                }

                LOGGER.info("WEB_SMS >>> SMS전송 >>> 메세지타입 : " + smsSendVO.getMsgType());
                LOGGER.info("WEB_SMS >>> SMS전송 >>> SMS전송 저장");

                // SMS
                if(smsSendVO.getMsgType().equals("1")) {
                    procCnt = smsSendMapper.getSmsSendReserveSaveInsert(smsSendVO); // SDK_SMS_SEND_ENC

                // LMS, MMS
                } else if(smsSendVO.getMsgType().equals("2") || smsSendVO.getMsgType().equals("3")) {
                    procCnt = smsSendMapper.getSmsSendReserveSaveInsertLMS(smsSendVO); // SDK_MMS_SEND_ENC
                }

                rowCount = rowCount + 1; // 홀수,짝수
            }
        }

        return procCnt;
    }

    /** 전송,예약 1000건 저장 */
    @Override
    public int getSmsSendReserve1000Save(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        smsSendVO.setRegDt(currentDt);
        smsSendVO.setRegId(sessionInfoVO.getUserId());
        smsSendVO.setModDt(currentDt);
        smsSendVO.setModId(sessionInfoVO.getUserId());

        smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        // 송신자
        smsSendVO.setSsOrgnCd(sessionInfoVO.getOrgnCd());
        smsSendVO.setSsOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        smsSendVO.setSsUserId(sessionInfoVO.getUserId());

        // 수신자
        if(smsSendVO.getRrOrgnFg().equals("C")) {
            smsSendVO.setRrOrgnCd(sessionInfoVO.getOrgnCd());
        } else {
            smsSendVO.setRrOrgnCd(smsSendVO.getRrOrgnCd());
        }
        smsSendVO.setRrOrgnFg(smsSendVO.getRrOrgnFg());
        smsSendVO.setRrUserId(smsSendVO.getRrUserId());

        // 송신주체 소속코드
        smsSendVO.setSmsOgnCd(sessionInfoVO.getOrgnCd());

        // 회원관리주체 소속코드
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ || sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            if(sessionInfoVO.getHqOfficeCd().equals("00000")){
                smsSendVO.setCstOgnCd(sessionInfoVO.getStoreCd());
            } else {
                smsSendVO.setCstOgnCd(sessionInfoVO.getHqOfficeCd());
            }
        } else {
            smsSendVO.setCstOgnCd("*");
        }

        // 전송일시
        if(smsSendVO.getReserveYn().equals("1")) { // 예약
            smsSendVO.setSendDate(smsSendVO.getSendDate());
        } else { // 즉시
            smsSendVO.setSendDate(currentDt);
        }

        // 전송이력시퀀스
        smsSendVO.setSmsSendSeq(smsSendVO.getSmsSendSeq());
        LOGGER.info("WEB_SMS >>> SMS전송 >>> 전송이력시퀀스 : " + smsSendVO.getSmsSendSeq());

        // 전송건수
        smsSendVO.setSmsSendCount("0");

        DefaultMap<Object> result = smsSendMapper.getSmsAmtList(smsSendVO);
        // 현재 잔여금액
        String smsAmt = result.getStr("smsAmt");
        // 현재 사용금액
        String useSmsAmt = String.valueOf( Integer.parseInt(smsSendVO.getMsgOneAmt()) * Integer.parseInt(smsSendVO.getSmsSendListCnt()) );
        LOGGER.info("WEB_SMS >>> SMS전송 >>> 현재 잔여금액 : " + smsAmt);
        LOGGER.info("WEB_SMS >>> SMS전송 >>> 현재 사용금액 : " + useSmsAmt);

        // SMS잔여금액 > SMS사용금액 체크
        if((Long.parseLong(smsAmt) - Long.parseLong(useSmsAmt)) > 0) {
            // 잔여금액 - 사용금액
            smsSendVO.setSmsAmt(String.valueOf( Long.parseLong(smsAmt) -  Long.parseLong(useSmsAmt) ));
            LOGGER.info("WEB_SMS >>> SMS전송 >>> 수정될 잔여금액 : " + smsSendVO.getSmsAmt());

            // 잔여금액 저장 update
            procCnt = smsSendMapper.getSmsAmtSaveUpdate(smsSendVO);

            // 전송이력 저장
            smsSendVO.setPhoneNumber("00000000000");
            LOGGER.info("WEB_SMS >>> SMS전송 >>> 전송이력 저장");
            procCnt = smsSendMapper.getSmsSendSeqSaveInsert(smsSendVO);

            LOGGER.info("WEB_SMS >>> SMS전송 >>> 메세지타입 : " + smsSendVO.getMsgType());
            LOGGER.info("WEB_SMS >>> SMS전송 >>> SMS전송 저장");

            // SMS
            if(smsSendVO.getMsgType().equals("1")) {
                procCnt = smsSendMapper.getSmsSendReserve1000SaveInsert(smsSendVO); // SDK_SMS_SEND_ENC_READY

            // LMS, MMS
            } else if(smsSendVO.getMsgType().equals("2") || smsSendVO.getMsgType().equals("3")) {
                procCnt = smsSendMapper.getSmsSendReserve1000SaveInsertLMS(smsSendVO); // SDK_MMS_SEND_ENC_READY
            }
        }

        return procCnt;
    }

    /** 첨부파일 저장 */
    @Override
    public String getSmsSendFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

//        System.out.println("test1111");
        boolean isSuccess = true;

        // 전송할 컨텐츠(파일명^컨텐츠타입^컨텐츠서브타입)
        String contentData = "";

        try{

            String fileId = multi.getParameter("pageGubun");

            // 저장경로 폴더
            String path_folder = "sms_img/";

            // 저장 경로 설정 (개발시 로컬) (파일 저장용)
//            String path = "D:\\Workspace\\javaWeb\\testSmsImg\\";

            // 파일서버 대응 경로 지정 (운영) (파일 저장용)
            String path = BaseEnv.FILE_UPLOAD_DIR + path_folder;

            LOGGER.info("WEB_SMS >>> SMS전송 >>> MSS 파일 저장 >>> 파일 저장 경로 : " + path);

            // 저장 경로 설정 (디비 저장용)
            String path_table = path_folder;

            // 업로드 되는 파일명
            String newFileName = "";
            // 원본 파일명
            String orgFileName = "";

            // 경로에 폴도가 있는지 체크
            File dir = new File(path);
            if(!dir.isDirectory()){
                dir.mkdir();
            }

            // 첨부파일1
//            List<MultipartFile> fileList = multi.getFiles("fileSms1");
            List<MultipartFile> fileList = multi.getFiles(fileId + "1");
            // 선택한 파일이 있으면
            for(MultipartFile mFile : fileList)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename(); // 원본 파일명
                String fileExt = FilenameUtils.getExtension(orgFileName); // 파일확장자

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    // 파일 저장하는 부분
                    try {
//                        mFile.transferTo(new File(path+newFileName+"."+fileExt));
                        File destFile = new File(path+newFileName+"."+fileExt);
                        mFile.transferTo(destFile);
                        Runtime.getRuntime().exec("chmod -R 664 " + destFile); // 권한
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    contentData = contentData + path_table + newFileName + "." + fileExt + "^1^0|";
                }
            }

            // 첨부파일2
//            List<MultipartFile> fileList2 = multi.getFiles("fileSms2");
            List<MultipartFile> fileList2 = multi.getFiles(fileId + "2");
            // 선택한 파일이 있으면
            for(MultipartFile mFile : fileList2)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename(); // 원본 파일명
                String fileExt = FilenameUtils.getExtension(orgFileName); // 파일확장자

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    // 파일 저장하는 부분
                    try {
//                        mFile.transferTo(new File(path+newFileName+"."+fileExt));
                        File destFile = new File(path+newFileName+"."+fileExt);
                        mFile.transferTo(destFile);
                        Runtime.getRuntime().exec("chmod -R 664 " + destFile); // 권한
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    contentData = contentData + path_table + newFileName + "." + fileExt + "^1^0|";
                }
            }

            // 첨부파일3
//            List<MultipartFile> fileList3 = multi.getFiles("fileSms3");
            List<MultipartFile> fileList3 = multi.getFiles(fileId + "3");
            // 선택한 파일이 있으면
            for(MultipartFile mFile : fileList3)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename(); // 원본 파일명
                String fileExt = FilenameUtils.getExtension(orgFileName); // 파일확장자

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    // 파일 저장하는 부분
                    try {
//                        mFile.transferTo(new File(path+newFileName+"."+fileExt));
                        File destFile = new File(path+newFileName+"."+fileExt);
                        mFile.transferTo(destFile);
                        Runtime.getRuntime().exec("chmod -R 664 " + destFile); // 권한
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    contentData = contentData + path_table + newFileName + "." + fileExt + "^1^0|";
                }
            }

            LOGGER.info("WEB_SMS >>> SMS전송 >>> MSS 파일 저장 >>> 저장할 CONTENT_DATA 컬럼값 : " + contentData);

        }catch(Exception e){

            isSuccess = false;
        }

        return contentData;
    }

    /** 수신자추가 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAddresseeAddList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            smsSendVO.setAgencyCd(sessionInfoVO.getOrgnCd());

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            smsSendVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            smsSendVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return smsSendMapper.getAddresseeAddList(smsSendVO);
    }

    /** 일반번호 인증요청 팝업 - 저장 */
    @Override
    public int getSmsGeneralNoRegisterSave(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        smsSendVO.setRegDt(currentDt);
        smsSendVO.setRegId(sessionInfoVO.getUserId());
        smsSendVO.setModDt(currentDt);
        smsSendVO.setModId(sessionInfoVO.getUserId());

        smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsSendVO.setUserId(sessionInfoVO.getUserId());

        procCnt = smsSendMapper.getSmsGeneralNoRegisterSaveInsert(smsSendVO);

        return procCnt;
    }

    /** 일반번호 인증요청 팝업 - 첨부파일 저장 */
    @Override
    public String getSmsGeneralNoFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        boolean isSuccess = true;

        // 저장할 컨텐츠(파일경로^파일명)
        String contentData = "";

        try{

            // 저장 경로 설정 (개발시 로컬)
//            String path = "D:\\Workspace\\javaWeb\\testBoardAtch\\addSmsNo\\";

            // 파일서버 대응 경로 지정 (운영) (파일 저장용)
            String path = BaseEnv.FILE_UPLOAD_DIR + "board/addSmsNo/";

            // 저장 경로 설정 (디비 저장용)
            String path_table = "board/addSmsNo/";

            // 업로드 되는 파일명
            String newFileName = "";
            // 원본 파일명
            String orgFileName = "";

            // 경로에 폴도가 있는지 체크
            File dir = new File(path);
            if(!dir.isDirectory()){
                dir.mkdir();
            }

            // 첨부파일
            List<MultipartFile> fileList = multi.getFiles("fileTel");
            // 선택한 파일이 있으면
            for(MultipartFile mFile : fileList)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename(); // 원본 파일명
                String fileExt = FilenameUtils.getExtension(orgFileName); // 파일확장자

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    // 파일 저장하는 부분
                    try {
                        mFile.transferTo(new File(path+newFileName+"."+fileExt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    contentData = path_table + "^" + newFileName + "." + fileExt;
                }
            }

        }catch(Exception e){

            isSuccess = false;
        }

        return contentData;
    }
}