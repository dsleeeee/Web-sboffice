/****************************************************************
 *
 * 파일명 : productDtl.js
 * 설  명 : 생산관리 - 생산등록 생산/폐기등록 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.27     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 생산/폐기등록 상세 팝업 controller */
app.controller('productDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('productDtlCtrl', $scope, $http, true));

    // 제목변경여부 파악을 위한 변수
    var global_productTitle;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        s.cellEditEnded.addHandler(function (s, e) {
          if (e.panel === s.cells) {
            var col = s.columns[e.col];
              //
              if (col.binding === "productSaleUprc") {
                var item = s.rows[e.row].dataItem;
                $scope.calcAmt(item);
              }
              if (col.binding === "productQty") {
                var item = s.rows[e.row].dataItem;
                $scope.calcAmt(item);
              }
          }

          s.collectionView.commitEdit();
        });

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 폐기등록시, 생산중량, 생산판매가는 입력 불가
                if (col.binding === 'productWeight' || col.binding === 'productSaleUprc') {
                    if($("#hdProductFg").val() === "1") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.calcAmt = function (item) {
        var productSaleUprc = parseInt(nvl(item.productSaleUprc, 0));
        var productQty   = parseInt(nvl(item.productQty, 0));
        var productAmt   = parseInt(productQty) * parseInt(productSaleUprc);

        item.productAmt = productAmt; // 조정금액
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("productDtlCtrl", function (event, data) {

        // 그리드 초기화
        var cv = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data = cv;

        if(!$.isEmptyObject(data)){

            $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

            // 상품 리스트 조회
            $scope.searchProductDtlList(data);
        }
        else { // 페이징처리에서 broadcast 호출시
            var params = {};
            params.productDate = $("#hdDtlProductDate").val();
            params.productFg = $("#hdDtlProductFg").val();
            params.seqNo = $("#hdDtlSeqNo").val();
            $scope.searchProductDtlList(params);
        }

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();

    });

    // 상품 리스트 조회
    $scope.searchProductDtlList = function (data) {
        var params = {};
        params.productDate = data.productDate;
        params.productFg = data.productFg;
        params.seqNo = data.seqNo;
        //params.productStorageCd = data.productStorageCd;
        params.listScale = 50;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/stock/product/product/productDtl/list.sb", params, function() {

            // hidden set
            $("#hdDtlProductDate").val(data.productDate);
            $("#hdDtlProductFg").val(data.productFg);
            $("#hdDtlSeqNo").val(data.seqNo);

            if(data.productFg === "0"){ // 생산등록
                $("#popDtlTitle").text(messages["product.product"]);
                $("#lblPDProductDateTitle").text(messages["product.product"] + messages["product.date"] + " : " + getFormatDate(data.productDate, '-'));
                $("#lblPDTitleTitle").text(messages["product.product"] + messages["product.title"]);
            }else{ // 폐기등록
                $("#popDtlTitle").text(messages["product.disuseReg"]);
                $("#lblPDProductDateTitle").text(messages["product.disuseReg"] + messages["product.date"] + " : " + getFormatDate(data.productDate, '-'));
                $("#lblPDTitleTitle").text(messages["product.disuseReg"] + messages["product.title"]);
            }

            //가상로그인 session 설정
            if(document.getElementsByName('sessionId')[0]){
                params['sid'] = document.getElementsByName('sessionId')[0].value;
            }

            // 진행상황과 제목 조회(상세정보 조회)
            // ajax 통신 설정
            $http({
              method : 'POST', //방식
              url    : '/stock/product/product/productDtl/info.sb', /* 통신할 URL */
              params : params, /* 파라메터로 보낼 데이터 */
              headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
              if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {

                  // 제목
                  $("#txtPDTitle").val(response.data.data.productTitle);
                  global_productTitle =	response.data.data.productTitle;

                  // 진행구분이 확정이면 상품추가/변경 불가
                  // 확정상태가 아니면 버튼 show
                  if (response.data.data.procFg !== "" && response.data.data.procFg === "0") {
                    $scope.btnDtlAddProd = true;
                    $scope.btnDtlSave    = true;
                    $scope.btnDtlConfirm = true;
                  }
                  else {
                    $scope.btnDtlAddProd = false;
                    $scope.btnDtlSave    = false;
                    $scope.btnDtlConfirm = false;
                  }

                }
              }
            }, function errorCallback(response) {
              // called asynchronously if an error occurs
              // or server returns response with an error status.
              if (response.data.message) {
                $scope._popMsg(response.data.message);
              } else {
                $scope._popMsg(messages['cmm.error']);
              }
              return false;
            }).then(function () {});
        });
    };

    // 상품추가
    $scope.addProd = function () {
       var params  = {};
       params.productDate = $("#hdDtlProductDate").val();
       params.productFg = $("#hdDtlProductFg").val();
       params.seqNo = $("#hdDtlSeqNo").val();

        $scope.wjProductRegistLayer.show(true);
        $scope._broadcast('productRegistCtrl', params);
    };

    // 저장
    $scope.saveProductDtl = function(confirmFg){
        
        // 제목 입력 체크
        if (nvl($("#txtPDTitle").val(), '') === '') {
            var msg = "";
            if($("#hdDtlProductFg").val() === "0"){
                msg = messages["product.product"] + messages["product.title"] + messages["cmm.require.text"];
            }else{
                msg = messages["product.disuseReg"] + messages["product.title"] + messages["cmm.require.text"];
            }
          $scope._popMsg(msg);
          return false;
        }

        // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
        if (confirmFg === 'Y' && $scope.flex.collectionView.itemsEdited.length <= 0) {
          var item = $scope.flex.collectionView.items[0];
          if (item === null) return false;

          $scope.flex.collectionView.editItem(item);
          item.status = "U";
          $scope.flex.collectionView.commitEdit();
        }

        // 상품변경 없이 제목만 변경했을 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
        if ($scope.flex.collectionView.itemsEdited.length <= 0 && global_productTitle != $("#txtPDTitle").val()){

            var item = $scope.flex.collectionView.items[0];
            if (item === null) return false;

            $scope.flex.collectionView.editItem(item);
            item.status = "U";
            $scope.flex.collectionView.commitEdit();
        }

        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

          var item = $scope.flex.collectionView.itemsEdited[i];

          // 체크박스가 체크되어 있는 상품은 삭제한다.
          if (item.gChk === true && nvl(item.productQty, '') !== '') {
            item.status = "D";
          }
          else {
            item.status = "U";
          }
          item.productDate = $("#hdDtlProductDate").val();
          item.productFg = $("#hdDtlProductFg").val();
          item.seqNo = $("#hdDtlSeqNo").val();
          item.productTitle = $("#txtPDTitle").val();
          item.storageCd = "999";	//001	->	999
          item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

          if(orgnFg === "HQ"){
            item.productStorageCd = "001";
          }else{
            item.productStorageCd = "000";
          }

          item.confirmFg = confirmFg;

          params.push(item);
        }

        $scope._save("/stock/product/product/productDtl/save.sb", params, function () {

            $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

            // 상품 상세 재조회
            var params = {};
            params.productDate = $("#hdDtlProductDate").val();
            params.productFg = $("#hdDtlProductFg").val();
            params.seqNo = $("#hdDtlSeqNo").val();
            $scope.searchProductDtlList(params);
            
            // 부모창 재조회
            var vScope = agrid.getScope('productCtrl');
            vScope.searchProductList();

            // 확정인 경우, 상세창 hide
            if (confirmFg === 'Y') {
              $scope.wjProductDtlLayer.hide(true);
            }
        });
    };

    // 확정
    $scope.confirmProduct = function(){

        if($scope.flex.collectionView.items.length == 0) {
            $scope._popMsg("확정하시려면 상품을 추가 해주세요.");
            return false;
        }

        var msg = messages["product.confirmMsg"]; // 확정하시겠습니까?
        var checkLenth = 0;

        s_alert.popConf(msg, function () {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                  var item = $scope.flex.collectionView.items[i];
                  if(item.gChk == true){
                      $scope._popMsg("확정하시려면 상품목록의 체크박스를 해제해주세요.");
                      return false;
                  }else{
                      checkLenth++;
                  }
             }

            //체크해제된 ROW수가 총 ROW와 같으면 확정
            if($scope.flex.collectionView.items.length == checkLenth){
                $scope.saveProductDtl('Y');
                return false;
            }
        });
    };

    // 상세 닫기
    $scope.closeDtl = function () {

        $("#hdDtlProductDate").val("");
        $("#hdDtlProductFg").val("");
        $("#hdDtlSeqNo").val("");

        $("#popDtlTitle").text("");
        $("#lblPDProductDateTitle").text("");
        $("#lblPDTitleTitle").text("");
        $("#txtPDTitle").val("");
    };

}]);