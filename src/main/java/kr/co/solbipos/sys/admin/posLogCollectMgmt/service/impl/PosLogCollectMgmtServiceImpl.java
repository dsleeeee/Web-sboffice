package kr.co.solbipos.sys.admin.posLogCollectMgmt.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.posLogCollectMgmt.service.PosLogCollectMgmtService;
import kr.co.solbipos.sys.admin.posLogCollectMgmt.service.PosLogCollectMgmtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : PosLogCollectMgmtServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > POS로그수집관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.04  김유승      최초생성
 * @ 2026.10.20  김유승      SQL 바이트 체크 추가
 * @ 2026.11.04  김유승      저장 전 입력값 체크 추가(명령타입, 바이트, 일자)
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("PosLogCollectMgmtService")
@Transactional
public class PosLogCollectMgmtServiceImpl implements PosLogCollectMgmtService {

    private final PosLogCollectMgmtMapper posLogCollectMgmtMapper;
    private final MessageService messageService;

    /**
     *  Constructor Injection
     */
    @Autowired
    public PosLogCollectMgmtServiceImpl(PosLogCollectMgmtMapper posLogCollectMgmtMapper, MessageService messageService) {
        this.posLogCollectMgmtMapper = posLogCollectMgmtMapper;
        this.messageService = messageService;
    }

    /** POS로그수집관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchPosLogList(PosLogCollectMgmtVO posLogCollectMgmtVO, SessionInfoVO sessionInfoVO) {
        return posLogCollectMgmtMapper.getSearchPosLogList(posLogCollectMgmtVO);
    }

    /** POS로그수집등록 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchStoreList(PosLogCollectMgmtVO posLogCollectMgmtVO, SessionInfoVO sessionInfoVO) {
        return posLogCollectMgmtMapper.getSearchStoreList(posLogCollectMgmtVO);
    }

    /** POS로그수집등록 팝업 - POS로그 저장 */
    @Override
    public int savePosLog(PosLogCollectMgmtVO[] posLogCollectMgmtVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();
        String currentDay = currentDateString();

        // 저장 전 전체 검사 (화면 체크 없이 직접 호출되는 경우 대비, 하나라도 오류면 저장하지 않음)
        for(PosLogCollectMgmtVO posLogCollectMgmtVO : posLogCollectMgmtVOs){

            // 명령타입 체크 (화면은 DB_SELECT, DB_TABLE, LOG_FILE만 사용)
            String commandType = posLogCollectMgmtVO.getCommandType();
            if(!"DB_SELECT".equals(commandType) && !"DB_TABLE".equals(commandType) && !"LOG_FILE".equals(commandType)) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("posLogCollectMgmt.msg.commandType"));
            }

            // SQL 바이트 체크 (컬럼 VARCHAR2(4000 BYTE))
            int sqlByte = StringUtil.getByteLength(posLogCollectMgmtVO.getSql());
            if(sqlByte > 4000) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("posLogCollectMgmt.sql") + "은 4000" + messageService.get("verManage.byte") + "제한(확인: " + sqlByte + messageService.get("verManage.byte") + ")");
            }

            // 로그타입, VCAT 경로, 사유, DB백업 포함, 스마트오더 수집여부 바이트 체크
            checkByteLength(messageService.get("posLogCollectMgmt.logType"), posLogCollectMgmtVO.getLogType(), 100);
            checkByteLength(messageService.get("posLogCollectMgmt.vcatPath"), posLogCollectMgmtVO.getVcatPath(), 1000);
            checkByteLength(messageService.get("posLogCollectMgmt.remark"), posLogCollectMgmtVO.getRemark(), 4000);
            checkByteLength(messageService.get("posLogCollectMgmt.dbBackup"), posLogCollectMgmtVO.getIncludeDbBackup(), 1);
            checkByteLength(messageService.get("posLogCollectMgmt.smartOrder"), posLogCollectMgmtVO.getSmartOrderYn(), 1);

            // 시작·종료일자 형식 체크 (화면은 yyyyMMdd, 컬럼 VARCHAR2(8 BYTE))
            String dateFrom = posLogCollectMgmtVO.getDateFrom();
            if(!StringUtil.isEmpty(dateFrom) && !dateFrom.matches("\\d{8}")) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("posLogCollectMgmt.startDate") + "는 yyyyMMdd(8자리) 형식만 가능합니다.(확인: " + dateFrom.length() + "자리)");
            }
            String dateTo = posLogCollectMgmtVO.getDateTo();
            if(!StringUtil.isEmpty(dateTo) && !dateTo.matches("\\d{8}")) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("posLogCollectMgmt.endDate") + "는 yyyyMMdd(8자리) 형식만 가능합니다.(확인: " + dateTo.length() + "자리)");
            }

            // 시작일자가 종료일자보다 늦은지 확인
            if(!StringUtil.isEmpty(dateFrom) && !StringUtil.isEmpty(dateTo) && dateFrom.compareTo(dateTo) > 0) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.dateChk.error"));
            }
        }

        for(PosLogCollectMgmtVO posLogCollectMgmtVO : posLogCollectMgmtVOs){

            posLogCollectMgmtVO.setSaleDate(currentDay);
            posLogCollectMgmtVO.setRegDt(currentDt);
            posLogCollectMgmtVO.setRegId(sessionInfoVO.getUserId());
            posLogCollectMgmtVO.setModDt(currentDt);
            posLogCollectMgmtVO.setModId(sessionInfoVO.getUserId());

            posLogCollectMgmtVO.setSeq(posLogCollectMgmtMapper.getMaxSeqNo(posLogCollectMgmtVO));

            result = posLogCollectMgmtMapper.savePosLog(posLogCollectMgmtVO);
        }

        return result;
    }

    /** 바이트 수 체크 (UTF-8 기준, 넘으면 저장 중단) */
    private void checkByteLength(String name, String value, int maxByte) {
        int byteLength = StringUtil.getByteLength(value);
        if(byteLength > maxByte) {
            throw new JsonException(Status.SERVER_ERROR, name + " " + maxByte + messageService.get("verManage.byte") + " 제한(확인: " + byteLength + messageService.get("verManage.byte") + ")");
        }
    }
}
