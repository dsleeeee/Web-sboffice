package kr.co.solbipos.sale.status.dc.dcfg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.dc.dcfg.service.DcDcfgService;
import kr.co.solbipos.sale.status.dc.dcfg.service.DcDcfgVO;

@Service("dcDcfgService")
public class DcDcfgServiceImpl implements DcDcfgService {
    private final DcDcfgMapper dcDcfgMapper;
    private final MessageService messageService;

    @Autowired
    public DcDcfgServiceImpl(DcDcfgMapper dcDcfgMapper, MessageService messageService) {
        this.dcDcfgMapper = dcDcfgMapper;
        this.messageService = messageService;
    }


    /** 할일구분별매출 - 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcDcfgList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO) {
    	dcDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

    	if(!StringUtil.getOrBlank(dcDcfgVO.getStoreCd()).equals("")) {
        	dcDcfgVO.setArrStoreCd(dcDcfgVO.getStoreCd().split(","));
        }
    	if(!StringUtil.getOrBlank(dcDcfgVO.getDcCd()).equals("")) {
        	dcDcfgVO.setArrDcCd(dcDcfgVO.getDcCd().split(","));
        }
        return dcDcfgMapper.getDcDcfgList(dcDcfgVO);
    }


    /** 할일구분별매출 - 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getDcDcfgDtlList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO) {
		dcDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(dcDcfgVO.getStoreCd()).equals("")) {
        	dcDcfgVO.setArrStoreCd(dcDcfgVO.getStoreCd().split(","));
        }
		return dcDcfgMapper.getDcDcfgDtlList(dcDcfgVO);
	}


	@Override
	public List<DefaultMap<String>> getDcNmlList(DcDcfgVO dcDcfgVO, SessionInfoVO sessionInfoVO) {
		dcDcfgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(dcDcfgVO.getStoreCd()).equals("")) {
        	dcDcfgVO.setArrStoreCd(dcDcfgVO.getStoreCd().split(","));
        }

		if(!StringUtil.getOrBlank(dcDcfgVO.getDcCd()).equals("")) {
        	dcDcfgVO.setArrDcCd(dcDcfgVO.getDcCd().split(","));
        }
		return dcDcfgMapper.getDcNmList(dcDcfgVO);
	}

}
