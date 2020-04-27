/****************************************************************
 *
 * 파일명 : info.js
 * 설  명 : 거래처 상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.04.23     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  거래처 상세
 */
app.controller('vendrInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrInfoCtrl', $scope, $http, false));

    // 거래처 번호(데이터 수정 시 사용)
    var valVendrCd;

    // 해당 scope 호출
    $scope.$on("vendrInfoCtrl", function(event, info) {

        // 거래처 번호(데이터 수정 시 사용)
        valVendrCd = info.vendrCd;

        // 제목
        $("#popInfo").text("[" + info.vendrCd + "] 거래처 상세" );

        // 입력값 초기화
        $scope.infoInit();

        // 상세 조회
        var param = info;
        
        $.postJSON("/base/prod/vendr/regist/view.sb", param, function(result) {

                var data = result.data;

                $("#vVendrCd").text(data.vendrCd);
                $("#vVendrNm").text(data.vendrNm);
                $("#vOwnerNm").text(data.ownerNm);
                $("#vVendorFg").text(data.vendorFgNm);
                $("#vVatIncldYn").text(data.vatIncldYnNm);
                $("#vUseYn").text(data.useYnNm);

                if(data.bizNo1 != null && data.bizNo2 != null && data.bizNo3 != null)
                    $("#vBizNo").text(data.bizNo1 + "-" + data.bizNo2 + "-" + data.bizNo3);

                if(data.telNo != null)
                    $("#vTelNo").text(data.telNo);

                if(data.emailAddr != null)
                    $("#vEmailAddr").text(data.emailAddr);

                if(data.faxNo != null)
                    $("#vFaxNo").text(data.faxNo);

                if(data.postNo != null && data.addr != null && data.addrDtl != null )
                    $("#vAddr").text("("+data.postNo+") "+data.addr + " "+data.addrDtl);

                if(data.remark != null)
                    $("#vRemark").text(data.remark);

            },
            function (result) {
                s_alert.pop(result.message);
                return false;
            }
        );
    });

    // 수정
    $scope.modifyInfo = function(){

        $scope.wjVendrInfoLayer.hide();
        $scope.wjVendrRegistLayer.show();

        var data = {};
        data.type = "mod";
        data.vendrCd = valVendrCd;

        $scope._broadcast('vendrRegistCtrl', data);
        event.preventDefault();
    };

    // 닫기
    $scope.close = function () {
        $scope.wjVendrInfoLayer.hide();

        // 재조회
        $scope._broadcast('vendrCtrl');
    };

    // 입력값 초기화
    $scope.infoInit = function(){

        $("#vVendrCd").text("");
        $("#vVendrNm").text("");
        $("#vOwnerNm").text("");
        $("#vVendorFg").text("");
        $("#vVatIncldYn").text("");
        $("#vUseYn").text("");
        $("#vBizNo").text("");
        $("#vTelNo").text("");
        $("#vEmailAddr").text("");
        $("#vFaxNo").text("");
        $("#vAddr").text("");
        $("#vRemark").text("");

    };

    // 탭변경
    $scope.changeTab = function(val) {

        if(val ==  "1"){ // 거래처 등록 Tab 클릭 시

        }else{ // 취급상품 Tab 클릭 시

            if(valVendrCd == ""){
                s_alert.pop(messages["vendr.request.regist.vendr"]);
                return false;

            }else{
                $scope.wjVendrInfoLayer.hide();
                $scope.wjVendrTrtmntLayer.show();

                $scope._broadcast('vendrTrtmntCtrl', valVendrCd);
                event.preventDefault();

            }
        }
    };

}]);