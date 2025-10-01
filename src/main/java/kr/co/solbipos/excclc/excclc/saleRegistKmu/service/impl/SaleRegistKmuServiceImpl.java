package kr.co.solbipos.excclc.excclc.saleRegistKmu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.saleRegistKmu.service.SaleRegistKmuService;
import kr.co.solbipos.excclc.excclc.saleRegistKmu.service.SaleRegistKmuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SaleRegistKmuServiceImpl.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(일반)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("saleRegistKmuService")
@Transactional
public class SaleRegistKmuServiceImpl implements SaleRegistKmuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SaleRegistKmuMapper saleRegistKmuMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleRegistKmuServiceImpl(SaleRegistKmuMapper saleRegistKmuMapper, MessageService messageService) {
        this.saleRegistKmuMapper = saleRegistKmuMapper;
        this.messageService = messageService;
    }

    /** 매출수기등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSaleRegistKmuList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            saleRegistKmuVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return saleRegistKmuMapper.getSaleRegistKmuList(saleRegistKmuVO);
    }

    /** 매출수기등록 상품 조회 */
    @Override
    public List<DefaultMap<String>> getSelectProdList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKmuMapper.getSelectProdList(saleRegistKmuVO);
    }

    /** 매출수기등록 저장 */
    @Override
    public int getNewRegistList(SaleRegistKmuVO[] saleRegistKmuVOs, SessionInfoVO sessionInfoVO) {

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
        for(SaleRegistKmuVO saleRegistKmuVO : saleRegistKmuVOs){

            saleRegistKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistKmuVO.setRegDt(currentDt);
            saleRegistKmuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKmuVO.setModDt(currentDt);
            saleRegistKmuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

            if(billNo == "" && (saleRegistKmuVO.getBillNo() == null || saleRegistKmuVO.getBillNo().equals(""))){
                // 영수번호 채번
                billNo = saleRegistKmuMapper.getBillNo(saleRegistKmuVO);
            }

            if(saleRegistKmuVO.getBillNo() == null || saleRegistKmuVO.getBillNo().equals("")){
                saleRegistKmuVO.setBillNo(billNo);
            }

            // 금액 결제구분에 따라 처리
            Long amt =  saleRegistKmuVO.getCashAmt();

            // 결제구분 (02: 현금, 01:카드, 11:외상)
             if("01".equals(saleRegistKmuVO.getSaleGubunCombo())) {
                 saleRegistKmuVO.setCashAmt(0);
                 saleRegistKmuVO.setCardAmt(amt);
                 saleRegistKmuVO.setPostPaidAmt(0);
             }
             else if("02".equals(saleRegistKmuVO.getSaleGubunCombo())) {
                 saleRegistKmuVO.setCashAmt(amt);
                 saleRegistKmuVO.setCardAmt(0);
                 saleRegistKmuVO.setPostPaidAmt(0);
             }
             else if("11".equals(saleRegistKmuVO.getSaleGubunCombo())) {
                 saleRegistKmuVO.setCashAmt(0);
                 saleRegistKmuVO.setCardAmt(0);
                 saleRegistKmuVO.setPostPaidAmt(amt);
             }

            if(saleRegistKmuVO.getCashPer() == 0.0 && saleRegistKmuVO.getCardPer() == 0.0 && saleRegistKmuVO.getPostPaidPer() == 0.0){
                saleRegistKmuVO.setCashPer((float)saleRegistKmuVO.getCashAmt()/(saleRegistKmuVO.getCashAmt() + saleRegistKmuVO.getCardAmt() + saleRegistKmuVO.getPostPaidAmt()));
                saleRegistKmuVO.setCardPer((float)saleRegistKmuVO.getCardAmt()/(saleRegistKmuVO.getCashAmt() + saleRegistKmuVO.getCardAmt() + saleRegistKmuVO.getPostPaidAmt()));
                saleRegistKmuVO.setPostPaidPer((float)saleRegistKmuVO.getPostPaidAmt()/(saleRegistKmuVO.getCashAmt() + saleRegistKmuVO.getCardAmt() + saleRegistKmuVO.getPostPaidAmt()));
            }

            if(saleRegistKmuVO.getSaleFg().equals("1")){
                saleRegistKmuVO.setSaleYn("Y");
            } else if(saleRegistKmuVO.getSaleFg().equals("-1")){
                saleRegistKmuVO.setSaleYn("N");
            }

            if(saleRegistKmuVO.getSaleAmt() > 0){
                if(saleRegistKmuVO.getVatFg().equals("1")){
                    taxSaleAmt += saleRegistKmuVO.getRealSaleAmt();
                } else {
                    noTaxSaleAmt += saleRegistKmuVO.getRealSaleAmt();
                }
            }

            totSaleAmt += saleRegistKmuVO.getSaleAmt();
            totDcAmt += saleRegistKmuVO.getDcAmt();
            realSaleAmt += saleRegistKmuVO.getRealSaleAmt();
            vatAmt += saleRegistKmuVO.getVatAmt();

            if(saleRegistKmuVOs.length == billDtlNo){

                saleRegistKmuVO.setTotSaleAmt(totSaleAmt);
                saleRegistKmuVO.setTotDcAmt(totDcAmt);
                saleRegistKmuVO.setTotRealSaleAmt(realSaleAmt);
                saleRegistKmuVO.setTotVatAmt(vatAmt);

                saleRegistKmuVO.setTaxSaleAmt(taxSaleAmt);
                saleRegistKmuVO.setNoTaxSaleAmt(noTaxSaleAmt);
                saleRegistKmuVO.setRecvPayAmt(saleRegistKmuVO.getCashAmt() + saleRegistKmuVO.getCardAmt());

                if(saleRegistKmuVO.getMembrNo() == "" || saleRegistKmuVO.getMembrNo() == null) {
                    saleRegistKmuVO.setMembrYn("N");
                } else {
                    saleRegistKmuVO.setMembrYn("Y");
                }

                // TB_SL_SALE_HDR
                saleRegistKmuMapper.getSaleHdr(saleRegistKmuVO);

                // TB_SL_SALE_HDR_PAY
                if(saleRegistKmuVO.getCashAmt() > 0){
                    saleRegistKmuVO.setPayCd("02");
                    saleRegistKmuVO.setPayAmt(saleRegistKmuVO.getCashAmt());
                    saleRegistKmuMapper.getSaleHdrPay(saleRegistKmuVO);
                }
                if(saleRegistKmuVO.getCardAmt() > 0){
                    saleRegistKmuVO.setPayCd("01");
                    saleRegistKmuVO.setPayAmt(saleRegistKmuVO.getCardAmt());
                    saleRegistKmuMapper.getSaleHdrPay(saleRegistKmuVO);
                }
                if(saleRegistKmuVO.getPostPaidAmt() > 0) {
                    saleRegistKmuVO.setPayCd("11");
                    saleRegistKmuVO.setPayAmt(saleRegistKmuVO.getPostPaidAmt());
                    saleRegistKmuMapper.getSaleHdrPay(saleRegistKmuVO);
                }

                // TB_SL_SALE_HDR_DC
                if(totDcAmt > 0){
                    saleRegistKmuMapper.getSaleHdrDc(saleRegistKmuVO);
                }

                // TB_SL_SALE_PAY_CASH
                if(saleRegistKmuVO.getCashAmt() > 0){
                    saleRegistKmuMapper.getSalePayCash(saleRegistKmuVO);
                }

                // TB_SL_SALE_PAY_CARD
                if(saleRegistKmuVO.getCardAmt() > 0){
                    saleRegistKmuMapper.getSalePayCard(saleRegistKmuVO);
                }

                // TB_SL_SALE_PAY_POSTPAID
                if(saleRegistKmuVO.getPostPaidAmt() > 0){
                    saleRegistKmuMapper.getSalePayPostpaid(saleRegistKmuVO);
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
                if(saleRegistKmuVO.getCashAmt() > 0){
                    saleRegistKmuVO.setPaySeq(++iPaySeq);
                    saleRegistKmuVO.setPayCd("02");
                    saleRegistKmuVO.setPayAmt(saleRegistKmuVO.getCashAmt());
                    saleRegistKmuMapper.getSalePaySeq(saleRegistKmuVO);
                }
                if(saleRegistKmuVO.getCardAmt() > 0){
                    saleRegistKmuVO.setPaySeq(++iPaySeq);
                    saleRegistKmuVO.setPayCd("01");
                    saleRegistKmuVO.setPayAmt(saleRegistKmuVO.getCardAmt());
                    saleRegistKmuMapper.getSalePaySeq(saleRegistKmuVO);
                }
                if(saleRegistKmuVO.getPostPaidAmt() > 0) {
                    saleRegistKmuVO.setPaySeq(++iPaySeq);
                    saleRegistKmuVO.setPayCd("11");
                    saleRegistKmuVO.setPayAmt(saleRegistKmuVO.getPostPaidAmt());
                    saleRegistKmuMapper.getSalePaySeq(saleRegistKmuVO);
                }

                if(saleRegistKmuVO.getMembrYn() == "Y") {
                    saleRegistKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

                    // 회원정보 조회
                    List<DefaultMap<Object>> list = saleRegistKmuMapper.getMemberList(saleRegistKmuVO);
                    if(list.size() > 0) {
                        saleRegistKmuVO.setMembrNm(list.get(0).getStr("membrNm"));
                        saleRegistKmuVO.setMembrClassCd(list.get(0).getStr("membrClassCd"));
                        saleRegistKmuVO.setMembrCardNo(list.get(0).getStr("membrCardNo"));
                        saleRegistKmuVO.setBkMembrNm(list.get(0).getStr("bkMembrNm"));
                    }

                    // TB_SL_SALE_HDR_MEMBR
                    saleRegistKmuMapper.getSaleHdrMembr(saleRegistKmuVO);
                }

                // 결제구분 외상거래
                if("11".equals(saleRegistKmuVO.getSaleGubunCombo())) {
                    // 매출
                    if("1".equals(saleRegistKmuVO.getSaleFg())) {
                        saleRegistKmuVO.setPostpaidFg("3"); // 후불구분
                    }
                    // 반품
                    else {
                        saleRegistKmuVO.setPostpaidFg("4"); // 후불구분
                    }
                    saleRegistKmuVO.setPostpaidPayFg("0"); // 후불입금수단구분
                    saleRegistKmuVO.setNonsaleTypeApprNo(saleRegistKmuVO.getMembrNo()); // 비매출승인번호
                    saleRegistKmuVO.setOrgNonsaleTypeApprNo(""); // 원거래비매출승인번호
                    saleRegistKmuVO.setNonsaleBillNo(saleRegistKmuVO.getStoreCd() + saleRegistKmuVO.getSaleDate() + "99" + saleRegistKmuVO.getBillNo()); // 비매출영수증번호
                    saleRegistKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

                    // TB_MB_MEMBER_POSTPAID
                    saleRegistKmuMapper.getMemberPostpaid(saleRegistKmuVO);

                    // TB_MB_MEMBER_PAID_BALANCE
                    saleRegistKmuMapper.getMemberPaidBalance(saleRegistKmuVO);
                }
            }

            billDtlNo++;
        }


        billDtlNo = 1;
        // dtl
        for(SaleRegistKmuVO saleRegistKmuVO : saleRegistKmuVOs){

            saleRegistKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            saleRegistKmuVO.setRegDt(currentDt);
            saleRegistKmuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKmuVO.setModDt(currentDt);
            saleRegistKmuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());
            saleRegistKmuVO.setBillDtlNo(String.valueOf(billDtlNo));

            if(saleRegistKmuVO.getBillNo() == null || saleRegistKmuVO.getBillNo().equals("")){
                saleRegistKmuVO.setBillNo(billNo);
            }

            // 금액 결제구분에 따라 처리
            Long amt =  saleRegistKmuVO.getCashAmt();

            // 결제구분 (02: 현금, 01:카드, 11:외상)
            if("01".equals(saleRegistKmuVO.getSaleGubunCombo())) {
                saleRegistKmuVO.setCashAmt(0);
                saleRegistKmuVO.setCardAmt(amt);
                saleRegistKmuVO.setPostPaidAmt(0);
            }
            else if("02".equals(saleRegistKmuVO.getSaleGubunCombo())) {
                saleRegistKmuVO.setCashAmt(amt);
                saleRegistKmuVO.setCardAmt(0);
                saleRegistKmuVO.setPostPaidAmt(0);
            }
            else if("11".equals(saleRegistKmuVO.getSaleGubunCombo())) {
                saleRegistKmuVO.setCashAmt(0);
                saleRegistKmuVO.setCardAmt(0);
                saleRegistKmuVO.setPostPaidAmt(amt);
            }

            if(saleRegistKmuVO.getCashPer() == 0.0 && saleRegistKmuVO.getCardPer() == 0.0 && saleRegistKmuVO.getPostPaidPer() == 0.0){
                saleRegistKmuVO.setCashPer((float)saleRegistKmuVO.getCashAmt()/(saleRegistKmuVO.getCashAmt() + saleRegistKmuVO.getCardAmt() + saleRegistKmuVO.getPostPaidAmt()));
                saleRegistKmuVO.setCardPer((float)saleRegistKmuVO.getCardAmt()/(saleRegistKmuVO.getCashAmt() + saleRegistKmuVO.getCardAmt() + saleRegistKmuVO.getPostPaidAmt()));
                saleRegistKmuVO.setPostPaidPer((float)saleRegistKmuVO.getPostPaidAmt()/(saleRegistKmuVO.getCashAmt() + saleRegistKmuVO.getCardAmt() + saleRegistKmuVO.getPostPaidAmt()));
            }

            if(saleRegistKmuVO.getSaleFg().equals("1")){
                saleRegistKmuVO.setSaleYn("Y");
            } else if(saleRegistKmuVO.getSaleFg().equals("-1")){
                saleRegistKmuVO.setSaleYn("N");
            }

            // TB_SL_SALE_DTL
            saleRegistKmuMapper.getSaleDtl(saleRegistKmuVO);

            if(saleRegistKmuVO.getRealSaleAmt() > 0){

                //  TB_SL_SALE_DTL_PAY
                if(saleRegistKmuVO.getCashAmt() > 0){
                    saleRegistKmuVO.setPayCd("02");
                    saleRegistKmuMapper.getSaleDtlPay(saleRegistKmuVO);
                }
                if(saleRegistKmuVO.getCardAmt() > 0){
                    saleRegistKmuVO.setPayCd("01");
                    saleRegistKmuMapper.getSaleDtlPay(saleRegistKmuVO);
                }
                if(saleRegistKmuVO.getPostPaidAmt() > 0) {
                    saleRegistKmuVO.setPayCd("11");
                    saleRegistKmuMapper.getSaleDtlPay(saleRegistKmuVO);
                }

            }

            if(saleRegistKmuVO.getDcAmt() > 0){
                //  TB_SL_SALE_DTL_DC
                saleRegistKmuMapper.getSaleDtlDc(saleRegistKmuVO);
            }

            billDtlNo++;
        }
        return 0;
    }

    /** 매출수기등록 특정 전표 조회 */
    @Override
    public List<DefaultMap<String>> getBillDtlList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKmuMapper.getBillDtlList(saleRegistKmuVO);
    }

    @Override
    public List<DefaultMap<String>> getCashAmt(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKmuMapper.getCashAmt(saleRegistKmuVO);
    }

    @Override
    public String getSaleFg(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {
        return saleRegistKmuMapper.getSaleFg(saleRegistKmuVO);
    }

    @Override
    public int getBillDel(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        saleRegistKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        saleRegistKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleRegistKmuVO.setRegDt(currentDt);
        saleRegistKmuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
        saleRegistKmuVO.setModDt(currentDt);
        saleRegistKmuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

        // TB_SL_SALE_HDR_MEMBR
        saleRegistKmuMapper.delSaleHdrMembr(saleRegistKmuVO);

        // TB_SL_SALE_HDR
        saleRegistKmuMapper.delSaleHdr(saleRegistKmuVO);

        // TB_MB_MEMBER_POSTPAID
        saleRegistKmuMapper.delMemberPostpaid(saleRegistKmuVO);

        // TB_MB_MEMBER_PAID_BALANCE
        saleRegistKmuMapper.delMemberPaidBalance(saleRegistKmuVO);

        return 0;
    }

    /** 회원선택 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleRegistKmuMemberList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {

        saleRegistKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        return saleRegistKmuMapper.getSaleRegistKmuMemberList(saleRegistKmuVO);
    }

    /** 매출전표등록(일반) - 삭제 */
    @Override
    public int getNewRegistDel(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        saleRegistKmuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        saleRegistKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleRegistKmuVO.setRegDt(currentDt);
        saleRegistKmuVO.setRegId("WEB_REG_" + sessionInfoVO.getUserId());
        saleRegistKmuVO.setModDt(currentDt);
        saleRegistKmuVO.setModId("WEB_REG_" + sessionInfoVO.getUserId());

        // TB_SL_SALE_HDR_MEMBR
        saleRegistKmuMapper.delSaleHdrMembr(saleRegistKmuVO);

        // TB_SL_SALE_HDR
        saleRegistKmuMapper.delSaleHdr(saleRegistKmuVO);

        // TB_MB_MEMBER_POSTPAID
        saleRegistKmuMapper.delMemberPostpaid(saleRegistKmuVO);

        // TB_MB_MEMBER_PAID_BALANCE
        saleRegistKmuMapper.delMemberPaidBalance(saleRegistKmuVO);

        return 0;
    }

}