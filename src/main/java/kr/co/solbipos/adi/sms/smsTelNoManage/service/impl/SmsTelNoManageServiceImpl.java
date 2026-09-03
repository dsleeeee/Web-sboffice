package kr.co.solbipos.adi.sms.smsTelNoManage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageService;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsTelNoManageServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > 발신번호관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.09.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("smsTelNoManageService")
@Transactional
public class SmsTelNoManageServiceImpl implements SmsTelNoManageService {
    private final SmsTelNoManageMapper smsTelNoManageMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsTelNoManageServiceImpl(SmsTelNoManageMapper smsTelNoManageMapper, MessageService messageService) { this.smsTelNoManageMapper = smsTelNoManageMapper;
        this.messageService = messageService;
    }

    /** 발신번호관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsTelNoManageList(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsTelNoManageVO.setUserId(sessionInfoVO.getUserId());

        return smsTelNoManageMapper.getSmsTelNoManageList(smsTelNoManageVO);
    }

    /** 발신번호관리 - 발신번호 등록 요청 저장 */
    @Override
    public int getSmsTelNoManageSave(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

        procCnt = smsTelNoManageMapper.getSmsTelNoManageSave(smsTelNoManageVO);

        return procCnt;
    }

    /**
     * 발신번호관리 - 기존에 등록 된 번호인지 확인
     * 인증된 휴대폰 번호가 기존 발신번호인지 조회한다.
     */
    @Override
    public int getSmsTelNoManageChk(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {
        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        return smsTelNoManageMapper.getSmsTelNoManageChk(smsTelNoManageVO);
    }

    /**
     * 발신번호관리 - 발신번호 등록 요청 결과 저장
     * KCP 거래의 주문번호와 요청자 정보로 발신번호 인증 결과를 저장한다.
     */
    @Override
    public int getSmsTelNoManageUpdate(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

        return smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);
    }

    /** 발신번호관리 - 저장 */
    @Override
    public int getSmsTelNoManageSaveUpdate(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {

            smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            smsTelNoManageVO.setModDt(currentDt);
            smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

            procCnt = smsTelNoManageMapper.getSmsTelNoManageSaveUpdate(smsTelNoManageVO);
        }

        return procCnt;
    }

    /** HCS_CRTLG_T.OGN_CD 값 가져옴 */
    @Override
    public String getOrdrIdxx(SmsTelNoManageVO smsTelNoManageVO) {
        String ordrIdxx = smsTelNoManageMapper.getOrdrIdxx(smsTelNoManageVO);
        System.out.println("JH : HCS_CRTLG_T.OGN_CD값 : " + ordrIdxx);
        return ordrIdxx;
    }

    /** 발신번호차단 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsTelNoStopList(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsTelNoManageVO.setUserId(sessionInfoVO.getUserId());

        return smsTelNoManageMapper.getSmsTelNoStopList(smsTelNoManageVO);
    }

    /** 발신번호차단 탭 - 저장 */
    @Override
    public int getSmsTelNoStopSaveUpdate(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {

            smsTelNoManageVO.setModDt(currentDt);
            smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

            procCnt = smsTelNoManageMapper.getSmsTelNoStopSaveUpdate(smsTelNoManageVO);
        }

        return procCnt;
    }

    /** 일반번호 인증요청 처리 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsGeneralNoManageList(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        return smsTelNoManageMapper.getSmsGeneralNoManageList(smsTelNoManageVO);
    }

    /** 일반번호 인증요청 처리 팝업 - 저장 */
    @Override
    public int getSmsGeneralNoManageSave(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {

            // 수정된 내역이 있을때만
            if(smsTelNoManageVO.getStatus() == GridDataFg.UPDATE) {

                smsTelNoManageVO.setRegDt(currentDt);
                smsTelNoManageVO.setRegId(sessionInfoVO.getUserId());
                smsTelNoManageVO.setModDt(currentDt);
                smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 관리요청번호 : " + smsTelNoManageVO.getCertId());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 수정전 처리구분 : " + smsTelNoManageVO.getBackAddProcFg());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 신규 처리구분 : " + smsTelNoManageVO.getAddProcFg());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 수정전 발신번호 : " + smsTelNoManageVO.getBackTelNo());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 신규 발신번호 : " + smsTelNoManageVO.getTelNo());

                // 수정전 처리구분이 완료이면서
                // && 수정전 처리구분 == 신규 처리구분
                // && 수정전 발신번호 == 신규 발신번호
                if ("2".equals(smsTelNoManageVO.getBackAddProcFg())
                        && smsTelNoManageVO.getBackAddProcFg().equals(smsTelNoManageVO.getAddProcFg())
                        && smsTelNoManageVO.getBackTelNo().equals(smsTelNoManageVO.getTelNo())) {

                    System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 안탐");

                } else {
                    System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 start");

                    // 수정전 발신번호 삭제
                    procCnt = smsTelNoManageMapper.getGeneralNoSmsNoSaveDelete(smsTelNoManageVO);

                    // 발신번호 등록 요청 저장(발신번호 등록전 상태로)
                    procCnt = smsTelNoManageMapper.getSmsGeneralNoSmsNoSaveInsert(smsTelNoManageVO);

                    // 완료
                    if ("2".equals(smsTelNoManageVO.getAddProcFg())) {
                        // 발신번호 등록 요청 승인(발신번호 등록)
                        smsTelNoManageVO.setResCd("0000");
                        procCnt = smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);
                    }

                    System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 end");
                }

                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 수정된 정보 저장");

                // 일반번호 인증요청 처리 update
                procCnt = smsTelNoManageMapper.getSmsGeneralNoManageSaveUpdate(smsTelNoManageVO);
            }
        }

        return procCnt;
    }

    /** 일반번호 인증요청 처리 팝업 - 발신번호 중복체크 */
    @Override
    public int getSmsGeneralNoManageCount(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {
            int chkTelNo = 0;

            // 완료
            if("2".equals(smsTelNoManageVO.getAddProcFg())) {
                chkTelNo = smsTelNoManageMapper.getSmsTelNoManageChk(smsTelNoManageVO);
            }

            procCnt = procCnt + chkTelNo; // 중복된 번호 건수
        }

        return procCnt;
    }

    /** 일반번호 인증요청 처리 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsGeneralNoManage2List(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        return smsTelNoManageMapper.getSmsGeneralNoManage2List(smsTelNoManageVO);
    }

    /** 일반번호 인증요청 처리 팝업2 - 저장 */
    @Override
    public int getSmsGeneralNoManage2Save(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {

            // 수정된 내역이 있을때만
            if(smsTelNoManageVO.getStatus() == GridDataFg.UPDATE) {

                smsTelNoManageVO.setRegDt(currentDt);
                smsTelNoManageVO.setRegId(sessionInfoVO.getUserId());
                smsTelNoManageVO.setModDt(currentDt);
                smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 관리요청번호 : " + smsTelNoManageVO.getCertId());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 수정전 처리구분 : " + smsTelNoManageVO.getBackAddProcFg());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 신규 처리구분 : " + smsTelNoManageVO.getAddProcFg());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 수정전 발신번호 : " + smsTelNoManageVO.getBackTelNo());
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 신규 발신번호 : " + smsTelNoManageVO.getTelNo());

                // 수정전 처리구분이 완료이면서
                // && 수정전 처리구분 == 신규 처리구분
                // && 수정전 발신번호 == 신규 발신번호
                if ("2".equals(smsTelNoManageVO.getBackAddProcFg())
                        && smsTelNoManageVO.getBackAddProcFg().equals(smsTelNoManageVO.getAddProcFg())
                        && smsTelNoManageVO.getBackTelNo().equals(smsTelNoManageVO.getTelNo())) {

                    System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 안탐");

                } else {
                    System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 start");

                    // 수정전 발신번호 삭제
                    procCnt = smsTelNoManageMapper.getGeneralNoSmsNoSaveDelete(smsTelNoManageVO);

                    // 발신번호 등록 요청 저장(발신번호 등록전 상태로)
                    procCnt = smsTelNoManageMapper.getSmsGeneralNoSmsNoSaveInsert(smsTelNoManageVO);

                    // 완료
                    if ("2".equals(smsTelNoManageVO.getAddProcFg())) {
                        // 발신번호 등록 요청 승인(발신번호 등록)
                        smsTelNoManageVO.setResCd("0000");
                        procCnt = smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);
                    }

                    System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 end");
                }

                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리2 >>> 저장(수정) >>> 수정된 정보 저장");

                // 일반번호 인증요청 처리 update
                procCnt = smsTelNoManageMapper.getSmsGeneralNoManage2SaveUpdate(smsTelNoManageVO);
            }
        }

        // 계정별 처리구분 완료 개수 제한 체크 (insert/update 반영 후 실제 DB 상태 기준, 초과 시 예외 던져서 트랜잭션 롤백)
        Set<String> chkGroupSet = new LinkedHashSet<String>();
        for (SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {
            if (smsTelNoManageVO.getStatus() == GridDataFg.UPDATE) {
                chkGroupSet.add(smsTelNoManageVO.getOrgnCd() + "|" + smsTelNoManageVO.getUserId() + "|" + smsTelNoManageVO.getTelFg());
            }
        }

        for (String group : chkGroupSet) {
            String[] parts = group.split("\\|");

            SmsTelNoManageVO chkVO = new SmsTelNoManageVO();
            chkVO.setOrgnCd(parts[0]);
            chkVO.setUserId(parts[1]);
            chkVO.setTelFg(parts[2]);

            int cnt = smsTelNoManageMapper.getAddProcFgCnt(chkVO);
            int limit = "0".equals(parts[2]) ? 2 : 5;

            if (cnt > limit) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("smsGeneralNoManage2.addProcFgCntOver") + "- 소속:" + parts[0] + " 사용자:" + parts[1]);
            }
        }

        // 번호 중복 체크 (같은 배치 내 충돌 + 기존 활성 등록 뺏기 둘 다 감지, insert/update 반영 후 실제 DB 상태 기준, 위반 시 예외 던져서 트랜잭션 롤백)
        Set<String> chkTelNoSet = new LinkedHashSet<String>();
        for (SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {
            if (smsTelNoManageVO.getStatus() == GridDataFg.UPDATE && "2".equals(smsTelNoManageVO.getAddProcFg())) {
                chkTelNoSet.add(smsTelNoManageVO.getTelNo());
            }
        }

        if (!chkTelNoSet.isEmpty()) {
            SmsTelNoManageVO dupChkVO = new SmsTelNoManageVO();
            dupChkVO.setChkTelNo(String.join(",", chkTelNoSet));
            dupChkVO.setChkTelNoList(chkTelNoSet.toArray(new String[0]));

            List<DefaultMap<String>> dupList = smsTelNoManageMapper.getDupUserTelNo(dupChkVO);

            if (!dupList.isEmpty()) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("smsTelNoStop.dupTelNo"));
            }
        }

        return procCnt;
    }

    /** SMS 발신번호 서류인증 미리보기 팝업 - 조회 */
    public DefaultMap<String> getSmsPreviewFileNm(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfo) {

        return smsTelNoManageMapper.getSmsPreviewFileNm(smsTelNoManageVO);
    }

    /** 음성파일등록 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getVoiceFileList(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {
        return smsTelNoManageMapper.getVoiceFileList(smsTelNoManageVO);
    }

    /** 음성파일등록 - 파일 저장(multipart, 게시판과 동일하게 /FileRoot/board/ 저장) */
    @Override
    public int saveVoiceFile(org.springframework.web.multipart.MultipartHttpServletRequest multi, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        SmsTelNoManageVO vo = new SmsTelNoManageVO();
        vo.setOrgnCd(multi.getParameter("orgnCd"));
        vo.setUserId(multi.getParameter("userId"));
        vo.setCertId(multi.getParameter("certId"));
        vo.setRegDt(currentDt);
        vo.setRegId(sessionInfoVO.getUserId());
        vo.setModDt(currentDt);
        vo.setModId(sessionInfoVO.getUserId());

        // 물리 저장 경로 (게시판과 동일)
        String path = kr.co.common.system.BaseEnv.FILE_UPLOAD_DIR + "board/";
        String pathTable = path; // (2026.09.03) 게시판(BoardServiceImpl)과 동일하게 FILE_PATH에 전체 경로(/FileRoot/board/) 저장
        java.io.File dir = new java.io.File(path);
        if (!dir.isDirectory()) { dir.mkdirs(); } // 상위 폴더까지 생성 (mkdir → mkdirs)

        java.util.List<org.springframework.web.multipart.MultipartFile> fileList = multi.getFiles("file");

        // 신청건 BOARD_SEQ_NO 채번 (건별 1회 : 파일 있으면 재사용, 없으면 신규)
        vo.setBoardSeqNo(smsTelNoManageMapper.getVoiceFileSeqNo(vo));
        // 해당 SEQ 안에서의 IDX 시작값 (한 번만 조회 후 Java에서 증가)
        int baseIdx = 1;
        try { baseIdx = Integer.parseInt(smsTelNoManageMapper.getVoiceFileIdx(vo)); } catch (Exception e) {}

        int seq = 0;
        for (org.springframework.web.multipart.MultipartFile mFile : fileList) {
            String orgFileName = mFile.getOriginalFilename();
            if (orgFileName == null || orgFileName.lastIndexOf('.') <= 0) { continue; }

            String fileExt = org.apache.commons.io.FilenameUtils.getExtension(orgFileName);
            // 저장 파일명 (millis + 순번, 게시판과 동일하게 확장자 없음)
            String newFileName = String.valueOf(System.currentTimeMillis()) + seq;
            // 원본명(확장자 제외, IE 경로 제거)
            String orgNmNoExt = orgFileName.substring(0, orgFileName.lastIndexOf("."));
            if (orgNmNoExt.contains("\\")) { orgNmNoExt = orgNmNoExt.substring(orgNmNoExt.lastIndexOf("\\") + 1); }

            try {
                // 저장 폴더 확실히 생성 (상위까지)
                java.io.File dirFile = new java.io.File(path);
                if (!dirFile.exists()) {
                    boolean made = dirFile.mkdirs();
                    if (!made && !dirFile.exists()) {
                        // 기술 상세(경로)는 로그로, 사용자 팝업은 messageService
                        System.out.println("WEB_SMS >>> 음성파일 저장 폴더 생성 실패 : " + dirFile.getAbsolutePath());
                        throw new kr.co.common.exception.JsonException(kr.co.common.data.enums.Status.SERVER_ERROR,
                                messageService.get("smsGeneralNoManage2.voiceFileDirFail"));
                    }
                }

                // transferTo 대신 스트림으로 직접 저장 (서블릿 임시경로 이슈 회피)
                java.io.File target = new java.io.File(dirFile, newFileName);
                try (java.io.InputStream is = mFile.getInputStream();
                     java.io.FileOutputStream os = new java.io.FileOutputStream(target)) {
                    org.springframework.util.FileCopyUtils.copy(is, os);
                }
            } catch (kr.co.common.exception.JsonException je) {
                // 이미 사용자 메시지가 세팅된 예외(폴더 생성 실패 등)는 그대로 전달
                throw je;
            } catch (Exception e) {
                // 기술 상세(스택)는 로그로, 사용자 팝업은 messageService
                e.printStackTrace();
                throw new kr.co.common.exception.JsonException(kr.co.common.data.enums.Status.SERVER_ERROR,
                        messageService.get("smsGeneralNoManage2.voiceFileSaveFail"));
            }

            vo.setIdx(String.valueOf(baseIdx + seq));   // 신청건 내 파일순번
            vo.setFilePath(pathTable);
            vo.setFileNm(newFileName);
            vo.setOrginlFileNm(orgNmNoExt);
            vo.setFileExt(fileExt);
            procCnt += smsTelNoManageMapper.insertVoiceFile(vo);

            seq++;
        }
        return procCnt;
    }

    /** 음성파일등록 - 삭제 */
    @Override
    public int delVoiceFile(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();
        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());
        return smsTelNoManageMapper.deleteVoiceFile(smsTelNoManageVO);
    }

}
