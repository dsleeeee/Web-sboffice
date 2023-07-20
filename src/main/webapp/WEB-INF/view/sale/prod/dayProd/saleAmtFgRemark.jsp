<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

 <wj-popup id="saleAmtFgRemarkPopupLayer" control="saleAmtFgRemarkPopupLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">

    <div ng-controller="saleAmtFgRemarkPopupCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dayProd.saleAmtFg"/>1
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body">
            상품분류: 맘스터치 > 맘스세트 의 상품 중 세트상품<br/>
            선택분류: 필수선택여부[필수] 인 구성상품<br/>
            구성상품의 매출금액은 세트상품으로 집계됨<br/>
            구성상품의 매출금액이 세트의 매출금액으로 처리 구성상품 추가/변경 시 관리자에게 알려주십시오.
            <div class="mt20 oh sb-select dkbr">
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()">
                    <s:message code="cmm.excel.down"/>
                </button>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            item-formatter="_itemFormatter"
                            is-read-only="false">

                        <wj-flex-grid-column header="<s:message code="dayProd.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayProd.sideProdNm"/>" binding="sideProdNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayProd.remark"/>" binding="sideRemark" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayProd.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayProd.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayProd.remark"/>" binding="remark" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/prod/dayProd/saleAmtFgRemark.js?ver=20230713.02" charset="utf-8"></script>