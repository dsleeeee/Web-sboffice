<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjVerDelInfoLayer" control="wjVerDelInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:250px;" fade-in="false" fade-out="false">
    <div ng-controller="verDelInfoCtrl">

        <%-- header --%>
        <%--<div class="wj-dialog-header wj-dialog-header-font">--%>
            <%--<s:message code="verDelInfo.info"/>--%>
            <%--<a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>--%>
        <%--</div>--%>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="mt10 oh bb pdb20">
                <div style="display: none;">
                    <p class="tl s14 mt5 lh15 red">프로그램구분 : <label id="lblVerDelProgFg"></label></p>
                </div>
                <p class="tl s14 mt5 lh15 red">버전일련번호 : <label id="lblVerDelVerSerNo"></label></p>
                <p class="tl s14 mt5 lh15">적용매장-등록매장수 : <label id="lblVerDelApplyStoreCnt"></label></p>
                <p class="tl s14 mt5 lh15">최근한달패치된매장수 : <label id="lblVerDelMonthStoreCnt"></label></p>
                <p class="tl s14 mt5 lh15">현재버전사용매장수 : <label id="lblVerDelUseStoreCnt"></label></p>
            </div>
            <div class="mt20 oh mb20">
                <%-- 시스템패스워드 --%>
                <div class="sb-select dkbr ml5 fl s14">
                    <s:message code="verDelInfo.systemPw" />
                    <input type="password" class="sb-input w150px" id="srchSystemPw" ng-model="systemPw" />
                </div>
            </div>
            <div class="btnSet tc">
                <s:message code="cmm.choo.delete" />&nbsp;
                <%-- 확인 --%>
                <span><a href="#" class="btn_blue" id="btnVerDelConfirm" ng-click="delete()"><s:message code="cmm.confirm" /></a></span>
                <%-- 취소 --%>
                <span><a href="#" class="btn_blue" id="btnVerDelClose" ng-click="close()"><s:message code="cmm.cancel" /></a></span>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verManage/verDelInfo.js?ver=20250617.01" charset="utf-8"></script>