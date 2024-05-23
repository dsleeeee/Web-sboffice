/****************************************************************
 *
 * 파일명 : prodImgBarrierFree.js
 * 설  명 : 베리어프리-이미지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.05.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];
var useYnComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

function imagePreview(imgVal, imgFg){
    var scope = agrid.getScope('prodImgBarrierFreeCtrl');
    scope.imgPreview(imgVal, imgFg);
}

/**
 *  상품 조회 그리드 생성
 */
app.controller('prodImgBarrierFreeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodImgBarrierFreeCtrl', $scope, $http, false));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // 전체기간 체크박스
    $scope.isChecked = true;

    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);
    $scope._setComboData('useYnAllComboData', useYnAllComboData); // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    $scope.initGrid = function (s, e) {
        // 그리드에서 사용하는 dataMap 초기화
        $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "prodCd") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodCd") {
                    $scope.getProdImg(selectedRow.prodCd, selectedRow.prodNm);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("prodImgBarrierFreeCtrl", function(event, data) {
        $scope.searchProdImgBarrierFreeList();
        event.preventDefault();
    });

    // 상품 목록 조회
    $scope.searchProdImgBarrierFreeList = function(){
        // 상품이미지 영역 숨기기
        $("#imgTbl").css("display", "none");
        $("#btnCopy").css("display", "none");

        //상품코드, 이름 초기화
        $("#prodInfo").text("");
        $("#hdProdCd").val("");
        $("#hdProdNm").val("");

        // 이미지와 첨부파일 초기화
        $scope.imgCancel("006", 'A');
        $scope.imgCancel("007", 'A');
        $scope.imgCancel("008", 'A');

        // 파라미터
        var params = {};
        params.listScale = 15;

        // 등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }

        if(brandUseFg === "1" && orgnFg === "H"){

            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/prod/prodImgBarrierFree/prodImgBarrierFree/getProdImgBarrierFreeList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // 상품 이미지 조회
    $scope.getProdImg = function(prodCd, prodNm){
        // 상품을 클릭하면 상품이미지등록이 보이도록
        $("#imgTbl").css("display", "");
        $("#btnCopy").css("display", "");

        //상품코드, 이름 셋팅
        $("#prodInfo").text("[" + prodCd + "] " + prodNm);
        $("#hdProdCd").val(prodCd);
        $("#hdProdNm").val(prodNm);

        // 이미지와 첨부파일 초기화
        $scope.imgCancel("006", 'A');
        $scope.imgCancel("007", 'A');
        $scope.imgCancel("008", 'A');

        var params = {};
        params.prodCd = prodCd;

        $scope._postJSONQuery.withOutPopUp("/base/prod/prodImgBarrierFree/prodImgBarrierFree/getProdImgBarrierFreeImageList.sb", params, function (response) {

            if (response.data.data.list.length > 0) {
                var list = response.data.data.list;

                for (var i = 0; i < list.length; i++) {

                    if(list[i].imgFg === "006"){
                        $("#imgKioskGreen").html("<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' class='imgPic'>");
                        $("#hdKioskGreenFileNm").val(list[i].imgFileNm);
                    }

                    if(list[i].imgFg === "007"){
                        $("#imgKioskYellow").html("<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' class='imgPic'>");
                        $("#hdKioskYellowFileNm").val(list[i].imgFileNm);
                    }

                    if(list[i].imgFg === "008"){
                        $("#imgKioskWhite").html("<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' class='imgPic'>");
                        $("#hdKioskWhiteFileNm").val(list[i].imgFileNm);
                    }
                }
            }
        });
    };

    // 첨부 이미지 미리보기
    $scope.imgPreview = function (imgVal, imgFg) {
        if(imgVal.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                if(imgFg === '006') $("#imgKioskGreen").html("<img src='" +  e.target.result + "' class='imgPic'>");
                if(imgFg === '007') $("#imgKioskYellow").html("<img src='" +  e.target.result + "' class='imgPic'>");
                if(imgFg === '008') $("#imgKioskWhite").html("<img src='" +  e.target.result + "' class='imgPic'>");
            };
            reader.readAsDataURL(imgVal.files[0]);
        }
    };

    // 이미지 선택취소
    $scope.imgCancel = function(imgFg, type){

        // type - A: 무조건 상품이미지 관련 데이터 초기화(상품리스트 조회, 상품코드 클릭 시 해당)
        // type - F: 상품이미지 파일이 등록되어있으면 '선택취소' 버튼 클릭 시 동작 안하도록

        if(type === "F"){
            if(imgFg === "006"){
                if($("#hdKioskGreenFileNm").val() !== "") {
                    if($("#fileKioskGreen").val() !== ""){
                        // 재조회
                        $scope.getProdImg($("#hdProdCd").val(), $("#hdProdNm").val());
                        return;
                    }else{
                        return;
                    }
                }
            }

            if(imgFg === "007"){
                if($("#hdKioskYellowFileNm").val() !== "") {
                    if($("#fileKioskYellow").val() !== ""){
                        // 재조회
                        $scope.getProdImg($("#hdProdCd").val(), $("#hdProdNm").val());
                        return;
                    }else{
                        return;
                    }
                }
            }

            if(imgFg === "008"){
                if($("#hdKioskWhiteFileNm").val() !== "") {
                    if($("#fileKioskWhite").val() !== ""){
                        // 재조회
                        $scope.getProdImg($("#hdProdCd").val(), $("#hdProdNm").val());
                        return;
                    }else{
                        return;
                    }
                }
            }
        }

        var element = "";

        // 이미지와 첨부파일 초기화
        if (imgFg === "006") {
            $("#imgKioskGreen").html("No Image");
            $("#hdKioskGreenFileNm").val("");
            element = "fileKioskGreen";
        } else if (imgFg === "007") {
            $("#imgKioskYellow").html("No Image");
            $("#hdKioskYellowFileNm").val("");
            element = "fileKioskYellow";
        } else if (imgFg === "008") {
            $("#imgKioskWhite").html("No Image");
            $("#hdKioskWhiteFileNm").val("");
            element = "fileKioskWhite";
        }

        $scope.resetFile(element);
    };

    // 브라우저에 따른 첨부파일 초기화
    $scope.resetFile = function(element){
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            //$("#" + element).replaceWith( $("#" + element).clone(true) );
            $("#" + element).val("");
        } else {
            // other browser 일때
            $("#" + element).val("");
        }
    };

    // 이미지 등록
    $scope.regImg = function(imgFg){
        var fileSize = 0;
        var element = "";
        var errMsg = "";

        if(imgFg === "006"){
            fileSize = 300;
            element = "fileKioskGreen";
            errMsg = messages["prodImgBarrierFree.fileSizeChk.300.msg"];
        }else if(imgFg === "007"){
            fileSize = 300;
            element = "fileKioskYellow";
            errMsg = messages["prodImgBarrierFree.fileSizeChk.300.msg"];
        }else if(imgFg === "008"){
            fileSize = 300;
            element = "fileKioskWhite";
            errMsg = messages["prodImgBarrierFree.fileSizeChk.300.msg"];
        }

        // 이미지 파일 여부 체크
        if (isNull($("#" + element)[0].files[0])) {
            $scope._popMsg(messages["prodImgBarrierFree.require.msg"]);
            return;
        }

        // 상품이미지 크기제한 체크
        if (!isNull($("#" + element)[0].files[0])) {
            var maxSize = fileSize * 1024;
            var fileSize = $("#" + element)[0].files[0].size;
            if (fileSize > maxSize) {
                $scope._popMsg(errMsg);
                return;
            }
        }

        // 이미지명 형식 체크
        var imgFullNm = $("#" + element).val().substring($("#" + element).val().lastIndexOf('\\') + 1);
        if(1 > imgFullNm.lastIndexOf('.')){
            $scope._popMsg(messages["prodImgBarrierFree.fileNmChk.msg"]);
            return;
        }

        // 이미지(.png) 확장자 체크
        var reg = /(.*?)\.(png|PNG|jpg|JPG)$/;

        if(! $("#" + element).val().match(reg)) {
            $scope._popMsg(messages["prodImgBarrierFree.msg"]);
            return;
        }


        // 이미지를 등록하시겠습니까?
        var msg = messages["prodImgBarrierFree.fileReg.msg"];
        s_alert.popConf(msg, function () {

            var formData = new FormData($("#regForm")[0]);

            formData.append("orgnFg", orgnFg); // sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 추가, form태그가 원인??
            formData.append("hqOfficeCd", hqOfficeCd);
            formData.append("storeCd", storeCd);
            formData.append("imgFg", imgFg);
            formData.append("prodCd", $("#hdProdCd").val());
            formData.append("imgFgType", element);
            formData.append("userId", userId);

            $scope.$broadcast('loadingPopupActive');

            $.ajax({
                url: "/base/prod/prodImgBarrierFree/prodImgBarrierFree/getProdImgBarrierFreeImageSave.sb",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                cache: false,
                success: function(result) {
                    if (result.status === "OK") {
                        $scope._popMsg("등록되었습니다.");

                        // 재조회
                        $scope.getProdImg($("#hdProdCd").val(), $("#hdProdNm").val());

                        $scope.$broadcast('loadingPopupInactive');
                    }
                    else if (result.status === "FAIL") {
                        var msg = result.status + " : " + result.data.msg;
                        $scope._popMsg(msg);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                    else if (result.status === "SERVER_ERROR") {
                        $scope._popMsg(result.message);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                    /*else if(result.status === undefined) {
                        location.href = "/";
                    }*/
                    else {
                        var msg = result.status + " : " + result.message;
                        $scope._popMsg(msg);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                },
                error : function(result){
                    $scope._popMsg("error");
                    $scope.$broadcast('loadingPopupInactive');
                }
            },function(){
                $scope._popMsg("Ajax Fail By HTTP Request 2");
                $scope.$broadcast('loadingPopupInactive');
            });
        });
    };

    // 이미지 삭제
    $scope.delImg = function(imgFg){

        // 등록한 이미지가 없을때 삭제버튼 클릭 시 동작 안하도록
        if(imgFg === "006"){
            if($("#hdKioskGreenFileNm").val() === ""){
                return;
            }
        }else if(imgFg === "007"){
            if($("#hdKioskYellowFileNm").val() === ""){
                return;
            }
        }else if(imgFg === "008"){
            if($("#hdKioskWhiteFileNm").val() === ""){
                return;
            }
        }

        // 이미지를 삭제하시겠습니까?
        var msg = messages["prodImgBarrierFree.fileDel.msg"];
        s_alert.popConf(msg, function () {

            var formData = new FormData($("#regForm")[0]);

            formData.append("orgnFg", orgnFg); // sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 추가, form태그가 원인??
            formData.append("hqOfficeCd", hqOfficeCd);
            formData.append("storeCd", storeCd);
            formData.append("imgFg", imgFg);
            formData.append("prodCd", $("#hdProdCd").val());
            formData.append("userId", userId);

            $scope.$broadcast('loadingPopupActive');

            $.ajax({
                url: "/base/prod/prodImgBarrierFree/prodImgBarrierFree/getProdImgBarrierFreeImageDelete.sb",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                cache: false,
                success: function(result) {

                    if (result.status === "OK") {
                        // 삭제 되었습니다.
                        $scope._popMsg(messages["cmm.delSucc"]);

                        // 재조회
                        $scope.getProdImg($("#hdProdCd").val(), $("#hdProdNm").val());

                        $scope.$broadcast('loadingPopupInactive');
                    }
                    else if (result.status === "FAIL") {
                        $scope._popMsg('Ajax Fail By HTTP Request');
                        $scope.$broadcast('loadingPopupInactive');
                    }
                    else if (result.status === "SERVER_ERROR") {
                        $scope._popMsg(result.message);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                    /*else if(result.status === undefined) {
                        location.href = "/";
                    }*/
                    else {
                        var msg = result.status + " : " + result.message;
                        $scope._popMsg(msg);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                },
                error : function(result){
                    $scope._popMsg("error");
                    $scope.$broadcast('loadingPopupInactive');
                }
            },function(){
                $scope._popMsg("Ajax Fail By HTTP Request");
                $scope.$broadcast('loadingPopupInactive');
            });
        });
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 이미지복사
    $scope.copy = function (data){
        $scope.wjProdImgBarrierFreeCopyLayer.show(true);
        $scope._broadcast('prodImgBarrierFreeCopyCtrl', data);
    };

    // 이미지전체삭제
    $scope.deleteAll = function (){
        $scope.wjProdImgBarrierFreeDeleteLayer.show(true);
    };

    // 이미지매장적용
    $scope.imgToStoreReg = function() {
        $scope.wjProdImgBarrierFreeStoreRegistLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 쿠폰순서 매장적용 팝업 핸들러 추가
        $scope.wjProdImgBarrierFreeStoreRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('prodImgBarrierFreeStoreRegistCtrl', null);
            }, 50)
        });
    });

}]);