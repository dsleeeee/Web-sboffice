/****************************************************************
 *
 * 파일명 : productRegist.js
 * 설  명 : 생산관리 - 생산등록 생산/폐기등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 생산/폐기등록 팝업 controller */
app.controller('productRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('productRegistCtrl', $scope, $http, true));

    // 제목변경여부 파악을 위한 변수
    var global_productTitle;

    // 신규 업로드 후, 바로 재조회시 사용
    var rSeqNo = "";

    $scope._setComboData("PRListScaleBox", gvListScaleBoxData);
    $scope._setComboData("PRAddQtyFg", [
        {"name": messages["product.addQtyFgApply"], "value": "apply"},
        {"name": messages["product.addQtyFgAdd"], "value": "add"}
    ]);

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

    //
    $scope.$on("productRegistCtrl", function (event, data) {

        // 그리드 초기화
        var cv = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data = cv;

        if(!$.isEmptyObject(data)){

            // hidden set
            $("#hdProductDate").val(data.productDate);
            $("#hdProductFg").val(data.productFg);
            $("#hdSeqNo").val(data.seqNo);

            // set info
            if(data.productFg === "0"){
                $("#popTitle").text(messages["product.product"]);
                $("#lblPRProductDateTitle").text(messages["product.product"] + messages["product.date"]);
                $("#lblPRProductDate").text(getFormatDate(data.productDate, '-'));
                $("#lblPRTitleTitle").text(messages["product.product"] + messages["product.title"]);
                $("#txtPRTitle").val(getFormatDate(data.productDate, '-') + " " + getCurTime(':') + " " + messages["product.product"]);
            }else{
                $("#popTitle").text(messages["product.disuseReg"]);
                $("#lblPRProductDateTitle").text(messages["product.disuseReg"] + messages["product.date"]);
                $("#lblPRProductDate").text(getFormatDate(data.productDate, '-'));
                $("#lblPRTitleTitle").text(messages["product.disuseReg"] + messages["product.title"]);
                $("#txtPRTitle").val(getFormatDate(data.productDate, '-')  + " " + getCurTime(':') + " " + messages["product.disuseReg"]);
            }

            // 수정화면인 경우
            if(data.seqNo !== undefined && data.seqNo !== null && data.seqNo !== ""){
                $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
            }
        }

        // 생산등록 상품리스트 조회(페이징 클릭 시)
        $scope.searchProductRegistList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 생산등록 상품리스트 조회
    $scope.searchProductRegistList = function(){

        // 파라미터
        var params = {};
        params.productDate = $("#hdProductDate").val();
        params.productFg = $("#hdProductFg").val();

        if($("#hdSeqNo").val() !== ""){
            params.seqNo = $("#hdSeqNo").val();
        }

        params.prodCd = $("#srchPRProdCd").val();
        params.prodNm = $("#srchPRProdNm").val();
        params.barcdCd = $("#srchPRBarcdCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.listScale = $scope.listScale;
        params.storageCd = "999";	//001	->	999

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/stock/product/product/productRegist/list.sb", params, function() {

            if($("#hdSeqNo").val() !== ""){

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
                      $("#txtPRTitle").val(response.data.data.productTitle);
                      global_productTitle =	response.data.data.productTitle;

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
            }
        });
    };

    // 조회버튼으로 조회시
    $scope.fnSearch = function () {

      if ($scope.flex.collectionView.itemsEdited.length > 0 || $scope.flex.collectionView.itemsAdded.length > 0) {
        var msg = messages["product.searchMsg"]; // 저장되지 않은 자료가 있습니다. 조회하시겠습니까?
        s_alert.popConf(msg, function () {
          $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
          $scope.searchProductRegistList();
        });
      } else {
        $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
        $scope.searchProductRegistList();
      }
   };

    // 생산등록 상품 저장
    $scope.saveProductRegist = function () {

        // 제목 입력 체크
        if ($("#txtPRTitle").val() === "") {
          var msg = $("#popTitle").text() + messages["product.title"] + messages["cmm.require.text"]; // 생산등록/폐기등록 제목을 입력하세요.
          $scope._popMsg(msg);
          return false;
        }

        // 상품변경 없이 제목만 변경했을 경우
        if($("#hdSeqNo").val() !== null && $("#hdSeqNo").val() !== '') {
            if ($scope.flex.collectionView.itemsAdded.length <= 0 && $scope.flex.collectionView.itemsEdited.length <= 0 && global_productTitle != $("#txtPRTitle").val()){

                var params = {};
                params.productDate = $("#hdDtlProductDate").val();
                params.productFg = $("#hdDtlProductFg").val();
                params.seqNo = $("#hdDtlSeqNo").val();
                params.productTitle = $("#txtPRTitle").val();

                $scope._save("/stock/product/product/productRegist/updateProductHdTitle.sb", params, function () {
                    // 부모창 재조회
                    var vScope = agrid.getScope('productCtrl');
                    vScope.searchProductList();

                    // 상세페이지에서 호출한 경우
                    if ($("#hdSeqNo").val() !== "") {
                        var vDtlScope = agrid.getScope('productDtlCtrl');
                        vDtlScope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

                        // 상품 리스트 재조회
                        var params = {};
                        params.productDate = $("#hdProductDate").val();
                        params.productFg = $("#hdProductFg").val();
                        params.seqNo = $("#hdSeqNo").val();

                        vDtlScope.searchProductDtlList(params);
                    }
                });
                return false;
            }
        }

        // 생산중량, 생산판매가, 반영수량 입력 체크
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            var item = $scope.flex.collectionView.itemsAdded[i];

            if(item.productQty !== null &&  item.productQty !== ""){
                if(item.productWeight === null || item.productSaleUprc === null
                   || item.productWeight === "" || item.productSaleUprc === ""){
                    $scope._popMsg(messages["product.productInput.chk.msg"]); // 등록할 상품의 생산중량, 생산판매가, 생산반영수량을 모두 입력하세요.
                    return false;
                }
            }
        }

        // 생산중량, 생산판매가, 반영수량 입력 체크
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];

            if(item.productQty !== null &&  item.productQty !== ""){
                if(item.productWeight === null || item.productSaleUprc === null
                   || item.productWeight === "" || item.productSaleUprc === ""){
                    $scope._popMsg(messages["product.productInput.chk.msg"]); // 등록할 상품의 생산중량, 생산판매가, 생산반영수량을 모두 입력하세요.
                    return false;
                }
            }
        }

        var params = [];
        // 추가된 상품 가져오기
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {

          var item = $scope.flex.collectionView.itemsAdded[i];

          // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
          if (item.productQty === null || item.productQty === '') {
            item.status = "R";
          } else if (item.gChk === true && item.productProdStatus === 'U') {
            item.status = "D";
          } else {
            item.status = "U";
          }
          item.productDate = $("#hdProductDate").val();
          item.productFg = $("#hdProductFg").val();

          if($("#hdSeqNo").val() !== ""){
              item.seqNo = $("#hdSeqNo").val();
          }

          item.productTitle  = $("#txtPRTitle").val();
          item.storageCd = "999";	//001	->	999
          item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

          if(orgnFg === "HQ"){
            item.productStorageCd = "001";
          }else{
            item.productStorageCd = "000";
          }

          if(item.status !== "R") params.push(item);
        }

        // 수정된 상품 가져오기
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

          var item = $scope.flex.collectionView.itemsEdited[i];

          // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
          if (item.productQty === null || item.productQty === '') {
            item.status = "R";
          } else if (item.gChk === true && item.productProdStatus === 'U') {
            item.status = "D";
          } else {
            item.status = "U";
          }
          item.productDate = $("#hdProductDate").val();
          item.productFg = $("#hdProductFg").val();

          if($("#hdSeqNo").val() !== ""){
              item.seqNo = $("#hdSeqNo").val();
          }

          item.productTitle  = $("#txtPRTitle").val();
          item.storageCd = "999";	//001	->	999
          item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

          if(orgnFg === "HQ"){
            item.productStorageCd = "001";
          }else{
            item.productStorageCd = "000";
          }

          if(item.status !== "R") params.push(item);
        }

        if(params.length > 0 || ($("#hdSeqNo").val() !== null && $("#hdSeqNo").val() !== '')) {

            $scope._postJSONSave.withPopUp("/stock/product/product/productRegist/save.sb", params, function (result) {
                if (result.data.status === "OK") {

                    // 현재 페이징 NO 갖고있기(페이징 유지를 위해)
                    var getCurr = $scope._getPagingInfo('curr');

                    // 부모창 재조회
                    var vScope = agrid.getScope('productCtrl');
                    vScope.searchProductList();

                    // 상세페이지에서 호출한 경우
                    if ($("#hdSeqNo").val() !== "") {
                        var vDtlScope = agrid.getScope('productDtlCtrl');
                        vDtlScope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

                        // 상품 리스트 재조회
                        var params = {};
                        params.productDate = $("#hdProductDate").val();
                        params.productFg = $("#hdProductFg").val();
                        params.seqNo = $("#hdSeqNo").val();

                        vDtlScope.searchProductDtlList(params);
                    }

                    // 등록화면 재조회
                    if($("#hdSeqNo").val() === null || $("#hdSeqNo").val() === "") {
                        $("#hdSeqNo").val(result.data.data);
                    }

                    var vRegScope = agrid.getScope('productRegistCtrl');
                    vRegScope._setPagingInfo('curr', getCurr); // 페이징 유지하기

                    $scope.searchProductRegistList();
                }
            });
        }else{
          $scope._popMsg(messages["cmm.not.modify"]);
          return false;
        }
    };

    // 상품분류정보 팝업
    $scope.popUpPRProdClass = function () {
      var popUp = $scope.prodClassPopUpLayer;
      popUp.show(true, function (s) {
        // 선택 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          var scope          = agrid.getScope('prodClassPopUpCtrl');
          var prodClassCd    = scope.getSelectedClass();
          var params         = {};
          params.prodClassCd = prodClassCd;
          // 조회 수행 : 조회URL, 파라미터, 콜백함수
          $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function (response) {
              $scope.prodClassCd   = prodClassCd;
              $scope.prodClassCdNm = response.data.data;
            }
          );
        }
      });
    };

    // 리더기자료 업로드 관련 함수
    $scope.readerFormUpload = function (prcsFg) {
        if (nvl($("#txtPRTitle").val(),'') === '' && prcsFg !== 'formDownload') {
          var msg =  messages["product.product"]  + " " + messages["product.title"] + messages["cmm.require.text"]; // 생산등록 제목을 입력하세요.
          $scope._popMsg(msg);
          return false;
        }

        // 양식다운로드
        if (prcsFg === 'formDownload') {

            var element = document.createElement('a');
            var text = '';

            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
            element.setAttribute('download', messages["product.product"] + "_" + getCurDateTime() + ".txt");
            element.style.display = 'none';     //하이퍼링크 요소가 보이지 않도록 처리
            document.body.appendChild(element); //DOM body요소에 하이퍼링크 부착
            element.click();                    //클릭 이벤트 트리거 - 이 시점에 다운로드 발생
            document.body.removeChild(element); //하이퍼링크 제거

        } else {

          var msg = messages["product.confm.upload.msg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

          s_alert.popConf(msg, function () {
              $("#textUpFile").val('');
              $("#textUpFile").trigger('click');
          });
        }
    };

    // 텍스트파일이 변경된 경우
    $scope.textFileChanged = function () {
        if ($('#textUpFile')[0].files[0]) {
            // 업로드 전 현재 세션ID 와 동일한 자료를 삭제한다.
            $scope.deleteExl();
        }
    };

    // 현재 세션ID 와 동일한 데이터 삭제
    $scope.deleteExl = function () {
        var params = {};
        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $http({
            method : 'POST', //방식
            url    : "/stock/product/product/productRegist/deleteUploadProduct.sb", /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $scope._popMsg(response.data.message);
            return false;
        }).then(function () {
            // 'complete' code here
            $scope.textUpload(); // 삭제 완료 된 후 텍스트업로드 호출
        });
    };

    // 텍스트 업로드
    $scope.textUpload = function () {

        $scope.excelTextFg = 'text';

        // 업로드 progress 관련 기본값 세팅
        $scope.stepCnt     = 5000; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0;   // 처리된 숫자

        // 선택한 파일이 있을 경우
        if ($('#textUpFile')[0].files[0]) {
            var file          = $('#textUpFile')[0].files[0];
            var fileName      = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자 체크. 확장자가 txt가 아닌 경우 오류메시지
            if (fileExtension.toLowerCase() !== '.txt') {
                $("#textUpFile").val('');
                $scope._popMsg(messages['product.not.textFile']); // 텍스트 파일만 업로드 됩니다.(*.txt)
                return false;
            }

            var fr = new FileReader();

            fr.onloadend = function (e) {
                // var data = e.target.result;
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                $timeout(function () {
                    var uploadData = '';
                    var bytes      = new Uint8Array(e.target.result);
                    var length     = bytes.byteLength;
                    for (var i = 0; i < length; i++) {
                        // 값이 스페이스인 경우 건너뜀.
                        if (bytes[i] === 32) {
                            continue;
                        }

                        // 마지막 데이터인 경우
                        if (i === (length - 1)) {
                            // 마지막 데이터가 엔터가 아닌 경우 json 형식 데이터 만들때 split 해서 쓰기 위해 enter 값을 마지막에 추가해준다.
                            if (bytes[i] !== 13) {
                                uploadData += String.fromCharCode(bytes[i]);
                                uploadData += String.fromCharCode(13);
                                // uploadData += '\n';
                            }
                        } else {
                            uploadData += String.fromCharCode(bytes[i]);
                        }
                    }
                    /*
                    console.log('### uploadData: \n' + uploadData);
                    console.log('### uploadData: \n' + JSON.stringify($scope.textUploadToJsonConvert(uploadData)) );
                    return;
                    */
                    // 읽어온 파일데이터가 null 이 아닌 경우
                    if (nvl(uploadData, '') !== '') {
                        var jsonData = $scope.textUploadToJsonConvert(uploadData);

                        if (jsonData.length > 0) {
                            $timeout(function () {
                                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                                console.log(jsonData);
                                $scope.save(jsonData);
                                rSeqNo = ""; // 초기화
                            }, 10);
                        } else {
                            $scope._popMsg(messages['product.noData'], function () {
                                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                            }); // 업로드 할 데이터가 없습니다.
                        }
                    } else {
                        $scope._popMsg(messages['product.noData'], function () {
                            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                        }); // 업로드 할 데이터가 없습니다.
                    }
                }, 10);
            };

            // r.readAsBinaryString(file);
            fr.readAsArrayBuffer(file);
        }
    };

    // 텍스트 업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.textUploadToJsonConvert = function (uploadData) {

        //var uploadDataArr       = uploadData.split(String.fromCharCode(13));
        var uploadDataArr       = uploadData.split('\n');
        var uploadDataArrLength = uploadDataArr.length;
        var jsonData            = [];
        var item                = {};
        var columnNum           = 4; // 텍스트 업로드시 1줄의 JSON 데이터 컬럼 수 설정

        for (var i = 0; i < uploadDataArrLength; i++) {
            // String.fromCharCode(13) 으로 replace 를 하면 제대로 되지 않음..그래서 \n으로 replace 함..
            if (nvl(uploadDataArr[i].replace(/\n/gi, ''), '') !== '') {
                // if (nvl(uploadDataArr[i].replace(/String.fromCharCode(13)/gi, ''), '') !== '') {
                // var data          = uploadDataArr[i].replace(/String.fromCharCode(13)/gi, '');
                var data          = uploadDataArr[i].replace(/\n/gi, '').trim();

                if(data !== null && data !== undefined && data !== "") {
                    //if()로 총 자릿수 체크 추가해야함.
                    var dataArr = [];
                    dataArr[0] = data;
                    dataArr[1] = data.substring(2, 6);
                    dataArr[2] = data.substring(6, 11);
                    dataArr[3] = data.substring(11, 17);
                    //var dataArr       = data.split(',');
                    var dataArrLength = dataArr.length;
                    item = {};

                    // 1줄의 데이터를 , 로 split 한 자료의 길이가 columnNum 보다 작은 경우 수량 없음 오류 메시지
                    if (dataArrLength < columnNum) {
                        $scope._popMsg(messages["product.not.qty"]); // 업로드 데이터 중 없는 데이터가 존재합니다.
                        jsonData = [];
                        break;
                    }

                    for (var j = 0; j < dataArrLength; j++) {
                        var value = nvl(dataArr[j], '').trim();
                        if (value !== '') {
                            //1줄의 데이터가 columnNum 보다 많은 경우 양식이 이상한 것이므로 for문 종료
                            if (j >= columnNum) break;
                            /*
                            if (j % columnNum === 0) {
                              item.prodBarcdCd = value;
                            } else if (j % columnNum === 1) {
                              item.qty = value;
                            }
                            */
                            if (j % columnNum === 0) item.prodWtUprc = value;
                            else if (j % columnNum === 1) item.barcdCd = value;
                            else if (j % columnNum === 2) item.productWeight = value;
                            else if (j % columnNum === 3) item.productSaleUprc = value;
                        }
                    }
                    console.log(item);
                    jsonData.push(item);
                }
            }
        }
        return jsonData;
    };

    // 텍스트 업로드 데이터 DB에 저장
    $scope.save = function (jsonData) {
        $scope.totalRows = jsonData.length;
        var params       = [];
        var msg          = '';

        console.log("totalRows : " + $scope.totalRows);

        // 업로드 5000개 제한
        if($scope.totalRows > $scope.stepCnt){
            msg = messages["product.chk.upload.count"] + messages["product.chk.dataForm"]; // 업로드 데이터 갯수는 5000개까지만 입력 가능합니다. 데이터 및 양식을 확인해주세요.
            $scope.valueCheckErrPopup(msg);
            return false;
        }

        // 저장 시작이면 업로드 중 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.textUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            var item = jsonData[i];

            // 업로드 데이터 유무 체크
            if (nvl(item.prodWtUprc, '') === '') {
                msg = messages["product.not.qty"] + messages["product.chk.dataForm"]; // 업로드 데이터 중 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 업로드 데이터 숫자가 아닌 값이 있는지 체크
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test(nvl(item.prodWtUprc, 0))) {
                msg = messages["product.chk.number"] + messages["product.chk.dataForm"]; // 업로드 데이터 중 숫자가 아닌 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 업로드 데이터 글자수 체크
            if (nvl(item.prodWtUprc, '').length  !==  18) {
                msg = messages["product.chk.digit"] + messages["product.chk.dataForm"]; // 업로드 데이터 중 18자리가 아닌 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            item.status = "U";
            item.productDate = $("#hdProductDate").val();
            item.productFg = $("#hdProductFg").val();
            if($("#hdSeqNo").val() !== ""){
                item.seqNo = $("#hdSeqNo").val();
            }
            item.productTitle = $("#txtPRTitle").val();
            item.storageCd = "999";	//001	->	999
            item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

            if(orgnFg === "HQ"){
              item.productStorageCd = "001";
            }else{
              item.productStorageCd = "000";
            }

            item.addQtyFg = $scope.PRAddQtyFgCombo.selectedValue;

            params.push(item);
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/stock/product/product/productRegist/saveUploadProduct.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (response.data.status === "OK") {
                    if(rSeqNo === "" && response.data.data !== null){
                        rSeqNo = response.data.data;
                    }
                }
            }
        }, function errorCallback(response) {
            $scope.textUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.save(jsonData);

            } else {

                // 업로드 에러내역 팝업 호출
                $scope.uploadErrInfo();

                // 현재 페이징 NO 갖고있기(페이징 유지를 위해)
                var getCurr = $scope._getPagingInfo('curr');

                // 부모창 재조회
                var vScope = agrid.getScope('productCtrl');
                vScope.searchProductList();

                // 상세페이지에서 호출한 경우
                if ($("#hdSeqNo").val() !== "") {
                     var vDtlScope = agrid.getScope('productDtlCtrl');
                      vDtlScope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

                      // 상품 리스트 재조회
                      var params = {};
                      params.productDate = $("#hdProductDate").val();
                      params.productFg = $("#hdProductFg").val();
                      params.seqNo = $("#hdSeqNo").val();

                    vDtlScope.searchProductDtlList(params);
                }

                // 등록화면 재조회
                if($("#hdSeqNo").val() === null || $("#hdSeqNo").val() === "") {
                    $("#hdSeqNo").val(rSeqNo);
                }

                var vRegScope = agrid.getScope('productRegistCtrl');
                vRegScope._setPagingInfo('curr', getCurr); // 페이징 유지하기

                $scope.searchProductRegistList();
            }
        });
    };

    // 업로드 에러내역 팝업 호출
    $scope.uploadErrInfo = function(){
        var params = {};
        $scope._broadcast('productErrInfoCtrl', params);
        $scope.wjProductErrInfoLayer.show(true);
    };

   // 엑셀업로딩 팝업 열기
   $scope.textUploadingPopup = function (showFg) {
       if (showFg) {
           // 팝업내용 동적 생성
           var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['product.textUploading'] + '</p>';
           innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span> / <span class="bk" id="totalRows">0</span></div>';
           innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
           // html 적용
           $scope._loadingPopup.content.innerHTML = innerHtml;
           // 팝업 show
           $scope._loadingPopup.show(true);
       } else {
           $scope._loadingPopup.hide(true);
       }
   };

   // 업로드 한 데이터 값체크 중 에러시 에러팝업 띄우기 및 엑셀업로딩 팝업 닫기
   $scope.valueCheckErrPopup = function (msg) {
        $scope._popMsg(msg, function () {
            $scope.textUploadingPopup(false); // 업로딩 팝업 닫기
        });
    };

   // 엑셀다운로드
   $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
          $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
            includeColumnHeaders: true,
            includeCellStyles   : false,
            includeColumns      : function (column) {
              return column.visible;
            }
          }, messages["product.product"] + '_다운로드_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
        }, 10);
    };

   // 상품코드/바코드 input 박스에서 keyDown시
   $scope.searchProdKeyEvt = function (event) {
     if (event.keyCode === 13) { // 이벤트가 enter 이면
        $scope.prodFindPop();
     }
   };

   // 상품찾기 클릭시
   $scope.prodFindPop = function () {
       $scope.getProdInfo();
   };

   // 상품찾아 그리드에 Add
   $scope.getProdInfo = function(){

      var searchFg = true;

      // 조회된 상품중에 해당 상품코드/바코드가 있는지 검색
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
          searchFg = false; // 그리드에 이미 해당 상품이 있는 경우 서버 조회 하지않도록 변수값 false 로 변경
        }
      }

      if (searchFg) {
        // 파라미터
        var params         = {};
        params.productDate = $("#hdProductDate").val();
        params.productFg = $("#hdProductFg").val();
        params.seqNo = $("#hdSeqNo").val();
        params.prodBarcdCd = $("#prodBarcdCd").val();
        params.listScale = 1; // 상품 하나만 조회해야 하므로 listScale 1로 줌.
        params.curr = 1;
        params.storageCd = "999";	//001	->	999

        var url = "/stock/product/product/productRegist/getProdInfo.sb";
        $scope._postJSONQuery.withOutPopUp(url, params, function (response) {
          if ($.isEmptyObject(response.data.data)) {
            $scope._popMsg(messages["cmm.empty.data"]);
          } else {
            $scope.addRow(response.data.data);
            if ($("#autoAddChk").prop("checked")) {
              $scope.modifyProductQty(1);
            } else {
              $scope.addQty = 1;
              $("#addQty").select();
            }
          }
        });
      } else {
        if ($("#autoAddChk").prop("checked")) {
          $scope.modifyProductQty(1);
        } else {
          $scope.addQty = 1;
          $("#addQty").select();
        }
      }
   };

   // 추가수량 input 박스에서 keyDown시
   $scope.addQtyKeyEvt = function (event) {
     if (event.keyCode === 13) {
       $scope.fnAddQty();
     }
   };

   // 추가버튼 클릭시
   $scope.fnAddQty = function () {
     var qty = $scope.addQty;
     $scope.modifyProductQty(qty);
   };

   // 그리드의 상품을 찾아서 조정수 수정
   $scope.modifyProductQty = function (addQty) {

     // 숫자가 아닌 값
     var numChkexp = /[^0-9]/g;
     if (numChkexp.test(nvl(addQty, 0))) {
         return false;
     }

     for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
       var item = $scope.flex.collectionView.items[i];
       if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
         $scope.flex.collectionView.editItem(item);
         item.productQty = parseInt(nvl(item.productQty, 0)) + parseInt(nvl(addQty, 0));
         $scope.calcAmt(item);
         $scope.flex.collectionView.commitEdit();
       }
     }

     // 자동추가가 체크되어 있는 경우 focus 를 계속 상품코드/바코드 입력하는곳에 둔다.
     if ($("#autoAddChk").prop("checked")) {
       $scope.addQty = '';
       $("#prodBarcdCd").select();
     }
   };

   // grid 의 row 추가
   $scope.addRow = function (params) {
    var flex = $scope.flex;
    if (!flex.collectionView) {
        flex.itemsSource = new wijmo.collections.CollectionView();
    }
    flex.collectionView.trackChanges = true;
    var newRow = flex.collectionView.addNew();
    newRow.status = 'U';
    for (var prop in params) {
        newRow[prop] = params[prop];
    }
    flex.collectionView.commitNew();
  };

   // 상품찾기 버튼
   $scope.prodFind = function(){
    if($("#prodFind").css("display") === "none"){
     $("#prodFind").css("display", "");
    } else {
     $("#prodFind").css("display", "none");
    }
   };

   // 팝업 닫기
   $scope.close = function () {

      $("#hdProductFg").val("");
      $("#hdProductDate").val("");
      $("#hdSeqNo").val("");

      $("#srchPRProdCd").val("");
      $("#srchPRProdNm").val("");
      $("#srchPRBarcdCd").val("");
      $("#srchPRProdClassCd").val("");
      $scope.prodClassCd = "";

      $("#popTitle").text("");
      $("#lblPRProductDateTitle").text("");
      $("#lblPRProductDate").text("");
      $("#lblPRTitleTitle").text("");
      $("#txtPRTitle").val("");

      $scope.PRAddQtyFgCombo.selectedIndex = 0;
      $("#prodBarcdCd").val("");
      $("input:checkbox[id='autoAddChk']").prop("checked", false);
      $("#addQty").val("");

       $("#prodFind").css("display", "none");

      $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

   };

}]);