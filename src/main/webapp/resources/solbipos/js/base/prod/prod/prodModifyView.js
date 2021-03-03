/****************************************************************
 *
 * 파일명 : prodModifyView.js
 * 설  명 : 상품정보관리 수정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
 *
 * **************************************************************/
/**
 * 팝업 그리드 생성
 */
app.controller('prodModifyCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodModifyCtrl', $scope, $http, false));

  // var vProdNoEnvFg = prodNoEnvFg;

  // 상품 이미지 삭제여부 (DEL:삭제)
  var prodImageDelFg;

  // 상품정보
  $scope.prodModifyInfo = {};
  $scope.setProdModifyInfo = function(data){
    $scope.prodModifyInfo = data;
  };
  $scope.getProdModifyInfo = function(){
    return $scope.prodModifyInfo;
  };

  // 상품정보 조회
  $scope.$on("prodModifyCtrl", function(event, data) {
    // data 조회하지 않고 상세정보와 동일하므로 파라미터로 처리
    $scope.$broadcast('loadingPopupActive');
    // 첨부파일, 상품 이미지 초기화
    $scope.clearProdImage();
    // 등록/수정 모드 파악
    $scope.chkSaveMode(data);

    // 수정 모드 시
    if(data.prodCd !== null && data.prodCd !== undefined && data.prodCd !== ""){

    // 신규 모드 시
    } else {
        var params = {};
        // 상품기본정보
        params.prodTypeFg = "1"; // 상품유형
        params.prodCd = ""; // 상품코드
        params.prodNm = ""; // 상품명
        params.prodClassCdNm = ""; // 상품분류명
        params.prodClassCd = ""; // 상품분류코드
        params.vendrCd = ""; // 거래처코드
        params.vendrNm = ""; // 거래처명
        params.saleProdYn = "Y"; // 판매상품여부
        params.saleUprc = $("#prodModifySaleUprc").val(); // 판매단가
        params.prodTipYn = "N"; // 봉사료 포함 여부
        params.vatFg = "1"; // 과세여부
        params.useYn = "Y"; // 사용여부
        params.barCd = ""; // 바코드
        // 상품부가정보
        params.stockProdYn = "Y"; // 재고관리여부
        params.soldOutYn = "N"; // 품절여부
        params.setProdFg = "1"; // 세트상품구분
        params.sideProdYn = "N"; // sideProdYn
        params.pointSaveYn = "Y"; // 포인트적립여부
        params.sdattrClassCd = ""; // 속성
        params.sdselGrpCd = ""; // 선택매뉴
        // 상품발주정보
        params.splyUprc = $("#prodModifySplyUprc").val(); // 공급단가
        params.splyUprcUseYn = "Y"; // 공급단가사용여부
        params.costUprc = $("#prodModifyCostUprc").val(); // 원가단가
        params.lastCostUprc = $("#prodModifyLastCostUprc").val(); // 최종원가단가
        params.poProdFg = "1"; // 발주상품구분
        params.poUnitFg = "1"; // 발주단위
        params.poUnitQty = $("#prodModifyPoUnitQty").val(); // 발주단위수량
        params.poMinQty = $("#prodModifyPoMinQty").val(); // 최소발주수량
        params.startStockQty = $("#prodModifyStartStockQty").val(); // 초기재고
        params.safeStockQty = $("#prodModifySafeStockQty").val(); // 안전재고
        // 비고
        params.remark = ""; // 비고
        // 상품정보 set (초기값 셋팅)
        $scope.setProdModifyInfo(params);
    }

    // 메시지창 닫기
    setTimeout(function() {
      $scope.$broadcast('loadingPopupInactive');
    }, 30);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

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
            var prodInfo = $scope.getProdModifyInfo();
            prodInfo.prodClassCd = prodClassCd;
            prodInfo.prodClassCdNm = response.data.data;
            $scope.setProdModifyInfo(prodInfo);
          }
        );
      }
    });
  };
  
    // 상품코드 중복체크
    $scope.chkProdCd = function () {
      if(isNull($scope.prodModifyInfo.prodCd)) {
          $scope._popMsg(messages["prod.prodCd"]+messages["cmm.require.text"]);
          return false;
      }

      var params    = {};
      params.prodCd = $scope.prodModifyInfo.prodCd;

      $scope._postJSONQuery.withPopUp( "/base/prod/prod/prod/getProdCdCnt.sb", params, function(response){
          var result = response.data.data;

          if(result === 0){ // 사용가능
              $scope._popMsg(messages["prod.notProdCdDuplicate.msg"]);
              $scope.prodModifyInfo.prodCdChkFg = $scope.prodModifyInfo.prodCd;

          }else{ // 중복
              $scope._popMsg(messages["prod.prodCdDuplicate.msg"]);
              $scope.prodModifyInfo.prodCdChkFg ="";
          }
      });
    };

    // 상품저장
    $scope.saveProd = function() {
        var params = $scope.prodModifyInfo;
        params.prodNoEnv = $("#prodCdInputType").val();
        params.saveMode = $("#saveMode").val();

        // 값 체크
        if($scope.valueCheck()){

            params.saleUprc = $("#prodModifySaleUprc").val(); // 판매단가
            params.splyUprc = $("#prodModifySplyUprc").val(); // 공급단가
            params.costUprc = $("#prodModifyCostUprc").val(); // 원가단가
            params.lastCostUprc = $("#prodModifyLastCostUprc").val(); // 최종원가단가
            params.poUnitQty = $("#prodModifyPoUnitQty").val(); // 발주단위수량
            params.poMinQty = $("#prodModifyPoMinQty").val(); // 최소발주수량
            params.startStockQty = $("#prodModifyStartStockQty").val(); // 초기재고
            params.safeStockQty = $("#prodModifySafeStockQty").val(); // 안전재고

            params.chkVendrCd = $scope.prodModifyInfo.vendrCd;

            // 저장수행
            $scope._postJSONSave.withPopUp("/base/prod/prod/prod/save.sb", params, function (response) {
                var result = response.data.data;

                if(result < 1){
                    $scope._popMsg(messages["cmm.registFail"]);

                }else{
                    $scope._popMsg(messages["cmm.saveSucc"]);

                    // 이미지파일 저장
                    $scope.prodImageFileSave(result);

                    $scope.prodModifyLayer.hide();

                    // 저장기능 수행후 재조회
                    $scope._broadcast('prodCtrl');
                }
            });
        }
    };

    // 값 체크
    $scope.valueCheck = function () {
        // 매장코드 수동입력 시
        if($("#saveMode").val() === "REG") {
            if ($("#prodCdInputType").val() === "1") { // 'MANUAL'
                //  상품코드
                if (isNull($scope.prodModifyInfo.prodCd)) {
                    $scope._popMsg(messages["prod.prodCdChk.msg"]);
                    return false;
                }
                // 상품코드 중복체크를 해주세요.
                var msg = messages["prod.prodCdDuplicateChk.msg"];
                if (isNull($scope.prodModifyInfo.prodCdChkFg)) {
                    $scope._popMsg(msg);
                    return false;
                }
                // 상품코드 중복체크를 다시 해주세요.
                var msg = messages["prod.prodCdDuplicateChkAgain.msg"];
                if ($scope.prodModifyInfo.prodCd !== $scope.prodModifyInfo.prodCdChkFg) {
                    $scope._popMsg(msg);
                    return false;
                }
            }
        }
        // 이미지명 형식 체크
        if($("#file").val() !== null && $("#file").val() !== undefined && $("#file").val() !== "") {
            var imgFullNm = $("#file").val().substring($("#file").val().lastIndexOf('\\') + 1);
            if (1 > imgFullNm.lastIndexOf('.')) {
                $scope._popMsg(messages["prod.fileNmChk.msg"]);
                return false;
            }
        }

        // 이미지(.png) 확장자 체크
        var reg = /(.*?)\.(png|PNG)$/;

        if(! $("#file").val().match(reg)) {
            $scope._popMsg(messages["prod.fileExtensionChk.msg"]);
            return;
        }

        // 분류조회
        if (isNull($scope.prodModifyInfo.prodClassCd)) {
            $scope._popMsg(messages["prod.prodClassCdNmChk.msg"]);
            return false;
        }
        //  상품명
        if (isNull($scope.prodModifyInfo.prodNm)) {
            $scope._popMsg(messages["prod.prodNmChk.msg"]);
            return false;
        }
        // 판매단가
        if (isNull($("#prodModifySaleUprc").val())) {
          $scope._popMsg(messages["prod.saleUprcChk.msg"]);
          $("#prodModifySaleUprc").focus();
          return false;
        }
        // 공급단가
        if (isNull($("#prodModifySplyUprc").val())) {
          $scope._popMsg(messages["prod.splyUprcChk.msg"]);
          $("#prodModifySplyUprc").focus();
          return false;
        }
        // 원가단가
        if (isNull($("#prodModifyCostUprc").val())) {
          $scope._popMsg(messages["prod.costUprcChk.msg"]);
          $("#prodModifyCostUprc").focus();
          return false;
        }
        // 최종원가단가
        if (isNull($("#prodModifyLastCostUprc").val())) {
          $scope._popMsg(messages["prod.lastCostUprcChk.msg"]);
          $("#prodModifyLastCostUprc").focus();
          return false;
        }
        // 발주단위수량
        if (isNull($("#prodModifyPoUnitQty").val())) {
          $scope._popMsg(messages["prod.poUnitQtyChk.msg"]);
          $("#prodModifyPoUnitQty").focus();
          return false;
        } else if ($("#prodModifyPoUnitQty").val() < 1) {
            $scope._popMsg(messages["prod.poUnitQtySizeChk.msg"]);
            $("#prodModifyPoUnitQty").focus();
            return false;
        }
        // 발주단위
        if($scope.prodModifyInfo.poUnitFg !== "" || $scope.prodModifyInfo.poUnitFg !== null) {
          if($scope.prodModifyInfo.poUnitFg === "1"){
              // 발주단위수량
              if ($("#prodModifyPoUnitQty").val() !== "1") {
                  $scope._popMsg(messages["prod.poUnitFgeChk.msg"]);
                  return false;
              }
          }
        }
        // 최소발주수량
        if (isNull($("#prodModifyPoMinQty").val())) {
          $scope._popMsg(messages["prod.poMinQtyChk.msg"]);
          $("#prodModifyPoMinQty").focus();
          return false;
        } else if ($("#prodModifyPoMinQty").val() < 1) {
            $scope._popMsg(messages["prod.poMinQtySizeChk.msg"]);
            $("#prodModifyPoMinQty").focus();
            return false;
        }
        // 초기재고
        if($("#saveMode").val() === "REG") { // 신규일때만
            if (isNull($("#prodModifyStartStockQty").val())) {
                $scope._popMsg(messages["prod.startStockQtyChk.msg"]);
                $("#prodModifyStartStockQty").focus();
                return false;
            }
        }
        // 안전재고
        if (isNull($("#prodModifySafeStockQty").val())) {
          $scope._popMsg(messages["prod.safeStockQtyChk.msg"]);
          $("#prodModifySafeStockQty").focus();
          return false;
        }
        // 상품 이미지
        if (!isNull($("#file")[0].files[0])) {
            var maxSize = 500 * 1024; // 500KB
            var fileSize = $("#file")[0].files[0].size;
            if (fileSize > maxSize) {
                $scope._popMsg(messages["prod.fileSizeChk.msg"]);
                return false;
            }
        }

        return true;
    };

    // 등록/수정 모드에 따른 VIEW 변경
    $scope.chkSaveMode = function(data){

        // 수정 모드 시
        if(data.prodCd !== null && data.prodCd !== undefined && data.prodCd !== ""){
            // 상품상세정보 조회
            var params = data;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._postJSONQuery.withPopUp("/base/prod/prod/prod/detail.sb", params, function(response){
                  // 상품정보
                  var prodModify = response.data.data.list;
                  // 상품정보 set
                  $scope.setProdModifyInfo(prodModify);

                  // 상품 이미지
                  if(prodModify.imgUrl === null){
                      $("#goodsNo").css('display', 'block');
                      $("#goodsYes").css('display', 'none');

                  } else {
                      $("#goodsNo").css('display', 'none');
                      $("#goodsYes").css('display', 'block');

                      try {
                          $("#imgProdImage").attr("src", prodModify.imgUrl);
                      } catch (e) {
                          alert(e);
                      }
                  }
              }
            );

            $("#prodCd").attr("readonly",true);
            $("#prodCd").css("width", "100%");
            $("#prodCdChkFg").val("");
            if(prodNoEnvFg === "MANUAL"){ $("#prodCdInputType").val("1"); }else{ $("#prodCdInputType").val("0"); }
            $("#btnChkProdCd").css("display", "none");
            $("#saveMode").val("MOD");

            // 초기재고
            $("#thStartStockQty").css('display', 'none');
            $("#tdStartStockQty").css('display', 'none');
            $("#thStartStockQtyNo").css('display', '');
            $("#tdStartStockQtyNo").css('display', '');

        // 신규 모드 시
        }else{
            if(prodNoEnvFg === "MANUAL"){
                $("#prodCd").removeAttr("readonly");
                $("#prodCd").css("width", "63%");
                $("#prodCdChkFg").val("");
                $("#prodCdInputType").val("1");
                $("#btnChkProdCd").css("display", "");
                document.getElementById("prodCd").placeholder = "";
            }else{
                $("#prodCd").attr("readonly",true);
                $("#prodCd").css("width", "100%");
                $("#prodCdChkFg").val("");
                $("#prodCdInputType").val("0");
                $("#btnChkProdCd").css("display", "none");
                document.getElementById("prodCd").placeholder = "상품코드는 자동생성 됩니다.";
            }

            $("#saveMode").val("REG");

            // 상품 이미지
            $("#goodsNo").css('display', 'block');
            $("#goodsYes").css('display', 'none');

            // 초기재고
            $("#thStartStockQty").css('display', '');
            $("#tdStartStockQty").css('display', '');
            $("#thStartStockQtyNo").css('display', 'none');
            $("#tdStartStockQtyNo").css('display', 'none');

            // 초기값 셋팅
            $("#prodModifySaleUprc").val(""); // 판매단가
            $("#prodModifySplyUprc").val("0"); // 공급단가
            $("#prodModifyCostUprc").val("0"); // 원가단가
            $("#prodModifyLastCostUprc").val("0"); // 최종원가단가
            $("#prodModifyPoUnitQty").val("1"); // 발주단위수량
            $("#prodModifyPoMinQty").val("1"); // 최소발주수량
            $("#prodModifyStartStockQty").val("0"); // 초기재고
            $("#prodModifySafeStockQty").val("0"); // 안전재고
        }
    };

    // 이미지 화면에 넣기
    $scope.changeProdImage = function (value) {
        if(value.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#imgProdImage").attr('src', e.target.result);
            };
            reader.readAsDataURL(value.files[0]);
        }

        // 상품 이미지
        $("#goodsNo").css('display', 'none');
        $("#goodsYes").css('display', 'block');
    };

    // 상품 이미지 파일 저장
    $scope.prodImageFileSave = function(data){
        var prodCd;
        // 수정
        if($("#saveMode").val() === "MOD"){
            prodCd = $scope.prodModifyInfo.prodCd;
        // 신규
        } else if($("#saveMode").val() === "REG"){
            prodCd = data;
        }

        var formData = new FormData($("#myForm")[0]);
        formData.append("orgnFg", orgnFg);
        formData.append("hqOfficeCd", hqOfficeCd);
        formData.append("storeCd", storeCd);
        formData.append("ImageProdCd", prodCd);
        formData.append("prodImageDelFg", prodImageDelFg);

        var url = '/base/prod/prod/prod/getProdImageFileSave.sb';

        $.ajax({
            url: url,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            // async:false,
            success: function(result) {
                // console.log('save result', result);
                if (result.status === "OK") {
                    $scope._popMsg("저장되었습니다.");
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            },
            error : function(result){
                $scope._popMsg("error");
                $scope.$broadcast('loadingPopupInactive');
            }
        },function() {
            $scope._popMsg("Ajax Fail By HTTP Request");
            $scope.$broadcast('loadingPopupInactive');
        });
    };

    // 상품 이미지 삭제
    $scope.delProdImage = function () {
        // 첨부파일, 상품 이미지 초기화
        $scope.clearProdImage();

        // 상품 이미지
        if($scope.prodModifyInfo.imgUrl != null) {
            // 상품 이미지 삭제여부 (DEL:삭제)
            prodImageDelFg = "DEL";
        }
    };

    // 첨부파일, 상품 이미지 초기화
    $scope.clearProdImage = function () {
        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            $("#file").replaceWith( $("#file").clone(true) );
        } else {
            // other browser 일때
            $("#file").val("");
        }
        // 상품 이미지 초기화
        $("#imgProdImage").attr("src", null);

        // 상품 이미지 삭제여부 (DEL:삭제)
        prodImageDelFg = null;
    };

    // 회원 포인트 조회 팝업
    $scope.popUpVendrCd = function() {
        var params = $scope.prodModifyInfo;
        $scope.setProdModifyInfo(params);

        $scope.wjSearchProdVendrLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 상품 거래처 조회 팝업 핸들러 추가
        $scope.wjSearchProdVendrLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('searchNoProdVendrTotalCtrl', $scope.getProdModifyInfo());
            }, 50)
        });
    });

}]);