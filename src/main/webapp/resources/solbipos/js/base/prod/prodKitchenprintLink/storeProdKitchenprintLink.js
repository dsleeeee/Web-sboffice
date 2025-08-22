/****************************************************************
 *
 * 파일명 : storeProdKitchenprintLink.js
 * 설  명 : 상품-매장주방프린터 연결 > 매장-상품 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.05.20     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  피자-매장-상품 그리드 생성
 */
app.controller('storeProdKitchenprintLinkCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdKitchenprintLinkCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData('sysStatFgComboData', sysStatFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 프린터코드
                if (col.binding === "prterNo") {
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

                // 프린터코드 클릭시 상세정보 조회
                if ( col.binding === "prterNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};
                    params.storeCd = selectedRow.storeCd;
                    params.storeNm = selectedRow.storeNm;
                    params.prterNo = selectedRow.prterNo;
                    params.prterNm = selectedRow.prterNm;

                    // 등록 상품
                    var storeScope = agrid.getScope('storeProdKitchenprintLinkProdCtrl');
                    storeScope._broadcast('storeProdKitchenprintLinkProdCtrl', params);

                    // 미등록 상품
                    var storeScope2 = agrid.getScope('storeProdKitchenprintLinkNoProdCtrl');
                    storeScope2._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
                    storeScope2._broadcast('storeProdKitchenprintLinkNoProdCtrl', params);
                    event.preventDefault();
                    // paging 영역 보이도록
                    var storeProdKitchenprintLinkNoProdCtrlPager = document.getElementById('storeProdKitchenprintLinkNoProdCtrlPager');
                    storeProdKitchenprintLinkNoProdCtrlPager.style.visibility='visible';

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
    $scope.$on("storeProdKitchenprintLinkCtrl", function(event, data) {
        $("#lblPrterNo").text("");

        // 등록 상품
        var storeScope = agrid.getScope('storeProdKitchenprintLinkProdCtrl');
        storeScope._gridDataInit();

        // 미등록 상품
        var storeScope2 = agrid.getScope('storeProdKitchenprintLinkNoProdCtrl');
        storeScope2._gridDataInit();
        // paging 영역 보이도록
        var storeProdKitchenprintLinkNoProdCtrlPager = document.getElementById('storeProdKitchenprintLinkNoProdCtrlPager');
        storeProdKitchenprintLinkNoProdCtrlPager.style.visibility='hidden';

        // 버튼
        var divBtnProd = document.getElementById('divBtnProd');
        divBtnProd.style.visibility='hidden';
        var divBtnProd2 = document.getElementById('divBtnProd2');
        divBtnProd2.style.visibility='hidden';

        $scope.searchStoreProdKitchenprintLink();
        event.preventDefault();
    });

    $scope.searchStoreProdKitchenprintLink = function(){
        var params = {};
        params.storeCd = $("#storeProdKitchenprintLinkStoreCd").val();
        if(hqOfficeCd === 'H0616'){
            params.storeCd = 'S614607';
        }else if(hqOfficeCd === 'DS053'){
            params.storeCd = 'DS00501';
        }
        params.sysStatFg = $scope.sysStatFg;

        $scope._inquiryMain("/base/prod/prodKitchenprintLink/storeProdKitchenprintLink/getStoreProdKitchenprintLinkList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 전체등록
    $scope.allRegist = function() {
        // 전체등록 하시겠습니까?
        $scope._popConfirm(messages["storeProdKitchenprintLink.allRegistSaveConfirm"], function() {
            var params = {};

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodKitchenprintLink/storeProdKitchenprintLink/getStoreProdKitchenprintLinkAllRegistSave.sb", params, function(){
                // 조회
                $scope.searchStoreProdKitchenprintLink();
            });
        });
    };

}]);


/**
 *  등록 상품 그리드 생성
 */
app.controller('storeProdKitchenprintLinkProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdKitchenprintLinkProdCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("storeProdKitchenprintLinkProdCtrl", function(event, data) {
        $("#lblPrterNo").text(data.storeCd + " / [" + data.prterNo + "] " + data.prterNm);
        $scope.setSelectedProd(data);
        $scope.searchStoreProdKitchenprintLinkProd();
        event.preventDefault();
    });

    $scope.searchStoreProdKitchenprintLinkProd = function(){
        var params = {};
        params.storeCd = $scope.selectedProd.storeCd;
        params.prterNo = $scope.selectedProd.prterNo;

        $scope._inquirySub("/base/prod/prodKitchenprintLink/storeProdKitchenprintLink/getStoreProdKitchenprintLinkProdList.sb", params, function() {}, false);
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
                $scope.flex.collectionView.items[i].storeCd = $scope.selectedProd.storeCd;
                $scope.flex.collectionView.items[i].prterNo = $scope.selectedProd.prterNo;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prodKitchenprintLink/storeProdKitchenprintLink/getStoreProdKitchenprintLinkSave.sb", params, function(){
            $scope.$apply(function() {
                // 매장 조회
                var scope = agrid.getScope('storeProdKitchenprintLinkCtrl');
                scope.searchStoreProdKitchenprintLink();

                // 등록 상품
                var storeScope = agrid.getScope('storeProdKitchenprintLinkProdCtrl');
                storeScope._broadcast('storeProdKitchenprintLinkProdCtrl', $scope.getSelectedProd());

                // 미등록 상품
                var storeScope2 = agrid.getScope('storeProdKitchenprintLinkNoProdCtrl');
                storeScope2._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
                storeScope2._broadcast('storeProdKitchenprintLinkNoProdCtrl', $scope.getSelectedProd());
            });
        });
    };

}]);


/**
 *  미등록 상품 그리드 생성
 */
app.controller('storeProdKitchenprintLinkNoProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdKitchenprintLinkNoProdCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData('useYnComboData', useYnFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("storeProdKitchenprintLinkNoProdCtrl", function(event, data) {
        if(data !== null && data!== undefined) {
            $scope.setSelectedNoProd(data);
        }
        $scope.searchStoreProdKitchenprintLinkNoProd();
        event.preventDefault();
    });

    $scope.searchStoreProdKitchenprintLinkNoProd = function(){
        var params = {};
        params.srchProdCd = $scope.srchProdCd;
        params.srchProdNm = $scope.srchProdNm;
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.useYn;
        params.storeCd = $scope.selectedNoProd.storeCd;
        params.prterNo = $scope.selectedNoProd.prterNo;
        params.listScale = 15;

        $scope._inquirySub("/base/prod/prodKitchenprintLink/storeProdKitchenprintLink/getStoreProdKitchenprintLinkNoProdList.sb", params, function() {}, false);
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
                $scope.flex.collectionView.items[i].storeCd = $scope.selectedNoProd.storeCd;
                $scope.flex.collectionView.items[i].prterNo = $scope.selectedNoProd.prterNo;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prodKitchenprintLink/storeProdKitchenprintLink/getStoreProdKitchenprintLinkSave.sb", params, function(){
            $scope.$apply(function() {
                // 매장 조회
                var scope = agrid.getScope('storeProdKitchenprintLinkCtrl');
                scope.searchStoreProdKitchenprintLink();

                // 등록 상품
                var storeScope = agrid.getScope('storeProdKitchenprintLinkProdCtrl');
                storeScope._broadcast('storeProdKitchenprintLinkProdCtrl', $scope.getSelectedNoProd());

                // 미등록 상품
                var storeScope2 = agrid.getScope('storeProdKitchenprintLinkNoProdCtrl');
                storeScope2._broadcast('storeProdKitchenprintLinkNoProdCtrl', $scope.getSelectedNoProd());
            });
        });
    };

}]);
