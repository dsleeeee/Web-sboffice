package kr.co.solbipos.store.storeMoms.lsmStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.common.enums.InFg;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreService;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : LsmStoreServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > LSM사용매장조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.26  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.04.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("lsmStoreService")
public class LsmStoreServiceImpl implements LsmStoreService {
    private final LsmStoreMapper lsmStoreMapper;
    private final MessageService messageService;
    private final PopupMapper popupMapper;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public LsmStoreServiceImpl(LsmStoreMapper lsmStoreMapper, MessageService messageService, PopupMapper popupMapper, CmmEnvUtil cmmEnvUtil) {
        this.lsmStoreMapper = lsmStoreMapper;
        this.messageService = messageService;
        this.popupMapper = popupMapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 터치키 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getLsmStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(lsmStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(lsmStoreVO.getStoreCds(), 3900));
            lsmStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return lsmStoreMapper.getLsmStoreList(lsmStoreVO);
    }

    /** 터치키 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getLsmStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(lsmStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(lsmStoreVO.getStoreCds(), 3900));
            lsmStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return lsmStoreMapper.getLsmStoreExcelList(lsmStoreVO);
    }

    /** 키오스크 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getLsmKioskStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(lsmStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(lsmStoreVO.getStoreCds(), 3900));
            lsmStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return lsmStoreMapper.getLsmKioskStoreList(lsmStoreVO);
    }

    /** 키오스크 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getLsmKioskStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(lsmStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(lsmStoreVO.getStoreCds(), 3900));
            lsmStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return lsmStoreMapper.getLsmKioskStoreExcelList(lsmStoreVO);
    }

    /** 키오스크 탭 엑셀 업로드 - 데이터 임시 저장 */
    @Override
    public int getKioskKeyTempInsert(LsmStoreVO[] lsmStoreVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();
        int i = 1;

        for(LsmStoreVO lsmStoreVO : lsmStoreVOs) {

            lsmStoreVO.setRegDt(currentDt);
            lsmStoreVO.setRegId(sessionInfoVO.getUserId());
            lsmStoreVO.setModDt(currentDt);
            lsmStoreVO.setModId(sessionInfoVO.getUserId());
            lsmStoreVO.setClsFg("K"); // K: KIOSK
            lsmStoreVO.setSessionId(sessionInfoVO.getUserId());

            //임시 테이블 삭제
            if(lsmStoreVO.getProgressCnt() == 0) {
                result = lsmStoreMapper.getKioskKeyTempDeleteAll(lsmStoreVO);
            }
            // 키오스크 키 관련 코드 조회
            DefaultMap<String> keyValue = lsmStoreMapper.getKioskKeyMapCode(lsmStoreVO);
            lsmStoreVO.setTuKeyCd(keyValue.get("tuKeyCd"));
            lsmStoreVO.setIndexNo(String.valueOf(keyValue.get("indexNo")));

            // 페이지 수 계산
            int indexNo = Integer.parseInt(String.valueOf(keyValue.get("indexNo")));
            lsmStoreVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));
//

            // 데이터 임시 저장
            result = lsmStoreMapper.getKioskKeyTempInsertAll(lsmStoreVO);

            i++;
        }

        return result;
    }

    /** 키오스크 탭 엑셀 업로드 - 키오스크키맵 삭제 */
    @Override
    public int getDeleteKioskKey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getDeleteKioskKey(lsmStoreVO);
    }

    /** 키오스크 탭 엑셀 업로드 - 데이터 중 LSM '사용' 데이터 수 조회 */
    @Override
    public int getKioskKeyCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getKioskKeyCnt(lsmStoreVO);
    }

    /** 키오스크 탭 엑셀 업로드 - 저장 */
    @Override
    public int getInsertKioskKey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setRegDt(currentDt);
        lsmStoreVO.setRegId(sessionInfoVO.getUserId());
        lsmStoreVO.setModDt(currentDt);
        lsmStoreVO.setModId(sessionInfoVO.getUserId());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());

        return lsmStoreMapper.getInsertKioskKey(lsmStoreVO);
    }

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장 */
    @Override
    public int getTukeyTempInsert(LsmStoreVO[] lsmStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();
        int i = 1;

        for(LsmStoreVO lsmStoreVO : lsmStoreVOs) {

            lsmStoreVO.setRegDt(currentDt);
            lsmStoreVO.setRegId(sessionInfoVO.getUserId());
            lsmStoreVO.setModDt(currentDt);
            lsmStoreVO.setModId(sessionInfoVO.getUserId());
            lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
            lsmStoreVO.setInFg(InFg.STORE);
            lsmStoreVO.setStyleCd("01");

            //임시 테이블 삭제
            if(lsmStoreVO.getProgressCnt() == 0) {
                result = lsmStoreMapper.getTukeyTempDeleteAll(lsmStoreVO);
            }
            // 데이터 임시 저장
            lsmStoreMapper.getTukeyTempInsert01(lsmStoreVO);  // 매장 판매터치키 등록 (01: 셀 사이즈)
            lsmStoreMapper.getTukeyTempInsert02(lsmStoreVO);  // 매장 판매터치키 등록 (02: 상품명)
            lsmStoreMapper.getTukeyTempInsert03(lsmStoreVO);  // 매장 판매터치키 등록 (03: 가격)

            i++;
        }

        return result;
    }

    /** 터치키 탭 엑셀 업로드 - 터치키 삭제 */
    @Override
    public int getDeleteTukey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getDeleteTukey(lsmStoreVO);
    }

    /** 터치키 탭 엑셀 업로드 - 데이터 중 LSM '사용' 데이터 수 조회 */
    @Override
    public int getTukeyCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getTukeyCnt(lsmStoreVO);
    }

    /** 터치키 탭 엑셀 업로드 - 저장 */
    @Override
    public int getInsertTukey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());

        String currentDt = currentDateTimeString();

        lsmStoreVO.setRegDt(currentDt);
        lsmStoreVO.setRegId(sessionInfoVO.getUserId());
        lsmStoreVO.setModDt(currentDt);
        lsmStoreVO.setModId(sessionInfoVO.getUserId());

        // 매장코드 조회
//        List<DefaultMap<String>> list = lsmStoreMapper.getSrchStoreCd(lsmStoreVO);

//        for(int i = 0; i < list.size(); i++) {
//
//            String storeCd = list.get(i).get("storeCd");
//            sessionInfoVO.setStoreCd(storeCd);
//            lsmStoreVO.setStoreCd(storeCd);
//            System.out.println("매장코드" + storeCd);
//            lsmStoreVO.setEnvstVal(CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"), "0"));
//            System.out.println(lsmStoreVO.getEnvstVal() + "환경변수");
//
//            lsmStoreMapper.getTukeyInsert01(lsmStoreVO);  // 매장 판매터치키 등록 (01: 셀 사이즈)
//            lsmStoreMapper.getTukeyInsert02(lsmStoreVO);  // 매장 판매터치키 등록 (02: 상품명)
//            lsmStoreMapper.getTukeyInsert03(lsmStoreVO);  // 매장 판매터치키 등록 (03: 가격)
//        }

        lsmStoreMapper.getTukeyInsert01(lsmStoreVO);  // 매장 판매터치키 등록 (01: 셀 사이즈)
        lsmStoreMapper.getTukeyInsert02(lsmStoreVO);  // 매장 판매터치키 등록 (02: 상품명)
        lsmStoreMapper.getTukeyInsert03(lsmStoreVO);  // 매장 판매터치키 등록 (03: 가격)

        return result;
    }

    /** 키오스크 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인 */
    @Override
    public String getKioskChkUseYn(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getKioskChkUseYn(lsmStoreVO);
    }

    /** 키오스크 탭 엑셀 업로드 - 카테고리별 상품수 확인 */
    @Override
    public String getKioskChkProdCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getKioskChkProdCnt(lsmStoreVO);
    }

    /** 터치키 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인 */
    @Override
    public String getTukeyChkUseYn(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getTukeyChkUseYn(lsmStoreVO);
    }

    /** 터치키 탭 엑셀 업로드 - 분류별 상품수 확인 */
    @Override
    public String getTukeyChkProdCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO) {
        lsmStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        lsmStoreVO.setSessionId(sessionInfoVO.getUserId());
        return lsmStoreMapper.getTukeyChkProdCnt(lsmStoreVO);
    }


}