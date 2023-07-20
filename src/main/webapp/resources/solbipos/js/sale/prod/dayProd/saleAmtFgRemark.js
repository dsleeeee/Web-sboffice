/****************************************************************
 *
 * 파일명 : saleAmtFgRemarkPopup.js
 * 설  명 : 설명팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.13     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('saleAmtFgRemarkPopupCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAmtFgRemarkPopupCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("saleAmtFgRemarkPopupCtrl", function(event, data) {
        $scope.getsaleAmtFgRemarkList();
    });

    // 프린터그룹 조회
    $scope.getsaleAmtFgRemarkList = function(){
        // 파라미터
        var params = {};
        $scope._inquirySub("/sale/prod/dayProd/dayProd/getSaleAmtFgRemarkList.sb", params);
    };

    // 닫기
    $scope.close = function(){
        $scope.saleAmtFgRemarkPopupLayer.hide();
    }

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

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
            }, '금액보정1_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
}]);