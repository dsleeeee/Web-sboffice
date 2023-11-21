package kr.co.solbipos.membr.anals.postpaidDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidService;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.anals.postpaid.service.impl.PostpaidMapper;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlService;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PostpaidDtlServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 후불회원상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.05.21  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.05.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("postpaidDtlService")
@Transactional
public class PostpaidDtlServiceImpl implements PostpaidDtlService {

    private final PostpaidDtlMapper mapper;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public PostpaidDtlServiceImpl(PostpaidDtlMapper mapper, PopupMapper popupMapper) {
        this.mapper = mapper;
        this.popupMapper = popupMapper;
    }

    /** 후불 회원 외상, 입금 내역 상세 - 조회 */
    @Override
    public List<DefaultMap<Object>> getPostpaidDtlMemberList(PostpaidDtlVO postpaidDtlVO, SessionInfoVO sessionInfoVO) {

        postpaidDtlVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        postpaidDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            postpaidDtlVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(postpaidDtlVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(postpaidDtlVO.getStoreCds(), 3900));
            postpaidDtlVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mapper.getPostpaidDtlMemberList(postpaidDtlVO);
    }

    /** 후불 회원 외상, 입금 내역 상세 - 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getPostpaidDtlMemberExcelList(PostpaidDtlVO postpaidDtlVO, SessionInfoVO sessionInfoVO) {

        postpaidDtlVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        postpaidDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            postpaidDtlVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(postpaidDtlVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(postpaidDtlVO.getStoreCds(), 3900));
            postpaidDtlVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mapper.getPostpaidDtlMemberExcelList(postpaidDtlVO);
    }
}
