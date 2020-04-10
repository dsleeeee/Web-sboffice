package kr.co.solbipos.iostock.frnchs.unusual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.unusual.service.UnusualService;
import kr.co.solbipos.iostock.frnchs.unusual.service.UnusualVO;

@Service("unusualService")
public class UnusualServiceImpl implements UnusualService {
    private final UnusualMapper unusualMapper;
    private final MessageService messageService;

    @Autowired
    public UnusualServiceImpl(UnusualMapper unusualMapper, MessageService messageService) {
        this.unusualMapper = unusualMapper;
        this.messageService = messageService;
    }

    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getUnusualList(UnusualVO unusualVO, SessionInfoVO sessionInfoVO) {
        unusualVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//        unusualVO.setStoreCd(sessionInfoVO.getStoreCd());
        unusualVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        
//        System.out.println("unusualVO.getOrgnFg :: "+unusualVO.getOrgnFg());
        
        if(!StringUtil.getOrBlank(unusualVO.getVendrCd()).equals("")) {
            unusualVO.setArrVendrCd(unusualVO.getVendrCd().split(","));
        }
         
//        System.out.println("unusualVO.getStoreCd :: "+unusualVO.getStoreCd());
        if(!StringUtil.getOrBlank(unusualVO.getStoreCd()).equals("")) {
        	unusualVO.setArrStoreCd(unusualVO.getStoreCd().split(","));
        }
//        System.out.println("unusualVO.getArrStoreCd :: "+unusualVO.getArrStoreCd());
        return unusualMapper.getUnusualList(unusualVO);
    }
}
