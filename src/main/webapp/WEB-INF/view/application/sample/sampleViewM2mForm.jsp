<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<!-- contents start -->
<div class="subCon" ng-controller="loginStatusCtrl">
  
  <%-- 메뉴이름 / 조회 조건 start --%>
  <div class="searchBar flddUnfld">
    <%-- <a href="#" class="open fl">${menuNm}</a> --%>
    <a href="#" class="open fl">메뉴이름</a>
    
    <%-- 조회 button --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="searchBtn" ng-click="_pageView('loginStatusCtrl', 1)">
        <s:message code="cmm.search" />
      </button>
    </div>    
  </div>
  <%-- //메뉴이름 / 조회 조건 end --%>
  
  <%-- 조회 조건 start --%>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
    
      <%-- form > text  --%>
			<tr>
				<th><%-- <s:message code="cmm.hedofc.cd" /> --%>text 1</th>
				<td>
					<input type="text" id="" ng-model="" maxlength="" ng-value="" class="sb-input w100" >
				</td>
				<th><%-- <s:message code="cmm.hedofc.cd" /> --%>text 2</th>
				<td>
					<input type="text" id="" ng-model="" maxlength="" ng-value="" class="sb-input w45">
					<a href="#" id="store" class="btn_grayS ml5"><s:message code="cmm.store.select" /></a>
				</td>				
			</tr>

			<%-- form > select --%>
      <tr>  
        <th><%-- <s:message code="cmm.status" /> --%>select 1</th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchSysStatFg"
                    ng-model="sysStatFg"
                    items-source="_getComboData('sysStatFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <th>select 2</th>
        <td>
          <div class="sb-select">
          	<span class="txtIn w45 mr10">
	            <wj-combo-box
	                    id="srchSysStatFg"
	                    ng-model="sysStatFg"
	                    items-source="_getComboData('sysStatFg')"
	                    display-member-path="name"
	                    selected-value-path="value"
	                    is-editable="false"
	                    initialized="_initComboBox(s)">
	            </wj-combo-box>
            </span>
            
            <span class="txtIn w45">
	            <wj-combo-box
	                    id="srchSysStatFg"
	                    ng-model="sysStatFg"
	                    items-source="_getComboData('sysStatFg')"
	                    display-member-path="name"
	                    selected-value-path="value"
	                    is-editable="false"
	                    initialized="_initComboBox(s)">
	            </wj-combo-box>
            </span>            
          </div>
        </td>
      </tr>
			
      <%-- form > calendar/radio --%>
      <tr>
        <th><s:message code="cmm.search.month" /></th>
        <td>
          <div class="sb-select">
              <wj-input-date
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      selection-mode="Month"
                      format="yyyy/M"
                      min="2000-01-01"
                      max="2099-12-31"                                         
                      initialized="_initDateBox(s)">
              </wj-input-date>
           </div>      
        </td>      
        <th><%-- <s:message code="cmm.search.date" /> --%> 조회시간</th>
        <td>
          <div class="sb-select">
              <wj-input-date-time
                      value="startDate"
                      id="dateTime"
                      ng-model="startDate"
                      control="startDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date-time>
          </div>
      </tr>
      <tr>   
        <th><s:message code="cmm.search.date" /></th>
        <td>
          <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="rg">~</span>
            <span class="txtIn w110px">
              <wj-input-date
                      value="endDate"
                      ng-model="endDate"
                      control="endDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="chk ml10">
              <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
              <label for="chkDt">
                <s:message code="cmm.all.day" />
              </label>
            </span>
          </div>
        </td>           
        <th>라디오</th>
        <td>            
           <span class="rdo ml10">
             <input type="radio" id="rdo1" name="1" ng-model="" ng-change=""/>
             <label for="rdo1">
               <%-- <s:message code="cmm." /> --%>라디오 예
             </label>
           </span>
           <span class="rdo ml10">
             <input type="radio" id="rdo2" name="1" ng-model="" ng-change="" checked />
             <label for="rdo2">
               <%-- <s:message code="cmm." /> --%>라디오 아니오
             </label>
           </span>
         </td>
      </tr>
      <tr>
      	<th>textarea</th>
      	<td colspan="3"><textarea id="" name="" rows="5" class="w100 s13"></textarea></td>
      </tr>   
    </tbody>
  </table>
  <%-- //조회 조건 end --%>

  <br/><br/>
  
  <%-- 메뉴이름 / 조회 조건 start --%>
  <div class="searchBar flddUnfld">
    <%-- <a href="#" class="open fl">${menuNm}</a> --%>
    <a href="#" class="open fl">메뉴이름</a>
  </div>
  <%-- //메뉴이름 / 조회 조건 end --%>
  
  <%-- 조회 조건 start --%>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
			<tr>
				<th>sample</th>
				<td><input type="text" id="" ng-model="" maxlength="" ng-value="" class="sb-input w100" ></td>	
			</tr>
    </tbody>
  </table>
  <%-- //조회 조건 end --%>
  
  <%-- 조회 button --%>
  <div class="fr" style="margin-top: 10px;">
    <button class="btn_blue fr" id="searchBtn" ng-click="_pageView('loginStatusCtrl', 1)">
      <s:message code="cmm.search" />
    </button>
  </div>
  <div class="clearfix"></div>


</div>
<!-- //contents end -->

<script>

	// 검색 조건 샘플 페이지 (참고 페이지 : POS관리 > POS 설정관리 > 포스로그인현황)	
	// jsp 추가
  var sysStatFg   = ${ccu.getCommCodeExcpAll("005")};

  var orgnFg = "${orgnFg}";
  var orgnCd = "${orgnCd}";
  var pAgencyCd = "${pAgencyCd}";
  
  // js 추가
  var app = agrid.getApp();

  /**********************************************************************
   *  포스로그인현황 화면
   **********************************************************************/
  app.controller('loginStatusCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('loginStatusCtrl', $scope, $http, true));

    // 전체기간 체크박스
    $scope.isChecked = true;

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData2);
    $scope._setComboData("sysStatFg", sysStatFg);
    //$scope._getComboDataQuery('005', 'sysStatFg', 'A');

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
      $scope.startDateCombo.isReadOnly = $scope.isChecked;
      $scope.endDateCombo.isReadOnly = $scope.isChecked;
    };

    // 조회 버튼 클릭 (_broadcast)
    $scope.$on("loginStatusCtrl", function(event, data) {
      $scope.searchLoginStatusList();
      event.preventDefault();
    });
  }

  ]);
  
</script>

