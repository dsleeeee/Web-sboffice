<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<%
    String goCancel = "";
//    goCancel = "alert(1);";
%>

<%
    /* ============================================================================== */
    /* =   PAGE : ���� ��û �� ��� ó�� PAGE                                       = */
    /* = -------------------------------------------------------------------------- = */
    /* =   ������ ������ �߻��ϴ� ��� �Ʒ��� �ּҷ� �����ϼż� Ȯ���Ͻñ� �ٶ��ϴ�.= */
    /* =   ���� �ּ� : http://kcp.co.kr/technique.requestcode.do                    = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2021   NHN KCP Inc.   All Rights Reserverd.               = */
    /* ============================================================================== */

    /* ============================================================================== */
    /* =   POST ���� üũ�κ�                                                       = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("������� >>> 01.cancel.jsp ȣ�� >>> request.getMethod() : " + request.getMethod());

    if ( !request.getMethod().equals("POST") )
    {
        out.println("�߸��� ��η� �����Ͽ����ϴ�.");
        return;
    }

    /* ============================================================================== */
%>
<%
    /* ============================================================================== */
    /* =   ȯ�� ���� ���� Include                                                   = */
    /* = -------------------------------------------------------------------------- = */
    /* =   �� �ʼ�                                                                  = */
    /* =   �׽�Ʈ �� �ǰ��� ������ site_conf_inc.jsp������ �����Ͻñ� �ٶ��ϴ�.     = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*" %>
    <%@ page import="java.net.URLEncoder"%>
    <%@ include file = "../cfg/site_conf_inc.jsp"%>
<%
    /* = -------------------------------------------------------------------------- = */
    /* =   ȯ�� ���� ���� Include END                                               = */
    /* ============================================================================== */
%>

<%!
    /* ============================================================================== */
    /* =   null ���� ó���ϴ� �޼ҵ�                                                = */
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
    /* =   02. ���� ��û ���� ����                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String req_tx         = "";                                                     // ��ҿ�û
    String tran_cd        = "";                                                     // �����ڵ�
    String cust_ip        = f_get_parm( request.getRemoteAddr()                  ); // ��û IP
    /* = -------------------------------------------------------------------------- = */
    String res_cd         = "";                                                     // �����ڵ�
    String res_msg        = "";                                                     // ���� �޼���
    String tno            = "";                                                     // KCP �ŷ� ���� ��ȣ
    String card_cd        = "";                                                     // ī�� �ڵ�
    String card_name      = "";                                                     // ī�� ��
    String amount         = "";                                                     // ���� �ݾ�
    String coupon_mny     = "";                                                     // ���� �ݾ�
    String canc_time       = "";                                                    // ��ҿ�û�ð�
    /* = -------------------------------------------------------------------------- = */
    String mod_type       = "";                                                     // ����TYPE(������ҽ� �ʿ�)
    String mod_desc       = "";                                                     // �������
    String panc_mod_mny   = "";                                                     // �κ���� �ݾ�
    String panc_rem_mny   = "";                                                     // �κ���� ���� �ݾ�
    String mod_pcan_seq_no = "";                                                    // �κ���� �Ϸù�ȣ
    String panc_coupon_mod_mny = "";                                                // ���� �κ� ��� ��û �ݾ�
    String panc_card_mod_mny = "";                                                  // ī�� �κ� ��� ��û �ݾ�
    String mod_tax_mny    = "";                                                     // ���ް� �κ� ��� ��û �ݾ�
    String mod_vat_mny    = "";                                                     // �ΰ��� �κ� ��� ��û �ݾ�
    String mod_free_mny   = "";                                                     // ����� �κ� ��� ��û �ݾ�
    /* ============================================================================== */
    /* =   02. ���� ��û ���� ���� END                                              = */
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   03. �ν��Ͻ� ���� �� �ʱ�ȭ(���� �Ұ�)                                   = */
    /* = -------------------------------------------------------------------------- = */
    /* =       ������ �ʿ��� �ν��Ͻ��� �����ϰ� �ʱ�ȭ �մϴ�.                     = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("������� >>> 03.�ν��Ͻ� ���� �� �ʱ�ȭ >>> start");
    System.out.println("������� >>> g_conf_gw_url : " + g_conf_gw_url);
    System.out.println("������� >>> g_conf_gw_port : " + g_conf_gw_port);
    System.out.println("������� >>> g_conf_tx_mode : " + g_conf_tx_mode);
    System.out.println("������� >>> g_conf_log_dir : " + g_conf_log_dir);

    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
    c_PayPlus.mf_init_set();

    System.out.println("������� >>> 03.�ν��Ͻ� ���� �� �ʱ�ȭ >>> end");
    /* ============================================================================== */
    /* =   03. �ν��Ͻ� ���� �� �ʱ�ȭ END                                          = */
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. ó�� ��û ���� ����                                                  = */
    /* = -------------------------------------------------------------------------- = */

    /* = -------------------------------------------------------------------------- = */
    /* =   04-1. ���/���� ��û                                                     = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("������� >>> 04-1.���/���� ��û >>> start");
    System.out.println("������� >>> req_tx : " + req_tx);
    System.out.println("������� >>> mod_type : " + mod_type);
    System.out.println("������� >>> tno : " + tno);
    System.out.println("������� >>> cust_ip : " + cust_ip);

    if ( req_tx.equals( "" ) )
    {
        int    mod_data_set_no;

        tran_cd = "00200000";
        mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );

        c_PayPlus.mf_set_us( mod_data_set_no, "tno",      tno         );                                        // KCP ���ŷ� �ŷ���ȣ
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_type", request.getParameter( "mod_type"));                   // ��ü��� STSC / �κ���� STPC
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",   cust_ip     );                                        // ���� ��û�� IP
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc", ""          );

        if ( mod_type.equals( "STPC" ) ) // �κ������ ���
        // ���� ���ΰ��� ��ü��Ҵ� STSC(��ü���)�� ��û ��Ź �帳�ϴ�.
        {
            c_PayPlus.mf_set_us( mod_data_set_no, "mod_mny", "" ); // ��ҿ�û�ݾ�
            c_PayPlus.mf_set_us( mod_data_set_no, "rem_mny", "" ); // ��Ұ����ܾ�

            //���հŷ� �κ� ��ҽ� �ּ��� Ǯ�� �ֽñ� �ٶ��ϴ�.
            //c_PayPlus.mf_set_us( mod_data_set_no, "tax_flag",     "TG03"                       ); // ���հ��� ����
            //c_PayPlus.mf_set_us( mod_data_set_no, "mod_tax_mny",  mod_tax_mny                  ); // ���ް� �κ� ��� ��û �ݾ�
            //c_PayPlus.mf_set_us( mod_data_set_no, "mod_vat_mny",  mod_vat_mny                  ); // �ΰ��� �κ� ��� ��û �ݾ�
            //c_PayPlus.mf_set_us( mod_data_set_no, "mod_free_mny", mod_free_mny                 ); // ����� �κ� ��� ��û �ݾ�
        }
    }

    System.out.println("������� >>> 04-1.���/���� ��û >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   04. ó�� ��û ���� ���� END                                              = */
    /* = ========================================================================== = */


    /* = ========================================================================== = */
    /* =   05. ����                                                                 = */
    /* = -------------------------------------------------------------------------- = */
    System.out.println("������� >>> 05.���� >>> start");
    System.out.println("������� >>> tran_cd : " + tran_cd);
    System.out.println("������� >>> g_conf_site_cd : " + g_conf_site_cd);
    System.out.println("������� >>> g_conf_site_key : " + g_conf_site_key);
    System.out.println("������� >>> g_conf_log_level : " + g_conf_log_level);

   if ( tran_cd.length() > 0 )
    {
         c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, "", "", g_conf_log_level, "0" );
    }
    else
    {
        c_PayPlus.m_res_cd  = "9562";
        c_PayPlus.m_res_msg = "���� ����";
    }
    res_cd  = c_PayPlus.m_res_cd;                      // ��� �ڵ�
    res_msg = c_PayPlus.m_res_msg;                     // ��� �޽���

    System.out.println("������� >>> res_cd : " + res_cd);
    System.out.println("������� >>> res_msg : " + res_msg);
    System.out.println("������� >>> 05.���� >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   05. ���� END                                                             = */
    /* ============================================================================== */

    /* = -------------------------------------------------------------------------- = */
    /* =   06. ��� ��� ó��                                                       = */
    /* ============================================================================== */
    System.out.println("������� >>> 06.��� ��� ó�� >>> start");
    System.out.println("������� >>> res_cd : " + res_cd);
    System.out.println("������� >>> mod_type : " + mod_type);

    if ( res_cd.equals( "0000" )) // ������� �� ���
    {

        card_cd     = c_PayPlus.mf_get_res( "card_cd"   );
        card_name   = c_PayPlus.mf_get_res( "card_name" );
        amount      = c_PayPlus.mf_get_res( "amount"    );
        coupon_mny  = c_PayPlus.mf_get_res( "coupon_mny");
        canc_time   = c_PayPlus.mf_get_res( "canc_time"  );

        if ( mod_type.equals( "STPC")) //�κ� ��� ��������� ���
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

    System.out.println("������� >>> card_cd : " + card_cd);
    System.out.println("������� >>> card_name : " + card_name);
    System.out.println("������� >>> amount : " + amount);
    System.out.println("������� >>> coupon_mny : " + coupon_mny);
    System.out.println("������� >>> canc_time : " + canc_time);
    System.out.println("������� >>> 06.��� ��� ó�� >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   07. ��� ��� ���                                                       = */
    /* ============================================================================== */
    System.out.println("������� >>> 07.��� ��� ��� >>> start");
    System.out.println("������� >>> res_cd : " + res_cd);
    System.out.println("������� >>> mod_type : " + mod_type);

     if ( res_cd.equals( "0000" ) && mod_type.equals( "STSC"))
    {
        out.println( "��ҿ�û�� �Ϸ�Ǿ����ϴ�.        <br>");
        out.println( "����ڵ� : "      + res_cd     + "<br>");
        out.println( "ī�� �ڵ�: "      + card_cd    + "<br>");
        out.println( "ī�� ��  : "      + card_name  + "<br>");
        out.println( "���� �ݾ�: "      + amount     + "<br>");
        out.println( "���� �ݾ�: "      + coupon_mny + "<br>");
        out.println( "��� �ð�: "      + canc_time  + "<br>");
        out.println( "����޼��� : "    + res_msg    + "<p>");

        System.out.println("�������� >>> canc_time : " + canc_time);
        System.out.println("�������� >>> tno : " + tno);

        // DB ȣ��
        goCancel = "pp_fun_smsChargeCancel('" + canc_time + "', '" + tno + "');";

        System.out.println("������� >>> DB insert >>> end");
    }
    else if ( res_cd.equals( "0000" ) && mod_type.equals( "STPC"))
    {
        out.println( "��ҿ�û�� �Ϸ�Ǿ����ϴ�.        <br>");
        out.println( "����ڵ� : "      + res_cd     + "<br>");
        out.println( "ī�� �ڵ�: "      + card_cd    + "<br>");
        out.println( "ī�� ��  : "      + card_name  + "<br>");
        out.println( "���� �ݾ�: "      + amount     + "<br>");
        out.println( "���� �ݾ�: "      + coupon_mny + "<br>");
        out.println( "��� �ð�: "      + canc_time  + "<br>");
        out.println( "��� �ݾ�: "      + panc_mod_mny + "<br>");
        out.println( "���� �ݾ�: "      + panc_rem_mny + "<br>");
        out.println( "���� ��� �ݾ�:"  + panc_coupon_mod_mny  + "<br>");
        out.println( "ī�� ��� �ݾ�:"  + panc_card_mod_mny  + "<br>");
        out.println( "�κ���� �Ϸù�ȣ:"  + mod_pcan_seq_no  + "<br>");
        out.println( "����޼��� : "    + res_msg    + "<p>");
    }
    else
    {
        out.println( "��ҿ�û�� ó�� ���� ���Ͽ����ϴ�.  <br>");
        out.println( "����ڵ� : "      + res_cd       + "<br>");
        out.println( "����޼��� : "    + res_msg      + "<p>");
    }

    System.out.println("������� >>> 07.��� ��� ��� >>> end");
    /* = -------------------------------------------------------------------------- = */
    /* =   07. ��� ��� ��� END                                                   = */
    /* ============================================================================== */
%>

<script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/common.js?ver=2019072403" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/common/alert.min.js?ver=2018100401" charset="utf-8"></script>
<script>
    <%=goCancel%>

    // �������� ����
    function pp_fun_smsChargeCancel(canc_time, tno)
    {
        var params = {};
        params.rtnTime = canc_time; // ��ҽð�
        params.successYn = "R"; // ������������
        params.controlno = tno; // KCP �ŷ���ȣ

        $.postJSONSave("/adi/sms/smsCharge/smsCharge/getSmsChargeSaveUpdate.sb", params, function (result) {
            }
            , function (result) {
            });
    }
</script>