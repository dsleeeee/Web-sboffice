/****************************************************************
 *
 * 파일명 : patchStoreDtl.js
 * 설  명 : 버전패치현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.05.28     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('patchStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('patchStoreDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("patchStoreDtlCtrl", function(event, data) {
        $scope.wjPatchStoreDtlLayer.show(true);
        $scope.patchFg = data.patchFg;
        $scope.searchPatchStoreDtlList(data);
        event.preventDefault();
    });

    $scope.searchPatchStoreDtlList = function(data){
        var params = {};
        params = data;

        $scope._inquiryMain("/store/storeMoms/storeVerPatch/storeVerPatch/getPatchStoreDtlList.sb", params, function() {}, false);
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }
        var title = '';
        if($scope.patchFg === "Y"){
            title = '패치완료';
        }else{
            title = '패치미완료';
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, title + '_매장현황_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);