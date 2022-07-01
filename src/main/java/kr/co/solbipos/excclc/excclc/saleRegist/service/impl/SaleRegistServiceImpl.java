package kr.co.solbipos.excclc.excclc.saleRegist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.saleRegist.service.SaleRegistService;
import kr.co.solbipos.excclc.excclc.saleRegist.service.SaleRegistVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SaleRegistServiceImpl.java
 * @Description : 정산관리 > 정산관리 > 매출수기등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.24  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.06.24
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("saleRegistService")
@Transactional
public class SaleRegistServiceImpl implements SaleRegistService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistMapper saleRegistMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleRegistServiceImpl(SaleRegistMapper saleRegistMapper, MessageService messageService) {
        this.saleRegistMapper = saleRegistMapper;
        this.messageService = messageService;
    }

    /** 매출수기등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSaleRegistList(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            saleRegistVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return saleRegistMapper.getSaleRegistList(saleRegistVO);
    }

    /** 매출수기등록 상품 조회 */
    @Override
    public List<DefaultMap<String>> getSelectProdList(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO) {
        return saleRegistMapper.getSelectProdList(saleRegistVO);
    }

    /** 매출수기등록 저장 */
    @Override
    public int getNewRegistList(SaleRegistVO[] saleRegistVOs, SessionInfoVO sessionInfoVO) {

        String billNo = "";
        int billDtlNo = 1;
        int taxSaleAmt = 0;
        int noTaxSaleAmt = 0;
        String currentDt = currentDateTimeString();

        int totSaleAmt = 0;
        int totDcAmt = 0;
        int realSaleAmt = 0;
        int vatAmt = 0;

        // hdr
        for(SaleRegistVO saleRegistVO : saleRegistVOs){

            saleRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistVO.setRegDt(currentDt);
            saleRegistVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistVO.setModDt(currentDt);
            saleRegistVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

            if(billNo == "" && (saleRegistVO.getBillNo() == null || saleRegistVO.getBillNo().equals(""))){
                // 영수번호 채번
                billNo = saleRegistMapper.getBillNo(saleRegistVO);
            }

            if(saleRegistVO.getBillNo() == null || saleRegistVO.getBillNo().equals("")){
                saleRegistVO.setBillNo(billNo);
            }

            if(saleRegistVO.getCashPer() == 0.0 && saleRegistVO.getCardPer() == 0.0){
                saleRegistVO.setCashPer((float)saleRegistVO.getCashAmt()/(saleRegistVO.getCashAmt() + saleRegistVO.getCardAmt()));
                saleRegistVO.setCardPer((float)saleRegistVO.getCardAmt()/(saleRegistVO.getCashAmt() + saleRegistVO.getCardAmt()));
            }

            if(saleRegistVO.getSaleFg().equals("1")){
                saleRegistVO.setSaleYn("Y");
            } else if(saleRegistVO.getSaleFg().equals("-1")){
                saleRegistVO.setSaleYn("N");
            }

            if(saleRegistVO.getSaleAmt() > 0){
                if(saleRegistVO.getVatFg().equals("1")){
                    taxSaleAmt += saleRegistVO.getRealSaleAmt();
                } else {
                    noTaxSaleAmt += saleRegistVO.getRealSaleAmt();
                }
            }

            totSaleAmt += saleRegistVO.getSaleAmt();
            totDcAmt += saleRegistVO.getDcAmt();
            realSaleAmt += saleRegistVO.getRealSaleAmt();
            vatAmt += saleRegistVO.getVatAmt();

            if(saleRegistVOs.length == billDtlNo){

                saleRegistVO.setTotSaleAmt(totSaleAmt);
                saleRegistVO.setTotDcAmt(totDcAmt);
                saleRegistVO.setTotRealSaleAmt(realSaleAmt);
                saleRegistVO.setTotVatAmt(vatAmt);

                saleRegistVO.setTaxSaleAmt(taxSaleAmt);
                saleRegistVO.setNoTaxSaleAmt(noTaxSaleAmt);
                saleRegistVO.setRecvPayAmt(saleRegistVO.getCashAmt() + saleRegistVO.getCardAmt());
                // TB_SL_SALE_HDR
                saleRegistMapper.getSaleHdr(saleRegistVO);

                // TB_SL_SALE_HDR_PAY
                if(saleRegistVO.getCashAmt() > 0){
                    saleRegistVO.setPayCd("02");
                    saleRegistVO.setPayAmt(saleRegistVO.getCashAmt());
                    saleRegistMapper.getSaleHdrPay(saleRegistVO);
                }
                if(saleRegistVO.getCardAmt() > 0){
                    saleRegistVO.setPayCd("01");
                    saleRegistVO.setPayAmt(saleRegistVO.getCardAmt());
                    saleRegistMapper.getSaleHdrPay(saleRegistVO);
                }

                // TB_SL_SALE_HDR_DC
                if(saleRegistVO.getDcAmt() > 0){
                    saleRegistMapper.getSaleHdrDc(saleRegistVO);
                }

                // TB_SL_SALE_PAY_CASH
                if(saleRegistVO.getCashAmt() > 0){
                    saleRegistMapper.getSalePayCash(saleRegistVO);
                }

                // TB_SL_SALE_PAY_CARD
                if(saleRegistVO.getCardAmt() > 0){
                    saleRegistMapper.getSalePayCard(saleRegistVO);
                }
            }

            billDtlNo++;
        }


        billDtlNo = 1;
        // dtl
        for(SaleRegistVO saleRegistVO : saleRegistVOs){

            saleRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistVO.setRegDt(currentDt);
            saleRegistVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistVO.setModDt(currentDt);
            saleRegistVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistVO.setBillDtlNo(String.valueOf(billDtlNo));


            if(saleRegistVO.getBillNo() == null || saleRegistVO.getBillNo().equals("")){
                saleRegistVO.setBillNo(billNo);
            }

            if(saleRegistVO.getCashPer() == 0.0 && saleRegistVO.getCardPer() == 0.0){
                saleRegistVO.setCashPer((float)saleRegistVO.getCashAmt()/(saleRegistVO.getCashAmt() + saleRegistVO.getCardAmt()));
                saleRegistVO.setCardPer((float)saleRegistVO.getCardAmt()/(saleRegistVO.getCashAmt() + saleRegistVO.getCardAmt()));
            }

            if(saleRegistVO.getSaleFg().equals("1")){
                saleRegistVO.setSaleYn("Y");
            } else if(saleRegistVO.getSaleFg().equals("-1")){
                saleRegistVO.setSaleYn("N");
            }

            // TB_SL_SALE_DTL
            saleRegistMapper.getSaleDtl(saleRegistVO);

            if(saleRegistVO.getRealSaleAmt() > 0){

                //  TB_SL_SALE_DTL_PAY
                if(saleRegistVO.getCashAmt() > 0){
                    saleRegistVO.setPayCd("02");
                    saleRegistMapper.getSaleDtlPay(saleRegistVO);
                }
                if(saleRegistVO.getCardAmt() > 0){
                    saleRegistVO.setPayCd("01");
                    saleRegistMapper.getSaleDtlPay(saleRegistVO);
                }
            }

            if(saleRegistVO.getDcAmt() > 0){
                //  TB_SL_SALE_DTL_DC
                saleRegistMapper.getSaleDtlDc(saleRegistVO);
            }

            billDtlNo++;
        }
        return 0;
    }

    /** 매출수기등록 특정 전표 조회 */
    @Override
    public List<DefaultMap<String>> getBillDtlList(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO) {
        return saleRegistMapper.getBillDtlList(saleRegistVO);
    }

    @Override
    public List<DefaultMap<String>> getCashAmt(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO) {
        return saleRegistMapper.getCashAmt(saleRegistVO);
    }

    @Override
    public String getSaleFg(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO) {
        return saleRegistMapper.getSaleFg(saleRegistVO);
    }

    @Override
    public int getBillDel(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO) {
        return saleRegistMapper.delSaleHdr(saleRegistVO);
    }
}
