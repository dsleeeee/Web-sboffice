/****************************************************************
 *
 * 파일명 : storeProdBatchRegist.js
 * 설  명 : 매출수기등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.24     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var saleFgData = [
    {"name":"매출","value":"1"},
    {"name":"반품","value":"-1"}
];
app.controller('selectProdCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('selectProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "prodCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 기능구분 선택
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                // 기능구분 클릭시 해당 기능 목록 조회
                if ( col.binding === "prodCd" ) {
                    var scope = agrid.getScope('newRegistCtrl');
                    scope.addProd(selectedRow);
                    event.preventDefault();
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    // 등록 상품 그리드 조회
    $scope.$on("selectProdCtrl", function(event, data) {
        // 등록상품조회
        $scope.searchRegProd();
        event.preventDefault();
    });

    // 등록 상품 조회
    $scope.searchRegProd = function(){
        var params        = {};
        params.storeCd = $("#storeCd").text();
        $scope._inquiryMain("/excclc/excclc/saleRegist/saleRegist/getSelectProdList.sb", params, function() {});
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

}]);

app.controller('newRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('newRegistCtrl', $scope, $http, true));

    $scope._setComboData("saleFg", saleFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 포맷 핸들러
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col  = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 판매단가
                if (col.binding === "saleUprc") {
                    if(item.prodTypeFg === 1 || item.prodTypeFg === "1") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');

                    } else if(item.prodTypeFg === 4 || item.prodTypeFg === "4") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "saleUprc") {
                var dataItem = s.rows[elements.row].dataItem;
                if (dataItem.prodTypeFg === 1 || dataItem.prodTypeFg === "1") {
                    elements.cancel = true;
                } else if (dataItem.prodTypeFg === 4 || dataItem.prodTypeFg === "4") {
                    elements.cancel = true;
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 수량
                if (col.binding === "saleQty" || col.binding === "saleUprc") {
                    if(item.saleQty === 0) {
                        item.saleAmt = "0";
                        item.dcAmt = "0";
                        item.realSaleAmt = "0";
                        item.vatAmt = "0";
                    } else {
                        item.saleAmt = item.saleUprc * item.saleQty;
                        item.dcAmt = "0";
                        item.realSaleAmt = item.saleAmt;
                        if(item.vatFg === '1'){
                            item.vatAmt = Math.floor(item.realSaleAmt - item.realSaleAmt/1.1);
                        } else {
                            item.vatAmt = "0";
                        }
                        if(Math.abs(item.realSaleAmt) >= 1000000000000){
                            $scope._popMsg("금액은 최대 999,999,999,999까지 가능합니다.");
                            item.saleQty = "0";
                            item.saleAmt = item.saleUprc * item.saleQty;
                            item.dcAmt = "0";
                            item.realSaleAmt = item.saleAmt;
                            if(item.vatFg === '1'){
                                item.vatAmt = Math.floor(item.realSaleAmt - item.realSaleAmt/1.1);
                            } else {
                                item.vatAmt = "0";
                            }
                        }
                    }

                    $scope.changeAmt();
                }
                // 할인액
                if (col.binding === "dcAmt") {
                    item.realSaleAmt = item.saleAmt - item.dcAmt;
                    if(item.vatFg === '1'){
                        item.vatAmt = Math.floor(item.realSaleAmt - item.realSaleAmt/1.1);
                    } else {
                        item.vatAmt = "0";
                    }
                    $scope.changeAmt();
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    // 등록 상품 그리드 조회
    $scope.$on("newRegistCtrl", function(event, data) {

        // 등록상품조회
        $scope.searchRegProd();
        event.preventDefault();
    });

    // 상품추가
    $scope.addProd = function (prod){

        // 파라미터 설정
        var params = {};
        params.prodCd = prod.prodCd;
        params.prodNm = prod.prodNm;
        params.saleUprc = prod.saleUprc;
        params.saleQty = "1";
        params.saleAmt = prod.saleUprc;
        params.dcAmt = "0";
        params.realSaleAmt = prod.saleUprc;
        if(prod.vatFg === '1'){
            params.vatAmt = Math.floor(prod.saleUprc - prod.saleUprc/1.1);
        } else {
            params.vatAmt = "0";
        }
        params.vatFg = prod.vatFg;
        params.prodTypeFg = prod.prodTypeFg;

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);

        $scope.changeAmt();
    };

    // 상품추가, 수량변경, 할인액변경 시에 타는 로직
    $scope.changeAmt = function (){
        var cashAmt = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            cashAmt += Number($scope.flex.collectionView.items[i].realSaleAmt);
        }
        $("#cash").val(cashAmt);
        $("#card").val(0);
    }

    // 현금 금액변경
    $scope.changeCashAmt = function (){
        var totalAmt = 0;
        var cashAmt = $("#cash").val();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            totalAmt += Number($scope.flex.collectionView.items[i].realSaleAmt);
        }
        $("#card").val(totalAmt - cashAmt);
    }

    // 카드 금액변경
    $scope.changeCardAmt = function (){
        var totalAmt = 0;
        var cardAmt = $("#card").val();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            totalAmt += Number($scope.flex.collectionView.items[i].realSaleAmt);
        }
        $("#cash").val(totalAmt - cardAmt);
    }

    $scope.billChk = function (){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["saleRegist.not.data"]);
            return false;
        } else {
            if ($("#billNo").text().length === 4) {
                var params = {};
                params.storeCd = $("#storeCd").text();
                params.saleDate = $("#saleDate").text().replaceAll("-","");
                params.billNo = $("#billNo").text();
                $scope._postJSONQuery.withOutPopUp('/excclc/excclc/saleRegist/saleRegist/getBillDel.sb', params, function (result) {
                    $scope.save();
                });
            } else {
                $scope.save();
            }
        }

    }

    // 등록
    $scope.save = function(){

        var totalAmt = 0;
        var cashAmt = $("#cash").val();
        var cardAmt = $("#card").val();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            totalAmt += Number($scope.flex.collectionView.items[i].realSaleAmt);
        }

        if(Number(cashAmt) + Number(cardAmt) !== totalAmt){
            $scope._popMsg(messages['saleRegist.saleAmt.chk']);
            return false;
        }

        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].saleQty !== "0" && $scope.flex.collectionView.items[i].saleQty !== 0){
                $scope.flex.collectionView.items[i].storeCd = $("#storeCd").text();
                $scope.flex.collectionView.items[i].saleDate = $("#saleDate").text().replaceAll("-","");
                $scope.flex.collectionView.items[i].saleFg = $scope.saleFg;
                $scope.flex.collectionView.items[i].cashAmt = $("#cash").val();
                $scope.flex.collectionView.items[i].cardAmt = $("#card").val();
                $scope.flex.collectionView.items[i].billNo = $("#billNo").text();
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length == 0){
            $scope._popMsg(messages["saleRegist.not.data"]);
            return false;
        }
        console.log(params);
        $scope._save("/excclc/excclc/saleRegist/saleRegist/getNewRegistList.sb", params, function (){
            $scope.newRegistLayer.hide();

            var scope = agrid.getScope('saleRegistCtrl');
            scope.searchSaleRegistList();
        });
    };

    // 조회
    $scope.searchBillDtl = function (){
        var params        = {};
        params.storeCd = $("#storeCd").text();
        params.saleDate = $("#saleDate").text().replaceAll("-","");
        params.posNo = $("#posNo").text();
        params.billNo = $("#billNo").text();
        $scope._inquirySub("/excclc/excclc/saleRegist/saleRegist/getBillDtlList.sb", params, function() {
            $.postJSON("/excclc/excclc/saleRegist/saleRegist/getCashAmt.sb", params, function(result) {
                $("#card").val(result.data.list[0]);
                $("#cash").val(result.data.list[1]);
            });
            $.postJSON("/excclc/excclc/saleRegist/saleRegist/getSaleFg.sb", params, function(result) {
                if(result.data.list === "1"){
                    $scope.saleFgCdCombo.selectedIndex = 0;
                } else {
                    $scope.saleFgCdCombo.selectedIndex = 1;
                }
            });
        });

    }
}]);