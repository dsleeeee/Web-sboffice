package kr.co.solbipos.sale.marketing.saleByChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.saleByChannel.service.SaleByChannelService;
import kr.co.solbipos.sale.marketing.saleByChannel.service.SaleByChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : SaleByChannelServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 채널별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("saleByChannelService")
@Transactional
public class SaleByChannelServiceImpl implements SaleByChannelService {

    private final SaleByChannelMapper saleByChannelMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public SaleByChannelServiceImpl(SaleByChannelMapper saleByChannelMapper, PopupMapper popupMapper) {
        this.saleByChannelMapper = saleByChannelMapper;
        this.popupMapper = popupMapper;
    }

    /** 채널별매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleByChannelList(SaleByChannelVO saleByChannelVO, SessionInfoVO sessionInfoVO) {

        saleByChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(saleByChannelVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleByChannelVO.getStoreCd(), 3900));
            saleByChannelVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return saleByChannelMapper.getSaleByChannelList(saleByChannelVO);
    }

    /** 그리드 컬럼 조회 */
    @Override
    public List<DefaultMap<String>> getDlvrColList(SaleByChannelVO saleByChannelVO, SessionInfoVO sessionInfoVO) {
        return saleByChannelMapper.getDlvrColList(saleByChannelVO);
    }
}
