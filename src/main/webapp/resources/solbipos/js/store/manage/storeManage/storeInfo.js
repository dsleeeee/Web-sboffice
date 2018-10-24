/****************************************************************
 *
 * 파일명 : storeInfo.js
 * 설  명 : 매장정보관리 > 매장상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  매장 상세
 **********************************************************************/
app.controller('storeInfoCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeInfoCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터 Set //todo
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);
  $scope._setComboData("areaCd", areaCd);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 팝업 오픈시 매장정보 조회
  $scope.$on("storeInfoCtrl", function(event, data) {
    $scope.getStoreInfo();
    event.preventDefault();
  });

  // 매장정보 조회
  $scope.getStoreInfo = function(){

    // 선택된 매장
    var storeScope = agrid.getScope('storeManageCtrl');
    storeScope.getSelectedStore();

    var params = storeScope.getSelectedStore();

    $.ajax({
      type: "POST",
      cache: false,
      async: true,
      dataType: "json",
      url: "/store/manage/storeManage/storeManage/getStoreDetail.sb",
      data: params,
      success: function(result) {
        if(result.status === "OK") {

          console.log(result);

          if(isEmptyObject(result.data) ) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
          }

          var installPosCnt = result.data.instPosCnt;
          var storeDetailInfo = result.data.storeDtlInfo;

          $("#storeInfoTitle").text("[" + storeDetailInfo.storeCd + "] " + storeDetailInfo.storeNm);

          // 조회한 데이터 셋팅
          $("#hqOfficeCd").val(storeDetailInfo.hqOfficeCd);
          $("#hqOfficeNm").val(storeDetailInfo.hqOfficeNm);
          $("#storeCd").val(storeDetailInfo.storeCd);
          $("#storeNm").val(storeDetailInfo.storeNm);
          $("#installPosCnt").val(installPosCnt);
          $("#ownerNm").val(storeDetailInfo.ownerNm);
          $("#bizStoreNm").val(storeDetailInfo.bizStoreNm);
          $("#bizNo1").val(storeDetailInfo.bizNo1);
          $("#bizNo2").val(storeDetailInfo.bizNo2);
          $("#bizNo3").val(storeDetailInfo.bizNo3);
          $("#telNo").val(storeDetailInfo.telNo);
          $("#faxNo").val(storeDetailInfo.faxNo);
          $("#emailAddr").val(storeDetailInfo.emailAddr);
          $("#hmpgAddr").val(storeDetailInfo.hmpgAddr);
          $("#postNo").val(storeDetailInfo.postNo);
          $("#addr").val(storeDetailInfo.addr);
          $("#addrDtl").val(storeDetailInfo.addrDtl);
          $("#sysRemark").val(storeDetailInfo.sysRemark);
          $("#hdRemark").val(storeDetailInfo.hdRemark);
          $("#storeRemark").val(storeDetailInfo.remark);

          // $scope.list = result.data.list;
          // if(isEmptyObject($scope.list)){
          //   $scope._popMsg(messages["cmm.empty.data"]);
          //   $scope._gridDataInit();
          //   return false;
          // }
        }
        else if(result.status === "FAIL") {
          return fail(result);
        }
        else if(result.status === "SESSION_EXFIRE") {
          s_alert.popOk(result.message, function() {
            location.href = result.url;
          });
        }
        else if(result.status === "SERVER_ERROR") {
          s_alert.pop(result.message);
        }
        else {
          var msg = result.status + " : " + resultmessage;
          alert(msg);
        }
      }
    });
  };

  // 저장
  $scope.save = function(){

  };

  // 매장 추가 팝업 오픈
  $scope.addStore = function(){

  };

  // 관리업체 조회
  $scope.searchManageVan = function(){

  };

  // 대리점 조회
  $scope.searchAgency = function(){

  };

}]);
