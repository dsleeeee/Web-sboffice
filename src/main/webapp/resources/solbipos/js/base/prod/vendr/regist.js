/****************************************************************
 *
 * 파일명 : regist.js
 * 설  명 : 거래처 등록 JavaScript
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
 *  거래처 등록
 */
app.controller('vendrRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrRegistCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("rVendorFg", vendorFgData);      // 거래처구분
    $scope._setComboData("rVatIncldYn", vatIncldYnData); // 부가세포함여부
    $scope._setComboData("rUseYn", useYnData);             // 사용여부

    // 등록인지 수정인지 파악하기 위해
    var valType;
    // 취급상품 등록 시 사용
    var valVendrCd;

    // 해당 scope 호출
    $scope.$on("vendrRegistCtrl", function(event, data) {

        // 입력값 초기화
        $scope.infoInit();

        // 등록인지 수정인지 파악(탭 클릭 시 사용)
        valType = data.type;
        // 취급상품 등록 시 사용
        valVendrCd = data.vendrCd;

        if( data.type == "reg"){

            // 제목과 버튼
            $("#popTitleReg").show();
            $("#popTitleMod").hide();
            $("#btnReg").show();
            $("#btnMod").hide();

        }else{

            // 상세 조회
            var param = data;

            $.postJSON("/base/prod/vendr/regist/view.sb", param, function(result) {

                    var data = result.data;

                    $("#rVendrCd").val(data.vendrCd);
                    $("#rVendrNm").val(data.vendrNm);
                    $("#rOwnerNm").val(data.ownerNm);
                    $("#rVendorFg").val(data.vendorFg);
                    $("#rVatIncldYn").val(data.vatIncldYn);
                    $("#rUseYn").val(data.useYn);
                    $("#rBizNo1").val(data.bizNo1);
                    $("#rBizNo2").val(data.bizNo2);
                    $("#rBizNo3").val(data.bizNo3);
                    $("#rTelNo").val(data.telNo);
                    $("#rFaxNo").val(data.faxNo);
                    $("#rPostNo").val(data.postNo);
                    $("#rAddr").val(data.addr);
                    $("#rAddrDtl").val(data.addrDtl);
                    $("#rEmailAddr").val(data.emailAddr);
                    $("#rRemark").val(data.remark);

                },
                function (result) {
                    s_alert.pop(result.message);
                    return false;
                }
            );

            // 제목과 버튼
            $("#popTitleReg").hide();
            $("#popTitleMod").show();
            $("#btnReg").hide();
            $("#btnMod").show();
        }

        event.preventDefault();
    });

    // 입력값 체크
    $scope.chkVal = function () {

        // 거래처명을 입력해주세요.
        var msg = messages["vendr.vendrNm"] + messages["cmm.require.text"];
        if( isNull($("#rVendrNm").val())) {
            $scope._popMsg(msg);
            return false;
        }

        // 대표자명을 입력해주세요.
        var msg = messages["vendr.ownerNm"] + messages["cmm.require.text"];
        if( isNull($("#rOwnerNm").val())) {
            $scope._popMsg(msg);
            return false;
        }

        /*// 거래처 구분 여부를 선택 해주세요.
        var msg = messages["vendr.vendorFg"] + messages["cmm.require.select"];
        if($scope.rVendorFg === "") {
            s_alert.pop(msg);
            return false;
        }

        // 부가세 포함여부를 선택 해주세요.
        var msg = messages["vendr.vatIncldYn"] + messages["cmm.require.select"];
        if($scope.rVatIncldYn === "") {
            s_alert.pop(msg);
            return false;
        }

        //사용여부를 선택 해주세요.
        var msg = messages["vendr.useYn"] + messages["cmm.require.select"];
        if(rUseYn.value === "") {
            s_alert.pop(msg);
            return false;
        }*/

        return true;
    };

    // 신규등록 버튼
    $scope.regVendr = function(){
        $scope.save("/base/prod/vendr/regist/regist.sb")
    };

    // 저장 버튼(수정)
    $scope.modVendr = function(){
        $scope.save("/base/prod/vendr/regist/modify.sb")
    };

    // 거래처 데이터 저장
    $scope.save = function(url){

        if($scope.chkVal()){

            var param = {};

            param.vendrCd = $("#rVendrCd").val();
            param.vendrNm = $("#rVendrNm").val();
            param.ownerNm = $("#rOwnerNm").val();
            param.vendorFg = $scope.vendorFg;
            param.vatIncldYn = $scope.vatIncldYn;
            param.useYn = $scope.useYn;
            param.bizNo = $("#rBizNo1").val() + $("#rBizNo2").val() + $("#rBizNo3").val();
            param.telNo = $("#rTelNo").val();
            param.emailAddr = $("#rEmailAddr").val();
            param.faxNo = $("#rFaxNo").val();
            param.postNo = $("#rPostNo").val();
            param.addr = $("#rAddr").val();
            param.addrDtl = $("#rAddrDtl").val();
            param.remark = $("#rRemark").val();

            // 저장
            $scope._postJSONSave.withPopUp(url, param, function () { $scope.close(); });
        }
    }

    // 닫기
    $scope.close = function () {
        $scope.infoInit();
        $scope.wjVendrRegistLayer.hide();

        // 재조회
        $scope._broadcast('vendrCtrl');
    };

    // 입력값 초기화
    $scope.infoInit = function(){

        var inputArr = [
            rVendrCd, rVendrNm, rOwnerNm, rBizNo1, rBizNo2, rBizNo3, rTelNo, rEmailAddr, rFaxNo, rPostNo, rAddr, rAddrDtl, rRemark
        ].forEach(function(element){element.value="";});

        var selectArr = [
            rVendorFg, rVatIncldYn, rUseYn
        ].forEach(function(element){element.selectedIndex=0;});

    };

    // 탭변경
    $scope.changeTab = function(val) {

        if(val ==  "1"){ // 거래처 등록 Tab 클릭 시

        }else{ // 취급상품 Tab 클릭 시
            
            if(valType == "reg"){
                s_alert.pop(messages["vendr.request.regist.vendr"]);
                return false;

            }else{
                $scope.wjVendrRegistLayer.hide();
                $scope.wjVendrTrtmntLayer.show();

                $scope._broadcast('vendrTrtmntCtrl', valVendrCd);
                event.preventDefault();

            }
        }
    };

}]);