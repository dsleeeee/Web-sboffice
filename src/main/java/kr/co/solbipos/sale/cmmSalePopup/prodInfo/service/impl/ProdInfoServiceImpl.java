package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoService;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("prodInfoService")
public class ProdInfoServiceImpl implements ProdInfoService {
    private final ProdInfoMapper prodInfoMapper;
    private final PopupMapper popupMapper;

    public ProdInfoServiceImpl(ProdInfoMapper prodInfoMapper, PopupMapper popupMapper) {
        this.prodInfoMapper = prodInfoMapper;
        this.popupMapper = popupMapper;
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
//    @Override
//    public List<DefaultMap<Object>> getProdSaleDtlList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {
//
////        System.out.println("test1111");
//        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//        prodInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
//        prodInfoVO.setLevel("Level" + prodInfoVO.getLevel());
//
//        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
//            // 매장 array 값 세팅
//            String[] storeCds = prodInfoVO.getStoreCds().split(",");
//            prodInfoVO.setStoreCdList(storeCds);
//        }
//        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
//            prodInfoVO.setStoreCds(sessionInfoVO.getStoreCd());
//            String[] storeCds = prodInfoVO.getStoreCds().split(",");
//            prodInfoVO.setStoreCdList(storeCds);
//        }
//
//        // 기간별매출 > 일자별 탭 > 상품분류별 탭
//        // 기간별매출 > 월별 탭 > 상품분류별 탭
//        if(("dayProdClass").equals(prodInfoVO.getGubun()) || ("monthProdClass").equals(prodInfoVO.getGubun())) {
//            if(prodInfoVO.getStrProdClassCd() != null && prodInfoVO.getStrProdClassCd().length() > 0) {
//                // 레벨에 따른 분류값 가져와서 배열변수에 넣음.
//                prodInfoVO.setArrProdClassCd(prodInfoVO.getStrProdClassCd().split(","));
//            }
//        }
//
//        return prodInfoMapper.getProdSaleDtlList(prodInfoVO);
//    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDtlDayList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {

        prodInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            prodInfoVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodInfoVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodInfoVO.getStoreCds(), 3900));
            prodInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodInfoMapper.getProdSaleDtlDayList(prodInfoVO);
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDtlMonthList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {

        prodInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodInfoVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodInfoVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodInfoVO.getStoreCds(), 3900));
            prodInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodInfoMapper.getProdSaleDtlMonthList(prodInfoVO);
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDtlDayProdClassList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {

        prodInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoVO.setEmpNo(sessionInfoVO.getEmpNo());
        prodInfoVO.setLevel("Level" + prodInfoVO.getLevel());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodInfoVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodInfoVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodInfoVO.getStoreCds(), 3900));
            prodInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if(prodInfoVO.getStrProdClassCd() != null && prodInfoVO.getStrProdClassCd().length() > 0) {
            // 레벨에 따른 분류값 가져와서 배열변수에 넣음.
            prodInfoVO.setArrProdClassCd(prodInfoVO.getStrProdClassCd().split(","));
        }

        return prodInfoMapper.getProdSaleDtlDayProdClassList(prodInfoVO);
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDtlMonthProdClassList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {

        prodInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoVO.setEmpNo(sessionInfoVO.getEmpNo());
        prodInfoVO.setLevel("Level" + prodInfoVO.getLevel());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodInfoVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodInfoVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodInfoVO.getStoreCds(), 3900));
            prodInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if(prodInfoVO.getStrProdClassCd() != null && prodInfoVO.getStrProdClassCd().length() > 0) {
            // 레벨에 따른 분류값 가져와서 배열변수에 넣음.
            prodInfoVO.setArrProdClassCd(prodInfoVO.getStrProdClassCd().split(","));
        }

        return prodInfoMapper.getProdSaleDtlMonthProdClassList(prodInfoVO);
    }

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDtlDayOfWeekList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO) {
        prodInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            prodInfoVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prodInfoVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodInfoVO.getStoreCds(), 3900));
            prodInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodInfoMapper.getProdSaleDtlDayOfWeekList(prodInfoVO);
    }
}