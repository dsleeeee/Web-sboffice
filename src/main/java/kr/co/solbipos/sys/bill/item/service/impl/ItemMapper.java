package kr.co.solbipos.sys.bill.item.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.bill.item.service.ItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ItemMapper.java
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
@Mapper
@Repository
public interface ItemMapper {
    
    /** 출력코드 목록 조회 */
    List<DefaultMap<String>> getItemList(ItemVO itemVO);
    
    /** 출력코드 생성 */
    int insertItemList(ItemVO itemVO);
    
    /** 출력코드 수정 */
    int updateItemList(ItemVO itemVO);
    
}
