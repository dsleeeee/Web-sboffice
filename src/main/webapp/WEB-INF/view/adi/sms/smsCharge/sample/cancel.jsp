<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<%
    String goCancel = "";
//    goCancel = "alert(1);";
%>

<%
    /* ============================================================================== */
    /* =   PAGE : 지불 요청 및 결과 처리 PAGE                                       = */
    /* = -------------------------------------------------------------------------- = */
    /* =   연동시 오류가 발생하는 경우 아래의 주소로 접속하셔서 확인하시기 바랍니다.= */
    /* =   접속 주소 : http://kcp.co.kr/technique.requestcode.do                    = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2021   NHN KCP Inc.   All Rights Reserverd.               = */
    /* ============================================================================== */

    /* ============================================================================== */
    /* =   POST 형식 체크부분                                                       = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("결제취소 >>> 01.cancel.jsp 호출 >>> request.getMethod() : " + request.getMethod());

    if ( !request.getMethod().equals("POST") )
    {
        out.println("잘못된 경로로 접속하였습니다.");
        return;
    }

    /* ============================================================================== */
%>
<%
    /* ============================================================================== */
    /* =   환경 설정 파일 Include                                                   = */
    /* = -------------------------------------------------------------------------- = */
    /* =   ※ 필수                                                                  = */
    /* =   테스트 및 실결제 연동시 site_conf_inc.jsp파일을 수정하시기 바랍니다.     = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*" %>
    <%@ page import="java.net.URLEncoder"%>
    <%@ include file = "../cfg/site_conf_inc.jsp"%>
<%
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
<%
    request.setCharacterEncoding ( "euc-kr" ) ;
    /* ============================================================================== */
    /* =   02. 지불 요청 정보 설정                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String req_tx         = "";                                                     // 취소요청
    String tran_cd        = "";                                                     // 업무코드
    String cust_ip        = f_get_parm( request.getRemoteAddr()                  ); // 요청 IP
    /* = -------------------------------------------------------------------------- = */
    String res_cd         = "";                                                     // 응답코드
    String res_msg        = "";                                                     // 응답 메세지
    String tno            = "";                                                     // KCP 거래 고유 번호
    String card_cd        = "";                                                     // 카드 코드
    String card_name      = "";                                                     // 카드 명
    String amount         = "";                                                     // 결제 금액
    String coupon_mny     = "";                                                     // 쿠폰 금액
    String canc_time       = "";                                                    // 취소요청시간
    /* = -------------------------------------------------------------------------- = */
    String mod_type       = "";                                                     // 변경TYPE(승인취소시 필요)
    String mod_desc       = "";                                                     // 변경사유
    String panc_mod_mny   = "";                                                     // 부분취소 금액
    String panc_rem_mny   = "";                                                     // 부분취소 가능 금액
    String mod_pcan_seq_no = "";                                                    // 부분취소 일련번호
    String panc_coupon_mod_mny = "";                                                // 쿠폰 부분 취소 요청 금액
    String panc_card_mod_mny = "";                                                  // 카드 부분 취소 요청 금액
    String mod_tax_mny    = "";                                                     // 공급가 부분 취소 요청 금액
    String mod_vat_mny    = "";                                                     // 부과세 부분 취소 요청 금액
    String mod_free_mny   = "";                                                     // 비과세 부분 취소 요청 금액
    /* ============================================================================== */
    /* =   02. 지불 요청 정보 설정 END                                              = */
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   03. 인스턴스 생성 및 초기화(변경 불가)                                   = */
    /* = -------------------------------------------------------------------------- = */
    /* =       결제에 필요한 인스턴스를 생성하고 초기화 합니다.                     = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("결제취소 >>> 03.인스턴스 생성 및 초기화 >>> start");
    System.out.println("결제취소 >>> g_conf_gw_url : " + g_conf_gw_url);
    System.out.println("결제취소 >>> g_conf_gw_port : " + g_conf_gw_port);
    System.out.println("결제취소 >>> g_conf_tx_mode : " + g_conf_tx_mode);
    System.out.println("결제취소 >>> g_conf_log_dir : " + g_conf_log_dir);

    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
    c_PayPlus.mf_init_set();

    System.out.println("결제취소 >>> 03.인스턴스 생성 및 초기화 >>> end");
    /* ============================================================================== */
    /* =   03. 인스턴스 생성 및 초기화 END                                          = */
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. 처리 요청 정보 설정                                                  = */
    /* = -------------------------------------------------------------------------- = */

    /* = -------------------------------------------------------------------------- = */
    /* =   04-1. 취소/매입 요청                                                     = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("결제취소 >>> 04-1.취소/매입 요청 >>> start");
    System.out.println("결제취소 >>> req_tx : " + req_tx);
    System.out.println("결제취소 >>> mod_type : " + mod_type);
    System.out.println("결제취소 >>> tno : " + tno);
    System.out.println("결제취소 >>> cust_ip : " + cust_ip);

    if ( req_tx.equals( "" ) )
    {
        int    mod_data_set_no;

        tran_cd = "00200000";
        mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );

        c_PayPlus.mf_set_us( mod_data_set_no, "tno",      tno         );                                        // KCP 원거래 거래번호
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_type", request.getParameter( "mod_type"));                   // 전체취소 STSC / 부분취소 STPC
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",   cust_ip     );                                        // 변경 요청자 IP
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc", ""          );

        if ( mod_type.equals( "STPC" ) ) // 부분취소의 경우
        // 당일 승인건의 전체취소는 STSC(전체취소)로 요청 부탁 드립니다.
        {
            c_PayPlus.mf_set_us( mod_data_set_no, "mod_mny", "" ); // 취소요청금액
            c_PayPlus.mf_set_us( mod_data_set_no, "rem_mny", "" ); // 취소가능잔액

            //복합거래 부분 취소시 주석을 풀어 주시기 바랍니다.
            //c_PayPlus.mf_set_us( mod_data_set_no, "tax_flag",     "TG03"                       ); // 복합과세 구분
            //c_PayPlus.mf_set_us( mod_data_set_no, "mod_tax_mny",  mod_tax_mny                  ); // 공급가 부분 취소 요청 금액
            //c_PayPlus.mf_set_us( mod_data_set_no, "mod_vat_mny",  mod_vat_mny                  ); // 부과세 부분 취소 요청 금액
            //c_PayPlus.mf_set_us( mod_data_set_no, "mod_free_mny", mod_free_mny                 ); // 비과세 부분 취소 요청 금액
        }
    }

    System.out.println("결제취소 >>> 04-1.취소/매입 요청 >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   04. 처리 요청 정보 설정 END                                              = */
    /* = ========================================================================== = */


    /* = ========================================================================== = */
    /* =   05. 실행                                                                 = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("결제취소 >>> 05.실행 >>> start");
    System.out.println("결제취소 >>> tran_cd : " + tran_cd);
    System.out.println("결제취소 >>> g_conf_site_cd : " + g_conf_site_cd);
    System.out.println("결제취소 >>> g_conf_site_key : " + g_conf_site_key);
    System.out.println("결제취소 >>> g_conf_log_level : " + g_conf_log_level);

   if ( tran_cd.length() > 0 )
    {
         c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, "", "", g_conf_log_level, "0" );
    }
    else
    {
        c_PayPlus.m_res_cd  = "9562";
        c_PayPlus.m_res_msg = "연동 오류";
    }
    res_cd  = c_PayPlus.m_res_cd;                      // 결과 코드
    res_msg = c_PayPlus.m_res_msg;                     // 결과 메시지

    System.out.println("결제취소 >>> res_cd : " + res_cd);
    System.out.println("결제취소 >>> res_msg : " + res_msg);
    System.out.println("결제취소 >>> 05.실행 >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   05. 실행 END                                                             = */
    /* ============================================================================== */

    /* = -------------------------------------------------------------------------- = */
    /* =   06. 취소 결과 처리                                                       = */
    /* ============================================================================== */
    System.out.println("결제취소 >>> 06.취소 결과 처리 >>> start");
    System.out.println("결제취소 >>> res_cd : " + res_cd);
    System.out.println("결제취소 >>> mod_type : " + mod_type);

    if ( res_cd.equals( "0000" )) // 정상결제 인 경우
    {

        card_cd     = c_PayPlus.mf_get_res( "card_cd"   );
        card_name   = c_PayPlus.mf_get_res( "card_name" );
        amount      = c_PayPlus.mf_get_res( "amount"    );
        coupon_mny  = c_PayPlus.mf_get_res( "coupon_mny");
        canc_time   = c_PayPlus.mf_get_res( "canc_time"  );

        if ( mod_type.equals( "STPC")) //부분 취소 정상결제인 경우
        {
            card_cd         = c_PayPlus.mf_get_res( "card_cd"   );
            card_name       = c_PayPlus.mf_get_res( "card_name" );
            amount          = c_PayPlus.mf_get_res( "amount"    );
            coupon_mny      = c_PayPlus.mf_get_res( "coupon_mny");
            canc_time       = c_PayPlus.mf_get_res( "canc_time" );
            panc_mod_mny    = c_PayPlus.mf_get_res( "panc_mod_mny");
            panc_rem_mny    = c_PayPlus.mf_get_res( "panc_rem_mny");
            panc_coupon_mod_mny  = c_PayPlus.mf_get_res( "panc_coupon_mod_mny ");
            panc_card_mod_mny = c_PayPlus.mf_get_res( "panc_card_mod_mny");
            mod_pcan_seq_no = c_PayPlus.mf_get_res( "mod_pcan_seq_no");

        }
    }

    System.out.println("결제취소 >>> card_cd : " + card_cd);
    System.out.println("결제취소 >>> card_name : " + card_name);
    System.out.println("결제취소 >>> amount : " + amount);
    System.out.println("결제취소 >>> coupon_mny : " + coupon_mny);
    System.out.println("결제취소 >>> canc_time : " + canc_time);
    System.out.println("결제취소 >>> 06.취소 결과 처리 >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   07. 취소 결과 출력                                                       = */
    /* ============================================================================== */
    System.out.println("결제취소 >>> 07.취소 결과 출력 >>> start");
    System.out.println("결제취소 >>> res_cd : " + res_cd);
    System.out.println("결제취소 >>> mod_type : " + mod_type);

     if ( res_cd.equals( "0000" ) && mod_type.equals( "STSC"))
    {
        out.println( "취소요청이 완료되었습니다.        <br>");
        out.println( "결과코드 : "      + res_cd     + "<br>");
        out.println( "카드 코드: "      + card_cd    + "<br>");
        out.println( "카드 명  : "      + card_name  + "<br>");
        out.println( "결제 금액: "      + amount     + "<br>");
        out.println( "쿠폰 금액: "      + coupon_mny + "<br>");
        out.println( "취소 시간: "      + canc_time  + "<br>");
        out.println( "결과메세지 : "    + res_msg    + "<p>");

        System.out.println("충전결제 >>> canc_time : " + canc_time);
        System.out.println("충전결제 >>> tno : " + tno);

        // DB 호출
        goCancel = "pp_fun_smsChargeCancel('" + canc_time + "', '" + tno + "');";

        System.out.println("결제취소 >>> DB insert >>> end");
    }
    else if ( res_cd.equals( "0000" ) && mod_type.equals( "STPC"))
    {
        out.println( "취소요청이 완료되었습니다.        <br>");
        out.println( "결과코드 : "      + res_cd     + "<br>");
        out.println( "카드 코드: "      + card_cd    + "<br>");
        out.println( "카드 명  : "      + card_name  + "<br>");
        out.println( "결제 금액: "      + amount     + "<br>");
        out.println( "쿠폰 금액: "      + coupon_mny + "<br>");
        out.println( "취소 시간: "      + canc_time  + "<br>");
        out.println( "취소 금액: "      + panc_mod_mny + "<br>");
        out.println( "남은 금액: "      + panc_rem_mny + "<br>");
        out.println( "쿠폰 취소 금액:"  + panc_coupon_mod_mny  + "<br>");
        out.println( "카드 취소 금액:"  + panc_card_mod_mny  + "<br>");
        out.println( "부분취소 일련번호:"  + mod_pcan_seq_no  + "<br>");
        out.println( "결과메세지 : "    + res_msg    + "<p>");
    }
    else
    {
        out.println( "취소요청이 처리 되지 못하였습니다.  <br>");
        out.println( "결과코드 : "      + res_cd       + "<br>");
        out.println( "결과메세지 : "    + res_msg      + "<p>");
    }

    System.out.println("결제취소 >>> 07.취소 결과 출력 >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   07. 취소 결과 출력 END                                                   = */
    /* ============================================================================== */
%>

<script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/common.js?ver=2019072403" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/alert.min.js?ver=2018100401" charset="utf-8"></script>
<script>
    <%=goCancel%>

    // 결제내역 저장
    function pp_fun_smsChargeCancel(canc_time, tno)
    {
        var params = {};
        params.rtnTime = canc_time; // 취소시간
        params.successYn = "R"; // 결제성공여부
        params.controlno = tno; // KCP 거래번호

        $.postJSONSave("/adi/sms/smsCharge/smsCharge/getSmsChargeSaveUpdate.sb", params, function (result) {
            }
            , function (result) {
            });
    }
</script>