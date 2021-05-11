package kr.co.solbipos.adi.dclz.dclzmanage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.dclz.dclzmanage.enums.DclzInFg;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageService;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DclzManageServiceImpl.java
 * @Description : 부가서비스 > 근태 관리 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dclzManageService")
@Transactional
public class DclzManageServiceImpl implements DclzManageService {

    private final DclzManageMapper dclzManageMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public DclzManageServiceImpl(DclzManageMapper dclzManageMapper, MessageService messageService) {
        this.dclzManageMapper = dclzManageMapper;
        this.messageService = messageService;
    }

    /** 근태관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO){

        dclzManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dclzManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(dclzManageVO.getStoreCd()).equals("")) {
                dclzManageVO.setArrStoreCd(dclzManageVO.getStoreCd().split(","));
            }
        }

        return dclzManageMapper.selectDclzManage(dclzManageVO);
    }

    /** 매장 사원 조회 */
    @Override
    public List<DefaultMap<String>> selectStoreEmployee(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO) {

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dclzManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return dclzManageMapper.selectStoreEmployee(dclzManageVO);
    }

    /** 근태등록 */
    @Override
    public int insertDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        // 기본값 세팅
        dclzManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        dclzManageVO.setPosNo("01"); // 기본 포스 번호 01 세팅 서효원 과장
        dclzManageVO.setInFg("WEB");
        dclzManageVO.setRegDt(dt);
        dclzManageVO.setModDt(dt);
        dclzManageVO.setRegId(sessionInfoVO.getUserId());
        dclzManageVO.setModId(sessionInfoVO.getUserId());

        // 근태 등록여부 확인
        int check = dclzManageMapper.selectWorkCheck(dclzManageVO);

        if (check > 0) {
            String arg[] = {dclzManageVO.getSaleDate()};
            // 해당 사원의 {0}일의 근태가 존재합니다.
            String msg = messageService.get("dclzManage.empty.emp", arg);
            throw new JsonException(Status.SERVER_ERROR, msg);

        }else{
            result = dclzManageMapper.insertDclzManage(dclzManageVO);
        }

        return result;
    }

    /** 근태수정 */
    @Override
    public int updateDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        // 기본값 세팅
        dclzManageVO.setModDt(dt);
        dclzManageVO.setModId(sessionInfoVO.getUserId());

        // 근태 등록여부 확인
        int check = dclzManageMapper.selectWorkCheck(dclzManageVO);

        if (check == 0) {
            String arg[] = {dclzManageVO.getSaleDate()};
            // 해당 사원의 {0}일의 근태가 존재하지 않습니다.
            String msg = messageService.get("dclzManage.empty.dclz", arg);
            throw new JsonException(Status.SERVER_ERROR, msg);

        }else{
            result = dclzManageMapper.updateDclzManage(dclzManageVO);
        }

        return result;
    }

    /** 근태삭제 */
    @Override
    public int deleteDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO) {
        return dclzManageMapper.deleteDclzManage(dclzManageVO);
    }

    /** 근태상세정보 조회 */
    @Override
    public DefaultMap<String> selectDclzManageDtl(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO) {
        return dclzManageMapper.selectDclzManageDtl(dclzManageVO);
    }

    /**
     * 출근일시 퇴근일시로 근무시간을 계산
     *
     * @param startDt YYYYMMDDHHmm 형태의 출근일시
     * @param endDt YYYYMMDDHHmm 형태의 퇴근일시
     * @return 분단위의 근무시간<br>
     *         0 아래의 값은 잘못된값임<br>
     */
    public long calShift(String startDt, String endDt) {

        long workMinute = 0;

        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);

            Date d1 = f.parse(endDt.substring(0, 12));
            Date d2 = f.parse(startDt.substring(0, 12));

            long diff = d1.getTime() - d2.getTime();
            workMinute = diff / 60000;

        } catch (Exception e) {
            // 등록에 실패 했습니다.
            String msg = messageService.get("cmm.registFail");
            throw new JsonException(Status.FAIL, msg, "");
        }
        // 퇴근일시 또는 출근일시가 잘못 선택 되었습니다.
        if (workMinute < 0) {
            String msg = messageService.get("dclzManage.wrong.date");
            throw new JsonException(Status.FAIL, msg, "");
        }
        return workMinute;
    }
}
