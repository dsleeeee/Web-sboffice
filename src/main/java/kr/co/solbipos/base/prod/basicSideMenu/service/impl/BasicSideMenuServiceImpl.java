package kr.co.solbipos.base.prod.basicSideMenu.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelClassVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelGroupVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelProdVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @author 솔비포스 WEB개발팀 이다솜
 * @version 1.0
 * @Class Name : BasicSideMenuServiceImpl.java
 * @Description : 기초관리 > 상품관리 > (기준)사이드메뉴
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.07  이다솜      최초생성
 * @See
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @since 2024.08.07
 */
@Service("basicSideMenuService")
public class BasicSideMenuServiceImpl implements BasicSideMenuService {

    private final BasicSideMenuMapper basicSideMenuMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public BasicSideMenuServiceImpl(BasicSideMenuMapper basicSideMenuMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.basicSideMenuMapper = basicSideMenuMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuGrpList(BasicSideMenuSelGroupVO basicSideMenuSelGroupVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        basicSideMenuSelGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        basicSideMenuSelGroupVO.setUserId(sessionInfoVO.getUserId());

        return basicSideMenuMapper.getMenuGrpList(basicSideMenuSelGroupVO);
    }

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 저장 */
    @Override
    public int saveMenuGrpList(BasicSideMenuSelGroupVO[] basicSideMenuSelGroupVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult;
        String currentDt = currentDateTimeString();

        for (BasicSideMenuSelGroupVO basicSideMenuSelGroupVO : basicSideMenuSelGroupVOs) {

            // 소속구분 설정
            basicSideMenuSelGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            basicSideMenuSelGroupVO.setRegDt(currentDt);
            basicSideMenuSelGroupVO.setRegId(sessionInfoVO.getUserId());
            basicSideMenuSelGroupVO.setModDt(currentDt);
            basicSideMenuSelGroupVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( basicSideMenuSelGroupVO.getStatus() == GridDataFg.INSERT ) {
                // 그룹코드 생성
                basicSideMenuSelGroupVO.setSdselGrpCd(basicSideMenuMapper.getMenuGrpCode(basicSideMenuSelGroupVO));

                result += basicSideMenuMapper.insertMenuGrpList(basicSideMenuSelGroupVO);

            // 수정
            } else if ( basicSideMenuSelGroupVO.getStatus() == GridDataFg.UPDATE ) {
                result += basicSideMenuMapper.updateMenuGrpList(basicSideMenuSelGroupVO);

            // 삭제
            } else if ( basicSideMenuSelGroupVO.getStatus() == GridDataFg.DELETE ) {
                result += basicSideMenuMapper.deleteMenuGrpList(basicSideMenuSelGroupVO);

            }
        }

        if ( result == basicSideMenuSelGroupVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuClassList(BasicSideMenuSelClassVO basicSideMenuSelClassVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        basicSideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return basicSideMenuMapper.getMenuClassList(basicSideMenuSelClassVO);
    }

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 저장 */
    @Override
    public int saveMenuClassList(BasicSideMenuSelClassVO[] basicSideMenuSelClassVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult;
        String currentDt = currentDateTimeString();

        for ( BasicSideMenuSelClassVO basicSideMenuSelClassVO : basicSideMenuSelClassVOs) {

            basicSideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            basicSideMenuSelClassVO.setRegDt(currentDt);
            basicSideMenuSelClassVO.setRegId(sessionInfoVO.getUserId());
            basicSideMenuSelClassVO.setModDt(currentDt);
            basicSideMenuSelClassVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( basicSideMenuSelClassVO.getStatus() == GridDataFg.INSERT ) {
                // 분류코드 생성
                basicSideMenuSelClassVO.setSdselClassCd(basicSideMenuMapper.getMenuClassCode(basicSideMenuSelClassVO));

                result += basicSideMenuMapper.insertMenuClassList(basicSideMenuSelClassVO);

            // 수정
            } else if ( basicSideMenuSelClassVO.getStatus() == GridDataFg.UPDATE ) {
                result += basicSideMenuMapper.updateMenuClassList(basicSideMenuSelClassVO);

            // 삭제
            } else if ( basicSideMenuSelClassVO.getStatus() == GridDataFg.DELETE ) {
                result += basicSideMenuMapper.deleteMenuClassList(basicSideMenuSelClassVO);
            }
        }

        if ( result == basicSideMenuSelClassVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 추가팝업 상품리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        basicSideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        basicSideMenuSelProdVO.setUserId(sessionInfoVO.getUserId());

        if(basicSideMenuSelProdVO.getSideEnvstVal() != ""){
            basicSideMenuSelProdVO.setSideEnvstVal("");
        }

        // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
        if (basicSideMenuSelProdVO.getProdHqBrandCd() == "" || basicSideMenuSelProdVO.getProdHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            if (basicSideMenuSelProdVO.getUserProdBrands() != null && !"".equals(basicSideMenuSelProdVO.getUserProdBrands())) {
                String[] userBrandList = basicSideMenuSelProdVO.getUserProdBrands().split(",");
                if (userBrandList.length > 0) {
                    basicSideMenuSelProdVO.setUserProdBrandList(userBrandList);
                }
            }
        }

        return basicSideMenuMapper.getProdList(basicSideMenuSelProdVO);

    }

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        basicSideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        basicSideMenuSelProdVO.setUserId(sessionInfoVO.getUserId());

        return basicSideMenuMapper.getMenuProdList(basicSideMenuSelProdVO);
    }

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 저장 */
    @Override
    public int saveMenuProdList(BasicSideMenuSelProdVO[] basicSideMenuSelProdVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult;
        String currentDt = currentDateTimeString();

        for ( BasicSideMenuSelProdVO basicSideMenuSelProdVO : basicSideMenuSelProdVOs) {
            // 소속구분 설정
            basicSideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            basicSideMenuSelProdVO.setRegDt(currentDt);
            basicSideMenuSelProdVO.setRegId(sessionInfoVO.getUserId());
            basicSideMenuSelProdVO.setModDt(currentDt);
            basicSideMenuSelProdVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( basicSideMenuSelProdVO.getStatus() == GridDataFg.INSERT ) {
                result += basicSideMenuMapper.insertMenuProdList(basicSideMenuSelProdVO);
            // 수정
            } else if ( basicSideMenuSelProdVO.getStatus() == GridDataFg.UPDATE ) {
                result += basicSideMenuMapper.updateMenuProdList(basicSideMenuSelProdVO);
            // 삭제
            } else if ( basicSideMenuSelProdVO.getStatus() == GridDataFg.DELETE ) {
                result += basicSideMenuMapper.deleteMenuProdList(basicSideMenuSelProdVO);
            }
        }

        if ( result == basicSideMenuSelProdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

}
