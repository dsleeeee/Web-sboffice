package kr.co.solbipos.application.common.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.common.data.enums.UseYn;
import kr.co.common.validate.WebMenuSave;
import kr.co.solbipos.application.common.enums.ResrceFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 리소스<br>
 * table : TB_WB_RESRCE_INFO
 *
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResrceInfoVO extends CmmVO {

    private static final long serialVersionUID = 6149780192504788158L;

    /** 리소스 코드 */
    private String resrceCd;

    /** 상위 리소스 */
    private String pResrce;

    /** 리소스 구분 */
    private ResrceFg resrceFg;

    /** 리소스 배열 : 메뉴 관리에서 기능 목록을 저장할때 사용 */
    private ResrceInfoVO[] resrceInfoArr;

    /** 기능 구분 */
    private String funcFg;

    /** 리소스 명 */
    @NotBlank(groups = {WebMenuSave.class}, message = "{webMenu.nm}{cmm.require.text}")
    @Size(groups = {WebMenuSave.class}, max = 45, message = "{webMenu.nm} {cmm.size.max}")
    private String resrceNm;

    /** URL */
    @Size(groups = {WebMenuSave.class}, max = 150, message = "URL {cmm.size.max}")
    private String url;

    /** 특수 권한 */
    private String spclAuthor;

    /** 표기 레벨 */
    private Long dispLevel;

    /** 표기 인덱스 */
    @NotNull(groups = {WebMenuSave.class}, message = "{webMenu.dispIndx}{cmm.require.text}")
    private Long dispIdx;

    /** 메뉴 아이콘명 */
    private String iconNm;

    /** 사용 여부 */
    private UseYn useYn;

    public String getpResrce() {
        return pResrce;
    }

    public void setpResrce(String pResrce) {
        this.pResrce = pResrce;
    }

    public String getPResrce() {
        return pResrce;
    }

    public void setPResrce(String pResrce) {
        this.pResrce = pResrce;
    }
}
