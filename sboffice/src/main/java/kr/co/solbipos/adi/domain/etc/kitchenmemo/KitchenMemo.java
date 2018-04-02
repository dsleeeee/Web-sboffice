package kr.co.solbipos.adi.domain.etc.kitchenmemo;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.solbipos.application.domain.cmm.Cmm;
import kr.co.solbipos.application.validate.login.Login;
import kr.co.solbipos.application.validate.user.PwChange;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KitchenMemo extends Cmm{
    
    /** 매장코드 */
    private String storeCd;
    
    /** 주방메모코드 */
    @NotBlank
    @Size(   min = 2, max = 3, message = "{msg.cmm.size.max}")
    private String kitchnMemoCd;
    
    /** 주방메모명 */
    @NotBlank
    @Size(groups = {PwChange.class}, min = 1, max = 50, message = "{msg.cmm.size.max}")
    private String kitchnMemoNm;
    
    /** 메모구분  */
    @NotBlank
    @Size( groups={ Login.class }, max = 1, message = "{msg.cmm.size.max}" )
    private String memoFg;
    
    /** 사용여부  */    
    @NotBlank
    private String useYn;
    
    /** 등록일시 */
    private String regDt;
    
    /** 등록아이디 */
    private String regId;
    
    /** 수정일시 */
    private String modDt;
    
    /** 수정아이디 */
    private String modId;
    
    /** 상태 */
    private String status;
    

}
