package kr.co.solbipos.base.prod.pizzaEdge.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.pizzaEdge.service.PizzaEdgeService;
import kr.co.solbipos.base.prod.pizzaEdge.service.PizzaEdgeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PizzaEdgeServiceImpl.java
 * @Description : 미스터피자 > 상품관리 > 피자-엣지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.04.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("PizzaEdgeService")
@Transactional
public class PizzaEdgeServiceImpl implements PizzaEdgeService {

    private final PizzaEdgeMapper pizzaEdgeMapper;
    private final MessageService messageService;

    @Autowired
    public PizzaEdgeServiceImpl(PizzaEdgeMapper pizzaEdgeMapper, MessageService messageService) {
        this.pizzaEdgeMapper = pizzaEdgeMapper;
        this.messageService = messageService;
    }

    /** 피자-엣지관리 - 피자 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchPizzaList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO) {

        pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaEdgeMapper.getSearchPizzaList(pizzaEdgeVO);
    }

    /** 피자-엣지관리 - 등록상품 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchPizzaMappList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO) {
        pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaEdgeMapper.getSearchPizzaMappList(pizzaEdgeVO);
    }

    /** 피자-엣지관리 - 미등록상품 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchNoRegProdList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO) {
        pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaEdgeMapper.getSearchNoRegProdList(pizzaEdgeVO);
    }

    /** 피자-엣지관리 - 등록상품 삭제 */
    @Override
    public int getDeleteProd(PizzaEdgeVO[] pizzaEdgeVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( PizzaEdgeVO pizzaEdgeVO : pizzaEdgeVOs) {

            pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            pizzaEdgeVO.setRegDt(currentDt);
            pizzaEdgeVO.setRegId(sessionInfoVO.getUserId());
            pizzaEdgeVO.setModDt(currentDt);
            pizzaEdgeVO.setModId(sessionInfoVO.getUserId());

            // 등록상품 삭제
            result = pizzaEdgeMapper.getDeleteProd(pizzaEdgeVO);

            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        return result;
    }

    /** 피자-엣지관리 - 상품등록 */
    @Override
    public int getRegProd(PizzaEdgeVO[] pizzaEdgeVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( PizzaEdgeVO pizzaEdgeVO : pizzaEdgeVOs) {

            pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            pizzaEdgeVO.setRegDt(currentDt);
            pizzaEdgeVO.setRegId(sessionInfoVO.getUserId());
            pizzaEdgeVO.setModDt(currentDt);
            pizzaEdgeVO.setModId(sessionInfoVO.getUserId());

            // 상품 등록
            result = pizzaEdgeMapper.getRegProd(pizzaEdgeVO);

            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        return result;
    }
}
