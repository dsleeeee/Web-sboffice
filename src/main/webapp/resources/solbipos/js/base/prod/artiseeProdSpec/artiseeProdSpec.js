/****************************************************************
 *
 * 파일명 : artiseeProdSpec.js
 * 설  명 : 아티제-상품특성관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.10.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부
var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

/**
 *  아티제-상품특성관리 그리드 생성
 */
app.controller('artiseeProdSpecCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseeProdSpecCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 특성코드
                if (col.binding === "specCd") {
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

                // 특성코드 클릭시 상세정보 조회
                if ( col.binding === "specCd") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    // 적용 상품
                    var storeScope = agrid.getScope('artiseeProdSpecProdCtrl');
                    storeScope._broadcast('artiseeProdSpecProdCtrl', selectedRow);
                    event.preventDefault();

                    // 미적용 상품
                    var storeScope2 = agrid.getScope('artiseeProdSpecNoProdCtrl');
                    storeScope2._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
                    storeScope2._broadcast('artiseeProdSpecNoProdCtrl', selectedRow);
                    event.preventDefault();
                    // paging 영역 보이도록
                    var artiseeProdSpecNoProdCtrlPager = document.getElementById('artiseeProdSpecNoProdCtrlPager');
                    artiseeProdSpecNoProdCtrlPager.style.visibility='visible';

                    // 미적용 상품 버튼
                    var divBtnProd = document.getElementById('divBtnProd');
                    divBtnProd.style.visibility='visible';
                    var divBtnProd2 = document.getElementById('divBtnProd2');
                    divBtnProd2.style.visibility='visible';
                }
            }
        });

        // 조회
        $scope.searchArtiseeProdSpec();
    };

    // <-- 검색 호출 -->
    $scope.$on("artiseeProdSpecCtrl", function(event, data) {
        $("#lblSpecNm").text("");

        // 적용 상품
        var storeScope = agrid.getScope('artiseeProdSpecProdCtrl');
        storeScope._gridDataInit();
        // storeScope._broadcast('artiseeProdSpecProdCtrl', null);

        // 미적용 상품
        var storeScope2 = agrid.getScope('artiseeProdSpecNoProdCtrl');
        storeScope2._gridDataInit();
        // storeScope2._broadcast('artiseeProdSpecNoProdCtrl', null);
        // paging 영역 보이도록
        var artiseeProdSpecNoProdCtrlPager = document.getElementById('artiseeProdSpecNoProdCtrlPager');
        artiseeProdSpecNoProdCtrlPager.style.visibility='hidden';

        // 미적용 상품 버튼
        var divBtnProd = document.getElementById('divBtnProd');
        divBtnProd.style.visibility='hidden';
        var divBtnProd2 = document.getElementById('divBtnProd2');
        divBtnProd2.style.visibility='hidden';

        $scope.searchArtiseeProdSpec();
        event.preventDefault();
    });

    $scope.searchArtiseeProdSpec = function(){
        var params = {};

        $scope._inquiryMain("/base/prod/artiseeProdSpec/artiseeProdSpec/getArtiseeProdSpecList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);


/**
 *  적용 상품 그리드 생성
 */
app.controller('artiseeProdSpecProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseeProdSpecProdCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "prodCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 대표명칭 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "prodCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 추가옵션 변경시 체크박스 체크
                if (col.binding === "option1" || col.binding === "option2" || col.binding === "option3" || col.binding === "option4" || col.binding === "option5") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

    };

    // 브랜드 변경시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };


    // <-- 검색 호출 -->
    $scope.$on("artiseeProdSpecProdCtrl", function(event, data) {
        $("#lblSpecNm").text(data.specCd + " / " + data.specNm);
        $scope.setSelectedProd(data);
        $scope.searchArtiseeProdSpecProd();
        event.preventDefault();
    });

    $scope.searchArtiseeProdSpecProd = function(){
        var params = {};
        params.specCd = $scope.selectedProd.specCd;

        $scope._inquirySub("/base/prod/artiseeProdSpec/artiseeProdSpec/getArtiseeProdSpecProdList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 로우 추가 -->
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = 'I';
        params.specCd = $scope.selectedProd.specCd;

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //로우 추가 -->


    // <-- 저장 -->
    $scope.save = function() {

        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        var params = new Array();
        var arr = [];
        var arr2 = [];
        var inputProdCd = "";

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            arr[i] = $scope.flex.collectionView.items[i].prodCd.trim().removeEnter();
        }

        arr.forEach(function(element){
            if(!arr2.includes(element)){
                arr2.push(element);
            }else{
                inputProdCd += element + ",";
            }
        });

        if(inputProdCd !== ""){
            $scope._popMsg(messages["artiseeProdSpec.prodCdDuplicate.msg"] + "<br>(" + inputProdCd.substr(0, inputProdCd.length - 1) + ")"); // 중복된 상품코드입니다.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if ($scope.flex.collectionView.itemsAdded[i].gChk === true) {
                $scope.flex.collectionView.itemsAdded[i].prodCd = $scope.flex.collectionView.itemsAdded[i].prodCd.trim().removeEnter();
                if($scope.flex.collectionView.itemsAdded[i].prodCd !== null && $scope.flex.collectionView.itemsAdded[i].prodCd !== ""){
                    params.push($scope.flex.collectionView.itemsAdded[i]);
                }else{
                    $scope._popMsg(messages["artiseeProdSpec.prodCdChk.msg"]); // 상품코드를 입력하세요.
                    return false;
                }
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if ($scope.flex.collectionView.itemsEdited[i].gChk === true) {
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/artiseeProdSpec/artiseeProdSpec/getArtiseeProdSpecProdSaveInsert.sb", params, function(){
            // 특성
            var scope = agrid.getScope('artiseeProdSpecCtrl');
            scope.searchArtiseeProdSpec();

            // 적용 상품
            $scope.searchArtiseeProdSpecProd();

            // 미적용 상품
            var storeScope2 = agrid.getScope('artiseeProdSpecNoProdCtrl');
            storeScope2._gridDataInit();
            storeScope2._broadcast('artiseeProdSpecNoProdCtrl', $scope.getSelectedProd());
        });

    };
    // <-- //저장 -->


    // <-- 삭제 -->
    $scope.del = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].specCd = $scope.selectedProd.specCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/artiseeProdSpec/artiseeProdSpec/getArtiseeProdSpecProdSaveDelete.sb", params, function(){
            // 특성
            var scope = agrid.getScope('artiseeProdSpecCtrl');
            scope.searchArtiseeProdSpec();

            // 적용 상품
            $scope.searchArtiseeProdSpecProd();

            // 미적용 상품
            var storeScope2 = agrid.getScope('artiseeProdSpecNoProdCtrl');
            storeScope2._gridDataInit();
            storeScope2._broadcast('artiseeProdSpecNoProdCtrl', $scope.getSelectedProd());
        });
    };
    // <-- //삭제 -->

    // 선택 매장
    $scope.selectedProd;
    $scope.setSelectedProd = function(store) {
        $scope.selectedProd = store;
    };
    $scope.getSelectedProd = function(){
        return $scope.selectedProd;
    };

}]);


/**
 *  미적용 상품 그리드 생성
 */
app.controller('artiseeProdSpecNoProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseeProdSpecNoProdCtrl', $scope, $http, false));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    // 콤보박스 데이터 Set
    $scope._setComboData('useYnAllCombo', useYnAllComboData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 등록일자 기본 '전체기간'으로
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // <-- 검색 호출 -->
    $scope.$on("artiseeProdSpecNoProdCtrl", function(event, data) {

        if(data !== null && data!== undefined) {
            $scope.setSelectedNoProd(data);
        }
        $scope.searchArtiseeProdSpecNoProd();
        event.preventDefault();
    });

    $scope.searchArtiseeProdSpecNoProd = function(){
        var params = {};
        params.specCd = $scope.selectedNoProd.specCd;
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.useYn;
        params.listScale = 10;

        $scope._inquiryMain("/base/prod/artiseeProdSpec/artiseeProdSpec/getArtiseeProdSpecNoProdList.sb", params, function() {}, false);
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

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

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

    // <-- 추가 -->
    $scope.add = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].specCd = $scope.selectedNoProd.specCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/artiseeProdSpec/artiseeProdSpec/getArtiseeProdSpecProdSaveInsert.sb", params, function(){
            // 특성
            var scope = agrid.getScope('artiseeProdSpecCtrl');
            scope.searchArtiseeProdSpec();

            // 적용 상품
            var storeScope = agrid.getScope('artiseeProdSpecProdCtrl');
            storeScope._gridDataInit();
            storeScope._broadcast('artiseeProdSpecProdCtrl', $scope.getSelectedNoProd());

            // 미적용 상품
            var storeScope2 = agrid.getScope('artiseeProdSpecNoProdCtrl');
            storeScope2._gridDataInit();
            storeScope2._broadcast('artiseeProdSpecNoProdCtrl', $scope.getSelectedNoProd());
        });
    };
    // <-- //추가 -->

}]);