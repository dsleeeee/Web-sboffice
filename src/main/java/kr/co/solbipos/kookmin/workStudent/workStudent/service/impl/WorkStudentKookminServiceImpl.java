package kr.co.solbipos.kookmin.workStudent.workStudent.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workStudent.service.WorkStudentKookminService;
import kr.co.solbipos.kookmin.workStudent.workStudent.service.WorkStudentKookminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : WorkStudentKookminServiceImpl.java
 * @Description : 국민대 > 근로학생관리 > 근로학생관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.31  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("WorkStudentKookminService")
@Transactional
public class WorkStudentKookminServiceImpl implements WorkStudentKookminService {

    private final WorkStudentKookminMapper workStudentKookminMapper;
    private final MessageService messageService;

    @Autowired
    public WorkStudentKookminServiceImpl(WorkStudentKookminMapper workStudentKookminMapper, MessageService messageService) {
        this.workStudentKookminMapper = workStudentKookminMapper;
        this.messageService = messageService;
    }

    @Override
    public List<DefaultMap<Object>> getWorkStudentKookminList(WorkStudentKookminVO workStudentKookminVO, SessionInfoVO sessionInfoVO) {
        workStudentKookminVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return workStudentKookminMapper.getWorkStudentKookminList(workStudentKookminVO);
    }

    @Override
    public int saveWorkStudent(WorkStudentKookminVO[] workStudentKookminVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(WorkStudentKookminVO workStudentKookminVO : workStudentKookminVOs){

            workStudentKookminVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            workStudentKookminVO.setRegId(sessionInfoVO.getUserId());
            workStudentKookminVO.setRegDt(currentDt);
            workStudentKookminVO.setModId(sessionInfoVO.getUserId());
            workStudentKookminVO.setModDt(currentDt);

            int result = 0;
            if(workStudentKookminVO.getStatus() == GridDataFg.INSERT) {
                procCnt += workStudentKookminMapper.insertWorkStudent(workStudentKookminVO);
            }
            else if(workStudentKookminVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += workStudentKookminMapper.updateWorkStudent(workStudentKookminVO);
            }
            else if(workStudentKookminVO.getStatus() == GridDataFg.DELETE) {
                procCnt += workStudentKookminMapper.deleteWorkStudent(workStudentKookminVO);
            }
        }
        return procCnt;
    }

    @Override
    public int saveWorkStudentExcelUpload(WorkStudentKookminVO[] workStudentKookminVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(WorkStudentKookminVO workStudentKookminVO : workStudentKookminVOs){

            workStudentKookminVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            workStudentKookminVO.setRegId(sessionInfoVO.getUserId());
            workStudentKookminVO.setRegDt(currentDt);
            workStudentKookminVO.setModId(sessionInfoVO.getUserId());
            workStudentKookminVO.setModDt(currentDt);

            int result = 0;
            // 학번 중복 체크
            result = workStudentKookminMapper.getChkStudentNo(workStudentKookminVO);
            if (result > 0) {
                throw new JsonException(Status.FAIL, messageService.get("workStudentKookmin.studentNoChk") + "(" + workStudentKookminVO.getStudentNo() + ")");
            }
            procCnt += workStudentKookminMapper.insertWorkStudent(workStudentKookminVO);
        }

        return procCnt;
    }
}
