package kr.co.solbipos.adi.etc.ehgt.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.common.data.enums.SoloHq;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtService;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtVO;
import kr.co.solbipos.adi.etc.ehgt.service.CrncyCdVO;
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
        
        //단독매장 여부(단독매장일 때 true)
        boolean isSoloStore = false;
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE
                && sessionInfoVO.getStoreCd() != null
                && sessionInfoVO.getStoreCd().equals(SoloHq.SOLO.getCode())) {
            isSoloStore = true;
        }
       
        // 본사일 경우
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            //세션의 본사코드를 조회에 사용
            ehgtVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            list = mapper.getHqEhgtListBySaleDt(ehgtVO);
        }
        //프랜차이즈 매장일 경우에는 본사 환율 조회
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE && !isSoloStore) {
            //가맹점일때 상위 조직코드 이용
            ehgtVO.setOrgnCd(sessionInfoVO.getStoreCd());
            list = mapper.getHqEhgtListBySaleDt(ehgtVO);
        }
        // 단독매장일 때 매장 환율 조회
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE && isSoloStore) {
            //세션의 본사코드를 조회에 사용
            ehgtVO.setOrgnCd(sessionInfoVO.getOrgnCd());
           
            list = mapper.getMsEhgtListBySaleDt(ehgtVO);
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
            aMap.put("crncy" + map.getStr("crncyCd"), Float.parseFloat(map.getStr("krwAmt")));
            saleDateMap.put(map.getStr("saleDate"), aMap);
        }
        
        //Map to List
        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();
        DefaultMap<Object> defaultMap = new DefaultMap<Object>();
        for(String key : saleDateMap.keySet()) {
            defaultMap = saleDateMap.get(key);
            /*
            try {
                defaultMap.put("saleDate", DateUtils.parseDate(key, "yyyyMMdd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            */
            defaultMap.put("saleDate", key);
            result.add(defaultMap);
        }
        return result;
    }

    @Override
    public List<DefaultMap<String>> getEhgtDetailBySaleDt(EhgtVO ehgtVO, SessionInfoVO sessionInfoVO) {
        
        //해당 일자의 데이터만 조회
        ehgtVO.setStartDt(ehgtVO.getSaleDate());
        ehgtVO.setEndDt(ehgtVO.getSaleDate());

        //단독매장 여부(단독매장일 때 true)
        boolean isSoloStore = false;
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE
                && sessionInfoVO.getStoreCd() != null
                && sessionInfoVO.getStoreCd().equals(SoloHq.SOLO.getCode())) {
            isSoloStore = true;
        }
       
        // 본사일 경우에는 본사 환율 조회
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            //세션의 본사코드를 조회에 사용
            ehgtVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            return mapper.getHqEhgtListBySaleDt(ehgtVO);
        }
        // 프랜차이즈 매장일 경우 본사 환율 조회
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE && !isSoloStore) {
            //가맹점일때 상위 조직코드 이용
            ehgtVO.setOrgnCd(sessionInfoVO.getStoreCd());
            return mapper.getHqEhgtListBySaleDt(ehgtVO);
        }
        // 단독매장일 때 매장 환율 조회
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE && isSoloStore) {
            return mapper.getMsEhgtListBySaleDt(ehgtVO);
        }
        throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
    }

    @Override
    public int saveEhgts(List<EhgtVO> ehgtVOs, SessionInfoVO sessionInfoVO) {
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
            // 프랜차이즈 본사 의 경우 등록/수정
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                List<DefaultMap<String>> chkList = mapper.getHqEhgtListBySaleDt(ehgtVO);
                if(chkList == null || chkList.size() < 1) {
                    procCnt += mapper.insertHqEhgt(ehgtVO);
                }
                else {
                    procCnt += mapper.updateHqEhgt(ehgtVO);
                }
            }
            // 단독 가맹점일 경우 등록/수정
            // 프랜차이즈 가맹점은 조회 화면만 있음. 
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
    public List<DefaultMap<String>> getCdListByGrpCd(UseYn useYn, SessionInfoVO sessionInfoVO) {
        
        //단독매장 여부(단독매장일 때 true)
        boolean isSoloStore = false;
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE
                && sessionInfoVO.getStoreCd() != null
                && sessionInfoVO.getStoreCd().equals(SoloHq.SOLO.getCode())) {
            isSoloStore = true;
        }
       
        CrncyCdVO crncyCdVO = new CrncyCdVO();
        crncyCdVO.setNmcodeGrpCd("052");
        crncyCdVO.setUseYn(useYn);
        
        // 본사일 경우에는 본사 환율 조회
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            //본사 통화구분 코드 조회
            //본사일 때 조직코드 값 이용
            crncyCdVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            return mapper.getHqCdListByGrpCd(crncyCdVO);
        }
        // 프랜차이즈 매장일 경우 본사 환율 조회
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE && !isSoloStore) {
            //본사 통화구분 코드 조회
            //가맹점일때 상위 조직코드 이용
            crncyCdVO.setOrgnCd(sessionInfoVO.getStoreCd());
            crncyCdVO.setNmcodeGrpCd("052");

            return mapper.getHqCdListByGrpCd(crncyCdVO);
        }
        // 단독매장일 때 매장 환율 조회
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE && isSoloStore) {
            crncyCdVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            
            return mapper.getMsCdListByGrpCd(crncyCdVO);
        }
        throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
    }

    @Override
    public int updateCrncyCd(List<CrncyCdVO> crncyCdVOs, SessionInfoVO sessionInfoVO) {
        
        int procCnt = 0;
        String dt = currentDateTimeString();
        for ( CrncyCdVO crncyCdVO : crncyCdVOs ) {
            crncyCdVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            crncyCdVO.setModDt(dt);
            crncyCdVO.setModId(sessionInfoVO.getUserId());
            if ( crncyCdVO.getStatus() == GridDataFg.UPDATE ) {
                // 본사일 경우에는 본사 환율 업데이트
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                    procCnt += mapper.updateHqCdUseYn(crncyCdVO);
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                    procCnt += mapper.updateMsCdUseYn(crncyCdVO);
                }
            }
        }
        
        if ( procCnt == crncyCdVOs.size()) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }



}
