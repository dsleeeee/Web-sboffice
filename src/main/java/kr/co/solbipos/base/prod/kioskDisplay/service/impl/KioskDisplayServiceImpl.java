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

import java.util.ArrayList;
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
            kioskDisplayVO.setStoreCds(sessionInfoVO.getStoreCd());
        }
        kioskDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskDisplayVO.setUserId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(kioskDisplayVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(kioskDisplayVO.getStoreCds(), 3900));
            kioskDisplayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (kioskDisplayVO.getProdCds() != null && !"".equals(kioskDisplayVO.getProdCds())) {
            String[] prodCdList = kioskDisplayVO.getProdCds().split(",");
            kioskDisplayVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (kioskDisplayVO.getStoreHqBrandCd() == "" || kioskDisplayVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskDisplayVO.getUserBrands() != null && !"".equals(kioskDisplayVO.getUserBrands())) {
                    String[] userBrandList = kioskDisplayVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskDisplayVO.setUserBrandList(userBrandList);
                    }
                }
            }
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (kioskDisplayVO.getProdHqBrandCd() == "" || kioskDisplayVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskDisplayVO.getProdBrands() != null && !"".equals(kioskDisplayVO.getProdBrands())) {
                    String[] prodBrandList = kioskDisplayVO.getProdBrands().split(",");
                    if (prodBrandList.length > 0) {
                        kioskDisplayVO.setProdBrandList(prodBrandList);
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
    public List<DefaultMap<String>> chkCd(KioskDisplayVO[] kioskDisplayVOs, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

        // 1000개씩 나눠 체크하기 위해 loop 계산
        int loop = (int) Math.ceil((double) kioskDisplayVOs.length / 1000);

        // 매장코드, 상품코드 체크 변수
        String strCd = "";

        // 시작과 끝 배열 계산 변수
        int start = 0;
        int end = 0;

        for(int i = 0; i < loop; i++){

            strCd = "";

            // 1000개씩 나눠 체크하기 위해, 시작과 끝 배열 계산
            start = i * 1000;
            end = (i * 1000) + 1000;

            // 마지막 loop의 끝 배열은 데이터 전체 길이로 셋팅
            if(i == (loop - 1)){
                end = kioskDisplayVOs.length;
            }

            // 매장코드, 상품코드 유효여부 체크를 위한 데이터 만들기
            for(int j = start; j < end; j++){
                strCd += (strCd.equals("") ? "" : ",") + kioskDisplayVOs[j].getStoreCd() + "_" + kioskDisplayVOs[j].getProdCd();
            }

            KioskDisplayVO kioskDisplayVO2 = new KioskDisplayVO();
            kioskDisplayVO2.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskDisplayVO2.setProdCdCol(strCd);

            if(!"".equals(kioskDisplayVO2.getProdCdCol())){

                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(kioskDisplayVO2.getProdCdCol(), 3900));

                for(int k = 0; k < storeVO.getArrSplitStoreCd().length; k++){
                    System.out.println("splitData_" + k + " : " + storeVO.getArrSplitStoreCd()[k]);
                }

                String storeCdQuery = popupMapper.getSearchMultiStoreRtn(storeVO);

                if(storeCdQuery.indexOf("SELECT") != -1){
                    kioskDisplayVO2.setStoreCdQuery(storeCdQuery);
                }else{
                    storeCdQuery = storeCdQuery.replaceAll("','", "|");
                    String sQuery1 = "SELECT REGEXP_SUBSTR(A.VALUE_01, '[^|]+', 1, LEVEL) AS VALUE_01" + "\n";
                    sQuery1 += "FROM (" + "\n";
                    sQuery1 += "SELECT " + storeCdQuery + " AS VALUE_01 FROM DUAL" + "\n";
                    sQuery1 += ") A" + "\n";
                    sQuery1 += "CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(A.VALUE_01, '[^|]+','')) + 1";

                    kioskDisplayVO2.setStoreCdQuery(sQuery1);
                }
            }else{
                kioskDisplayVO2.setStoreCdQuery("SELECT '' AS VALUE_01 FROM DUAL"); // 쿼리 오류 방지를 위해 빈쿼리 셋팅
            }

            list.addAll(kioskDisplayMapper.chkCd(kioskDisplayVO2));
        }

        return list;
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

    //매장별 상품브랜드 조회
    @Override
    public List<DefaultMap<Object>> getUserBrandComboListAll(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            kioskDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskDisplayVO.setOrgnFg("H");
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            kioskDisplayVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskDisplayVO.setOrgnFg("S");
        }

        return kioskDisplayMapper.getUserBrandComboListAll(kioskDisplayVO);
    }
}