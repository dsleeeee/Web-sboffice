package kr.co.solbipos.base.prod.kioskKeyMap.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.kioskOption.service.KioskOptionVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuSelProdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static org.apache.commons.lang3.time.DateUtils.parseDate;

/**
 * @Class Name : KioskKeyMapServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 키오스크키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("kioskKeyMapService")
public class KioskKeyMapServiceImpl implements KioskKeyMapService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KioskKeyMapMapper kioskKeyMapMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public KioskKeyMapServiceImpl(KioskKeyMapMapper kioskKeyMapMapper,  MessageService messageService, CmmEnvUtil cmmEnvUtil) {

        this.kioskKeyMapMapper = kioskKeyMapMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 키오스크용 포스 조회 */
    @Override
    public List<DefaultMap<String>> getKioskPosList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());

        return kioskKeyMapMapper.getKioskPosList(kioskKeyMapVO);
    }

    /** 키오스크 카테고리(분류) 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskCategory(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());

        return kioskKeyMapMapper.getKioskCategory(kioskKeyMapVO);
    }

    /** 키오스크 카테고리(분류) 저장 */
    @Override
    public int saveKioskCategory(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
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
                kioskKeyMapVO.setTuClsCd(kioskKeyMapMapper.getKioskCategoryCode(kioskKeyMapVO));

                result += kioskKeyMapMapper.insertKioskCategory(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += kioskKeyMapMapper.updateKioskCategory(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += kioskKeyMapMapper.deleteKioskCategory(kioskKeyMapVO);

                // 해당 카테고리(분류)에 해당하는 상품도 삭제
                kioskKeyMapMapper.deleteAllKioskKeyMap(kioskKeyMapVO);
            }
        }

        if ( result == kioskKeyMapVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 키오스크 키맵 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskKeyMap(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());

        return kioskKeyMapMapper.getKioskKeyMap(kioskKeyMapVO);
    }

    /** 키오스크 키맵 수정 */
    @Override
    public int updateKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 페이지 수 계산
            int indexNo = Integer.parseInt(kioskKeyMapVO.getIndexNo());
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            if ( kioskKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += kioskKeyMapMapper.updateKioskKeyMap(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += kioskKeyMapMapper.deleteKioskKeyMap(kioskKeyMapVO);
            }
        }

        if ( result == kioskKeyMapVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /**  키오스크 상품 조회 */
    @Override
    public List<DefaultMap<String>> getKioskProdList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        kioskKeyMapVO.setOrgnFg(orgnFg);
        kioskKeyMapVO.setHqOfficeCd(hqOfficeCd);
        kioskKeyMapVO.setStoreCd(storeCd);

        return kioskKeyMapMapper.getKioskProdList(kioskKeyMapVO);
    }

    /** 키오스크 키맵 등록 */
    @Override
    public int saveKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int procCnt = 0;

        for(KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setRegDt(dt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(dt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 키오스크 키 관련 코드 조회
            DefaultMap<String> keyValue = kioskKeyMapMapper.getKioskKeyMapCode(kioskKeyMapVO);

            kioskKeyMapVO.setTuKeyCd(keyValue.get("tuKeyCd"));
            kioskKeyMapVO.setIndexNo(String.valueOf(keyValue.get("indexNo")));

            // 페이지 수 계산
            int indexNo = Integer.parseInt(String.valueOf(keyValue.get("indexNo")));
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            int result = kioskKeyMapMapper.saveKioskKeyMap(kioskKeyMapVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return procCnt;

    }
}
