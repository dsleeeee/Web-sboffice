package kr.co.solbipos.excclc.excclc.saleRegistKwu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.saleRegistKwu.service.SaleRegistKwuVO;
import kr.co.solbipos.excclc.excclc.saleRegistKwu.service.SaleRegistKwuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SaleRegistKwuServiceImpl.java
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

@Service("SaleRegistKwuService")
@Transactional
public class SaleRegistKwuServiceImpl implements SaleRegistKwuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistKwuMapper saleRegistKwuMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleRegistKwuServiceImpl(SaleRegistKwuMapper saleRegistKwuMapper, MessageService messageService) {
        this.saleRegistKwuMapper = saleRegistKwuMapper;
        this.messageService = messageService;
    }

    /** 매출수기등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSaleRegistKwuList(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            saleRegistKwuVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return saleRegistKwuMapper.getSaleRegistKwuList(saleRegistKwuVO);
    }

    /** 매출수기등록 상품 조회 */
    @Override
    public List<DefaultMap<String>> getSelectProdList(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwuMapper.getSelectProdList(saleRegistKwuVO);
    }

    /** 매출처 팝업 조회 */
    @Override
    public List<DefaultMap<String>> getMembrKwList(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        saleRegistKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleRegistKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleRegistKwuVO.setStoreCd(sessionInfoVO.getStoreCd());
        return saleRegistKwuMapper.getMembrKwList(saleRegistKwuVO);
    }

    /** 매출수기등록 저장 */
    @Override
    public int getNewRegistKwList(SaleRegistKwuVO[] saleRegistKwuVOs, SessionInfoVO sessionInfoVO) {

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
        for(SaleRegistKwuVO saleRegistKwuVO : saleRegistKwuVOs){

            saleRegistKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistKwuVO.setRegDt(currentDt);
            saleRegistKwuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKwuVO.setModDt(currentDt);
            saleRegistKwuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

            if(billNo == "" && (saleRegistKwuVO.getBillNo() == null || saleRegistKwuVO.getBillNo().equals(""))){
                // 영수번호 채번
                billNo = saleRegistKwuMapper.getBillNo(saleRegistKwuVO);
            }

            if(saleRegistKwuVO.getBillNo() == null || saleRegistKwuVO.getBillNo().equals("")){
                saleRegistKwuVO.setBillNo(billNo);
            }

            if(saleRegistKwuVO.getCashPer() == 0.0 && saleRegistKwuVO.getCardPer() == 0.0){
                saleRegistKwuVO.setCashPer((float)saleRegistKwuVO.getCashAmt()/(saleRegistKwuVO.getCashAmt() + saleRegistKwuVO.getCardAmt()));
                saleRegistKwuVO.setCardPer((float)saleRegistKwuVO.getCardAmt()/(saleRegistKwuVO.getCashAmt() + saleRegistKwuVO.getCardAmt()));
            }

            if(saleRegistKwuVO.getSaleFg().equals("1")){
                saleRegistKwuVO.setSaleYn("Y");
            } else if(saleRegistKwuVO.getSaleFg().equals("-1")){
                saleRegistKwuVO.setSaleYn("N");
            }

            if(saleRegistKwuVO.getSaleAmt() > 0){
                if(saleRegistKwuVO.getProdVatFg().equals("1")){
                    taxSaleAmt += saleRegistKwuVO.getRealSaleAmt();
                } else {
                    noTaxSaleAmt += saleRegistKwuVO.getRealSaleAmt();
                }
            }

            if(saleRegistKwuVO.getProdSaleFg().equals("0")){
                dcCd = "05";
            }

            totSaleAmt += saleRegistKwuVO.getSaleAmt();
            totDcAmt += saleRegistKwuVO.getDcAmt();
            realSaleAmt += saleRegistKwuVO.getRealSaleAmt();
            vatAmt += saleRegistKwuVO.getVatAmt();

            if(saleRegistKwuVOs.length == billDtlNo){

                saleRegistKwuVO.setTotSaleAmt(totSaleAmt);
                saleRegistKwuVO.setTotDcAmt(totDcAmt);
                saleRegistKwuVO.setTotRealSaleAmt(realSaleAmt);
                saleRegistKwuVO.setTotVatAmt(vatAmt);

                saleRegistKwuVO.setTaxSaleAmt(taxSaleAmt);
                saleRegistKwuVO.setNoTaxSaleAmt(noTaxSaleAmt);
                saleRegistKwuVO.setRecvPayAmt(saleRegistKwuVO.getCashAmt() + saleRegistKwuVO.getCardAmt());

                // TB_SL_SALE_HDR
                saleRegistKwuMapper.getSaleHdr(saleRegistKwuVO);
                saleRegistKwuMapper.getSaleHdrInfo(saleRegistKwuVO);

                // TB_SL_SALE_HDR_PAY
                if(saleRegistKwuVO.getCashAmt() > 0){
                    saleRegistKwuVO.setPayCd("02");
                    saleRegistKwuVO.setPayAmt(saleRegistKwuVO.getCashAmt());
                    saleRegistKwuMapper.getSaleHdrPay(saleRegistKwuVO);
                }
                if(saleRegistKwuVO.getCardAmt() > 0){
                    saleRegistKwuVO.setPayCd("01");
                    saleRegistKwuVO.setPayAmt(saleRegistKwuVO.getCardAmt());
                    saleRegistKwuMapper.getSaleHdrPay(saleRegistKwuVO);
                }

                // TB_SL_SALE_HDR_DC
                if(totDcAmt > 0){
                    saleRegistKwuVO.setDcCd(dcCd);
                    saleRegistKwuMapper.getSaleHdrDc(saleRegistKwuVO);
                }

                // TB_SL_SALE_PAY_CASH
                if(saleRegistKwuVO.getCashAmt() > 0){
                    saleRegistKwuMapper.getSalePayCash(saleRegistKwuVO);
                }

                // TB_SL_SALE_PAY_CARD
                if(saleRegistKwuVO.getCardAmt() > 0){
                    saleRegistKwuMapper.getSalePayCard(saleRegistKwuVO);
                }

                // 2022.12.27 INSERT 막음
                // TB_SL_SALE_PAY
                /*if(saleRegistKwuVO.getCashAmt() > 0){
                    saleRegistKwuVO.setPayCd("02");
                    saleRegistKwuVO.setPayAmt(saleRegistKwuVO.getCashAmt());
                    saleRegistKwuMapper.getSalePay(saleRegistKwuVO);
                }
                if(saleRegistKwuVO.getCardAmt() > 0){
                    saleRegistKwuVO.setPayCd("01");
                    saleRegistKwuVO.setPayAmt(saleRegistKwuVO.getCardAmt());
                    saleRegistKwuMapper.getSalePay(saleRegistKwuVO);
                }*/

                // TB_SL_SALE_PAY_SEQ
                int iPaySeq = 0;
                if(saleRegistKwuVO.getCashAmt() > 0){
                    saleRegistKwuVO.setPaySeq(++iPaySeq);
                    saleRegistKwuVO.setPayCd("02");
                    saleRegistKwuVO.setPayAmt(saleRegistKwuVO.getCashAmt());
                    saleRegistKwuMapper.getSalePaySeq(saleRegistKwuVO);
                }
                if(saleRegistKwuVO.getCardAmt() > 0){
                    saleRegistKwuVO.setPaySeq(++iPaySeq);
                    saleRegistKwuVO.setPayCd("01");
                    saleRegistKwuVO.setPayAmt(saleRegistKwuVO.getCardAmt());
                    saleRegistKwuMapper.getSalePaySeq(saleRegistKwuVO);
                }
            }

            billDtlNo++;
        }


        billDtlNo = 1;
        // dtl
        for(SaleRegistKwuVO saleRegistKwuVO : saleRegistKwuVOs){

            saleRegistKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistKwuVO.setRegDt(currentDt);
            saleRegistKwuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKwuVO.setModDt(currentDt);
            saleRegistKwuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKwuVO.setBillDtlNo(String.valueOf(billDtlNo));


            if(saleRegistKwuVO.getBillNo() == null || saleRegistKwuVO.getBillNo().equals("")){
                saleRegistKwuVO.setBillNo(billNo);
            }

            if(saleRegistKwuVO.getCashPer() == 0.0 && saleRegistKwuVO.getCardPer() == 0.0){
                saleRegistKwuVO.setCashPer((float)saleRegistKwuVO.getCashAmt()/(saleRegistKwuVO.getCashAmt() + saleRegistKwuVO.getCardAmt()));
                saleRegistKwuVO.setCardPer((float)saleRegistKwuVO.getCardAmt()/(saleRegistKwuVO.getCashAmt() + saleRegistKwuVO.getCardAmt()));
            }

            if(saleRegistKwuVO.getSaleFg().equals("1")){
                saleRegistKwuVO.setSaleYn("Y");
            } else if(saleRegistKwuVO.getSaleFg().equals("-1")){
                saleRegistKwuVO.setSaleYn("N");
            }

            // TB_SL_SALE_DTL
            saleRegistKwuMapper.getSaleDtl(saleRegistKwuVO);
            saleRegistKwuMapper.getSaleDtlInfo(saleRegistKwuVO);

            if(saleRegistKwuVO.getRealSaleAmt() > 0){

                //  TB_SL_SALE_DTL_PAY
                if(saleRegistKwuVO.getCashAmt() > 0){
                    saleRegistKwuVO.setPayCd("02");
                    saleRegistKwuMapper.getSaleDtlPay(saleRegistKwuVO);
                }
                if(saleRegistKwuVO.getCardAmt() > 0){
                    saleRegistKwuVO.setPayCd("01");
                    saleRegistKwuMapper.getSaleDtlPay(saleRegistKwuVO);
                }
            }

            if(saleRegistKwuVO.getDcAmt() > 0){
                //  TB_SL_SALE_DTL_DC
                if(saleRegistKwuVO.getProdSaleFg().equals("0")){
                    saleRegistKwuVO.setDcCd("05");
                } else {
                    saleRegistKwuVO.setDcCd("01");
                }
                saleRegistKwuMapper.getSaleDtlDc(saleRegistKwuVO);
            }

            billDtlNo++;
        }
        return 0;
    }

    /** 매출수기등록 특정 전표 조회 */
    @Override
    public List<DefaultMap<String>> getBillDtlList(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwuMapper.getBillDtlList(saleRegistKwuVO);
    }

    @Override
    public List<DefaultMap<String>> getCashAmt(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwuMapper.getCashAmt(saleRegistKwuVO);
    }

    @Override
    public List<DefaultMap<String>> getHdrInfo(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        saleRegistKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return saleRegistKwuMapper.getHdrInfo(saleRegistKwuVO);
    }

    @Override
    public String getSaleFg(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwuMapper.getSaleFg(saleRegistKwuVO);
    }

    @Override
    public int getBillDel(SaleRegistKwuVO saleRegistKwuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKwuMapper.delSaleHdr(saleRegistKwuVO);
    }
}
