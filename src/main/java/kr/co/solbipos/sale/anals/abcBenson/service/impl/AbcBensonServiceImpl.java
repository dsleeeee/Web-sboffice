package kr.co.solbipos.sale.anals.abcBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.abcBenson.service.AbcBensonService;
import kr.co.solbipos.sale.anals.abcBenson.service.AbcBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : AbcBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 상품 ABC분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("AbcBensonService")
@Transactional
public class AbcBensonServiceImpl implements AbcBensonService {

    private final AbcBensonMapper abcBensonMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public AbcBensonServiceImpl(AbcBensonMapper abcBensonMapper, PopupMapper popupMapper) {
        this.abcBensonMapper = abcBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**상픔ABC분석 - 상픔ABC분석 리스트 조회   */
    @Override
    public List<DefaultMap<String>> getAbcBensonList(AbcBensonVO abcBensonVO, SessionInfoVO sessionInfoVO) {
        abcBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        abcBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        abcBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(abcBensonVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(abcBensonVO.getStoreCd(), 3900));
            abcBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return abcBensonMapper.getAbcBensonList(abcBensonVO);
    }
}
