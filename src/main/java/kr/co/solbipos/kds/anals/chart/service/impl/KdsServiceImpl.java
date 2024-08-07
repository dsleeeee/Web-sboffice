package kr.co.solbipos.kds.anals.chart.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.kds.anals.chart.service.KdsService;
import kr.co.solbipos.kds.anals.chart.service.KdsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("kdsDayService")
@Transactional
public class KdsServiceImpl implements KdsService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KdsMapper mapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KdsServiceImpl(KdsMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 주문 단위 - 일자별
     */
    @Override
    public List<DefaultMap<String>> getKdsDay(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장은 본인 매장만 조회 가능
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getKdsDay(kdsVO);
    }

    /**
     * 시간대별
     */
    @Override
    public List<DefaultMap<String>> getKdsDayTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장은 본인 매장만 조회 가능
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        List<DefaultMap<String>> kdsTimeList = mapper.getKdsDayTime(kdsVO);

        for (int i = 0; i < kdsTimeList.size(); i++) {
            for (int j = 0; j < kdsVO.getKdsTimeList().size(); j++) {
                String c = kdsTimeList.get(i).getStr("cntHh" + kdsVO.getKdsTimeList().get(j));
                String p = kdsTimeList.get(i).getStr("picHh" + kdsVO.getKdsTimeList().get(j));
                String m = kdsTimeList.get(i).getStr("makeHh" + kdsVO.getKdsTimeList().get(j));
                if (c.equals("") && p.equals("") && m.equals("")) {
                    kdsTimeList.get(i).remove("cntHh" + kdsVO.getKdsTimeList().get(j));
                    kdsTimeList.get(i).remove("picHh" + kdsVO.getKdsTimeList().get(j));
                    kdsTimeList.get(i).remove("makeHh" + kdsVO.getKdsTimeList().get(j));
                }
            }
        }

        return kdsTimeList;
    }

    /**
     * 시간대별 - 차트
     */
    @Override
    public List<DefaultMap<String>> getKdsDayTimeChart(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장은 본인 매장만 조회 가능
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getKdsDayTimeChart(kdsVO);
    }

    /**
     * 일별 - 상품별
     */
    @Override
    public List<DefaultMap<String>> getKdsDayProd(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getKdsDayProd(kdsVO);
    }

    /**
     * 월별 - 상품별
     */
    @Override
    public List<DefaultMap<String>> getKdsMonth(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getKdsMonth(kdsVO);
    }

    /**
     * 매장대비
     */
    @Override
    public List<DefaultMap<String>> getKdsStore(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        /*List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if ((kdsVO.getProdCd() != null && !kdsVO.getProdCd().equals("")) || (kdsVO.getProdNm() != null && !kdsVO.getProdNm().equals("")) || (kdsVO.getProdClassCd() != null && !kdsVO.getProdClassCd().equals(""))) {
            result = mapper.getKdsStoreProd(kdsVO);
        } else {
            result = mapper.getKdsStore(kdsVO);
        }
        return result;*/
        return mapper.getKdsStore(kdsVO);
    }

    /**
     * 일 - 상품 시간대별
     */
    @Override
    public List<DefaultMap<String>> getKdsDayProdTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        } else {
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
        }
        return mapper.getKdsDayProdTime(kdsVO);
    }

    /**
     * 일 - 상품 시간대별 차트
     */
    @Override
    public List<DefaultMap<String>> getKdsDayProdTimeChart(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getKdsDayProdTimeChart(kdsVO);
    }

    /**
     * 일별-SERVICE TIME 구간별 영수증 수
     */
    @Override
    public List<DefaultMap<String>> getKdsServiceTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getKdsServiceTime(kdsVO);
    }
}
