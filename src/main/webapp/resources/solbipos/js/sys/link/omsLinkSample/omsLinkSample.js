/****************************************************************
 *
 * 파일명 : omsLinkSample.js
 * 설  명 : OMS연동샘플 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.11     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 *  OMS연동샘플 그리드 생성
 */
app.controller('omsLinkSampleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('omsLinkSampleCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on('omsLinkSampleCtrl', function (event, data) {
        
        // OMS연동샘플 API 호출 목록 조회(전체조회)
        $scope.searchOmsLinkSampleReqList(0);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // OMS연동샘플 API 호출 목록 조회
    $scope.searchOmsLinkSampleReqList = function (seq) {

        var params = {};
        params.seq = seq;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain('/sys/link/omsLinkSample/getOmsLinkSampleReqList.sb', params);
    };

    // 매장등록(001)
    $scope.regStore = function (searchType) {

        if ($("#regStore_shopName").val() === "") {
            $scope._popMsg(messages['omsLinkSample.shopName'] + messages['cmm.require.text']); // 매장명을(를) 입력해주세요.
            return;
        }

        if ($("#regStore_taxNo").val() === "") {
            $scope._popMsg(messages['omsLinkSample.taxNo'] + messages['cmm.require.text']); // 사업자번호을(를) 입력해주세요.
            return;
        }

        if ($("#regStore_ceoName").val() === "") {
            $scope._popMsg(messages['omsLinkSample.ceoName'] + messages['cmm.require.text']); // 대표자명을(를) 입력해주세요.
            return;
        }

        if ($("#regStore_postNo").val() === "") {
            $scope._popMsg(messages['omsLinkSample.postNo'] + messages['cmm.require.text']); // 우편번호을(를) 입력해주세요.
            return;
        }

        if ($("#regStore_addrBase").val() === "") {
            $scope._popMsg(messages['omsLinkSample.addrBase'] + messages['cmm.require.text']); // 주소을(를) 입력해주세요.
            return;
        }

        if ($("#regStore_addrDetail").val() === "") {
            $scope._popMsg(messages['omsLinkSample.addrDetail'] + messages['cmm.require.text']); // 상세주소을(를) 입력해주세요.
            return;
        }

        if ($("#regStore_posType").val() === "") {
            $scope._popMsg(messages['omsLinkSample.posType'] + messages['cmm.require.text']); // POS사정보코드을(를) 입력해주세요.
            return;
        }

        if ($("#regStore_posShopId").val() === "") {
            $scope._popMsg(messages['omsLinkSample.posShopId'] + messages['cmm.require.text']); // POS사매장ID을(를) 입력해주세요.
            return;
        }

        var params = {};
        params.linkType = "001";
        params.searchType = searchType;
        params.shopName = $("#regStore_shopName").val();
        params.texNo = $("#regStore_taxNo").val();
        params.ceoName = $("#regStore_ceoName").val();
        params.postNo = $("#regStore_postNo").val();
        params.addrBase = $("#regStore_addrBase").val();
        params.addrDetail = $("#regStore_addrDetail").val();
        params.posType = $("#regStore_posType").val();
        params.posShopId = $("#regStore_posShopId").val();

        $scope._postJSONSave.withOutPopUp("/sys/link/omsLinkSample/getOmsLinkSampleReqApi.sb", params, function (response) {

            // api 로그 seq값
            var seq = response.data.data;

            // OMS연동샘플 API 호출 목록 조회(방금 호출한 건 즉시조회)
            $scope.searchOmsLinkSampleReqList(seq);

        });
    };

    // 매장 배대사 등록(002)
    $scope.regStoreAgency = function (searchType) {

        var params = {};
        params.linkType = "002";
        params.searchType = searchType;

    };

    // 배대사 코드 조회(003)
    $scope.searchAgencyCode = function (searchType) {

        var params = {};
        params.linkType = "003";
        params.searchType = searchType;

        $scope._postJSONSave.withOutPopUp("/sys/link/omsLinkSample/getOmsLinkSampleReqApi.sb", params, function (response) {

            // api 로그 seq값
            var seq = response.data.data;

            // OMS연동샘플 API 호출 목록 조회(방금 호출한 건 즉시조회)
            $scope.searchOmsLinkSampleReqList(seq);

        });
    };

    // 배대사 매장 조회(004)
    $scope.searchAgencyStore = function (searchType) {

        if ($("#searchAgencyStore_posShopId").val() === "") {
            $scope._popMsg(messages['omsLinkSample.posShopId'] + messages['cmm.require.text']); // POS사매장ID을(를) 입력해주세요.
            return;
        }

        if ($("#searchAgencyStore_agencyCode").val() === "") {
            $scope._popMsg(messages['omsLinkSample.agencyCode'] + messages['cmm.require.text']); // 배달업체코드을(를) 입력해주세요.
            return;
        }

        var params = {};
        params.linkType = "004";
        params.searchType = searchType;
        params.posShopId = $("#searchAgencyStore_posShopId").val();
        params.agencyCode = $("#searchAgencyStore_agencyCode").val();
        params.subAgencyCode = $("#searchAgencyStore_subAgencyCode").val();

        $scope._postJSONSave.withOutPopUp("/sys/link/omsLinkSample/getOmsLinkSampleReqApi.sb", params, function (response) {

            // api 로그 seq값
            var seq = response.data.data;

            // OMS연동샘플 API 호출 목록 조회(방금 호출한 건 즉시조회)
            $scope.searchOmsLinkSampleReqList(seq);

        });
    };

    // 배대사 매핑 등록(005)
    $scope.regAgencyMapping = function (searchType) {

        if ($("#regAgencyMapping_posShopId").val() === "") {
            $scope._popMsg(messages['omsLinkSample.posShopId'] + messages['cmm.require.text']); // POS사매장ID을(를) 입력해주세요.
            return;
        }

        if ($("#regAgencyMapping_agencyCode").val() === "") {
            $scope._popMsg(messages['omsLinkSample.agencyCode'] + messages['cmm.require.text']); // 배달업체코드을(를) 입력해주세요.
            return;
        }

        var params = {};
        params.linkType = "005";
        params.searchType = searchType;
        params.posShopId = $("#regAgencyMapping_posShopId").val();
        params.agencyCode = $("#regAgencyMapping_agencyCode").val();
        params.subAgencyCode = $("#regAgencyMapping_subAgencyCode").val();
        params.storeCode = $("#regAgencyMapping_storeCode").val();
        params.isB2BContract = $("#regAgencyMapping_isB2BContract").val();

        $scope._postJSONSave.withOutPopUp("/sys/link/omsLinkSample/getOmsLinkSampleReqApi.sb", params, function (response) {

            // api 로그 seq값
            var seq = response.data.data;

            // OMS연동샘플 API 호출 목록 조회(방금 호출한 건 즉시조회)
            $scope.searchOmsLinkSampleReqList(seq);

        });
    };

    // 배대사 매핑 삭제(006)
    $scope.delAgencyMapping = function (searchType) {

        if ($("#delAgencyMapping_posShopId").val() === "") {
            $scope._popMsg(messages['omsLinkSample.posShopId'] + messages['cmm.require.text']); // POS사매장ID을(를) 입력해주세요.
            return;
        }

        if ($("#delAgencyMapping_mappingSequence").val() === "") {
            $scope._popMsg(messages['omsLinkSample.mappingSequence'] + messages['cmm.require.text']); // 매핑일련번호을(를) 입력해주세요.
            return;
        }

        var params = {};
        params.linkType = "006";
        params.searchType = searchType;
        params.posShopId = $("#delAgencyMapping_posShopId").val();
        params.mappingSequence = $("#delAgencyMapping_mappingSequence").val();

        $scope._postJSONSave.withOutPopUp("/sys/link/omsLinkSample/getOmsLinkSampleReqApi.sb", params, function (response) {

            // api 로그 seq값
            var seq = response.data.data;

            // OMS연동샘플 API 호출 목록 조회(방금 호출한 건 즉시조회)
            $scope.searchOmsLinkSampleReqList(seq);

        });
    };

}]);