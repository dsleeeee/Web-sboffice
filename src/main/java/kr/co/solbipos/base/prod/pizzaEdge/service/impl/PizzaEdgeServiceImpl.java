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

    @Override
    public List<DefaultMap<Object>> getSearchPizzaList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO) {

        pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaEdgeMapper.getSearchPizzaList(pizzaEdgeVO);
    }

    @Override
    public List<DefaultMap<Object>> getSearchPizzaMappList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO) {
        pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaEdgeMapper.getSearchPizzaMappList(pizzaEdgeVO);
    }

    @Override
    public List<DefaultMap<Object>> getSearchNoRegProdList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO) {
        pizzaEdgeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return pizzaEdgeMapper.getSearchNoRegProdList(pizzaEdgeVO);
    }

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

            // 매장에서 사용중인 기존 옵션상품 삭제
            result = pizzaEdgeMapper.getDeleteProd(pizzaEdgeVO);

            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        return result;
    }

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

            // 매장에서 사용중인 기존 옵션상품 삭제
            result = pizzaEdgeMapper.getRegProd(pizzaEdgeVO);

            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        return result;
    }
}
