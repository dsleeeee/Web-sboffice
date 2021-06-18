/****************************************************************
 *
 * 파일명 : dlvrAddr.js
 * 설  명 : 배달권역관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.06     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  등록 배달권역
 */
app.controller('dlvrAddrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrAddrCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        var params = {};
        $scope._inquirySub("/base/store/dlvrAddr/list.sb", params);
    };

    // <-- 검색 호출 -->
    $scope.$on("dlvrAddrCtrl", function(event, data) {
        $scope.searchDlvrAddr();
        event.preventDefault();
    });

    $scope.searchDlvrAddr = function(){
        var params = {};
        $scope._inquirySub("/base/store/dlvrAddr/list.sb", params, function() {
            var scope = agrid.getScope('dlvrAddrCodeCtrl');
            scope._broadcast('dlvrAddrCodeCtrl');
        }, false);
    };

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
        // 저장
        $scope.save();
    };
    // <-- //그리드 행 삭제 -->

    // <-- 그리드 저장 -->
    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/dlvrAddr/delDlvrAddr.sb", params, function(){
            $scope.searchDlvrAddr();
            var scope = agrid.getScope('dlvrAddrCodeCtrl');
            scope._gridDataInit();
            scope._broadcast('dlvrAddrCodeCtrl');
        });
    };
}]);


/**
 *  미등록 배달권역
 */
app.controller('dlvrAddrCodeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrAddrCodeCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("dlvrAddrCodeCtrl", function(event, data) {
        if(nvl($("#srchAddrNm").val(), '') === ''){
            $scope._popMsg(messages['dlvrAddr.dlvrAddrNm.msg']);
            return;
        }
        $scope.searchdlvrAddrCode();
        event.preventDefault();
    });

    $scope.searchdlvrAddrCode = function(){
        var params = {};
        params.addrNm = $("#srchAddrNm").val();
        $scope._inquirySub("/base/store/dlvrAddr/codeList.sb", params);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 행 삭제 -->
    $scope.add = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
        // 저장
        $scope.save();
    };
    // <-- //그리드 행 삭제 -->

    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/dlvrAddr/addDlvrAddr.sb", params, function(){
            var scope = agrid.getScope('dlvrAddrCtrl');
            scope.searchDlvrAddr();
        });
    };
}]);