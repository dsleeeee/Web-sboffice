package kr.co.solbipos.base.prod.kioskDisplay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayService;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayVO;
import kr.co.solbipos.base.prod.prodBarcd.service.ProdBarcdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskDisplayServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 비노출관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.10  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("KioskDisplayService")
public class KioskDisplayServiceImpl implements KioskDisplayService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KioskDisplayMapper kioskDisplayMapper;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public KioskDisplayServiceImpl(KioskDisplayMapper kioskDisplayMapper, PopupMapper popupMapper) {
        this.kioskDisplayMapper = kioskDisplayMapper;
        this.popupMapper = popupMapper;
    }

    @Override
    public List<DefaultMap<String>> getProdList(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            kioskDisplayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        kioskDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskDisplayVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(kioskDisplayVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(kioskDisplayVO.getStoreCd(), 3900));
            kioskDisplayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (kioskDisplayVO.getProdCds() != null && !"".equals(kioskDisplayVO.getProdCds())) {
            String[] prodCdList = kioskDisplayVO.getProdCds().split(",");
            kioskDisplayVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (kioskDisplayVO.getStoreHqBrandCd() == "" || kioskDisplayVO.getStoreHqBrandCd() == null || kioskDisplayVO.getProdHqBrandCd() == "" || kioskDisplayVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskDisplayVO.getUserBrands() != null && !"".equals(kioskDisplayVO.getUserBrands())) {
                    String[] userBrandList = kioskDisplayVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskDisplayVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return kioskDisplayMapper.getProdList(kioskDisplayVO);
    }

    @Override
    public DefaultMap<String> getProdDetail(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO) {

        DefaultMap result = new DefaultMap<>();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            kioskDisplayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 상품상세정보 조회
        result = kioskDisplayMapper.getProdDetail(kioskDisplayVO);

        // 연결상품목록 조회
        List<DefaultMap<String>> linkedProdList = kioskDisplayMapper.getLinkedProdList(kioskDisplayVO);
        result.put("linkedProdList", linkedProdList);

        return result;
    }

    // 상품 품절관리 업데이트
    @Override
    public int getProdKioskDisplaySave(KioskDisplayVO[] kioskDisplayVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(KioskDisplayVO kioskDisplayVO : kioskDisplayVOs) {

            kioskDisplayVO.setModDt(currentDt);
            kioskDisplayVO.setModId(sessionInfoVO.getUserId());

            procCnt = kioskDisplayMapper.getProdKioskDisplaySave(kioskDisplayVO);
        }

        return procCnt;
    }

    // 엑셀 업로드 전 매장코드, 상품코드 유효여부 체크
    @Override
    public int chkCd(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO) {
        kioskDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 상품코드 array 값 세팅
        kioskDisplayVO.setArrProdCdCol(kioskDisplayVO.getProdCdCol().split(","));

        return kioskDisplayMapper.chkCd(kioskDisplayVO);
    }

    // 엑셀 업로드
    @Override
    public int getExcelUploadSave(KioskDisplayVO[] kioskDisplayVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (KioskDisplayVO kioskDisplayVO : kioskDisplayVOs) {
            kioskDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskDisplayVO.setModDt(currentDt);
            kioskDisplayVO.setModId(sessionInfoVO.getUserId());

            result += kioskDisplayMapper.getExcelUploadSave(kioskDisplayVO);
        }

        return result;
    }
}