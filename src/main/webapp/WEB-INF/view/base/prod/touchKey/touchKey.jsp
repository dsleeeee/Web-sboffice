<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="touchKeyEnvstVal" value="${touchKeyEnvstVal}" />
<c:set var="touchKeyEnvstVal2" value="${touchKeyEnvstVal2}" />
<c:set var="touchKeyGrp" value="${touchKeyGrp}" />
<c:set var="brandUseFg" value="${brandUseFg}" />
<c:set var="fontSizeEnvstVal" value="${fontSizeEnvstVal}" />

<%--서브컨텐츠--%>
<div class="subCon" ng-controller="touchKeyCtrl" id="touchKeyView">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="storeSideMenu.touchKey" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSrchTouchKey">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr id="trTouchKeyGrp">
            <%-- 분류조회 --%>
            <th><s:message code="touchKey.grp" /></th>
            <td colspan="3">
                <div class="sb-select" style="width:120px; float:left;">
                    <wj-combo-box
                            id="touchKeyGrpCombo"
                            ng-model="touchKeyGrp"
                            control="touchKeyGrpCombo"
                            items-source="_getComboData('touchKeyGrpCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
                <%-- 추가터치키생성 --%>
                <button class="btn_skyblue fl ml20" id="btnNewGrp" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && (touchKeyEnvstVal2 == '0' || touchKeyEnvstVal2 == '2')}">style="margin-left : 4px;visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="touchKey.newGrp"/></button>
                <%-- 터치키복사 --%>
                <button class="btn_skyblue fl ml20" id="btnCopyTouchKey" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && (touchKeyEnvstVal2 == '0' || touchKeyEnvstVal2 == '2')}">style="margin-left : 4px;visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose> ng-click="$broadcast('showPopUpCopy')">
                    <s:message code="touchKey.copy" />
                </button>
                <%-- 터치키미적용상품 --%>
                <button class="btn_skyblue fl ml20" id="btnNoTouchKey" style="margin-left : 4px;" ng-click="$broadcast('showPopUpNoTouchKey')">
                    <s:message code="touchKey.noTouchKey" />
                </button>
                <%-- 초기화 --%>
                <button class="btn_skyblue fl ml5" id="btnInti"<c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && touchKeyEnvstVal2 == '0'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>>
                    <s:message code="cmm.init"/>
                </button>
                <%-- 터치키 그룹명 --%>
                <button class="btn_skyblue fl ml5" id="btnGrpNm"<c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && (touchKeyEnvstVal2 == '0' || touchKeyEnvstVal2 == '2')}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose> ng-click="$broadcast('showGrpNm')">
                    <s:message code="touchKey.grpNm"/>
                </button>
                <c:if test="${orgnFg == 'STORE' && hqOfficeCd != '00000' && touchKeyEnvstVal2 == '2'}">
                    <%-- 매장수정허용분류 --%>
                    <button class="btn_blk ml5 fr"  id="storeModGrpMs" ng-click="$broadcast('showPopUpStoreModGrp')">
                        <s:message code="touchKey.storeModGrp" />
                    </button>
                </c:if>
                <%-- 신규생성 취소 --%>
                <%--<button class="btn_skyblue fl ml20" id="btnCancleNewGrp" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="touchKey.cancle"/></button>--%>
                <%-- 터치키 신규 등록인지 수정인지 여부 파악을 위해--%>
                <input type="hidden" id="hdNewGrp"/>
            </td>
        </tr>
        <tr id="trApplyStore" style="display: none;">
            <th><s:message code="touchKey.applyStore" /></th>
            <td colspan="3" class="oh">
                <button class="btn_blk fl" id="btnApplyStore" ng-click="$broadcast('showPopUp')">
                    <s:message code="touchKey.applyStore" />
                </button>
                <%-- 터치키 매장복사 --%>
                <button class="btn_blk ml5 fl"  id="copyTouchKey" ng-click="copyStoreTouchKey()">
                    <s:message code="touchKey.storeCopy" />
                </button>
                <%-- 매장수정허용분류 --%>
                <button class="btn_blk ml5 fl"  id="storeModGrpHq" ng-click="$broadcast('showPopUpStoreModGrp')">
                    <s:message code="touchKey.storeModGrp" />
                </button>
            </td>
        </tr>
        </tbody>
    </table>

    <%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>
    <div class="TblWrapBr touchKeyWrap oh mt10">
        <%--left--%>
        <div class="w30 fl">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code="touchKey.prodList"/></span>
            </div>
            <div class="b4 mb5 pd5">
                <div class="updownSet">
                    <span class="fl bk lh30 s14 ml5"><s:message code="touchKey.classUseYn"/> :</span>
                    <div class="sb-select dkbr fl w85px ml5">
                        <wj-combo-box
                                id="touchKeyFilterCombo"
                                ng-model="touchKeyFilter"
                                items-source="_getComboData('touchKeyFilterCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="setTouchKeyFilter(s)">
                        </wj-combo-box>
                    </div>
                </div>
                <%-- 브랜드 --%>
                <c:if test="${brandUseFg == '1'}">
                <div class="updownSet">
                    <span class="fl bk lh30 s14 ml5"><s:message code="touchKey.brand" /> :</span>
                    <div class="sb-select dkbr fl w85px ml5">
                        <wj-combo-box
                                id="srchBrandCombo"
                                ng-model="hqBrandCd"
                                items-source="_getComboData('srchBrandCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index-changed="selectedBrand(s)">
                        </wj-combo-box>
                    </div>
                </div>
                </c:if>
                <div class="updownSet mt5">
                    <%--<span class="fl bk lh30 s14 ml5"><s:message code="touchKey.class"/> :</span>--%>
                    <input type="text" id="_prodClassCdNm" name="prodClassCdNm" class="sb-input fl w70 ml5" style="font-size: 13px;"
                           ng-model="prodClassInfo.prodClassCdNm"
                           ng-click="popUpProdClass()"
                           placeholder="상품분류 선택" ng-readonly="true">
                </div>
                <div class="updownSet mt5">
                    <%--<span class="fl bk lh30 s14 ml5">&nbsp;&nbsp;&nbsp;<s:message code="touchKey.grid.prodNm"/> :</span>--%>
                    <input type="text" id="_prodNm" name="prodNm" class="sb-input fl w70 ml5" style="font-size: 13px;" ng-model="prodClassInfo.prodNm" placeholder="상품명">
                    <button class="btn_skyblue fl ml5" id="btnSearch" ng-click="_broadcast('touchKeyCtrl')">
                        <s:message code="cmm.search" />
                    </button>
                </div>
            </div>
            <%--위즈모 테이블--%>
            <c:if test="${brandUseFg == '0'}">
                <div class="cfgWrap2" style="height:461px;">
            </c:if>
            <c:if test="${brandUseFg == '1'}">
                <div class="cfgWrap2" style="height:446px;">
            </c:if>
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="ListBox"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        allow-dragging="None"
                        is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="touchKey.grid.touchKeyUsed"/>"
                                         binding="touchKeyUsed" width="38"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.grid.prodNm"/>" binding="prodNm" width="200"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.grid.prodCd"/>" binding="prodCd"
                                         width="100" visible="true" align="center"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="touchKey.grid.prodClass"/>"
                                         binding="prodClassCd"
                                         visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.grid.prodClassNm"/>"
                                         binding="prodClassNm" width="100"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.grid.saleUprc"/>"
                                         binding="saleUprc" width="*" visible="false"></wj-flex-grid-column>
                    <%-- [1250 맘스터치] --%>
                    <c:if test="${momsEnvstVal == '1'}">
                        <wj-flex-grid-column header="<s:message code="touchKey.grid.groupProdNm"/>"
                                             binding="groupProdNm" width="100"></wj-flex-grid-column>
                    </c:if>
                </wj-flex-grid>
            </div>
        </div>
        <%--//left--%>
        <%--right--%>
        <div class="fl ml10" style="width: 502px;">
            <%--미리보기 영역 시작--%>
            <%--포스에서 1024*768 사이즈에 보이지 않아 위치변경함(스타일적용, 저장)--%>
            <div class="updownSet oh mb10">
                <div class="fl txtIn">
                    <div class="sb-select dkbr fl w120px" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && touchKeyEnvstVal2 == '0'}">style="visibility: hidden"</c:when><c:otherwise></c:otherwise></c:choose>>
                        <div id="selectStyle" ng-model="selectStyle"></div>
                    </div>
                    <button class="btn_skyblue fl ml5" id="btnApplyStyle" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && touchKeyEnvstVal2 == '0'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="touchKey.applyStyle"/></button>
                    <button class="btn_skyblue fl ml5" id="btnViewStyle" ng-click="viewStyle()" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && touchKeyEnvstVal2 == '0'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="touchKey.viewStyle"/></button>
                    <button class="btn_skyblue fl ml20" id="btnSave" <c:choose><c:when test="${orgnFg == 'STORE' && touchKeyEnvstVal == '2' && touchKeyEnvstVal2 == '0'}">style="visibility: hidden"</c:when><c:otherwise>style="margin-left : 4px;"</c:otherwise></c:choose>><s:message code="cmm.save"/></button>
                    <%--포스에서 1024*768 사이즈에 보이지 않아 위치변경함(초기화, 삭제)--%>
                    <div id="keyStyleAd" class="fl hideNav" style="margin-left : 4px;">
                        <button class="btn_skyblue mb5" id="btnReset" ng-click="$broadcast('btnReset')">
                            <s:message code="touchKey.reset"/></button>
                        <button class="btn_skyblue" id="btnDelete" ng-click="$broadcast('btnDelete')">
                            <s:message code="touchKey.delete"/></button>
                    </div>
                </div>
            </div>
            <div id="touchArea" class="prev2 fl">
                <%--분류버튼 영역 시작--%>
                <div class="touchClassWrap" id="classWrap">
                    <%--1줄 "hClassLine1", 2줄 "hClassLine2", 3줄 "hClassLine3" 사용 --%>
                    <%--터치키가 들어가는 위치 --%>
                    <div class="" id="classArea" tabindex="-1">
                    </div>
                </div>
                <%--//분류버튼 영역 끝--%>
                <%--상품버튼 영역 시작--%>
                <div class="touchProdsWrap" id="prodWrap">
                    <%--1줄 "hProdLine1", 2줄 "hProdLine2", 3줄 "hProdLine3" 사용 --%>
                    <%--터치키가 들어가는 위치 --%>
                    <div class="" id="prodArea" tabindex="-1">
                    </div>
                </div>
                <%--//상품버튼 영역 끝 --%>
            </div>
            <%--//미리보기 영역 끝 --%>
        </div>
        <%--//right--%>
        <div class="fl ml5" style="width: 125px;">
            <div class="updownSet oh mb10">
            </div>
            <%-- 페이지 버튼 영역 시작 --%>
            <%-- 분류페이지 버튼 --%>
            <div id="divClassNavWrap" class="classNavWrap">
                <div class="classPageNoWrap">
                    <span id="classPageNoText" class="s16"></span>
                </div>
                <div class="classNav">
                    <div class="classNavPrev fl" id="grpNavPrev"></div>
                    <div class="classNavNext fl" id="grpNavNext"></div>
                </div>
            </div>
            <%-- 상품페이지 버튼 --%>
            <div id="divProdNavWrap" class="prodNavWrap">
                <div id="keyStyle" class="oh keyStyleWrap hideNav">
                    <div id="colorStyleWrap" class="mb5">
                        <span class="s12 fl lh15 bk mt5"><s:message code="touchKey.fill"/></span>
                        <div class="sb-select txtIn w100">
                            <%-- 채우기 --%>
                            <div id="fillColor"></div>
                        </div>
                    </div>
                    <div id="fontStyleWrap">
                        <span class="s12 fl lh15 bk"><s:message code="touchKey.cellType"/></span>
                        <div class="sb-select txtIn fl w100 mb5">
                            <div id="cellTypeCombo"></div>
                        </div>
                        <span class="s12 fl lh15 bk"><s:message code="touchKey.font"/></span>
                        <div class="sb-select txtIn fl w100 mb5">
                            <%-- 폰트컬러 --%>
                            <div id="fontColor"></div>
                        </div>
                        <div class="sb-select txtIn fl w100 mb5">
                            <%-- 폰트사이즈 --%>
                            <div id="fontSize"></div>
                        </div>
                    </div>
                    <div id="touchKeyStyleCopyWrap">
                        <span class="s12 fl lh20 bk"><s:message code="touchKey.touchKeyStyleCopy"/></span>
                        <button class="btn_skyblue fl mr5" id="btnTouchKeyStyleCopy"><s:message code="cmm.copy" /></button>
                        <button class="btn_skyblue" id="btnTouchKeyStyleApply"><s:message code="cmm.apply" /></button>
                        <div class="mt5" id="divTouchKeyStyleCopy" style="display: none;">
                            <span class="s12 fl lh15 bk">채우기 <input type="color" style="width:18px; height:18px;" id="pickCopyFillColor"></span></br>
                            <span class="s12 fl lh15 bk">상품명 <input type="color" style="width:18px; height:18px;" id="pickCopyFont02"><label id="lblCopyFont02"></label></span></br>
                            <span class="s12 fl lh15 bk">금&nbsp;&nbsp; 액 <input type="color" style="width:18px; height:18px;" id="pickCopyFont03"><label id="lblCopyFont03"></label></span>
                            <input type="hidden" id="hdCopyFillColor" />
                            <input type="hidden" id="hdCopyFont02" />
                            <input type="hidden" id="hdCopyFont03" />
                            <input type="hidden" id="hdTouchKeyStyleApply" value="N"/>
                        </div>
                    </div>
                </div>
                <div class="prodPageNoWrap">
                    <span id="prodPageNoText" class="s16"></span>
                </div>
                <div class="prodNav">
                    <div class="prodNavPrev fl" id="prodNavPrev"></div>
                    <div class="prodNavNext fl" id="prodNavNext"></div>
                </div>
            </div>
            <%--// 페이지 버튼 영역 끝 --%>
        </div>
    </div>

    <%-- 상품분류 팝업 --%>
    <c:if test="${param.gubun ne 'sideMenu'}">
        <c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
        </c:import>
    </c:if>
    <%-- 터치키 매장적용 팝업 --%>
    <c:import url="/WEB-INF/view/base/prod/touchKey/popUpTouchKeyApplyStore.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 터치키복사 팝업 --%>
    <c:import url="/WEB-INF/view/base/prod/touchKey/popUpCopyTouchKey.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 터치키미적용상품 팝업 --%>
    <c:import url="/WEB-INF/view/base/prod/touchKey/popUpNoTouchKey.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 매장수정허용분류 팝업 --%>
    <c:import url="/WEB-INF/view/base/prod/touchKey/popUpStoreModGrp.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 그룹명 팝업 --%>
    <c:import url="/WEB-INF/view/base/prod/touchKey/popUpGrpNm.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>

    <%-- 매장 판매터치키복사 --%>
    <c:import url="/WEB-INF/view/base/store/view/copyStoreTouchKey.jsp">
    </c:import>

</div>
<%--//서브컨텐츠--%>

<script>

    // 본사권한 [기초관리] - [매장관리] - [매장정보조회]의 판매터치키변경을 클릭하여 접속한 경우, 왼쪽 메뉴영역은 접어두기.
    var referrer = document.referrer;
    if(orgnFg === "STORE" && referrer.indexOf("/base/store/view/view/list.sb") > 0){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
    // 최초 접속시에는 이전 URL 인자값으로 판별가능
    if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
    // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
    if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
        $(".menuControl").trigger("click");
    }

    // 브랜드
    var brandList = ${brandList};
    var touchKeyGrpData = ${touchKeyGrp};
    var tukeyGrpData = ${touchKeyGrp};
    tukeyGrpData.unshift({name: "전체", value: ""});

    // 매장 [1248]
    var touchKeyEnvstVal2 = "${touchKeyEnvstVal2}";

    // 본사 환경설정값 - 터치메뉴 폰트크기[1236]
    var fontSizeEnvstVal = ${fontSizeEnvstVal};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // 기존 터치키 그룹이 있을 떄/ 없을 때 버튼, selectBox 설정
    if(touchKeyGrpData.length === 0){
        $("#btnSrchTouchKey").css("display", "none");  //조회버튼
        $("#btnNewGrp").css("display", "none");         //추가터치키생성버튼
        //$("#btnCancleNewGrp").css("display", "none");      //신규생성취소버튼
        $("#touchKeyGrpCombo").attr("disabled", true);  //터치키 그룹코드 콤보박스
        $("#touchKeyGrpCombo").css('background-color', '#F0F0F0'); // 터치키 그룹코드 콤보박스 (회색처리)
        $("#btnApplyStore").css("display", "none");     //터치키매장적용버튼
        $("#trTouchKeyGrp").css("display", "none");     //터치키그룹코드 콤보박스 행
        $("#trApplyStore").css("display", "none");     //터치키매장적용버튼 행

        // 터치키 저장 시 새 그룹으로 생성해 저장하겠다는 Flag
        $("#hdNewGrp").val("Y");

    }else{
        $("#btnSrchTouchKey").css("display", "");
        $("#btnNewGrp").css("display", "");
        //$("#btnCancleNewGrp").css("display", "");
        $("#touchKeyGrpCombo").attr("disabled", false);
        $("#touchKeyGrpCombo").css('background-color', '#FFFFFF');
        $("#btnApplyStore").css("display", "");
        $("#trTouchKeyGrp").css("display", "");
        $("#trApplyStore").css("display", "");

        // 터치키 저장 기본 수정 Flag로 셋팅
        $("#hdNewGrp").val("N");
    }

    // 매장은 터치키 매장적용, 터치키복사 기능 사용할 수 없음.
    if(orgnFg === "STORE") {
        $("#trApplyStore").css("display", "none");
    }else{
        $("#trApplyStore").css("display", "");
    }

    var urlParams = (function (url) {
        var result = {};
        var idx = url.lastIndexOf('?');

        if (idx > 0) {
            var params = url.substring(idx + 1).split('&');

            for (var i = 0; i < params.length; i++) {
                idx = params[i].indexOf('=');

                if (idx > 0) {
                    result[params[i].substring(0, idx)] = params[i].substring(idx + 1);
                }
            }
        }

        return result;
    })(window.location.href);

    // Default resources are included in grapheditor resources
    mxLoadResources = false;

    // urlParams is null when used for embedding
    window.urlParams = window.urlParams || {};

    window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE || 10485760;

    window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
    window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
    window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
    window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
    window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';

    window.TOUCHKEY_OPEN_URL = window.TOUCHKEY_OPEN_URL || '/base/prod/touchKey/touchKey/touchKeyList.sb';
    window.TOUCHKEY_SAVE_URL = window.TOUCHKEY_SAVE_URL || '/base/prod/touchKey/touchKey/save.sb';

    window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
    window.mxLanguage = window.mxLanguage || urlParams['lang'];
    window.mxLanguages = window.mxLanguages || ['ko'];

    window.MAX_CLASS_ROW = '${maxClassRow}' || '2';

</script>
<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="/resource/vendor/wijmo/js/grid/wijmo.grid.filter.min.js?ver=520182500"
        charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TouchKey.js?ver=20220527.05"
        charset="utf-8"></script>

<%-- 스타일미리보기 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/touchKey/touchKeyStyleView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
