package kr.co.solbipos.adi.dclz.dclzmanage.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.dclz.dclzmanage.enums.DclzInFg;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageService;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO;

/**
 * @Class Name : DclzManageServiceImpl.java
 * @Description : 부가서비스 > 근태 관리 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
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

    @Autowired
    DclzManageMapper dclzManageMapper;

    @Autowired
    MessageService messageService;

    @Override
    public List<DefaultMap<String>> selectDclzManage(DclzManageVO dclzManageVO) {
        return dclzManageMapper.selectDclzManage(dclzManageVO);
    }

    @Override
    public int insertDclzManage(DclzManageVO dclzManageVO, String userId) {

        // 기본값 세팅
        dclzManageVO.setPosNo("01"); // 기본 포스 번호 01 세팅 서효원 과장
        dclzManageVO.setInFg(DclzInFg.WEB);
        String dt = currentDateTimeString();
        dclzManageVO.setRegDt(dt);
        dclzManageVO.setModDt(dt);
        dclzManageVO.setRegId(userId);
        dclzManageVO.setModId(userId);

        // 해당 근무일에 근태가 있는지 확인
        int check = selectWorkCheck(dclzManageVO);

        if (check > 0) {
            String arg[] = {dclzManageVO.getEmpInDate()};
            // 해당 사원의 {0}일의 근태가 존재합니다.
            String msg = messageService.get("dclzManage.empty.emp", arg);
            throw new JsonException(Status.FAIL, msg);
        }

        // 출근일시 퇴근일시로 근무시간을 계산
        dclzManageVO.setWorkTime(calShift(dclzManageVO.getEmpInDt(), dclzManageVO.getEmpOutDt()));

        return dclzManageMapper.insertDclzManage(dclzManageVO);
    }

    @Override
    public int updateDclzManage(DclzManageVO dclzManageVO, String userId) {

        // 기본값 세팅
        dclzManageVO.setModDt(currentDateTimeString());
        dclzManageVO.setModId(userId);

        /**
         *
         * 해당 근무일에 근태가 있는지 확인 없는 경우에는 업데이트를 할 수 없다.
         *
         */
        int check = selectWorkCheck(dclzManageVO);

        if (check == 0) {
            String arg[] = {dclzManageVO.getEmpInDate()};
            // 해당 사원의 {0}일의 근태가 존재하지 않습니다.
            String msg = messageService.get("dclzManage.empty.dclz", arg);
            throw new JsonException(Status.FAIL, msg);
        }

        dclzManageVO.setWorkTime(calShift(dclzManageVO.getEmpInDt(), dclzManageVO.getEmpOutDt()));

        return dclzManageMapper.updateDclzManage(dclzManageVO);
    }

    @Override
    public int deleteDclzManage(DclzManageVO dclzManageVO) {
        return dclzManageMapper.deleteDclzManage(dclzManageVO);
    }

    @Override
    public List<DefaultMap<String>> selectStoreEmployee(DclzManageVO dclzManageVO) {
        return dclzManageMapper.selectStoreEmployee(dclzManageVO);
    }

    @Override
    public int selectWorkCheck(DclzManageVO dclzManageVO) {
        return dclzManageMapper.selectWorkCheck(dclzManageVO);
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

            Date d1 = f.parse(endDt);
            Date d2 = f.parse(startDt);

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
