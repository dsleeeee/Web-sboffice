package kr.co.solbipos.adi.service.dclz.dclzmanage;

import static kr.co.solbipos.utils.DateUtil.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.adi.domain.dclz.dclzmanage.DclzManage;
import kr.co.solbipos.adi.enums.DclzInFg;
import kr.co.solbipos.adi.persistence.dclz.dclzmanage.DclzManageMapper;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.exception.JsonException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 부가서비스 > 근태관리 > 근태관리
 * 
 * @author 정용길
 *
 */
@Service
public class DclzManageServiceImpl implements DclzManageService {

    @Autowired
    DclzManageMapper dclzManageMapper;

    @Autowired
    MessageService messageService;

    @Override
    public List<DefaultMap<String>> selectDclzManage(DclzManage dclzManage) {
        return dclzManageMapper.selectDclzManage(dclzManage);
    }

    @Override
    public int insertDclzManage(DclzManage dclzManage, String userId) {

        // 기본값 세팅
        dclzManage.setPosNo("01"); // 기본 포스 번호 01 세팅 서효원 과장
        dclzManage.setInFg(DclzInFg.WEB);
        String dt = currentDateTimeString();
        dclzManage.setRegDt(dt);
        dclzManage.setModDt(dt);
        dclzManage.setRegId(userId);
        dclzManage.setModId(userId);

        // 해당 근무일에 근태가 있는지 확인
        int check = selectWorkCheck(dclzManage);

        if (check > 0) {
            String arg[] = {dclzManage.getEmpInDate()};
            // 해당 사원의 {0}일의 근태가 존재합니다.
            String msg = messageService.get("msg.dclz.empty.emp", arg);
            throw new JsonException(Status.FAIL, msg);
        }

        // 출근일시 퇴근일시로 근무시간을 계산
        dclzManage.setWorkTime(calShift(dclzManage.getEmpInDt(), dclzManage.getEmpOutDt()));

        return dclzManageMapper.insertDclzManage(dclzManage);
    }

    @Override
    public int updateDclzManage(DclzManage dclzManage, String userId) {

        // 기본값 세팅
        dclzManage.setModDt(currentDateTimeString());
        dclzManage.setModId(userId);

        /**
         * 
         * 해당 근무일에 근태가 있는지 확인 없는 경우에는 업데이트를 할 수 없다.
         * 
         */
        int check = selectWorkCheck(dclzManage);

        if (check == 0) {
            String arg[] = {dclzManage.getEmpInDate()};
            // 해당 사원의 {0}일의 근태가 존재하지 않습니다.
            String msg = messageService.get("msg.dclz.empty.dclz", arg);
            throw new JsonException(Status.FAIL, msg);
        }

        dclzManage.setWorkTime(calShift(dclzManage.getEmpInDt(), dclzManage.getEmpOutDt()));

        return dclzManageMapper.updateDclzManage(dclzManage);
    }

    @Override
    public int deleteDclzManage(DclzManage dclzManage) {
        return dclzManageMapper.deleteDclzManage(dclzManage);
    }

    @Override
    public List<DefaultMap<String>> selectStoreEmployee(DclzManage dclzManage) {
        return dclzManageMapper.selectStoreEmployee(dclzManage);
    }

    @Override
    public int selectWorkCheck(DclzManage dclzManage) {
        return dclzManageMapper.selectWorkCheck(dclzManage);
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
            String msg = messageService.get("label.registFail");
            throw new JsonException(Status.FAIL, msg, "");
        }
        // 퇴근일시 또는 출근일시가 잘못 선택 되었습니다.
        if (workMinute < 0) {
            String msg = messageService.get("msg.dclz.wrong.date");
            throw new JsonException(Status.FAIL, msg, "");
        }
        return workMinute;
    }
}
