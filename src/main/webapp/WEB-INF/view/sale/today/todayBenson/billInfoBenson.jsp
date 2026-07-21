<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="wjBillInfoBensonLayer" control="wjBillInfoBensonLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:396px;">
    <div id="empCardLayer" class="wj-dialog wj-dialog-columns" ng-controller="billInfoBensonCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="todayBenson.billInfo"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body" style="height: 600px; overflow-y: auto; overflow-x: hidden;">
            <%-- 영수증 실물 --%>
            <div id="billInfoBensonPrintArea" ng-bind-html="printData"></div>
        </div>
    </div>
</wj-popup>

<style>
    /* 영수증 출력데이터(PRINT_DATA)는 원본 POS 출력폭(294~336px) 기준 고정폭 폰트로 만들어져 있어
       줄바꿈 없이 그대로 렌더링해야 구분선/컬럼 정렬이 깨지지 않는다. */
    #billInfoBensonPrintArea {
        width: 336px;
        margin: 0 auto;
    }
    #billInfoBensonPrintArea pre {
        margin: 0;
        white-space: pre;
        overflow-x: hidden;
        font-family: 'Courier New', Consolas, monospace;
        font-size: 12px;
        line-height: 1.4;
    }
    /* posTemplate.js의 FONT_HV2X 와 동일한 규칙 : font-size를 2배로 키워 가로/세로를 함께 2배 확대 */
    #billInfoBensonPrintArea .HV2X {
        font-size: 24px;
    }
</style>

<script type="text/javascript" src="/resource/solbipos/js/sale/today/todayBenson/billInfoBenson.js?ver=20260716.01" charset="utf-8"></script>
