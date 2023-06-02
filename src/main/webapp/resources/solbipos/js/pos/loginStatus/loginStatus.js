/****************************************************************
 *
 * 파일명 : loginStatus.js
 * 설  명 : 포스로그인현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.05     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// NXPOS버전구분
var nxposVerNo = [
  {"name":"기존버전","value":"1"},
  {"name":"NXPOS2","value":"2"}
];

// 포스버전구분
var verTypeFg = [
    {"name":"NXPOS VER1","value":"1"},
    {"name":"NXPOS VER2","value":"2"}
];

/**********************************************************************
 *  포스로그인현황 화면
 **********************************************************************/
app.controller('loginStatusCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('loginStatusCtrl', $scope, $http, true));

  // 전체기간 체크박스
  $scope.isChecked = false;

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData2);
  $scope._setComboData("sysStatFg", sysStatFg);
  //$scope._getComboDataQuery('005', 'sysStatFg', 'A');

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;
  };

  $scope.initGrid = function (s, e) {
    $scope.verTypeFgDataMap = new wijmo.grid.DataMap(verTypeFg, 'value', 'name');      // NXPOS버전구분
    $scope.nxposVerNoDataMap = new wijmo.grid.DataMap(nxposVerNo, 'value', 'name');    // 포스버전구분
  };

  // 조회 버튼 클릭 (_broadcast)
  $scope.$on("loginStatusCtrl", function(event, data) {
    $scope.searchLoginStatusList();
    event.preventDefault();
  });

  // 포스 로그인현황 조회
  $scope.searchLoginStatusList = function(){

    // console.log(isEmptyObject($scope.hqOfficeCd));
    // console.log(isEmptyObject($scope.hqOfficeNm));
    // console.log(isEmptyObject($scope.storeCd));
    // console.log(isEmptyObject($scope.storeNm));
    //
    // if( isEmptyObject($scope.hqOfficeCd) || isEmptyObject($scope.hqOfficeNm)
    //  || isEmptyObject($scope.storeCd) || isEmptyObject($scope.storeNm)) {
    //   $scope._popMsg("검색조건을 입력해주세요.");
    //   return false;
    // }

    var startDt = new Date(wijmo.Globalize.format($scope.startDate, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (1000 * 60 * 60 * 24); // 시 * 분 * 초 * 밀리세컨

    // 조회일자 최대 한달(31일) 제한
    if (diffDay > 31) {
        $scope._popMsg(messages['cmm.dateOver.1month.error']);
        return false;
    }

    console.log('$scope.listScale : '+ $scope.listScale);

    var params = {};
    params.listScale = $scope.listScale;
    params.curr = $scope._getPagingInfo('curr');
    params.orgnFg = orgnFg;
    params.pAgencyCd = pAgencyCd;
    params.agencyCd = orgnCd;
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
    }
    params.hqOfficeCd = $("#srchHqOfficeCd").val();
    params.hqOfficeNm = $("#srchHqOfficeNm").val();
    params.storeCd = $("#srchStoreCd").val();
    params.storeNm = $("#srchStoreNm").val();
    params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;

    $scope._inquiryMain("/pos/confg/loginStatus/loginStatus/list.sb", params, function() {
    });
  };
}

]);
