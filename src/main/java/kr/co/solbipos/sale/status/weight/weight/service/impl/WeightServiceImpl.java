package kr.co.solbipos.sale.status.weight.weight.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.weight.weight.service.WeightService;
import kr.co.solbipos.sale.status.weight.weight.service.WeightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : WeightServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 중량별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.08  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("weightService")
public class WeightServiceImpl implements WeightService {
    private final WeightMapper weightMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public WeightServiceImpl(WeightMapper weightMapper, PopupMapper popupMapper, MessageService messageService) {
        this.weightMapper = weightMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 중량별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getWeightList(WeightVO weightVO, SessionInfoVO sessionInfoVO) {

        weightVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        weightVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        weightVO.setEmpNo(sessionInfoVO.getEmpNo());

    	if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            weightVO.setStoreCd(sessionInfoVO.getStoreCd());
		}

        if(!StringUtil.getOrBlank(weightVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(weightVO.getStoreCd(), 3900));
            weightVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return weightMapper.getWeightList(weightVO);
    }

    /** 일자별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getWeightDayList(WeightVO weightVO, SessionInfoVO sessionInfoVO) {

        weightVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        weightVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        weightVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            weightVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            weightVO.setArrStoreCd(weightVO.getStoreCd().split(","));
        }
        return weightMapper.getWeightDayList(weightVO);
    }

    /** 상품별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getWeightProdList(WeightVO weightVO, SessionInfoVO sessionInfoVO) {

        weightVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        weightVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        weightVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            weightVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            weightVO.setArrStoreCd(weightVO.getStoreCd().split(","));
        }
        return weightMapper.getWeightProdList(weightVO);
    }

    /** 상품별 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getWeightProdExcelList(WeightVO weightVO, SessionInfoVO sessionInfoVO) {

        weightVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        weightVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        weightVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            weightVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            weightVO.setArrStoreCd(weightVO.getStoreCd().split(","));
        }
        return weightMapper.getWeightProdExcelList(weightVO);
    }

}