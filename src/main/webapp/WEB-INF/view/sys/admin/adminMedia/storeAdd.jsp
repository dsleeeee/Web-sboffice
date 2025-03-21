<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<%-- 매장추가 레이어 --%>
<wj-popup control="storeAddLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:680px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="media.store.registed" />
            <span id="versionDetailTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" >
            <%-- 탭 --%>
            <ul class="subTab">
                    <%-- 버전정보 --%>
                <li><a id="storeInfo" href="#" onclick="changeTab()"><s:message code="media.verInfo" /></a></li>
                    <%-- 적용매장 --%>
                <li><a id="storeEnv" href="#" class="on"><s:message code="media.store.registed" /></a></li>
            </ul>

            <div  ng-controller="addStoreCtrl">
                <div class="oh">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <%-- 본사코드 --%>
                            <th><s:message code="adminMedia.hqOfficeCd" /></th>
                            <td>
                                <input type="text" class="sb-input w100" id="adminHqOfficeCd" />
                            </td>
                            <%-- 본사명 --%>
                            <th><s:message code="adminMedia.hqOfficeNm" /></th>
                            <td>
                                <input type="text" class="sb-input w100" id="adminHqOfficeNm" />
                            </td>
                        </tr>
                        <tr>
                            <%-- 매장코드 --%>
                            <th><s:message code="adminMedia.storeCd" /></th>
                            <td>
                                <input type="text" class="sb-input w100" id="adminStoreCd" />
                            </td>
                            <%-- 매장명 --%>
                            <th><s:message code="adminMedia.storeNm" /></th>
                            <td>
                                <input type="text" class="sb-input w100" id="adminStoreNm" />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="mt10 tr">
                        <%-- 조회 --%>
                        <button id="btnSearchStore" class="btn_skyblue" onclick="search()"><s:message code="cmm.search" /></button>
                    </div>
                </div>

                <%-- 등록매장 그리드 --%>
                <div class="oh mt10 w50 fl">
                    <div class="wj-TblWrap mr10" style="height:405px; overflow-y: hidden;">
                        <div class="oh mb10">
                            <span class="fl bk lh20 s14"><s:message code="media.store.registed"/></span>
                            <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
                        </div>
                        <div id="regProdGrid" style="height: 370px;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="70" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.storeNm"/>" binding="storeNm" align="left" width="70" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="media.store.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="50" align="center" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="media.store.posCnt"/>" binding="posCnt"  width="50" align="center" is-read-only="true" ></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>

            <%--- 미등록매장 그리드 --%>
            <div class="oh mt10 w50 ">
                <div class=" ">
                    <div class="wj-TblWrap ml10" style="height:405px; overflow-y: hidden;" ng-controller="allStoreCtrl">
                        <div class="oh mb10">
                            <span class="fl bk lh20 s14"><s:message code="media.store.noRegisted" /></span>
                            <span class="fr"><a href="#" class="btn_grayS2" ng-click="save()" ><s:message code="media.regist.new" /></a></span>
                        </div>
                        <div id="noRegProdGrid" style="height: 370px;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="70" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.storeNm"/>" binding="storeNm" align="left" width="70" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="media.store.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="50" align="center" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="media.store.posCnt"/>" binding="posCnt"  width="50" align="center" is-read-only="true" ></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/adminMedia/storeAdd.js?ver=20250307.01" charset="utf-8"></script>

<script>
    $(document).ready(function(){
        $("#chkMulti").change(function(){
            if($("#chkMulti").is(":checked")){
                setText();
            }else{
            }
        });
    });

    var orgnFg = "${orgnFg}";
    var sysStatFgTotal = ${ccu.getCommCodeSelect("005")};

</script>
