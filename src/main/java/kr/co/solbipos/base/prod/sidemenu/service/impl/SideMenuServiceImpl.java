package kr.co.solbipos.base.prod.sidemenu.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.impl.ProdMapper;
import kr.co.solbipos.base.prod.artiseeProdMapping.service.ArtiseeProdMappingVO;
import kr.co.solbipos.base.prod.artiseeProdMapping.service.impl.ArtiseeProdMappingMapper;
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
    private final ProdMapper prodMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;
    private final ArtiseeProdMappingMapper artiseeProdMappingMapper; // 아티제상품코드맵핑

    /** Constructor Injection */
    @Autowired
    public SideMenuServiceImpl(SideMenuMapper sideMenuMapper, ProdMapper prodMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil, ArtiseeProdMappingMapper artiseeProdMappingMapper) {
        this.sideMenuMapper = sideMenuMapper;
        this.prodMapper = prodMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.artiseeProdMappingMapper = artiseeProdMappingMapper;
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
        String procResult;
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
                // 분류코드 생성
                sideMenuAttrClassVO.setSdattrClassCd(sideMenuMapper.getAttrClassCode(sideMenuAttrClassVO));

                result += sideMenuMapper.insertAttrClassList(sideMenuAttrClassVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.insertHqAttrClassListToStore(sideMenuAttrClassVO);
                }
                // 수정
            } else if ( sideMenuAttrClassVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateAttrClassList(sideMenuAttrClassVO);

                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.updateHqAttrClassListToStore(sideMenuAttrClassVO);
                }
                // 삭제
            } else if ( sideMenuAttrClassVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteAttrClassList(sideMenuAttrClassVO);

                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.deleteHqAttrClassListToStore(sideMenuAttrClassVO);
                }
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
        String procResult;
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
                // 속성코드 생성
                sideMenuAttrCdVO.setSdattrCd(sideMenuMapper.getAttrCode(sideMenuAttrCdVO));

                result += sideMenuMapper.insertAttrCdList(sideMenuAttrCdVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.insertHqAttrCdListToStore(sideMenuAttrCdVO);
                }
                // 수정
            } else if ( sideMenuAttrCdVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateAttrCdList(sideMenuAttrCdVO);

                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.updateHqAttrCdListToStore(sideMenuAttrCdVO);
                }
                // 삭제
            } else if ( sideMenuAttrCdVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteAttrCdList(sideMenuAttrCdVO);

                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.deleteHqAttrCdListToStore(sideMenuAttrCdVO);
                }
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
        sideMenuSelGroupVO.setUserId(sessionInfoVO.getUserId());

        return sideMenuMapper.getMenuGrpList(sideMenuSelGroupVO);
    }

    /** 사이드메뉴-선택메뉴탭-선택그룹 저장 */
    @Override
    public int saveMenuGrpList(SideMenuSelGroupVO[] sideMenuSelGroupVOs,
        SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult;
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
                // 그룹코드 생성
                sideMenuSelGroupVO.setSdselGrpCd(sideMenuMapper.getMenuGrpCode(sideMenuSelGroupVO));

                result += sideMenuMapper.insertMenuGrpList(sideMenuSelGroupVO);
                // TODO :: 그룹추가시 매장에 자동으로 내려줄것인가? : 20181231 노현수
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.insertHqMenuGrpListToStore(sideMenuSelGroupVO);
                }
            // 수정
            } else if ( sideMenuSelGroupVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateMenuGrpList(sideMenuSelGroupVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.updateHqMenuGrpListToStore(sideMenuSelGroupVO);
                }
            // 삭제
            } else if ( sideMenuSelGroupVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteMenuGrpList(sideMenuSelGroupVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.deleteHqMenuGrpListToStore(sideMenuSelGroupVO);
                }
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
        String procResult;
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
                // 분류코드 생성
                sideMenuSelClassVO.setSdselClassCd(sideMenuMapper.getMenuClassCode(sideMenuSelClassVO));

                result += sideMenuMapper.insertMenuClassList(sideMenuSelClassVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.insertHqMenuClassListToStore(sideMenuSelClassVO);
                }
            // 수정
            } else if ( sideMenuSelClassVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateMenuClassList(sideMenuSelClassVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.saveHqMenuClassListToStore(sideMenuSelClassVO);
                }
            // 삭제
            } else if ( sideMenuSelClassVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteMenuClassList(sideMenuSelClassVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.deleteHqMenuClassListToStore(sideMenuSelClassVO);
                }
            }

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                // 매핑스트링 TMP 테이블
                ArtiseeProdMappingVO artiseeProdMappingVO = new ArtiseeProdMappingVO();
                artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                artiseeProdMappingVO.setSessionId(sessionInfoVO.getSessionId());
                artiseeProdMappingVO.setUserId(sessionInfoVO.getUserId());

                // TMP테이블 삭제
                artiseeProdMappingMapper.deleteMappingTmp01(artiseeProdMappingVO);
                artiseeProdMappingMapper.deleteMappingTmp02(artiseeProdMappingVO);

                // 맵핑스트링 저장
                artiseeProdMappingMapper.insertMappingString(artiseeProdMappingVO);

                // ERP 맵핑상품코드 삭제
                SideMenuManageVO sideMenuManageVO = new SideMenuManageVO();
                sideMenuManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                sideMenuManageVO.setSessionId(sessionInfoVO.getSessionId());
                sideMenuManageVO.setUserId(sessionInfoVO.getUserId());
                int procCnt = sideMenuMapper.getErpProdMappingDelete(sideMenuManageVO);
            }
        }

        if ( result == sideMenuSelClassVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

//        // 선택분류 삭제 완료 후, 해당 선택그룹에 선택된 분류가 있는지 확인 후, 없으면 선택그룹 사용안함 처리.
//        SideMenuSelGroupVO sideMenuSelGroupVO = new SideMenuSelGroupVO();
//
//        sideMenuSelGroupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
//        sideMenuSelGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//        sideMenuSelGroupVO.setStoreCd(sessionInfoVO.getStoreCd());
//        sideMenuSelGroupVO.setSdselGrpCd(sideMenuSelClassVOs[0].getSdselGrpCd());
//
//        sideMenuSelGroupVO.setRegDt(currentDt);
//        sideMenuSelGroupVO.setRegId(sessionInfoVO.getUserId());
//        sideMenuSelGroupVO.setModDt(currentDt);
//        sideMenuSelGroupVO.setModId(sessionInfoVO.getUserId());
//
//        int isChk = sideMenuMapper.getSideMenuSelGrpClassCnt(sideMenuSelGroupVO);
//
//        if(isChk == 0) {
//            result = sideMenuMapper.deleteMenuGrpList(sideMenuSelGroupVO);
//            if(result == 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }
    }

    /** 사이드메뉴-선택메뉴 - 선택할 상품 목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(SideMenuSelProdVO sideMenuSelProdVO,
        SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        sideMenuSelProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        sideMenuSelProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        sideMenuSelProdVO.setUserId(sessionInfoVO.getUserId());

        if(sideMenuSelProdVO.getSideEnvstVal() != ""){
            // 매장상품제한구분 환경변수 값(환경변수 1100 사용)
            String sideEnvstVal;
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) { // 본사는 해당옵션의 제약X
                sideEnvstVal = "0";
            } else {
                sideEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"));
            }

            if(sideEnvstVal.equals("1")){
                sideMenuSelProdVO.setSideEnvstVal(sideEnvstVal);
            } else {
                sideMenuSelProdVO.setSideEnvstVal("");
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (sideMenuSelProdVO.getProdHqBrandCd() == "" || sideMenuSelProdVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (sideMenuSelProdVO.getUserProdBrands() != null && !"".equals(sideMenuSelProdVO.getUserProdBrands())) {
                    String[] userBrandList = sideMenuSelProdVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        sideMenuSelProdVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

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
        sideMenuSelProdVO.setUserId(sessionInfoVO.getUserId());

        return sideMenuMapper.getMenuProdList(sideMenuSelProdVO);
    }

    /** 사이드메뉴-선택메뉴탭-선택상품 저장 */
    @Override
    public int saveMenuProdList(SideMenuSelProdVO[] sideMenuSelProdVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult;
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
                result += sideMenuMapper.insertMenuProdList(sideMenuSelProdVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.insertHqMenuProdListToStore(sideMenuSelProdVO);
                }
            // 수정
            } else if ( sideMenuSelProdVO.getStatus() == GridDataFg.UPDATE ) {
                result += sideMenuMapper.updateMenuProdList(sideMenuSelProdVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.saveHqMenuProdListToStore(sideMenuSelProdVO);
                }
            // 삭제
            } else if ( sideMenuSelProdVO.getStatus() == GridDataFg.DELETE ) {
                result += sideMenuMapper.deleteMenuProdList(sideMenuSelProdVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = sideMenuMapper.deleteHqMenuProdListToStore(sideMenuSelProdVO);
                }
            }

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                // 매핑스트링 TMP 테이블
                ArtiseeProdMappingVO artiseeProdMappingVO = new ArtiseeProdMappingVO();
                artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                artiseeProdMappingVO.setSessionId(sessionInfoVO.getSessionId());
                artiseeProdMappingVO.setUserId(sessionInfoVO.getUserId());

                // TMP테이블 삭제
                artiseeProdMappingMapper.deleteMappingTmp01(artiseeProdMappingVO);
                artiseeProdMappingMapper.deleteMappingTmp02(artiseeProdMappingVO);

                // 맵핑스트링 저장
                artiseeProdMappingMapper.insertMappingString(artiseeProdMappingVO);

                // ERP 맵핑상품코드 삭제
                SideMenuManageVO sideMenuManageVO = new SideMenuManageVO();
                sideMenuManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                sideMenuManageVO.setSessionId(sessionInfoVO.getSessionId());
                sideMenuManageVO.setUserId(sessionInfoVO.getUserId());
                int procCnt = sideMenuMapper.getErpProdMappingDelete(sideMenuManageVO);
            }
        }

        if ( result == sideMenuSelProdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

//        // 선택상품 삭제 완료 후, 해당 선택분류에 선택된 상품 있는지 확인 후, 없으면 선택분류 사용안함 처리.
//        SideMenuSelClassVO sideMenuSelClassVO = new SideMenuSelClassVO();
//
//        sideMenuSelClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
//        sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//        sideMenuSelClassVO.setStoreCd(sessionInfoVO.getStoreCd());
//        sideMenuSelClassVO.setSdselGrpCd(sideMenuSelProdVOs[0].getSdselGrpCd());
//        sideMenuSelClassVO.setSdselClassCd(sideMenuSelProdVOs[0].getSdselClassCd());
//
//        sideMenuSelClassVO.setRegDt(currentDt);
//        sideMenuSelClassVO.setRegId(sessionInfoVO.getUserId());
//        sideMenuSelClassVO.setModDt(currentDt);
//        sideMenuSelClassVO.setModId(sessionInfoVO.getUserId());
//
//        int isChk = sideMenuMapper.getSideMenuSelClassProdCnt(sideMenuSelClassVO);
//
//        if(isChk == 0) {
//            result = sideMenuMapper.deleteMenuClassList(sideMenuSelClassVO);
//            if(result == 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }
    }

    /** 사이드메뉴-사이드메뉴관리탭 상품 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuManageProdList(SideMenuManageVO sideMenuManageVO, SessionInfoVO sessionInfoVO) {

        sideMenuManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            sideMenuManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return sideMenuMapper.getSideMenuManageProdList(sideMenuManageVO);
    }

    /** 사이드메뉴-사이드메뉴관리탭 상품정보일괄변경 저장(사이드메뉴여부, 속성, 선택메뉴) */
    @Override
    public int saveSideMenuManageProdBatch(SideMenuManageVO[] sideMenuManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuManageVO sideMenuManageVO : sideMenuManageVOs) {

            sideMenuManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            sideMenuManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                sideMenuManageVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            sideMenuManageVO.setModDt(currentDt);
            sideMenuManageVO.setModId(sessionInfoVO.getUserId());

            if(sideMenuManageVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = sideMenuMapper.saveSideMenuManageProdBatch(sideMenuManageVO);

                // 본사인 경우 매장에 수정정보 내려줌
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    // 상품정보 매장에 UPDATE
                    procCnt = sideMenuMapper.saveSideMenuManageProdBatchStoreUpdate(sideMenuManageVO);
                }

                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    // 사이드사용여부 Y 체크
                    if (sideMenuManageVO.getSideProdYn().equals("Y")) {
                        // 매핑스트링 TMP 테이블
                        ArtiseeProdMappingVO artiseeProdMappingVO = new ArtiseeProdMappingVO();
                        artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                        artiseeProdMappingVO.setSessionId(sessionInfoVO.getSessionId());
                        artiseeProdMappingVO.setUserId(sessionInfoVO.getUserId());

                        // TMP테이블 삭제
                        artiseeProdMappingMapper.deleteMappingTmp01(artiseeProdMappingVO);
                        artiseeProdMappingMapper.deleteMappingTmp02(artiseeProdMappingVO);

                        // 맵핑스트링 저장
                        artiseeProdMappingMapper.insertMappingString(artiseeProdMappingVO);

                        sideMenuManageVO.setSessionId(sessionInfoVO.getSessionId());
                        sideMenuManageVO.setUserId(sessionInfoVO.getUserId());

                        // ERP 맵핑상품코드 삭제
                        procCnt = sideMenuMapper.getErpProdMappingDelete(sideMenuManageVO);
                    }
                }
            }
        }

        return procCnt;
    }

    /** 사이드메뉴-사이드메뉴관리탭 속성 콤보박스 */
    @Override
    public List<DefaultMap<String>> getSideMenuAttrClassCombo(SideMenuManageVO sideMenuManageVO, SessionInfoVO sessionInfoVO) {

        sideMenuManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            sideMenuManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return sideMenuMapper.getSideMenuAttrClassCombo(sideMenuManageVO);
    }


    /** 사이드메뉴-사이드메뉴관리탭 선택메뉴 콤보박스 */
    @Override
    public List<DefaultMap<String>> getSideMenuSdselGrpCdCombo(SideMenuManageVO sideMenuManageVO, SessionInfoVO sessionInfoVO) {

        sideMenuManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            sideMenuManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return sideMenuMapper.getSideMenuSdselGrpCdCombo(sideMenuManageVO);
    }

    /** 사이드메뉴-선택메뉴 탭-선택분류복사 팝업 - 저장 */
    @Override
    public int getSdselClassCopySave(SideMenuSelClassVO[] sideMenuSelClassVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String procResult;
        String currentDt = currentDateTimeString();

        for(SideMenuSelClassVO sideMenuSelClassVO : sideMenuSelClassVOs) {
            sideMenuSelClassVO.setModDt(currentDt);
            sideMenuSelClassVO.setModId(sessionInfoVO.getUserId());
            sideMenuSelClassVO.setRegDt(currentDt);
            sideMenuSelClassVO.setRegId(sessionInfoVO.getUserId());

            sideMenuSelClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                sideMenuSelClassVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // 분류코드 생성
            sideMenuSelClassVO.setApplySdselClassCd(sideMenuMapper.getMenuClassCode(sideMenuSelClassVO));

            // 선택분류 생성시 표기순번 자동채번
            sideMenuSelClassVO.setApplyDispSeq(sideMenuMapper.getDispSeqCode(sideMenuSelClassVO));

            System.out.println("사이드 선택메뉴 복사 > 복사할 그룹코드 : " + sideMenuSelClassVO.getCopySdselGrpCd() + " ,  분류코드 : " + sideMenuSelClassVO.getCopySdselClassCd());
            System.out.println("사이드 선택메뉴 복사 > 적용할 그룹코드 : " + sideMenuSelClassVO.getApplySdselGrpCd() + " ,  분류코드 : " + sideMenuSelClassVO.getApplySdselClassCd() + " ,  표기순번 : " + sideMenuSelClassVO.getApplyDispSeq());

            // 선택분류 복사
            procCnt = sideMenuMapper.getSdselClassCopySaveInsertClass(sideMenuSelClassVO);

            // 본사에서 접속시
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                sideMenuSelClassVO.setSdselGrpCd(sideMenuSelClassVO.getApplySdselGrpCd());
                sideMenuSelClassVO.setSdselClassCd(sideMenuSelClassVO.getApplySdselClassCd());

                procResult = sideMenuMapper.insertHqMenuClassListToStore(sideMenuSelClassVO);
            }

            // 선택상품 복사
            procCnt = sideMenuMapper.getSdselClassCopySaveMergeProd(sideMenuSelClassVO);
            // 본사에서 접속시
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                procCnt = sideMenuMapper.getSdselClassCopySaveMergeProdStore(sideMenuSelClassVO);
            }
        }

        return procCnt;
    }

    /** 선택분류 적용매장등록 팝업 - 선택분류 조회 */
    @Override
    public List<DefaultMap<Object>> getSdselClassCodeComboList(SideMenuSelClassVO sideMenuSelClassVO, SessionInfoVO sessionInfoVO) {

        sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuMapper.getSdselClassCodeComboList(sideMenuSelClassVO);
    }

    /** 선택분류 적용매장등록 팝업 - 적용매장 조회 */
    @Override
    public List<DefaultMap<Object>> getSdselClassRegStoreList(SideMenuSelClassVO sideMenuSelClassVO, SessionInfoVO sessionInfoVO) {

        sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuMapper.getSdselClassRegStoreList(sideMenuSelClassVO);
    }

    /** 선택분류 적용매장등록 팝업 - 미적용매장 조회 */
    @Override
    public List<DefaultMap<Object>> getSdselClassNoRegStoreList(SideMenuSelClassVO sideMenuSelClassVO, SessionInfoVO sessionInfoVO) {

        sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuMapper.getSdselClassNoRegStoreList(sideMenuSelClassVO);
    }

    /** 선택분류 적용매장등록 팝업 - 저장 */
    @Override
    public int getSdselClassRegStoreSave(SideMenuSelClassVO[] sideMenuSelClassVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuSelClassVO sideMenuSelClassVO : sideMenuSelClassVOs) {

            sideMenuSelClassVO.setModDt(currentDt);
            sideMenuSelClassVO.setModId(sessionInfoVO.getUserId());

            sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if (sideMenuSelClassVO.getStatus() == GridDataFg.INSERT) {
                sideMenuSelClassVO.setRegDt(currentDt);
                sideMenuSelClassVO.setRegId(sessionInfoVO.getUserId());

                procCnt = sideMenuMapper.getSdselClassRegStoreSaveInsert(sideMenuSelClassVO);

            } else if (sideMenuSelClassVO.getStatus() == GridDataFg.DELETE) {
                procCnt = sideMenuMapper.getSdselClassRegStoreSaveDelete(sideMenuSelClassVO);
            }
        }

        return procCnt;
    }

    /** 선택상품 적용매장등록 팝업 - 선택상품 조회 */
    @Override
    public List<DefaultMap<Object>> getSdselProdCodeComboList(SideMenuSelProdVO sideMenuSelProdVO, SessionInfoVO sessionInfoVO) {

        sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuMapper.getSdselProdCodeComboList(sideMenuSelProdVO);
    }

    /** 선택상품 적용매장등록 팝업 - 적용매장 조회 */
    @Override
    public List<DefaultMap<Object>> getSdselProdRegStoreList(SideMenuSelProdVO sideMenuSelProdVO, SessionInfoVO sessionInfoVO) {

        sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuMapper.getSdselProdRegStoreList(sideMenuSelProdVO);
    }

    /** 선택상품 적용매장등록 팝업 - 미적용매장 조회 */
    @Override
    public List<DefaultMap<Object>> getSdselProdNoRegStoreList(SideMenuSelProdVO sideMenuSelProdVO, SessionInfoVO sessionInfoVO) {

        sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return sideMenuMapper.getSdselProdNoRegStoreList(sideMenuSelProdVO);
    }

    /** 선택상품 적용매장등록 팝업 - 저장 */
    @Override
    public int getSdselProdRegStoreSave(SideMenuSelProdVO[] sideMenuSelProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuSelProdVO sideMenuSelProdVO : sideMenuSelProdVOs) {

            sideMenuSelProdVO.setModDt(currentDt);
            sideMenuSelProdVO.setModId(sessionInfoVO.getUserId());

            sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if (sideMenuSelProdVO.getStatus() == GridDataFg.INSERT) {
                sideMenuSelProdVO.setRegDt(currentDt);
                sideMenuSelProdVO.setRegId(sessionInfoVO.getUserId());

                procCnt = sideMenuMapper.getSdselProdRegStoreSaveInsert(sideMenuSelProdVO);

            } else if (sideMenuSelProdVO.getStatus() == GridDataFg.DELETE) {
                procCnt = sideMenuMapper.getSdselProdRegStoreSaveDelete(sideMenuSelProdVO);
            }
        }

        return procCnt;
    }

    /** 선택분류 적용매장 전체 삭제 */
    @Override
    public int getSdselClassRegStoreDeleteAll(SideMenuSelClassVO[] sideMenuSelClassVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SideMenuSelClassVO sideMenuSelClassVO : sideMenuSelClassVOs) {

            sideMenuSelClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 적용매장구분이 변경된 경우만 삭제
            if(!sideMenuSelClassVO.getRegStoreFg().equals(CmmUtil.nvl(sideMenuSelClassVO.getOldRegStoreFg(), "0"))) {
                procCnt = sideMenuMapper.getSdselClassRegStoreDeleteAll(sideMenuSelClassVO);
            }
        }

        return procCnt;
    }

    /** 선택상품 적용매장 전체 삭제 */
    @Override
    public int getSdselProdRegStoreDeleteAll(SideMenuSelProdVO[] sideMenuSelProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SideMenuSelProdVO sideMenuSelProdVO : sideMenuSelProdVOs) {

            sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 적용매장구분이 변경된 경우만 삭제
            if(!sideMenuSelProdVO.getRegStoreFg().equals(CmmUtil.nvl(sideMenuSelProdVO.getOldRegStoreFg(), "0"))) {
                procCnt = sideMenuMapper.getSdselProdRegStoreDeleteAll(sideMenuSelProdVO);
            }
        }

        return procCnt;
    }

    /** 상품정보 저장 전 체크 - 선택한 선택메뉴코드가 세트('C')이면서, 나(현재 선택한 상품)를 가진 세트가 있는지 확인 */
    @Override
    public String getSideMenuChk(SideMenuManageVO[] sideMenuManageVOs, SessionInfoVO sessionInfoVO){

        String result = "";

        for(SideMenuManageVO sideMenuManageVO : sideMenuManageVOs) {

            ProdVO prodVO = new ProdVO();
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                prodVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            prodVO.setSdselGrpCd(sideMenuManageVO.getSdselGrpCd());
            prodVO.setProdCd(sideMenuManageVO.getProdCd());
            prodVO.setProdNm(sideMenuManageVO.getProdNm());

            if(prodMapper.getSideMenuChk(prodVO).equals("N")){
                result = prodVO.getProdNm() + "[" + prodVO.getProdCd() + "]";
                return result;
            }
        }

        return result;
    }

    /** 선택상품 저장 전, 선택그룹에 이미 등록된 상품인지 확인 */
    @Override
    public List<DefaultMap<Object>> chkMenuProdUse(SideMenuSelProdVO sideMenuSelProdVO, SessionInfoVO sessionInfoVO) {

        sideMenuSelProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sideMenuSelProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            sideMenuSelProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        sideMenuSelProdVO.setArrProdCd(sideMenuSelProdVO.getProdCd().split(","));
        return sideMenuMapper.chkMenuProdUse(sideMenuSelProdVO);
    }
}
