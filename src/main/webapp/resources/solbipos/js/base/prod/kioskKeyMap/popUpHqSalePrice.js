/****************************************************************
 *
 * 파일명 : popUpHqSalePrice.js
 * 설  명 : 본사판매가관리 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.08.24     권지현      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 저장 시 매장 적용 구분
var storeSaveFg = [
    {"name":"전매장적용","value":"all"},
    {"name":"미적용","value":"none"},
    {"name":"전매장적용(제한매장포함)","value":"tot"},
    {"name":"매장선택","value":"choice"}
];

app.controller('popUpHqSalePriceCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('popUpHqSalePriceCtrl', $scope, $http, false));

    $scope._setComboData("storeSaveFg", storeSaveFg);

    $scope.saleUprcApply = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
                    $scope.checked(item);
                }
                // 판매가 변경시 다른 컬럼값도 변경
                if (col.binding === "saleUprc") {
                    $scope.calcAmt(item);
                    if($scope.saleUprcApply){
                        $scope.saleUprc(item);
                    }
                }
            }
            s.collectionView.commitEdit();
        });

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem                  = {};
        dataItem.gChk                 = messages["cmm.chk"];
        dataItem.tuClsType            = messages["kioskKeyMap.tuClsType"];
        dataItem.tuClsTypeNm         = messages["kioskKeyMap.tuClsType"];
        dataItem.prodCd               = messages["salePrice.prodCd"];
        dataItem.prodNm               = messages["salePrice.prodNm"];
        dataItem.hqSaleUprc           = messages["salePrice.salePrice"];
        dataItem.storeSaleUprc        = messages["salePrice.salePrice"];
        dataItem.saleUprc             = messages["salePrice.salePrice"];
        dataItem.hqStinSaleUprc       = messages["salePrice.stinSaleUprc"];
        dataItem.storeStinSaleUprc    = messages["salePrice.stinSaleUprc"];
        dataItem.stinSaleUprc         = messages["salePrice.stinSaleUprc"];
        dataItem.hqDlvrSaleUprc       = messages["salePrice.dlvrSaleUprc"];
        dataItem.storeDlvrSaleUprc    = messages["salePrice.dlvrSaleUprc"];
        dataItem.dlvrSaleUprc         = messages["salePrice.dlvrSaleUprc"];
        dataItem.hqPackSaleUprc       = messages["salePrice.packSaleUprc"];
        dataItem.storePackSaleUprc    = messages["salePrice.packSaleUprc"];
        dataItem.packSaleUprc         = messages["salePrice.packSaleUprc"];
        dataItem.prcCtrlFg            = messages["salePriceManage.prcCtrlFg"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
    };

    // 가격 변경
    $scope.calcAmt = function(item){
        var hqCostUprc = item.hqCostUprc;
        var hqSplyUprc = item.hqSplyUprc;
        var storeSplyUprc = item.storeSplyUprc;
        // var hqSaleUprc = item.hqSaleUprc;
        var saleUprc = item.saleUprc;
        var poUnitQty =  item.poUnitQty;

        item.hqMarginAmt = (hqSplyUprc - hqCostUprc); // 본사마진금액
        item.hqMarginRate = (hqSplyUprc - hqCostUprc) / hqCostUprc * 100; // 본사마진율
        // item.saleUprcAmt = (saleUprc - poUnitQty); // 현재판매금액
        item.storeMarginAmt = ((saleUprc - poUnitQty) - storeSplyUprc); // 매장마진금액
        item.storeMarginRate = ((saleUprc - poUnitQty) - storeSplyUprc) / (saleUprc - poUnitQty) * 100; // 매장마진율
    };

    // 일괄변경
    $scope.saleUprc = function (item){
        item.stinSaleUprc = item.saleUprc;
        item.dlvrSaleUprc = item.saleUprc;
        item.packSaleUprc = item.saleUprc;
    };

    //판매가 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("popUpHqSalePriceCtrl", function(event, key) {

        $scope._setComboData("hqTuClsType", kioskTuClsTypeListAll);
        var scope = agrid.getScope("kioskKeyMapRegistCtrl");
        // 본사판매가 조회
        $scope.searchSalePriceList(scope.tuClsType);
        event.preventDefault();

    });

    // 본사판매가 조회
    $scope.searchSalePriceList = function(data){

        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $("#srchHqSalePriceProdCd").val();
        params.prodNm = $("#srchHqSalePriceProdNm").val();
        if(data !== undefined){
            params.tuClsType = data;
        } else {
            params.tuClsType = $scope.hqTuClsType;
        }
        console.log(params);

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getHqSalePrice.sb", params, function () {
            if(data !== undefined) {
                $scope.hqTuClsTypeCombo.selectedValue = data;
            }
        });
    };

    // 닫기
    $scope.close = function () {
        if(orgnFg === "HQ"){
            $("#srchEnvStoreCd").val("");
            $("#srchEnvStoreNm").val("");
        }
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer2.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl2');
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

    $scope.selectedIndexChanged = function (s) {
        if (s.selectedValue === "choice") {
            $("#storeSaveStore").show();
        }
        else {
            $("#storeSaveStore").hide();
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.choiceSaveStoreShow = function () {
        $scope._broadcast('choiceSaveStoreCtrl');
    };

    // 저장
    $scope.saveProdPrice = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if(coercionFg === "0"){
                    if ($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
                        $scope._popMsg(messages["salePrice.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
                        return false;
                    }
                }
            }
        }

        if($scope.storeSaveFgCombo.selectedValue === "choice") {
            if ($("#choiceSaveStoreCd").val() === "") {
                $scope._popMsg("매장을 선택해주세요");
                return false;
            }
        }

        var numchkexp = /[^0-9]/g; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-[0-9]/g;

        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if (($scope.flex.collectionView.items[i].storeSaleUprc !== $scope.flex.collectionView.items[i].saleUprc) ||
                    ($scope.flex.collectionView.items[i].storeStinSaleUprc !== $scope.flex.collectionView.items[i].stinSaleUprc) ||
                    ($scope.flex.collectionView.items[i].storeDlvrSaleUprc !== $scope.flex.collectionView.items[i].dlvrSaleUprc) ||
                    ($scope.flex.collectionView.items[i].storePackSaleUprc !== $scope.flex.collectionView.items[i].packSaleUprc)) {

                    // 변경판매가 - 필수 입력값 체크
                    if ($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                        $scope._popMsg(messages["salePrice.saleUprcBlank"]);
                        return false;
                    }

                    // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
                    Number.isInteger = Number.isInteger || function(value) {
                        return typeof value === "number" &&
                            isFinite(value) &&
                            Math.floor(value) === value;
                    };

                    // 변경판매가 - 소수점 입력 불가
                    if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].saleUprc)) == false){
                        $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 변경판매가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) {
                        if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                            $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경판매가 - 1000000000 이상 입력 불가
                    if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){
                        $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 내점/배달/포장 판매가 사용 시
                    if(subPriceFg === "1") {

                        // 변경내점-판매가 입력했을 경우 체크
                        if ($scope.flex.collectionView.items[i].stinSaleUprc !== "" && $scope.flex.collectionView.items[i].stinSaleUprc !== null) {

                            // 변경내점-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].stinSaleUprc)) == false) {
                                $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경내점-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test($scope.flex.collectionView.items[i].stinSaleUprc)) {
                                if ((numchkexp2.test($scope.flex.collectionView.items[i].stinSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경내점-판매가 - 1000000000 이상 입력 불가
                            if ($scope.flex.collectionView.items[i].stinSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경배달-판매가 입력했을 경우 체크
                        if ($scope.flex.collectionView.items[i].dlvrSaleUprc !== "" && $scope.flex.collectionView.items[i].dlvrSaleUprc !== null) {

                            // 변경배달-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].dlvrSaleUprc)) == false) {
                                $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경배달-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test($scope.flex.collectionView.items[i].dlvrSaleUprc)) {
                                if ((numchkexp2.test($scope.flex.collectionView.items[i].dlvrSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경배달-판매가 - 1000000000 이상 입력 불가
                            if ($scope.flex.collectionView.items[i].dlvrSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경포장-판매가 입력했을 경우 체크
                        if ($scope.flex.collectionView.items[i].packSaleUprc !== "" && $scope.flex.collectionView.items[i].packSaleUprc !== null) {

                            // 변경포장-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].packSaleUprc)) == false) {
                                $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경포장-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test($scope.flex.collectionView.items[i].packSaleUprc)) {
                                if ((numchkexp2.test($scope.flex.collectionView.items[i].packSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경포장-판매가 - 1000000000 이상 입력 불가
                            if ($scope.flex.collectionView.items[i].packSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }
                    }
                    $scope.flex.collectionView.items[i].storeCd = $("#searchStoreCd").val();
                    $scope.flex.collectionView.items[i].applyFg = $scope.storeSaveFgCombo.selectedValue;
                    if($scope.storeSaveFgCombo.selectedValue === "choice") {
                        if($("#choiceSaveStoreCd").val() === ""){
                            $scope._popMsg("매장을 선택해주세요");
                            return false;
                        }
                        $scope.flex.collectionView.items[i].saveStoreCds = $("#choiceSaveStoreCd").val();
                    }
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        if ($scope.storeSaveFgCombo.selectedValue !== "none") {
            $scope._popConfirm( "하위매장에 가격이 적용됩니다. 그래도 저장하시겠습니까?", function(){
                console.log(params);
                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save('/base/price/salePrice/prodSalePrice/saveHqProdSalePrice.sb', params, function(){
                    $scope.searchSalePriceList();
                });
            });
        } else {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/price/salePrice/prodSalePrice/saveHqProdSalePrice.sb', params, function(){
                $scope.searchSalePriceList();
            });
        }

    };
}]);