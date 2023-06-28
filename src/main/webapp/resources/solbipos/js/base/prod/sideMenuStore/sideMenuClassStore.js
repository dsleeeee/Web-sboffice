/****************************************************************
 *
 * 파일명 : sideMenuClassStore.js
 * 설  명 : 선택분류(매장별) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.06.07     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 등록구분
var regYnAllData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"Y"},
    {"name":"미등록","value":"N"}
];
var regYnData = [
    {"name":"등록","value":"Y"},
    {"name":"미등록","value":"N"}
];

// 적용매장구분
var regStoreFgData = [
    {"name":"미사용","value":"0"},
    {"name":"제외매장","value":"1"},
    {"name":"허용매장","value":"2"}
];

/**
 *  선택분류(매장별) 조회 그리드 생성
 */
app.controller('sideMenuClassStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sideMenuClassStoreCtrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("regYnCombo", regYnAllData); // 등록구분
    $scope._setComboData("regYnChgCombo", regYnData); // 일괄변경 - 등록구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.regStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
        $scope.regYnDataMap = new wijmo.grid.DataMap(regYnData, 'value', 'name'); // 등록구분

        // 그리드 값 변경 시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "regYn") {
                    item.gChk = true;
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("sideMenuClassStoreCtrl", function(event, data) {
        $scope.searchSideMenuClassStore();
        event.preventDefault();
    });

    $scope.searchSideMenuClassStore = function() {
        if($("#sideMenuClassStoreStoreCd").val() == ""){
            $scope._popMsg(messages["cmm.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        var params = {};
        params.storeCd = $("#sideMenuClassStoreStoreCd").val();
        // params.sdselGrpCd = $("#sideMenuClassStoreSdselGrpCd").val();
        params.regYn = $scope.regYn;

        $scope._inquiryMain("/base/prod/sideMenuStore/sideMenuClassStore/getSideMenuClassStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.sideMenuClassStoreStoreShow = function () {
        $scope._broadcast('sideMenuClassStoreStoreCtrl');
    };

    // 선택그룹 선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    // $scope.sideMenuClassStoreSdselGrpShow = function () {
    //     $scope._broadcast('sideMenuClassStoreSdselGrpCtrl');
    // };

    // 일괄적용
    $scope.batchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // 적용매장구분
                if(chgGubun == "regYnChg") {
                    $scope.flex.collectionView.items[i].regYn = $scope.regYnChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // 저장
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/sideMenuStore/sideMenuClassStore/getSideMenuClassStoreSave.sb", params, function(){
                $scope.searchSideMenuClassStore();
            });
        });
    };

}]);