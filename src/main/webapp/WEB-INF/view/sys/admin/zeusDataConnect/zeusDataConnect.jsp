<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="zeusDataConnectCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('zeusDataConnectCtrl', 1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <div class="w100 mt10 oh">
        <div class="w60 fl">
            <div class="mb10 oh">
                <p class="tl s14 mt5 lh15 red">※ 제우스에서 데이터 수정시 링크 반영</p>
                <p class="tl s14 mt5 lh15">1. 데이터 수정시 2시간마다 스케줄로 연동됨</p>
                <p class="tl s14 mt5 lh15">2. 즉시 데이터 수정이 필요한 경우</p>
                <p class="tl s14 mt5 lh15">&nbsp;&nbsp;- [연동신청처리] : 연동신청한 매장 처리</p>
                <p class="tl s14 mt5 lh15">&nbsp;&nbsp;- [제우스->링크 데이터연동] : 연동중인 매장의 데이터 처리</p>
            </div>
        </div>
        <div class="w40 fr">
            <%-- 사용자용 --%>
            <div class="updownSet oh mb10">
                <%-- 비밀번호 --%>
                <input type="password" class="sb-input w120px" id="srchSystemPwCall" ng-model="systemPwCall" />
                <%-- 제우스->링크 데이터연동 --%>
                <button class="btn_skyblue" ng-click="zeusPkg01Call()"><s:message code="zeusDataConnect.zeusPkg01Call"/></button>
                <%-- 연동신청처리 --%>
                <button class="btn_skyblue" ng-click="zeusPkg02Call()"><s:message code="zeusDataConnect.zeusPkg02Call"/></button>
            </div>
            <%-- 개발자용 --%>
            <div class="updownSet oh mb10">
                개발자용 :
                <%-- 비밀번호 --%>
                <input type="password" class="sb-input w120px" id="srchSystemPw" ng-model="systemPw" />
                <%-- 제우스 PKG 호출 01 --%>
                <button class="btn_skyblue" ng-click="zeusPkg01()"><s:message code="zeusDataConnect.zeusPkg01"/></button>
                <%-- 제우스 PKG 호출 02 --%>
                <button class="btn_skyblue" ng-click="zeusPkg02()"><s:message code="zeusDataConnect.zeusPkg02"/></button>
            </div>
        </div>
    </div>

    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.hqOfficeCd"/>" binding="cocd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.storeCd"/>" binding="buut" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.storeNm"/>" binding="buname1" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.storeNm"/>" binding="storeNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.mappingFg"/>" binding="mappingFg" data-map="mappingFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.mappingTime"/>" binding="mappingDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.lastMappingTime"/>" binding="regDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="zeusDataConnect.remark"/>" binding="remark" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/zeusDataConnect/zeusDataConnect.js?ver=20250319.01" charset="utf-8"></script>