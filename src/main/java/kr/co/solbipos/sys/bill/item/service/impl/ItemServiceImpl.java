package kr.co.solbipos.sys.bill.item.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.bill.item.service.ItemService;
import kr.co.solbipos.sys.bill.item.service.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ItemServiceImpl.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력코드 구성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {
    
    private final ItemMapper itemMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public ItemServiceImpl(ItemMapper itemMapper, MessageService messageService) {
        this.itemMapper = itemMapper;
        this.messageService = messageService;
    }

    /** 출력코드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getItemList(ItemVO itemVO) {
        return itemMapper.getItemList(itemVO);
    }
    
    /** 출력코드 목록 저장 */
    @Override
    public int saveItemList(ItemVO[] itemVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( ItemVO itemVO : itemVOs ) {
            
            itemVO.setRegDt(currentDt);
            itemVO.setRegId(sessionInfoVO.getUserId());
            itemVO.setModDt(currentDt);
            itemVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( itemVO.getStatus() == GridDataFg.INSERT ) {
                
                result += itemMapper.insertItemList(itemVO);
            // 수정
            } else if ( itemVO.getStatus() == GridDataFg.UPDATE ) {
                
                result += itemMapper.updateItemList(itemVO);
            // 삭제
            } else if ( itemVO.getStatus() == GridDataFg.DELETE ) {
                
            }
            
        }
        
        if ( result == itemVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }

}
