package kr.co.solbipos.sale.benson.timeProdBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.benson.timeProdBenson.service.TimeProdBensonService;
import kr.co.solbipos.sale.benson.timeProdBenson.service.TimeProdBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeProdBensonServiceImpl.java
 * @Description : (벤슨) 상품매출분석 > 상품별시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeProdBensonService")
@Transactional
public class TimeProdBensonServiceImpl implements TimeProdBensonService {
    private final TimeProdBensonMapper timeProdBensonMapper;
    private final PopupMapper popupMapper;

    public TimeProdBensonServiceImpl(TimeProdBensonMapper timeProdBensonMapper, PopupMapper popupMapper) {
        this.timeProdBensonMapper = timeProdBensonMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdBensonList(TimeProdBensonVO timeProdBensonVO, SessionInfoVO sessionInfoVO) {

        timeProdBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeProdBensonVO.getStoreCds(), 3900));
            timeProdBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(timeProdBensonVO.getProdCds(), 3900));
            timeProdBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeProdBensonVO.getStoreHqBrandCd() == "" || timeProdBensonVO.getStoreHqBrandCd() == null || timeProdBensonVO.getProdHqBrandCd() == "" || timeProdBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdBensonVO.getUserBrands().split(",");
                timeProdBensonVO.setUserBrandList(userBrandList);
            }
        }

        if(timeProdBensonVO.getOptionFg().equals("time")){ // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(timeProdBensonVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(timeProdBensonVO.getEndTime());

            String timeCol = "";

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
                timeCol += Integer.toString(i);
                if(i != iSaleDateEnd){
                    timeCol += ",";
                }
            }

            String[] arrTimeCol = timeCol.split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    timeProdBensonVO.setArrTimeCol(arrTimeCol);
                }
            }
        } else if(timeProdBensonVO.getOptionFg().equals("timeSlot")){

            String[] arrTimeCol = timeProdBensonVO.getTimeCol().replace("~","").split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    timeProdBensonVO.setArrTimeCol(arrTimeCol);
                }
            }
        }

        return timeProdBensonMapper.getTimeProdBensonList(timeProdBensonVO);
    }

    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdBensonExcelList(TimeProdBensonVO timeProdBensonVO, SessionInfoVO sessionInfoVO) {

        timeProdBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeProdBensonVO.getStoreCds(), 3900));
            timeProdBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(timeProdBensonVO.getProdCds(), 3900));
            timeProdBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeProdBensonVO.getStoreHqBrandCd() == "" || timeProdBensonVO.getStoreHqBrandCd() == null || timeProdBensonVO.getProdHqBrandCd() == "" || timeProdBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdBensonVO.getUserBrands().split(",");
                timeProdBensonVO.setUserBrandList(userBrandList);
            }
        }

        if(timeProdBensonVO.getOptionFg().equals("time")){ // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(timeProdBensonVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(timeProdBensonVO.getEndTime());

            String timeCol = "";

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
                timeCol += Integer.toString(i);
                if(i != iSaleDateEnd){
                    timeCol += ",";
                }
            }

            String[] arrTimeCol = timeCol.split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    timeProdBensonVO.setArrTimeCol(arrTimeCol);
                }
            }
        } else if(timeProdBensonVO.getOptionFg().equals("timeSlot")){

            String[] arrTimeCol = timeProdBensonVO.getTimeCol().replace("~","").split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    timeProdBensonVO.setArrTimeCol(arrTimeCol);
                }
            }
        }

        return timeProdBensonMapper.getTimeProdBensonExcelList(timeProdBensonVO);
    }
}
