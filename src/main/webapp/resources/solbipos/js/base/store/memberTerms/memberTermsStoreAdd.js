/****************************************************************
 *
 * 파일명 : memberTermsStoreAdd.js
 * 설  명 : 회원약관관리 매장추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.07.06    김설아      1.0
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

    $scope.hqOfficeCd = gvHqOfficeCd;
    // 조회조건
    $scope._setComboData("hqOffice", hqList);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    // 조회 버튼 클릭
    $scope.$on("addStoreCtrl", function(event, data) {

        if($("#srchHqOffice").val() != '' || $("#srchHqOffice").val() != null || $("#srchHqOffice").val() != undefined){
            $scope.selectedHqOffice = $("#srchHqOffice").val();
        }
        $scope.addStoreSearch();
        event.preventDefault();
    });

    // 선택본사
    $scope.selectedHqOffice;
    $scope.setSelectedHqOffice = function(s) {
        $scope.selectedHqOffice = s.selectedValue;
    };
    $scope.getSelectedHqOffice = function(){
        return $scope.selectedHqOffice;
    };

    // 선택본사
    $scope.selectedSysStatFg;
    $scope.setSelectedSysStatFg = function(s) {
        $scope.selectedSysStatFg = s.selectedValue;
    };
    $scope.getSelectedSysStatFg = function(){
        return $scope.selectedSysStatFg;
    };

    // 적용매장 목록 조회
    $scope.addStoreSearch = function(){

        var params = {};
        var scope  = agrid.getScope('memberTermsCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        if( !isEmptyObject($scope.store)){
            params = $scope.store;
        }

        params.verSerNo    = ver;
        params.searchSatus = 'Y';
        params.hqOfficeCd  = $scope.hqOfficeCd;
        params.sysStatFg = $scope.sysStatFg;
        params.storeCd = $("#addStoreChoiceCd").val();
        $scope._inquiryMain("/base/store/media/applcStore/srchStoreList.sb", params, function() {
            // 적용매장 조회 후, 미적용 매장 조회
            var allStoreScope = agrid.getScope("allStoreCtrl");
            allStoreScope._pageView('allStoreCtrl', 1);

        }, false);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.addStoreChoiceShow = function () {
        $scope._broadcast('addStoreChoiceCtrl');
    };


    // 삭제
    $scope.delete = function(){

        var params = new Array();
        var scope  = agrid.getScope('memberTermsCtrl');
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
        $scope._save("/base/store/media/applcStore/removeStore.sb", params, function(){
            // 적용매장 조회 후, 미적용 매장 조회
            var addStoreScope = agrid.getScope("addStoreCtrl");
            addStoreScope._broadcast('addStoreCtrl');
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
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    // 조회 버튼 클릭
    $scope.$on("allStoreCtrl", function(event, data) {
        $scope.allStoreSearch();
        event.preventDefault();
    });

    // 미적용매장 목록 조회
    $scope.allStoreSearch = function(){

        var params = {};
        var scope  = agrid.getScope('memberTermsCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        var addStoreScope = agrid.getScope('addStoreCtrl');

        params.verSerNo    = ver;
        params.searchSatus = 'N';
        params.hqOfficeCd  = addStoreScope.hqOfficeCd;
        params.sysStatFg = addStoreScope.sysStatFg;
        params.storeCd = $("#addStoreChoiceCd").val();

        // 복수검색 기능 사용여부
        if ($("#chkMulti").prop("checked")) {
            params.chkMulti = "Y";
        }else{
            params.chkMulti = "N";
        }

        $scope._inquiryMain("/base/store/media/applcStore/srchStoreList.sb", params, function() {
        }, false);
    };


    // 저장
    $scope.save = function(){

        var params = new Array();
        var scope  = agrid.getScope('memberTermsCtrl');
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
        $scope._save("/base/store/media/applcStore/regist.sb", params, function(){
            // 적용매장 조회 후, 미적용 매장 조회
            var addStoreScope = agrid.getScope("addStoreCtrl");
            addStoreScope._broadcast('addStoreCtrl');
        });
    };
}]);
