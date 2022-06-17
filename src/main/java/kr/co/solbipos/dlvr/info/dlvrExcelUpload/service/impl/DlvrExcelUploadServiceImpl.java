package kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.DlvrExcelUploadService;
import kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.DlvrExcelUploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;


@Service("dlvrExcelUploadService")
@Transactional
public class DlvrExcelUploadServiceImpl implements DlvrExcelUploadService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrExcelUploadMapper dlvrExcelUploadMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DlvrExcelUploadServiceImpl(DlvrExcelUploadMapper dlvrExcelUploadMapper, MessageService messageService) {
        this.dlvrExcelUploadMapper = dlvrExcelUploadMapper;
        this.messageService = messageService;
    }

    /** 배달지엑셀업로드 임시테이블 데이터 전체 삭제 */
    @Override
    public int getDlvrExcelUploadDeleteAll(DlvrExcelUploadVO dlvrExcelUploadVO, SessionInfoVO sessionInfoVO) {
        dlvrExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        dlvrExcelUploadVO.setSessionId(sessionInfoVO.getUserId());
        return dlvrExcelUploadMapper.getDlvrExcelUploadDeleteAll(dlvrExcelUploadVO);
    }

    /** 업로드시 임시테이블 저장 */
    @Override
    public int getDlvrExcelUploadCheckSave(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(DlvrExcelUploadVO dlvrExcelUploadVO : dlvrExcelUploadVOs) {
            dlvrExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dlvrExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
            dlvrExcelUploadVO.setRegDt(currentDt);
            dlvrExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            dlvrExcelUploadVO.setModDt(currentDt);
            dlvrExcelUploadVO.setModId(sessionInfoVO.getUserId());

            dlvrExcelUploadVO.setSessionId(sessionInfoVO.getUserId());
            dlvrExcelUploadVO.setSeq(i);

            dlvrExcelUploadVO.setResult("검증전");

            if(dlvrExcelUploadVO.getDlvrFg() != null && !"".equals(dlvrExcelUploadVO.getDlvrFg())) {

                DefaultMap<String> dlvrFgMap = new DefaultMap<>();
                dlvrFgMap.put("CALL", "1");
                dlvrFgMap.put("조리", "2");
                dlvrFgMap.put("배달", "3");
                dlvrFgMap.put("완료", "4");
                dlvrFgMap.put("취소", "5");

                String dlvrFg = (String)dlvrFgMap.get(dlvrExcelUploadVO.getDlvrFg().toLowerCase());
                dlvrExcelUploadVO.setDlvrFg(dlvrFg);

            }

            resultCnt += dlvrExcelUploadMapper.getDlvrExcelUploadCheckSave(dlvrExcelUploadVO);
            i++;
        }

        return resultCnt;
    }

    /** 임시테이블 조회 */
    @Override
    public List<DefaultMap<Object>> getDlvrExcelUploadCheckList(DlvrExcelUploadVO dlvrExcelUploadVO, SessionInfoVO sessionInfoVO) {

        dlvrExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());

        dlvrExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

        return dlvrExcelUploadMapper.getDlvrExcelUploadCheckList(dlvrExcelUploadVO);
    }

    /** 배달지엑셀업로드 임시테이블 데이터 삭제 */
    @Override
    public int getDlvrExcelUploadCheckDelete(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;

        for(DlvrExcelUploadVO dlvrExcelUploadVO : dlvrExcelUploadVOs) {

            if (dlvrExcelUploadVO.getStatus() == GridDataFg.DELETE) {

                dlvrExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                dlvrExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
                dlvrExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

                // 검증결과 삭제
                resultCnt += dlvrExcelUploadMapper.getDlvrExcelUploadCheckDelete(dlvrExcelUploadVO);
            }
        }

        return resultCnt;
    }

    @Override
    public int getDlvrExcelUploadCheckDelete2(DlvrExcelUploadVO dlvrExcelUploadVO, SessionInfoVO sessionInfoVO) {

        dlvrExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        dlvrExcelUploadVO.setSessionId(sessionInfoVO.getUserId());
        dlvrExcelUploadVO.setSeq(0);
        dlvrExcelUploadVO.setDeleteFg("검증성공");
        return dlvrExcelUploadMapper.getDlvrExcelUploadCheckDelete(dlvrExcelUploadVO);
    }

    /** 검증결과 저장 */
    @Override
    public int getDlvrExcelUploadCheckSaveAdd(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;
        String currentDt = currentDateTimeString();

        for(DlvrExcelUploadVO dlvrExcelUploadVO : dlvrExcelUploadVOs) {

            dlvrExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dlvrExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
            dlvrExcelUploadVO.setRegDt(currentDt);
            dlvrExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            dlvrExcelUploadVO.setModDt(currentDt);
            dlvrExcelUploadVO.setModId(sessionInfoVO.getUserId());

            dlvrExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

            if (dlvrExcelUploadVO.getResult() == null || dlvrExcelUploadVO.getResult() == "") {
                dlvrExcelUploadVO.setResult("검증성공");
            }

            resultCnt += dlvrExcelUploadMapper.getDlvrExcelUploadCheckSave(dlvrExcelUploadVO);

        }
        return resultCnt;
    }

    /** 검증결과 저장 */
    @Override
    public int getDeliveryTelNoSave(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;
        String currentDt = currentDateTimeString();

        for(DlvrExcelUploadVO dlvrExcelUploadVO : dlvrExcelUploadVOs) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date now = new Date();
            Calendar cal = Calendar.getInstance();

            //  saleDate값과 cidCallSeq값 가져오기
            cal.setTime(now);
            cal.add(Calendar.DATE, -1);
            dlvrExcelUploadVO.setSaleDate(dateFormat.format(cal.getTime()));
            dlvrExcelUploadVO.setCidCallSeq(dlvrExcelUploadMapper.getCidCallSeq(dlvrExcelUploadVO));

            if(Integer.parseInt(dlvrExcelUploadVO.getCidCallSeq()) >= 100000){
                cal.setTime(now);
                cal.add(Calendar.DATE, -2);
                dlvrExcelUploadVO.setSaleDate(dateFormat.format(cal.getTime()));
                dlvrExcelUploadVO.setCidCallSeq(dlvrExcelUploadMapper.getCidCallSeq(dlvrExcelUploadVO));

            }

            dlvrExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dlvrExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
            dlvrExcelUploadVO.setRegDt(currentDt);
            dlvrExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            dlvrExcelUploadVO.setModDt(currentDt);
            dlvrExcelUploadVO.setModId(sessionInfoVO.getUserId());

            dlvrExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

            if (dlvrExcelUploadVO.getResult() != null && dlvrExcelUploadVO.getResult().equals("검증성공")) {
                resultCnt += dlvrExcelUploadMapper.getDeliveryTelNoSave(dlvrExcelUploadVO);
            }

        }

        return resultCnt;
    }
}
