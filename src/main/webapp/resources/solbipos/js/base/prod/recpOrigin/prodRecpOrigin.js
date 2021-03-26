/****************************************************************
 *
 * 파일명 : prodRecpOrigin.js
 * 설  명 : 상품-원산지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부
var useYnComboData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 사용여부
var recpOriginUseYnComboData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"Y"},
    {"name":"미등록","value":"N"}
];

// 재료 및 원산지 등록에 추가버튼, '재료코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelectedProd = "N";

/**
 *  상품-원산지관리 그리드 생성
 */
app.controller('prodRecpOriginCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodRecpOriginCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnCombo", useYnComboData); // 사용여부
    $scope._setComboData("recpOriginUseYnCombo", recpOriginUseYnComboData); // 원산지등록 사용여부

    $scope.isChecked = true;

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 상품코드
                if (col.binding === "prodCd") {
                    // var item = s.rows[e.row].dataItem;
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
                    params.hqBrandCd = selectedRow.hqBrandCd;

                    var storeScope = agrid.getScope('prodRecpOriginDetailCtrl');
                    storeScope._broadcast('prodRecpOriginDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("prodRecpOriginCtrl", function(event, data) {
        $scope.searchProdRecpOrigin();
        event.preventDefault();
    });

    $scope.searchProdRecpOrigin = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.chkDt = $scope.isChecked;
        params.useYn = $scope.useYn;
        params.recpOriginUseYn = $scope.recpOriginUseYn;

        $scope._inquiryMain("/base/prod/recpOrigin/prodRecpOrigin/getProdRecpOriginList.sb", params, function() {
            addSelectedProd = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('prodRecpOriginDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('prodRecpOriginDetailCtrl', null);
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
 *  재료 및 원산지 등록 그리드 생성
 */
app.controller('prodRecpOriginDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodRecpOriginDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("prodRecpOriginDetailCtrl", function(event, data) {
        $scope.setSelectedProd(data);

        if(!$.isEmptyObject($scope.selectedProd) ) {
            addSelectedProd = "Y";
        }
        if(addSelectedProd === "Y") {
            $("#lblProd").text(" ( [ " + $scope.selectedProd.prodCd + " ] " + $scope.selectedProd.prodNm + " )");
            $scope.searchProdRecpOriginDetail();

        } else if(addSelectedProd === "N") {
            $("#lblProd").text("");
        }
        event.preventDefault();
    });

    $scope.searchProdRecpOriginDetail = function(){
        var params = {};
        params.prodCd = $scope.selectedProd.prodCd;
        params.hqBrandCd = $scope.selectedProd.hqBrandCd;

        $scope._inquiryMain("/base/prod/recpOrigin/prodRecpOrigin/getProdRecpOriginDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedProd;
    $scope.setSelectedProd = function(store) {
        $scope.selectedProd = store;
    };
    $scope.getSelectedProd = function(){
        return $scope.selectedProd;
    };

    // <-- 추가 -->
    $scope.add = function() {
        if(!$.isEmptyObject($scope.selectedProd) ) {
            addSelectedProd = "Y";
        }

        if(addSelectedProd === "Y") {
            $scope.wjProdRecpOriginAddLayer.show(true);
            event.preventDefault();
        } else if(addSelectedProd === "N" ) {
            $scope._popMsg(messages["prodRecpOrigin.prodCdBlank"]); // 상품코드를 선택해주세요.
            return false;
        }
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 재료 및 원산지 등록 팝업 핸들러 추가
        $scope.wjProdRecpOriginAddLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('prodRecpOriginAddCtrl', $scope.getSelectedProd());
            }, 50)
        });
    });
    // <-- //추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        if(!$.isEmptyObject($scope.selectedProd) ) {
            addSelectedProd = "Y";
        }

        if(addSelectedProd === "Y") {
            $scope._popConfirm(messages["cmm.choo.delete"], function() {
                for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                    var item = $scope.flex.collectionView.items[i];

                    if(item.gChk) {
                        $scope.flex.collectionView.removeAt(i);
                    }
                }
                // 저장
                $scope.delSave();
            });

        } else if(addSelectedProd === "N" ) {
            $scope._popMsg(messages["prodRecpOrigin.prodCdBlank"]); // 상품코드를 선택해주세요.
            return false;
        }
    };

    $scope.delSave = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            $scope.flex.collectionView.itemsRemoved[i].prodCd = $scope.selectedProd.prodCd;
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", params, function(){});
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

    // <-- 저장 -->
    $scope.save = function() {
        if(!$.isEmptyObject($scope.selectedProd) ) {
            addSelectedProd = "Y";
        }

        if(addSelectedProd === "Y") {
            $scope._popConfirm(messages["cmm.choo.save"], function() {
                var recpSeq = 1;

                // 파라미터 설정
                var params = new Array();
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    $scope.flex.collectionView.items[i].status = "U";
                    $scope.flex.collectionView.items[i].prodCd = $scope.selectedProd.prodCd;
                    $scope.flex.collectionView.items[i].recpSeq = recpSeq;
                    params.push($scope.flex.collectionView.items[i]);
                    recpSeq++;
                }

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", params, function(){ $scope.allSearch() });
            });

        } else if(addSelectedProd === "N" ) {
            $scope._popMsg(messages["prodRecpOrigin.prodCdBlank"]); // 상품코드를 선택해주세요.
            return false;
        }
    };
    // <-- //저장 -->

    // 재조회
    $scope.allSearch = function () {
        $scope.searchProdRecpOriginDetail();
    };

}]);