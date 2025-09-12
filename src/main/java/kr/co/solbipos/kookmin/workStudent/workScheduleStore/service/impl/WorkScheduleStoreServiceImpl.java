package kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.WorkScheduleStoreService;
import kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.WorkScheduleStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : WorkScheduleStoreServiceImpl.java
 * @Description : 국민대 > 근로학생관리 > 매장별 근무테이블 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.09
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("WorkScheduleStoreService")
@Transactional
public class WorkScheduleStoreServiceImpl implements WorkScheduleStoreService {

    private final WorkScheduleStoreMapper workScheduleStoreMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public WorkScheduleStoreServiceImpl(WorkScheduleStoreMapper workScheduleStoreMapper, PopupMapper popupMapper) {
        this.workScheduleStoreMapper = workScheduleStoreMapper;
        this.popupMapper = popupMapper;
    }

    /** 근무테이블 조회 */
    @Override
    public List<DefaultMap<String>> getWorkScheduleStoreList(WorkScheduleStoreVO workScheduleStoreVO, SessionInfoVO sessionInfoVO) {
        workScheduleStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return workScheduleStoreMapper.getWorkScheduleStoreList(workScheduleStoreVO);
    }

    /** 로우 추가 매장별 근무코드 조회 */
    @Override
    public List<DefaultMap<Object>> addRowWorkScheduleStore(WorkScheduleStoreVO workScheduleStoreVO, SessionInfoVO sessionInfoVO) {

        workScheduleStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(workScheduleStoreVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(workScheduleStoreVO.getStoreCd(), 3900));
            workScheduleStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 근무코드 조회
        List<DefaultMap<Object>> list = workScheduleStoreMapper.getWorkSchCodeList(workScheduleStoreVO);

        // 근무코드 최댓값 조회(A뒤 값 중 최대값)
        int maxCode = workScheduleStoreMapper.getMaxWowkSchCode(workScheduleStoreVO);

        List<DefaultMap<Object>> addRowList = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DefaultMap<Object> row = new DefaultMap<>();

                // 값 추출
                String storeCd = list.get(i).getStr("storeCd");
                String storeNm = list.get(i).getStr("storeNm");
                String maxFirst = list.get(i).getStr("maxFirst");
                if(maxFirst == null || maxFirst.equals("")){
                    maxFirst = ++maxCode + "";
                }
                String maxLast = list.get(i).getStr("maxLast");
                if(maxLast == null || maxLast.equals("")){
                    maxLast = "1";
                }

                String workSchCode = "A" + maxFirst + "-" + maxLast;

                // row에 값 세팅
                row.put("storeCd", storeCd);
                row.put("storeNm", storeNm);
                row.put("workSchCode", workSchCode);

                // 리스트에 추가
                addRowList.add(row);
            }
        }
        return addRowList;
    }

    /** 근무테이블 저장 */
    @Override
    public int saveWorkScheduleStore(WorkScheduleStoreVO[] workScheduleStoreVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(WorkScheduleStoreVO workScheduleStoreVO : workScheduleStoreVOs){

            workScheduleStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            workScheduleStoreVO.setRegId(sessionInfoVO.getUserId());
            workScheduleStoreVO.setRegDt(currentDt);
            workScheduleStoreVO.setModId(sessionInfoVO.getUserId());
            workScheduleStoreVO.setModDt(currentDt);

            // 근무요일 셋팅
            String workDay = "";
            if(workScheduleStoreVO.getSun()) workDay += "1"; //일
            if(workScheduleStoreVO.getMon()) workDay += "2"; //월
            if(workScheduleStoreVO.getTue()) workDay += "3"; //화
            if(workScheduleStoreVO.getWed()) workDay += "4"; //수
            if(workScheduleStoreVO.getThu()) workDay += "5"; //목
            if(workScheduleStoreVO.getFri()) workDay += "6"; //금
            if(workScheduleStoreVO.getSat()) workDay += "7"; //토
            workScheduleStoreVO.setWorkDay(workDay);

            if(workScheduleStoreVO.getStatus() == GridDataFg.INSERT) {
                // 근무코드 값 조회
                String workSchCode = workScheduleStoreMapper.getWorkSchCodeStore(workScheduleStoreVO);
                workScheduleStoreVO.setWorkSchCode(workSchCode);
                //추가
                procCnt += workScheduleStoreMapper.insertWorkScheduleStore(workScheduleStoreVO);
            }
            else if(workScheduleStoreVO.getStatus() == GridDataFg.UPDATE) {
                //수정
                procCnt += workScheduleStoreMapper.updateWorkScheduleStore(workScheduleStoreVO);
            }
            else if(workScheduleStoreVO.getStatus() == GridDataFg.DELETE) {
                //삭제
                procCnt += workScheduleStoreMapper.deleteWorkScheduleStore(workScheduleStoreVO);
            }

        }

        return procCnt;
    }
}
