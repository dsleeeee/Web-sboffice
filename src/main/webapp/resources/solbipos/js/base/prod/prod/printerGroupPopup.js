/****************************************************************
 *
 * 파일명 : printerGroupPopup.js
 * 설  명 : 프린터 리스트 팝업(상품신규등록시 상품그룹-프린터연결을 위한) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.08     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('printerGroupPopupCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('printerGroupPopupCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("printerGroupPopupCtrl", function(event, data) {
        $scope.getPrinterGroupList();
    });

    // 프린터그룹 조회
    $scope.getPrinterGroupList = function(){
        // 파라미터
        var params = {};
        $scope._inquirySub("/base/prod/prodKitchenprintLink/printerGroup/getPrinterGroupList.sb", params);
    };

    // 프린터 등록
    $scope.save = function(){
        if($scope.flex.rows.length <= 0){ // 조회 된 건이 없을때
            $scope.close();
        } else {
            var scope = agrid.getScope('prodModifyCtrl');
            // 파라미터
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].prodCd = scope.getKitchenprrint();
                    $scope.flex.collectionView.items[i].status = 'I';
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
            console.log(params);
            $scope._save("/base/prod/prodKitchenprintLink/printerGroup/saveProdMapping.sb", params, function () {
                $scope.printerGroupPopupLayer.hide();
                scope.prodModifyLayer.hide();
            });
        }
    };

    // 닫기
    $scope.close = function(){
        var scope = agrid.getScope('prodModifyCtrl');
        $scope.printerGroupPopupLayer.hide();
        scope.prodModifyLayer.hide();
    }
}]);