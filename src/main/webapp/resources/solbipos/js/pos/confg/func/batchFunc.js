/****************************************************************
 *
 * 파일명 : batchFunc.js
 * 설  명 : 일괄기능적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.08     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

//등록 기능키 조회
function regFnkey(){
    var scope = agrid.getScope('regFnkeyCtrl');
    scope.searchRegFnkey();
}

// 닫기
function closePop(){
    var scope = agrid.getScope('regFnkeyCtrl');
    scope.close();
}

app.controller('regFnkeyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regFnkeyCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    //$scope._setComboData("bfFnkeyFg", funcFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // 등록 기능키 그리드 조회
    $scope.$on("regFnkeyCtrl", function(event, data) {

        // 매장명, 매장코드
        $("#storeInfo").text('['+data.storeCd +'] '+data.storeNm);
        $("#hdStoreCd").val(data.storeCd);

        // 등록 기능키 조회
        $scope.searchRegFnkey();
    });

    // 등록 기능키 조회
    $scope.searchRegFnkey = function(){

        var params = {};

        params.fnkeyFg = $("#bfFnkeyFg").val();
        params.fnkeyNo = $("#bfFnkeyNo").val();
        params.fnkeyNm = $("#bfFnkeyNm").val();
        params.storeCd = $("#hdStoreCd").val();
        params.regYn = 'Y';

        $scope._inquirySub("/pos/confg/func/func/getFuncKeyList.sb", params, function() {

            // 미등록 기능키 조회
            var noRegFnKeyGrid = agrid.getScope("noRegFnkeyCtrl");
            noRegFnKeyGrid._pageView('noRegFnkeyCtrl', 1);

        });
    };

    // 삭제
    $scope.delete = function(){

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "D";
                $scope.flex.collectionView.items[i].storeCd = $("#hdStoreCd").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length == 0){
            $scope._popMsg("선택된 기능키가 없습니다.");
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/pos/confg/func/func/saveFuncKey.sb", params, function(){
            $scope.searchRegFnkey();
            // 적용매장 조회 후, 미적용 매장 조회
            var addStoreScope = agrid.getScope("noRegFnkeyCtrl");
            addStoreScope._broadcast('noRegFnkeyCtrl');
        });
    };

    // 닫기
    $scope.close = function(){

        $("#bfFnkeyFg").val("");
        $("#bfFnkeyNo").val("");
        $("#bfFnkeyNm").val("");

        /*$scope.batchStoreLayer.hide();
        $scope.batchFuncLayer.hide();*/

    }

}]);

/**
 *  상품 미적용 매장 그리드 생성
 */
app.controller('noRegFnkeyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegFnkeyCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // 미등록 기능키 그리드 조회
    $scope.$on("noRegFnkeyCtrl", function(event, data) {
        $scope.searchNoRegFnkey();
        event.preventDefault();
    });

    // 미등록 기능키 조회
    $scope.searchNoRegFnkey = function(){

        var params = {};

        params.fnkeyFg = $("#bfFnkeyFg").val();
        params.fnkeyNo = $("#bfFnkeyNo").val();
        params.fnkeyNm = $("#bfFnkeyNm").val();
        params.storeCd = $("#hdStoreCd").val();
        params.regYn = 'N';

        $scope._inquirySub("/pos/confg/func/func/getFuncKeyList.sb", params, function() {
        });
    };

    // 저장
    $scope.regist = function(){

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                $scope.flex.collectionView.items[i].storeCd = $("#hdStoreCd").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length == 0){
            $scope._popMsg("선택된 기능키가 없습니다.");
            return false;
        }

        console.log('save params',params);

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/pos/confg/func/func/saveFuncKey.sb", params, function(){
            $scope.searchNoRegFnkey();
            // 미적용매장 조회 후, 적용 매장 조회
            var addStoreScope = agrid.getScope("regFnkeyCtrl");
            addStoreScope.searchRegFnkey();
        });
    };

}]);