/**
 * get application
 */
var app = agrid.getApp();

/** 일별종합 controller */
app.controller('dayCloseDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayCloseDtlCtrl', $scope, $http, true));

  var closeFg = "";

  $scope.openDate = wcombo.genDateVal("#openDate", gvStartDate);
  $scope.closeDate = wcombo.genDateVal("#closeDate", gvStartDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dayCloseDtlCtrl", function (event, data) {
    $scope.dayCloseDtl(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 팝업오픈
  $scope.dayCloseDtl = function (data) {

    storeCd = data.storeCd;

    $("#dayCloseDtlTitle").text("[" + data.storeCd + "]" + data.storeNm + '    ' + data.closeDate + '    ');

    if(data.closeFg === "0"){
      $("#dayCloseDtlTitle").text($("#dayCloseDtlTitle").text() + "미마감");
    } else if(data.closeFg === "1"){
      $("#dayCloseDtlTitle").text($("#dayCloseDtlTitle").text() + "마감");
    }

    if(isNull(data.closeFg)){
      closeFg = "";
      $scope.openDate.value = getFormatDate(data.closeDate.replaceAll("-", ""));
      $scope.closeDate.value = getFormatDate(data.closeDate.replaceAll("-", ""));
      $("#interestAmt").val(0);
      $("#inAmt").val(0);
      $("#outAmt").val(0);
      $("#groupAmt").val(0);
      $("#hockeyAmt").val(0);
      $("#etcAmt").val(0);
      $("#inDayAmt").val(0);
      $("#inSum").val(0);
      $("#inMonthSum").val(0);
      $("#inBMonthSum").val(0);
      $("#inTotalSum").val(0);
      $("#outSum").val(0);
      $("#outMonthSum").val(0);
      $("#outBMonthSum").val(0);
      $("#outTotalSum").val(0);
      $("#remark1").val("");
      $("#remark2").val("");
      $("#remark3").val("");
      $("#remark4").val("");
      $("#remark5").val("");
      $("#remark6").val("");
    } else {
      closeFg = data.closeFg;
      $scope.openDate.value = getFormatDate(data.openDate);
      $scope.closeDate.value = getFormatDate(data.closeDate.replaceAll("-", ""));
      $("#interestAmt").val(data.interestAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#inAmt").val(data.inAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#outAmt").val(data.outAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#groupAmt").val(data.groupAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#hockeyAmt").val(data.hockeyAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#etcAmt").val(data.etcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#inDayAmt").val(data.inDayAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#inSum").val(data.inSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#inMonthSum").val(data.inMonthSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#inBMonthSum").val(data.inBMonthSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#inTotalSum").val(data.inTotalSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#outSum").val(data.outSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#outMonthSum").val(data.outMonthSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#outBMonthSum").val(data.outBMonthSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#outTotalSum").val(data.outTotalSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
      $("#remark1").val(data.remark1);
      $("#remark2").val(data.remark2);
      $("#remark3").val(data.remark3);
      $("#remark4").val(data.remark4);
      $("#remark5").val(data.remark5);
      $("#remark6").val(data.remark6);
    }
  };

  // 마감데이터 수신
  $scope.getDayCloseDtl = function () {
    if(closeFg === "1"){
      $scope._popMsg("마감취소 후 마감데이터수신 진행하여 주십시오.");
      return false;
    }

    var params        = {};
    params.storeCd    = storeCd;
    params.closeDate  = wijmo.Globalize.format($scope.closeDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._postJSONQuery.withOutPopUp("/excclc/excclc/dayClose/dayClose/getDayCloseDtl.sb", params, function (response){

      if($.isEmptyObject(response.data.data.list)) {
        $scope._popMsg(messages["cmm.empty.data"]);
        return false;
      }

      var closeInfo = response.data.data.list;
      $scope.groupAmt = closeInfo.groupAmt;
      $scope.hockeyAmt = closeInfo.hockeyAmt;
      $scope.etcAmt = closeInfo.etcAmt;
      $scope.remark6 = closeInfo.remark6;

      $scope._popMsg("마감데이터 수신이 완료되었습니다.");
    });
  };

  $scope.close = function (data){
    if(closeFg === "1" && data === "1"){
      $scope._popMsg("이미 마감된 내역입니다.");
      return false;
    }

    // 파라미터
    var params          = {};
    params.storeCd      = storeCd;
    params.openDate     = wijmo.Globalize.format($scope.openDate.value, 'yyyyMMdd');
    params.closeDate    = wijmo.Globalize.format($scope.closeDate.value, 'yyyyMMdd');
    params.closeFg      = data;
    params.interestAmt  = $("#interestAmt").val().replaceAll(",", "");
    params.inAmt        = $("#inAmt").val().replaceAll(",", "");
    params.outAmt       = $("#outAmt").val().replaceAll(",", "");
    params.groupAmt     = $("#groupAmt").val().replaceAll(",", "");
    params.hockeyAmt    = $("#hockeyAmt").val().replaceAll(",", "");
    params.etcAmt       = $("#etcAmt").val().replaceAll(",", "");
    params.inDayAmt     = $("#inDayAmt").val().replaceAll(",", "");
    params.inSum        = $("#inSum").val().replaceAll(",", "");
    params.inMonthSum   = $("#inMonthSum").val().replaceAll(",", "");
    params.inBMonthSum  = $("#inBMonthSum").val().replaceAll(",", "");
    params.inTotalSum   = $("#inTotalSum").val().replaceAll(",", "");
    params.outSum       = $("#outSum").val().replaceAll(",", "");
    params.outMonthSum  = $("#outMonthSum").val().replaceAll(",", "");
    params.outBMonthSum = $("#outBMonthSum").val().replaceAll(",", "");
    params.outTotalSum  = $("#outTotalSum").val().replaceAll(",", "");
    params.remark1      = $("#remark1").val();
    params.remark2      = $("#remark2").val();
    params.remark3      = $("#remark3").val();
    params.remark4      = $("#remark4").val();
    params.remark5      = $("#remark5").val();
    params.remark6      = $("#remark6").val();

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._postJSONSave.withPopUp("/excclc/excclc/dayClose/dayClose/saveClose.sb", params, function(){
      $scope._broadcast('dayCloseCtrl');
      $scope.dayCloseDtlLayer.hide();
    });
  };

}]);
