package kr.co.solbipos.base.store.tableattr.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.tableattr.service.TableAttrVO;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : TableAttrServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 테이블속성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TableAttrMapper {

    /**
     * storeCd, confgFg 로 매장 테이블 속성 조회
     * @param param
     * @return
     */
    String selectXmlByStore(DefaultMap<String> param);

    /**
     * 매장 테이블 속성 DB 값이 없을 경우 Default 설정 조회
     * @param param
     * @return
     */
    List<TableAttrVO> selectDefaultXml();

    /**
     * 매장 환경설정 XML 값을 insert
     * @param param
     * @return
     */
    int insertStoreConfgXml(DefaultMap<String> param);

    /**
     * 매장 환경설정 XML 값을 update
     * @param param
     * @return
     */
    int updateStoreConfgXml(DefaultMap<String> param);

    /**
     * 매장 테이블 속성 저장(Merge)
     * @param tableAttrVO
     * @return
     */
    int mergeStoreTableAttr(TableAttrVO tableAttrVO);
}
