/****************************************************************
 *
 * 파일명 : memberRegist.js
 * 설  명 : 회원정보관리 > 회원정보등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

// 회원추가정보-구분
var privateFgComboData = [
    {"name":"선택","value":""},
    {"name":"개인","value":"1"},
    {"name":"단체","value":"2"}
];
// 회원추가정보-외상가능
var postpaidFgComboData = [
    {"name":"선택","value":""},
    {"name":"허용안함","value":"0"},
    {"name":"외상허용","value":"1"}
];
// 단체-구분
var groupTypeFgComboData = [
    {"name":"선택","value":""},
    {"name":"단체","value":"1"},
    {"name":"하키","value":"2"},
    {"name":"기타","value":"3"}
];
// 단체-납부상태
var paymentFgComboData = [
    {"name":"선택","value":""},
    {"name":"미납","value":"0"},
    {"name":"납부완료","value":"1"},
    {"name":"부분납부","value":"2"}
];
// 이용정보-사용기간 구분
var useDateFgComboData = [
    {"name":"직접입력","value":"0"},
    {"name":"1개월","value":"1"},
    {"name":"3개월","value":"2"},
    {"name":"6개월","value":"3"},
    {"name":"12개월","value":"4"}
];

app.controller('memberBasicCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberBasicCtrl', $scope, $http, false));

    // 기본 회원등급
    if (memberClassList.length == 0) {
        memberClassList = [{value: "", name: "전체"}, {value: "001", name: "기본등급"}];
    }

    // 조회조건 콤보박스 데이터
    $scope._getComboDataQuery('055', 'rGendrFg', '');//성별
    $scope._getComboDataQuery('076', 'rWeddingYn', '');//결혼여부
    $scope._getComboDataQuery('067', 'rUseYn', ''); //사용여부
    $scope._getComboDataQuery('072', 'recvYn', ''); //sms수신
    $scope._getComboDataQuery('299', 'rMembrcardYn', ''); //카드발급구분

    /*$scope._setComboData("rGendrFg", genderDataMapEx);*/
    // $scope._setComboData("rWeddingYn", weddingDataMap);
    /*$scope._setComboData("rWeddingYn", weddingList);
    $scope._setComboData("rUseYn", useDataMap);*/
    $scope._setComboData("basicRegStoreCd", regstrStoreList); // 등록매장
    // $scope._setComboData("rMemberClass", memberClassList);
    $scope._setComboData("rMemberClassSelect", memberClassSelect);


    // [1246 광운대아이스링크]
    // 콤보박스 데이터
    $scope._setComboData("membrFgCombo", membrFgComboList); // 회원추가정보-회원구분
    $scope._setComboData("privateFgCombo", privateFgComboData); // 회원추가정보-구분
    $scope._setComboData("postpaidFgCombo", postpaidFgComboData); // 회원추가정보-외상가능
    $scope._setComboData("groupFgCombo", groupFgComboList); // 단체-단체구분
    $scope._setComboData("groupTypeFgCombo", groupTypeFgComboData); // 단체-구분
    $scope._setComboData("paymentFgCombo", paymentFgComboData); // 단체-납부상태
    $scope._setComboData("teacherCdCombo", teacherCdComboList); // 강사,강습구분현황-강사명
    $scope._setComboData("classFgCombo", classFgComboList); // 강사,강습구분현황-강습구분
    $scope._setComboData("skateFgCombo", skateFgComboList); // 강사,강습구분현황-스케이트종류
    $scope._setComboData("useDateFgCombo", useDateFgComboData); // 이용정보-사용기간 구분


    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // 저장/수정인지 파악하기 위해
    $scope.saveMode = "REG";

    /*********************************************************
     * 팝업 오픈
     * 선택된 회원이 없으면 : 신규등록 (폼 리셋)
     * 선택된 회원이 있으면 : 데이터 수정
     * *******************************************************/
    $scope.$on("memberBasicCtrl", function (event, data) {

        $scope.setSelectedMember(data);

        if ($.isEmptyObject(data)) {
            $scope.saveMode = "REG";
            $scope.resetForm();
            $("#trMovePoint").css("display", "")
        } else {
            console.log($scope.member.temp);
            $scope.saveMode = "MOD";
            $scope.getMemberInfo();
            $("#trMovePoint").css("display", "none")
        }

        event.preventDefault();
    });

    /*********************************************************
     * 회원 등록을 위한 폼 리셋
     * *******************************************************/
    $scope.resetForm = function () {

        //$("#regForm")[0].reset();
        // $("#memberInfoTitle").text("");
        //$scope.$apply(function () {
            $scope.member.membrNo = '자동채번';
            $scope.member.beforeBizNo = '';

            // 등록매장 기본셋팅
            // if(orgnFg == "STORE"){
            //     $scope.member.regStoreCd = orgnCd;
            //     if(hqOfficeCd == "00000"){
            //         $scope.member.storeNm = orgnNm;
            //     }else{
            //         $scope.basicRegStoreCdCombo.isReadOnly = true;
            //     }
            // }else{
            //     $scope.basicRegStoreCdCombo.selectedIndex = 0;
            //     $scope.basicRegStoreCdCombo.isReadOnly = false;
            // }

            // 등록매장 기본셋팅
            if ($scope.saveMode === "REG") {
                $scope._setComboData("basicRegStoreCd", regstrStoreList2); // 등록매장
                $scope.basicRegStoreCdCombo.selectedIndex = 0;
                $scope.basicRegStoreCdCombo.isReadOnly = false;
            }
            // 수정
            else if ($scope.saveMode === "MOD") {
                $scope._setComboData("basicRegStoreCd", regstrStoreList); // 등록매장
                $scope.basicRegStoreCdCombo.isReadOnly = true;
            }

            $scope.member.membrNm = '';
            $scope.member.membrNicknm = '';
            $scope.member.membrEngNm = '';
            $scope.rMembrcardYn.selectedIndex = 1;
            $scope.member.membrCardNo = '';
            $scope.member.telNo = '01000000000';
            $scope.member.shortNo = '';
            $scope.genderCombo.selectedIndex = 0;

            // 결혼여부 기본셋팅
            $scope.weddingYnCombo.selectedIndex = 0;
            $("#trWeddingDay").css("display", "none");
            $scope.weddingDayCombo.selectedValue = new Date();
            $scope.weddingDayCombo.refresh();
            $scope.member.weddingDay = new Date();

            // 생일구분 기본셋팅
            $("input:checkbox[id='birthChk']").prop("checked", false);
            $("input:radio[name='lunarYn']:radio[value='N']").prop('checked', true);
            $("input:radio[name='lunarYn']:radio[value='Y']").prop('checked', false);
            $("#divBirthday1").css("display", "none");
            $("#thBirthday2").css("display", "none");
            $("#thBirthday3").css("display", "none");
            $scope.birthdayCombo.selectedValue = new Date();
            $scope.birthdayCombo.refresh();
            $scope.member.birthday = new Date();

            $scope.rMemberClassSelectCombo.selectedIndex = 0;
            $scope.useYnCombo.selectedIndex = 1;
            $scope.member.lnPartner = '';
            $scope.member.cdCompany = '';
            $scope.member.cdPartner = '';
            $scope.member.emailAddr = '';
            $scope.member.postNo = '';
            $scope.member.latitude = '';
            $scope.member.longitude = '';
            $scope.member.addr = '';
            $scope.member.addrDtl = '';
            $scope.emailRecvYnCombo.selectedIndex = 1;
            $scope.smsRecvYnCombo.selectedIndex = 1;
            $scope.member.remark = '';
            $scope.member.phoneNo = '01000000000';
            // 이전포인트 초기값 세팅
            $scope.member.movePoint = 0;

            // [1246 광운대아이스링크]
            if(kwuEnvstVal === "1") {
                // 데이터 바인딩
                $scope.srchMembrFgCombo.selectedIndex = 0;
                $scope.srchPrivateFgCombo.selectedIndex = 0;
                $scope.srchPostpaidFgCombo.selectedIndex = 0;
                $scope.member.peopleCnt = "";
                $scope.srchGroupFgCombo.selectedIndex = 0;
                $scope.srchGroupTypeFgCombo.selectedIndex = 0;
                $scope.srchPaymentFgCombo.selectedIndex = 0;
                $scope.srchTeacherCdCombo.selectedIndex = 0;
                $scope.srchClassFgCombo.selectedIndex = 0;
                $scope.srchSkateFgCombo.selectedIndex = 0;
                $scope.member.registerDate = new Date();
                $scope.member.useDateFg = "0";
                $scope.member.useStartDate = new Date();
                $scope.member.useEndDate = new Date();
                $("input:checkbox[id='useDateChk']").prop("checked", false);
                $("#trUseDate").css("display", "none");
                $("#divUseDate").css("display", "none");
                $("input:checkbox[id='useStartDateChk']").prop("checked", false);
                $("#divUseStartDate").css("display", "none");
                $("input:checkbox[id='useEndDateChk']").prop("checked", false);
                $("#divUseEndDate").css("display", "none");
                $scope.member.useWeek = "";
                $scope.member.useProdNm = "";
                $scope.member.useAmt = "";
                $scope.member.businessAmt = "";
                $scope.member.teacherAmt = "";
                $scope.member.teacherCnt = "";
                $scope.member.useRemainAmt = "";
                $scope.member.transportationAmt = "";
                $scope.member.transportationCnt = "";
            }
        //});
    };

    /*********************************************************
     * 회원정보 조회
     * *******************************************************/
    $scope.getMemberInfo = function () {

        // 기존셋팅 한번 초기화
        $scope.resetForm();
        
        var params = $scope.getSelectedMember();

        $scope._postJSONQuery.withOutPopUp('/membr/info/view/base/getMemberInfo.sb', params, function (response) {

            if ($.isEmptyObject(response.data)) {
                $scope._popMsg(messages["cmm.empty.data"]);
                $scope.memberRegistLayer.hide();
                return false;
            }

            var memberDetailInfo = response.data.data;
            // $scope.saveMode = "MOD";
            $scope.$emit("modMember", $scope.saveMode, memberDetailInfo);

            $("#memberInfoTitle").text("[" + memberDetailInfo.membrNo + "] " + memberDetailInfo.membrNm);

            // 결혼기념일을 입력한 경우
            if(memberDetailInfo.weddingYn === 'Y') {
                $("#trWeddingDay").css("display", "");
                if(memberDetailInfo.weddingday === null || memberDetailInfo.weddingday === "" || memberDetailInfo.weddingday === undefined){
                    memberDetailInfo.weddingday = new Date();
                }else{
                    memberDetailInfo.weddingday = stringToDate(memberDetailInfo.weddingday);
                }
            }else{
                memberDetailInfo.weddingday = new Date();
            }

            // 생일을 입력한 경우
            if(memberDetailInfo.birthday != '' && memberDetailInfo.birthday != null) {
                $("#divBirthday1").css("display", "");
                $("#thBirthday2").css("display", "");
                $("#thBirthday3").css("display", "");

                $("input:checkbox[id='birthChk']").prop("checked", true);
                
                // 날짜 형태 변환
                memberDetailInfo.birthday =  stringToDate(memberDetailInfo.birthday);
            }else{
                memberDetailInfo.lunarYn = 'N';
                memberDetailInfo.birthday =  new Date();
            }

            // 데이터 바인딩
            $scope.member = memberDetailInfo;
            $scope.member.temp = orgnNm;

            console.log($scope.member);

            // [1246 광운대아이스링크]
            if(kwuEnvstVal === "1") {
                $scope.member.registerDate = new Date();
                $scope.member.useStartDate = new Date();
                $scope.member.useEndDate = new Date();
                $("input:checkbox[id='useDateChk']").prop("checked", false);
                $("#trUseDate").css("display", "none");
                $("#divUseDate").css("display", "none");
                $("input:checkbox[id='useStartDateChk']").prop("checked", false);
                $("#divUseStartDate").css("display", "none");
                $("input:checkbox[id='useEndDateChk']").prop("checked", false);
                $("#divUseEndDate").css("display", "none");

                // 광운대아이스링크 추가정보 조회
                $scope.getMemberInfoAddKWU(params);
            }
        });
    };

    // 광운대아이스링크 추가정보 조회
    $scope.getMemberInfoAddKWU = function (params) {
        $scope._postJSONQuery.withOutPopUp('/membr/info/view/base/getMemberInfoAddKwu.sb', params, function (response) {

            if (!$.isEmptyObject(response.data)) {
                var memberDetailInfoAddKWU = response.data.data;

                // 데이터 바인딩
                $scope.member.membrFg = memberDetailInfoAddKWU.membrFg;
                $scope.member.privateFg = memberDetailInfoAddKWU.privateFg;
                $scope.member.postpaidFg = memberDetailInfoAddKWU.postpaidFg;
                $scope.member.peopleCnt = memberDetailInfoAddKWU.peopleCnt;
                $scope.member.groupFg = memberDetailInfoAddKWU.groupFg;
                $scope.member.groupTypeFg = memberDetailInfoAddKWU.groupTypeFg;
                $scope.member.paymentFg = memberDetailInfoAddKWU.paymentFg;
                $scope.member.teacherCd = memberDetailInfoAddKWU.teacherCd;
                $scope.member.classFg = memberDetailInfoAddKWU.classFg;
                $scope.member.skateFg = memberDetailInfoAddKWU.skateFg;
                $scope.member.registerDate = stringToDate(memberDetailInfoAddKWU.registerDate);
                $scope.member.useDateFg = memberDetailInfoAddKWU.useDateFg;
                if(memberDetailInfoAddKWU.useStartDate === "" || memberDetailInfoAddKWU.useStartDate === null) {
                    $scope.member.useStartDate = new Date();
                    $("input:checkbox[id='useStartDateChk']").prop("checked", false);
                    $("#divUseStartDate").css("display", "none");
                } else {
                    $scope.member.useStartDate = stringToDate(memberDetailInfoAddKWU.useStartDate);
                    $("input:checkbox[id='useStartDateChk']").prop("checked", true);
                    $("#divUseStartDate").css("display", "");
                }
                if(memberDetailInfoAddKWU.useEndDate === "" || memberDetailInfoAddKWU.useEndDate === null) {
                    $scope.member.useEndDate = new Date();
                    $("input:checkbox[id='useEndDateChk']").prop("checked", false);
                    $("#divUseEndDate").css("display", "none");
                } else {
                    $scope.member.useEndDate = stringToDate(memberDetailInfoAddKWU.useEndDate);
                    $("input:checkbox[id='useEndDateChk']").prop("checked", true);
                    $("#divUseEndDate").css("display", "");
                }
                if( (memberDetailInfoAddKWU.useStartDate === "" || memberDetailInfoAddKWU.useStartDate === null) && (memberDetailInfoAddKWU.useEndDate === "" || memberDetailInfoAddKWU.useEndDate === null) ) {
                    $("input:checkbox[id='useDateChk']").prop("checked", false);
                    $("#trUseDate").css("display", "none");
                    $("#divUseDate").css("display", "none");
                } else {
                    $("input:checkbox[id='useDateChk']").prop("checked", true);
                    $("#trUseDate").css("display", "");
                    $("#divUseDate").css("display", "");
                }
                $scope.member.useWeek = memberDetailInfoAddKWU.useWeek;
                $scope.member.useProdNm = memberDetailInfoAddKWU.useProdNm;
                $scope.member.useAmt = memberDetailInfoAddKWU.useAmt;
                $scope.member.businessAmt = memberDetailInfoAddKWU.businessAmt;
                $scope.member.teacherAmt = memberDetailInfoAddKWU.teacherAmt;
                $scope.member.teacherCnt = memberDetailInfoAddKWU.teacherCnt;
                $scope.member.useRemainAmt = memberDetailInfoAddKWU.useRemainAmt;
                $scope.member.transportationAmt = memberDetailInfoAddKWU.transportationAmt;
                $scope.member.transportationCnt = memberDetailInfoAddKWU.transportationCnt;
            }
        });
    };

    // 사용기간 구분 선택시 만료일자 변경
    $scope.srchUseDateFgComboChange = function(s) {
        if(s.selectedValue != "") {
            var chgAddMonth = 0;
            var chgAddYear = 0;
            // 1개월
            if(s.selectedValue === "1") {
                chgAddMonth = 1;
            // 3개월
            } else if(s.selectedValue === "2") {
                chgAddMonth = 3;
            // 6개월
            } else if(s.selectedValue === "3") {
                chgAddMonth = 6;
            // 12개월
            } else if(s.selectedValue === "4") {
                chgAddYear = 1;
            }

            if(s.selectedValue !== "0") {
                // 몇달 후 구하기
                var date =  new Date($scope.member.useStartDate); // 시작일자
                var year = new String(date.getFullYear()+chgAddYear);
                var month = new String(date.getMonth()+1+chgAddMonth);
                month = month.length <= 1 ? "0"+month : month;
                var day = new String(date.getDate());
                day = day.length <= 1 ? "0"+day : day;

                $scope.member.useEndDate = stringToDate(year + month + day);
            }
        }
    };
    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {

        // 회원명을 입력하세요.
        // var msg = messages["regist.membr.nm"] + messages["cmm.require.text"];
        // if (isNull($scope.member.membrNm)) {
        //     $scope._popMsg(msg);
        //     return false;
        // }

        // 등록매장을 선택해주세요.
        if (hqOfficeCd !== '00000') {
            var msg = messages["regist.reg.store.cd"] + messages["cmm.require.select"];
            if (isNull($scope.basicRegStoreCdCombo.selectedValue)) {
                $scope._popMsg(msg);
                return false;
            }
        }

        // 회원명 최대길이 체크
        if (nvl($scope.member.membrNm, '') !== '' && nvl($scope.member.membrNm + '', '').getByteLengthForOracle() > 50) {
            msg = messages["regist.membr.nm"] + messages["cmm.overLength"] + " 50 " + ", 현재 : " + $scope.member.membrNm.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 회원명 영문 최대길이 체크
        if (nvl($scope.member.membrEngNm, '') !== '' && nvl($scope.member.membrEngNm + '', '').getByteLengthForOracle() > 50) {
            msg = messages["member.excel.nm.en"] + messages["cmm.overLength"] + " 50 " + ", 현재 : " + $scope.member.membrEngNm.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 단축번호 최대길이 체크
        if (nvl($scope.member.shortNo, '') !== '' && nvl($scope.member.shortNo + '', '').getByteLengthForOracle() > 4) {
            msg = messages["member.excel.shortNo"] + messages["cmm.overLength"] + " 4 ";
            $scope._popMsg(msg);
            return false;
        }

        // 연락처(전화번호) 를 입력하세요.
        var msg = messages["regist.tel"] + messages["cmm.require.text"];
        if (isNull($scope.member.telNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 연락처(전화번호) 정규식
        var msg = messages["regist.tel"] + messages["cmm.require.number"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.member.telNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 전화번호 최대길이 체크
        if (nvl($scope.member.telNo, '') !== '' && nvl($scope.member.telNo + '', '').getByteLengthForOracle() > 14) {
            msg = messages["regist.tel"] + messages["cmm.overLength"] + " 14 " + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // var msg = messages["regist.tel"]+messages["cmm.require.text"];
        // if( isNull( $scope.member.telNo )) {
        //     $scope._popMsg(msg);
        //     return false;
        // }


        // 핸드폰번호를 입력하세요.
        // var msg = messages["regist.phone.no"] + messages["cmm.require.text"];
        // if (isNull($scope.member.phoneNo)) {
        //     $scope._popMsg(msg);
        //     return false;
        // }

        // 핸드폰번호
        // var numChkregexp = /^\d{3}[-]?\d{3,4}[-]?\d{4}$/g;
        // if (!numChkregexp.test($scope.member.phoneNo)) {
        //     console.log($scope.member.phoneNo);
        //     console.log(numChkregexp.test($scope.member.phoneNo));
        //     $scope._popMsg(messages["cmm.not.phoneNo"]);
        //     return false;
        // }

        // 핸드폰번호 최대길이 체크
        // if (nvl($scope.member.phoneNo, '') !== '' && nvl($scope.member.phoneNo + '', '').getByteLengthForOracle() > 14) {
        //     msg = messages["regist.phone.no"] + messages["excelUpload.overLength"] + " 14 " + messages["excelUpload.bateLengthInfo"];
        //     $scope._popMsg(msg);
        //     return false;
        // }

        // 회원카드번호를 입력하세요.
        // var msg = messages["regist.membr.card.no"] + messages["cmm.require.text"];
        // if (isNull($scope.member.membrCardNo)) {
        //     $scope._popMsg(msg);
        //     return false;
        // }
        // 회원카드번호는 숫자만 입력할 수 있습니다.
        var msg = messages["regist.membr.card.no"] + messages["cmm.require.number.en"];
        var numChkregexp = /[^a-zA-Z0-9]/g;
        if(!isNull($scope.member.membrCardNo)) {
            if (numChkregexp.test($scope.member.membrCardNo)) {
                $scope._popMsg(msg);
                return false;
            }
        }

        // 결혼여부를 선택하세요.
        var msg = messages["regist.wedding"] + messages["cmm.require.select"];
        if (isNull($scope.weddingYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 등급코드을 선택하세요.
        var msg = messages["grade.membr.grade.cd"] + messages["cmm.require.select"];
        if (isNull($scope.rMemberClassSelectCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        //  사용여부를 선택하세요.
        var msg = messages["grade.membr.use.yn"] + messages["cmm.require.select"];
        if (isNull($scope.useYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 이메일 최대길이 체크
        if (nvl($scope.member.emailAddr, '') !== '' && nvl($scope.member.emailAddr + '', '').getByteLengthForOracle() > 30) {
            msg = messages["regist.email"] + messages["cmm.overLength"] + " 30 " + ", 현재 : " + $scope.member.emailAddr.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        //  성별을 선택하세요.
        var msg = messages["regist.gender"] + messages["cmm.require.select"];
        if (isNull($scope.genderCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 주소 최대길이 체크
        if (nvl($scope.member.addr, '') !== '' && nvl($scope.member.addr + '', '').getByteLengthForOracle() > 200) {
            msg = messages["regist.addr"] + messages["cmm.overLength"] + " 200 " + ", 현재 : " + $scope.member.addr.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 상세주소 최대길이 체크
        if (nvl($scope.member.addrDtl, '') !== '' && nvl($scope.member.addrDtl + '', '').getByteLengthForOracle() > 200) {
            msg = messages["regist.delivery.addr.dtl"] + messages["cmm.overLength"] + " 200 " + ", 현재 : " + $scope.member.addrDtl.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        //  이메일수신 여부을 선택하세요.
        var msg = messages["regist.email.recv"] + messages["cmm.require.select"];
        if (isNull($scope.emailRecvYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        //  SMS수신 여부를 선택하세요.
        var msg = messages["regist.sms.recv"] + messages["cmm.require.select"];
        if (isNull($scope.smsRecvYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 비고 최대길이 체크
        if (nvl($scope.member.remark, '') !== '' && nvl($scope.member.remark + '', '').getByteLengthForOracle() > 100) {
            msg = messages["regist.remark"] + messages["cmm.overLength"] + " 100 " + ", 현재 : " + $scope.member.remark.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 이전포인트 숫자만
        var msg = messages["regist.membr.move.point"] + messages["cmm.require.number.en"];
        var numChkregexp = /[^0-9]/g;
        if(!isNull($scope.member.movePoint)) {
            if (numChkregexp.test($scope.member.movePoint)) {
                $scope._popMsg(msg);
                return false;
            }
        }
        //
        // if($scope.saveMode === "REG"){
        //   // 회원 거래처 매핑코드를 선택해주세요.
        //   var msg = messages["regist.membr.mappingCd"]+messages["cmm.require.select"];
        //   if( isNull( $scope.member.cdCompany)) {
        //     $scope._popMsg(msg);
        //     return false;
        //   }
        // }

        return true;
    };

    /*********************************************************
     * 저장
     * *******************************************************/
    $scope.save = function () {

        if (!$scope.valueCheck()) return false;

        var params = $scope.member;
        params.postNo  = $("#rPostNo").val();
        params.latitude = $("#rLatitude").val();
        params.longitude = $("#rLongitude").val();
        params.addr  = $("#rAddr").val();
        params.addrDtl  = $("#rAddrDtl").val();

        /*if($scope.member.telNo === ''){
            $scope.member.telNo = '01000000000';
        }*/
        
        // 기혼인 경우만 결혼날짜 입력
        if($scope.weddingYnCombo.selectedIndex === 1) {
            params.weddingday = dateToDaystring($scope.member.weddingday);
        }else{
            params.weddingday = '';
        }
        
        // 생일을 입력한 경우만 생일 입력
        if($("input:checkbox[id='birthChk']").is(":checked")) {
            params.birthday = dateToDaystring($scope.member.birthday);
            params.lunarYn = $(":input:radio[name=lunarYn]:checked").val();
        }else{
            params.birthday = '';
        }
        // 단축번호
        if (params.shortNo === "") {
            if(params.telNo.length < 4) {
                params.shortNo = params.telNo;
            } else {
                params.shortNo = params.telNo.substr(params.telNo.length - 4, 4);
            }
        }
        // 단독매장의 경우
        /*if (hqOfficeCd === '00000') {
            params.regStoreCd = $("#basicRegStoreCd").val();
        }*/
        // console.log(params);

        var memberInfoScope = agrid.getScope('memberCtrl');

        // [1246 광운대아이스링크]
        if(kwuEnvstVal === "1") {
            params.registerDate = dateToDaystring($scope.member.registerDate).replaceAll("-", "");

            if($("input:checkbox[id='useDateChk']").is(":checked")){
                params.useDateFg = $scope.member.useDateFg;

                if($("input:checkbox[id='useStartDateChk']").is(":checked")){
                    params.useStartDate = dateToDaystring($scope.member.useStartDate).replaceAll("-", "");
                }else{
                    params.useStartDate = "";
                }

                if($("input:checkbox[id='useEndDateChk']").is(":checked")){
                    params.useEndDate = dateToDaystring($scope.member.useEndDate).replaceAll("-", "");
                }else{
                    params.useEndDate = "";
                }

            }else{
                params.useDateFg = "0";
                params.useStartDate = "";
                params.useEndDate = "";
            }
        }


        // 회원 신규 등록시
        if ($.isEmptyObject($scope.selectedMember)) {
            if ($scope.saveMode === "REG") {
                $scope._postJSONSave.withPopUp("/membr/info/view/base/registMemberInfo.sb", params, function (result) {
                    var scope = agrid.getScope('memberCtrl');
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // console.log(result.data.data)
                    $scope.$emit("responseGet", result.data.data, $scope.saveMode);
                    $scope.memberRegistLayer.hide();
                    scope.getMemberList();
                    // 데이터 초기화 (수정, 저장 후 다시 팝업 호출 시, 예전 정보가 남아있는 경우가 있어서)
                    $scope.resetForm();

                    // 사용자 행위 기록
                    var actParams = {};
                    actParams.resrceCd = menuCd;
                    actParams.pathNm = "회원관리-회원정보-회원정보관리";
                    actParams.contents = "[신규등록] 버튼 클릭하여 회원정보 등록 시";

                    $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});
                });
            }
        } else {
            // 수정
            if ($scope.saveMode === "MOD") {
                $scope._postJSONSave.withPopUp("/membr/info/view/base/updateMemberInfo.sb", params, function (result) {
                    var scope = agrid.getScope('memberCtrl');
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    $scope.$emit("responseGet", result.data.data, $scope.saveMode);
                    $scope.memberRegistLayer.hide();
                    scope.getMemberList();
                    $scope.$broadcast("memberChgBatchCtrl");
                    // $scope.memberInfoDetailLayer.hide();

                    // 사용자 행위 기록
                    var actParams = {};
                    actParams.resrceCd = menuCd;
                    actParams.pathNm = "회원관리-회원정보-회원정보관리";
                    actParams.contents = "회원명 클릭하여 회원정보 수정 시";

                    $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});
                });
            }
        }

        // 데이터 초기화 (수정, 저장 후 다시 팝업 호출 시, 예전 정보가 남아있는 경우가 있어서)
        // $scope.member = {};
    };

    // 닫기
    $scope.close = function () {
        $scope.member = {};
        $scope.resetForm();
        $scope.memberRegistLayer.hide();
    };

    // form 변화
    $scope.$watch('member', function () {

    }, true);

    // 생일 구분 입력 CheckBox 클릭에 따른 VIEW 변화
    $scope.changeWeddingCombo = function (s, e) {

        if($scope.saveMode === "REG") {
            // 날짜 다시 오늘날짜로 새로 셋팅
            $scope.weddingDayCombo.selectedValue = new Date();
            $scope.weddingDayCombo.refresh();
            $scope.member.weddingday = new Date();
        }

        if (s.selectedValue === 'N') {
            $("#trWeddingDay").css("display", "none");
            $scope.weddingDayCombo.isReadOnly = true;
        } else {
            $("#trWeddingDay").css("display", "");
            $scope.weddingDayCombo.isReadOnly = false;
        }
    };

    // 생일 구분 입력 CheckBox 클릭에 따른 VIEW 변화
    $scope.showBirthday = function () {

        if($scope.saveMode === "REG"){

            // 날짜 다시 오늘날짜로 새로 셋팅
            $scope.birthdayCombo.selectedValue = new Date();
            $scope.birthdayCombo.refresh();
            $scope.member.birthday = new Date();

            if($("input:checkbox[id='birthChk']").is(":checked")){
                $("#divBirthday1").css("display", "");
                $("#thBirthday2").css("display", "");
                $("#thBirthday3").css("display", "");
                $("input:radio[name='lunarYn']:radio[value='N']").prop('checked', true);

            }else{
                $("#divBirthday1").css("display", "none");
                $("#thBirthday2").css("display", "none");
                $("#thBirthday3").css("display", "none");
                $("input:radio[name='lunarYn']:radio[value='N']").prop('checked', false);
                $("input:radio[name='lunarYn']:radio[value='Y']").prop('checked', false);
            }

        }else{

            if($("input:checkbox[id='birthChk']").is(":checked")){
                $("#divBirthday1").css("display", "");
                $("#thBirthday2").css("display", "");
                $("#thBirthday3").css("display", "");
            }else{
                $("#divBirthday1").css("display", "none");
                $("#thBirthday2").css("display", "none");
                $("#thBirthday3").css("display", "none");
            }
        }
    }


    /*********************************************************
     * 회원 거래처 매핑코드 조회(보나비)
     * *******************************************************/
    $scope.searchMemberMappingCd = function(){
        $scope.memberMappingLayer.show(true, function(s) {

            var memberMappingScope = agrid.getScope('memberMappingCtrl');
            // console.log('getCompany', memberMappingScope.getCompany());
            $scope.$apply(function(){
                if( !$.isEmptyObject(memberMappingScope.getCompany())) {
                    $scope.member.lnPartner = memberMappingScope.getCompany().lnPartner;
                    $scope.member.cdCompany = memberMappingScope.getCompany().cdCompany;
                    $scope.member.cdPartner = memberMappingScope.getCompany().cdPartner;
                }
            });
        });
    };

    /*********************************************************
     * 주소검색 TODO
     * *******************************************************/
    // $scope.searchAddr = function(){
    // };

    // 화면 ready 된 후 설정
    // angular.element(document).ready(function () {
    //     // 회원조회 팝업 핸들러 추가
    //     $scope.memberMappingLayer.shown.addHandler(function (s) {
    //         setTimeout(function() {
    //             // $scope._broadcast('memberMappingCtrl');
    //         }, 50)
    //     });
    // });

    // 지도보기 팝업
    $scope.openMap = function () {

        // 위도,경도 또는 주소가 있는지 체크
        if($("#rLatitude").val() === "" || $("#rLongitude").val() === "") {
          if($("#rAddr").val() === ""){
            $scope._popMsg(messages["regist.membr.mapOpen.msg"]); // 정확한 주소가 없어 지도를 조회할 수 없습니다.
            return;
          }
        }

        var params = {};
        params.title = messages["regist.membr.openMap"];              // 지도 팝업 title
        params.markerNm = $("#rMembrNm").val() === "" ? messages["regist.addr"] + messages["regist.membr.location"] : $("#rMembrNm").val();   // 지도 위치 마커명
        params.addr = $("#rAddr").val();                              // 주소
        params.latitude = $("#rLatitude").val();                      // 위도
        params.longitude = $("#rLongitude").val();                    // 경도

        $scope.mapPopLayer.show(true);
        $scope._broadcast('mapPopCtrl', params);

    };

    // 시작일자, 만료일자 입력 CheckBox 클릭에 따른 VIEW 변화
    $scope.showUseDate = function () {
        if($("input:checkbox[id='useDateChk']").is(":checked")){
            $("#trUseDate").css("display", "");
            $("#divUseDate").css("display", "");
            // 신규
            if ($scope.saveMode === "REG") {
                $("input:checkbox[id='useStartDateChk']").prop("checked", true);
                $("#divUseStartDate").css("display", "");
                $("input:checkbox[id='useEndDateChk']").prop("checked", true);
                $("#divUseEndDate").css("display", "");
            }
        }else{
            $("#trUseDate").css("display", "none");
            $("#divUseDate").css("display", "none");
        }
    };

    // 시작일자 입력 CheckBox 클릭에 따른 VIEW 변화
    $scope.showUseStartDate = function () {
        if($("input:checkbox[id='useStartDateChk']").is(":checked")){
            $("#divUseStartDate").css("display", "");
        }else{
            $("#divUseStartDate").css("display", "none");
        }
    };

    // 만료일자 입력 CheckBox 클릭에 따른 VIEW 변화
    $scope.showUseEndDate = function () {
        if($("input:checkbox[id='useEndDateChk']").is(":checked")){
            $("#divUseEndDate").css("display", "");
        }else{
            $("#divUseEndDate").css("display", "none");
        }
    };

}]);
