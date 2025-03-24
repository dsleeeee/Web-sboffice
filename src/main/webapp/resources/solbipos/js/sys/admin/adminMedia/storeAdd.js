/****************************************************************
 *
 * 파일명 : storeAdd.js
 * 설  명 : (관리자) 듀얼모니터 영상관리 탭 - 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.03.07     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 탭 변경
function changeTab(){
    var scope = agrid.getScope("addStoreCtrl");
    scope.changeTab();
}

// 조회
function search(){
    var scope = agrid.getScope("addStoreCtrl");
    scope._pageView('addStoreCtrl', 1);
}

/**********************************************************************
 *  적용매장 그리드
 **********************************************************************/
app.controller('addStoreCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('addStoreCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    // 조회 버튼 클릭
    $scope.$on("addStoreCtrl", function(event, data) {

        $scope.addStoreSearch();
        event.preventDefault();
    });

    // 적용매장 목록 조회
    $scope.addStoreSearch = function(){

        var params = {};
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        // if( !isEmptyObject($scope.store)){
        //     params = $scope.store;
        // }


        params.verSerNo     = ver;
        params.searchSatus  = 'Y';
        params.hqOfficeCd   = $("#adminHqOfficeCd").val();
        params.hqOfficeNm   = $("#adminHqOfficeNm").val();
        params.storeCd      = $("#adminStoreCd").val();
        params.storeNm      = $("#adminStoreNm").val();
        
        $scope._inquiryMain("/sys/admin/adminMedia/adminMedia/srchStoreList.sb", params, function() {
            // 적용매장 조회 후, 미적용 매장 조회
            var allStoreScope = agrid.getScope("allStoreCtrl");
            allStoreScope.allStoreSearch(); // 미적용매장 조회시, 본사권한은 검색조건 매장브랜드 값 넘기기
            //allStoreScope._pageView('allStoreCtrl', 1);

        }, false);
    };

    // 삭제
    $scope.delete = function(){

        var params = new Array();
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].verSerNo = ver;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length == 0){
            $scope._popMsg("선택된 매장이 없습니다.");
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/admin/adminMedia/adminMedia/getRemoveStore.sb", params, function(){
            // 적용매장 조회 후, 미적용 매장 조회
            var addStoreScope = agrid.getScope("addStoreCtrl");
            addStoreScope._broadcast('addStoreCtrl');

            var scope = agrid.getScope("adminMediaCtrl");
            scope.getMediaList();
        });
    };

    //탭변경
    $scope.changeTab = function(){
        $scope.storeAddLayer.hide();
        $scope.versionInfoDetailLayer.show();
    };

}]);


/**********************************************************************
 *  미적용매장 그리드
 **********************************************************************/
app.controller('allStoreCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('allStoreCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("sysStatFg", sysStatFgTotal);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    // 조회 버튼 클릭
    $scope.$on("allStoreCtrl", function(event, data) {
        $scope.allStoreSearch(data);
        event.preventDefault();
    });

    // 미적용매장 목록 조회
    $scope.allStoreSearch = function(){

        var params = {};
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        var addStoreScope = agrid.getScope('addStoreCtrl');

        params.verSerNo     = ver;
        params.searchSatus  = 'N';
        params.hqOfficeCd   = $("#adminHqOfficeCd").val();
        params.hqOfficeNm   = $("#adminHqOfficeNm").val();
        params.storeCd      = $("#adminStoreCd").val();
        params.storeNm      = $("#adminStoreNm").val();

        $scope._inquiryMain("/sys/admin/adminMedia/adminMedia/srchStoreList.sb", params, function() {
        }, false);
    };


    // 저장
    $scope.save = function(){

        var params = new Array();
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].verSerNo = ver;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length == 0){
            $scope._popMsg("선택된 매장이 없습니다.");
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/admin/adminMedia/adminMedia/getRegistStore.sb", params, function(){
            // 적용매장 조회 후, 미적용 매장 조회
            var addStoreScope = agrid.getScope("addStoreCtrl");
            addStoreScope._broadcast('addStoreCtrl');

            var scope = agrid.getScope("adminMediaCtrl");
            scope.getMediaList();
        });
    };
}]);
