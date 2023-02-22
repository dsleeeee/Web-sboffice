<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="momsBatchCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="momsBatch.momsBatch"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button id="nxBtnSearch" class="btn_blue fr"  ng-click="_pageView('momsBatchCtrl',1)">
                <s:message code="momsBatch.batchProc" />
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
            <tr>
                <%-- 본사코드 --%>
                <th><s:message code="momsBatch.hqOfficeCd" /></th>
                <td>
                    <input type="text" id="hqOfficeNm" class="sb-input w70" ng-model="momsBatch.hqOfficeNm" readonly="readonly" ng-click="searchHq()" style="float: left;"/>
                    <input type="hidden" id="hqOfficeCd" ng-model="momsBatch.hqOfficeCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnDelHq" style="margin-left: 5px;" ng-click="delHq()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <%-- 매장코드 --%>
                <th><s:message code="momsBatch.storeCd" /></th>
                <td>
                    <%-- 매장선택 모듈 싱글 선택 사용시 include
                          param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수            --%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM2.jsp" flush="true">
                      <jsp:param name="targetId" value="momsBatchStore" />
                    </jsp:include> <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 처리정보 --%>
                <th><s:message code="momsBatch.procInfo" /></th>
                <td>
                    <div class="sb-select w70">
                        <wj-combo-box
                            id="procInfo"
                            ng-model="procInfo"
                            items-source="_getComboData('procInfo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="procInfoCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 추가정보 --%>
                <th><s:message code="momsBatch.datas" /></th>
                <td>
                    <input type="text" id="datas" class="sb-input w70" ng-model="momsBatch.datas" style="float: left;"/>
                    <button type="button" class="btn_skyblue fl mr2" id="btnAdd1" style="margin-left: 5px;" ng-click="btnAdd1()">⊥</button>
                    <button type="button" class="btn_skyblue fl" id="btnAdd2" style="margin-left: 5px;" ng-click="btnAdd2()">♪</button>
                </td>
            </tr>
        </tbody>
    </table>
</div>

<script>
    // [162] 맘스터치일괄처리
    var momsBatchType = ${ccu.getCommCodeExcpAll("162")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/momsBatch/momsBatch.js?ver=20230223.02" charset="utf-8"></script>

<%-- 본사 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchHq.jsp">
</c:import>