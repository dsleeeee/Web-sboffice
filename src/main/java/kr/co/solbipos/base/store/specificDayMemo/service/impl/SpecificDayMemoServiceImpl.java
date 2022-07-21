package kr.co.solbipos.base.store.specificDayMemo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.specificDayMemo.service.SpecificDayMemoService;
import kr.co.solbipos.base.store.specificDayMemo.service.SpecificDayMemoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SpecificDayMemoServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 이벤트등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("specificDayMemoService")
@Transactional
public class SpecificDayMemoServiceImpl implements SpecificDayMemoService {
    private final SpecificDayMemoMapper specificDayMemoMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SpecificDayMemoServiceImpl(SpecificDayMemoMapper specificDayMemoMapper) {
        this.specificDayMemoMapper = specificDayMemoMapper;
    }

    /** 이벤트등록 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSpecificDayMemoList(SpecificDayMemoVO specificDayMemoVO, SessionInfoVO sessionInfoVO) {

        specificDayMemoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        specificDayMemoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            specificDayMemoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return specificDayMemoMapper.getSpecificDayMemoList(specificDayMemoVO);
    }

    /** 신규 등록 */
    @Override
    public int getSpecificDayMemoRegist(SpecificDayMemoVO specificDayMemoVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        specificDayMemoVO.setRegId(sessionInfoVO.getUserId());
        specificDayMemoVO.setRegDt(currentDt);
        specificDayMemoVO.setModId(sessionInfoVO.getUserId());
        specificDayMemoVO.setModDt(currentDt);
        specificDayMemoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        specificDayMemoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            specificDayMemoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        String specificNo = specificDayMemoMapper.getSpecificNo(specificDayMemoVO);
        specificDayMemoVO.setSpecificNo(specificNo);

        return specificDayMemoMapper.getSpecificDayMemoRegist(specificDayMemoVO);
    }

    @Override
    public int getSpecificDayMemoSave(SpecificDayMemoVO[] specificDayMemoVOs, SessionInfoVO sessionInfoVO) {

        int cnt = 0;
        String currentDt = currentDateTimeString();

        for(SpecificDayMemoVO specificDayMemoVO : specificDayMemoVOs){

            specificDayMemoVO.setModId(sessionInfoVO.getUserId());
            specificDayMemoVO.setModDt(currentDt);
            specificDayMemoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            specificDayMemoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                specificDayMemoVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            cnt += specificDayMemoMapper.getSpecificDayMemoSave(specificDayMemoVO);
        }
        return cnt;
    }

    @Override
    public int getSpecificDayMemoDelete(SpecificDayMemoVO[] specificDayMemoVOs, SessionInfoVO sessionInfoVO) {

        int cnt = 0;

        for(SpecificDayMemoVO specificDayMemoVO : specificDayMemoVOs){

            specificDayMemoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            specificDayMemoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                specificDayMemoVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            cnt += specificDayMemoMapper.getSpecificDayMemoDelete(specificDayMemoVO);
        }
        return cnt;
    }
}