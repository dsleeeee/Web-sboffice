package kr.co.solbipos.base.prod.prodImg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgService;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service("prodImgService")
public class ProdImgServiceImpl implements ProdImgService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProdImgMapper prodImgMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public ProdImgServiceImpl(ProdImgMapper prodImgMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.prodImgMapper = prodImgMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**  상품이미지관리 - 상품목록조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodImgVO.setOrgnFg(orgnFg);
        prodImgVO.setHqOfficeCd(hqOfficeCd);
        prodImgVO.setStoreCd(storeCd);

        /*
          단독매장의 경우 SALE_PRC_FG = '2' (매장판매가 셋팅)
          프랜차이즈의 경우, 상품 판매가 본사통제여부 조회하여
          본사통제구분이 '본사'일때, SALE_PRC_FG = '1' (본사판매가 셋팅)
          본사통제구분이 '매장'일때, SALE_PRC_FG = '2' (매장판매가 셋팅)
        */
        if("00000".equals(hqOfficeCd)) { // 단독매장
            prodImgVO.setSalePrcFg("2");
        } else {

            // 상품가격 본사통제여부 확인(1 : 본사통제, 2 : 매장통제)
            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

            if( StringUtil.isEmpties(storeCd)) { // 본사일때
                prodImgVO.setSalePrcFg("1");
            } else {                             // 매장일때
                if("1".equals(envstVal)) prodImgVO.setSalePrcFg("1");
                else                     prodImgVO.setSalePrcFg("2");
            }
        }
        return prodImgMapper.getProdList(prodImgVO);
    }

    /**  상품이미지관리 - 상품이미지조회 */
    @Override
    public List<DefaultMap<String>> getProdImg(@RequestBody ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        prodImgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodImgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodImgVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodImgMapper.getProdImg(prodImgVO);
    }
}
