/****************************************************************
 *
 * 파일명 : salePriceManage.js
 * 설  명 : 판매가관리(매장용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.13     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 단독매장용 판매가 선택 DropBoxDataMap
var saleAmtOptionFgStore = [
    {"name":"매장판매가","value":"S"}
];
var stinSaleAmtOptionFgStore = [
    {"name":"매장판매가","value":"S"},
    {"name":"매장내점-판매가","value":"SS"}
];
var dlvrSaleAmtOptionFgStore = [
    {"name":"매장판매가","value":"S"},
    {"name":"매장배달-판매가","value":"SD"}
];
var packSaleAmtOptionFgStore = [
    {"name":"매장판매가","value":"S"},
    {"name":"매장포장-판매가","value":"SP"}
];

// 프랜차이즈매장용 판매가 선택 DropBoxDataMap
var saleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"}
];
var stinSaleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"},
    {"name":"매장내점-판매가","value":"SS"},
    {"name":"본사내점-판매가","value":"HS"}
];
var dlvrSaleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"},
    {"name":"매장배달-판매가","value":"SD"},
    {"name":"본사배달-판매가","value":"HD"}
];
var packSaleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"},
    {"name":"매장포장-판매가","value":"SP"},
    {"name":"본사포장-판매가","value":"HP"}
];

// 단위 DropBoxDataMap
var unitFg = [
    {"name":"1원 단위","value":"1"},
    {"name":"100원 단위","value":"100"},
    {"name":"1,000원 단위","value":"1000"}
];
// 반올림 DropBoxDataMap
var modeFg = [
    {"name":"반올림","value":"0"},
    {"name":"절상","value":"1"},
    {"name":"절하","value":"2"}
];

/**
 *  판매가관리(매장용) 그리드 생성
 */
app.controller('salePriceManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('salePriceManageCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 매장일때만
        if(hqOfficeCd != "00000") {
            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];

                    // 체크박스
                    if (col.binding === "gChk" || col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (item[("prcCtrlFg")] === 'H') {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            item[("gChk")] = false; // 전체 체크시 오류

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            });
        }


        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem                  = {};
        dataItem.gChk                 = messages["cmm.chk"];
        dataItem.prodCd               = messages["salePrice.prodCd"];
        dataItem.prodNm               = messages["salePrice.prodNm"];
        dataItem.hqCostUprc           = messages["salePriceManage.hqCostUprc"];
        dataItem.hqSplyUprc           = messages["salePriceManage.hqSplyUprc"];
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

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function(){
        if( $("#tblChange").css("display") === 'none'){
            $("#tblChange").show();
        } else {
            $("#tblChange").hide();
        }
    };

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);
    if(hqOfficeCd == "00000") {
        $scope._setComboData("saleAmtOption", saleAmtOptionFgStore);
        $scope._setComboData("storeStinSaleUprcOption", stinSaleAmtOptionFgStore);
        $scope._setComboData("storeDlvrSaleUprcOption", dlvrSaleAmtOptionFgStore);
        $scope._setComboData("storePackSaleUprcOption", packSaleAmtOptionFgStore);
    } else {
        $scope._setComboData("saleAmtOption", saleAmtOptionFg);
        $scope._setComboData("storeStinSaleUprcOption", stinSaleAmtOptionFg);
        $scope._setComboData("storeDlvrSaleUprcOption", dlvrSaleAmtOptionFg);
        $scope._setComboData("storePackSaleUprcOption", packSaleAmtOptionFg);
    }
    $scope._setComboData("changeUnit", unitFg);
    $scope._setComboData("changeMode", modeFg);
    $scope._setComboData("stinSaleUprcChangeUnit", unitFg);
    $scope._setComboData("stinSaleUprcChangeMode", modeFg);
    $scope._setComboData("dlvrSaleUprcChangeUnit", unitFg);
    $scope._setComboData("dlvrSaleUprcChangeMode", modeFg);
    $scope._setComboData("packSaleUprcChangeUnit", unitFg);
    $scope._setComboData("packSaleUprcChangeMode", modeFg);

    // <-- 검색 호출 -->
    $scope.$on("salePriceManageCtrl", function(event, data) {
        $scope.searchSalePriceManage();
        event.preventDefault();
    });

    $scope.searchSalePriceManage = function(){
        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.listScale = $scope.listScaleCombo.text;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;

        $scope._inquirySub('/base/price/salePriceManage/salePriceManage/getSalePriceManageList.sb', params, function() {}, false);
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

    // 상품정보
    $scope.prodInfo;
    $scope.setProdInfo = function(data){
        $scope.prodInfo = data;
    };
    $scope.getProdInfo = function(){
        return $scope.prodInfo;
    };

    // 판매가 일괄적용 버튼 클릭
    // 매장판매가 일괄적용시, 입력한 매장판매가를 적용시킴.
    // 본사판매가 일괄적용시, 조회된 본사판매가를 적용시킴.
    $scope.changeAmt = function() {

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var saleAmtOption = $scope.prodInfo.saleAmtOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputSaleRate").val() === ""){
            $scope._popMsg("판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newSaleAmt = 0;

        // 변경 판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newSaleAmt = 0;
                if(saleAmtOption === "S"){ // 매장
                    newSaleAmt = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputSaleRate").val())/100);
                }else if(saleAmtOption === "H"){ // 본사
                    newSaleAmt = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputSaleRate").val())/100);
                }

                $scope.flex.collectionView.items[i].saleUprc = $scope.calChangeAmt(newSaleAmt, "1");
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 내점-판매가 일괄적용 버튼 클릭
    $scope.changeStinSaleUprc = function(){

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("내점-판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var storeStinSaleUprcOption = $scope.prodInfo.storeStinSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputStinSaleUprcRate").val() === ""){
            $scope._popMsg("내점-판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newStinSaleUprc = 0;

        // 변경내점-판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newStinSaleUprc = 0;
                if(storeStinSaleUprcOption === "S"){ // 매장
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }else if(storeStinSaleUprcOption === "H"){ // 본사
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }else if(storeStinSaleUprcOption === "SS") { // 매장내점
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].storeStinSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }else if(storeStinSaleUprcOption === "HS") { // 본사내점
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].hqStinSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }

                $scope.flex.collectionView.items[i].stinSaleUprc = $scope.calChangeAmt(newStinSaleUprc, "2");
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 배달-판매가 일괄적용 버튼 클릭
    $scope.changeDlvrSaleUprc = function(){

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("배달-판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var storeDlvrSaleUprcOption = $scope.prodInfo.storeDlvrSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputDlvrSaleUprcRate").val() === ""){
            $scope._popMsg("배달-판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newDlvrSaleUprc = 0;

        // 변경배달-판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newDlvrSaleUprc = 0;
                if(storeDlvrSaleUprcOption === "S"){ // 매장
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }else if(storeDlvrSaleUprcOption === "H"){ // 본사
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }else if(storeDlvrSaleUprcOption === "SD") { // 매장배달
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].storeDlvrSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }else if(storeDlvrSaleUprcOption === "HD") { // 본사배달
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].hqDlvrSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }

                $scope.flex.collectionView.items[i].dlvrSaleUprc = $scope.calChangeAmt(newDlvrSaleUprc, "3");
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 포장-판매가 일괄적용 버튼 클릭
    $scope.changePackSaleUprc = function(){

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg(" 포장-판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var storePackSaleUprcOption = $scope.prodInfo.storePackSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputPackSaleUprcRate").val() === ""){
            $scope._popMsg("포장-판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newPackSaleUprc = 0;

        // 변경포장-판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newPackSaleUprc = 0;
                if(storePackSaleUprcOption === "S"){ // 매장
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }else if(storePackSaleUprcOption === "H"){ // 본사
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }else if(storePackSaleUprcOption === "SP") { // 매장포장
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].storePackSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }else if(storePackSaleUprcOption === "HP") { // 본사포장
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].hqPackSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }

                $scope.flex.collectionView.items[i].packSaleUprc = $scope.calChangeAmt(newPackSaleUprc, "4");
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 변경판매가 계산
    $scope.calChangeAmt = function(amt, type){

        var ChangeAmt = 0;
        var unit;
        var mode;

        if(type === "1"){ // 판매가
            unit = $scope.prodInfo.changeUnit;
            mode = $scope.prodInfo.changeMode;
        }else if(type === "2"){ // 내점-판매가
            unit = $scope.prodInfo.stinSaleUprcChangeUnit;
            mode = $scope.prodInfo.stinSaleUprcChangeMode;
        }else if(type === "3"){ // 배달-판매가
            unit = $scope.prodInfo.dlvrSaleUprcChangeUnit;
            mode = $scope.prodInfo.dlvrSaleUprcChangeMode;
        }else{ // 포장-판매가
            unit = $scope.prodInfo.packSaleUprcChangeUnit;
            mode = $scope.prodInfo.packSaleUprcChangeMode;
        }

        if(mode === "0"){ // 반올림
            ChangeAmt = Math.round(amt/(unit*10))*(unit*10);
        }else if(mode === "1"){ //절상
            ChangeAmt = Math.ceil(amt/(unit*10))*(unit*10);
        }else if(mode === "2"){ //절하
            ChangeAmt = Math.floor(amt/(unit*10))*(unit*10);
        }

        return ChangeAmt;
    };

    // 저장
    $scope.saveProdPrice = function(){

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {
                // PRC_CTRL_FG 가격관리구분 S인 상품만 수정가능
                if ($scope.flex.collectionView.items[i].prcCtrlFg === "H") {
                    $scope._popMsg(messages["salePriceManage.prcCtrlFgHqBlank"]); // 가격관리구분이 '본사'인 상품은 수정할 수 없습니다.
                    return false;
                }
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
                        $scope._popMsg(messages["salePriceManage.saleUprcBlank"]);
                        return false;
                    }

                    // 변경판매가 - 소수점 입력 불가
                    if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].saleUprc)) == false){
                        $scope._popMsg(messages["salePriceManage.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 변경판매가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) {
                        if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                            $scope._popMsg(messages["salePriceManage.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경판매가 - 1000000000 이상 입력 불가
                    if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){
                        $scope._popMsg(messages["salePriceManage.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 내점/배달/포장 판매가 사용 시
                    if(subPriceFg === "1") {

                        // 변경내점-판매가 입력했을 경우 체크
                        if ($scope.flex.collectionView.items[i].stinSaleUprc !== "" && $scope.flex.collectionView.items[i].stinSaleUprc !== null) {

                            // 변경내점-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].stinSaleUprc)) == false) {
                                $scope._popMsg(messages["salePriceManage.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경내점-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test($scope.flex.collectionView.items[i].stinSaleUprc)) {
                                if ((numchkexp2.test($scope.flex.collectionView.items[i].stinSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePriceManage.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경내점-판매가 - 1000000000 이상 입력 불가
                            if ($scope.flex.collectionView.items[i].stinSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePriceManage.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경배달-판매가 입력했을 경우 체크
                        if ($scope.flex.collectionView.items[i].dlvrSaleUprc !== "" && $scope.flex.collectionView.items[i].dlvrSaleUprc !== null) {

                            // 변경배달-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].dlvrSaleUprc)) == false) {
                                $scope._popMsg(messages["salePriceManage.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경배달-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test($scope.flex.collectionView.items[i].dlvrSaleUprc)) {
                                if ((numchkexp2.test($scope.flex.collectionView.items[i].dlvrSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePriceManage.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경배달-판매가 - 1000000000 이상 입력 불가
                            if ($scope.flex.collectionView.items[i].dlvrSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePriceManage.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경포장-판매가 입력했을 경우 체크
                        if ($scope.flex.collectionView.items[i].packSaleUprc !== "" && $scope.flex.collectionView.items[i].packSaleUprc !== null) {

                            // 변경포장-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].packSaleUprc)) == false) {
                                $scope._popMsg(messages["salePriceManage.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경포장-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test($scope.flex.collectionView.items[i].packSaleUprc)) {
                                if ((numchkexp2.test($scope.flex.collectionView.items[i].packSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePriceManage.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경포장-판매가 - 1000000000 이상 입력 불가
                            if ($scope.flex.collectionView.items[i].packSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePriceManage.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                    }

                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/price/salePriceManage/salePriceManage/getSalePriceManageSave.sb', params, function(){
            $scope.searchSalePriceManage();
        });
    };

}]);