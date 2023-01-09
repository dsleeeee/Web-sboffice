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
      <c:if test="${orgnFg != 'HQ'}">
        <%-- 탭 --%>
        <ul class="subTab">
          <%-- 버전정보 --%>
          <li><a id="storeInfo" href="#" onclick="changeTab()"><s:message code="media.verInfo" /></a></li>
          <%-- 적용매장 --%>
          <li><a id="storeEnv" href="#" class="on"><s:message code="media.store.registed" /></a></li>
        </ul>
      </c:if>

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
                <th><s:message code="media.store.storeCd"/></th>
                <td>
                  <%-- 매장선택 모듈 싱글 선택 사용시 include
                       param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                    displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                    modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                    closeFunc - 팝업 닫기시 호출할 함수
                  --%>
                  <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                    <jsp:param name="targetId" value="addStoreChoice"/>
                  </jsp:include>
                  <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
              <%-- 매장상태구분 --%>
              <th><s:message code="media.store.sysStatFg" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchSysStatFg"
                          ng-model="sysStatFg"
                          items-source="_getComboData('sysStatFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setSelectedSysStatFg(s)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <c:if test="${brandUseFg == '1'}">
              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                  <%-- 매장브랜드 --%>
                  <th><s:message code="media.storeHqBrand" /></th>
                  <td>
                    <div class="sb-select">
                      <wj-combo-box
                        id="srchStoreHqBrandCd"
                        ng-model="storeHqBrandCd"
                        items-source="_getComboData('srchStoreHqBrandCd')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchStoreHqBrandCdCombo">
                      </wj-combo-box>
                    </div>
                  </td>
                  <th></th>
                  <td></td>
                </tr>
              </c:if>
            </c:if>
            </tbody>
          </table>
          <div class="mt10 tr">
            <%-- 조회 --%>
            <button id="btnSearchStore" class="btn_skyblue" onclick="search()"><s:message code="cmm.search" /></button>
          </div>
        </div>

        <%-- 등록매장 그리드 --%>
        <div class="oh mt40 w50 fl">
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
                 <wj-flex-grid-column header="<s:message code="media.store.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.store.storeNm"/>" binding="storeNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.store.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="50" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.store.posCnt"/>" binding="posCnt"  width="50" align="center" is-read-only="true" ></wj-flex-grid-column>

              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>

      <%--- 미등록매장 그리드 --%>
      <div class="oh mt40 w50 ">
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
                <wj-flex-grid-column header="<s:message code="media.store.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.store.storeNm"/>" binding="storeNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/base/store/media/storeAdd.js?ver=20230109.02" charset="utf-8"></script>

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
  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";
  // 사용자 매장브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};

</script>
