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
        return mapper.getKdsDay(kdsVO);
    }

    /**
     * 시간대별
     */
    @Override
    public List<DefaultMap<String>> getKdsDayTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
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
        LOGGER.debug("kdsVO.orgnFg {}", kdsVO.getOrgnFg());
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
        LOGGER.debug("prodCd: {}", kdsVO.getProdCd());
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
//        if (kdsVO.getProdCd().equals(null)){
        if (kdsVO.getProdCd() == null || kdsVO.getProdCd().equals("")) {
            result = mapper.getKdsStore(kdsVO);
        } else {
            result = mapper.getKdsStoreProd(kdsVO);
        }
        return result;
    }

    /**
     * 일 - 상품 시간대별
     */
    @Override
    public List<DefaultMap<String>> getKdsDayProdTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getKdsDayProdTime(kdsVO);
    }

    /**
     * 일 - 상품 시간대별 차트
     */
    @Override
    public List<DefaultMap<String>> getKdsDayProdTimeChart(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getKdsDayProdTimeChart(kdsVO);
    }

    /**
     * 일별-SERVICE TIME 구간별 영수증 수
     */
    @Override
    public List<DefaultMap<String>> getKdsServiceTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            kdsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            kdsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getKdsServiceTime(kdsVO);
    }
}
