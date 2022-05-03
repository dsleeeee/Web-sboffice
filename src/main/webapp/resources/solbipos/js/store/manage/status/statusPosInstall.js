/****************************************************************
 *
 * 파일명 : statusPosInstall.js
 * 설  명 : 매장현황 POS설치현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 현재상태 DropBoxDataMap
var instFgData = [
    {"name":"전체","value":""},
    {"name":"설치의뢰","value":"0"},
    {"name":"신규설치","value":"1"},
    {"name":"재설치","value":"2"}
];

/**
 *  POS설치현황 그리드 생성
 */
app.controller('statusPosInstallCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusPosInstallCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("instFg", instFgData); //현재상태

    // 검색조건에 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // 전체기간 체크박스
    $scope.isChecked = true;

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.instFgDataMap = new wijmo.grid.DataMap(instFgData, 'value', 'name'); //현재상태

        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // <-- 검색 호출 -->
    $scope.$on("statusPosInstallCtrl", function(event, data) {
        $scope.searchStatusPosInstall();
        event.preventDefault();
    });

    // POS설치현황 그리드 조회
    $scope.searchStatusPosInstall = function() {
        var params = {};
        // 조회일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        if(orgnFg != null && orgnFg == 'AGENCY') {
            params.agencyCd = orgnCd;
        }
        params.listScale = $scope.listScalePosInstall;

        $scope._inquiryMain("/store/manage/status/posInstl/getStatusPosInstallList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

  // 엑셀 다운로드
  $scope.excelDownloadStatusPosinstall = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      },
          messages["storeStatus.posInstall"] + '_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);