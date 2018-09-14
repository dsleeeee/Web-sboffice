<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/application/pos/excpForward/" />

<div class="subCon">

    <%-- 예외출고 상품 목록 --%>
    <div class="mb40" ng-controller="productCtrl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:260px;">
            <%-- 쿠폰분류등록 그리드 --%>
            <div id="productGrid" class="wj-gridWrap" style="height:190px">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="application.pos.excpForward.storeCd"/>" binding="storeCd" width="*" isReadOnly="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodClassCd"/>" binding="prodClassCd" width="*" isReadOnly="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodClassNm"/>" binding="prodClassNm" width="*" isReadOnly="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodCd"/>" binding="prodCd" width="*" isReadOnly="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodNm"/>" binding="prodNm" width="*" isReadOnly="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="application.pos.excpForward.regProd"/>" binding="regProd" width="100" isReadOnly="true"></wj-flex-grid-column>
                </wj-flex-grid>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
  var orgnFg       = "${orgnFg}";
  var baseUrl      = "${baseUrl}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/application/pos/excpForward/excpForward.js?ver=20180914" charset="utf-8"></script>
