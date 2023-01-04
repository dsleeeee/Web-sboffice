package kr.co.solbipos.base.prod.prodOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionService;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionVO;
import kr.co.solbipos.base.prod.prodOption.service.impl.ProdOptionMapper;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdOptionServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.19  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodOptionService")
@Transactional
public class ProdOptionServiceImpl implements ProdOptionService {
    private final ProdOptionMapper prodOptionMapper;

    public ProdOptionServiceImpl(ProdOptionMapper prodOptionMapper) {
        this.prodOptionMapper = prodOptionMapper;
    }

    /** 옵션그룹조회 */
    @Override
    public List<DefaultMap<String>> getProdOptionGroup(ProdOptionVO prodOptionVO, SessionInfoVO sessionInfoVO) {
        prodOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return prodOptionMapper.getProdOptionGroup(prodOptionVO);
    }

    /** 옵션그룹저장 */
    @Override
    public int saveProdOptionGroup(ProdOptionVO[] prodOptionVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
        for(ProdOptionVO prodOptionVO : prodOptionVOs){
            prodOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodOptionVO.setRegFg(sessionInfoVO.getOrgnFg().getCode());
            prodOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            prodOptionVO.setRegDt(dt);
            prodOptionVO.setRegId(sessionInfoVO.getUserId());
            prodOptionVO.setModDt(dt);
            prodOptionVO.setModId(sessionInfoVO.getUserId());

            if ( prodOptionVO.getStatus() == GridDataFg.INSERT ) {      // 등록
                prodOptionVO.setOptionGrpCd(prodOptionMapper.getProdOptionGroupCode(prodOptionVO));
                result += prodOptionMapper.saveProdOptionGroup(prodOptionVO);
            } else if(prodOptionVO.getStatus() == GridDataFg.UPDATE) {  // 수정
                result += prodOptionMapper.saveProdOptionGroup(prodOptionVO);
            } else if(prodOptionVO.getStatus() == GridDataFg.DELETE) {  // 삭제
                result += prodOptionMapper.deleteProdOptionGroup(prodOptionVO);
            }
        }
        return result;
    }

    /** 옵션속성조회 */
    @Override
    public List<DefaultMap<String>> getProdOptionVal(ProdOptionVO prodOptionVO, SessionInfoVO sessionInfoVO) {
        prodOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return prodOptionMapper.getProdOptionVal(prodOptionVO);
    }

    /** 옵션속성저장 */
    @Override
    public int saveProdOptionVal(ProdOptionVO[] prodOptionVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
        for(ProdOptionVO prodOptionVO : prodOptionVOs){
            prodOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodOptionVO.setRegFg(sessionInfoVO.getOrgnFg().getCode());
            prodOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            prodOptionVO.setRegDt(dt);
            prodOptionVO.setRegId(sessionInfoVO.getUserId());
            prodOptionVO.setModDt(dt);
            prodOptionVO.setModId(sessionInfoVO.getUserId());

            if ( prodOptionVO.getStatus() == GridDataFg.INSERT ) {      // 등록
                prodOptionVO.setOptionValCd(prodOptionMapper.getProdOptionValCode(prodOptionVO));
                result += prodOptionMapper.saveProdOptionVal(prodOptionVO);
            } else if(prodOptionVO.getStatus() == GridDataFg.UPDATE) {  // 수정
                result += prodOptionMapper.saveProdOptionVal(prodOptionVO);
            } else if(prodOptionVO.getStatus() == GridDataFg.DELETE) {  // 삭제
                result += prodOptionMapper.deleteProdOptionVal(prodOptionVO);
            }
        }
        return result;
    }


}