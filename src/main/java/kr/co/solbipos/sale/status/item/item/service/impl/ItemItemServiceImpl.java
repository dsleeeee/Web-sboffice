package kr.co.solbipos.sale.status.item.item.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.gift.service.GiftVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.sale.status.item.item.service.ItemItemService;
import kr.co.solbipos.sale.status.item.item.service.ItemItemVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ItemItemServiceImpl.java
 * @Description : 매출관리 > 매출현황 > 매출항목표시
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.26  이승규      최초생성
 *
 * @author 엠투엠글로벌 이승규
 * @since 2020.03.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("itemItemService")
@Transactional
public class ItemItemServiceImpl implements ItemItemService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    ItemItemMapper itemItemMapper;
    MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public ItemItemServiceImpl(ItemItemMapper itemItemMapper, MessageService messageService) {
        this.itemItemMapper = itemItemMapper;
        this.messageService = messageService;
    }


    /** 매출항목표시 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getItemList(ItemItemVO itemItemVO, SessionInfoVO sessionInfoVO) {

    	itemItemVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	itemItemVO.setStoreCd(sessionInfoVO.getStoreCd());

        if (itemItemVO.getOrgnFg() != null && "HQ".equals(itemItemVO.getOrgnFg())) {
        	return itemItemMapper.getHqItemList(itemItemVO);
        } else if (itemItemVO.getOrgnFg() != null && "STORE".equals(itemItemVO.getOrgnFg())) {
        	return itemItemMapper.getMsItemList(itemItemVO);
        }

        return null;
    }

    /** 매출항목표시 정보 수정 */
    @Override
    public EmpResult saveItemInfo(ItemItemVO[] ItemItemVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        for(ItemItemVO itemItemVO : ItemItemVOs) {

        	 //itemItemVO.setPriorPwd(itemItemMapper.getItemPassword(itemItemVO));
            itemItemVO.setRegId(sessionInfoVO.getUserId());
            itemItemVO.setRegDt(dt);
            itemItemVO.setModId(sessionInfoVO.getUserId());
            itemItemVO.setModDt(dt);

            if (itemItemVO.getOrgnFg() != null && "HQ".equals(itemItemVO.getOrgnFg())) {
            	if( itemItemMapper.saveHqItemInfo(itemItemVO) != 1 ) {
                    return EmpResult.FAIL;
                }
            } else if (itemItemVO.getOrgnFg() != null && "STORE".equals(itemItemVO.getOrgnFg())) {
            	if( itemItemMapper.saveMsItemInfo(itemItemVO) != 1 ) {
                    return EmpResult.FAIL;
                }
            }

        }

        return EmpResult.SUCCESS;
    }

}
