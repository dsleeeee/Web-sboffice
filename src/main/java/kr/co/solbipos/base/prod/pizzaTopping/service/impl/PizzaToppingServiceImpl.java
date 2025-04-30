package kr.co.solbipos.base.prod.pizzaTopping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.pizzaTopping.service.PizzaToppingService;
import kr.co.solbipos.base.prod.pizzaTopping.service.PizzaToppingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PizzaToppingServiceImpl.java
 * @Description : 미스터피자 > 상품관리 > 피자-토핑관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("pizzaToppingService")
@Transactional
public class PizzaToppingServiceImpl implements PizzaToppingService {
    private final PizzaToppingMapper pizzaToppingMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public PizzaToppingServiceImpl(PizzaToppingMapper pizzaToppingMapper) {
        this.pizzaToppingMapper = pizzaToppingMapper;
    }

    /** 피자-토핑관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getPizzaToppingList(PizzaToppingVO pizzaToppingVO, SessionInfoVO sessionInfoVO) {

        pizzaToppingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaToppingMapper.getPizzaToppingList(pizzaToppingVO);
    }

    /** 피자-토핑관리 - 등록 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getPizzaToppingProdList(PizzaToppingVO pizzaToppingVO, SessionInfoVO sessionInfoVO) {

        pizzaToppingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaToppingMapper.getPizzaToppingProdList(pizzaToppingVO);
    }

    /** 피자-토핑관리 - 미등록 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getPizzaToppingNoProdList(PizzaToppingVO pizzaToppingVO, SessionInfoVO sessionInfoVO) {

        pizzaToppingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaToppingMapper.getPizzaToppingNoProdList(pizzaToppingVO);
    }

    /** 피자-토핑관리 - 상품 저장 */
    @Override
    public int getPizzaToppingProdSave(PizzaToppingVO[] pizzaToppingVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(PizzaToppingVO pizzaToppingVO : pizzaToppingVOs) {
            pizzaToppingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            pizzaToppingVO.setModDt(currentDt);
            pizzaToppingVO.setModId(sessionInfoVO.getUserId());

            if (pizzaToppingVO.getStatus() == GridDataFg.INSERT) {
                pizzaToppingVO.setRegDt(currentDt);
                pizzaToppingVO.setRegId(sessionInfoVO.getUserId());

                procCnt = pizzaToppingMapper.getPizzaToppingProdSaveInsert(pizzaToppingVO);

            } else if (pizzaToppingVO.getStatus() == GridDataFg.DELETE) {
                procCnt = pizzaToppingMapper.getPizzaToppingProdSaveDelete(pizzaToppingVO);
            }
        }

        return procCnt;
    }
}