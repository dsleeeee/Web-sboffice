package kr.co.solbipos.base.prod.tableOrderKeyMap.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.tableOrderKeyMap.service.TableOrderKeyMapService;
import kr.co.solbipos.base.prod.tableOrderKeyMap.service.TableOrderKeyMapVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TableOrderKeyMapServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > 테이블오더키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.07.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("tableOrderKeyMapService")
@Transactional
public class TableOrderKeyMapServiceImpl implements TableOrderKeyMapService {
    private final TableOrderKeyMapMapper tableOrderKeyMapMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public TableOrderKeyMapServiceImpl(TableOrderKeyMapMapper tableOrderKeyMapMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) { this.tableOrderKeyMapMapper = tableOrderKeyMapMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 테이블오더키맵 매장적용 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTableOrderKeyMapStoreRegistList(TableOrderKeyMapVO tableOrderKeyMapVO, SessionInfoVO sessionInfoVO) {

        tableOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (tableOrderKeyMapVO.getStoreHqBrandCd() == "" || tableOrderKeyMapVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (tableOrderKeyMapVO.getUserBrands() != null && !"".equals(tableOrderKeyMapVO.getUserBrands())) {
                    String[] userBrandList = tableOrderKeyMapVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        tableOrderKeyMapVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return tableOrderKeyMapMapper.getTableOrderKeyMapStoreRegistList(tableOrderKeyMapVO);
    }

    /** 테이블오더 - 키오스크 카테고리(분류) 등록 */
    @Override
    public int saveKioskCategory(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 페이지 수 계산
            int indexNo = Integer.parseInt(kioskKeyMapVO.getIndexNo());
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 4) + 1)));

            if ( kioskKeyMapVO.getStatus() == GridDataFg.INSERT ) { // 생성

                // 분류코드 생성
                kioskKeyMapVO.setTuClsCd(tableOrderKeyMapMapper.getKioskCategoryCode(kioskKeyMapVO));

                result += tableOrderKeyMapMapper.insertKioskCategory(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정

                result += tableOrderKeyMapMapper.updateKioskCategory(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += tableOrderKeyMapMapper.deleteKioskCategory(kioskKeyMapVO);

                // 해당 카테고리(분류)에 해당하는 상품도 삭제
                tableOrderKeyMapMapper.deleteAllKioskKeyMap(kioskKeyMapVO);
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            tableOrderKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
        }

        if ( result >= 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 테이블오더 - 키오스크 키맵 수정 */
    @Override
    public int updateKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();
        String pageFg = "";

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 페이지 수 계산
            int indexNo = Integer.parseInt(kioskKeyMapVO.getIndexNo());
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            if ( kioskKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += tableOrderKeyMapMapper.updateKioskKeyMap(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += tableOrderKeyMapMapper.deleteKioskKeyMap(kioskKeyMapVO);
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            tableOrderKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
        }

        if ( result >= 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 테이블오더 - 키오스크 키맵 등록 */
    @Override
    public int saveKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();

        int procCnt = 0;
        String pageFg = "";

        for(KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setRegDt(dt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(dt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 키오스크 키 관련 코드 조회
            DefaultMap<String> keyValue = tableOrderKeyMapMapper.getKioskKeyMapCode(kioskKeyMapVO);

            kioskKeyMapVO.setTuKeyCd(keyValue.get("tuKeyCd"));
            kioskKeyMapVO.setIndexNo(String.valueOf(keyValue.get("indexNo")));

            // 페이지 수 계산
            int indexNo = Integer.parseInt(String.valueOf(keyValue.get("indexNo")));
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            int result = tableOrderKeyMapMapper.saveKioskKeyMap(kioskKeyMapVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            tableOrderKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
        }

        return procCnt;
    }

    /** 테이블오더 - 키맵매장적용 */
    @Override
    public int saveKioskKeyMapStore(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            sessionInfoVO.setStoreCd(kioskKeyMapVO.getStoreCd());

            // KIOSK-매장수정여부(0:미사용, 1:사용, 2:사용(특정카테고리))
            String envstVal1249 = CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1249"), "0");

            // 매장적용할 키맵그룹 코드를 담을 list 형태의 변수
            List<DefaultMap<Object>> tuClsTypeEnvList;

            // 선택한 키맵그룹을 list 변수에 담기
            List<DefaultMap<Object>> keyMapList = new ArrayList<DefaultMap<Object>>();
            DefaultMap<Object> m = new DefaultMap<>();
            System.out.println(kioskKeyMapVO.getTuClsType() + "키맵그룹 확인");
            m.put("env40684069", kioskKeyMapVO.getTuClsType());
            keyMapList.add(m);

            tuClsTypeEnvList = keyMapList;

            for (int i = 0; i < tuClsTypeEnvList.size(); i++) {

                // 키맵그룹 코드 셋팅
                kioskKeyMapVO.setTuClsType(tuClsTypeEnvList.get(i).getStr("env40684069"));

                // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                result = tableOrderKeyMapMapper.mergeKioskCategoryStoreReg(kioskKeyMapVO);
                if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 1249 0/1이면 전체 삭제
                // 1249 2이면 229 Y 제외 삭제
                if(envstVal1249.equals("0") || envstVal1249.equals("1")) {
                    // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                    result = tableOrderKeyMapMapper.mergeKioskKeyMapStoreReg(kioskKeyMapVO);
                    if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                } else  if(envstVal1249.equals("2")) {

                    // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                    result = tableOrderKeyMapMapper.mergeKioskKeyMapStoreReg2(kioskKeyMapVO);
                    if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));


                }

                // 기존 데이터 중, 불필요한 매장 카테고리(분류) 정보 삭제
                tableOrderKeyMapMapper.deleteNotUseKioskCategoryToStore(kioskKeyMapVO);
                if(envstVal1249.equals("0") || envstVal1249.equals("1")) {
                    // 기존 데이터 중, 불필요한 매장 맵핑상품 정보 삭제
                    tableOrderKeyMapMapper.deleteNotUseKioskKeyMapToStore(kioskKeyMapVO);
                } else  if(envstVal1249.equals("2")) {
                    // 기존 데이터 중, 불필요한 매장 맵핑상품 정보 삭제
                    tableOrderKeyMapMapper.deleteNotUseKioskKeyMapToStore2(kioskKeyMapVO);

                }
            }

            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            kioskKeyMapVO.setTuClsType("");
            tableOrderKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
        }

        return result;
    }
}