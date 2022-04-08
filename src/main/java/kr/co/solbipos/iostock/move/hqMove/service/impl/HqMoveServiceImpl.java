package kr.co.solbipos.iostock.move.hqMove.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.List;

import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.hqMove.service.HqMoveService;
import kr.co.solbipos.iostock.move.hqMove.service.HqMoveVO;

@Service("hqMoveService")
@Transactional
public class HqMoveServiceImpl implements HqMoveService {
    private final HqMoveMapper hqMoveMapper;
    private final MessageService messageService;

    @Autowired
    public HqMoveServiceImpl(HqMoveMapper hqMoveMapper, MessageService messageService) {
        this.hqMoveMapper = hqMoveMapper;
        this.messageService = messageService;
    }

    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqMoveList(HqMoveVO hqMoveVO, SessionInfoVO sessionInfoVO) {

        hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return hqMoveMapper.getHqMoveList(hqMoveVO);
    }

    /** 매장이동관리 - 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(HqMoveVO hqMoveVO, SessionInfoVO sessionInfoVO) {
        hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return hqMoveMapper.getSlipNoInfo(hqMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqMoveDtlList(HqMoveVO hqMoveVO, SessionInfoVO sessionInfoVO) {
        hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return hqMoveMapper.getHqMoveDtlList(hqMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상세 리스트 저장 */
    @Override
    public int saveHqMoveDtl(HqMoveVO[] hqMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        HqMoveVO hqStoreMoveHdVO = new HqMoveVO();

        for (HqMoveVO hqMoveVO : hqMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(hqMoveVO.getConfirmFg());

                hqStoreMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqStoreMoveHdVO.setSlipNo(hqMoveVO.getSlipNo());
                hqStoreMoveHdVO.setDlvrFg(hqMoveVO.getDlvrFg());
                hqStoreMoveHdVO.setRemark(hqMoveVO.getRemark());
                hqStoreMoveHdVO.setRegFg(sessionInfoVO.getOrgnFg());
                hqStoreMoveHdVO.setRegId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setRegDt(currentDt);
                hqStoreMoveHdVO.setModId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (hqMoveVO.getOutEtcQty() == null ? 0 : hqMoveVO.getOutEtcQty());
            int outUnitQty = (hqMoveVO.getOutUnitQty() == null ? 0 : hqMoveVO.getOutUnitQty());

            hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqMoveVO.setOutUnitQty(outUnitQty);
            hqMoveVO.setOutEtcQty(outEtcQty);
            hqMoveVO.setInUnitQty(outUnitQty);
            hqMoveVO.setInEtcQty(outEtcQty);
            hqMoveVO.setInTotQty(hqMoveVO.getOutTotQty());
            hqMoveVO.setRegId(sessionInfoVO.getUserId());
            hqMoveVO.setRegDt(currentDt);
            hqMoveVO.setModId(sessionInfoVO.getUserId());
            hqMoveVO.setModDt(currentDt);

            hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // DTL 수정
            result = hqMoveMapper.updateHqMoveDtl(hqMoveVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            
            // 본사확정인 경우
            if(confirmFg.equals("Y")) {
	        	//이입
            	hqMoveVO.setStorageCd(hqMoveVO.getInStorageCd());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
                    hqMoveVO.setOccrFg("42");
                } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    hqMoveVO.setOccrFg("52");
                }
            	hqMoveVO.setConfirmFg("Y");
	            result = hqMoveMapper.insertHqStoreOutstockProd(hqMoveVO);
	            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
	            //이출
	            hqMoveVO.setStorageCd(hqMoveVO.getOutStorageCd());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
                    hqMoveVO.setOccrFg("41");
                } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    hqMoveVO.setOccrFg("51");
                }
	            hqMoveVO.setInUnitQty(hqMoveVO.getOutUnitQty());
	            hqMoveVO.setInEtcQty(hqMoveVO.getOutEtcQty());
	            hqMoveVO.setInTotQty(hqMoveVO.getOutTotQty());
	            hqMoveVO.setInAmt(hqMoveVO.getOutAmt());
	            hqMoveVO.setInVat(hqMoveVO.getOutVat());
	            hqMoveVO.setInTot(hqMoveVO.getOutTot());
	            result = hqMoveMapper.insertHqStoreOutstockProd(hqMoveVO);
	            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            }
            
            returnResult += result;
            i++;
        }

        hqStoreMoveHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqStoreMoveHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // HD 수정
        result = hqMoveMapper.updateHqMoveHd(hqStoreMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // 확정인 경우
        if(confirmFg.equals("Y")) {
            // 출고, 반품에 사용할 전표번호 생성
            String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            HqMoveVO iostockNewSlipNoVO = new HqMoveVO();
            iostockNewSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            iostockNewSlipNoVO.setYymm(yymm);

            iostockNewSlipNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                iostockNewSlipNoVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            String iostockNewSlipNo = hqMoveMapper.getIostockNewSlipNo(iostockNewSlipNoVO);

            String outSlipNo = iostockNewSlipNo;
            String inSlipNo = String.valueOf(Long.parseLong(iostockNewSlipNo) + 1);

            hqStoreMoveHdVO.setProcFg("3");
            hqStoreMoveHdVO.setOutSlipNo(outSlipNo);
            hqStoreMoveHdVO.setInSlipNo(inSlipNo);

            hqStoreMoveHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                hqStoreMoveHdVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // HD의 수정
            result = hqMoveMapper.updateHqMoveConfirm(hqStoreMoveHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 매장이동관리 - 매장이동관리 상세 삭제 */
    @Override
    public int deleteHqMoveDtl(HqMoveVO hqMoveVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // DTL 삭제
        result = hqMoveMapper.deleteAllHqMoveDtl(hqMoveVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // HD 삭제
        result = hqMoveMapper.deleteHqMoveHd(hqMoveVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqMoveRegistList(HqMoveVO hqMoveVO, SessionInfoVO sessionInfoVO) {

        hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return hqMoveMapper.getHqMoveRegistList(hqMoveVO);
    }


    /** 매장이동관리 - 매장이동관리 신규등록 리스트 저장 */
    @Override
    public int saveHqMoveRegist(HqMoveVO[] hqMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        // 전표번호 생성
        String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
        HqMoveVO newSlipNoVO = new HqMoveVO();
        newSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        newSlipNoVO.setYymm(yymm);

        newSlipNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            newSlipNoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        String newSlipNo = hqMoveMapper.getNewSlipNo(newSlipNoVO);

        HqMoveVO hqStoreMoveHdVO = new HqMoveVO();

        for (HqMoveVO hqMoveVO : hqMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(hqMoveVO.getConfirmFg());

                hqStoreMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqStoreMoveHdVO.setSlipNo(newSlipNo);
                hqStoreMoveHdVO.setMoveDate(hqMoveVO.getMoveDate());
                hqStoreMoveHdVO.setOutStorageCd(hqMoveVO.getOutStorageCd());
                hqStoreMoveHdVO.setInStorageCd(hqMoveVO.getInStorageCd());
                hqStoreMoveHdVO.setProcFg("0");
                hqStoreMoveHdVO.setDlvrFg(hqMoveVO.getDlvrFg());
                hqStoreMoveHdVO.setRemark(hqMoveVO.getRemark());
                hqStoreMoveHdVO.setRegFg(sessionInfoVO.getOrgnFg());
                hqStoreMoveHdVO.setRegId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setRegDt(currentDt);
                hqStoreMoveHdVO.setModId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setModDt(currentDt);
            }
            // 단위수량과 낱개수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outUnitQty = (hqMoveVO.getOutUnitQty() == null ? 0 : hqMoveVO.getOutUnitQty());
            int outEtcQty = (hqMoveVO.getOutEtcQty() == null ? 0 : hqMoveVO.getOutEtcQty());

            hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqMoveVO.setSlipNo(newSlipNo);
            hqMoveVO.setOutUnitQty(outUnitQty);
            hqMoveVO.setOutEtcQty(outEtcQty);
            hqMoveVO.setInUnitQty(outUnitQty);
            hqMoveVO.setInEtcQty(outEtcQty);
            hqMoveVO.setInTotQty(hqMoveVO.getOutTotQty());
            hqMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            hqMoveVO.setRegId(sessionInfoVO.getUserId());
            hqMoveVO.setRegDt(currentDt);
            hqMoveVO.setModId(sessionInfoVO.getUserId());
            hqMoveVO.setModDt(currentDt);

            hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // DTL 등록
            result = hqMoveMapper.insertHqMoveDtl(hqMoveVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            
            // 본사확정인 경우
            if(confirmFg.equals("Y")) {
	        	//이입
            	hqMoveVO.setStorageCd(hqMoveVO.getInStorageCd());
            	if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
                    hqMoveVO.setOccrFg("42");
                } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    hqMoveVO.setOccrFg("51");
                }
	            result = hqMoveMapper.insertHqStoreOutstockProd(hqMoveVO);
	            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
	            //이출
	            hqMoveVO.setStorageCd(hqMoveVO.getOutStorageCd());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
                    hqMoveVO.setOccrFg("41");
                } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    hqMoveVO.setOccrFg("52");
                }
	            hqMoveVO.setInUnitQty(hqMoveVO.getOutUnitQty());
	            hqMoveVO.setInEtcQty(hqMoveVO.getOutEtcQty());
	            hqMoveVO.setInTotQty(hqMoveVO.getOutTotQty());
	            hqMoveVO.setInAmt(hqMoveVO.getOutAmt());
	            hqMoveVO.setInVat(hqMoveVO.getOutVat());
	            hqMoveVO.setInTot(hqMoveVO.getOutTot());
	            result = hqMoveMapper.insertHqStoreOutstockProd(hqMoveVO);
	            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            }
            
            returnResult += result;
            i++;
        }

        hqStoreMoveHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqStoreMoveHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // HD 등록
        result = hqMoveMapper.insertHqMoveHd(hqStoreMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // 확정인 경우
        if(confirmFg.equals("Y")) {
            // 출고, 반품에 사용할 전표번호 생성
            HqMoveVO iostockNewSlipNoVO = new HqMoveVO();
            iostockNewSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            iostockNewSlipNoVO.setYymm(yymm);

            iostockNewSlipNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                iostockNewSlipNoVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            String iostockNewSlipNo = hqMoveMapper.getIostockNewSlipNo(iostockNewSlipNoVO);

            String outSlipNo = iostockNewSlipNo;
            String inSlipNo = String.valueOf(Long.parseLong(iostockNewSlipNo) + 1);

            hqStoreMoveHdVO.setProcFg("3");
            hqStoreMoveHdVO.setOutSlipNo(outSlipNo);
            hqStoreMoveHdVO.setInSlipNo(inSlipNo);

            // HD의 수정
            result = hqMoveMapper.updateHqMoveConfirm(hqStoreMoveHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }

    /** 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqMoveAddProdList(HqMoveVO hqMoveVO, SessionInfoVO sessionInfoVO) {

        hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return hqMoveMapper.getHqMoveRegistList(hqMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 저장 */
    @Override
    public int saveHqMoveAddProd(HqMoveVO[] hqMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqMoveVO hqStoreMoveHdVO = new HqMoveVO();

        for (HqMoveVO hqMoveVO : hqMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                hqStoreMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqStoreMoveHdVO.setSlipNo(hqMoveVO.getSlipNo());
                hqStoreMoveHdVO.setRegId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setRegDt(currentDt);
                hqStoreMoveHdVO.setModId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (hqMoveVO.getOutEtcQty() == null ? 0 : hqMoveVO.getOutEtcQty());

            hqMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqMoveVO.setOutEtcQty(outEtcQty);
            hqMoveVO.setInUnitQty(hqMoveVO.getOutUnitQty());
            hqMoveVO.setInEtcQty(outEtcQty);
            hqMoveVO.setInTotQty(hqMoveVO.getOutTotQty());
            hqMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            hqMoveVO.setRegId(sessionInfoVO.getUserId());
            hqMoveVO.setRegDt(currentDt);
            hqMoveVO.setModId(sessionInfoVO.getUserId());
            hqMoveVO.setModDt(currentDt);

            hqMoveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                hqMoveVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // DTL 등록
            result = hqMoveMapper.insertHqMoveDtl(hqMoveVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        hqStoreMoveHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqStoreMoveHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // HD 수정
        result = hqMoveMapper.updateHqMoveAddProdHd(hqStoreMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
