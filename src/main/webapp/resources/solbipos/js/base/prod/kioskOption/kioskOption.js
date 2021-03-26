/****************************************************************
 *
 * 파일명 : kioskOption.js
 * 설  명 : 키오스크옵션관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 DropBoxDataMap
var useYnFgData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 옵션구분 DropBoxDataMap
var optionFgData = [
    {"name":"기본옵션","value":"0"},
    {"name":"무료옵션","value":"1"},
    {"name":"유료옵션","value":"2"}
];

// 상품 미선택시 추가버튼, '재료코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected = "N";

/**
 *  상품목록 조회 그리드 생성
 */
app.controller('kioskOptionCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskOptionCtrl', $scope, $http, true));

    $scope.isChecked = true;

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;
    };

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYnFgData); // 사용여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "prodCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 상품코드 클릭시 상세정보 조회
                if ( col.binding === "prodCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.prodCd = selectedRow.prodCd;
                    params.prodNm = selectedRow.prodNm;

                    var storeScope = agrid.getScope('kioskOptionDetailCtrl');
                    storeScope._broadcast('kioskOptionDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("kioskOptionCtrl", function(event, data) {
        $scope.searchKioskOption();
        event.preventDefault();
    });

    $scope.searchKioskOption = function() {
        var params = {};
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');

        $scope._inquiryMain("/base/prod/kioskOption/kioskOption/getKioskOptionList.sb", params, function() {
            addSelected = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('kioskOptionDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('kioskOptionDetailCtrl', null);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

}]);

/**
 *  키오스크옵션관리 조회 그리드 생성
 */
app.controller('kioskOptionDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskOptionDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.optionFgDataMap = new wijmo.grid.DataMap(optionFgData, 'value', 'name'); // 옵션구분
    };

    // <-- 검색 호출 -->
    $scope.$on("kioskOptionDetailCtrl", function(event, data) {
        $scope.setSelectedStore(data);

        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $("#lblProdCd").text("[ " + $scope.selectedStore.prodCd + " ]");
            $("#lblProdNm").text($scope.selectedStore.prodNm);
            $scope.searchKioskOptionDetail();

        } else if(addSelected === "N") {
            $("#lblProdCd").text("");
            $("#lblProdNm").text("");
        }
        event.preventDefault();
    });

    $scope.searchKioskOptionDetail = function() {
        var params = {};
        params.prodCd = $scope.selectedStore.prodCd;

        $scope._inquiryMain("/base/prod/kioskOption/kioskOption/getKioskOptionDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(store) {
        $scope.selectedStore = store;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // <-- 추가 -->
    $scope.add = function() {
        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }

        if(addSelected === "Y") {
            $scope.wjKioskOptionProdLayer.show(true);
            event.preventDefault();
        } else if(addSelected === "N" ) {
            $scope._popMsg(messages["kioskOption.prodCdBlank"]);
            return false;
        }
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 재료-상품 등록 팝업 핸들러 추가
        $scope.wjKioskOptionProdLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('kioskOptionProdCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });
    // <-- //추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }

        if(addSelected === "Y") {
            $scope._popConfirm(messages["cmm.choo.delete"], function() {
                // 파라미터 설정
                var params = new Array();
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    if($scope.flex.collectionView.items[i].gChk) {
                        $scope.flex.collectionView.items[i].prodCd = $scope.selectedStore.prodCd;
                        params.push($scope.flex.collectionView.items[i]);
                    }
                }

                if(params.length <= 0) {
                    s_alert.pop(messages["cmm.not.select"]);
                    return;
                }

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save("/base/prod/kioskOption/kioskOption/getKioskOptionSaveDelete.sb", params, function(){ $scope.allSearch() });
            });

        } else if(addSelected === "N" ) {
            $scope._popMsg(messages["kioskOption.prodCdBlank"]);
            return false;
        }
    };
    // <-- //그리드 행 삭제 -->

    // 위로 옮기기 버튼
    $scope.rowMoveUp = function() {
        var movedRows = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (i > 0 && item.gChk) {
                if (!$scope.flex.collectionView.items[i - 1].gChk) {
                    movedRows = i - 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // 아래로 옮기기 버튼
    $scope.rowMoveDown = function() {
        var movedRows = 0;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
                if (!$scope.flex.collectionView.items[i + 1].gChk) {
                    movedRows = i + 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // 저장
    $scope.save = function() {
        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }

        if(addSelected === "Y") {
            $scope._popConfirm(messages["cmm.choo.save"], function() {
                var dispSeq = 1;

                // 파라미터 설정
                var params = new Array();
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    $scope.flex.collectionView.items[i].prodCd = $scope.selectedStore.prodCd;
                    $scope.flex.collectionView.items[i].dispSeq = dispSeq;
                    params.push($scope.flex.collectionView.items[i]);
                    dispSeq++;
                }

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save("/base/prod/kioskOption/kioskOption/getKioskOptionSaveUpdate.sb", params, function(){ $scope.allSearch() });
            });

        } else if(addSelected === "N" ) {
            $scope._popMsg(messages["kioskOption.prodCdBlank"]);
            return false;
        }
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchKioskOptionDetail();
    };
}]);