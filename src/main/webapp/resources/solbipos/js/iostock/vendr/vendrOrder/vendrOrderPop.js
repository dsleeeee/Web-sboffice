/** 발주 팝업 controller */
app.controller('vendrOrderPopCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrOrderPopCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrOrderPopCtrl", function (event, data) {
    $scope.slipNo = data.slipNo;
    $scope.slipFg = data.slipFg;
    $scope.vendrCd = (nvl(data.vendrCd, '') === '' ? '' : data.vendrCd);

    // 신규등록인 경우 발주정보 탭만 활성화
    if($scope.slipNo === '') {
      $scope.dtlShowFg = true;
      $scope.prodShowFg = false;
      $scope.reportShowFg = false;
    }
    // 신규등록이 아닌 경우 모든 탭 활성화
    else {
      $scope.dtlShowFg = true;
      $scope.prodShowFg = true;
      $scope.reportShowFg = true;
    }

    $scope.wjVendrOrderPopLayer.show(true);

    // 발주정보 탭 show
    $scope.dtlShow();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 발주정보 탭 보이기
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
    $scope._broadcast('vendrOrderDtlCtrl', params);
  };


  // 발주상품 탭 보이기
  $scope.prodShow    = function () {
    if($scope.slipNo === null) {
      $scope._popMsg(messages["vendrOrder.pop.not.slip"]);
      return false;
    }

    $("#dtlTab").removeClass("on");
    $("#prodTab").addClass("on");
    $("#reportTab").removeClass("on");

    $("#dtlView").hide();
    $("#prodView").show();
    $("#reportView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("vendrOrderProdCtrl");
    scope.flex.refresh();

    var params = {};
    params.slipNo = $scope.slipNo;
    params.slipFg = $scope.slipFg;
    params.vendrCd = $scope.vendrCd;
    $scope._broadcast('vendrOrderProdCtrl', params);
  };


  // 발주서 탭 보이기
  $scope.reportShow = function () {
    if($scope.slipNo === null) {
      $scope._popMsg(messages["vendrOrder.pop.not.slip"]);
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
    $scope._broadcast('vendrOrderReportCtrl', params);
  };

}]);
