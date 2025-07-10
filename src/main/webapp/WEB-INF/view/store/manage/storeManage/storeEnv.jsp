<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="storeEnvLayer" show-trigger="Click" hide-trigger="Click" style="width:800px;height:650px;display:none;">
  <div class="wj-dialog wj-dialog-columns title" >

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.storeInfo" />
      <span id="storeEnvTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <div ng-controller="storeEnvCtrl">
        <ul class="subTab">
          <%-- 매장정보 --%>
          <li><a id="storeInfo" href="#" ng-click="changeInfoTab();"><s:message code="storeManage.storeInfo" /></a></li>
          <%-- 매장환경 --%>
          <li><a id="storeEnv" href="#" class="on"><s:message code="storeManage.storeEnv" /></a></li>
          <%-- 메뉴권한 --%>
          <li><a id="storeAuth" href="#" ng-click="changeAuthTab();"><s:message code="storeManage.auth" /></a></li>
        </ul>

          <div class="mt20">
              <input type="text" class="sb-input w200px" id="srchConfig"/>
              <%-- 조회버튼 --%>
              <button type="button" class="btn_skyblue" ng-click="srchEnvst();"><s:message code="cmm.search"/></button>
              <%-- 다중사업자관리 --%>
              <button type="button" class="btn_skyblue" id="btnMultiBizManage" ng-click="multiBizManage();"><s:message code="storeManage.multiBizManage"/></button>

              <%-- 환경설정값 조회 시, 조회버튼 클릭으로 조회하는 것인지 또는 탭 클릭으로 조회하는 것인지 확인을 위한 값 --%>
              <%-- [포스환경]탭 에서 [조회] 버튼을 클릭하여 환경설정을 조회할 경우, 포스명칭이 바뀌는것을 방지하기 위해 사용 --%>
              <input type="hidden" id="hdSrchYn"/>

              <%-- [1337] 다중사업자사용여부 '사용'으로 저장시 터미널정보 기본값 있는지 확인을 위해 사용 --%>
              <input type="hidden" id="hdVendorCd"/>
              <input type="hidden" id="hdVendorTermnlNo"/>
              <input type="hidden" id="hdvendorSerNo"/>
          </div>
      </div>

      <%-- 매장환경, 외식환경, 유통환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storeCmmEnv.jsp">
      </c:import>

      <%-- 포스환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storePosEnv.jsp">
      </c:import>

      <%-- 포스기능키 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storePosFuncKey.jsp">
      </c:import>

      <%-- 주방프린터 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/kitchenPrint.jsp">
      </c:import>

      <%-- 주방프린터 상품연결 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/kitchenPrintProduct.jsp">
      </c:import>

      <%-- 다중사업자관리 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/multiBizManage.jsp">
      </c:import>

    </div>
  </div>
</wj-popup>
<script type="text/javascript">
    // 매장환경 저장전 체크에 필요한 환경설정값 갖고있기
    var orgEnv1102 = "";
    var orgEnv1221 = "";
    var orgEnv2001 = "";
    var orgEnv2002 = "";
    var orgEnv1337 = "";


    // 포스환경 저장전 체크에 필요한 환경설정값 갖고있기
    var orgEnv1015 = "";
    var orgEnv4020 = "";
    var orgEnv4021 = "";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeEnv.js?ver=20250709.01" charset="utf-8"></script>

