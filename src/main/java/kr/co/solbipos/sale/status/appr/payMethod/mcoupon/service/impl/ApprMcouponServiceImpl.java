package kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.ApprMcouponService;
import kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.ApprMcouponVO;

@Service("apprMcouponService")
public class ApprMcouponServiceImpl implements ApprMcouponService {
    private final ApprMcouponMapper apprMcouponMapper;
    private final MessageService messageService;

    @Autowired
    public ApprMcouponServiceImpl(ApprMcouponMapper apprMcouponMapper, MessageService messageService) {
        this.apprMcouponMapper = apprMcouponMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprMcouponList(ApprMcouponVO apprMcouponVO, SessionInfoVO sessionInfoVO) {
		apprMcouponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprMcouponMapper.getApprMcouponList(apprMcouponVO);
	}
}
