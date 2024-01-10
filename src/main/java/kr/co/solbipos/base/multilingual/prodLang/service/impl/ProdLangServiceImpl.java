package kr.co.solbipos.base.multilingual.prodLang.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service.FnkeyCmNmcdVO;
import kr.co.solbipos.base.multilingual.prodLang.service.ProdLangService;
import kr.co.solbipos.base.multilingual.prodLang.service.ProdLangVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdLangServiceImpl.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(상품)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.12.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("ProdLangService")
public class ProdLangServiceImpl implements ProdLangService {

    private final ProdLangMapper prodLangMapper;
    private final MessageService messageService;

    @Autowired
    public ProdLangServiceImpl(ProdLangMapper prodLangMapper, MessageService messageService) {

        this.prodLangMapper = prodLangMapper;
        this.messageService = messageService;
    }

    /** 상품명 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdNmList(ProdLangVO prodLangVO, SessionInfoVO sessionInfoVO){

        prodLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodLangVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 브랜드 array 값 세팅
            if (prodLangVO.getUserProdBrands() != null && !"".equals(prodLangVO.getUserProdBrands())) {
                String[] userBrandList = prodLangVO.getUserProdBrands().split(",");
                if (userBrandList.length > 0) {
                    prodLangVO.setUserProdBrandList(userBrandList);
                }
            }
        }

        return prodLangMapper.getProdNmList(prodLangVO);
    }

    /** 상품명 영문, 중문, 일문 저장 */
    @Override
    public int saveProdNm(ProdLangVO[] prodLangVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (ProdLangVO prodLangVO : prodLangVOs) {

            prodLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodLangVO.setRegDt(dt);
            prodLangVO.setRegId(sessionInfoVO.getUserId());
            prodLangVO.setModDt(dt);
            prodLangVO.setModId(sessionInfoVO.getUserId());

            result += prodLangMapper.saveProdNm(prodLangVO);
        }

        if (result == prodLangVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 상품설명 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdInfoList(ProdLangVO prodLangVO, SessionInfoVO sessionInfoVO){

        prodLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodLangVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 브랜드 array 값 세팅
            if (prodLangVO.getUserProdBrands() != null && !"".equals(prodLangVO.getUserProdBrands())) {
                String[] userBrandList = prodLangVO.getUserProdBrands().split(",");
                if (userBrandList.length > 0) {
                    prodLangVO.setUserProdBrandList(userBrandList);
                }
            }
        }

        return prodLangMapper.getProdInfoList(prodLangVO);
    }

    /** 상품설명 영문, 중문, 일문 저장 */
    @Override
    public int saveProdInfo(ProdLangVO[] prodLangVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (ProdLangVO prodLangVO : prodLangVOs) {

            prodLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodLangVO.setRegDt(dt);
            prodLangVO.setRegId(sessionInfoVO.getUserId());
            prodLangVO.setModDt(dt);
            prodLangVO.setModId(sessionInfoVO.getUserId());

            result += prodLangMapper.saveProdInfo(prodLangVO);
        }

        if (result == prodLangVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
