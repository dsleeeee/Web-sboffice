/****************************************************************
 *
 * 파일명 : prodSalePrice.js
 * 설  명 : 상품별 판매가관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 판매가 선택 DropBoxDataMap
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

/**
 * 상품별 판매가관리 그리드 생성
 */
app.controller('prodSalePriceCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSalePriceCtrl', $scope, $http, false));

    // 상품정보
    $scope.prodInfo;
    $scope.setProdInfo = function(data){
        $scope.prodInfo = data;
    };
    $scope.getProdInfo = function(){
        return $scope.prodInfo;
    };

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData2);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("saleAmtOption", saleAmtOptionFg);
    $scope._setComboData("stinSaleUprcOption", stinSaleAmtOptionFg);
    $scope._setComboData("dlvrSaleUprcOption", dlvrSaleAmtOptionFg);
    $scope._setComboData("packSaleUprcOption", packSaleAmtOptionFg);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    // 검색조건 일괄변경
    $("#tblProdChange").show();

  $scope.prodSaleUprcApply = true;

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodSalePriceCtrl', $scope, $http, false));

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
          if($scope.prodSaleUprcApply){
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
      dataItem.storeCd              = messages["salePrice.storeCd"];
      dataItem.storeNm              = messages["salePrice.storeNm"];
      dataItem.hqSaleUprc           = messages["salePrice.salePrice"];
      dataItem.saleUprcP            = messages["salePrice.salePrice"];
      dataItem.saleUprc             = messages["salePrice.salePrice"];
      dataItem.hqStinSaleUprc       = messages["salePrice.stinSaleUprc"];
      dataItem.stinSaleUprcP        = messages["salePrice.stinSaleUprc"];
      dataItem.stinSaleUprc         = messages["salePrice.stinSaleUprc"];
      dataItem.hqDlvrSaleUprc       = messages["salePrice.dlvrSaleUprc"];
      dataItem.dlvrSaleUprcP        = messages["salePrice.dlvrSaleUprc"];
      dataItem.dlvrSaleUprc         = messages["salePrice.dlvrSaleUprc"];
      dataItem.hqPackSaleUprc       = messages["salePrice.packSaleUprc"];
      dataItem.packSaleUprcP        = messages["salePrice.packSaleUprc"];
      dataItem.packSaleUprc         = messages["salePrice.packSaleUprc"];
      dataItem.prcCtrlFg            = messages["salePrice.prcCtrlFg"];

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

  // 상품정보관리 그리드 조회
  $scope.$on("prodSalePriceCtrl", function(event, data) {

    $scope.searchSalePriceInfo();
    event.preventDefault();
  });

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function(){
        if( $("#tblProdChange").css("display") === 'none'){
            $("#tblProdChange").show();
        } else {
            $("#tblProdChange").hide();
        }
    };

  // 상품별 판매가관리 목록 조회
  $scope.searchSalePriceInfo = function(){

    if( isEmptyObject( $("#prodCd").val()) ) {
      $scope._popMsg("상품을 선택해주세요.");
      return false;
    }

    var params = {};
    params.prodCd = $("#prodCd").val();
    params.storeCd = $("#storeCd").val();

    // 상품 정보 조회
    $scope._postJSONQuery.withOutPopUp('/base/price/salePrice/prodSalePrice/getProdInfo.sb', params,
      function(response){
        // console.log('response.data.data', response.data.data);
        $scope.setProdInfo(response.data.data);

        if( isEmptyObject($scope.getProdInfo().prodCd) ) {
          $scope._popMsg(messages["cmm.error"]);
          return false;
        }
        $scope.searchSalePriceList();
      }
    );
  };

    // 판매가 콤보박스 선택 이벤트
    $scope.inputSaleAmtReadOnly = false;
    $scope.setSelectedSaleAmtOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputSaleAmtReadOnly = false;
        } else {
            $scope.inputSaleAmtReadOnly = true;
        }
    };

    // 내점-판매가 콤보박스 선택 이벤트
    $scope.inputStinSaleUprcReadOnly = false;
    $scope.setSelectedStinSaleUprcOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputStinSaleUprcReadOnly = false;
        } else {
            $scope.inputStinSaleUprcReadOnly = true;
        }
    };

    // 배달-판매가 콤보박스 선택 이벤트
    $scope.inputDlvrSaleUprcReadOnly = false;
    $scope.setSelectedDlvrSaleUprcOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputDlvrSaleUprcReadOnly = false;
        } else {
            $scope.inputDlvrSaleUprcReadOnly = true;
        }
    };

    // 포장-판매가 콤보박스 선택 이벤트
    $scope.inputPackSaleUprcReadOnly = false;
    $scope.setSelectedPackSaleUprcOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputPackSaleUprcReadOnly = false;
        } else {
            $scope.inputPackSaleUprcReadOnly = true;
        }
    };

  // 가격 변경
  $scope.calcAmt = function(item){

    // console.log('calcAmt',item);
    var costUprc = item.costUprc;
    var saleUprc = item.saleUprc;
    var splyUprc = item.storeSplyUprc;
    var poUnitQty =  item.poUnitQty;

    item.hqMarginAmt = (splyUprc - costUprc); // 본사마진금액
    item.hqMarginRate = (splyUprc - costUprc) / costUprc * 100; // 본사마진율
    item.saleUprcAmt = (saleUprc - poUnitQty); // 현재판매금액
    item.storeMarginAmt = ((saleUprc - poUnitQty) - splyUprc); // 매장마진금액
    item.storeMarginRate = ((saleUprc - poUnitQty) - splyUprc) / (saleUprc - poUnitQty) * 100; // 매장마진율
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

  // 판매가 그리드 조회
  $scope.searchSalePriceList = function(){

    var params = {};
    params.prodCd = $("#prodCd").val();
    params.storeCd = $("#storeCd").val();
    params.momsTeam = $scope.momsTeam;
    params.momsAcShop = $scope.momsAcShop;
    params.momsAreaFg = $scope.momsAreaFg;
    params.momsCommercial = $scope.momsCommercial;
    params.momsShopType = $scope.momsShopType;
    params.momsStoreManageType = $scope.momsStoreManageType;
    params.branchCd = $scope.branchCd;
    if(brandUseFg === "1" && orgnFg === "HQ"){
        // 선택한 매장브랜드가 있을 때
        params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
        // 선택한 매장브랜드가 없을 때('전체' 일때)
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var userHqBrandCd = "";
            for(var i=0; i < userHqBrandCdComboList.length; i++){
                if(userHqBrandCdComboList[i].value !== null) {
                    userHqBrandCd += userHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
    }
    params.listScale = $scope.listScaleCombo.text;
    // console.log(params);

    $scope._inquirySub('/base/price/salePrice/prodSalePrice/getProdSalePriceList.sb', params, function() {

      // 조회한 값으로 마진금액, 마진율 계산
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        $scope.calcAmt($scope.flex.collectionView.items[i]);
        $scope.flex.collectionView.commitEdit();
      }

        // 가격관리구분에 의해 본사는 매장의 상품가격 수정 불가(매장판매가관리본사강제수정 가능인 경우는 수정가능)
        if(coercionFg === "0") {
            var grid = wijmo.Control.getControl("#wjGridProdSalePriceArea");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if (item.prcCtrlFg === "S") {
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }
        }


    }, false);
  };

  // 판매가 일괄적용 버튼 클릭
  // 매장판매가 일괄적용시, 입력한 매장판매가를 적용시킴.
  // 본사판매가 일괄적용시, 조회된 본사판매가를 적용시킴.
  $scope.changeAmt = function() {

    if( isEmptyObject( $("#prodCd").val()) ) {
      $scope._popMsg("상품을 선택해주세요.");
      return false;
    }

    if( $scope.flex.collectionView === undefined){
      $scope._popMsg("판매가를 일괄적용할 매장을 조회해주세요.");
      return false;
    }

    var saleAmtOption = $scope.prodInfo.saleAmtOption;

    var selectCnt = 0;
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
        var item = $scope.flex.collectionView.items[i];
        if(item.gChk){ selectCnt ++; }
    }

    if(selectCnt < 1) {
        $scope._popMsg("매장을 선택해주세요.");
        return false;
    }

    if(saleAmtOption === 'S') {
       if($scope.prodInfo.inputSaleAmt === undefined || $scope.prodInfo.inputSaleAmt === '') {
          $scope._popMsg("판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
          return false;
        }
     }

    var newSaleAmt = 0;

    if(saleAmtOption === 'S'){ // 매장
        newSaleAmt = Number($scope.prodInfo.inputSaleAmt);
    }else{ // 본사
        newSaleAmt = $scope.prodInfo.saleUprc; // 상품선택에서 선택한 상품의 본사가격
    }

    // 변경 판매가 적용
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
            $scope.flex.collectionView.items[i].saleUprc = newSaleAmt;
        }
    }

    $scope.flex.collectionView.commitEdit();
    $scope.flex.collectionView.refresh();
  };
  
  // 내점-판매가 일괄적용 버튼 클릭
  $scope.changeStinSaleUprc = function(){

    if( isEmptyObject( $("#prodCd").val()) ) {
      $scope._popMsg("상품을 선택해주세요.");
      return false;
    }

    if( $scope.flex.collectionView === undefined){
        $scope._popMsg("내점-판매가를 일괄적용할 매장을 조회해주세요.");
       return false;
    }

    var stinSaleUprcOption = $scope.prodInfo.stinSaleUprcOption;

    var selectCnt = 0;
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
        var item = $scope.flex.collectionView.items[i];
        if(item.gChk){ selectCnt ++; }
    }

    if(selectCnt < 1) {
        $scope._popMsg("매장을 선택해주세요.");
        return false;
    }

    if(stinSaleUprcOption === 'S') {
        if($scope.prodInfo.inputStinSaleUprc === undefined || $scope.prodInfo.inputStinSaleUprc === '') {
            $scope._popMsg("내점-판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
            return false;
        }
    }

    var newStinSaleUprc = 0;

    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
       if($scope.flex.collectionView.items[i].gChk) {

           newStinSaleUprc = 0;

           if(stinSaleUprcOption === "S"){ // 매장
               newStinSaleUprc = Number($scope.prodInfo.inputStinSaleUprc);
           }else if(stinSaleUprcOption === "H"){ // 본사
               newStinSaleUprc = Number($scope.prodInfo.saleUprc);
           }else if(stinSaleUprcOption === "SS") { // 매장내점
               newStinSaleUprc = Number($scope.flex.collectionView.items[i].stinSaleUprcP);
           }else if(stinSaleUprcOption === "HS") { // 본사내점
               newStinSaleUprc = Number($scope.prodInfo.stinSaleUprc);
           }

           $scope.flex.collectionView.items[i].stinSaleUprc = newStinSaleUprc;
        }
    }

    $scope.flex.collectionView.commitEdit();
    $scope.flex.collectionView.refresh();
  };

  // 배달-판매가 일괄적용 버튼 클릭
  $scope.changeDlvrSaleUprc = function(){

      if( isEmptyObject( $("#prodCd").val()) ) {
          $scope._popMsg("상품을 선택해주세요.");
          return false;
      }

      if( $scope.flex.collectionView === undefined){
          $scope._popMsg("배달-판매가를 일괄적용할 매장을 조회해주세요.");
          return false;
      }

      var dlvrSaleUprcOption = $scope.prodInfo.dlvrSaleUprcOption;

      var selectCnt = 0;
      for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
          var item = $scope.flex.collectionView.items[i];
          if(item.gChk){ selectCnt ++; }
      }

      if(selectCnt < 1) {
          $scope._popMsg("매장을 선택해주세요.");
          return false;
      }

      if(dlvrSaleUprcOption === 'S') {
          if($scope.prodInfo.inputDlvrSaleUprc === undefined || $scope.prodInfo.inputDlvrSaleUprc === '') {
              $scope._popMsg("배달-판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
              return false;
          }
      }

      var newDlvrSaleUprc = 0;

      for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
          if($scope.flex.collectionView.items[i].gChk) {

              newDlvrSaleUprc = 0;

              if(dlvrSaleUprcOption === "S"){ // 매장
                  newDlvrSaleUprc = Number($scope.prodInfo.inputDlvrSaleUprc);
              }else if(dlvrSaleUprcOption === "H"){ // 본사
                  newDlvrSaleUprc = Number($scope.prodInfo.saleUprc);
              }else if(dlvrSaleUprcOption === "SD") { // 매장배달
                  newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].dlvrSaleUprcP);
              }else if(dlvrSaleUprcOption === "HD") { // 본사배달
                  newDlvrSaleUprc = Number($scope.prodInfo.dlvrSaleUprc);
              }

              $scope.flex.collectionView.items[i].dlvrSaleUprc = newDlvrSaleUprc;
          }
      }

      $scope.flex.collectionView.commitEdit();
      $scope.flex.collectionView.refresh();
  };

  // 포장-판매가 일괄적용 버튼 클릭
  $scope.changePackSaleUprc = function(){

      if( isEmptyObject( $("#prodCd").val()) ) {
          $scope._popMsg("상품을 선택해주세요.");
          return false;
      }

      if( $scope.flex.collectionView === undefined){
          $scope._popMsg(" 포장-판매가를 일괄적용할 매장을 조회해주세요.");
          return false;
      }

      var packSaleUprcOption = $scope.prodInfo.packSaleUprcOption;

      var selectCnt = 0;
      for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
          var item = $scope.flex.collectionView.items[i];
          if(item.gChk){ selectCnt ++; }
      }

      if(selectCnt < 1) {
          $scope._popMsg("매장을 선택해주세요.");
          return false;
      }

      if(packSaleUprcOption === 'S') {
          if($scope.prodInfo.inputPackSaleUprc === undefined || $scope.prodInfo.inputPackSaleUprc === '') {
              $scope._popMsg("포장-판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
              return false;
          }
      }

      var newPackSaleUprc = 0;

      for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
          if($scope.flex.collectionView.items[i].gChk) {

              newPackSaleUprc = 0;

              if(packSaleUprcOption === "S"){ // 매장
                  newPackSaleUprc = Number($scope.prodInfo.inputPackSaleUprc);
              }else if(packSaleUprcOption === "H"){ // 본사
                  newPackSaleUprc = Number($scope.prodInfo.saleUprc);
              }else if(packSaleUprcOption === "SP") { // 매장포장
                  newPackSaleUprc = Number($scope.flex.collectionView.items[i].packSaleUprcP);
              }else if(packSaleUprcOption === "HP") { // 본사포장
                  newPackSaleUprc = Number($scope.prodInfo.packSaleUprc);
              }

              $scope.flex.collectionView.items[i].packSaleUprc = newPackSaleUprc;
          }
      }

      $scope.flex.collectionView.commitEdit();
      $scope.flex.collectionView.refresh();
  };

  // 저장
  $scope.saveProdPrice = function(){

    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
      if ($scope.flex.collectionView.items[i].gChk) {
          if(coercionFg === "0") {
              // PRC_CTRL_FG 가격관리구분 H인 상품만 수정가능
              if ($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
                  $scope._popMsg(messages["salePrice.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
                  return false;
              }
          }
      }
    }

    // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
    Number.isInteger = Number.isInteger || function(value) {
      return typeof value === "number" &&
          isFinite(value) &&
          Math.floor(value) === value;
    };

    var numchkexp = /[^0-9]/g; // 숫자가 아닌 값 체크
    var numchkexp2 = /^-[0-9]/g;

    // 파라미터 설정
    var params = new Array();

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if ($scope.flex.collectionView.items[i].gChk) {
        if (($scope.flex.collectionView.items[i].saleUprcP !== $scope.flex.collectionView.items[i].saleUprc) ||
            ($scope.flex.collectionView.items[i].stinSaleUprcP !== $scope.flex.collectionView.items[i].stinSaleUprc) ||
            ($scope.flex.collectionView.items[i].dlvrSaleUprcP !== $scope.flex.collectionView.items[i].dlvrSaleUprc) ||
            ($scope.flex.collectionView.items[i].packSaleUprcP !== $scope.flex.collectionView.items[i].packSaleUprc)) {

          // 변경판매가 - 필수 입력값 체크
          if ($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
            $scope._popMsg(messages["salePrice.saleUprcBlank"]);
            return false;
          }

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

          params.push($scope.flex.collectionView.items[i]);

        }
      }
    }

    // console.log('params',params)

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/price/salePrice/prodSalePrice/saveProdSalePrice.sb', params, function(){
      $scope.searchSalePriceList();
    });
  };

  // 상품선택 모듈 팝업 사용시 정의 (상품찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.prodShow = function () {
    $scope._broadcast('prodCtrl');
  };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 파라미터
        var params = {};
        params.prodCd = $("#prodCd").val();
        params.storeCd = $("#storeCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('prodSalePriceExcelCtrl', params);
        });
    };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('prodSalePriceExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSalePriceExcelCtrl', $scope, $http, false));



    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        s.allowMerging = 'ColumnHeaders';
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem                  = {};
        dataItem.gChk                 = messages["cmm.chk"];
        dataItem.storeCd              = messages["salePrice.storeCd"];
        dataItem.storeNm              = messages["salePrice.storeNm"];
        dataItem.hqSaleUprc           = messages["salePrice.salePrice"];
        dataItem.saleUprcP            = messages["salePrice.salePrice"];
        dataItem.saleUprc             = messages["salePrice.salePrice"];
        dataItem.hqStinSaleUprc       = messages["salePrice.stinSaleUprc"];
        dataItem.stinSaleUprcP        = messages["salePrice.stinSaleUprc"];
        dataItem.stinSaleUprc         = messages["salePrice.stinSaleUprc"];
        dataItem.hqDlvrSaleUprc       = messages["salePrice.dlvrSaleUprc"];
        dataItem.dlvrSaleUprcP        = messages["salePrice.dlvrSaleUprc"];
        dataItem.dlvrSaleUprc         = messages["salePrice.dlvrSaleUprc"];
        dataItem.hqPackSaleUprc       = messages["salePrice.packSaleUprc"];
        dataItem.packSaleUprcP        = messages["salePrice.packSaleUprc"];
        dataItem.packSaleUprc         = messages["salePrice.packSaleUprc"];
        dataItem.prcCtrlFg            = messages["salePrice.prcCtrlFg"];

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

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodSalePriceExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/salePrice/salePrice/getProdSaleExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridExcelList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            // for (var i = 0; i < columnsCnt; i++) {
            //     columns[i].visible = true;
            // }

            // <-- //그리드 visible -->

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        //return column.visible;
                        return column.binding != 'gChk';
                    }
                }, messages["salePrice.prodSalePrice"] + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);
