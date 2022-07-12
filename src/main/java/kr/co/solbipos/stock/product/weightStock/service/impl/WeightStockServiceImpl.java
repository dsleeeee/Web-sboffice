package kr.co.solbipos.stock.product.weightStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.product.weightStock.service.WeightStockService;
import kr.co.solbipos.stock.product.weightStock.service.WeightStockVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import kr.co.common.system.BaseEnv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : WeightStockServiceImpl.java
 * @Description : 재고관리 > 생산관리 > 중량재고현황(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("weightStockService")
@Transactional
public class WeightStockServiceImpl implements WeightStockService {
    private final WeightStockMapper weightStockMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public WeightStockServiceImpl(WeightStockMapper weightStockMapper) {
        this.weightStockMapper = weightStockMapper;
    }

    /** 중량재고현황(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getWeightStockList(WeightStockVO weightStockVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        weightStockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        weightStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            weightStockVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(weightStockVO.getStoreCd()).equals("")) {
            weightStockVO.setArrStoreCd(weightStockVO.getStoreCd().split(","));
        }

        return weightStockMapper.getWeightStockList(weightStockVO);
    }
}