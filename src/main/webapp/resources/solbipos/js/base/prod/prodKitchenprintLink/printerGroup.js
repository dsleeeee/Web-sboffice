/****************************************************************
 *
 * 파일명 : kitchenprintTab.js
 * 설  명 : 상품-매장주방프린터 연결 - 주방프린터그룹관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.10     권지현      1.0
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

// 그룹관리-그룹등록
app.controller('printerGroupCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('printerGroupCtrl', $scope, $http, true));

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnAll", useYnAllFgData);

    $scope.saleUprcApply = true;

    $("#btnAddMenuGroup").css("display", "none");
    $("#btnSaveMenuGroup").css("display", "none");
    $("#btnDelProdMapping").css("display", "none");
    $("#btnSaveProdMapping").css("display", "none");
    $("#btnSrchProd").css("display", "none");
    $("#btnRegProd").css("display", "none");

    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'addProd' || col.binding === 'addPrinter') {
                    if(item.printerGroupCd !== '자동채번') {
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
                if (col.binding === "addProd") {
                    if(selectedRow.printerGroupCd !== '자동채번') {

                        $("#addProd").show();
                        $("#addPrinter").hide();

                        // 선택한 그룹코드 갖고있기
                        $("#groupCd").val(selectedRow.printerGroupCd);

                        // 선택한 그룹 명시
                        $("#lblMenuGroup").text("[" + selectedRow.printerGroupCd + "] " + selectedRow.printerGroupNm);

                        // 상품연결 grid 조회
                        $scope._broadcast('prodMappingCtrl');

                        // 버튼 visible 셋팅
                        $("#btnDelProdMapping").css("display", "");
                        $("#btnSrchProd").css("display", "");
                        $("#btnRegProd").css("display", "");
                        event.preventDefault();
                    }
                }
                else if(col.binding === "addPrinter"){
                    if(selectedRow.printerGroupCd !== '자동채번') {

                        $("#addProd").hide();
                        $("#addPrinter").show();

                        // 선택한 그룹코드 갖고있기
                        $("#groupCd").val(selectedRow.printerGroupCd);

                        // 선택한 그룹 명시
                        $("#lblPrinterGroup1").text("[" + selectedRow.printerGroupCd + "] " + selectedRow.printerGroupNm);

                        // 상품연결 grid 조회
                        $scope._broadcast('printerMappingCtrl');

                        // 버튼 visible 셋팅
                        $("#btnDelPrinterMapping").css("display", "");
                        $("#btnSavePrinterMapping").css("display", "");
                        $("#btnAddPrinterMapping").css("display", "");

                    }
                }
            }
        });

        // 그룹조회
        $scope.searchMenuGroup();

    };

    $scope.$on("printerGroupCtrl", function(event, data) {

        // 프린터그룹조회
        $scope.searchMenuGroup();
        event.preventDefault();
    });

    // 프린터그룹조회
    $scope.searchMenuGroup = function () {

        var params = [];
        params.printerGroupNm = $scope.printerGroupNm;

        $scope._inquirySub("/base/prod/prodKitchenprintLink/printerGroup/getPrinterGroupList.sb", params, function() {

            $("#addProd").show();
            $("#addPrinter").hide();

            // 선택한 매장타입 초기화
            $("#groupCd").val("");
            $("#lblMenuGroup").text("");

            // 버튼 visible 셋팅 - 매장타입관리 grid 버튼은 보이고 나머지 grid 버튼은 숨길것.
            $("#btnAddMenuGroup").css("display", "");
            $("#btnSaveMenuGroup").css("display", "");
            $("#btnDelProdMapping").css("display", "none");
            $("#btnSaveProdMapping").css("display", "none");
            $("#btnSrchProd").css("display", "none");
            $("#btnRegProd").css("display", "none");

            // 상품연결 그리드 초기화
            var prodMappingScope = agrid.getScope('prodMappingCtrl');
            prodMappingScope.prodMappingGridDefault();

            // 상품 그리드 및 input 값 초기화
            var prodSelectScope = agrid.getScope('prodSelectCtrl');
            prodSelectScope.prodSelectGridDefault();

            prodSelectScope.srchGroupStartDate.value = getCurDate('-');
            prodSelectScope.srchGroupEndDate.value = getCurDate('-');
            prodSelectScope.srchGroupStartDate.isReadOnly = true;
            prodSelectScope.srchGroupEndDate.isReadOnly = true;
            prodSelectScope.isGroupChecked = true;
            $("#srchProdCd").val("");
            $("#srchProdNm").val("");
            $("#srchProdClassCd").val("");
            $("#_prodClassCd").val("");
            $("#srchBarCd").val("");
            prodSelectScope.srchBrandCombo.selectedIndex = 0;
            prodSelectScope.srchUseYnCombo.selectedIndex = 0;
            prodSelectScope.srchProdTypeFgCombo.selectedIndex = 0;
            prodSelectScope.srchStoreGroupCombo.selectedIndex = 0;

        }, false);
    }

    // 그룹추가
    $scope.addMenuGroup = function () {

        var params = [];
        params.printerGroupCd = '자동채번';
        params.printerGroupNm = '';

        // 행추가
        $scope._addRow(params, 1);
    }
    
    // 그룹저장
    $scope.saveMenuGroup = function () {

        $scope.flex.collectionView.commitEdit();

        // 생성, 수정 Validation Check
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if ($scope.flex.collectionView.items[i].printerGroupNm === null || $scope.flex.collectionView.items[i].printerGroupNm === '') {
                $scope._popMsg(messages['printerGroup.require.groupNm.msg']); // 그룹명을 입력해주세요.
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
        $scope._save('/base/prod/prodKitchenprintLink/printerGroup/savePrinterGroup.sb', params, function () {

            // 그룹조회
            $scope.searchMenuGroup();

        });

    };

}]);

// 상품추가
app.controller('prodMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodMappingCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
            }
            s.collectionView.commitEdit();
        });
    };

    $scope.$on("prodMappingCtrl", function(event, data) {
        // 상품연결조회
        $scope.searchProdMapping();
        event.preventDefault();
    });

    // 상품연결조회
    $scope.searchProdMapping = function () {

        var params = [];
        params.printerGroupCd = $("#groupCd").val();

        $scope._inquirySub("/base/prod/prodKitchenprintLink/printerGroup/getProdMapping.sb", params, function() {

            // 상품선택 조회
            var prodSelectGrid = agrid.getScope("prodSelectCtrl");
            prodSelectGrid._pageView('prodSelectCtrl', 1);

        }, false);
    }

    // 상품연결삭제
    $scope.delProdMapping = function(){

        $scope._popConfirm(messages["printerGroup.delChk.msg"], function () {
            // 파라미터 설정
            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].printerGroupCd = $("#groupCd").val();
                    $scope.flex.collectionView.items[i].status = 'D';

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodKitchenprintLink/printerGroup/saveProdMapping.sb", params, function () {

                // 상품연결 재조회
                $scope.searchProdMapping();

                // 상품선택 재조회
                var prodSelectGrid = agrid.getScope("prodSelectCtrl");
                prodSelectGrid._pageView('prodSelectCtrl', 1);

            });
        });
    };

    // 상품연결 그리드 초기화
    $scope.prodMappingGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 그룹관리-상품선택
app.controller('prodSelectCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSelectCtrl', $scope, $http, true));

    // 등록일자 셋팅
    $scope.srchGroupStartDate = wcombo.genDateVal("#srchGroupStartDate", gvStartDate);
    $scope.srchGroupEndDate   = wcombo.genDateVal("#srchGroupEndDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchBrand", brandList);
    $scope._setComboData("srchUseYn", useYnAllFgData);
    $scope._setComboData('srchProdTypeFg', prodTypeFg);
    $scope._setComboData('srchStoreGroup', storeGroupList);

    // 등록일자 기본 '전체기간'으로
    $scope.isGroupChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.srchGroupStartDate.isReadOnly = $scope.isGroupChecked;
        $scope.srchGroupEndDate.isReadOnly = $scope.isGroupChecked;

        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
            }
        });
    };

    $scope.$on("prodSelectCtrl", function(event, data) {

        // 상품조회
        $scope.searchProd();
        event.preventDefault();
    });

    // 상품조회
    $scope.searchProd = function(){

        var params = [];
        params.chkDt = $scope.isGroupChecked;
        params.startDate = wijmo.Globalize.format($scope.srchGroupStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchGroupEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.srchProdCd;
        params.prodNm = $scope.srchProdNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $("#srchBarCd").val();
        params.hqBrandCd = $scope.srchBrandCombo.selectedValue;
        params.useYn = $scope.srchUseYnCombo.selectedValue;
        params.prodTypeFg = $scope.srchProdTypeFgCombo.selectedValue;
        params.storeGroup = $scope.srchStoreGroupCombo.selectedValue;
        params.printerGroupCd = $("#groupCd").val();

        $scope._inquirySub("/base/prod/prodKitchenprintLink/printerGroup/getProdList.sb", params, function() {}, false);
    };

    // 상품등록
    $scope.regProd = function () {

        $scope._popConfirm(messages["printerGroup.addChk.msg"], function () {
            $scope.flex.collectionView.commitEdit();

            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].printerGroupCd = $("#groupCd").val();
                    $scope.flex.collectionView.items[i].status = 'I';

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodKitchenprintLink/printerGroup/saveProdMapping.sb", params, function () {

                // 상품 재조회
                $scope.searchProd();

                // 상품연결 재조회
                var prodMappingGrid = agrid.getScope("prodMappingCtrl");
                prodMappingGrid._pageView('prodMappingCtrl', 1);

            });
        });
    };

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchGroupStartDate.isReadOnly = $scope.isGroupChecked;
        $scope.srchGroupEndDate.isReadOnly = $scope.isGroupChecked;
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

    // 상품 그리드 초기화
    $scope.prodSelectGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);



// 프린터추가
app.controller('printerMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('printerMappingCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
    };

    $scope.$on("printerMappingCtrl", function(event, data) {
        // 프린터연결조회
        $scope.searchPrinterMapping();
        event.preventDefault();
    });

    // 프린터연결조회
    $scope.searchPrinterMapping = function () {

        var params = [];
        params.printerGroupCd = $("#groupCd").val();

        $scope._inquirySub("/base/prod/prodKitchenprintLink/printerGroup/getPrinterMapping.sb", params, function() {

            // 프린터선택 조회
            var prodSelectGrid = agrid.getScope("printerSelectCtrl");
            prodSelectGrid._pageView('printerSelectCtrl', 1);

        }, false);
    }

    // 매핑프린터 삭제
    $scope.delPrinterMapping = function(){

        $scope._popConfirm(messages["printerGroup.delChk.msg"], function () {

            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].printerGroupCd = $("#groupCd").val();
                    $scope.flex.collectionView.items[i].status = 'D';

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/prodKitchenprintLink/printerGroup/savePrinterMapping.sb', params, function () {

                // 상품연결 재조회
                $scope.searchPrinterMapping();

                // 상품선택 재조회
                var prodSelectGrid = agrid.getScope("printerSelectCtrl");
                prodSelectGrid._pageView('printerSelectCtrl', 1);

            });

        });
    };

    // 매핑프린터 그리드 초기화
    $scope.printerMappingGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 그룹관리-프린터선택
app.controller('printerSelectCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('printerSelectCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
    };

    $scope.$on("printerSelectCtrl", function(event, data) {
        // 프린터
        $scope.searchPrinter();
        event.preventDefault();
    });

    // 프린터 조회
    $scope.searchPrinter = function(){

        var params = [];
        params.printerGroupCd = $("#groupCd").val();
        $scope._inquirySub("/base/prod/prodKitchenprintLink/printerGroup/getPrinterList.sb", params, function() {}, false);
    };

    // 프린터 등록
    $scope.addPrinterMapping = function () {

        $scope._popConfirm(messages["printerGroup.addChk.msg"], function () {

            $scope.flex.collectionView.commitEdit();

            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].printerGroupCd = $("#groupCd").val();
                    $scope.flex.collectionView.items[i].status = 'I';

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodKitchenprintLink/printerGroup/savePrinterMapping.sb", params, function(){

                // 프린터 재조회
                $scope.searchPrinter();

                // 프린터연결 재조회
                var prodMappingGrid = agrid.getScope("printerMappingCtrl");
                prodMappingGrid._pageView('printerMappingCtrl', 1);

            });

        });
    };

    // 프린터 그리드 초기화
    $scope.printerSelectGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

