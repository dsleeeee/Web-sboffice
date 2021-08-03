/****************************************************************
 *
 * 파일명 : multiStoreGroup.js
 * 설  명 : 다중매장그룹관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.30     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var useYnAllFgData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 그룹 grid
app.controller('multiStoreGroupCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('multiStoreGroupCtrl', $scope, $http, true));

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnAll", useYnAllFgData);

    // 버튼 visible 셋팅
    $("#btnAddGroup").css("display", "none");
    $("#btnSaveGroup").css("display", "none");
    $("#btnDelStoreMapping").css("display", "none");
    $("#btnSaveStoreMapping").css("display", "none");
    $("#btnSearchStore").css("display", "none");
    $("#btnRegStore").css("display", "none");

    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'multistoreCd') {
                    if(item.storeGroupCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 카테고리 코드 클릭 시, 키맵과 상품 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "multistoreCd") {
                    if(selectedRow.multistoreCd !== '자동채번') {

                        // 선택한 그룹코드 갖고있기
                        $("#hdMultiStoreCd").val(selectedRow.multistoreCd);

                        // 선택한 그룹 명시
                        $("#lblGroup").text("[" + selectedRow.multistoreCd + "] " + selectedRow.multistoreNm);

                        // 그룹-매장연결 grid  조회
                        //$scope._broadcast('multiStoreMappingCtrl');

                        // 버튼 visible 셋팅
                        $("#btnDelStoreMapping").css("display", "");
                        $("#btnSaveStoreMapping").css("display", "");
                        $("#btnSearchStore").css("display", "");
                        $("#btnRegStore").css("display", "");

                        event.preventDefault();
                    }
                }
            }
        });

        // 그룹조회
        $scope.searchGroup();

    };

    $scope.$on("multiStoreGroupCtrl", function(event, data) {

        // 그룹조회
        $scope.searchGroup();
        event.preventDefault();
    });

    // 그룹조회
    $scope.searchGroup = function () {

        var params = [];
        params.multistoreNm = $scope.multistoreNm;
        params.useYn = $scope.useYn;

        $scope._inquirySub("/base/store/multiStoreGroup/multiStoreGroup/getMultiStoreGroup.sb", params, function() {

            // 선택한 그룹 초기화
            $("#hdMultiStoreCd").val("");
            $("#lblGroup").text("");

            // 버튼 visible 셋팅 - 그룹 grid 버튼은 보이고 나머지 grid 버튼은 숨길것.
            $("#btnAddGroup").css("display", "");
            $("#btnSaveGroup").css("display", "");
            $("#btnDelStoreMapping").css("display", "none");
            $("#btnSaveStoreMapping").css("display", "none");
            $("#btnSearchStore").css("display", "none");
            $("#btnRegStore").css("display", "none");

            // 그룹-매장매핑 그리드 초기화
            var multiStoreMappingGrid = agrid.getScope('multiStoreMappingCtrl');
            multiStoreMappingGrid.multiStoreMappingGridDefault();

            // 매장선택 그리드 및 input 값 초기화
            var multiStoreSelectGrid = agrid.getScope('multiStoreSelectCtrl');
            multiStoreSelectGrid.multiStoreSelectGridDefault();

            $("#searchStoreCd").val("");
            $("#searchStoreNm").val("");
            multiStoreSelectGrid.sysStatFgCombo.selectedIndex = 0;
            
        }, false);
    };

    // 그룹추가
    $scope.addGroup = function () {

        var params = [];
        params.multistoreCd = '자동채번';
        params.multistoreNm = '';
        params.useYn = 'Y';
        params.remark = '';

        // 행추가
        $scope._addRow(params, 1);
    }

    // 그룹저장
    $scope.saveGroup = function () {

        $scope.flex.collectionView.commitEdit();

        // 생성, 수정 Validation Check
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if ($scope.flex.collectionView.items[i].multistoreNm === null || $scope.flex.collectionView.items[i].multistoreNm === '') {
                $scope._popMsg(messages['multiStoreGroup.require.multiStoreGroupNm.msg']); // 그룹명을 입력해주세요.
                return;
            }
        }

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/multiStoreGroup/multiStoreGroup/saveMultiStoreGroup.sb', params, function () {

            // 그룹조회
            $scope.searchGroup();

        });

    };

}]);

// 그룹-매장매핑 grid
app.controller('multiStoreMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('multiStoreMappingCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {

    };

    $scope.$on("multiStoreMappingCtrl", function(event, data) {

        // 그룹 - 매장매핑 조회
        $scope.searchStoreMapping();
        event.preventDefault();

    });

    // 그룹 - 매장매핑 조회
    $scope.searchStoreMapping = function () {

        var params = [];
        params.multistoreCd = $("#hdMultiStoreCd").val();

        $scope._inquirySub("/base/store/multiStoreGroup/multiStoreGroup/getStoreMapping.sb", params, function() {

            // 매장선택 조회
            var multiStoreSelectGrid = agrid.getScope("multiStoreSelectCtrl");
            multiStoreSelectGrid._pageView('multiStoreSelectCtrl', 1);

        }, false);
    };

    // 그룹 - 매장매핑 삭제
    $scope.delStoreMapping = function(){

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].multistoreCd = $("#hdMultiStoreCd").val();
                $scope.flex.collectionView.items[i].status = 'D';

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/multiStoreGroup/multiStoreGroup/saveStoreMapping.sb", params, function(){

            // 그룹 - 매장매핑 재조회
            $scope.searchStoreMapping();

            // 매장선택 재조회
            var multiStoreSelectGrid = agrid.getScope("multiStoreSelectCtrl");
            multiStoreSelectGrid._pageView('multiStoreSelectCtrl', 1);

        });
    };

    // 그룹 - 매장매핑 수정
    $scope.saveStoreMapping = function(){

        $scope.flex.collectionView.commitEdit();

        // 생성, 수정 Validation Check
       /* for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if ($scope.flex.collectionView.items[i].saleUprc === null || $scope.flex.collectionView.items[i].saleUprc === '') {
                $scope._popMsg(messages['storeType.require.saleUprc.msg']); // 판매가를 입력해주세요.
                return;
            }
        }*/

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].multistoreCd = $("#hdMultiStoreCd").val();
            $scope.flex.collectionView.itemsEdited[i].status = 'U';

            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/multiStoreGroup/multiStoreGroup/saveStoreMapping.sb", params, function(){

            // 그룹 - 매장매핑 재조회
            $scope.searchStoreMapping();

            // 매장선택 재조회
            var multiStoreSelectGrid = agrid.getScope("multiStoreSelectCtrl");
            multiStoreSelectGrid._pageView('multiStoreSelectCtrl', 1);

        });

    };

    // 그룹 - 매장매핑 그리드 초기화
    $scope.multiStoreMappingGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 매장선택 grid
app.controller('multiStoreSelectCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('multiStoreSelectCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData('sysStatFg', sysStatFg);

    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    $scope.$on("multiStoreSelectCtrl", function(event, data) {

        // 매장선택 조회
        $scope.searchStore();
        event.preventDefault();
    });

    // 매장선택 조회
    $scope.searchStore = function(){

        var params = [];
        params.storeCd = $("#searchStoreCd").val();
        params.storeNm = $("#searchStoreNm").val();
        params.sysStatFg = $scope.sysStatFgCombo.selectedValue;
        params.multistoreCd = $("#hdMultiStoreCd").val();

        $scope._inquirySub("/base/store/multiStoreGroup/multiStoreGroup/getStoreList.sb", params, function() {}, false);
    };

    // 매장등록
    $scope.regStore = function () {

        $scope.flex.collectionView.commitEdit();

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].multistoreCd = $("#hdMultiStoreCd").val();
                $scope.flex.collectionView.items[i].status = 'I';

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/multiStoreGroup/multiStoreGroup/saveStoreMapping.sb", params, function(){

            // 매장선택 재조회
            $scope.searchStore();

            // 그룹 - 매장매핑 재조회
            var multiStoreMappingGrid= agrid.getScope('multiStoreMappingCtrl');
            multiStoreMappingGrid._pageView('multiStoreMappingCtrl', 1);
            
        });
    };

    // 매장선택 그리드 초기화
    $scope.multiStoreSelectGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);
