<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/> --%>

<style>
.sam-tbl {margin-top:50px}
.sam-tbl table {width:100%}
.sam-tbl table thead th,.sam-tbl table tbody th,.sam-tbl table tbody td  {position:relative; font-size:13px; text-align:center; padding:3px; line-height:20px; border:1px solid #ddd}
.sam-tbl table thead th,.sam-tbl table tbody th {background-color:#f6f6f6; color:#222}
.sam-tbl table thead th {padding:5px}
</style>



<div class="subCon" ng-controller="sampleLayerCtrl">

	<div class="sam-tbl">
		<p class="mb20">샘플 레이어 팝업</p>
	  <table>
	    <colgroup>
	      <col class="" />
	    </colgroup>
	    <tbody>
	      <tr>
	      	<th>팝업 1</th>
	      	<td><button class="btn_skyblue" id="" ng-click="samLayer()"><s:message code="hqEmp.hqEmpReg" /></button></td>
				</tr>
	    </tbody>
	  </table>
  </div>

<%-- 샘플 팝업 --%>
<wj-popup control="sampleLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:700px;">

  <div class="wj-dialog wj-dialog-columns" ng-controller="sampleLyCtrl">
	  <form name="">
	    <div class="wj-dialog-header wj-dialog-header-font">
	      <span><s:message code="hqEmp.hqEmpInfo"/></span>
	      <a href="#" class="wj-hide btn_close"></a>
	    </div>
	
	    <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
	      <h3 class="h3_tbl brt"><s:message code="hqEmp.hqEmpInfo"/></h3>
	
	      <%-- 상세 --%>
	      <div style="height: 266px; overflow-y: auto;">
	
	        <table class="tblType01">
	          <colgroup>
	            <col class="w20" />
	            <col class="w30" />
	            <col class="w20" />
	            <col class="w30" />
	          </colgroup>
	          <tbody>
	          <tr>
	            <%-- 사원번호 --%>
	            <th>
	              <div class="impWrap"><s:message code="hqEmp.empNo" /></div>
	            </th>
	            <td>
	              <input type="text" name="" class="sb-input w100" ng-model="" placeholder=""/>
	            </td>
	            <th>
	              <div class="impWrap"><s:message code="hqEmp.empNo" /></div>
	            </th>
	            <td>
	              <input type="text" name="" class="sb-input w100" ng-model="" placeholder=""/>
	            </td>
	          </tr>
	          </tbody>
	        </table>
	      </div>
	    </div>
	
	    <div class="wj-dialog-footer">
	      <%-- 등록 regist() --%>
	      <button class="btn btn_blue" ng-click="" ng-show=""><s:message code="cmm.new.add"/></button>
	      <%-- 저장 --%>
	      <button class="btn btn_blue" ng-click="" ng-show=""><s:message code="cmm.save"/></button>
	      <%-- 닫기 --%>
	      <button class="btn btn_gray" ng-click="close()"><s:message code="cmm.close"/></button>
	    </div>
	  </form>
  </div>
  
</wj-popup>	
	
<script>

 /**
  * get application
  */
 var app = agrid.getApp();

 /**
  * 샘플 레이어 팝업
  */
 app.controller('sampleLayerCtrl', ['$scope', '$http', function ($scope, $http) {
   // 상위 객체 상속 : T/F 는 picker
   angular.extend(this, new RootController('sampleLayerCtrl', $scope, $http, false));
     
   $scope.selectedSample;
   
   // 신규등록 버튼 클릭
   $scope.samLayer = function(){
     $scope.selectedSample = {};
     
     $scope.sampleLayer.show(true, function(s){
       /* $scope.getHqEmpList(); */
     });
   };
   
   // 화면 ready 된 후 설정
   angular.element(document).ready(function () {
	   
     // 사원 등록 팝업 핸들러 추가
     $scope.sampleLayer.shown.addHandler(function (s) {
       setTimeout(function() {
    	   
         var params = $scope.selectedSample;
         $scope._broadcast('sampleLyCtrl', params);
       }, 50);
       
     });
     
   });

 }]);
 
 </script>
	

	<p class="mt30">샘플 탭 type1</p>
	
	<div class="con">
	    <div class="tabType1" ng-controller="statusCtrl" ng-init="init()">
	        <ul>
	            <%-- 매장 탭 --%>
	            <li>
	                <a id="statusStoreTab" href="#" class="on" ng-click="statusStoreShow()"><s:message code="storeStatus.store"/></a>
	            </li>
	            <%-- 관리업체 탭 --%>
	            <li>
	                <a id="statusAgencyTab" href="#" ng-click="statusAgencyShow()"><s:message code="storeStatus.agency"/></a>
	            </li>
	        </ul>
	    </div>
	</div>


<script>

 /**
  * get application
  */
 var app = agrid.getApp();
  
 /**
  * 샘플 탭 1
  */
 app.controller('statusCtrl', ['$scope', function ($scope) {
	    $scope.init = function () {
	        $("#statusStoreView").show();
	        $("#statusAgencyView").hide();
	    };

	    // 매장 탭 보이기
	    $scope.statusStoreShow = function () {
	        $("#statusStoreTab").addClass("on");
	        $("#statusAgencyTab").removeClass("on");

	        $("#statusStoreView").show();
	        $("#statusAgencyView").hide();

	        // angular 그리드 hide 시 깨지므로 refresh()
	        var scope = agrid.getScope("statusStoreCtrl");
	        scope.flex.refresh();
	    };

	    // 관리업체 탭 보이기
	    $scope.statusAgencyShow = function () {
	        $("#statusStoreTab").removeClass("on");
	        $("#statusAgencyTab").addClass("on");

	        $("#statusStoreView").hide();
	        $("#statusAgencyView").show();

	        // angular 그리드 hide 시 깨지므로 refresh()
	        var scope = agrid.getScope("statusAgencyCtrl");
	        scope.flex.refresh();
	    };
	}]);
 
</script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 매장 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 관리업체 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusAgency.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>



	<p class="mt30 mb20">샘플 탭 type2 >> 포스관리 >> Pos 버전 수신현황</p>

  <%-- 탭 --%>
  <ul class="subTab mb20">
    <li><a href="#" id="verrecv" class="on" onclick="changeTab('R')"><s:message code="verRecv.verrecv" /></a></li>
    <li><a href="#" id="storerecv" onclick="changeTab('S')"><s:message code="verRecv.storerecv" /></a></li>
    <li><a href="#" id="verstore" onclick="changeTab('V')"><s:message code="verRecv.verstore" /></a></li>
  </ul>

   <div ng-controller="verRecvCtrl">
			탭 1 콘텐츠 영역
   </div>

<script>
/**
 * 샘플 탭 2
 */
var app = agrid.getApp();

function changeTab(val){

  if(val === 'S'){
    location.href = '/pos/confg/verRecv/storeRecv/list.sb';
  } else if(val === 'V') {
    location.href = '/pos/confg/verRecv/verStore/list.sb';
  }
}

/**********************************************************************
 *  버전목록 그리드
 **********************************************************************/
app.controller('verRecvCtrl', ['$scope', '$http', function ($scope, $http) {
/*   // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRecvCtrl', $scope, $http, true)); */

}]);


</script>


</div>


