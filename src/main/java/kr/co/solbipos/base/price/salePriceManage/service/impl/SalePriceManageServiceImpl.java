package kr.co.solbipos.base.price.salePriceManage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePriceManage.service.SalePriceManageService;
import kr.co.solbipos.base.price.salePriceManage.service.SalePriceManageVO;
import kr.co.solbipos.base.price.salePrice.service.impl.SalePriceMapper;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : SalePriceManageServiceImpl.java
 * @Description : 기초관리 > 가격관리 > 판매가관리(매장용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.04.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("salePriceManageService")
@Transactional
public class SalePriceManageServiceImpl implements SalePriceManageService {
    private final SalePriceManageMapper salePriceManageMapper;
    private final SalePriceMapper salePriceMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SalePriceManageServiceImpl(SalePriceManageMapper salePriceManageMapper, SalePriceMapper salePriceMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.salePriceManageMapper = salePriceManageMapper;
        this.salePriceMapper = salePriceMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 판매가관리 조회 */
    @Override
    public List<DefaultMap<Object>> getSalePriceManageList(SalePriceManageVO salePriceManageVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        salePriceManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        salePriceManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            salePriceManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return salePriceManageMapper.getSalePriceManageList(salePriceManageVO);
    }

    /** 판매가관리 저장 */
    @Override
    public int getSalePriceManageSave(SalePriceManageVO[] salePriceManageVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDate = currentDateString();
        String currentDt = currentDateTimeString();

        for(SalePriceManageVO salePriceManageVO : salePriceManageVOs) {

            // SalePriceVO
            SalePriceVO salePriceVO = new SalePriceVO();

            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceVO.setStoreCd(sessionInfoVO.getStoreCd());

//            salePriceVO.setPrcCtrlFg("2"); // 매장에서 등록
            salePriceVO.setStartDate(currentDate);
            salePriceVO.setEndDate("99991231");

            salePriceVO.setRegDt(currentDt);
            salePriceVO.setRegId(sessionInfoVO.getUserId());
            salePriceVO.setModDt(currentDt);
            salePriceVO.setModId(sessionInfoVO.getUserId());

            salePriceVO.setProdCd(salePriceManageVO.getProdCd());
            salePriceVO.setSaleUprc(salePriceManageVO.getSaleUprc());

            // 판매가 변경 히스토리 등록
            int prodCnt = salePriceMapper.getRegistProdCount(salePriceVO);

            if(prodCnt > 0){
                result = salePriceMapper.updateStoreProdSalePriceHistory(salePriceVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 매장 판매가 변경
            result = salePriceMapper.modifyStoreProdSalePrice(salePriceVO);

        }
        return result;
    }
}