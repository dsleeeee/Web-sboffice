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

// 부가세포함여부
var vatIncldYnData = [
    {"name":"별도","value":"N"},
    {"name":"포함","value":"Y"}
];

// 거래처구분
var vendorFgData2 = [
    {"name":"매출거래처","value":"2"}
];

// 거래처구분
var vendorFgData1 = [
    {"name":"매입거래처","value":"1"}
];

// 업체구분
var companyFgData = [
    {"name":"미정","value":"0000"}
];

// 사업자구분
var businessFgData = [
    {"name":"국민대학교","value":"0000"}
];

// 매입처구분
var acquireCdData = [
    {"name":"수정예정","value":"000"},
    {"name":"국내개인","value":"003"}
];

// 매입구분
var acquireFgData = [
    {"name":"현매","value":"1"},
    {"name":"현매(월)","value":"2"},
    {"name":"위탁","value":"3"},
    {"name":"외상","value":"4"},
    {"name":"현매(거)","value":"5"}
];

// 관리지점
var manageSpotCdData = [
    {"name":"수정예정","value":"000"},
    {"name":"문구점","value":"002"}
];

/**
 *  거래처 등록
 */
app.controller('vendrRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrRegistCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    if(urlVendorFg == "2") {
        $scope._setComboData("rVendorFg", vendorFgData2);      // 거래처구분
    }
    else if(urlVendorFg == "1") {
        $scope._setComboData("rVendorFg", vendorFgData1);      // 거래처구분
    }
    else {
        $scope._setComboData("rVendorFg", vendorFgData);      // 거래처구분
    }
    $scope._setComboData("rVatIncldYn", vatIncldYnData);  // 부가세포함여부
    $scope._setComboData("rUseYn", useYnData);            // 사용여부
    $scope._setComboData("rShipFg", useYnData);           // 직배송
    $scope._setComboData("rCompanyFg", companyFgData); // 업체구분
    $scope._setComboData("rBusinessFg", businessFgData); // 사업자구분
    $scope._setComboData("rAcquireCd", acquireCdData); // 매입처구분
    $scope._setComboData("rAcquireFg", acquireFgData); // 매입구분
    $scope._setComboData("rManageSpotCd", manageSpotCdData); // 관리지점

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

            $("#trMembr").hide();

        }else{

            // 상세 조회
            var param = data;

            $.postJSON("/base/prod/vendr/regist/view.sb", param, function(result) {

                    var data = result.data;

                    $("#rVendrCd").val(data.vendrCd);
                    $("#rVendrNm").val(data.vendrNm);
                    $("#rOwnerNm").val(data.ownerNm);
                    $scope.vendorFgCombo.selectedValue = data.vendorFg;
                    $scope.vatIncldYnCombo.selectedValue = data.vatIncldYn;
                    $scope.useYnCombo.selectedValue = data.useYn;
                    $("#rBizNo1").val(data.bizNo1);
                    $("#rBizNo2").val(data.bizNo2);
                    $("#rBizNo3").val(data.bizNo3);
                    $("#rTelNo").val(data.telNo);
                    $("#rFaxNo").val(data.faxNo);
                    $("#rPostNo").val(data.postNo);
                    $("#rAddr").val(data.addr);
                    $("#rAddrDtl").val(data.addrDtl);
                    $("#rEmailAddr").val(data.emailAddr);
                    $scope.shipFgCombo.selectedValue = data.shipFg;
                    $("#rRemark").val(data.remark);

                    if(urlVendorFg == "2" || urlVendorFg == "1") {
                        $scope.businessFgCombo.selectedValue = data.businessFg;
                        $("#rCorporationNumber").val(data.corporationNumber);
                        $("#rBusinessStatus").val(data.businessStatus);
                        $("#rIndustry").val(data.industry);
                        $("#rHomepage").val(data.homepage);
                        $("#rOwnerTelNo").val(data.ownerTelNo);
                        $("#rOwnerEmail").val(data.ownerEmail);
                        $("#rManagerNm").val(data.managerNm);
                        $("#rManagerTelNo").val(data.managerTelNo);
                        $("#rManagerEmail").val(data.managerEmail);
                        $("#rManagerSpot").val(data.managerSpot);
                        $("#rManagerPhoneNo").val(data.managerPhoneNo);
                        $("#rBankCd").val(data.bankCd);
                        $("#rAccountNo").val(data.accountNo);
                        $("#rDepositor").val(data.depositor);
                        $("#rCollectFg").val(data.collectFg);
                        $("#rDouzoneErp").val(data.douzoneErp);
                        $("#rCreditLimit").val(data.creditLimit);
                        $("#rCollateralType").val(data.collateralType);
                        $("#rCollateralAmt").val(data.collateralAmt);
                        $("#rContrastDate").val(data.contrastDate);
                        $("#rCollatorStore").val(data.collatorStore);
                        $("#rCollatorCompany").val(data.collatorCompany);
                        $("#rDealStartDate").val(data.dealStartDate);
                        $("#rDealEndDate").val(data.dealEndDate);
                        $("#rLastSaleDate").val(data.lastSaleDate);
                        $("#rLastDepositDate").val(data.lastDepositDate);
                    }
                    if(urlVendorFg == "2") {
                        $scope.companyFgCombo.selectedValue = data.companyFg;
                        $("#rPurchaseId").val(data.purchaseId);
                        $("#rMembrNo").val(data.membrNo);
                        $("#rMembrNm").val(data.membrNm);
                    }
                    if(urlVendorFg == "1") {
                        $("#rElectronicBill").val(data.electronicBill);
                        $("#rAcquireProd").val(data.acquireProd);
                        $scope.acquireCdCombo.selectedValue = data.acquireCd;
                        $scope.acquireFgCombo.selectedValue = data.acquireFg;
                        $scope.manageSpotCdCombo.selectedValue = data.manageSpotCd;
                    }

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

            $("#trMembr").show();
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

        // 이메일 최대길이 체크
        if (nvl($("#rEmailAddr").val(), '') !== '' && nvl($("#rEmailAddr").val() + '', '').getByteLengthForOracle() > 100) {
            msg = messages["vendr.emailAddr"] + messages["cmm.overLength"] + " 100 " +
                ", 현재 : " + $("#rEmailAddr").val().getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 주소 최대길이 체크
        if (nvl($("#rAddr").val(), '') !== '' && nvl($("#rAddr").val() + '', '').getByteLengthForOracle() > 100) {
            msg = messages["vendr.addr"] + messages["cmm.overLength"] + " 100 " +
                ", 현재 : " + $("#rAddr").val().getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 주소상세 최대길이 체크
        if (nvl($("#rAddrDtl").val(), '') !== '' && nvl($("#rAddrDtl").val() + '', '').getByteLengthForOracle() > 100) {
            msg = messages["vendr.addrDtl"] + messages["cmm.overLength"] + " 100 " +
                ", 현재 : " + $("#rAddrDtl").val().getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
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

            if(urlVendorFg == "2" || urlVendorFg == "1") {
                param.businessFg = $scope.businessFgCombo.selectedValue;
                param.corporationNumber = $("#rCorporationNumber").val();
                param.businessStatus = $("#rBusinessStatus").val();
                param.industry = $("#rIndustry").val();
                param.homepage = $("#rHomepage").val();
                param.ownerTelNo = $("#rOwnerTelNo").val();
                param.ownerEmail = $("#rOwnerEmail").val();
                param.managerNm = $("#rManagerNm").val();
                param.managerTelNo = $("#rManagerTelNo").val();
                param.managerEmail = $("#rManagerEmail").val();
                param.managerSpot = $("#rManagerSpot").val();
                param.managerPhoneNo = $("#rManagerPhoneNo").val();
                param.bankCd = $("#rBankCd").val();
                param.accountNo = $("#rAccountNo").val();
                param.depositor = $("#rDepositor").val();
                param.collectFg = $("#rCollectFg").val();
                param.douzoneErp = $("#rDouzoneErp").val();
                param.creditLimit = $("#rCreditLimit").val();
                param.collateralType = $("#rCollateralType").val();
                param.collateralAmt = $("#rCollateralAmt").val();
                param.contrastDate = $("#rContrastDate").val();
                param.collatorStore = $("#rCollatorStore").val();
                param.collatorCompany = $("#rCollatorCompany").val();
                param.dealStartDate = $("#rDealStartDate").val();
                param.dealEndDate = $("#rDealEndDate").val();
                param.lastSaleDate = $("#rLastSaleDate").val();
                param.lastDepositDate = $("#rLastDepositDate").val();
            }
            if(urlVendorFg == "2") {
                param.companyFg = $scope.companyFgCombo.selectedValue;
                param.purchaseId = $("#rPurchaseId").val();
                param.membrNo = $("#rMembrNo").val();
                param.membrNm = $("#rMembrNm").val();
            }
            if(urlVendorFg == "1") {
                param.electronicBill = $("#rElectronicBill").val();
                param.acquireProd = $("#rAcquireProd").val();
                param.acquireCd = $scope.acquireCdCombo.selectedValue;
                param.acquireFg = $scope.acquireFgCombo.selectedValue;
                param.manageSpotCd = $scope.manageSpotCdCombo.selectedValue;
            }

            if(orgnFg == "HQ" && gEnvst1242 == "2"){ // 본사권한 이면서, [1242] 거래처출고구분 값이 [2] 거래처별출고전표자동생성 인 경우만, 입력 가능
                param.shipFg = $scope.shipFgCombo.selectedValue;
            }else{
                param.shipFg = 'N'; // 매장권한 이거나, 본사권한 이지만 [1242] 거래처출고구분 값이 [2] 거래처별출고전표자동생성이 아닌 경우는 입력 불가
            }

            // 저장
            $scope._postJSONSave.withPopUp(url, param, function () {
                $scope.close();
            });
        }
    };

    // 닫기
    $scope.close = function () {
        // $scope.infoInit();
        $scope.wjVendrRegistLayer.hide();

        // 재조회
        $scope._broadcast('vendrCtrl');
    };

    // 입력값 초기화
    $scope.infoInit = function(){

        if(urlVendorFg == "2") {
            var inputArr = [
                rVendrCd, rVendrNm, rOwnerNm, rBizNo1, rBizNo2, rBizNo3, rTelNo, rEmailAddr, rFaxNo, rPostNo, rAddr, rAddrDtl, rRemark,
                rCorporationNumber, rBusinessStatus, rIndustry, rHomepage, rOwnerTelNo,
                rOwnerEmail, rManagerNm, rManagerTelNo, rManagerEmail, rManagerSpot, rManagerPhoneNo, rBankCd, rAccountNo,
                rDepositor, rCollectFg, rDouzoneErp, rCreditLimit, rCollateralType, rCollateralAmt, rContrastDate, rCollatorStore,
                rCollatorCompany, rDealStartDate, rDealEndDate, rLastSaleDate, rLastDepositDate,
                rPurchaseId, rMembrNo, rMembrNm
            ].forEach(function(element){element.value="";});
        }
        else if(urlVendorFg == "1") {
            var inputArr = [
                rVendrCd, rVendrNm, rOwnerNm, rBizNo1, rBizNo2, rBizNo3, rTelNo, rEmailAddr, rFaxNo, rPostNo, rAddr, rAddrDtl, rRemark,
                rCorporationNumber, rBusinessStatus, rIndustry, rHomepage, rOwnerTelNo,
                rOwnerEmail, rManagerNm, rManagerTelNo, rManagerEmail, rManagerSpot, rManagerPhoneNo, rBankCd, rAccountNo,
                rDepositor, rCollectFg, rDouzoneErp, rCreditLimit, rCollateralType, rCollateralAmt, rContrastDate, rCollatorStore,
                rCollatorCompany, rDealStartDate, rDealEndDate, rLastSaleDate, rLastDepositDate,
                rElectronicBill, rAcquireProd
            ].forEach(function(element){element.value="";});
        }
        else {
            var inputArr = [
                rVendrCd, rVendrNm, rOwnerNm, rBizNo1, rBizNo2, rBizNo3, rTelNo, rEmailAddr, rFaxNo, rPostNo, rAddr, rAddrDtl, rRemark
            ].forEach(function(element){element.value="";});
        }

        /*var selectArr = [
            rVendorFg, rVatIncldYn, rUseYn
        ].forEach(function(element){element.selectedIndex=0;});*/

        // 거래처구분, 부가세포함여부, 사용여부값 기본 셋팅
        $scope.vendorFgCombo.selectedValue = '1';
        $scope.vatIncldYnCombo.selectedValue = 'N';
        $scope.useYnCombo.selectedValue = 'Y';
        $scope.shipFgCombo.selectedValue = 'N';

        if(urlVendorFg == "2" || urlVendorFg == "1") {
            $scope.businessFgCombo.selectedValue = "0000";
        }
        if(urlVendorFg == "2") {
            $scope.companyFgCombo.selectedValue = "0000";
        }
        if(urlVendorFg == "1") {
            $scope.acquireCdCombo.selectedValue = "000";
            $scope.acquireFgCombo.selectedValue = "1";
            $scope.manageSpotCdCombo.selectedValue = "000";
        }
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