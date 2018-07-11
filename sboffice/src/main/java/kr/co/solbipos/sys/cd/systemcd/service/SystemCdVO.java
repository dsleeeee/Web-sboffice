package kr.co.solbipos.sys.cd.systemcd.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : SystemCdVO.java
 * @Description : 시스템관리 > 코드관리 > 시스템 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemCdVO extends CmmVO {

   private static final long serialVersionUID = -479310152661794262L;
   
   /** 명칭코드그룹코드 */
   private String nmcodeGrpCd;
   
   /** 명칭코드코드 */
   private String nmcodeCd;
   
   /** 명칭코드명*/
   private String nmcodeNm;
   
   /** 명칭코드항목1 */
   private String nmcodeItem1;
   
   /** 명칭코드항목2 */
   private String nmcodeItem2;
   
   /** 사용컬럼명 */
   private String useColNm;
   
}
