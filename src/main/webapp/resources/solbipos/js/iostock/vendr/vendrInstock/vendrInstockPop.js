/** 입고/반출 팝업 controller */
app.controller('vendrInstockPopCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrInstockPopCtrl', $scope, $http, true));
  
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrInstockPopCtrl", function (event, data) {
    $scope.slipNo = data.slipNo;
    $scope.slipFg = data.slipFg;
    $scope.vendrCd = (nvl(data.vendrCd, '') === '' ? '' : data.vendrCd);

    // 신규등록인 경우 입고/반출정보 탭만 활성화
    if($scope.slipNo === '') {
      $scope.dtlShowFg = true;
      $scope.prodShowFg = false;
      $scope.reportShowFg = false;
    }
    // 신규등록이 아닌 경우 모든 탭 활성화
    else {
      $scope.dtlShowFg = true;
      $scope.prodShowFg = true;
      if($scope.slipFg === -1) {
        $scope.reportShowFg = true;
      }
    }

    $scope.wjVendrInstockPopLayer.show(true);

    // 입고
    if($scope.slipFg === 1) {
      $("#popTitle").html(messages["vendrInstock.pop.inTitle"]); // 현재 팝업이 같은 scope 가 아니라서 jquery 형태로 text 부여
      $scope.dtlTab = messages["vendrInstock.pop.vendrInstockDtl"];
      $scope.prodTab = messages["vendrInstock.pop.vendrInstockProd"];
    }
    //반출
    else if($scope.slipFg === -1) {
      $("#popTitle").html(messages["vendrInstock.pop.rtnTitle"]); // 현재 팝업이 같은 scope 가 아니라서 jquery 형태로 text 부여
      $scope.dtlTab = messages["vendrInstock.pop.vendrRtnDtl"];
      $scope.prodTab = messages["vendrInstock.pop.vendrRtnProd"];
      $scope.reportTab = messages["vendrInstock.pop.vendrRtnReport"];
    }

    // 입고/반출정보 탭 show
    $scope.dtlShow();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 입고/반출정보 탭 보이기
  $scope.dtlShow        = function () {
    $("#dtlTab").addClass("on");
    $("#prodTab").removeClass("on");
    $("#reportTab").removeClass("on");

    $("#dtlView").show();
    $("#prodView").hide();
    $("#reportView").hide();

    var params = {};
    params.slipNo = $scope.slipNo;
    params.slipFg = $scope.slipFg;
    $scope._broadcast('vendrInstockDtlCtrl', params);
  };


  // 입고/반출상품 탭 보이기
  $scope.prodShow    = function () {
    if($scope.slipNo === null) {
      $scope._popMsg(messages["vendrInstock.pop.not.slip"]);
      return false;
    }

    $("#dtlTab").removeClass("on");
    $("#prodTab").addClass("on");
    $("#reportTab").removeClass("on");

    $("#dtlView").hide();
    $("#prodView").show();
    $("#reportView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("vendrInstockProdCtrl");
    scope.flex.refresh();

    var params = {};
    params.slipNo = $scope.slipNo;
    params.slipFg = $scope.slipFg;
    params.vendrCd = $scope.vendrCd;
    $scope._broadcast('vendrInstockProdCtrl', params);
  };


  // 반출서 탭 보이기
  $scope.reportShow = function () {
    if($scope.slipNo === null) {
      $scope._popMsg(messages["vendrInstock.pop.not.slip"]);
      return false;
    }

    $("#dtlTab").removeClass("on");
    $("#prodTab").removeClass("on");
    $("#reportTab").addClass("on");

    $("#dtlView").hide();
    $("#prodView").hide();
    $("#reportView").show();

    var params = {};
    params.slipNo = $scope.slipNo;
    params.slipFg = $scope.slipFg;
    params.vendrCd = $scope.vendrCd;
    $scope._broadcast('vendrInstockReportCtrl', params);
  };

   
}]);
