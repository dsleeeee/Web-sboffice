<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<%
    /* ============================================================================== */
    /* =   PAGE : 결제 요청 PAGE                                                    = */
    /* = -------------------------------------------------------------------------- = */
    /* =   이 페이지는 표준웹을 통해서 결제자가 결제 요청을 하는 페이지             = */
    /* =   입니다. 아래의 ※ 필수, ※ 옵션 부분과 매뉴얼을 참조하셔서 연동을        = */
    /* =   진행하여 주시기 바랍니다.                                                = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2020  NHN KCP Inc.   All Rights Reserverd.                = */
    /* ============================================================================== */
%>
<%
    /* ============================================================================== */
    /* =   환경 설정 파일 Include                                                   = */
    /* = -------------------------------------------------------------------------- = */
    /* =   ※ 필수                                                                  = */
    /* =   테스트 및 실결제 연동시 site_conf_inc.jsp 파일을 수정하시기 바랍니다.    = */
    /* = -------------------------------------------------------------------------- = */
%>
<%@ include file="../cfg/site_conf_inc.jsp"%>
<%
    request.setCharacterEncoding ( "euc-kr" ) ;
    /* = -------------------------------------------------------------------------- = */
    /* =   환경 설정 파일 Include END                                               = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null 값을 처리하는 메소드                                                = */
    /* = -------------------------------------------------------------------------- = */
    public String f_get_parm( String val )
    {
        if ( val == null ) val = "";
        return  val;
    }
    /* ============================================================================== */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>가맹점 결제 샘플페이지</title>

    <!-- 공통: font preload -->
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Regular.woff" type="font/woff" as="font" crossorigin>
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Medium.woff" type="font/woff" as="font" crossorigin>
    <link rel="preload" href="https://cdn.kcp.co.kr/font/NotoSansCJKkr-Bold.woff" type="font/woff" as="font" crossorigin>
    <!-- //공통: font preload -->

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link href="/resource/solbipos/css/cmm/sms.style.css" rel="stylesheet" type="text/css" id="cssLink"/>

    <script type="text/javascript">
        /****************************************************************/
        /* m_Completepayment  설명                                      */
        /****************************************************************/
        /* 인증완료시 재귀 함수                                         */
        /* 해당 함수명은 절대 변경하면 안됩니다.                        */
        /* 해당 함수의 위치는 payplus.js 보다먼저 선언되어여 합니다.    */
        /* Web 방식의 경우 리턴 값이 form 으로 넘어옴                   */
        /****************************************************************/
        function m_Completepayment( FormOrJson, closeEvent )
        {
            var frm = document.order_info;

            /********************************************************************/
            /* FormOrJson은 가맹점 임의 활용 금지                               */
            /* frm 값에 FormOrJson 값이 설정 됨 frm 값으로 활용 하셔야 됩니다.  */
            /* FormOrJson 값을 활용 하시려면 기술지원팀으로 문의바랍니다.       */
            /********************************************************************/
            GetField( frm, FormOrJson );


            if( frm.res_cd.value == "0000" )
            {
                alert("결제 승인 요청 전,\n\n반드시 결제창에서 고객님이 결제 인증 완료 후\n\n리턴 받은 ordr_chk 와 업체 측 주문정보를\n\n다시 한번 검증 후 결제 승인 요청하시기 바랍니다."); //업체 연동 시 필수 확인 사항.
                /*
                    가맹점 리턴값 처리 영역
                */

                frm.submit();
            }
            else
            {
                alert( "[" + frm.res_cd.value + "] " + frm.res_msg.value );

                closeEvent();
            }
        }
    </script>

    <%
        /* ============================================================================== */
        /* =   Javascript source Include                                                = */
        /* = -------------------------------------------------------------------------- = */
        /* =   ※ 필수                                                                  = */
        /* =   테스트 및 실결제 연동시 site_conf_inc.jsp파일을 수정하시기 바랍니다.     = */
        /* = -------------------------------------------------------------------------- = */
    %>
    <script type="text/javascript" src="<%= g_conf_js_url %>"></script>
    <%
        /* = -------------------------------------------------------------------------- = */
        /* =   Javascript source Include END                                            = */
        /* ============================================================================== */
    %>
    <script type="text/javascript">


        function pay()
        {
            var frm = document.order_info;

            if (frm.pay_method[0].checked)
            {
                frm.pay_method.value="100000000000"; //신용카드
            }

            else if (frm.pay_method[1].checked)
            {
                frm.pay_method.value="010000000000"; //계좌이체
            }

            else if (frm.pay_method[2].checked)
            {
                frm.pay_method.value="001000000000"; //가상계좌
            }

            else if (frm.pay_method[3].checked)
            {
                frm.pay_method.value="000010000000"; //휴대폰
            }

            else if (frm.pay_method[4].checked)
            {
                frm.pay_method.value="000100000000"; //포인트
            }

            else if (frm.pay_method[5].checked)
            {
                frm.pay_method.value="000000001000"; //상품권
            }

            else if (frm.pay_method[6].checked)
            {
                frm.pay_method.value="111000000000"; //신용카드/계좌이체/가상계좌
            }

        }
        /* 표준웹 실행 */
        function jsf__pay( form )
        {
            pay();

            try
            {
                KCP_Pay_Execute( form );
            }
            catch (e)
            {
                /* IE 에서 결제 정상종료시 throw로 스크립트 종료 */
            }

        }

        /* 주문번호 생성 예제 */
        function init_orderid()
        {
            var today = new Date();
            var year  = today.getFullYear();
            var month = today.getMonth() + 1;
            var date  = today.getDate();
            var time  = today.getTime();

            if(parseInt(month) < 10) {
                month = "0" + month;
            }

            if(parseInt(date) < 10) {
                date = "0" + date;
            }

            var order_idxx = "TEST" + year + "" + month + "" + date + "" + time;

            document.order_info.ordr_idxx.value = order_idxx;
        }

    </script>
</head>

<body onload="init_orderid();">

<div class="wrap">

    <!-- 주문정보 입력 form : order_info -->
    <form name="order_info" method="post" action="pp_cli_hub.jsp" >

        <%
            /* ============================================================================== */
            /* =   1. 주문 정보 입력                                                        = */
            /* = -------------------------------------------------------------------------- = */
            /* =   결제에 필요한 주문 정보를 입력 및 설정합니다.                            = */
            /* = -------------------------------------------------------------------------- = */
        %>
        <!-- header -->
        <div class="header">
            <a href="../index.html" class="btn-back"><span>뒤로가기</span></a>
            <h1 class="title">주문/결제 SAMPLE</h1>
        </div>
        <!-- //header -->
        <!-- contents -->
        <div id="skipCont" class="contents">
            <p class="txt-type-1">이 페이지는 결제를 요청하는 샘플 페이지입니다.</p>
            <p class="txt-type-2">소스 수정 시 [※ 필수] 또는 [※ 옵션] 표시가 포함된 문장은 가맹점의 상황에 맞게 적절히 수정 적용하시기 바랍니다.</p>
            <!-- 주문내역 -->
            <h2 class="title-type-3">주문내역</h2>
            <ul class="list-type-1">
                <!-- 주문번호(ordr_idxx) -->
                <li>
                    <div class="left"><p class="title">주문번호</p></div>
                    <div class="right">
                        <div class="ipt-type-1 pc-wd-2">
                            <input type="text" name="ordr_idxx" value="" maxlength="40" />
                            <a href="#none" class="btn-clear"></a>
                        </div>
                    </div>
                </li>
                <!-- 상품명(good_name) -->
                <li>
                    <div class="left"><p class="title">상품명</p></div>
                    <div class="right">
                        <div class="ipt-type-1 pc-wd-2">
                            <input type="text" name="good_name" value="운동화" />
                            <a href="#none" class="btn-clear"></a>
                        </div>
                    </div>
                </li>
                <!-- 결제금액(good_mny) - ※ 필수 : 값 설정시 ,(콤마)를 제외한 숫자만 입력하여 주십시오. -->
                <li>
                    <div class="left"><p class="title">상품금액</p></div>
                    <div class="right">
                        <div class="ipt-type-1 gap-2 pc-wd-2">
                            <input type="text" name="good_mny" value="1004" maxlength="9" />
                            <a href="#none" class="btn-clear"></a>
                            <span class="txt-price">원</span>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="line-type-1"></div>
            <!-- 주문정보 -->
            <h2 class="title-type-3">주문정보</h2>
            <ul class="list-type-1">
                <!-- 주문자명(buyr_name) -->
                <li>
                    <div class="left"><p class="title">주문자명</p></div>
                    <div class="right">
                        <div class="ipt-type-1 pc-wd-2">
                            <input type="text" name="buyr_name" value="홍길동" />
                            <a href="#none" class="btn-clear"></a>
                        </div>
                    </div>
                </li>
                <!-- 주문자 연락처1(buyr_tel1) -->
                <li>
                    <div class="left"><p class="title">전화번호</p></div>
                    <div class="right">
                        <div class="ipt-type-1 pc-wd-2">
                            <input type="text" name="buyr_tel1" value="02-0000-0000" />
                            <a href="#none" class="btn-clear"></a>
                        </div>
                    </div>
                </li>
                <!-- 휴대폰번호(buyr_tel2) -->
                <li>
                    <div class="left"><p class="title">휴대폰번호</p></div>
                    <div class="right">
                        <div class="ipt-type-1 pc-wd-2">
                            <input type="text" name="buyr_tel2" value="010-0000-0000" />
                            <a href="#none" class="btn-clear"></a>
                        </div>
                    </div>
                </li>
                <!-- 주문자 E-mail(buyr_mail) -->
                <li>
                    <div class="left"><p class="title">이메일</p></div>
                    <div class="right">
                        <div class="ipt-type-1 pc-wd-2">
                            <input type="text" name="buyr_mail" value="test@test.co.kr" />
                            <a href="#none" class="btn-clear"></a>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="line-type-1"></div>
            <%
                /* ============================================================================== */
                /* =   결제 수단 정보 설정                                                 = */
                /* = -------------------------------------------------------------------------- = */
                /* =   결제에 필요한 결제 수단 정보를 설정합니다.                               = */
                /* =                                                                            = */
                /* =  신용카드 : 100000000000, 계좌이체 : 010000000000, 가상계좌 : 001000000000 = */
                /* =  포인트   : 000100000000, 휴대폰   : 000010000000, 상품권   : 000000001000 = */
                /* =                                                                            = */
                /* =  위와 같이 설정한 경우 표준웹에서 설정한 결제수단이 표시됩니다.            = */
                /* =  표준웹에서 여러 결제수단을 표시하고 싶으신 경우 설정하시려는 결제         = */
                /* =  수단에 해당하는 위치에 해당하는 값을 1로 변경하여 주십시오.               = */
                /* =                                                                            = */
                /* =  예) 신용카드, 계좌이체, 가상계좌를 동시에 표시하고자 하는 경우            = */
                /* =  pay_method = "111000000000"                                               = */
                /* =  신용카드(100000000000), 계좌이체(010000000000), 가상계좌(001000000000)에  = */
                /* =  해당하는 값을 모두 더해주면 됩니다.                                       = */
                /* =                                                                            = */
                /* = ※ 필수                                                                    = */
                /* =  KCP에 신청된 결제수단으로만 결제가 가능합니다.                            = */
                /* = -------------------------------------------------------------------------- = */
            %>
            <h2 class="title-type-3">결제수단</h2>
            <ul class="list-check-1">
                <li>
                    <input type="radio" id="radio-2-1" class="ipt-radio-1" name="pay_method" value="100000000000" checked />
                    <label for="radio-2-1"><span class="ico-radio"><span></span></span>신용카드</label>
                </li>
                <li>
                    <input type="radio" id="radio-2-2" class="ipt-radio-1" name="pay_method" value="010000000000" />
                    <label for="radio-2-2"><span class="ico-radio"><span></span></span>계좌이체</label>
                </li>
                <li>
                    <input type="radio" id="radio-2-3" class="ipt-radio-1" name="pay_method" value="001000000000" />
                    <label for="radio-2-3"><span class="ico-radio"><span></span></span>가상계좌</label>
                </li>
                <li>
                    <input type="radio" id="radio-2-4" class="ipt-radio-1" name="pay_method" value="000010000000" />
                    <label for="radio-2-4"><span class="ico-radio"><span></span></span>휴대폰</label>
                </li>
                <li>
                    <input type="radio" id="radio-2-5" class="ipt-radio-1" name="pay_method" value="000100000000" />
                    <label for="radio-2-5"><span class="ico-radio"><span></span></span>포인트</label>
                </li>
                <li>
                    <input type="radio" id="radio-2-6" class="ipt-radio-1" name="pay_method" value="000000001000" />
                    <label for="radio-2-6"><span class="ico-radio"><span></span></span>상품권</label>
                </li>
                <li>
                    <input type="radio" id="radio-2-8" class="ipt-radio-1" name="pay_method" value="111000000000" />
                    <label for="radio-2-8"><span class="ico-radio"><span></span></span>신용카드/계좌이체/가상계좌</label>
                </li>
            </ul>
            <div Class="Line-Type-1"></div>
            <ul class="list-btn-2">
                <!-- D : 버튼 비활성시 a태그에 class disable 추가 -->
                <li class="pc-only-show"><a href="../index.html" class="btn-type-3 pc-wd-2">뒤로</a></li>
                <li><a href="#none" onclick="jsf__pay(document.order_info);" class="btn-type-2 pc-wd-3">결제요청</a></li>
            </ul>
        </div>
        <!-- //contents -->

        <div class="grid-footer">
            <div class="inner">
                <!-- footer -->
                <div class="footer">
                    ⓒ NHN KCP Corp.
                </div>
                <!-- //footer -->
            </div>
        </div>

        <!-- 공통: js -->
        <script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>
        <script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/front.js"></script>
        <!-- //공통: js -->



        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   1. 주문 정보 입력 END                                                    = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   2. 가맹점 필수 정보 설정                                                 = */
            /* = -------------------------------------------------------------------------- = */
            /* =   ※ 필수 - 결제에 반드시 필요한 정보입니다.                               = */
            /* =   site_conf_inc.jsp 파일을 참고하셔서 수정하시기 바랍니다.                 = */
            /* = -------------------------------------------------------------------------- = */
            // 요청종류 : 승인(pay)/취소,매입(mod) 요청시 사용
        %>
        <input type="hidden" name="req_tx"          value="pay" />
        <input type="hidden" name="site_cd"         value="<%= g_conf_site_cd   %>" />
        <input type="hidden" name="site_name"       value="<%= g_conf_site_name %>" />
        <input type="hidden" name="pay_method" value=""> <!-- 결제 수단 -->
        <%
            /*
               할부옵션 : 표준웹에서 카드결제시 최대로 표시할 할부개월 수를 설정합니다.(0 ~ 18 까지 설정 가능)
               ※ 주의  - 할부 선택은 결제금액이 50,000원 이상일 경우에만 가능, 50000원 미만의 금액은 일시불로만 표기됩니다
                          예) value 값을 "5" 로 설정했을 경우 => 카드결제시 결제창에 일시불부터 5개월까지 선택가능
            */
        %>
        <input type="hidden" name="quotaopt"        value="12"/>
        <!-- 필수 항목 : 결제 금액/화폐단위 -->
        <input type="hidden" name="currency"        value="WON"/>
        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   2. 가맹점 필수 정보 설정 END                                             = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   3. 표준웹 필수 정보(변경 불가)                                   = */
            /* = -------------------------------------------------------------------------- = */
            /* =   결제에 필요한 주문 정보를 입력 및 설정합니다.                            = */
            /* = -------------------------------------------------------------------------- = */
        %>
        <!-- 표준웹 설정 정보입니다(변경 불가) -->
        <input type="hidden" name="module_type"     value="<%= module_type %>"/>
        <!--
              ※ 필 수
                  필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다
                  값을 설정하지 마십시오
        -->
        <input type="hidden" name="res_cd"          value=""/>
        <input type="hidden" name="res_msg"         value=""/>
        <input type="hidden" name="enc_info"        value=""/>
        <input type="hidden" name="enc_data"        value=""/>
        <input type="hidden" name="ret_pay_method"  value=""/>
        <input type="hidden" name="tran_cd"         value=""/>
        <input type="hidden" name="use_pay_method"  value=""/>

        <!-- 주문정보 검증 관련 정보 : 표준웹 에서 설정하는 정보입니다 -->
        <input type="hidden" name="ordr_chk"        value=""/>

        <!--  현금영수증 관련 정보 : 표준웹 에서 설정하는 정보입니다 -->
        <input type="hidden" name="cash_yn"         value=""/>
        <input type="hidden" name="cash_tr_code"    value=""/>
        <input type="hidden" name="cash_id_info"    value=""/>

        <!-- 2012년 8월 18일 전자상거래법 개정 관련 설정 부분 -->
        <!-- 제공 기간 설정 0:일회성 1:기간설정(ex 1:2012010120120131)  -->
        <input type="hidden" name="good_expr" value="0">

        <%
            /* = -------------------------------------------------------------------------- = */
            /* =   3. 표준웹 필수 정보 END                                          = */
            /* ============================================================================== */
        %>

        <%
            /* ============================================================================== */
            /* =   4. 옵션 정보                                                             = */
            /* = -------------------------------------------------------------------------- = */
            /* =   ※ 옵션 - 결제에 필요한 추가 옵션 정보를 입력 및 설정합니다.             = */
            /* = -------------------------------------------------------------------------- = */

    /* 사용카드 설정 여부 파라미터 입니다.(통합결제창 노출 유무)
    <input type="hidden" name="used_card_YN"        value="Y"/> */
    /* 사용카드 설정 파라미터 입니다. (해당 카드만 결제창에 보이게 설정하는 파라미터입니다. used_card_YN 값이 Y일때 적용됩니다.
    /<input type="hidden" name="used_card"        value="CCBC:CCKM:CCSS"/> */

    /* 신용카드 결제시 OK캐쉬백 적립 여부를 묻는 창을 설정하는 파라미터 입니다
         포인트 가맹점의 경우에만 창이 보여집니다
        <input type="hidden" name="save_ocb"        value="Y"/> */

    /* 고정 할부 개월 수 선택
        value값을 "7" 로 설정했을 경우 => 카드결제시 결제창에 할부 7개월만 선택가능
    <input type="hidden" name="fix_inst"        value="07"/> */

    /*  무이자 옵션
            ※ 설정할부    (가맹점 관리자 페이지에 설정 된 무이자 설정을 따른다)                             - "" 로 설정
            ※ 일반할부    (KCP 이벤트 이외에 설정 된 모든 무이자 설정을 무시한다)                           - "N" 로 설정
            ※ 무이자 할부 (가맹점 관리자 페이지에 설정 된 무이자 이벤트 중 원하는 무이자 설정을 세팅한다)   - "Y" 로 설정
    <input type="hidden" name="kcp_noint"       value=""/> */

    /*  무이자 설정
            ※ 주의 1 : 할부는 결제금액이 50,000 원 이상일 경우에만 가능
            ※ 주의 2 : 무이자 설정값은 무이자 옵션이 Y일 경우에만 결제 창에 적용
            예) BC 2,3,6개월, 국민 3,6개월, 삼성 6,9개월 무이자 : CCBC-02:03:06,CCKM-03:06,CCSS-03:06:04
    <input type="hidden" name="kcp_noint_quota" value="CCBC-02:03:06,CCKM-03:06,CCSS-03:06:09"/> */


    /* 해외카드 구분하는 파라미터 입니다.(해외비자, 해외마스터, 해외JCB로 구분하여 표시)
    <input type="hidden" name="used_card_CCXX"        value="Y"/> */

    /*  가상계좌 은행 선택 파라미터
         ※ 해당 은행을 결제창에서 보이게 합니다.(은행코드는 매뉴얼을 참조)
    <input type="hidden" name="wish_vbank_list" value="05:03:04:07:11:23:26:32:34:81:71"/> */

    /*  가상계좌 입금 기한 설정하는 파라미터 - 발급일 + 3일
    <input type="hidden" name="vcnt_expire_term" value="3"/> */

    /*  가상계좌 입금 시간 설정하는 파라미터
         HHMMSS형식으로 입력하시기 바랍니다
         설정을 안하시는경우 기본적으로 23시59분59초가 세팅이 됩니다
         <input type="hidden" name="vcnt_expire_term_time" value="120000"/> */

    /* 포인트 결제시 복합 결제(신용카드+포인트) 여부를 결정할 수 있습니다.- N 일경우 복합결제 사용안함
        <input type="hidden" name="complex_pnt_yn" value="N"/>    */

    /* 현금영수증 등록 창을 출력 여부를 설정하는 파라미터 입니다
         ※ Y : 현금영수증 등록 창 출력
         ※ N : 현금영수증 등록 창 출력 안함 
    ※ 주의 : 현금영수증 사용 시 KCP 상점관리자 페이지에서 현금영수증 사용 동의를 하셔야 합니다
        <input type="hidden" name="disp_tax_yn"     value="Y"/> */

    /* 결제창에 가맹점 사이트의 로고를 표준웹 좌측 상단에 출력하는 파라미터 입니다
       업체의 로고가 있는 URL을 정확히 입력하셔야 하며, 최대 150 X 50  미만 크기 지원

    ※ 주의 : 로고 용량이 150 X 50 이상일 경우 site_name 값이 표시됩니다.
        <input type="hidden" name="site_logo"       value="" /> */

    /* 결제창 영문 표시 파라미터 입니다. 영문을 기본으로 사용하시려면 Y로 세팅하시기 바랍니다
        2010-06월 현재 신용카드와 가상계좌만 지원됩니다
        <input type="hidden" name="eng_flag"      value="Y"> */

    /* KCP는 과세상품과 비과세상품을 동시에 판매하는 업체들의 결제관리에 대한 편의성을 제공해드리고자, 
        복합과세 전용 사이트코드를 지원해 드리며 총 금액에 대해 복합과세 처리가 가능하도록 제공하고 있습니다
        복합과세 전용 사이트 코드로 계약하신 가맹점에만 해당이 됩니다
        상품별이 아니라 금액으로 구분하여 요청하셔야 합니다
        총결제 금액은 과세금액 + 부과세 + 비과세금액의 합과 같아야 합니다. 
        (good_mny = comm_tax_mny + comm_vat_mny + comm_free_mny)
        
        <input type="hidden" name="tax_flag"       value="TG03">  <!-- 변경불가    -->
        <input type="hidden" name="comm_tax_mny"   value=""    >  <!-- 과세금액    --> 
        <input type="hidden" name="comm_vat_mny"   value=""    >  <!-- 부가세     -->
        <input type="hidden" name="comm_free_mny"  value=""    >  <!-- 비과세 금액 --> */

    /* skin_indx 값은 스킨을 변경할 수 있는 파라미터이며 총 7가지가 지원됩니다. 
        변경을 원하시면 1부터 7까지 값을 넣어주시기 바랍니다. 

        <input type="hidden" name="skin_indx"      value="1"> */

    /* 상품코드 설정 파라미터 입니다.(상품권을 따로 구분하여 처리할 수 있는 옵션기능입니다.)
        <input type="hidden" name="good_cd"      value=""> */
        
    /* 가맹점에서 관리하는 고객 아이디 설정을 해야 합니다. 상품권 결제 시 반드시 입력하시기 바랍니다.
        <input type="hidden" name="shop_user_id"    value=""/> */
        
    /* 복지포인트 결제시 가맹점에 할당되어진 코드 값을 입력해야합니다.
        <input type="hidden" name="pt_memcorp_cd"   value=""/> */

    /* 결제창의 상단문구를 변경할 수 있는 파라미터 입니다.
       <input type="hidden" name="kcp_pay_title"   value="상단문구추가"/> */
            /* = -------------------------------------------------------------------------- = */
            /* =   4. 옵션 정보 END                                                         = */
            /* ============================================================================== */
        %>
    </form>
</div>
<!--//wrap-->
</body>
</html>