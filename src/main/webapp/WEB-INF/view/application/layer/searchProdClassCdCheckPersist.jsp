<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="prodClassCheckPersistPopUpLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;" onload="window.self.focus();">
    <div class="wj-dialog wj-dialog-columns" ng-controller="prodClassCheckPersistPopUpCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.layer.prodClass"/>
            <span class="s13">
                (체크유지)
            </span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 300px;">
            <%-- 상품분류 트리 --%>
            <div class="theTreeAll_cls" id="treeProdClassCheckPersist" style="height:auto;overflow: hidden; "></div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue" id="btnSelectCheckPersist"><s:message code="cmm.chk"/></button>
            <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/application/layer/searchProdClassCdCheckPersist.js?ver=20250703.01" charset="utf-8"></script>

<%--
    분류모듈v3(체크박스 체크유지)
    1. 대/중/소 코드 모두 조회처리
    2. 하위 코드가 모두 풀려도 상위코드 체크유지 처리
        1. 하위가 있는경우: 클릭-전체선택/클릭-하위전체풀림/클릭-자기자신풀림
--%>