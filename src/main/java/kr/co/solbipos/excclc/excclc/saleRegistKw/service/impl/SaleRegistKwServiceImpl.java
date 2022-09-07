package kr.co.solbipos.excclc.excclc.saleRegistKw.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.saleRegistKw.service.SaleRegistKwVO;
import kr.co.solbipos.excclc.excclc.saleRegistKw.service.SaleRegistKwService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SaleRegistKwServiceImpl.java
 * @Description : 광운대 > 후방매출등록 > 후방매출등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.31  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.08.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("saleRegistKwService")
@Transactional
public class SaleRegistKwServiceImpl implements SaleRegistKwService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistKwMapper saleRegistKwMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleRegistKwServiceImpl(SaleRegistKwMapper saleRegistKwMapper, MessageService messageService) {
        this.saleRegistKwMapper = saleRegistKwMapper;
        this.messageService = messageService;
    }

    /** 매출수기등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSaleRegistKwList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            saleRegistKwVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return saleRegistKwMapper.getSaleRegistKwList(saleRegistKwVO);
    }

    /** 매출수기등록 상품 조회 */
    @Override
    public List<DefaultMap<String>> getSelectProdList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwMapper.getSelectProdList(saleRegistKwVO);
    }

    /** 매출처 팝업 조회 */
    @Override
    public List<DefaultMap<String>> getMembrKwList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        saleRegistKwVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleRegistKwVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleRegistKwVO.setStoreCd(sessionInfoVO.getStoreCd());
        return saleRegistKwMapper.getMembrKwList(saleRegistKwVO);
    }

    /** 매출수기등록 저장 */
    @Override
    public int getNewRegistKwList(SaleRegistKwVO[] saleRegistKwVOs, SessionInfoVO sessionInfoVO) {

        String billNo = "";
        long billDtlNo = 1;
        long taxSaleAmt = 0;
        long noTaxSaleAmt = 0;
        String currentDt = currentDateTimeString();

        long totSaleAmt = 0;
        long totDcAmt = 0;
        long realSaleAmt = 0;
        long vatAmt = 0;
        String dcCd = "01";

        // hdr
        for(SaleRegistKwVO saleRegistKwVO : saleRegistKwVOs){

            saleRegistKwVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistKwVO.setRegDt(currentDt);
            saleRegistKwVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKwVO.setModDt(currentDt);
            saleRegistKwVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

            if(billNo == "" && (saleRegistKwVO.getBillNo() == null || saleRegistKwVO.getBillNo().equals(""))){
                // 영수번호 채번
                billNo = saleRegistKwMapper.getBillNo(saleRegistKwVO);
            }

            if(saleRegistKwVO.getBillNo() == null || saleRegistKwVO.getBillNo().equals("")){
                saleRegistKwVO.setBillNo(billNo);
            }

            if(saleRegistKwVO.getCashPer() == 0.0 && saleRegistKwVO.getCardPer() == 0.0){
                saleRegistKwVO.setCashPer((float)saleRegistKwVO.getCashAmt()/(saleRegistKwVO.getCashAmt() + saleRegistKwVO.getCardAmt()));
                saleRegistKwVO.setCardPer((float)saleRegistKwVO.getCardAmt()/(saleRegistKwVO.getCashAmt() + saleRegistKwVO.getCardAmt()));
            }

            if(saleRegistKwVO.getSaleFg().equals("1")){
                saleRegistKwVO.setSaleYn("Y");
            } else if(saleRegistKwVO.getSaleFg().equals("-1")){
                saleRegistKwVO.setSaleYn("N");
            }

            if(saleRegistKwVO.getSaleAmt() > 0){
                if(saleRegistKwVO.getProdVatFg().equals("1")){
                    taxSaleAmt += saleRegistKwVO.getRealSaleAmt();
                } else {
                    noTaxSaleAmt += saleRegistKwVO.getRealSaleAmt();
                }
            }

            if(saleRegistKwVO.getProdSaleFg().equals("0")){
                dcCd = "05";
            }

            totSaleAmt += saleRegistKwVO.getSaleAmt();
            totDcAmt += saleRegistKwVO.getDcAmt();
            realSaleAmt += saleRegistKwVO.getRealSaleAmt();
            vatAmt += saleRegistKwVO.getVatAmt();

            if(saleRegistKwVOs.length == billDtlNo){

                saleRegistKwVO.setTotSaleAmt(totSaleAmt);
                saleRegistKwVO.setTotDcAmt(totDcAmt);
                saleRegistKwVO.setTotRealSaleAmt(realSaleAmt);
                saleRegistKwVO.setTotVatAmt(vatAmt);

                saleRegistKwVO.setTaxSaleAmt(taxSaleAmt);
                saleRegistKwVO.setNoTaxSaleAmt(noTaxSaleAmt);
                saleRegistKwVO.setRecvPayAmt(saleRegistKwVO.getCashAmt() + saleRegistKwVO.getCardAmt());

                // TB_SL_SALE_HDR
                saleRegistKwMapper.getSaleHdr(saleRegistKwVO);
                saleRegistKwMapper.getSaleHdrInfo(saleRegistKwVO);

                // TB_SL_SALE_HDR_PAY
                if(saleRegistKwVO.getCashAmt() > 0){
                    saleRegistKwVO.setPayCd("02");
                    saleRegistKwVO.setPayAmt(saleRegistKwVO.getCashAmt());
                    saleRegistKwMapper.getSaleHdrPay(saleRegistKwVO);
                }
                if(saleRegistKwVO.getCardAmt() > 0){
                    saleRegistKwVO.setPayCd("01");
                    saleRegistKwVO.setPayAmt(saleRegistKwVO.getCardAmt());
                    saleRegistKwMapper.getSaleHdrPay(saleRegistKwVO);
                }

                // TB_SL_SALE_HDR_DC
                if(totDcAmt > 0){
                    saleRegistKwVO.setDcCd(dcCd);
                    saleRegistKwMapper.getSaleHdrDc(saleRegistKwVO);
                }

                // TB_SL_SALE_PAY_CASH
                if(saleRegistKwVO.getCashAmt() > 0){
                    saleRegistKwMapper.getSalePayCash(saleRegistKwVO);
                }

                // TB_SL_SALE_PAY_CARD
                if(saleRegistKwVO.getCardAmt() > 0){
                    saleRegistKwMapper.getSalePayCard(saleRegistKwVO);
                }
            }

            billDtlNo++;
        }


        billDtlNo = 1;
        // dtl
        for(SaleRegistKwVO saleRegistKwVO : saleRegistKwVOs){

            saleRegistKwVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistKwVO.setRegDt(currentDt);
            saleRegistKwVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKwVO.setModDt(currentDt);
            saleRegistKwVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKwVO.setBillDtlNo(String.valueOf(billDtlNo));


            if(saleRegistKwVO.getBillNo() == null || saleRegistKwVO.getBillNo().equals("")){
                saleRegistKwVO.setBillNo(billNo);
            }

            if(saleRegistKwVO.getCashPer() == 0.0 && saleRegistKwVO.getCardPer() == 0.0){
                saleRegistKwVO.setCashPer((float)saleRegistKwVO.getCashAmt()/(saleRegistKwVO.getCashAmt() + saleRegistKwVO.getCardAmt()));
                saleRegistKwVO.setCardPer((float)saleRegistKwVO.getCardAmt()/(saleRegistKwVO.getCashAmt() + saleRegistKwVO.getCardAmt()));
            }

            if(saleRegistKwVO.getSaleFg().equals("1")){
                saleRegistKwVO.setSaleYn("Y");
            } else if(saleRegistKwVO.getSaleFg().equals("-1")){
                saleRegistKwVO.setSaleYn("N");
            }

            // TB_SL_SALE_DTL
            saleRegistKwMapper.getSaleDtl(saleRegistKwVO);
            saleRegistKwMapper.getSaleDtlInfo(saleRegistKwVO);

            if(saleRegistKwVO.getRealSaleAmt() > 0){

                //  TB_SL_SALE_DTL_PAY
                if(saleRegistKwVO.getCashAmt() > 0){
                    saleRegistKwVO.setPayCd("02");
                    saleRegistKwMapper.getSaleDtlPay(saleRegistKwVO);
                }
                if(saleRegistKwVO.getCardAmt() > 0){
                    saleRegistKwVO.setPayCd("01");
                    saleRegistKwMapper.getSaleDtlPay(saleRegistKwVO);
                }
            }

            if(saleRegistKwVO.getDcAmt() > 0){
                //  TB_SL_SALE_DTL_DC
                if(saleRegistKwVO.getProdSaleFg().equals("0")){
                    saleRegistKwVO.setDcCd("05");
                } else {
                    saleRegistKwVO.setDcCd("01");
                }
                saleRegistKwMapper.getSaleDtlDc(saleRegistKwVO);
            }

            billDtlNo++;
        }
        return 0;
    }

    /** 매출수기등록 특정 전표 조회 */
    @Override
    public List<DefaultMap<String>> getBillDtlList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwMapper.getBillDtlList(saleRegistKwVO);
    }

    @Override
    public List<DefaultMap<String>> getCashAmt(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwMapper.getCashAmt(saleRegistKwVO);
    }

    @Override
    public List<DefaultMap<String>> getHdrInfo(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        saleRegistKwVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return saleRegistKwMapper.getHdrInfo(saleRegistKwVO);
    }

    @Override
    public String getSaleFg(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwMapper.getSaleFg(saleRegistKwVO);
    }

    @Override
    public int getBillDel(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwMapper.delSaleHdr(saleRegistKwVO);
    }
}
