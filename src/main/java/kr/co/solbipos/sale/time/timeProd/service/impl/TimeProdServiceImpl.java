package kr.co.solbipos.sale.time.timeProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.time.timeProd.service.TimeProdService;
import kr.co.solbipos.sale.time.timeProd.service.TimeProdVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeProdServiceImpl.java
 * @Description : 맘스터치 > 시간대별매출 > 상품별 시간대 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeProdService")
@Transactional
public class TimeProdServiceImpl implements TimeProdService {
    private final TimeProdMapper timeProdMapper;

    public TimeProdServiceImpl(TimeProdMapper timeProdMapper) {
        this.timeProdMapper = timeProdMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdList(TimeProdVO timeProdVO, SessionInfoVO sessionInfoVO) {

        timeProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeProdVO.getStoreCds().split(",");
        timeProdVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (timeProdVO.getProdCds() != null && !"".equals(timeProdVO.getProdCds())) {
            String[] prodCdList = timeProdVO.getProdCds().split(",");
            timeProdVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeProdVO.getStoreHqBrandCd() == "" || timeProdVO.getStoreHqBrandCd() == null || timeProdVO.getProdHqBrandCd() == "" || timeProdVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdVO.getUserBrands().split(",");
                timeProdVO.setUserBrandList(userBrandList);
            }
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수
        String timeCol = "";
        for(int i = 0; i <= 23; i++) {
            timeCol += Integer.toString(i);
            if(i != 23){
                timeCol += ",";
            }
        }
        String[] arrTimeCol = timeCol.split(",");
        timeProdVO.setArrTimeCol(arrTimeCol);

        return timeProdMapper.getTimeProdList(timeProdVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdExcelList(TimeProdVO timeProdVO, SessionInfoVO sessionInfoVO) {

        timeProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeProdVO.getStoreCds().split(",");
        timeProdVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (timeProdVO.getProdCds() != null && !"".equals(timeProdVO.getProdCds())) {
            String[] prodCdList = timeProdVO.getProdCds().split(",");
            timeProdVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeProdVO.getStoreHqBrandCd() == "" || timeProdVO.getStoreHqBrandCd() == null || timeProdVO.getProdHqBrandCd() == "" || timeProdVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdVO.getUserBrands().split(",");
                timeProdVO.setUserBrandList(userBrandList);
            }
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수
        String timeCol = "";
        for(int i = 0; i <= 23; i++) {
            timeCol += Integer.toString(i);
            if(i != 23){
                timeCol += ",";
            }
        }
        String[] arrTimeCol = timeCol.split(",");
        timeProdVO.setArrTimeCol(arrTimeCol);

        return timeProdMapper.getTimeProdExcelList(timeProdVO);
    }
}