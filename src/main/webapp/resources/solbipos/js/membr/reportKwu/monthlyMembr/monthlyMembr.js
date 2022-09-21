/****************************************************************
 *
 * 파일명 : monthlyMembr.js
 * 설  명 : 월별회원등록현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 성별 DropBoxDataMap
var vGendrFg = [
    {"name":"남","value":"M"},
    {"name":"여","value":"F"}
];

/**
 *  월별회원등록현황 조회 그리드 생성
 */
app.controller('monthlyMembrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthlyMembrCtrl', $scope, $http, true));

    // 검색조건 조회월
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 데이터 Set
    $scope._setComboData("classFgCombo", vComboClassFg);   // 강습구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.gendrFgDataMap = new wijmo.grid.DataMap(vGendrFg, 'value', 'name');      // 성별
        $scope.skateFgDataMap = new wijmo.grid.DataMap(vSkateFg, 'value', 'name');      // 스케이트종류
        $scope.classFgDataMap = new wijmo.grid.DataMap(vClassFg, 'value', 'name');      // 강습구분
        $scope.teacherCdDataMap = new wijmo.grid.DataMap(vTeacherCd, 'value', 'name');  // 강사명
    };

    //
    $scope.$on("monthlyMembrCtrl", function(event, data) {

        // 파라미터
        var params       = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM'); // 조회월
        params.classFg = $scope.classFgCombo.selectedValue;                     // 강습구분

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/membr/reportKwu/monthlyMembr/getMonthlyMembrList.sb", params, function() {});
    });

    // 엑셀 다운로드
    $scope.excelDownload = function () {
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
              }, '월별회원등록현황' + getToday() + '.xlsx', function () {
                $timeout(function () {
                  $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
              });
        }, 10);
    };

}]);