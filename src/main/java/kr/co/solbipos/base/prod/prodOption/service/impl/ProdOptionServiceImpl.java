package kr.co.solbipos.base.prod.prodOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionService;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /** 추가(상품포함) 팝업 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(ProdOptionVO prodOptionVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        prodOptionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        prodOptionVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (prodOptionVO.getProdHqBrandCd() == "" || prodOptionVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (prodOptionVO.getUserProdBrands() != null && !"".equals(prodOptionVO.getUserProdBrands())) {
                    String[] userBrandList = prodOptionVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        prodOptionVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return prodOptionMapper.getProdList(prodOptionVO);

    }

}