package kr.co.solbipos.iostock.vendr.vendrOrder.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderService;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("vendrOrderService")
public class VendrOrderServiceImpl implements VendrOrderService {
    private final VendrOrderMapper vendrOrderMapper;
    private final MessageService messageService;

    @Autowired
    public VendrOrderServiceImpl(VendrOrderMapper vendrOrderMapper, MessageService messageService) {
        this.vendrOrderMapper = vendrOrderMapper;
        this.messageService = messageService;
    }

    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrOrderList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqVendrOrderList(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//            result = vendrOrderMapper.getStVendrOrderList(vendrOrderVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 발주정보 상세 조회 */
    @Override
    public DefaultMap<String> getSlipInfo(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqSlipInfo(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrOrderMapper.getStSlipInfo(vendrOrderVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 발주정보 저장 */
    @Override
    public DefaultMap<String> saveVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        String slipNo = "";
        int result = 0;

        String currentDt = currentDateTimeString();
        vendrOrderVO.setRegId(sessionInfoVO.getUserId());
        vendrOrderVO.setRegDt(currentDt);
        vendrOrderVO.setModId(sessionInfoVO.getUserId());
        vendrOrderVO.setModDt(currentDt);

        // 신규등록
        if(StringUtil.getOrBlank(vendrOrderVO.getSlipNo()).equals("")) {
            // 전표번호 조회
            String yymm = DateUtil.currentDateString().substring(2, 6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            VendrOrderVO maxSlipNoVO = new VendrOrderVO();
            maxSlipNoVO.setYymm(yymm);

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                maxSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                slipNo = vendrOrderMapper.getHqNewSlipNo(maxSlipNoVO);

                vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                vendrOrderVO.setSlipNo(slipNo);
                result = vendrOrderMapper.insertHqVendrOrderHd(vendrOrderVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                maxSlipNoVO.setStoreCd(sessionInfoVO.getStoreCd());
//                slipNo = vendrOrderMapper.getStMaxSlipNo(maxSlipNoVO);

//                vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//                vendrOrderVO.setSlipNo(slipNo);
//                result = vendrOrderMapper.insertHqVendrOrderHd(vendrOrderVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        // 신규등록이 아닌 경우
        else {
            slipNo = vendrOrderVO.getSlipNo();
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                result = vendrOrderMapper.updateHqVendrOrderHd(vendrOrderVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//                result = vendrOrderMapper.updateStVendrOrderHd(vendrOrderVO);
//                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("slipNo", slipNo);
        return resultMap;
    }

}
