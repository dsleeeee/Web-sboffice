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

      <div style="height:150px; overflow-y: auto;">
        <table class="tblType01">
          <colgroup>
            <col class="w35" />
            <col class="w65" />
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


<%--
      <div style="height:150px; overflow-y: auto;">
        <table class="tblType01 moreDark">
          <colgroup>
            <col class="w15" />
            <col class="w15" />
            <col class="w45" />
          </colgroup>
          <thead>
          <tr>
            <th><s:message code="cmm.no" /></th>
            <th><s:message code="storeManage.posNo" /></th>
            <th><s:message code="storeManage.posNm" /></th>
          </tr>
          </thead>
          <tbody id="posNmContent">
          </tbody>
        </table>
      </div>
--%>

      <%-- 복사 --%>
      <div class="btnSet" class="mt10 mb10">
        <span><a href="#" class="btn_blue" ng-click="copy()"><s:message code="cmm.copy" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/copyStoreEnv.js?ver=20181228.01" charset="utf-8"></script>
