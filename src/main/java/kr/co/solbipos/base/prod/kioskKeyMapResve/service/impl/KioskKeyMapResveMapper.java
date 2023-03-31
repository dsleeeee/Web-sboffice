package kr.co.solbipos.base.prod.kioskKeyMapResve.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.kioskKeyMapResve.service.KioskKeyMapResveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KioskKeyMapResveMapper {

    /** 리스트 조회 */
    List<DefaultMap<Object>> getKioskKeyMapResveList(KioskKeyMapResveVO kioskKeyMapResveVO);
    List<DefaultMap<Object>> getKioskKeyMapResveAddList(KioskKeyMapResveVO kioskKeyMapResveVO);

    /** 키오스크키맵설정(예약) 추가 */
    int saveKioskKeyMapResve(KioskKeyMapResveVO kioskKeyMapResveVO);

    /** 키오스크키맵설정(예약) 삭제 */
    int deleteKioskKeyMapResve(KioskKeyMapResveVO kioskKeyMapResveVO);
}