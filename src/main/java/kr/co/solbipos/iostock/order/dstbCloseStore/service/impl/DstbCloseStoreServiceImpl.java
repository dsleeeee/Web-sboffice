package kr.co.solbipos.iostock.order.dstbCloseStore.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreService;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreVO;

@Service("dstbCloseStoreService")
@Transactional
public class DstbCloseStoreServiceImpl implements DstbCloseStoreService {
    private final DstbCloseStoreMapper dstbCloseStoreMapper;
    private final MessageService messageService;

    public DstbCloseStoreServiceImpl(DstbCloseStoreMapper dstbCloseStoreMapper, MessageService messageService) {
        this.dstbCloseStoreMapper = dstbCloseStoreMapper;
        this.messageService = messageService;
    }

    /** 분배마감 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseStoreList(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getDstbCloseStoreList(dstbCloseStoreVO);
    }

    /** 분배마감 리스트 확정 */
    @Override
    public int saveDstbCloseStoreConfirm(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseStoreVO dstbCloseStoreVO : dstbCloseStoreVOs) {
            dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbCloseStoreVO.setUpdateProcFg("20");
            dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setRegDt(currentDt);
            dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setModDt(currentDt);

            result = dstbCloseStoreMapper.updateDstbCloseConfirm(dstbCloseStoreVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 분배마감 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseStoreDtlList(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getDstbCloseStoreDtlList(dstbCloseStoreVO);
    }

    /** 분배마감 상세 리스트 저장 */
    @Override
    public int saveDstbCloseStoreDtl(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseStoreVO dstbCloseStoreVO : dstbCloseStoreVOs) {
            int slipFg     = dstbCloseStoreVO.getSlipFg();
            int mgrUnitQty = (dstbCloseStoreVO.getMgrUnitQty() == null ? 0 : dstbCloseStoreVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty  = (dstbCloseStoreVO.getMgrEtcQty()  == null ? 0 : dstbCloseStoreVO.getMgrEtcQty()) * slipFg;
            int mgrTotQty  = (dstbCloseStoreVO.getMgrTotQty()  == null ? 0 : dstbCloseStoreVO.getMgrTotQty()) * slipFg;
            Long mgrAmt    = (dstbCloseStoreVO.getMgrAmt()     == null ? 0 : dstbCloseStoreVO.getMgrAmt()) * slipFg;
            Long mgrVat    = (dstbCloseStoreVO.getMgrVat()     == null ? 0 : dstbCloseStoreVO.getMgrVat()) * slipFg;
            Long mgrTot    = (dstbCloseStoreVO.getMgrTot()     == null ? 0 : dstbCloseStoreVO.getMgrTot()) * slipFg;

            dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
            dstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
            dstbCloseStoreVO.setMgrTotQty(mgrTotQty);
            dstbCloseStoreVO.setMgrAmt(mgrAmt);
            dstbCloseStoreVO.setMgrVat(mgrVat);
            dstbCloseStoreVO.setMgrTot(mgrTot);
            dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setRegDt(currentDt);
            dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setModDt(currentDt);

//            // 분배수량이 0 이나 null 인 경우 삭제
//            if(dstbCloseStoreVO.getMgrTotQty() == 0 || dstbCloseStoreVO.getMgrTotQty() == null) {
//                result = dstbCloseStoreMapper.deleteDstbCloseDtl(dstbCloseStoreVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
//            else {
                result = dstbCloseStoreMapper.updateDstbCloseDtl(dstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if(dstbCloseStoreVO.getConfirmYn()) {
                    dstbCloseStoreVO.setProcFg("20");
                    result = dstbCloseStoreMapper.updateDstbCloseDtlConfirm(dstbCloseStoreVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
//            }

            returnResult += result;
        }

        return returnResult;
    }

    /** 추가등록시 주문가능여부 조회 */
    @Override
    public DefaultMap<String> getOrderFg(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getOrderFg(dstbCloseStoreVO);
    }

    /** 분배마감 - 추가등록 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbAddList(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getDstbAddList(dstbCloseStoreVO);
    }

    /** 분배마감 - 추가등록 상세 리스트 저장 */
    @Override
    public int saveDstbAdd(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseStoreVO dstbCloseStoreVO : dstbCloseStoreVOs) {
            int slipFg       = dstbCloseStoreVO.getSlipFg();
            int mgrUnitQty   = (dstbCloseStoreVO.getMgrUnitQty() == null ? 0 : dstbCloseStoreVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty    = (dstbCloseStoreVO.getMgrEtcQty()  == null ? 0 : dstbCloseStoreVO.getMgrEtcQty())  * slipFg;
            int mgrTotQty    = (dstbCloseStoreVO.getMgrTotQty()  == null ? 0 : dstbCloseStoreVO.getMgrTotQty())  * slipFg;
            Long mgrAmt      = (dstbCloseStoreVO.getMgrAmt()     == null ? 0 : dstbCloseStoreVO.getMgrAmt())     * slipFg;
            Long mgrVat      = (dstbCloseStoreVO.getMgrVat()     == null ? 0 : dstbCloseStoreVO.getMgrVat())     * slipFg;
            Long mgrTot      = (dstbCloseStoreVO.getMgrTot()     == null ? 0 : dstbCloseStoreVO.getMgrTot())     * slipFg;

            if(mgrTotQty > 0) {
                dstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
                dstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
                dstbCloseStoreVO.setMgrTotQty(mgrTotQty);
                dstbCloseStoreVO.setMgrAmt(mgrAmt);
                dstbCloseStoreVO.setMgrVat(mgrVat);
                dstbCloseStoreVO.setMgrTot(mgrTot);
                dstbCloseStoreVO.setProcFg("10");
                dstbCloseStoreVO.setDstbFg("0");
                dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
                dstbCloseStoreVO.setRegDt(currentDt);
                dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
                dstbCloseStoreVO.setModDt(currentDt);
                dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                result = dstbCloseStoreMapper.insertDstbAdd(dstbCloseStoreVO);
                if (result <= 0)
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                returnResult += result;
            }
        }

        return returnResult;
    }


    /** 분배마감 - 엑셀업로드 */
    @Override
    public int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        excelUploadVO.setRegId(sessionInfoVO.getUserId());
        excelUploadVO.setRegDt(currentDt);
        excelUploadVO.setModId(sessionInfoVO.getUserId());
        excelUploadVO.setModDt(currentDt);

        // 엑셀업로드 한 수량을 분배수량으로 입력
        result = dstbCloseStoreMapper.insertDstbToExcelUploadData(excelUploadVO);

        // 분배수량으로 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = dstbCloseStoreMapper.deleteExcelUploadCompleteData(excelUploadVO);

        return result;
    }
}
