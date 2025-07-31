package kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.DaySaleMrpizzaVO;
import kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service.MenuSaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service.MenuSaleMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MenuSaleMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 메뉴별판매
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("menuSaleMrpizzaService")
@Transactional
public class MenuSaleMrpizzaServiceImpl implements MenuSaleMrpizzaService {

    private final MenuSaleMrpizzaMapper menuSaleMrpizzaMapper;
    private final PopupMapper popupMapper;

    public MenuSaleMrpizzaServiceImpl(MenuSaleMrpizzaMapper menuSaleMrpizzaMapper, PopupMapper popupMapper) {
        this.menuSaleMrpizzaMapper = menuSaleMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /** 메뉴별판매 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getMenuSaleMrpizzaList(MenuSaleMrpizzaVO menuSaleMrpizzaVO, SessionInfoVO sessionInfoVO) {

        menuSaleMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(menuSaleMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(menuSaleMrpizzaVO.getStoreCds(), 3900));
            menuSaleMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (menuSaleMrpizzaVO.getProdClassCd() != null && !"".equals(menuSaleMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = menuSaleMrpizzaVO.getProdClassCd().split(",");
            menuSaleMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (menuSaleMrpizzaVO.getProdCds() != null && !"".equals(menuSaleMrpizzaVO.getProdCds())) {
            String[] prodCdList = menuSaleMrpizzaVO.getProdCds().split(",");
            menuSaleMrpizzaVO.setProdCdList(prodCdList);
        }

        return menuSaleMrpizzaMapper.getMenuSaleMrpizzaList(menuSaleMrpizzaVO);
    }

    /** 메뉴별판매 엑셀다운로드 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getMenuSaleMrpizzaExcelList(MenuSaleMrpizzaVO menuSaleMrpizzaVO, SessionInfoVO sessionInfoVO) {

        menuSaleMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(menuSaleMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(menuSaleMrpizzaVO.getStoreCds(), 3900));
            menuSaleMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (menuSaleMrpizzaVO.getProdClassCd() != null && !"".equals(menuSaleMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = menuSaleMrpizzaVO.getProdClassCd().split(",");
            menuSaleMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (menuSaleMrpizzaVO.getProdCds() != null && !"".equals(menuSaleMrpizzaVO.getProdCds())) {
            String[] prodCdList = menuSaleMrpizzaVO.getProdCds().split(",");
            menuSaleMrpizzaVO.setProdCdList(prodCdList);
        }

        return menuSaleMrpizzaMapper.getMenuSaleMrpizzaExcelList(menuSaleMrpizzaVO);
    }
}
