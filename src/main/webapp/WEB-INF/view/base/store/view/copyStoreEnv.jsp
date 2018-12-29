<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="copyStoreEnvLayer" show-trigger="Click" hide-trigger="Click" style="width:600px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="copyStoreEnvCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeView.copy.store" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 매장검색영역 --%>
      <div class="searchBar_s">
        <a href="#" class="open">환경복사 매장 선택</a><!--하단 검색테이블 열기 .open, 하단 검색테이블 닫기 .close-->
        <p class="noFolding" style="display:none;">환경복사 매장 선택</p><!-- 접기기능 사용하지 않을경우 -->
      </div>
      <div class="tblBr mb40" >
        <table class="tblType01">
          <colgroup>
            <col class="w30" />
            <col class="w70" />
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="storeView.original.storeCd" /></th>
            <td>
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
              <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                <jsp:param name="targetId" value="originalStore"/>
              </jsp:include>
            </td>
          </tr>
          <tr>
            <th><s:message code="storeView.target.storeCd" /></th>
            <td>
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
              <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                <jsp:param name="targetId" value="targetStore"/>
              </jsp:include>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 fr" style="display:block;position: relative;margin-top: 6px;">
          <button class="btn_skyblue" id="btnSearch" ng-click="searchEnvList()">
            <s:message code="cmm.search" />
          </button>
        </div>
      </div>

      <%-- 포스선택영역 --%>
      <table class="tblType01">
        <colgroup>
          <col class="w20" />
          <col class="w30" />
          <col class="w20" />
          <col class="w30" />
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="storeView.original.pos" /></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="originalPosNo"
                      ng-model="originalPosNo"
                      items-source="originalPosArr"
                      display-member-path="posCdNm"
                      selected-value-path="posNo"
                      is-editable="false"
                      control="comboDt.originalPosCombo"
                      initialized="_initComboBox(s)"
                      ng-readonly="isPosReadonly">
              </wj-combo-box>
            </div>
          </td>
          <th><s:message code="storeView.target.pos" /></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="targetPos"
                      ng-model="targetPos"
                      items-source="targetPosArr"
                      display-member-path="posCdNm"
                      selected-value-path="posNo"
                      is-editable="false"
                      ng-readonly="isPosChk"
                      control="comboDt.targetPosCombo"
                      initialized="_initComboBox(s)">
              </wj-combo-box>
            </div>
          </td>
        </tr>
        </tbody>
      </table>


      <%--위즈모 테이블--%>
      <div class="theGrid mt10" style="height: 200px;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeView.copy.env"/>" binding="nmcodeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeView.copy.env"/>" binding="nmcodeCd" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <input type="hidden" ng-model="originalPosNo" id="originalPosNo" />
      <input type="hidden" ng-model="targetPosNo" id="targetPosNo" />


      <div class="btnSet">
        <%-- 복사 --%>
        <span><a href="#" class="btn_blue" ng-click="copy()"><s:message code="cmm.copy" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/copyStoreEnv.js?ver=20181228.01" charset="utf-8"></script>
