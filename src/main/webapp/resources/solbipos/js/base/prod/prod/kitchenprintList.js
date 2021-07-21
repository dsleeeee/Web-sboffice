/****************************************************************
 *
 * 파일명 : kitchenprintList.js
 * 설  명 : 프린터 리스트 팝업(상품신규등록시 프린터연결을 위한) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.15     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('kitchenprintLinkCtrl', ['$scope', '$http', function ($scope, $http) {

    var prodInfo;

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kitchenprintLinkCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("kitchenprintLinkCtrl", function(event, data) {
        $scope.getKitchenprintList();
    });

    // 매장목록 조회
    $scope.getKitchenprintList = function(){
        // 파라미터
        var params = {};
        $scope._inquirySub("/base/prod/prod/prod/getKitchenprintList.sb", params);
    };

    // 프린터 등록
    $scope.save = function(){
        if($scope.flex.rows.length <= 0){ // 조
            $scope.close();
        } else {
            var scope = agrid.getScope('prodModifyCtrl');
            // 파라미터
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].prodCd = scope.getKitchenprrint();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
            $scope._save("/base/prod/prod/prod/kitchenprintLink.sb", params, function () {
                $scope.kitchenprintLinkLayer.hide();
                scope.prodModifyLayer.hide();
            });
        }
    };

    // 프린터 등록
    $scope.close = function(){
        var scope = agrid.getScope('prodModifyCtrl');
        $scope.kitchenprintLinkLayer.hide();
        scope.prodModifyLayer.hide();
    }
}]);