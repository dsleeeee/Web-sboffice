package kr.co.solbipos.membr.anals.postpaidSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.postpaidSale.service.PostpaidSaleService;
import kr.co.solbipos.membr.anals.postpaidSale.service.PostpaidSaleVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PostpaidSaleServiceImpl.java
 * @Description : 국민대 > 매출처관리 > 외상매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("postpaidSaleService")
@Transactional
public class PostpaidSaleServiceImpl implements PostpaidSaleService {
    private final PostpaidSaleMapper postpaidSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public PostpaidSaleServiceImpl(PostpaidSaleMapper postpaidSaleMapper){
        this.postpaidSaleMapper = postpaidSaleMapper;
    }

    /** 외상매출조회 - 조회 */
    @Override
    public List<DefaultMap<Object>> getPostpaidSaleList(PostpaidSaleVO postpaidSaleVO, SessionInfoVO sessionInfoVO) {

        postpaidSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        postpaidSaleVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        return postpaidSaleMapper.getPostpaidSaleList(postpaidSaleVO);
    }

}