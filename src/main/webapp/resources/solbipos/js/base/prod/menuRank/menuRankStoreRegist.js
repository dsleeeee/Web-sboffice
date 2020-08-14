/****************************************************************
 *
 * 파일명 : menuRankStoreRegist.js
 * 설  명 : 메뉴 순위 사용/미사용 매장 등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.06     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

function closePop(){
    var scope = agrid.getScope("menuRankDisplayCtrl");
    // 팝업 닫을 때, 부모창 재조회
    scope.searchUseRank();
}

/**
 *  메뉴순위 적용 매장 그리드 생성
 */
app.controller('regMenuRankStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regMenuRankStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    $scope.$on("regMenuRankStoreCtrl", function(event, data) {

        if(data !== undefined) {

            // 표시내용 관련 정보
            $("#nmcodeNm").text(data.nmcodeNm);
            $("#nmcodeCd").val(data.nmcodeCd);

            // 이전 검색조건 초기화
            $("#storeCd").val("");
            $("#storeNm").val("");
        }

        // 등록 매장 조회
        $scope.searchRegStore();
    });

    // 등록 매장 조회
    $scope.searchRegStore = function(){

        var params = {};
        params.storeCd = $("#storeCd").val();
        params.storeNm = $("#storeNm").val();
        params.nmcodeCd = $("#nmcodeCd").val();
        params.storeRegFg = 'Y';

        $scope._inquirySub("/base/prod/menuRank/display/getRegStore.sb", params, function() {

            // 미등록 매장 조회
            var noRegStoreGrid = agrid.getScope("noRegMenuRankStoreCtrl");
            noRegStoreGrid._pageView('noRegMenuRankStoreCtrl', 1);

        });
    }

    // 등록매장삭제
    $scope.delete = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].nmcodeCd = $("#nmcodeCd").val();
                $scope.flex.collectionView.items[i].nmcodeNm = $("#nmcodeNm").text();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["menuRank.require.chkNotUseStore"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/menuRank/display/deleteStore.sb", params, function(){ $scope.allSearch() });
    }

    // 매장 삭제 완료 후처리
    $scope.allSearch = function () {
        $scope.searchRegStore();
        var noRegStoreGrid = agrid.getScope("noRegMenuRankStoreCtrl");
        noRegStoreGrid.searchNoRegStore();
    };

}]);

/**
 *  메뉴순위 미등록 매장 그리드 생성
 */
app.controller('noRegMenuRankStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegMenuRankStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // 미등록 매장 그리드 조회
    $scope.$on("noRegMenuRankStoreCtrl", function(event, data) {
        $scope.searchNoRegStore();
        event.preventDefault();
    });

    // 미등록 매장 조회
    $scope.searchNoRegStore = function(){

        var params = {};
        params.storeCd = $("#storeCd").val();
        params.storeNm = $("#storeNm").val();
        params.nmcodeCd = $("#nmcodeCd").val();
        params.storeRegFg = 'N';

        $scope._inquirySub("/base/prod/menuRank/display/getRegStore.sb", params, function() {}, false);
    }

    // 매장등록
    $scope.regist = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].nmcodeCd = $("#nmcodeCd").val();
                $scope.flex.collectionView.items[i].nmcodeNm = $("#nmcodeNm").text();
                $scope.flex.collectionView.items[i].useYn = 'Y';
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["menuRank.require.chkUseStore"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/menuRank/display/insertStore.sb", params, function(){ $scope.allSearch() });
    };

    // 매장 등록 완료 후처리
    $scope.allSearch = function () {
        $scope.searchNoRegStore();
        var regStoreGrid = agrid.getScope("regMenuRankStoreCtrl");
        regStoreGrid.searchRegStore();
    };

}]);