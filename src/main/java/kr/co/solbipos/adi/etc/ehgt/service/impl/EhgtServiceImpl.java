package kr.co.solbipos.adi.etc.ehgt.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtService;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtVO;
import kr.co.solbipos.adi.etc.ehgt.service.HqCdVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

/**
 * @Class Name : EhgtServiceImpl.java
 * @Description : 부가서비스 > 환율 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  조병준      최초생성
 *
 * @author NHN한국사이버결제 조병준
 * @since 2018. 08.09
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("EhgtService")
@Transactional
public class EhgtServiceImpl implements EhgtService {

    @Autowired
    EhgtMapper mapper;

    @Autowired
    MessageService messageService;

    @Override
    public List<DefaultMap<Object>> getEhgtListBySaleDt(EhgtVO ehgtVO, SessionInfoVO sessionInfoVO) {
        
        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        
        //세션의 본사코드를 조회에 사용
        ehgtVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            mapper.getHqEhgtListBySaleDt(ehgtVO);
        }
        // 가맹점
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            mapper.getMsEhgtListBySaleDt(ehgtVO);
        }

        
        //조회된 일자별 통화코드를 화면에 사용되기 위한 포맷으로 변경
        //{saleDate:20180809, crncyUSD:1000, crncyEUR:1200} 
        DefaultMap<Object> aMap = new DefaultMap<Object>();
        Map<String, DefaultMap<Object>> saleDateMap = new LinkedHashMap<String,
                DefaultMap<Object>>();
        
        for(DefaultMap<String> map : list) {
            aMap = saleDateMap.get(map.getStr("saleDate"));
            if(aMap == null) {
                aMap = new DefaultMap<Object>();
            }
            aMap.put("crncy" + map.getStr("crncyCd"), map.getInt("krwAmt"));
            saleDateMap.put(map.getStr("saleDate"), aMap);
        }
        
        //Map to List
        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();
        DefaultMap<Object> defaultMap = new DefaultMap<Object>();
        for(String key : saleDateMap.keySet()) {
            defaultMap = saleDateMap.get(key);
            try {
                defaultMap.put("saleDate", DateUtils.parseDate(key, "yyyyMMdd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            result.add(defaultMap);
        }
        return result;
    }

    @Override
    public List<DefaultMap<String>> getEhgtDetailBySaleDt(EhgtVO ehgtVO, SessionInfoVO sessionInfoVO) {
        
        //세션의 본사코드를 조회에 사용
        ehgtVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        //해당 일자의 데이터만 조회
        ehgtVO.setStartDt(ehgtVO.getSaleDate());
        ehgtVO.setEndDt(ehgtVO.getSaleDate());
        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            return mapper.getHqEhgtListBySaleDt(ehgtVO);
        }
        // 가맹점
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            return mapper.getMsEhgtListBySaleDt(ehgtVO);
        }
        else
        {
            return null;
        }
    }

    @Override
    public int saveEhgts(EhgtVO[] ehgtVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String dt = currentDateTimeString();

        for(EhgtVO ehgtVO: ehgtVOs) {
            ehgtVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            ehgtVO.setRegDt(dt);
            ehgtVO.setRegId(sessionInfoVO.getUserId());
            ehgtVO.setModDt(dt);
            ehgtVO.setModId(sessionInfoVO.getUserId());
            
            //해당 영업일에 데이터가 없는 경우 INSERT, 있으면 UPDATE
            ehgtVO.setStartDt(ehgtVO.getSaleDate());
            ehgtVO.setEndDt(ehgtVO.getSaleDate());
            // 본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                List<DefaultMap<String>> chkList = mapper.getHqEhgtListBySaleDt(ehgtVO);
                if(chkList == null || chkList.size() < 1) {
                    procCnt += mapper.insertHqEhgt(ehgtVO);
                }
                else {
                    procCnt += mapper.updateHqEhgt(ehgtVO);
                }
            }
            // 가맹점
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                List<DefaultMap<String>> chkList = mapper.getMsEhgtListBySaleDt(ehgtVO);
                if(chkList == null || chkList.size() < 1) {
                    procCnt += mapper.insertMsEhgt(ehgtVO);
                }
                else {
                    procCnt += mapper.updateMsEhgt(ehgtVO);
                }
            }
        }
        return procCnt;
    }

    @Override
    public List<DefaultMap<String>> getHqCdListByGrpCd(HqCdVO hqCdVO) {
        return mapper.getHqCdListByGrpCd(hqCdVO);
    }

    @Override
    public int updateHqCd(HqCdVO[] hqCdVOs, SessionInfoVO sessionInfoVO) {
        
        int procCnt = 0;
        String dt = currentDateTimeString();
        for ( HqCdVO hqCdVO : hqCdVOs ) {
            hqCdVO.setModDt(dt);
            hqCdVO.setModId(sessionInfoVO.getUserId());
            if ( hqCdVO.getStatus() == GridDataFg.UPDATE ) {
                procCnt += mapper.updateHqCdUseYn(hqCdVO);
            }
        }
        
        if ( procCnt == hqCdVOs.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }



}
