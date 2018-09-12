package kr.co.solbipos.adi.etc.kitchenmemo.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoService;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
* @Class Name : KitchenMemoServiceImpl.java
* @Description : 부가서비스 > 주방메모관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("kitchenMemoService")
public class KitchenMemoServiceImpl implements KitchenMemoService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SessionService sessionService;

    @Autowired
    KitchenMemoMapper mapper;

    /** 주방메모 목록 조회 */
    @Override
    public List<DefaultMap<String>> getKitchenMemoList(KitchenMemoVO kitchenMemoVO,
        SessionInfoVO sessionInfoVO) {

        // 조회결과
        List<DefaultMap<String>> returnList = null;

        kitchenMemoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kitchenMemoVO.setRegFg(sessionInfoVO.getOrgnFg().toString());

        // 로그인을 본사로 했으면 본사의 주방메모 조회 / 매장으로 하면 매장의 주방메모 조회
        // 주방메모-본사통제여부가 '본사'면 해당 본사의 메모 조회
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            kitchenMemoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        returnList = mapper.getKitchenMemoList(kitchenMemoVO);

        return returnList;
    }

    /** 주방메모 저장 */
    @Override
    public int save(KitchenMemoVO[] kitchenMemoVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(KitchenMemoVO kitchenMemoVO : kitchenMemoVOs){

            kitchenMemoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // regFg : 등록주체 ( STORE:매장 / HQ :본사 )
            kitchenMemoVO.setRegFg(sessionInfoVO.getOrgnFg().toString());
            // 매장에서 등록시에만 매장코드가 들어간다.
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                kitchenMemoVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            kitchenMemoVO.setRegId(sessionInfoVO.getUserId());
            kitchenMemoVO.setRegDt(insertDt);
            kitchenMemoVO.setModId(sessionInfoVO.getUserId());
            kitchenMemoVO.setModDt(insertDt);

            if(kitchenMemoVO.getStatus() == GridDataFg.INSERT) {
                // 본사코드 기준으로 max값
                String kitchnMemoCd = mapper.getKitchnMemoCd(kitchenMemoVO);
                kitchenMemoVO.setKitchnMemoCd(kitchnMemoCd);
                procCnt += mapper.insertKitchenMemo(kitchenMemoVO);
            }
            else if(kitchenMemoVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateKitchenMemo(kitchenMemoVO);
            }
            else if(kitchenMemoVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteKitchenMemo(kitchenMemoVO);
            }
        }
        return procCnt;
    }

}
