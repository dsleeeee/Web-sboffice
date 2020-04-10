package kr.co.solbipos.sale.status.item.item.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.item.item.service.ItemItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ItemItemMapper {

    /** 매출항목표시 리스트 조회(본사)*/
	List<DefaultMap<String>> getHqItemList(ItemItemVO itemItemVO);

	/** 매출항목표시 리스트 조회(매장)*/
	List<DefaultMap<String>> getMsItemList(ItemItemVO itemItemVO);

	/** 매출항목표시 정보 수정(본사)*/
    int saveHqItemInfo(ItemItemVO itemItemVO);

    /** 매출항목표시 정보 수정(매장)*/
    int saveMsItemInfo(ItemItemVO itemItemVO);


}
