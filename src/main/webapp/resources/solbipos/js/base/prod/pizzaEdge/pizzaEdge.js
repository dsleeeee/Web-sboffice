/****************************************************************
 *
 * 파일명 : pizzaEdge.js
 * 설  명 : 미스터피자 > 상품관리 > 피자-엣지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.04.24     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 * 피자 그리드 생성
 */
app.controller('pizzaEdgeCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pizzaEdgeCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "sdselProdCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        
        // 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if( col.binding === "sdselProdCd") {
                    var params = {};
                    params.prodCd       = selectedRow.prodCd;
                    params.sdselProdCd  = selectedRow.sdselProdCd;
                    params.sdselClassCd = selectedRow.sdselClassCd;
                    $scope._broadcast('pizzaEdgeNoRegCtrl', params);
                    $scope._broadcast('pizzaEdgeMappCtrl', params);
                }
            }
        });
    };

    // 피자 그리드 조회
    $scope.$on('pizzaEdgeCtrl', function(event, data) {
        $scope.getSearchPizzaList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 피자 그리드 조회
    $scope.getSearchPizzaList = function (){

        var params = {};

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/prod/pizzaEdge/pizzaEdge/getSearchPizzaList.sb", params, function() {
            
            // 등록, 미등록 상품 그리드 초기화
            $scope.$apply(function() {
                var scope = agrid.getScope('pizzaEdgeMappCtrl')
                scope._gridDataInit();
                var scope2 =agrid.getScope('pizzaEdgeNoRegCtrl')
                scope2._gridDataInit();
            });
        });
    }

}]);

/**
 * 등록상품 그리드 생성
 */
app.controller('pizzaEdgeMappCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pizzaEdgeMappCtrl', $scope, $http, false));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 등록상품 그리드 조회
    $scope.$on("pizzaEdgeMappCtrl", function(event, data) {
        $scope.prodCd       = data.prodCd;
        $scope.sdselProdCd  = data.sdselProdCd;
        $scope.sdselClassCd = data.sdselClassCd;

        // 등록상품 그리드 조회
        $scope.getSearchPizzaMappList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 등록상품 그리드 조회
    $scope.getSearchPizzaMappList = function (data){

        var params = data;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/prod/pizzaEdge/pizzaEdge/getSearchPizzaMappList.sb", params, function() {
            $("#btnDeleteMapp").show();
            $("#btnSaveMapp").show();
        });
    }

    // 등록상품 그리드 행 삭제
    $scope.delete = function() {
        $scope._popConfirm( "상품을 삭제하시겠습니까?", function() {
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }
            $scope.save();
        });
    }

    // 등록상품 그리드 저장
    $scope.save = function() {
        // 파라미터 설정
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            $scope.flex.collectionView.itemsRemoved[i].sdselClassCd = $scope.sdselClassCd;
            $scope.flex.collectionView.itemsRemoved[i].prodCd       = $scope.sdselProdCd;
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/pizzaEdge/pizzaEdge/getDeleteProd.sb", params, function() {
            var params2 = {};
            params2.prodCd          = $scope.prodCd
            params2.sdselProdCd     = $scope.sdselProdCd
            params2.sdselClassCd    = $scope.sdselClassCd;
            $scope._broadcast('pizzaEdgeNoRegCtrl', params2);
            $scope._broadcast('pizzaEdgeMappCtrl', params2);
        });
    };

}]);

/**
 * 미등록상품 그리드 생성
 */
app.controller('pizzaEdgeNoRegCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pizzaEdgeNoRegCtrl', $scope, $http, false));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 미등록상품 그리드 조회
    $scope.$on("pizzaEdgeNoRegCtrl", function(event, data) {
        $scope.prodCd       = data.prodCd;
        $scope.sdselProdCd  = data.sdselProdCd;
        $scope.sdselClassCd = data.sdselClassCd;

        // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
        $scope.getSearchNoRegProdList(data)
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 미등록상품 그리드 조회
    $scope.getSearchNoRegProdList = function (data){

        var params = {};
        params.prodCd       = $scope.prodCd;
        params.sdselProdCd  = $scope.sdselProdCd;
        params.sdselClassCd = $scope.sdselClassCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/prod/pizzaEdge/pizzaEdge/getSearchNoRegProdList.sb", params, function() {
            $("#btnSaveNoReg").show();
        });
    }

    // 미등록상품 그리드 저장
    $scope.save = function() {
        $scope._popConfirm( "상품을 등록하시겠습니까?", function() {
            // 파라미터 설정
            var params = [];
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk === true) {
                    $scope.flex.collectionView.items[i].status = "I";
                    $scope.flex.collectionView.items[i].mappingSdselClassCd = $scope.flex.collectionView.items[i].sdselClassCd;
                    $scope.flex.collectionView.items[i].mappingProdCd       = $scope.flex.collectionView.items[i].sdselProdCd;
                    $scope.flex.collectionView.items[i].sdselClassCd        = $scope.sdselClassCd;
                    $scope.flex.collectionView.items[i].sdselProdCd         = $scope.sdselProdCd;
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/pizzaEdge/pizzaEdge/getRegProd.sb", params, function () {
                var params2 = {};
                params2.prodCd          = $scope.prodCd;
                params2.sdselProdCd     = $scope.sdselProdCd
                params2.sdselClassCd    = $scope.sdselClassCd;
                $scope._broadcast('pizzaEdgeNoRegCtrl', params2);
                $scope._broadcast('pizzaEdgeMappCtrl', params2);
            });
        });
    };

}]);
