<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : 결제 결과 출력 PAGE                                               = */
    /* = -------------------------------------------------------------------------- = */
    /* =   결제 요청 결과값을 출력하는 페이지입니다.                                = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2020  NHN KCP Inc.   All Rights Reserverd.                = */
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
<%
    request.setCharacterEncoding("euc-kr") ;

    /* ============================================================================== */
    /* =   지불 결과                                                                = */
    /* = -------------------------------------------------------------------------- = */
    String site_cd          = f_get_parm( request.getParameter( "site_cd"        ) );      // 사이트 코드
    String req_tx           = f_get_parm( request.getParameter( "req_tx"         ) );      // 요청 구분(승인/취소)
    String use_pay_method   = f_get_parm( request.getParameter( "use_pay_method" ) );      // 사용 결제 수단
    String bSucc            = f_get_parm( request.getParameter( "bSucc"          ) );      // 업체 DB 정상처리 완료 여부
    /* = -------------------------------------------------------------------------- = */
    String res_cd           = f_get_parm( request.getParameter( "res_cd"         ) );      // 결과 코드
    String res_msg          = f_get_parm( request.getParameter( "res_msg"        ) );      // 결과 메시지
    String res_msg_bsucc    = "";
    /* = -------------------------------------------------------------------------- = */
    String amount           = f_get_parm( request.getParameter( "amount"         ) );      // KCP 실제 거래 금액
    String ordr_idxx        = f_get_parm( request.getParameter( "ordr_idxx"      ) );      // 주문번호
    String tno              = f_get_parm( request.getParameter( "tno"            ) );      // KCP 거래번호
    String good_name        = f_get_parm( request.getParameter( "good_name"      ) );      // 상품명
    String buyr_name        = f_get_parm( request.getParameter( "buyr_name"      ) );      // 구매자명
    String buyr_tel1        = f_get_parm( request.getParameter( "buyr_tel1"      ) );      // 구매자 전화번호
    String buyr_tel2        = f_get_parm( request.getParameter( "buyr_tel2"      ) );      // 구매자 휴대폰번호
    String buyr_mail        = f_get_parm( request.getParameter( "buyr_mail"      ) );      // 구매자 E-Mail
    /* = -------------------------------------------------------------------------- = */
    // 공통
    String pnt_issue        = f_get_parm( request.getParameter( "pnt_issue"      ) );      // 포인트 서비스사
    String app_time         = f_get_parm( request.getParameter( "app_time"       ) );      // 승인시간 (공통)
    /* = -------------------------------------------------------------------------- = */
    // 신용카드
    String card_cd          = f_get_parm( request.getParameter( "card_cd"        ) );      // 카드 코드
    String card_name        = f_get_parm( request.getParameter( "card_name"      ) );      // 카드명
    String noinf            = f_get_parm( request.getParameter( "noinf"          ) );      // 무이자 여부
    String quota            = f_get_parm( request.getParameter( "quota"          ) );      // 할부개월
    String app_no           = f_get_parm( request.getParameter( "app_no"         ) );      // 승인번호
    /* = -------------------------------------------------------------------------- = */
    // 계좌이체
    String bank_name        = f_get_parm( request.getParameter( "bank_name"      ) );      // 은행명
    String bank_code        = f_get_parm( request.getParameter( "bank_code"      ) );      // 은행코드
    /* = -------------------------------------------------------------------------- = */
    // 가상계좌
    String bankname         = f_get_parm( request.getParameter( "bankname"       ) );      // 입금할 은행
    String depositor        = f_get_parm( request.getParameter( "depositor"      ) );      // 입금할 계좌 예금주
    String account          = f_get_parm( request.getParameter( "account"        ) );      // 입금할 계좌 번호
    String va_date          = f_get_parm( request.getParameter( "va_date"        ) );      // 가상계좌 입금마감시간
    /* = -------------------------------------------------------------------------- = */
    // 포인트
    String add_pnt          = f_get_parm( request.getParameter( "add_pnt"        ) );      // 발생 포인트
    String use_pnt          = f_get_parm( request.getParameter( "use_pnt"        ) );      // 사용가능 포인트
    String rsv_pnt          = f_get_parm( request.getParameter( "rsv_pnt"        ) );      // 적립 포인트
    String pnt_app_time     = f_get_parm( request.getParameter( "pnt_app_time"   ) );      // 승인시간
    String pnt_app_no       = f_get_parm( request.getParameter( "pnt_app_no"     ) );      // 승인번호
    String pnt_amount       = f_get_parm( request.getParameter( "pnt_amount"     ) );      // 적립금액 or 사용금액
    /* = -------------------------------------------------------------------------- = */
    //휴대폰
    String commid           = f_get_parm( request.getParameter( "commid"         ) );      // 통신사 코드
    String mobile_no        = f_get_parm( request.getParameter( "mobile_no"      ) );      // 휴대폰 번호
    /* = -------------------------------------------------------------------------- = */
    //상품권
    String tk_van_code      = f_get_parm( request.getParameter( "tk_van_code"    ) );      // 발급사 코드
    String tk_app_no        = f_get_parm( request.getParameter( "tk_app_no"      ) );      // 승인 번호
    /* = -------------------------------------------------------------------------- = */
    // 현금영수증
    String cash_yn          = f_get_parm( request.getParameter( "cash_yn"        ) );      // 현금 영수증 등록 여부
    String cash_authno      = f_get_parm( request.getParameter( "cash_authno"    ) );      // 현금 영수증 승인 번호
    String cash_tr_code     = f_get_parm( request.getParameter( "cash_tr_code"   ) );      // 현금 영수증 발행 구분
    String cash_id_info     = f_get_parm( request.getParameter( "cash_id_info"   ) );      // 현금 영수증 등록 번호
    String cash_no          = f_get_parm( request.getParameter( "cash_no"        ) );      // 현금 영수증 거래 번호    
    /* ============================================================================== */

    String req_tx_name = "";

    if     ( req_tx.equals( "pay" ) ) 
    {
        req_tx_name = "지불" ;
    }
    else if( req_tx.equals( "mod" ) )
    {
        req_tx_name = "취소/매입" ;
    }

    /* ============================================================================== */
    /* =   가맹점 측 DB 처리 실패시 상세 결과 메시지 설정                           = */
    /* = -------------------------------------------------------------------------- = */

    if ( "pay".equals ( req_tx ) )
    {
        // 업체 DB 처리 실패
        if ( "false".equals ( bSucc ) )
        {
            if ( "0000".equals ( res_cd ) )
            {
                res_msg_bsucc = "결제는 정상적으로 이루어졌지만 쇼핑몰에서 결제 결과를 처리하는 중 오류가 발생하여 시스템에서 자동으로 취소 요청을 하였습니다. <br> 쇼핑몰로 전화하여 확인하시기 바랍니다." ;
            }
            else
            {
                res_msg_bsucc = "결제는 정상적으로 이루어졌지만 쇼핑몰에서 결제 결과를 처리하는 중 오류가 발생하여 시스템에서 자동으로 취소 요청을 하였으나, <br> <b>취소가 실패 되었습니다.</b><br> 쇼핑몰로 전화하여 확인하시기 바랍니다." ;
            }
        }
    }

    /* = -------------------------------------------------------------------------- = */
    /* =   가맹점 측 DB 처리 실패시 상세 결과 메시지 설정 끝                        = */
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
  
  <meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
  <meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
  <meta http-equiv="Pragma" content="no-cache"> 
  <meta http-equiv="Expires" content="-1">
  <link href="/resource/solbipos/css/cmm/sms.style.css" rel="stylesheet" type="text/css" id="cssLink"/>
  <script type="text/javascript">
        /* 신용카드 영수증 */ 
        /* 실결제시 : "https://admin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=card_bill&tno=" */ 
        /* 테스트시 : "https://testadmin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=card_bill&tno=" */ 
         function receiptView( tno, ordr_idxx, amount ) 
        {
            receiptWin = "https://testadmin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=card_bill&tno=";
            receiptWin += tno + "&";
            receiptWin += "order_no=" + ordr_idxx + "&"; 
            receiptWin += "trade_mony=" + amount ;

            window.open(receiptWin, "", "width=455, height=815"); 
        }

        /* 현금 영수증 */ 
        /* 실결제시 : "https://admin8.kcp.co.kr/assist/bill.BillActionNew.do" */ 
        /* 테스트시 : "https://testadmin8.kcp.co.kr/assist/bill.BillActionNew.do" */   
        function receiptView2( cash_no, ordr_idxx, amount ) 
        {
            receiptWin2 = "https://testadmin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd=cash_bill&cash_no=";
            receiptWin2 += cash_no + "&";             
            receiptWin2 += "order_id="     + ordr_idxx + "&";
            receiptWin2 += "trade_mony="  + amount ;

            window.open(receiptWin2, "", "width=370, height=625"); 
        }

        /* 가상 계좌 모의입금 페이지 호출 */
        /* 테스트시에만 사용가능 */
        /* 실결제시 해당 스크립트 주석처리 */
        function receiptView3() 
        {
            receiptWin3 = "http://devadmin.kcp.co.kr/Modules/Noti/TEST_Vcnt_Noti.jsp"; 
            window.open(receiptWin3, "", "width=520, height=300"); 
        }
    </script>
</head>

<body>
<div class="wrap">
    <form name="cancel" method="post">
    <!-- header -->
        <div class="header">
            <a href="../index.html" class="btn-back"><span>뒤로가기</span></a>
            <h1 class="title">결과 출력</h1>
        </div>
        <!-- //header -->
        <!-- contents -->
        <div id="skipCont" class="contents">
            <p class="txt-type-1">이 페이지는 결제 결과를 출력하는 샘플(예시) 페이지입니다.</p>
            <p class="txt-type-2">요청 결과를 출력하는 페이지 입니다.<br />요청이 정상적으로 처리된 경우 결과코드(res_cd)값이 0000으로 표시됩니다.</p>
            <h2 class="title-type-3">처리결과</h2>
<%
    /* ============================================================================== */
    /* =   결제 결과 코드 및 메시지 출력(결과페이지에 반드시 출력해주시기 바랍니다.)= */
    /* = -------------------------------------------------------------------------- = */
    /* =   결제 정상 : res_cd값이 0000으로 설정됩니다.                              = */
    /* =   결제 실패 : res_cd값이 0000이외의 값으로 설정됩니다.                     = */
    /* = -------------------------------------------------------------------------- = */
%>
            <ul class="list-type-1">
                <!-- 결과 코드 -->
                <li>
                    <div class="left"><p class="title">결과코드</p></div>
                    <div class="right"><div class="ipt-type-1 pc-wd-2"><%= res_cd %></div></div>
                </li>
                <!-- 결과 코드 -->
                <li>
                    <div class="left"><p class="title">결과메세지</p></div>
                    <div class="right"><div class="ipt-type-1 pc-wd-2"><%= res_msg %></div></div>
                </li>
<%
    // 처리 페이지(pp_cli_hub.jsp)에서 가맹점 DB처리 작업이 실패한 경우 상세메시지를 출력합니다.
    if( !"".equals ( res_msg_bsucc ) )
    {
%>
                <li>
                    <div class="left"><p class="title">결과 상세 메세지</p></div>
                    <div class="right"><div class="ipt-type-1 pc-wd-2"><%= res_msg_bsucc %></div></div>
                </li>              
<%
    }
%>
            </ul>
<%
    /* = -------------------------------------------------------------------------- = */
    /* =   결제 결과 코드 및 메시지 출력 끝                                         = */
    /* ============================================================================== */
%>

<%
    /* ============================================================================== */
    /* =   01. 결제 결과 출력                                                       = */
    /* = -------------------------------------------------------------------------- = */
    if ( "pay".equals ( req_tx ) )
    {
        /* ============================================================================== */
        /* =   01-1. 업체 DB 처리 정상(bSucc값이 false가 아닌 경우)                     = */
        /* = -------------------------------------------------------------------------- = */
        if ( ! "false".equals ( bSucc ) )
        {
            /* ============================================================================== */
            /* =   01-1-1. 정상 결제시 결제 결과 출력 ( res_cd값이 0000인 경우)             = */
            /* = -------------------------------------------------------------------------- = */
            if ( "0000".equals ( res_cd ) )
            {
%>
                <div class="line-type-1"></div>
                <!-- 주문내역 -->
                <h2 class="title-type-3">주문정보</h2>
                <ul class="list-type-1">
                    <!-- 주문번호(ordr_idxx) -->
                    <li>
                        <div class="left"><p class="title">주문번호</p></div>
                        <div class="right"><div class="ipt-type-1 pc-wd-2"><%= ordr_idxx %></div></div>
                    </li>
                    <!-- KCP 거래번호(tno) -->
                    <li>
                        <div class="left"><p class="title">KCP 거래번호</p></div>
                        <div class="right"><div class="ipt-type-1 pc-wd-2"><%= tno %></div></div>
                    </li>
                    <!-- 상품명(good_name) -->
                    <li>
                        <div class="left"><p class="title">상품명</p></div>
                        <div class="right"><%= good_name %></div>
                    </li>
                    <!-- 결제금액(amount) -->
                    <li>
                        <div class="left"><p class="title">결제금액</p></div>
                        <div class="right"><%= amount %>원</div>
                    </li>
                    <!-- 주문자명(buyr_name) -->
                    <li>
                        <div class="left"><p class="title">주문자명</p></div>
                        <div class="right"><%= buyr_name %></div>
                    </li>
                    <!-- 주문자 연락처1(buyr_tel1) -->
                    <li>
                        <div class="left"><p class="title">전화번호</p></div>
                        <div class="right"><%= buyr_tel1 %></div>
                    </li>
                    <!-- 휴대폰번호(buyr_tel2) -->
                    <li>
                        <div class="left"><p class="title">휴대폰번호</p></div>
                        <div class="right"><%= buyr_tel2 %></div>
                    </li>
                    <!-- 주문자 E-mail(buyr_mail) -->
                    <li>
                        <div class="left"><p class="title">E-mail</p></div>
                        <div class="right"><%= buyr_mail %></div>
                    </li>
                </ul>                
                <div class="line-type-1"></div>
            
<%
                   /* ============================================================================== */
                   /* =   신용카드 결제 결과 출력                                             = */
                   /* = -------------------------------------------------------------------------- = */
               if ( use_pay_method.equals("100000000000") )
                {
%>
            
                <div class="line-type-1"></div>
                <!-- 주문내역 -->
                <h2 class="title-type-3">신용카드 정보</h2>
                <ul class="list-type-1">
                    <!-- 결제수단 : 신용카드 -->
                    <li>
                        <div class="left"><p class="title">결제 수단</p></div>
                        <div class="right">신용카드</div>
                    </li>
                    <!-- 결제 카드 -->
                    <li>
                        <div class="left"><p class="title">결제 카드</p></div>
                        <div class="right"><%= card_cd %> / <%= card_name %></div>
                    </li>
                    <!-- 승인시간 -->
                    <li>
                        <div class="left"><p class="title">승인 시간</p></div>
                        <div class="right"><%= app_time %></div>
                    </li>
                    <!-- 승인번호 -->
                    <li>
                        <div class="left"><p class="title">승인 번호</p></div>
                        <div class="right"><%= app_no %></div>
                    </li>
                    <!-- 할부개월 -->
                    <li>
                        <div class="left"><p class="title">할부 개월</p></div>
                        <div class="right"><%= quota %></div>
                    </li>
                    <!-- 무이자 여부 -->
                    <li>
                        <div class="left"><p class="title">무이자 여부</p></div>
                        <div class="right"><%= noinf %></div>
                    </li>
<%                  
                    
                    /* ============================================================================== */
                    /* =   신용카드 영수증 출력                                                     = */
                    /* = -------------------------------------------------------------------------- = */
                    /* =   실제 거래건에 대해서 영수증을 출력할 수 있습니다.                        = */
                    /* = -------------------------------------------------------------------------- = */
%>

                    <li>
                        <div class="left"><p class="title">영수증 확인</p></div>
                        <div class="right"><a href="javascript:receiptView('<%=tno%>','<%= ordr_idxx %>','<%= amount %>')">영수증을 확인합니다.</a></div>
                    </li>
                </ul> 
                <div class="line-type-1"></div>

<%
                    /* = -------------------------------------------------------------- = */
                    /* =   복합결제(포인트+신용카드) 승인 결과 처리  (포인트정보)       = */
                    /* = -------------------------------------------------------------- = */
                     if ( pnt_issue.equals("SCSK") || pnt_issue.equals( "SCWB" ) )
                    {
%>
                    <div class="line-type-1"></div>
                    <h2 class="title-type-3">포인트 정보</h2>
                    <ul class="list-type-1">
                    <!-- 포인트사 -->
                    <li>
                        <div class="left"><p class="title">포인트사</p></div>
                        <div class="right"><%= pnt_issue %></div>
                    </li>
                    <!-- 포인트 승인시간 -->
                    <li>
                        <div class="left"><p class="title">포인트 승인시간</p></div>
                        <div class="right"><%= pnt_app_time %></div>
                    </li>
                    <!-- 포인트 승인번호 -->
                    <li>
                        <div class="left"><p class="title">포인트 승인번호</p></div>
                        <div class="right"><%= pnt_app_no %></div>
                    </li>
                    <!-- 적립금액 or 사용금액 -->
                    <li>
                        <div class="left"><p class="title">적립금액 or 사용금액</p></div>
                        <div class="right"><%= pnt_amount %></div>
                    </li>
                    <!-- 발생 포인트 -->
                    <li>
                        <div class="left"><p class="title">발생 포인트</p></div>
                        <div class="right"><%= add_pnt %></div>
                    </li>
                    <!-- 사용가능 포인트 -->
                    <li>
                        <div class="left"><p class="title">사용가능 포인트</p></div>
                        <div class="right"><%= use_pnt %></div>
                    </li>
                    <!-- 총 누적 포인트 -->
                    <li>
                        <div class="left"><p class="title">총 누적 포인트</p></div>
                        <div class="right"><%= rsv_pnt %></div>
                    </li>
				</ul> 
                <div class="line-type-1"></div>

               
<%                  }
			    }
                /* ============================================================================== */
                /* =   계좌이체 결제 결과 출력                                                  = */
                /* = -------------------------------------------------------------------------- = */
                else if (use_pay_method.equals("010000000000"))       // 계좌이체
                {
%>
                <div class="line-type-1"></div>
                <h2 class="title-type-3">계좌이체 정보</h2>
                <ul class="list-type-1">
                    <!-- 결제수단 : 계좌이체 -->
                    <li>
                        <div class="left"><p class="title">결제 수단</p></div>
                        <div class="right">계좌이체</div>
                    </li>
                    <!-- 이체 은행 -->
                    <li>
                        <div class="left"><p class="title">이체 은행</p></div>
                        <div class="right"><%= bank_name %></div>
                    </li>
                    <!-- 이체 은행코드 -->
                    <li>
                        <div class="left"><p class="title">이체 은행코드</p></div>
                        <div class="right"><%= bank_code %></div>
                    </li>
                    <!-- 승인 시간 -->
                    <li>
                        <div class="left"><p class="title">승인 시간</p></div>
                        <div class="right"><%= app_time %></div>
                    </li>
                </ul>
                <div class="line-type-1"></div>
<%
                }
                /* ============================================================================== */
                /* =   가상계좌 결제 결과 출력                                                  = */
                /* = -------------------------------------------------------------------------- = */
                else if (use_pay_method.equals("001000000000"))
                {
%>
                <div class="line-type-1"></div>
                <h2 class="title-type-3">가상계좌 정보</h2>
                <ul class="list-type-1">
                    <!-- 결제수단 : 가상계좌 -->
                    <li>
                        <div class="left"><p class="title">결제 수단</p></div>
                        <div class="right">가상계좌</div>
                    </li>
                    <!-- 입금은행 -->
                    <li>
                        <div class="left"><p class="title">입금 은행</p></div>
                        <div class="right"><%= bankname %></div>
                    </li>
                    <!-- 입금계좌 예금주 -->
                    <li>
                        <div class="left"><p class="title">입금할 계좌 예금주</p></div>
                        <div class="right"><%= depositor %></div>
                    </li>
                    <!-- 입금계좌 번호 -->
                    <li>
                        <div class="left"><p class="title">입금할 계좌 번호</p></div>
                        <div class="right"><%= account %></div>
                    </li>
                    <!-- 가상계좌 입금마감시간 -->
                    <li>
                        <div class="left"><p class="title">가상계좌 입금마감시간</p></div>
                        <div class="right"><%= va_date %></div>
                    </li>
                    <!-- 가상계좌 모의입금(테스트시) -->
                    <li>
                        <div class="left"><p class="title">가상계좌 모의입금</br>(테스트시 사용)</p></div>
                        <div class="right"><a href="javascript:receiptView3()">모의입금<a/></div>
                    </li>
                </ul>
                <div class="line-type-1"></div>
<%
                }
                /* ============================================================================== */
                /* =   포인트 결제 결과 출력                                                    = */
                /* = -------------------------------------------------------------------------- = */
                else if (use_pay_method.equals("000100000000"))
                {
%>
                <div class="line-type-1"></div>
                <h2 class="title-type-3">포인트 정보</h2>
                <ul class="list-type-1">
                    <!-- 결제수단 : 포인트 -->
                    <li>
                        <div class="left"><p class="title">결제 수단</p></div>
                        <div class="right">포인트</div>
                    </li>
                    <!-- 포인트사 -->
                    <li>
                        <div class="left"><p class="title">포인트사</p></div>
                        <div class="right"><%= pnt_issue %></div>
                    </li>
                    <!-- 포인트 승인시간 -->
                    <li>
                        <div class="left"><p class="title">포인트 승인시간</p></div>
                        <div class="right"><%= pnt_app_time %></div>
                    </li>
                    <!-- 포인트 승인번호 -->
                    <li>
                        <div class="left"><p class="title">포인트 승인번호</p></div>
                        <div class="right"><%= pnt_app_no %></div>
                    </li>
                    <!-- 적립금액 or 사용금액 -->
                    <li>
                        <div class="left"><p class="title">적립금액 or 사용금액</p></div>
                        <div class="right"><%= pnt_amount %></div>
                    </li>
                    <!-- 발생 포인트 -->
                    <li>
                        <div class="left"><p class="title">발생 포인트</p></div>
                        <div class="right"><%= add_pnt %></div>
                    </li>
                    <!-- 사용가능 포인트 -->
                    <li>
                        <div class="left"><p class="title">사용가능 포인트</p></div>
                        <div class="right"><%= use_pnt %></div>
                    </li>
                    <!-- 총 누적 포인트 -->
                    <li>
                        <div class="left"><p class="title">총 누적 포인트</p></div>
                        <div class="right"><%= rsv_pnt %></div>
                    </li>
                </ul>
                <div class="line-type-1"></div>
                                    
<%
                }
                /* ============================================================================== */
                /* =   휴대폰 결제 결과 출력                                                    = */
                /* = -------------------------------------------------------------------------- = */
                else if (use_pay_method.equals("000010000000"))
                {
%>
                <div class="line-type-1"></div>
                <h2 class="title-type-3">휴대폰 정보</h2>
                <ul class="list-type-1">
                    <!-- 결제수단 : 휴대폰 -->
                    <li>
                        <div class="left"><p class="title">결제 수단</p></div>
                        <div class="right">휴대폰</div>
                    </li>
                    <!-- 승인 시간 -->
                    <li>
                        <div class="left"><p class="title">승인 시간</p></div>
                        <div class="right"><%= app_time %></div>
                    </li>
                    <!-- 통신사코드 -->
                    <li>
                        <div class="left"><p class="title">통신사 코드</p></div>
                        <div class="right"><%= commid %></div>
                    </li>
                    <!-- 휴대폰 번호 -->
                    <li>
                        <div class="left"><p class="title">휴대폰 번호</p></div>
                        <div class="right"><%= mobile_no %></div>
                    </li>
                </ul>
                <div class="line-type-1"></div>
                    
<%
                }
                /* ============================================================================== */
                /* =   상품권 결제 결과 출력                                                    = */
                /* = -------------------------------------------------------------------------- = */
                else if (use_pay_method.equals("000000001000"))
                {
%>
                <div class="line-type-1"></div>
                <h2 class="title-type-3">상품권 정보</h2>
                <ul class="list-type-1">
                    <!-- 결제수단 : 상품권 -->
                    <li>
                        <div class="left"><p class="title">결제 수단</p></div>
                        <div class="right">상품권</div>
                    </li>
                    <!-- 발급사 코드 -->
                    <li>
                        <div class="left"><p class="title">발급사 코드</p></div>
                        <div class="right"><%= tk_van_code %></div>
                    </li>
                    <!-- 승인 시간 -->
                    <li>
                        <div class="left"><p class="title">승인 시간</p></div>
                        <div class="right"><%= app_time %></div>
                    </li>
                    <!-- 승인 번호 -->
                    <li>
                        <div class="left"><p class="title">승인 번호</p></div>
                        <div class="right"><%= tk_app_no %></div>
                    </li>
                </ul>
                <div class="line-type-1"></div>
<%
                }
                /* ============================================================================== */
                /* =   현금영수증 정보 출력                                                     = */
                /* = -------------------------------------------------------------------------- = */
                if( !"".equals ( cash_yn ) )
                {
                    if ( "010000000000".equals ( use_pay_method ) | "001000000000".equals ( use_pay_method ) )
                    {
%>
                <!-- 현금영수증 정보 출력-->
                <div class="line-type-1"></div>
                <h2 class="title-type-3">현금영수증 정보</h2>
                <ul class="list-type-1">
                    <!-- 결제수단 : 상품권 -->
                    <li>
                        <div class="left"><p class="title">현금영수증 등록여부</p></div>
                        <div class="right"><%= cash_yn %></div>
                    </li>
<%
                    //현금영수증이 등록된 경우 승인번호 값이 존재
                    if( !"".equals ( cash_authno ) )
                    {
%>
                    <!-- 현금영수증 승인번호 -->
                    <li>
                        <div class="left"><p class="title">현금영수증 승인번호</p></div>
                        <div class="right"><%= cash_authno %></div>
                    </li>
                    <!-- 승인 시간 -->
                    <li>
                        <div class="left"><p class="title">현금영수증 거래번호</p></div>
                        <div class="right"><%= cash_no %></div>
                    </li>
                    <!-- 영수증 확인 -->
                    <li>
                        <div class="left"><p class="title">현금영수증 확인</p></div>
                        <div class="right"><a href="javascript:receiptView2('<%=cash_no%>','<%= ordr_idxx %>','<%= amount %>')">현금영수증을 확인합니다.</a></div>
                    </li>
<%
                    }
%>
                </ul>
                <div class="line-type-1"></div>
                                    
<%
                    }
                }
            }
            /* = -------------------------------------------------------------------------- = */
            /* =   01-1-1. 정상 결제시 결제 결과 출력 END                                   = */
            /* ============================================================================== */
        }
        /* = -------------------------------------------------------------------------- = */
        /* =   01-1. 업체 DB 처리 정상 END                                              = */
        /* ============================================================================== */
    }
    /* = -------------------------------------------------------------------------- = */
    /* =   01. 결제 결과 출력 END                                                   = */
    /* ============================================================================== */
%>
        <div Class="Line-Type-1"></div>
            <ul class="list-btn-2">
                <li><a href="../index.html" class="btn-type-3 pc-wd-2">처음으로</a></li>
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
    </form>
</div>  
</body>
</html>
