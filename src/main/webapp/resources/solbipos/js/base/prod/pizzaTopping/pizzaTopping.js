/****************************************************************
 *
 * 파일명 : pizzaTopping.js
 * 설  명 : 피자-토핑관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.04.28     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  피자-토핑관리 그리드 생성
 */
app.controller('pizzaToppingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pizzaToppingCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData('useYnComboData', useYnFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부

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
                    var params = {};
                    params.prodCd = selectedRow.prodCd;
                    params.prodNm = selectedRow.prodNm;

                    // 등록 상품
                    var storeScope = agrid.getScope('pizzaToppingProdCtrl');
                    storeScope._broadcast('pizzaToppingProdCtrl', params);

                    // 미등록 상품
                    var storeScope2 = agrid.getScope('pizzaToppingNoProdCtrl');
                    storeScope2._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
                    storeScope2._broadcast('pizzaToppingNoProdCtrl', params);
                    event.preventDefault();
                    // paging 영역 보이도록
                    var pizzaToppingNoProdCtrlPager = document.getElementById('pizzaToppingNoProdCtrlPager');
                    pizzaToppingNoProdCtrlPager.style.visibility='visible';

                    // 버튼
                    var divBtnProd = document.getElementById('divBtnProd');
                    divBtnProd.style.visibility='visible';
                    var divBtnProd2 = document.getElementById('divBtnProd2');
                    divBtnProd2.style.visibility='visible';
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("pizzaToppingCtrl", function(event, data) {
        $scope.searchPizzaTopping();
        event.preventDefault();
    });

    $scope.searchPizzaTopping = function(){
        var params = {};
        params.srchProdCd = $scope.srchProdCd;
        params.srchProdNm = $scope.srchProdNm;
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.useYn;
        params.listScale = 100;

        $scope._inquiryMain("/base/prod/pizzaTopping/pizzaTopping/getPizzaToppingList.sb", params, function() {
            $scope.$apply(function() {
                // 등록 상품
                var storeScope = agrid.getScope('pizzaToppingProdCtrl');
                storeScope._gridDataInit();
                // storeScope._broadcast('pizzaToppingProdCtrl', null);

                // 미등록 상품
                var storeScope2 = agrid.getScope('pizzaToppingNoProdCtrl');
                storeScope2._gridDataInit();
                // storeScope2._broadcast('pizzaToppingNoProdCtrl', null);
                // paging 영역 보이도록
                var pizzaToppingNoProdCtrlPager = document.getElementById('pizzaToppingNoProdCtrlPager');
                pizzaToppingNoProdCtrlPager.style.visibility='hidden';

                // 버튼
                var divBtnProd = document.getElementById('divBtnProd');
                divBtnProd.style.visibility='hidden';
                var divBtnProd2 = document.getElementById('divBtnProd2');
                divBtnProd2.style.visibility='hidden';
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
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

}]);


/**
 *  등록 상품 그리드 생성
 */
app.controller('pizzaToppingProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pizzaToppingProdCtrl', $scope, $http, false));




    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("pizzaToppingProdCtrl", function(event, data) {
        $("#lblProdCd").text(data.prodCd + " / " + data.prodNm);
        $scope.setSelectedProd(data);
        $scope.searchPizzaToppingProd();
        event.preventDefault();
    });

    $scope.searchPizzaToppingProd = function(){
        var params = {};
        params.prodCd = $scope.selectedProd.prodCd;

        $scope._inquirySub("/base/prod/pizzaTopping/pizzaTopping/getPizzaToppingProdList.sb", params, function() {}, false);
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

    // 삭제
    $scope.del = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "D";
                $scope.flex.collectionView.items[i].prodCd = $scope.selectedProd.prodCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/pizzaTopping/pizzaTopping/getPizzaToppingProdSave.sb", params, function(){
            $scope.$apply(function() {
                // 등록 상품
                var storeScope = agrid.getScope('pizzaToppingProdCtrl');
                storeScope._broadcast('pizzaToppingProdCtrl', $scope.getSelectedProd());

                // 미등록 상품
                var storeScope2 = agrid.getScope('pizzaToppingNoProdCtrl');
                storeScope2._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
                storeScope2._broadcast('pizzaToppingNoProdCtrl', $scope.getSelectedProd());
            });
        });
    };

}]);


/**
 *  미등록 상품 그리드 생성
 */
app.controller('pizzaToppingNoProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pizzaToppingNoProdCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData('useYnComboData2', useYnFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("pizzaToppingNoProdCtrl", function(event, data) {
        if(data !== null && data!== undefined) {
            $scope.setSelectedNoProd(data);
        }
        $scope.searchPizzaToppingNoProd();
        event.preventDefault();
    });

    $scope.searchPizzaToppingNoProd = function(){
        var params = {};
        params.srchProdCd = $scope.srchProdCd;
        params.srchProdNm = $scope.srchProdNm;
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.useYn;
        params.prodCd = $scope.selectedNoProd.prodCd;
        params.listScale = 15;

        $scope._inquirySub("/base/prod/pizzaTopping/pizzaTopping/getPizzaToppingNoProdList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedNoProd;
    $scope.setSelectedNoProd = function(store) {
        $scope.selectedNoProd = store;
    };
    $scope.getSelectedNoProd = function(){
        return $scope.selectedNoProd;
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass2 = function() {
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
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass2 = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 저장
    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                $scope.flex.collectionView.items[i].prodCd = $scope.selectedNoProd.prodCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/pizzaTopping/pizzaTopping/getPizzaToppingProdSave.sb", params, function(){
            $scope.$apply(function() {
                // 등록 상품
                var storeScope = agrid.getScope('pizzaToppingProdCtrl');
                storeScope._broadcast('pizzaToppingProdCtrl', $scope.getSelectedNoProd());

                // 미등록 상품
                var storeScope2 = agrid.getScope('pizzaToppingNoProdCtrl');
                storeScope2._broadcast('pizzaToppingNoProdCtrl', $scope.getSelectedNoProd());
            });
        });
    };

}]);