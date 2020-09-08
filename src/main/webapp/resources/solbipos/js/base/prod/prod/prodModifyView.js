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

  var vProdNoEnvFg = prodNoEnvFg;

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
    // 등록/수정 모드 파악
    $scope.chkSaveMode(data);
    // 상품정보 set
    $scope.setProdModifyInfo(data);

    // 첨부파일, 상품 이미지 초기화
    $scope.clearProdImage();

    // 수정 모드 시
    if(data.prodCd !== null && data.prodCd !== undefined && data.prodCd !== ""){
        // 상품 이미지
        if(data.imgUrl === null){
            $("#goodsNo").css('display', 'block');
            $("#goodsYes").css('display', 'none');

        } else {
            $("#goodsNo").css('display', 'none');
            $("#goodsYes").css('display', 'block');

            try {
                $("#imgProdImage").attr("src", data.imgUrl);
            } catch (e) {
                alert(e);
            }
        }

    // 신규 모드 시
    } else {
        var params = {};
        params.saleProdYn = $scope.prodModifyInfo.saleProdYn = "Y"; // 판매상품여부
        // params.saleUprc = $("#prodModifySaleUprc").val(); // 판매단가
        params.splyUprc = $("#prodModifySplyUprc").val(); // 공급단가
        params.costUprc = $("#prodModifyCostUprc").val(); // 원가단가
        params.lastCostUprc = $("#prodModifyLastCostUprc").val(); // 최종원가단가
        // params.poUnitQty = $("#prodModifyPoUnitQty").val(); // 발주단위수량
        // params.poMinQty = $("#prodModifyPoMinQty").val(); // 최소발주수량
        params.defaultStock = $("#prodModifyDefaultStock").val(); // 초기재고
        params.safeStockQty = $("#prodModifySafeStockQty").val(); // 안전재고
        // 상품정보 set (초기값 셋팅)
        $scope.setProdModifyInfo(params);

        // 상품 이미지
        $("#goodsNo").css('display', 'block');
        $("#goodsYes").css('display', 'none');
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
      params.prodCd = $scope.prodModifyInfo.prodCd

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
            // params.defaultStock = $("#prodModifyDefaultStock").val(); // 초기재고 -> 현재 저장,조회 로직 빠져있음
            params.safeStockQty = $("#prodModifySafeStockQty").val(); // 안전재고

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

        // 분류조회
        if ($scope.prodModifyInfo.prodClassCd === undefined) {
            $scope._popMsg(messages["prod.prodClassCdNmChk.msg"]);
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
        if (isNull($("#prodModifyDefaultStock").val())) {
          $scope._popMsg(messages["prod.defaultStockChk.msg"]);
          $("#prodModifyDefaultStock").focus();
          return false;
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

          $("#prodCd").attr("readonly",true);
          $("#prodCd").css("width", "100%");
          $("#prodCdChkFg").val("");
          if(prodNoEnvFg === "MANUAL"){ $("#prodCdInputType").val("1"); }else{ $("#prodCdInputType").val("0"); }
          $("#btnChkProdCd").css("display", "none");
          $("#saveMode").val("MOD");

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

          $scope.prodModifyInfo.saleProdYn = "Y"; // 판매상품여부
          // $("#prodModifySaleUprc").val("0"); // 판매단가
          $("#prodModifySplyUprc").val("0"); // 공급단가
          $("#prodModifyCostUprc").val("0"); // 원가단가
          $("#prodModifyLastCostUprc").val("0"); // 최종원가단가
          // $("#prodModifyPoUnitQty").val("1"); // 발주단위수량
          // $("#prodModifyPoMinQty").val("1"); // 최소발주수량
          $("#prodModifyDefaultStock").val("0"); // 초기재고
          $("#prodModifySafeStockQty").val("0"); // 안전재고

          $("#saveMode").val("REG");
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

}]);