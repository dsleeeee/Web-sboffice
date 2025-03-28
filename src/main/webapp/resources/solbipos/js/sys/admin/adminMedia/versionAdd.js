/****************************************************************
 *
 * 파일명 : storeAdd.js
 * 설  명 : (관리자) 듀얼모니터 영상관리 탭 - 적용버전 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.03.25     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 탭 변경
function changeTab2(tabFg){
    var scope = agrid.getScope("regVersionCtrl");
    scope.changeTab(tabFg);
}

// 조회
function searchVersion(){
    var scope = agrid.getScope("regVersionCtrl");
    scope._pageView('regVersionCtrl', 1);
}

/**********************************************************************
 *  적용매장 그리드
 **********************************************************************/
app.controller('regVersionCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regVersionCtrl', $scope, $http,  false));

    $scope._setComboData("progFgCombo", progFgAll);
    $scope._setComboData("progDetailFgCombo", progDetailFgAll);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.progFgDataMap     = new wijmo.grid.DataMap(progFg, 'value', 'name');
        $scope.progDetailFgDataMap  = new wijmo.grid.DataMap(progDetailFg, 'value', 'name');
    };

    // 조회 버튼 클릭
    $scope.$on("regVersionCtrl", function(event, data) {

        $scope.getSearchRegVersion();
        event.preventDefault();
    });

    // 적용매장 목록 조회
    $scope.getSearchRegVersion = function(){

        var params = {};
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        // if( !isEmptyObject($scope.store)){
        //     params = $scope.store;
        // }


        params.adverFileNo  = ver;
        params.searchSatus  = 'Y';
        params.verSerNo     = $("#adminVerSerNo").val();
        params.verSerNm     = $("#adminVerSerNm").val();
        params.progFg       = $scope.srchProgFgCombo.selectedValue;
        params.progDetailFg = $scope.srchProgDetailFgCombo.selectedValue;

        $scope._inquiryMain("/sys/admin/adminMedia/adminMedia/getSearchRegVersionList.sb", params, function() {
            // 적용매장 조회 후, 미적용 매장 조회
            var allVersionScope = agrid.getScope("allVersionCtrl");
            allVersionScope.getSearchAllVersion(params); // 미적용매장 조회시, 본사권한은 검색조건 매장브랜드 값 넘기기

        }, false);
    };

    // 삭제
    $scope.delete = function(){

        var params = new Array();
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].adverFileNo = ver;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length == 0){
            $scope._popMsg("선택된 매장이 없습니다.");
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/admin/adminMedia/adminMedia/getRemoveVersion.sb", params, function(){
            // 적용매장 조회 후, 미적용 매장 조회
            var addStoreScope = agrid.getScope("regVersionCtrl");
            addStoreScope._broadcast('regVersionCtrl');
            var scope = agrid.getScope("adminMediaCtrl");
            scope.getMediaList();

        });
    };

    //탭변경
    $scope.changeTab = function(tabFg){
        $scope.versionAddLayer.hide();
        if(tabFg === 'I') {
            $scope.versionInfoDetailLayer.show();
        }else{
            $scope.storeAddLayer.show(true, function () {
                // 탭 닫을때 그리드 초기화
                var sScope = agrid.getScope("addStoreCtrl");
                sScope.dtlGridDefault();
                var nScope = agrid.getScope("allStoreCtrl");
                nScope.dtlGridDefault();

                $("#adminHqOfficeCd").val("");
                $("#adminHqOfficeNm").val("");
                $("#adminStoreCd").val("");
                $("#adminStoreNm").val("");
            });
        }
    };

    // 상세 그리드 초기화
    $scope.dtlGridDefault = function () {
        $timeout(function () {
            var cv          = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data     = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);


/**********************************************************************
 *  미적용버전 그리드
 **********************************************************************/
app.controller('allVersionCtrl', ['$scope', '$http','$timeout', function ($scope, $http,$timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('allVersionCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.progFgDataMap     = new wijmo.grid.DataMap(progFg, 'value', 'name');
        $scope.progDetailFgDataMap  = new wijmo.grid.DataMap(progDetailFg, 'value', 'name');
    };

    // 조회 버튼 클릭
    $scope.$on("allVersionCtrl", function(event, data) {
        $scope.getSearchAllVersion();
        event.preventDefault();
    });

    // 미적용매장 목록 조회
    $scope.getSearchAllVersion = function(data){

        var params = {};
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        params.adverFileNo  = ver;
        params.searchSatus  = 'N';
        params.verSerNo     = data.verSerNo;
        params.verSerNm     = data.verSerNm;
        params.progFg       = data.progFg;
        params.progDetailFg = data.progDetailFg;

        $scope._inquiryMain("/sys/admin/adminMedia/adminMedia/getSearchRegVersionList.sb", params, function() {
        }, false);
    };


    // 저장
    $scope.save = function(){

        var params = new Array();
        var scope  = agrid.getScope('adminMediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].adverFileNo = ver;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length == 0){
            $scope._popMsg("선택된 매장이 없습니다.");
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/admin/adminMedia/adminMedia/getRegistVersion.sb", params, function(){
            // 적용매장 조회 후, 미적용 매장 조회
            var addStoreScope = agrid.getScope("regVersionCtrl");
            addStoreScope._broadcast('regVersionCtrl');
            var scope = agrid.getScope("adminMediaCtrl");
            scope.getMediaList();

        });
    };

    // 상세 그리드 초기화
    $scope.dtlGridDefault = function () {
        $timeout(function () {
            var cv          = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data     = cv;
            $scope.flex.refresh();
        }, 10);
    };
}]);
