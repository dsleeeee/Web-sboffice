<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="clsTypeNmLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="clsTypeNmCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <h3 class="" style="line-height:50px;"><s:message code="kioskKeyMap.tuClsTypeNm"/></h3>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="mt10 mr30 tr">
            <button class="btn_blue" ng-click="excelDownload()">
                <s:message code="cmm.excel.down" />
            </button>
        </div>
        <div class="wj-dialog-body">
            <div class="wj-gridWrap" style="height:400px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.posNo"/>"         binding="posNo" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsType"/>"     binding="tuClsType" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.tuClsTypeNm"/>"   binding="tuClsTypeNm" width="200" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn_blue" ng-click="saveClsTypeNm()"><s:message code="cmm.apply" /></button>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapClsTypeNm.js?ver=20221228.01" charset="utf-8"></script>
