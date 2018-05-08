package kr.co.solbipos.adi.service.etc.kitchenmemo;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemoVO;
import kr.co.solbipos.adi.persistence.etc.kitchenmemo.KitchenMemoMapper;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.application.enums.grid.GridDataFg;

@Service
public class KitchenMemoServiceImpl implements KitchenMemoService {

    @Autowired
    SessionService sessionService;

    @Autowired
    KitchenMemoMapper kichenMemoMapper;

    @Override
    public <E> List<E> selectKitchenMemo(SessionInfoVO sessionInfoVO) {
        return kichenMemoMapper.selectKitchenMemo(sessionInfoVO);
    }

    @Override
    public int save(KitchenMemoVO[] kitchenMemoVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(KitchenMemoVO kitchenMemoVO : kitchenMemoVOs){
            kitchenMemoVO.setStoreCd(sessionInfoVO.getOrgnCd());
            kitchenMemoVO.setRegId(sessionInfoVO.getUserId());
            kitchenMemoVO.setRegDt(insertDt);
            kitchenMemoVO.setModId(sessionInfoVO.getUserId());
            kitchenMemoVO.setModDt(insertDt);

            if(kitchenMemoVO.getStatus() == GridDataFg.INSERT) {
                procCnt += kichenMemoMapper.insertKitchenMemo(kitchenMemoVO);
            }
            else if(kitchenMemoVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += kichenMemoMapper.updateKitchenMemo(kitchenMemoVO);
            }
            else if(kitchenMemoVO.getStatus() == GridDataFg.DELETE) {
                procCnt += kichenMemoMapper.deleteKitchenMemo(kitchenMemoVO);
            }
        }
        return procCnt;
    }

    @Override
    public int selectKitchenMemoCnt(KitchenMemoVO kitchenMemoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        kitchenMemoVO.setStoreCd(sessionInfoVO.getOrgnCd());
        kitchenMemoVO.setRegId(sessionInfoVO.getUserId());
        kitchenMemoVO.setModId(sessionInfoVO.getUserId());

        return kichenMemoMapper.selectKitchenMemoCnt(kitchenMemoVO);
    }
}
