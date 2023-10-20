<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div id="selectMenuSingleArea" class="wj-TblWrap mt5 ng-cloak" ng-hide="isSelectMenuSingleTab">

    <table class="searchTbl mb5" style="border-top:1px solid #ddd">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
            <tr>
                <th>
                    <div class="sb-select w100">
                        <wj-combo-box
                             id="srchTypeSelGroupSingle"
                             items-source="_getComboData('srchTypeSelGroupSingle')"
                             display-member-path="name"
                             selected-value-path="value"
                             is-editable="false"
                             control="srchTypeSelGroupSingleCombo">
                        </wj-combo-box>
                    </div>
                </th>
                <td>
                    <input type="text" id="txtSelGroupSingle" class="fl sb-input w100" onkeyup="fnNxBtnSearch(2);"/>
                </td>
                <th>
                    <div class="sb-select w100">
                        <wj-combo-box
                          id="srchTypeSelProdSingle"
                          items-source="_getComboData('srchTypeSelProdSingle')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          control="srchTypeSelProdSingleCombo">
                        </wj-combo-box>
                    </div>
                </th>
                <td>
                    <input type="text" id="txtSelProdSingle" class="fl sb-input w100" onkeyup="fnNxBtnSearch(2);"/>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="w40 fl">
        <div>
            <%--위즈모 테이블--%>
             <div class="wj-TblWrapBr pd5" style="height: 240px;" ng-controller="sideMenuSelectGroupSingleCtrl">
               <div class="updownSet oh mb5">
                 <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselGrp' /></span>
                     <button class="btn_skyblue" id="btnAddSelGroupSingle" ng-click="addRow()" >
                       <s:message code="cmm.add" />
                     </button>
                     <button class="btn_skyblue" id="btnDelSelGroupSingle" ng-click="deleteRow()" >
                       <s:message code="cmm.delete" />
                     </button>
                     <button class="btn_skyblue" id="btnSaveSelGroupSingle" ng-click="save()" >
                       <s:message code="cmm.save" />
                     </button>
               </div>
               <%-- 개발시 높이 조절해서 사용--%>
               <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
               <div style="height:180px">
                 <wj-flex-grid
                   autoGenerateColumns="false"
                   control="flex"
                   initialized="initGrid(s,e)"
                   sticky-headers="true"
                   selection-mode="Row"
                   items-source="data"
                   item-formatter="_itemFormatter"
                   ime-enabled="true"
                   id="wjGridSelGroupSingleList">

                   <!-- define columns -->
                   <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="100"></wj-flex-grid-column>
                   <c:if test="${brandUseFg == '1'}">
                       <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.brand"/>" binding="hqBrandCd" data-map="brandDataMap" width="80"></wj-flex-grid-column>
                       </c:if>
                   </c:if>
                   <c:if test="${orgnFg == 'HQ'}">
                     <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                   </c:if>
                   <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" data-map="fixProdFgDataMap" width="50" is-read-only="true"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselTypeFg"/>" binding="sdselTypeFg" data-map="sdselTypeFgDataMap" width="70" is-read-only="true"></wj-flex-grid-column>
                 </wj-flex-grid>
               </div>
             </div>
             <%--//위즈모 테이블--%>
        </div>

        <div>
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr pd5" style="height: 260px;" ng-controller="sideMenuSelectClassSingleCtrl">
                <div class="updownSet oh mb10" style="height:60px;">
                  <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselClass' /><span id="sideSelectGroupSingleTitle"></span> </span>
                  <br>
                  <br>
                    <%-- 선택분류복사 --%>
                    <button class="btn_skyblue" id="btnSdselClassCopySingle" ng-click="sdselClassCopySingle()" >
                        <s:message code="sideMenu.selectMenu.sdselClassCopy" />
                    </button>
                    <button class="btn_up" id="btnUpSelClassSingle" ng-click="rowMoveUp()" >
                      <s:message code="cmm.up" />
                    </button>
                    <button class="btn_down" id="btnDownSelClassSingle" ng-click="rowMoveDown()" >
                      <s:message code="cmm.down" />
                    </button>
                    <button class="btn_skyblue" id="btnAddSelClassSingle" ng-click="addRow()" >
                      <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnDelSelClassSingle" ng-click="deleteRow()" >
                      <s:message code="cmm.delete" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveSelClassSingle" ng-click="saveClass()" >
                      <s:message code="cmm.save" />
                    </button>
                </div>
                <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                    <div class="updownSet oh mb10">
                        <%-- 적용매장등록 --%>
                        <button class="btn_skyblue" id="btnSdselClassRegStoreSingle" ng-click="sdselClassRegStore()">
                            <s:message code="sideMenu.selectMenu.sdselClassRegStore" />
                        </button>
                    </div>
                </c:if>
                <%-- 개발시 높이 조절해서 사용--%>
                <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                <div style="height:140px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridSelClassSingleList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassCd"/>" binding="sdselClassCd" width="70" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassNm"/>" binding="sdselClassNm" width="*"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.requireYn"/>" binding="requireYn" data-map="requireYnDataMap" width="85" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="sdselQty" width="50" max-length="3"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="fixProdCnt" width="*" visible="false"></wj-flex-grid-column>
                        <%--<wj-flex-grid-column header="순서" binding="dispSeq" width="50"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                        <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.oldRegStoreFg"/>" binding="oldRegStoreFg" data-map="oldRegStoreFgDataMap" width="85" visible="false"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>

    <div class="w60 fl">
        <div>
          <%--위즈모 테이블--%>
          <div class="wj-TblWrapBr ml10 pd5" style="height: 500px;" ng-controller="sideMenuSelectProdSingleCtrl">
          <%-- 선택한 선택그룹의 브랜드 코드 --%>
          <input type="hidden" id="hdSdselGrpBrandCdSingle" />
            <div class="updownSet oh  mb5">
              <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselProd' /><span id="sideClassSingleTitle"></span> </span>
                <button class="btn_up" id="btnUpSelProdSingle" ng-click="rowMoveUp()">
                  <s:message code="cmm.up" />
                </button>
                <button class="btn_down" id="btnDownSelProdSingle" ng-click="rowMoveDown()">
                  <s:message code="cmm.down" />
                </button>
                <button class="btn_skyblue" id="btnAddSelProdSingle" ng-click="addRow()">
                  <s:message code="cmm.add" />
                </button>
                <button class="btn_skyblue" id="btnDelSelProdSingle" ng-click="deleteRow()">
                  <s:message code="cmm.delete" />
                </button>
                <button class="btn_skyblue" id="btnSaveSelProdSingle" ng-click="saveProd()">
                  <s:message code="cmm.save" />
                </button>
            </div>
            <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
              <div class="updownSet oh mb10">
                  <%-- 적용매장등록 --%>
                  <button class="btn_skyblue" id="btnSdselProdRegStoreSingle" ng-click="sdselProdRegStore()">
                      <s:message code="sideMenu.selectMenu.sdselClassRegStore" />
                  </button>
              </div>
            </c:if>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div style="height:450px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridSelProdSingleList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                    <c:if test="${brandUseFg == '1'}">
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.brand"/>" binding="hqBrandCd" data-map="brandDataMap" width="80" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdUprc"/>" binding="addProdUprc" width="50"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdQty"/>" binding="addProdQty" width="50" max-length="3"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" width="50" data-map="fixProdFgDataMap"></wj-flex-grid-column>
                    <%--<wj-flex-grid-column header="순서" binding="dispSeq" width="50"></wj-flex-grid-column>--%>
                    <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                    <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.oldRegStoreFg"/>" binding="oldRegStoreFg" data-map="oldRegStoreFgDataMap" width="85" visible="false"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>

            </div>
          </div>
          <%--//위즈모 테이블--%>
        </div>
    </div>

</div>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenuSelectMenuSingle.js?ver=20230714.04" charset="utf-8"></script>

<%-- 선택분류복사 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenu/sdselClassCopySingle.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>