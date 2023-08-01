/****************************************************************
 *
 * 파일명 : bbqMemberExcelUpload.js
 * 설  명 : 회원엑셀업로드(BBQ) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.26    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 검색기간 콤보박스
var periodData = [
    {"name":"기간 미사용","value":"all"},
    {"name":"가입일","value":"reg"},
    {"name":"최종방문일","value":"last"}
];

/**
 *  회원엑셀업로드(BBQ) 그리드 생성
 */
app.controller('bbqMemberExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('bbqMemberExcelUploadCtrl', $scope, $http, $timeout, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("periodType", periodData);        // 검색기간 타입
    $scope._setComboData("membrClassCd", memberClassList); // 회원등급

    $scope.initGrid = function (s, e) {

    };

    // 조회 버튼 클릭
    $scope.$on("bbqMemberExcelUploadCtrl", function (event, data) {
        // 회원목록 조회
        $scope.getMemberList();
        event.preventDefault();
    });

    // 회원목록 조회
    $scope.getMemberList = function () {

        var params = {};
        params.periodType = $scope.periodType;
        params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
        params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
        params.memberClass = $scope.membrClassCdCombo.selectedValue;
        params.membrNo = $("#memberNo").val();
        params.membrNm = $("#memberNm").val();
        params.membrClassManageFg = membrClassManageFg;
        params.listScale = 500;

        $scope._inquiryMain("/membr/info/view/view/getMemberlist.sb", params, function () {});
    };

    // 엑셀업로드(BBQ)
    $scope.excelUpload = function () {

        // 등록매장을 선택하세요.
        if($("#bbqMemberExcelUploadStoreCd").val() === ""){
            $scope._popMsg(messages["bbq.member.excel.regStore.chkMsg"] );
            return false;
        }
        
        var vScope = agrid.getScope('bbqExcelFileUploadCtrl');

        // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
        $scope._popConfirm(messages["bbq.member.excel.confmMsg"], function() {

            /* 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
            vScope.parentCtrl = 'bbqMemberExcelUploadCtrl';

            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');
        });
    };

    /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
    $scope.uploadCallBack = function () {
        $scope._pageView('bbqMemberExcelUploadCtrl', 1);

        var vScope = agrid.getScope('bbqExcelFileUploadCtrl');
        vScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
    };


    // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.bbqMemberExcelUploadStoreShow = function () {
        $scope._pageView('bbqMemberExcelUploadStoreCtrl', 1);
    };

}]);