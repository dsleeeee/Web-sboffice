package kr.co.solbipos.base.prod.touchKeyResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.touchKeyResve.service.TouchKeyResveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TouchKeyResveMapper {

    /** 리스트 조회 */
    List<DefaultMap<Object>> getTouchKeyResveList(TouchKeyResveVO touchKeyResveVO);
    List<DefaultMap<Object>> getTouchKeyResveAddList(TouchKeyResveVO touchKeyResveVO);

    /** 판매터치키(예약) 추가 */
    int saveTouchKeyResve(TouchKeyResveVO touchKeyResveVO);

    int getTouchKeyResveCnt(TouchKeyResveVO touchKeyResveVO);

    /** 판매터치키(예약) 삭제 */
    int deleteTouchKeyResve(TouchKeyResveVO touchKeyResveVO);
}