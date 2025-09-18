package kr.co.solbipos.kookmin.workStudent.workHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workHistory.service.WorkHistoryService;
import kr.co.solbipos.kookmin.workStudent.workHistory.service.WorkHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("WorkHistoryService")
@Transactional
public class WorkHistoryServiceImpl implements WorkHistoryService {

    private final WorkHistoryMapper workHistoryMapper;

    @Autowired
    public WorkHistoryServiceImpl(WorkHistoryMapper workHistoryMapper) {
        this.workHistoryMapper = workHistoryMapper;
    }

    /** 근무내역 조회 */
    @Override
    public List<DefaultMap<Object>> getWorkHistoryList(WorkHistoryVO workHistoryVO, SessionInfoVO sessionInfoVO) {
        workHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return workHistoryMapper.getWorkHistoryList(workHistoryVO);
    }

    /** 근무내역 - 근무시간 저장 */
    @Override
    public int saveWorkHistory(WorkHistoryVO[] workHistoryVOS, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(WorkHistoryVO workHistoryVO : workHistoryVOS){

            workHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            workHistoryVO.setRegId(sessionInfoVO.getUserId());
            workHistoryVO.setRegDt(currentDt);
            workHistoryVO.setModId(sessionInfoVO.getUserId());
            workHistoryVO.setModDt(currentDt);
            workHistoryVO.setRegInFg("W");

            if(workHistoryVO.getRealStartTime() != null && !workHistoryVO.getRealStartTime().equals("")){
                workHistoryVO.setRealStartTime(workHistoryVO.getWorkDate() + workHistoryVO.getRealStartTime() + "00");
            }
            if(workHistoryVO.getRealEndTime() != null && !workHistoryVO.getRealEndTime().equals("")){
                workHistoryVO.setRealEndTime(workHistoryVO.getWorkDate() + workHistoryVO.getRealEndTime() + "00");
            }

            workHistoryVO.setBaseStartTime(workHistoryVO.getWorkDate() + workHistoryVO.getBaseStartTime() + "00");
            workHistoryVO.setBaseEndTime(workHistoryVO.getWorkDate() + workHistoryVO.getBaseEndTime() + "00");

            System.out.println("시작" + workHistoryVO.getRealStartTime() + "종료" + workHistoryVO.getRealEndTime() +
                    "기준시작" + workHistoryVO.getBaseStartTime() + "기준종료" + workHistoryVO.getBaseEndTime());

            //추가
            procCnt += workHistoryMapper.mergeWorkHistory(workHistoryVO);

        }

        return procCnt;
    }

    /** 근무내역 - 근무시간 일괄등록 */
    @Override
    public int saveRegCommuteAll(WorkHistoryVO[] workHistoryVOS, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(WorkHistoryVO workHistoryVO : workHistoryVOS){

            workHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            workHistoryVO.setRegId(sessionInfoVO.getUserId());
            workHistoryVO.setRegDt(currentDt);
            workHistoryVO.setModId(sessionInfoVO.getUserId());
            workHistoryVO.setModDt(currentDt);
            workHistoryVO.setRegInFg("W");

            if(workHistoryVO.getRealStartTime() != null && !workHistoryVO.getRealStartTime().equals("")){
                workHistoryVO.setRealStartTime(workHistoryVO.getWorkDate() + workHistoryVO.getRealStartTime() + "00");
            }else{
                workHistoryVO.setRealStartTime(workHistoryVO.getWorkDate() + workHistoryVO.getBaseStartTime() + "00");
            }

            if(workHistoryVO.getRealEndTime() != null && !workHistoryVO.getRealEndTime().equals("")){
                workHistoryVO.setRealEndTime(workHistoryVO.getWorkDate() + workHistoryVO.getRealEndTime() + "00");
            }else{
                workHistoryVO.setRealEndTime(workHistoryVO.getWorkDate() + workHistoryVO.getBaseEndTime() + "00");
            }
            workHistoryVO.setBaseStartTime(workHistoryVO.getWorkDate() + workHistoryVO.getBaseStartTime() + "00");
            workHistoryVO.setBaseEndTime(workHistoryVO.getWorkDate() + workHistoryVO.getBaseEndTime() + "00");

            System.out.println("시작" + workHistoryVO.getRealStartTime() + "종료" + workHistoryVO.getRealEndTime() +
                    "기준시작" + workHistoryVO.getBaseStartTime() + "기준종료" + workHistoryVO.getBaseEndTime());
            //추가
            procCnt += workHistoryMapper.mergeWorkHistory(workHistoryVO);

        }

        return procCnt;
    }
}
