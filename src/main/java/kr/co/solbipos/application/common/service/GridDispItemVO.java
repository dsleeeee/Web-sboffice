package kr.co.solbipos.application.common.service;

/**
 * @Class Name : GridDispItemVO.java
 * @Description : 화면 내에 그리드 컬럼 레이아웃 조회에 사용 <br>컬럼 순서 및 표시 유무 포함
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class GridDispItemVO extends CmmVO {
    
    private static final long serialVersionUID = 3155803237408563801L;
    /** 사용자 아이디 */
    private String userId;
    /** 리소스 코드 */
    private String resrceCd;
    /** 그리드 인덱스 */
    private Long gridIdx;
    /** 컬럼 항목 */
    private String columnItem;
    
    
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the resrceCd
     */
    public String getResrceCd() {
        return resrceCd;
    }
    /**
     * @param resrceCd the resrceCd to set
     */
    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }
    /**
     * @return the gridIdx
     */
    public Long getGridIdx() {
        return gridIdx;
    }
    /**
     * @param gridIdx the gridIdx to set
     */
    public void setGridIdx(Long gridIdx) {
        this.gridIdx = gridIdx;
    }
    /**
     * @return the columnItem
     */
    public String getColumnItem() {
        return columnItem;
    }
    /**
     * @param columnItem the columnItem to set
     */
    public void setColumnItem(String columnItem) {
        this.columnItem = columnItem;
    }
    
}
