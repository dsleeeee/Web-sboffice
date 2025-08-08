package kr.co.solbipos.sale.marketing.saleByTime.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.saleByTime.service.SaleByTimeService;
import kr.co.solbipos.sale.marketing.saleByTime.service.SaleByTimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : SaleByTimeServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 시간대별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("saleByTimeService")
@Transactional
public class SaleByTimeServiceImpl implements SaleByTimeService {

    private final SaleByTimeMapper saleByTimeMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public SaleByTimeServiceImpl(SaleByTimeMapper saleByTimeMapper, PopupMapper popupMapper) {
        this.saleByTimeMapper = saleByTimeMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대별매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleByTimeList(SaleByTimeVO saleByTimeVO, SessionInfoVO sessionInfoVO) {

        saleByTimeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(saleByTimeVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleByTimeVO.getStoreCd(), 3900));
            saleByTimeVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매출 시간대 설정
        if(saleByTimeVO.getOptionFg().equals("time")){ // 시간대
            int iSaleDateStart = Integer.parseInt(saleByTimeVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(saleByTimeVO.getEndTime());

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
                    saleByTimeVO.setArrTimeCol(arrTimeCol);
                }
            }
        } else if(saleByTimeVO.getOptionFg().equals("timeSlot")){ // 시간대분류

            String[] arrTimeCol = saleByTimeVO.getTimeCol().replace("~","").split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    saleByTimeVO.setArrTimeCol(arrTimeCol);
                }
            }
        }

        return saleByTimeMapper.getSaleByTimeList(saleByTimeVO);
    }
}
