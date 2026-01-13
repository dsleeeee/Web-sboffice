/****************************************************************
 *
 * 파일명 : prodDetailView.js
 * 설  명 : 상품정보관리 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
 *
 * **************************************************************/
/**
 * 팝업 그리드 생성
 */
app.controller('prodDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodDetailCtrl', $scope, $http, false));
    // 상품 본사통제구분 (H : 본사, S: 매장)
    // $scope.prodEnvstVal = prodEnvstVal;
    // $scope.userOrgnFg = gvOrgnFg;

    // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
    // $scope.userStoreCd = gvStoreCd;

    $scope.btnShowFg = false;
    // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
    // || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
    //   $scope.btnShowFg = true;
    // }

    // 판매가 내점/배달/포장에 적용
    $scope.saleUprcApply = true;

    // 상품상세정보
    $scope.prodDetail = {};
    // 상품정보관리 그리드 조회
    $scope.$on("prodDetailCtrl", function(event, data) {

        var url = "/base/prod/prod/prod/detail.sb";

        if (orgnFg === "HQ") {
            if(data.storeCd != null && data.storeCd != "" && data.storeCd != undefined){
                url = "/base/prod/soldOut/soldOut/detail.sb";
            }
        }

        // 파라미터
        var params = {};
        params = data;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp(url, params, function(response){
                // 상품정보
                var prodDetail = response.data.data.list;
                // 상품상세정보 Set
                $scope.prodDetail = prodDetail;
                var scope = agrid.getScope("prodCtrl");

                if(scope !== null && scope !== "" && scope !== undefined){ // 상품정보관리 화면 외에 다른 화면에서도 사용할 때 에러 발생
                    scope.setProdInfo(prodDetail);
                }

                // 연결상품정보
                var linkedProdList = prodDetail.linkedProdList;
                var linkedProdInfo = "";
                if( linkedProdList.length > 0 ) {
                    $.each(linkedProdList, function() {
                        linkedProdInfo += '<tr><th><div class=\"impWrap\">' + messages['prod.prodCd'] + '<em class=\"imp\">*</em></div></th>'
                            + '<td><a href=\"#\" class=\"link\">' + this.unitProdCd + '</a></td></tr>';
                    });
                } else {
                    linkedProdInfo = '<tr><th colspan=\"2\">' + messages['cmm.empty.data'] + '</th></tr>';
                }
                $("#_linkedProdInfoProdDetail").html(linkedProdInfo);

                // 상품 이미지
                if(prodDetail.imgUrl === null) {
                    $("#goodsNoProdDetail").css('display', 'block');
                    $("#goodsYesProdDetail").css('display', 'none');

                } else {
                    $("#goodsNoProdDetail").css('display', 'none');
                    $("#goodsYesProdDetail").css('display', 'block');
                    $("#imgProdImageProdDetail").attr("src", prodDetail.imgUrl);
                }

                // 세트구성상품 팝업버튼 visible 처리
                if(prodDetail.setProdFg === "1"){
                    $("#btnDtlSetConfigProd").css("display", "none");
                } else {
                    $("#btnDtlSetConfigProd").css("display", "");
                }

                if(prodDetail.depositCupFg === null){
                    prodDetail.depositCupFg = "";
                }

                if(prodDetail.pointUseYn === null){
                    prodDetail.pointUseYn = "Y";
                }

                if(prodDetail.dcYn === null){
                    prodDetail.dcYn = "Y";
                }

                if(prodDetail.bundleFg === null){
                    prodDetail.bundleFg = "0";
                }


            // KIOSK 시간설정 셋팅
                if($scope.prodDetail.saleTimeFg === 'Y'){

                    var vParams = {};
                    vParams.prodCd = $scope.prodDetail.prodCd;

                    $scope._postJSONQuery.withOutPopUp("/base/prod/prod/prod/getProdSaleTime.sb", vParams, function(response){
                        if(response.data.data.list.length > 0){
                            var data = response.data.data.list;
                            var str = "";

                            for(var i=0; i<data.length; i++){
                                if(i > 0){
                                    str += '</br>';
                                }
                                str += data[i].sSaleTime.substring(0, 2) + ":" + data[i].sSaleTime.substring(2, 4) + " ~ "
                                     + data[i].eSaleTime.substring(0, 2) + ":" + data[i].eSaleTime.substring(2, 4);
                            }
                            $("#divSaleTime").html(str);
                        }
                    });
                }else{
                    $("#divSaleTime").text("");
                }

                // [1250 맘스터치]
                if($scope.prodDetail.momsKioskEdge === null || $scope.prodDetail.momsKioskEdge === ""){
                    $scope.prodDetail.momsKioskEdge = "0";
                }

                // 출시일
                if($scope.prodDetail.releaseDate !== null && $scope.prodDetail.releaseDate !== undefined && $scope.prodDetail.releaseDate !== ""){
                    $("#lblReleaseDate").text(getFormatDate($scope.prodDetail.releaseDate, "-"));
                }else{
                    $("#lblReleaseDate").text("");
                }

                // 단종
                if($scope.prodDetail.disconYn !== null && $scope.prodDetail.disconYn !== undefined && $scope.prodDetail.disconYn !== "" && $scope.prodDetail.disconYn === 'Y'){
                    $("#lblDisconDate").text(getFormatDate($scope.prodDetail.disconDate, "-"));
                }else{
                    $("#lblDisconDate").text("");
                }

                // 판매방식
                var vStr = "";
                vStr += $scope.prodDetail.saleTypeYnSin === 'Y' ? messages["prod.inStore"]  + ", " : "";
                vStr += $scope.prodDetail.saleTypeYnDlv === 'Y' ? messages["prod.delivery"] + ", " : "";
                vStr += $scope.prodDetail.saleTypeYnPkg === 'Y' ? messages["prod.packing"] + ", " : "";
                if(vStr.length > 0){
                    $("#lblSaleType").text(vStr.substring(0, vStr.length-2));
                }else{
                    $("#lblSaleType").text("");
                }

                // 판매채널
                vStr = "";
                vStr += $scope.prodDetail.saleChnYnPos === 'Y' ? messages["prod.pos"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnKsk === 'Y' ? messages["prod.kiosk"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnCmp === 'Y' ? messages["prod.app"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnBae === 'Y' ? messages["prod.baemin"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnBao === 'Y' ? messages["prod.baemin1"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnYgy === 'Y' ? messages["prod.yogiyo"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnYge === 'Y' ? messages["prod.yogiyoExp"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnCpn === 'Y' ? messages["prod.coupang"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnTng === 'Y' ? messages["prod.baedaltong"]  + ", " : "";
                vStr += $scope.prodDetail.saleChnYnDdn === 'Y' ? messages["prod.ddangyo"]  + ", " : "";
                if(vStr.length > 0){
                    $("#lblSaleChannel").text(vStr.substring(0, vStr.length-2));
                }else{
                    $("#lblSaleChannel").text("");
                }

                // 도서구분 값 없으면 [일반상품관리] 화면은 일반상품관리, [도서관리] 화면은 도서관리
                if ($scope.prodDetail.orgProdFg === null || $scope.prodDetail.orgProdFg === "") {
                    if (urlProdFg === "1") {
                        $scope.prodDetail.orgProdFg = "10";
                    } else if (urlProdFg === "2") {
                        $scope.prodDetail.orgProdFg = "20";
                    } else {
                        $scope.prodDetail.orgProdFg = "00";
                    }
                    $("#trOrgProdFg_Orderkit").css("display", "none");
                } else {
                    if ($scope.prodDetail.orgProdFg === "30") {
                        $("#trOrgProdFg_Orderkit").css("display", ""); // 오더킷 상품 표시
                    } else {
                        $("#trOrgProdFg_Orderkit").css("display", "none");
                    }
                }

                // 상품등록구분
                if ($scope.prodDetail.orgProdFg === null || $scope.prodDetail.orgProdFg === "" || $scope.prodDetail.orgProdFg === "00") {
                    $("#trDtlOrgProdFg_Orderkit").css("display", "none");
                } else {
                    $("#trDtlOrgProdFg_Orderkit").css("display", "");
                }
            }
        );
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 세트구성상품 팝업
    $scope.dtlSetConfigProd = function () {

        var params = {};
        params.prodCd = $scope.prodDetail.prodCd;
        params.setProdFg = $scope.prodDetail.setProdFg;
        params.viewType = "detail";

        $scope.setConfigProdLayer.show(true);
        $scope._broadcast('setConfigProdCtrl', params);
        event.preventDefault();
    };

}]);
