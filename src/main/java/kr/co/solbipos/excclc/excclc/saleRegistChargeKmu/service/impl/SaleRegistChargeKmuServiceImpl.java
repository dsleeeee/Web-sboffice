package kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service.SaleRegistChargeKmuService;
import kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service.SaleRegistChargeKmuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SaleRegistChargeKmuServiceImpl.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(수수료)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("saleRegistChargeKmuService")
@Transactional
public class SaleRegistChargeKmuServiceImpl implements SaleRegistChargeKmuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistChargeKmuMapper saleRegistChargeKmuMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleRegistChargeKmuServiceImpl(SaleRegistChargeKmuMapper saleRegistChargeKmuMapper, MessageService messageService) {
        this.saleRegistChargeKmuMapper = saleRegistChargeKmuMapper;
        this.messageService = messageService;
    }

    /** 매출수기등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSaleRegistChargeKmuList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            saleRegistChargeKmuVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return saleRegistChargeKmuMapper.getSaleRegistChargeKmuList(saleRegistChargeKmuVO);
    }

    /** 매출수기등록 상품 조회 */
    @Override
    public List<DefaultMap<String>> getSelectProdList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistChargeKmuMapper.getSelectProdList(saleRegistChargeKmuVO);
    }

    /** 매출수기등록 저장 */
    @Override
    public int getNewRegistList(SaleRegistChargeKmuVO[] saleRegistChargeKmuVOs, SessionInfoVO sessionInfoVO) {

        String billNo = "";
        long billDtlNo = 1;
        long taxSaleAmt = 0;
        long noTaxSaleAmt = 0;
        String currentDt = currentDateTimeString();

        long totSaleAmt = 0;
        long totDcAmt = 0;
        long realSaleAmt = 0;
        long vatAmt = 0;

        // hdr
        for(SaleRegistChargeKmuVO saleRegistChargeKmuVO : saleRegistChargeKmuVOs){

            saleRegistChargeKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistChargeKmuVO.setRegDt(currentDt);
            saleRegistChargeKmuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistChargeKmuVO.setModDt(currentDt);
            saleRegistChargeKmuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

            if(billNo == "" && (saleRegistChargeKmuVO.getBillNo() == null || saleRegistChargeKmuVO.getBillNo().equals(""))){
                // 영수번호 채번
                billNo = saleRegistChargeKmuMapper.getBillNo(saleRegistChargeKmuVO);
            }

            if(saleRegistChargeKmuVO.getBillNo() == null || saleRegistChargeKmuVO.getBillNo().equals("")){
                saleRegistChargeKmuVO.setBillNo(billNo);
            }

            // 금액 결제구분에 따라 처리
            Long amt =  saleRegistChargeKmuVO.getCashAmt();

            // 결제구분 (02: 현금, 01:카드, 11:외상)
            if("01".equals(saleRegistChargeKmuVO.getSaleGubunCombo())) {
                saleRegistChargeKmuVO.setCashAmt(0);
                saleRegistChargeKmuVO.setCardAmt(amt);
                saleRegistChargeKmuVO.setPostPaidAmt(0);
            }
            else if("02".equals(saleRegistChargeKmuVO.getSaleGubunCombo())) {
                saleRegistChargeKmuVO.setCashAmt(amt);
                saleRegistChargeKmuVO.setCardAmt(0);
                saleRegistChargeKmuVO.setPostPaidAmt(0);
            }
            else if("11".equals(saleRegistChargeKmuVO.getSaleGubunCombo())) {
                saleRegistChargeKmuVO.setCashAmt(0);
                saleRegistChargeKmuVO.setCardAmt(0);
                saleRegistChargeKmuVO.setPostPaidAmt(amt);
            }

            if(saleRegistChargeKmuVO.getCashPer() == 0.0 && saleRegistChargeKmuVO.getCardPer() == 0.0 && saleRegistChargeKmuVO.getPostPaidPer() == 0.0){
                saleRegistChargeKmuVO.setCashPer((float)saleRegistChargeKmuVO.getCashAmt()/(saleRegistChargeKmuVO.getCashAmt() + saleRegistChargeKmuVO.getCardAmt() + saleRegistChargeKmuVO.getPostPaidAmt()));
                saleRegistChargeKmuVO.setCardPer((float)saleRegistChargeKmuVO.getCardAmt()/(saleRegistChargeKmuVO.getCashAmt() + saleRegistChargeKmuVO.getCardAmt() + saleRegistChargeKmuVO.getPostPaidAmt()));
                saleRegistChargeKmuVO.setPostPaidPer((float)saleRegistChargeKmuVO.getPostPaidAmt()/(saleRegistChargeKmuVO.getCashAmt() + saleRegistChargeKmuVO.getCardAmt() + saleRegistChargeKmuVO.getPostPaidAmt()));
            }

            if(saleRegistChargeKmuVO.getSaleFg().equals("1")){
                saleRegistChargeKmuVO.setSaleYn("Y");
            } else if(saleRegistChargeKmuVO.getSaleFg().equals("-1")){
                saleRegistChargeKmuVO.setSaleYn("N");
            }

            if(saleRegistChargeKmuVO.getSaleAmt() > 0){
                if(saleRegistChargeKmuVO.getVatFg().equals("1")){
                    taxSaleAmt += saleRegistChargeKmuVO.getRealSaleAmt();
                } else {
                    noTaxSaleAmt += saleRegistChargeKmuVO.getRealSaleAmt();
                }
            }

            totSaleAmt += saleRegistChargeKmuVO.getSaleAmt();
            totDcAmt += saleRegistChargeKmuVO.getDcAmt();
            realSaleAmt += saleRegistChargeKmuVO.getRealSaleAmt();
            vatAmt += saleRegistChargeKmuVO.getVatAmt();

            if(saleRegistChargeKmuVOs.length == billDtlNo){

                saleRegistChargeKmuVO.setTotSaleAmt(totSaleAmt);
                saleRegistChargeKmuVO.setTotDcAmt(totDcAmt);
                saleRegistChargeKmuVO.setTotRealSaleAmt(realSaleAmt);
                saleRegistChargeKmuVO.setTotVatAmt(vatAmt);

                saleRegistChargeKmuVO.setTaxSaleAmt(taxSaleAmt);
                saleRegistChargeKmuVO.setNoTaxSaleAmt(noTaxSaleAmt);
                saleRegistChargeKmuVO.setRecvPayAmt(saleRegistChargeKmuVO.getCashAmt() + saleRegistChargeKmuVO.getCardAmt());

                if(saleRegistChargeKmuVO.getMembrNo() == "" || saleRegistChargeKmuVO.getMembrNo() == null) {
                    saleRegistChargeKmuVO.setMembrYn("N");
                } else {
                    saleRegistChargeKmuVO.setMembrYn("Y");
                }

                // TB_SL_SALE_HDR
                saleRegistChargeKmuMapper.getSaleHdr(saleRegistChargeKmuVO);

                // TB_SL_SALE_HDR_PAY
                if(saleRegistChargeKmuVO.getCashAmt() > 0){
                    saleRegistChargeKmuVO.setPayCd("02");
                    saleRegistChargeKmuVO.setPayAmt(saleRegistChargeKmuVO.getCashAmt());
                    saleRegistChargeKmuMapper.getSaleHdrPay(saleRegistChargeKmuVO);
                }
                if(saleRegistChargeKmuVO.getCardAmt() > 0){
                    saleRegistChargeKmuVO.setPayCd("01");
                    saleRegistChargeKmuVO.setPayAmt(saleRegistChargeKmuVO.getCardAmt());
                    saleRegistChargeKmuMapper.getSaleHdrPay(saleRegistChargeKmuVO);
                }
                if(saleRegistChargeKmuVO.getPostPaidAmt() > 0) {
                    saleRegistChargeKmuVO.setPayCd("11");
                    saleRegistChargeKmuVO.setPayAmt(saleRegistChargeKmuVO.getPostPaidAmt());
                    saleRegistChargeKmuMapper.getSaleHdrPay(saleRegistChargeKmuVO);
                }

                // TB_SL_SALE_HDR_DC
                if(totDcAmt > 0){
                    saleRegistChargeKmuMapper.getSaleHdrDc(saleRegistChargeKmuVO);
                }

                // TB_SL_SALE_PAY_CASH
                if(saleRegistChargeKmuVO.getCashAmt() > 0){
                    saleRegistChargeKmuMapper.getSalePayCash(saleRegistChargeKmuVO);
                }

                // TB_SL_SALE_PAY_CARD
                if(saleRegistChargeKmuVO.getCardAmt() > 0){
                    saleRegistChargeKmuMapper.getSalePayCard(saleRegistChargeKmuVO);
                }

                // TB_SL_SALE_PAY_POSTPAID
                if(saleRegistChargeKmuVO.getPostPaidAmt() > 0){
                    saleRegistChargeKmuMapper.getSalePayPostpaid(saleRegistChargeKmuVO);
                }

                // 2022.12.27 INSERT 막음
                // TB_SL_SALE_PAY
                /*if(saleRegistVO.getCashAmt() > 0){
                    saleRegistVO.setPayCd("02");
                    saleRegistVO.setPayAmt(saleRegistVO.getCashAmt());
                    saleRegistMapper.getSalePay(saleRegistVO);
                }
                if(saleRegistVO.getCardAmt() > 0){
                    saleRegistVO.setPayCd("01");
                    saleRegistVO.setPayAmt(saleRegistVO.getCardAmt());
                    saleRegistMapper.getSalePay(saleRegistVO);
                }*/

                // TB_SL_SALE_PAY_SEQ
                int iPaySeq = 0;
                if(saleRegistChargeKmuVO.getCashAmt() > 0){
                    saleRegistChargeKmuVO.setPaySeq(++iPaySeq);
                    saleRegistChargeKmuVO.setPayCd("02");
                    saleRegistChargeKmuVO.setPayAmt(saleRegistChargeKmuVO.getCashAmt());
                    saleRegistChargeKmuMapper.getSalePaySeq(saleRegistChargeKmuVO);
                }
                if(saleRegistChargeKmuVO.getCardAmt() > 0){
                    saleRegistChargeKmuVO.setPaySeq(++iPaySeq);
                    saleRegistChargeKmuVO.setPayCd("01");
                    saleRegistChargeKmuVO.setPayAmt(saleRegistChargeKmuVO.getCardAmt());
                    saleRegistChargeKmuMapper.getSalePaySeq(saleRegistChargeKmuVO);
                }
                if(saleRegistChargeKmuVO.getPostPaidAmt() > 0) {
                    saleRegistChargeKmuVO.setPaySeq(++iPaySeq);
                    saleRegistChargeKmuVO.setPayCd("11");
                    saleRegistChargeKmuVO.setPayAmt(saleRegistChargeKmuVO.getPostPaidAmt());
                    saleRegistChargeKmuMapper.getSalePaySeq(saleRegistChargeKmuVO);
                }

                if(saleRegistChargeKmuVO.getMembrYn() == "Y") {
                    saleRegistChargeKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

                    // 회원정보 조회
                    List<DefaultMap<Object>> list = saleRegistChargeKmuMapper.getMemberList(saleRegistChargeKmuVO);
                    if(list.size() > 0) {
                        saleRegistChargeKmuVO.setMembrNm(list.get(0).getStr("membrNm"));
                        saleRegistChargeKmuVO.setMembrClassCd(list.get(0).getStr("membrClassCd"));
                        saleRegistChargeKmuVO.setMembrCardNo(list.get(0).getStr("membrCardNo"));
                        saleRegistChargeKmuVO.setBkMembrNm(list.get(0).getStr("bkMembrNm"));
                    }

                    // TB_SL_SALE_HDR_MEMBR
                    saleRegistChargeKmuMapper.getSaleHdrMembr(saleRegistChargeKmuVO);
                }

                // 결제구분 외상거래
                if("11".equals(saleRegistChargeKmuVO.getSaleGubunCombo())) {
                    // 매출
                    if("1".equals(saleRegistChargeKmuVO.getSaleFg())) {
                        saleRegistChargeKmuVO.setPostpaidFg("3"); // 후불구분
                    }
                    // 반품
                    else {
                        saleRegistChargeKmuVO.setPostpaidFg("4"); // 후불구분
                    }
                    saleRegistChargeKmuVO.setPostpaidPayFg("0"); // 후불입금수단구분
                    saleRegistChargeKmuVO.setNonsaleTypeApprNo(saleRegistChargeKmuVO.getMembrNo()); // 비매출승인번호
                    saleRegistChargeKmuVO.setOrgNonsaleTypeApprNo(""); // 원거래비매출승인번호
                    saleRegistChargeKmuVO.setNonsaleBillNo(saleRegistChargeKmuVO.getStoreCd() + saleRegistChargeKmuVO.getSaleDate() + "99" + saleRegistChargeKmuVO.getBillNo()); // 비매출영수증번호
                    saleRegistChargeKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

                    // TB_MB_MEMBER_POSTPAID
                    saleRegistChargeKmuMapper.getMemberPostpaid(saleRegistChargeKmuVO);

                    // TB_MB_MEMBER_PAID_BALANCE
                    saleRegistChargeKmuMapper.getMemberPaidBalance(saleRegistChargeKmuVO);
                }
            }

            billDtlNo++;
        }


        billDtlNo = 1;
        // dtl
        for(SaleRegistChargeKmuVO saleRegistChargeKmuVO : saleRegistChargeKmuVOs){

            saleRegistChargeKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistChargeKmuVO.setRegDt(currentDt);
            saleRegistChargeKmuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistChargeKmuVO.setModDt(currentDt);
            saleRegistChargeKmuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistChargeKmuVO.setBillDtlNo(String.valueOf(billDtlNo));

            if(saleRegistChargeKmuVO.getBillNo() == null || saleRegistChargeKmuVO.getBillNo().equals("")){
                saleRegistChargeKmuVO.setBillNo(billNo);
            }

            // 금액 결제구분에 따라 처리
            Long amt =  saleRegistChargeKmuVO.getCashAmt();

            // 결제구분 (02: 현금, 01:카드, 11:외상)
            if("01".equals(saleRegistChargeKmuVO.getSaleGubunCombo())) {
                saleRegistChargeKmuVO.setCashAmt(0);
                saleRegistChargeKmuVO.setCardAmt(amt);
                saleRegistChargeKmuVO.setPostPaidAmt(0);
            }
            else if("02".equals(saleRegistChargeKmuVO.getSaleGubunCombo())) {
                saleRegistChargeKmuVO.setCashAmt(amt);
                saleRegistChargeKmuVO.setCardAmt(0);
                saleRegistChargeKmuVO.setPostPaidAmt(0);
            }
            else if("11".equals(saleRegistChargeKmuVO.getSaleGubunCombo())) {
                saleRegistChargeKmuVO.setCashAmt(0);
                saleRegistChargeKmuVO.setCardAmt(0);
                saleRegistChargeKmuVO.setPostPaidAmt(amt);
            }

            if(saleRegistChargeKmuVO.getCashPer() == 0.0 && saleRegistChargeKmuVO.getCardPer() == 0.0 && saleRegistChargeKmuVO.getPostPaidPer() == 0.0){
                saleRegistChargeKmuVO.setCashPer((float)saleRegistChargeKmuVO.getCashAmt()/(saleRegistChargeKmuVO.getCashAmt() + saleRegistChargeKmuVO.getCardAmt() + saleRegistChargeKmuVO.getPostPaidAmt()));
                saleRegistChargeKmuVO.setCardPer((float)saleRegistChargeKmuVO.getCardAmt()/(saleRegistChargeKmuVO.getCashAmt() + saleRegistChargeKmuVO.getCardAmt() + saleRegistChargeKmuVO.getPostPaidAmt()));
                saleRegistChargeKmuVO.setPostPaidPer((float)saleRegistChargeKmuVO.getPostPaidAmt()/(saleRegistChargeKmuVO.getCashAmt() + saleRegistChargeKmuVO.getCardAmt() + saleRegistChargeKmuVO.getPostPaidAmt()));
            }

            if(saleRegistChargeKmuVO.getSaleFg().equals("1")){
                saleRegistChargeKmuVO.setSaleYn("Y");
            } else if(saleRegistChargeKmuVO.getSaleFg().equals("-1")){
                saleRegistChargeKmuVO.setSaleYn("N");
            }

            // TB_SL_SALE_DTL
            saleRegistChargeKmuMapper.getSaleDtl(saleRegistChargeKmuVO);

            if(saleRegistChargeKmuVO.getRealSaleAmt() > 0){

                //  TB_SL_SALE_DTL_PAY
                if(saleRegistChargeKmuVO.getCashAmt() > 0){
                    saleRegistChargeKmuVO.setPayCd("02");
                    saleRegistChargeKmuMapper.getSaleDtlPay(saleRegistChargeKmuVO);
                }
                if(saleRegistChargeKmuVO.getCardAmt() > 0){
                    saleRegistChargeKmuVO.setPayCd("01");
                    saleRegistChargeKmuMapper.getSaleDtlPay(saleRegistChargeKmuVO);
                }
                if(saleRegistChargeKmuVO.getPostPaidAmt() > 0) {
                    saleRegistChargeKmuVO.setPayCd("11");
                    saleRegistChargeKmuMapper.getSaleDtlPay(saleRegistChargeKmuVO);
                }

            }

            if(saleRegistChargeKmuVO.getDcAmt() > 0){
                //  TB_SL_SALE_DTL_DC
                saleRegistChargeKmuMapper.getSaleDtlDc(saleRegistChargeKmuVO);
            }

            // TB_SL_SALE_DTL_SLIP_KMU
            saleRegistChargeKmuMapper.getSaleDtlSlipKmu(saleRegistChargeKmuVO);

            billDtlNo++;
        }
        return 0;
    }

    /** 매출수기등록 특정 전표 조회 */
    @Override
    public List<DefaultMap<String>> getBillDtlList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistChargeKmuMapper.getBillDtlList(saleRegistChargeKmuVO);
    }

    @Override
    public List<DefaultMap<String>> getCashAmt(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistChargeKmuMapper.getCashAmt(saleRegistChargeKmuVO);
    }

    @Override
    public String getSaleFg(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistChargeKmuMapper.getSaleFg(saleRegistChargeKmuVO);
    }

    @Override
    public int getBillDel(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        saleRegistChargeKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        saleRegistChargeKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleRegistChargeKmuVO.setRegDt(currentDt);
        saleRegistChargeKmuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
        saleRegistChargeKmuVO.setModDt(currentDt);
        saleRegistChargeKmuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

        // TB_SL_SALE_HDR_MEMBR
        saleRegistChargeKmuMapper.delSaleHdrMembr(saleRegistChargeKmuVO);

        // TB_SL_SALE_HDR
        saleRegistChargeKmuMapper.delSaleHdr(saleRegistChargeKmuVO);

        // TB_MB_MEMBER_PAID_BALANCE
        saleRegistChargeKmuMapper.delMemberPaidBalance(saleRegistChargeKmuVO);

        // TB_MB_MEMBER_POSTPAID
        saleRegistChargeKmuMapper.delMemberPostpaid(saleRegistChargeKmuVO);

        // TB_SL_SALE_DTL_SLIP_KMU
        saleRegistChargeKmuMapper.delSaleDtlSlipKmu(saleRegistChargeKmuVO);

        return 0;
    }

    /** 회원선택 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleRegistChargeKmuMemberList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {

        saleRegistChargeKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        return saleRegistChargeKmuMapper.getSaleRegistChargeKmuMemberList(saleRegistChargeKmuVO);
    }

    /** 매출처선택 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleRegistChargeKmuCustomerList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO) {

        saleRegistChargeKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return saleRegistChargeKmuMapper.getSaleRegistChargeKmuCustomerList(saleRegistChargeKmuVO);
    }

}