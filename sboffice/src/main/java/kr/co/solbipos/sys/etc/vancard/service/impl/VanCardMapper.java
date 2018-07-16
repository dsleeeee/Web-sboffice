package kr.co.solbipos.sys.etc.vancard.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.etc.vancard.service.CardCmpnyVO;
import kr.co.solbipos.sys.etc.vancard.service.VanCardVO;
import kr.co.solbipos.sys.etc.vancard.service.VanCmpnyVO;

/**
 * @Class Name : VanCardMapper.java
 * @Description : 시스템관리 > VAN/CARD사 관리
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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface VanCardMapper {
    
    /** VAN사 목록 조회 */
    List<DefaultMap<String>> getVanCmpnyList(VanCmpnyVO vanCmpnyVO);
    
    /** VAN사 목록 생성 */
    int insertVanCmpnyList(VanCmpnyVO vanCmpnyVO);
    
    /** VAN사 목록 수정 */
    int updateVanCmpnyList(VanCmpnyVO vanCmpnyVO);
    
    /** CARD사 목록 조회 */
    List<DefaultMap<String>> getCardCmpnyList(CardCmpnyVO cardCmpnyVO);
    
    /** CARD사 목록 생성 */
    int insertCardCmpnyList(CardCmpnyVO cardCmpnyVO);
    
    /** CARD사 목록 수정 */
    int updateCardCmpnyList(CardCmpnyVO cardCmpnyVO);
    
    /** VAN사 CARD사 매핑 목록 조회 */
    List<DefaultMap<String>> getMapngList(VanCardVO vanCardVO);
    
    /** VAN사 CARD사 매핑 목록 생성 */
    int insertMapngList(VanCardVO vanCardVO);
    
    /** VAN사 CARD사 매핑 목록 수정 */
    int updateMapngList(VanCardVO vanCardVO);
    
}
