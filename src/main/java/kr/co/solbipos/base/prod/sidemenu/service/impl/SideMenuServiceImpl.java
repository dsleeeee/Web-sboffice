package kr.co.solbipos.base.prod.sidemenu.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.sidemenu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SideMenuServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sideMenuService")
public class SideMenuServiceImpl implements SideMenuService {

    private final SideMenuMapper sideMenuMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public SideMenuServiceImpl(SideMenuMapper sideMenuMapper, MessageService messageService) {
        this.sideMenuMapper = sideMenuMapper;
        this.messageService = messageService;
    }

    /** 사이드메뉴-속성탭-속성분류 목록 조회 */
    @Override
    public List<DefaultMap<String>> getAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO,
        SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        sideMenuAttrClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuAttrClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideMenuAttrClassVO.setStoreCd(sessionInfoVO.getStoreCd());

        return sideMenuMapper.getAttrClassList(sideMenuAttrClassVO);
    }

    /** 사이드메뉴-속성탭-속성분류 저장 */
    @Override
    public int saveAttrClassList(SideMenuAttrClassVO[] sideMenuAttrClassVOs,
        SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( SideMenuAttrClassVO sideMenuAttrClassVO : sideMenuAttrClassVOs ) {

            // 소속구분 설정
            sideMenuAttrClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            sideMenuAttrClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            sideMenuAttrClassVO.setStoreCd(sessionInfoVO.getStoreCd());

            sideMenuAttrClassVO.setRegDt(currentDt);
            sideMenuAttrClassVO.setRegId(sessionInfoVO.getUserId());
            sideMenuAttrClassVO.setModDt(currentDt);
            sideMenuAttrClassVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( sideMenuAttrClassVO.getStatus() == GridDataFg.INSERT ) {
                result += sideMenuMapper.insertAttrClassList(sideMenuAttrClassVO);
                // 수정
            } else if ( sideMenuAttrClassVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateAttrClassList(sideMenuAttrClassVO);
                // 삭제
            } else if ( sideMenuAttrClassVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteAttrClassList(sideMenuAttrClassVO);
            }

        }

        if ( result == sideMenuAttrClassVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 사이드메뉴-속성탭-속성 목록 조회 */
    @Override
    public List<DefaultMap<String>> getAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO,
        SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        sideMenuAttrCdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuAttrCdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideMenuAttrCdVO.setStoreCd(sessionInfoVO.getStoreCd());

        return sideMenuMapper.getAttrCdList(sideMenuAttrCdVO);
    }

    /** 사이드메뉴-속성탭-속성 저장 */
    @Override
    public int saveAttrCdList(SideMenuAttrCdVO[] sideMenuAttrCdVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( SideMenuAttrCdVO sideMenuAttrCdVO : sideMenuAttrCdVOS) {

            // 소속구분 설정
            sideMenuAttrCdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            sideMenuAttrCdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            sideMenuAttrCdVO.setStoreCd(sessionInfoVO.getStoreCd());

            sideMenuAttrCdVO.setRegDt(currentDt);
            sideMenuAttrCdVO.setRegId(sessionInfoVO.getUserId());
            sideMenuAttrCdVO.setModDt(currentDt);
            sideMenuAttrCdVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( sideMenuAttrCdVO.getStatus() == GridDataFg.INSERT ) {
                result += sideMenuMapper.insertAttrCdList(sideMenuAttrCdVO);
                // 수정
            } else if ( sideMenuAttrCdVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateAttrCdList(sideMenuAttrCdVO);
                // 삭제
            } else if ( sideMenuAttrCdVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteAttrCdList(sideMenuAttrCdVO);
            }

        }

        if ( result == sideMenuAttrCdVOS.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 사이드메뉴-선택메뉴탭-선택그룹 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuGrpList(SideMenuSelGroupVO sideMenuSelGroupVO,
        SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        sideMenuSelGroupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuSelGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideMenuSelGroupVO.setStoreCd(sessionInfoVO.getStoreCd());

        return sideMenuMapper.getMenuGrpList(sideMenuSelGroupVO);
    }

    /** 사이드메뉴-선택메뉴탭-선택그룹 저장 */
    @Override
    public int saveMenuGrpList(SideMenuSelGroupVO[] sideMenuSelGroupVOs,
        SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( SideMenuSelGroupVO sideMenuSelGroupVO : sideMenuSelGroupVOs) {

            // 소속구분 설정
            sideMenuSelGroupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            sideMenuSelGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            sideMenuSelGroupVO.setStoreCd(sessionInfoVO.getStoreCd());

            sideMenuSelGroupVO.setRegDt(currentDt);
            sideMenuSelGroupVO.setRegId(sessionInfoVO.getUserId());
            sideMenuSelGroupVO.setModDt(currentDt);
            sideMenuSelGroupVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( sideMenuSelGroupVO.getStatus() == GridDataFg.INSERT ) {
                result += sideMenuMapper.insertMenuGrpList(sideMenuSelGroupVO);
                // 수정
            } else if ( sideMenuSelGroupVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateMenuGrpList(sideMenuSelGroupVO);
                // 삭제
            } else if ( sideMenuSelGroupVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteMenuGrpList(sideMenuSelGroupVO);
            }

        }

        if ( result == sideMenuSelGroupVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 사이드메뉴-선택메뉴탭-선택분류 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuClassList(SideMenuSelClassVO sideMenuSelClassVO,
        SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        sideMenuSelClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideMenuSelClassVO.setStoreCd(sessionInfoVO.getStoreCd());

        return sideMenuMapper.getMenuClassList(sideMenuSelClassVO);
    }

    /** 사이드메뉴-선택메뉴탭-선택분류 저장 */
    @Override
    public int saveMenuClassList(SideMenuSelClassVO[] sideMenuSelClassVOs,
        SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( SideMenuSelClassVO sideMenuSelClassVO : sideMenuSelClassVOs) {

            // 소속구분 설정
            sideMenuSelClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            sideMenuSelClassVO.setStoreCd(sessionInfoVO.getStoreCd());

            sideMenuSelClassVO.setRegDt(currentDt);
            sideMenuSelClassVO.setRegId(sessionInfoVO.getUserId());
            sideMenuSelClassVO.setModDt(currentDt);
            sideMenuSelClassVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( sideMenuSelClassVO.getStatus() == GridDataFg.INSERT ) {
                result += sideMenuMapper.insertMenuClassList(sideMenuSelClassVO);
                // 수정
            } else if ( sideMenuSelClassVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateMenuClassList(sideMenuSelClassVO);
                // 삭제
            } else if ( sideMenuSelClassVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteMenuClassList(sideMenuSelClassVO);
            }

        }

        if ( result == sideMenuSelClassVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 사이드메뉴-선택메뉴 - 선택할 상품 목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(SideMenuSelProdVO sideMenuSelProdVO,
        SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        sideMenuSelProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideMenuSelProdVO.setStoreCd(sessionInfoVO.getStoreCd());

        return sideMenuMapper.getProdList(sideMenuSelProdVO);

    }

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuProdList(SideMenuSelProdVO sideMenuSelProdVO,
        SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        sideMenuSelProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideMenuSelProdVO.setStoreCd(sessionInfoVO.getStoreCd());

        return sideMenuMapper.getMenuProdList(sideMenuSelProdVO);
    }

    /** 사이드메뉴-선택메뉴탭-선택상품 저장 */
    @Override
    public int saveMenuProdList(SideMenuSelProdVO[] sideMenuSelProdVOs,
        SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( SideMenuSelProdVO sideMenuSelProdVO : sideMenuSelProdVOs) {

            // 소속구분 설정
            sideMenuSelProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            sideMenuSelProdVO.setStoreCd(sessionInfoVO.getStoreCd());

            sideMenuSelProdVO.setRegDt(currentDt);
            sideMenuSelProdVO.setRegId(sessionInfoVO.getUserId());
            sideMenuSelProdVO.setModDt(currentDt);
            sideMenuSelProdVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( sideMenuSelProdVO.getStatus() == GridDataFg.INSERT ) {
                result = sideMenuMapper.insertMenuProdList(sideMenuSelProdVO);
                if(result == 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));


                // 수정
            } else if ( sideMenuSelProdVO.getStatus() == GridDataFg.UPDATE ) {
                result = sideMenuMapper.updateMenuProdList(sideMenuSelProdVO);
                if(result == 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 삭제
            } else if ( sideMenuSelProdVO.getStatus() == GridDataFg.DELETE ) {
                result = sideMenuMapper.deleteMenuProdList(sideMenuSelProdVO);
                if(result == 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            }

        }

        return result;
    }
}
