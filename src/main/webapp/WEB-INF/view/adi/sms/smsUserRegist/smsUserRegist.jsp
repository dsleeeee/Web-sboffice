<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<style>
  #smsUserRegistView{
    --sur-blue:#1B5EE3; --sur-blue-soft:#EAF1FE; --sur-blue-dark:#144CBB;
    --sur-ink:#171A21; --sur-ink-2:#3C4049; --sur-ink-3:#6B7078; --sur-ink-4:#9297A0; --sur-ink-5:#C4C8CE;
    --sur-line:#E4E6EA; --sur-line-strong:#D3D6DB;
    --sur-surface:#ffffff; --sur-surface-1:#F6F7F9; --sur-surface-2:#F0F1F4;
    --sur-green:#1C9A5B; --sur-green-soft:#E8F7EE;
    --sur-amber:#B7791F; --sur-amber-soft:#FDF3E1;
    --sur-red:#D3413C; --sur-red-soft:#FCEBEA;
    --sur-radius:10px;
  }
  #smsUserRegistView{background:var(--sur-surface-1); color:var(--sur-ink-2); font-size:14px; line-height:1.6; word-break:keep-all; min-height:100vh; padding:56px 28px 28px;}
  #smsUserRegistView *{box-sizing:border-box;}
  #smsUserRegistView .sur-content{display:flex; justify-content:center;}
  #smsUserRegistView .sur-panel{width:100%; max-width:640px;}

  #smsUserRegistView .sur-page-head{margin-bottom:24px;}
  #smsUserRegistView .sur-page-title{font-size:20px; font-weight:800; color:var(--sur-ink); letter-spacing:-0.01em;}
  #smsUserRegistView .sur-page-desc{margin-top:6px; font-size:13px; color:var(--sur-ink-4);}

  /* Stepper */
  #smsUserRegistView .sur-stepper{display:flex; align-items:center; margin-bottom:28px; background:var(--sur-surface); border:1px solid var(--sur-line); border-radius:var(--sur-radius); padding:18px 20px;}
  #smsUserRegistView .sur-step{display:flex; align-items:center; gap:10px; flex:1;}
  #smsUserRegistView .sur-step-circle{
    width:26px; height:26px; border-radius:50%; display:flex; align-items:center; justify-content:center;
    font-size:12px; font-weight:700; flex-shrink:0; border:1.5px solid var(--sur-line-strong); color:var(--sur-ink-4); background:var(--sur-surface);
  }
  #smsUserRegistView .sur-step-label{font-size:12.5px; font-weight:600; color:var(--sur-ink-4); white-space:nowrap;}
  #smsUserRegistView .sur-step.sur-done .sur-step-circle{background:var(--sur-green); border-color:var(--sur-green); color:#fff;}
  #smsUserRegistView .sur-step.sur-done .sur-step-label{color:var(--sur-ink-2);}
  #smsUserRegistView .sur-step.sur-current .sur-step-circle{background:var(--sur-blue); border-color:var(--sur-blue); color:#fff;}
  #smsUserRegistView .sur-step.sur-current .sur-step-label{color:var(--sur-ink);}
  #smsUserRegistView .sur-step-line{height:1.5px; background:var(--sur-line); flex:1; margin:0 4px; max-width:40px;}
  #smsUserRegistView .sur-step-line.sur-done{background:var(--sur-green);}

  /* Card */
  #smsUserRegistView .sur-card{background:var(--sur-surface); border:1px solid var(--sur-line); border-radius:var(--sur-radius); overflow:hidden;}
  #smsUserRegistView .sur-card + .sur-card{margin-top:16px;}
  #smsUserRegistView .sur-card-head{padding:18px 22px; border-bottom:1px solid var(--sur-line); display:flex; align-items:center; justify-content:space-between;}
  #smsUserRegistView .sur-card-head h2{font-size:14.5px; font-weight:700; color:var(--sur-ink);}
  #smsUserRegistView .sur-card-body{padding:22px;}
  #smsUserRegistView .sur-badge{font-size:11px; font-weight:700; padding:3px 8px; border-radius:100px;}
  #smsUserRegistView .sur-badge-required{background:var(--sur-red-soft); color:var(--sur-red);}

  /* Screen 1: consent */
  #smsUserRegistView .sur-consent-item{border-bottom:1px solid var(--sur-line);}
  #smsUserRegistView .sur-consent-item:last-child{border-bottom:none;}
  #smsUserRegistView .sur-consent-row{display:flex; align-items:center; justify-content:space-between; padding:16px 22px; cursor:pointer;}
  #smsUserRegistView .sur-consent-row-left{display:flex; align-items:center; gap:10px;}
  #smsUserRegistView .sur-checkbox{
    width:20px; height:20px; border-radius:5px; border:1.5px solid var(--sur-line-strong); flex-shrink:0;
    display:flex; align-items:center; justify-content:center; background:var(--sur-surface);
  }
  #smsUserRegistView .sur-checkbox.sur-checked{background:var(--sur-blue); border-color:var(--sur-blue);}
  #smsUserRegistView .sur-checkbox svg{opacity:0;}
  #smsUserRegistView .sur-checkbox.sur-checked svg{opacity:1;}
  #smsUserRegistView .sur-consent-title{font-size:13.5px; font-weight:600; color:var(--sur-ink);}
  #smsUserRegistView .sur-consent-toggle{font-size:12px; color:var(--sur-ink-4); display:flex; align-items:center; gap:4px;}
  #smsUserRegistView .sur-consent-detail{padding:0 22px 18px 52px; font-size:12.5px; color:var(--sur-ink-3); line-height:1.7;}
  #smsUserRegistView .sur-consent-body{background:var(--sur-surface-1); border:1px solid var(--sur-line); border-radius:10px; padding:4px 16px;}

  #smsUserRegistView .sur-ci-row{display:flex; gap:16px; padding:11px 0; border-bottom:1px solid var(--sur-line);}
  #smsUserRegistView .sur-ci-row:last-child{border-bottom:none;}
  #smsUserRegistView .sur-ci-k{width:104px; flex-shrink:0; font-size:12px; font-weight:700; color:var(--sur-ink-3);}
  #smsUserRegistView .sur-ci-v{font-size:12.5px; color:var(--sur-ink-2); line-height:1.6;}

  #smsUserRegistView .sur-consent-list{padding:12px 0; display:flex; flex-direction:column; gap:9px;}
  #smsUserRegistView .sur-consent-list > li{list-style:none; position:relative; padding-left:14px; font-size:12.5px; color:var(--sur-ink-2); line-height:1.6;}
  #smsUserRegistView .sur-consent-list > li::before{content:""; position:absolute; left:0; top:8px; width:4px; height:4px; border-radius:50%; background:var(--sur-ink-4);}
  #smsUserRegistView .sur-consent-sublist{margin-top:6px; padding-left:2px; display:flex; flex-direction:column; gap:5px;}
  #smsUserRegistView .sur-consent-sublist li{list-style:none; position:relative; padding-left:13px; font-size:12px; color:var(--sur-ink-3); line-height:1.55;}
  #smsUserRegistView .sur-consent-sublist li::before{content:"–"; position:absolute; left:0; top:0; color:var(--sur-ink-5);}

  #smsUserRegistView .sur-consent-note{margin:2px 0 12px; padding:13px 14px; background:var(--sur-amber-soft); border-radius:8px; display:flex; flex-direction:column; gap:8px;}
  #smsUserRegistView .sur-consent-note p{font-size:12px; color:#8a5a10; line-height:1.6;}

  #smsUserRegistView .sur-all-agree{display:flex; align-items:center; gap:10px; padding:16px 22px; background:var(--sur-surface-1); border-bottom:1px solid var(--sur-line); cursor:pointer;}
  #smsUserRegistView .sur-all-agree-label{font-size:13.5px; font-weight:700; color:var(--sur-ink);}

  /* Terms sections (긴 약관 전문) */
  #smsUserRegistView .sur-terms-sub{font-size:13px; font-weight:700; color:var(--sur-ink); margin:20px 0 10px;}
  #smsUserRegistView .sur-terms-sub:first-child{margin-top:2px;}
  #smsUserRegistView .sur-terms-scroll{max-height:230px; overflow-y:auto; border:1px solid var(--sur-line); border-radius:8px; background:var(--sur-surface-1); padding:14px 16px;}
  #smsUserRegistView .sur-terms-h{font-size:12.5px; font-weight:700; color:var(--sur-blue-dark); margin:16px 0 6px;}
  #smsUserRegistView .sur-terms-h:first-child{margin-top:0;}
  #smsUserRegistView .sur-terms-p{font-size:12px; color:var(--sur-ink-3); line-height:1.7; margin:0 0 6px; word-break:keep-all;}
  #smsUserRegistView .sur-terms-p.indent{padding-left:12px;}
  #smsUserRegistView .sur-terms-p.indent2{padding-left:24px;}
  #smsUserRegistView .sur-terms-tbl{width:100%; border-collapse:collapse; margin:8px 0 6px; font-size:12px;}
  #smsUserRegistView .sur-terms-tbl th, #smsUserRegistView .sur-terms-tbl td{border:1px solid var(--sur-line); padding:6px 10px; text-align:left; vertical-align:top;}
  #smsUserRegistView .sur-terms-tbl th{width:88px; background:var(--sur-surface-2); font-weight:700; color:var(--sur-ink-3);}
  #smsUserRegistView .sur-terms-tbl td{color:var(--sur-ink-2);}

  /* Screen 2: register */
  #smsUserRegistView .sur-field-row{display:flex; align-items:center; padding:16px 0; border-bottom:1px solid var(--sur-line);}
  #smsUserRegistView .sur-field-row:last-child{border-bottom:none;}
  #smsUserRegistView .sur-field-label{width:140px; flex-shrink:0; font-size:13px; font-weight:600; color:var(--sur-ink-3);}
  #smsUserRegistView .sur-field-value{font-size:14px; color:var(--sur-ink); font-weight:600;}

  #smsUserRegistView .sur-verify-box{border:1.5px dashed var(--sur-line-strong); border-radius:8px; padding:24px; text-align:center; display:flex; flex-direction:column; align-items:center; gap:12px;}
  #smsUserRegistView .sur-verify-icon{width:44px; height:44px; border-radius:50%; background:var(--sur-blue-soft); display:flex; align-items:center; justify-content:center;}
  #smsUserRegistView .sur-verify-text{font-size:13px; color:var(--sur-ink-4);}

  #smsUserRegistView .sur-btn{
    display:inline-flex; align-items:center; justify-content:center; gap:6px;
    padding:11px 20px; border-radius:8px; font-size:13.5px; font-weight:700; cursor:pointer;
    border:none;
  }
  #smsUserRegistView .sur-btn-primary{background:var(--sur-blue); color:#fff;}
  #smsUserRegistView .sur-btn-primary:disabled{background:var(--sur-ink-5); cursor:not-allowed;}
  #smsUserRegistView .sur-btn-outline{background:var(--sur-surface); color:var(--sur-ink-2); border:1px solid var(--sur-line-strong);}
  #smsUserRegistView .sur-btn-sm{padding:8px 14px; font-size:12.5px;}

  #smsUserRegistView .sur-verified-result{border:1.5px solid var(--sur-green); background:var(--sur-green-soft); border-radius:8px; padding:18px 20px; display:flex; align-items:flex-start; gap:12px;}
  #smsUserRegistView .sur-verified-result .sur-vi-icon{width:22px; height:22px; border-radius:50%; background:var(--sur-green); flex-shrink:0; display:flex; align-items:center; justify-content:center;}
  #smsUserRegistView .sur-verified-result-title{font-size:13.5px; font-weight:700; color:var(--sur-ink);}
  #smsUserRegistView .sur-verified-result-desc{font-size:12.5px; color:var(--sur-ink-3); margin-top:4px;}
  #smsUserRegistView .sur-verified-info{margin-top:12px; display:flex; flex-direction:column; gap:6px;}
  #smsUserRegistView .sur-verified-info-row{display:flex; gap:8px; font-size:12.5px;}
  #smsUserRegistView .sur-verified-info-row .sur-k{color:var(--sur-ink-4); width:60px; flex-shrink:0;}
  #smsUserRegistView .sur-verified-info-row .sur-v{color:var(--sur-ink-2); font-weight:600; font-variant-numeric:tabular-nums;}

  #smsUserRegistView .sur-dup-error{border:1.5px solid var(--sur-red); background:var(--sur-red-soft); border-radius:8px; padding:16px 18px; display:flex; align-items:flex-start; gap:10px; font-size:12.5px; color:var(--sur-ink-2);}

  #smsUserRegistView .sur-verify-fail-box{border:1.5px dashed var(--sur-red); border-radius:8px; padding:24px; text-align:center; display:flex; flex-direction:column; align-items:center; gap:10px; background:var(--sur-red-soft);}
  #smsUserRegistView .sur-verify-fail-icon{width:44px; height:44px; border-radius:50%; background:var(--sur-surface); border:1.5px solid var(--sur-red); display:flex; align-items:center; justify-content:center;}
  #smsUserRegistView .sur-verify-fail-title{font-size:13.5px; font-weight:700; color:var(--sur-ink);}
  #smsUserRegistView .sur-verify-fail-desc{font-size:12.5px; color:var(--sur-ink-3); line-height:1.6;}
  #smsUserRegistView .sur-verify-fail-reason{font-size:12px; color:var(--sur-red); background:var(--sur-surface); border:1px solid var(--sur-red); border-radius:6px; padding:6px 12px; font-weight:600;}

  #smsUserRegistView .sur-actions{display:flex; gap:10px; margin-top:20px;}
  #smsUserRegistView .sur-actions .sur-btn{flex:1;}

  /* Screen 3: done */
  #smsUserRegistView .sur-done-wrap{text-align:center; padding:56px 22px;}
  #smsUserRegistView .sur-done-icon{width:64px; height:64px; border-radius:50%; background:var(--sur-green-soft); border:2px solid var(--sur-green); display:flex; align-items:center; justify-content:center; margin:0 auto 20px;}
  #smsUserRegistView .sur-done-title{font-size:18px; font-weight:800; color:var(--sur-ink);}
  #smsUserRegistView .sur-done-desc{margin-top:8px; font-size:13px; color:var(--sur-ink-4);}
  #smsUserRegistView .sur-done-summary{margin-top:28px; text-align:left; background:var(--sur-surface-1); border-radius:8px; padding:18px 20px;}

  #smsUserRegistView .sur-delete-row{display:flex; justify-content:center; padding:16px 22px 22px;}
  #smsUserRegistView .sur-btn-danger-outline{
    display:inline-flex; align-items:center; justify-content:center; width:100%; padding:13px 20px; border-radius:8px;
    background:var(--sur-surface); border:1px solid var(--sur-red); color:var(--sur-red); font-size:14px; font-weight:700; cursor:pointer;
  }

  @media (max-width:760px){
    #smsUserRegistView .sur-field-row{flex-direction:column; align-items:flex-start; gap:6px;}
    #smsUserRegistView .sur-field-label{width:auto;}
  }
</style>

<div id="smsUserRegistView" name="smsUserRegistView" class="subCon" ng-controller="smsUserRegistCtrl">
  <%-- runVerify()가 getVerifyVal.sb의 callUrl을 #smsUserRegistKcpAuthForm.action에 지정한다. --%>
  <%-- 고유 폼으로 reg_cert_key/kcp_page_submit_yn만 POST하며 updateVerify.sb는 결과를 S2S 조회한다. --%>
  <form id="smsUserRegistKcpAuthForm" name="smsUserRegistKcpAuthForm" method="post" style="display:none;">
    <input type="hidden" name="reg_cert_key" />
    <input type="hidden" name="kcp_page_submit_yn" value="N" />
  </form>
  <div class="sur-content">
    <div class="sur-panel">

      <!-- ===================== STEP 1: 동의 화면 ===================== -->
      <div ng-show="step==1">
        <div class="sur-page-head">
          <div class="sur-page-title">SMS 사용 등록</div>
          <div class="sur-page-desc">본인인증 및 개인정보 수집·이용에 동의해야 다음 단계로 진행할 수 있습니다.</div>
        </div>

        <div class="sur-stepper">
          <div class="sur-step sur-current"><div class="sur-step-circle">1</div><div class="sur-step-label">약관 동의</div></div>
          <div class="sur-step-line"></div>
          <div class="sur-step"><div class="sur-step-circle">2</div><div class="sur-step-label">본인인증 · 등록</div></div>
          <div class="sur-step-line"></div>
          <div class="sur-step"><div class="sur-step-circle">3</div><div class="sur-step-label">완료</div></div>
        </div>

        <div class="sur-card">
          <!-- 전체 동의 -->
          <div class="sur-all-agree" ng-click="toggleAllConsent()">
            <div class="sur-checkbox" ng-class="{'sur-checked': isAllAgreed()}"><svg width="12" height="10" viewBox="0 0 12 10" fill="none"><path d="M1 5L4.2 8.2L11 1" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
            <div class="sur-all-agree-label">아래 내용에 모두 동의합니다</div>
          </div>

          <!-- 필수1: 개인정보 수집·이용 및 SMS 발신 서비스 이용약관 -->
          <div class="sur-consent-item">
            <div class="sur-consent-row" ng-click="toggleConsent('c1')">
              <div class="sur-consent-row-left">
                <div class="sur-checkbox" ng-class="{'sur-checked': consent.c1}"><svg width="12" height="10" viewBox="0 0 12 10" fill="none"><path d="M1 5L4.2 8.2L11 1" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
                <div class="sur-consent-title">[필수] 개인정보 수집·이용 및 SMS 발신 서비스 이용약관</div>
              </div>
              <div class="sur-consent-toggle" ng-click="$event.stopPropagation(); toggleDetail('c1')">자세히 <span>{{detailOpen.c1 ? '▴' : '▾'}}</span></div>
            </div>
            <div class="sur-consent-detail" ng-show="detailOpen.c1">

            <!-- 본인확인을 위한 개인정보 수집·이용 -->
            <div class="sur-terms-sub">본인확인을 위한 개인정보 수집·이용</div>
            <div class="sur-consent-body">
              <div class="sur-ci-row">
                <div class="sur-ci-k">수집 항목</div>
                <div class="sur-ci-v">DI(중복가입확인정보), 휴대폰번호</div>
              </div>
              <div class="sur-ci-row">
                <div class="sur-ci-k">수집 목적</div>
                <div class="sur-ci-v">DI를 활용한 계정 동일인 확인 및 SMS 사용 등록 중복가입 방지, 발신번호 소유자 확인, 로그인 인증(MFA)</div>
              </div>
              <div class="sur-ci-row">
                <div class="sur-ci-k">보유 및 이용 기간</div>
                <div class="sur-ci-v">
                  · DI, 휴대폰번호: 회원 탈퇴 또는 발신번호 등록 해지 시 지체 없이 파기<br>
                  · 인증 이력: 1년 이상 보관
                </div>
              </div>
              <div class="sur-ci-row">
                <div class="sur-ci-k">동의 거부 시</div>
                <div class="sur-ci-v">SMS 사용 등록이 제한됩니다.</div>
              </div>
            </div>

            <!-- SMS 발신 서비스 이용약관 -->
            <div class="sur-terms-sub">SMS 발신 서비스 이용약관</div>
            <div class="sur-consent-body">
              <ul class="sur-consent-list">
                <li>발신번호 등록 요청은 <b>SMS 전송화면</b>에서 진행할 수 있습니다.</li>
                <li>발신번호를 등록하려면 신청자 본인인증을 진행하며, <b>인증 DI와 계정 DI가 일치</b>하는 경우에만 등록할 수 있습니다.</li>
                <li>
                  <b>무선(휴대폰) 번호</b> — 최대 2회선
                  <ul class="sur-consent-sublist">
                    <li>본인 명의: 등록하려는 번호로 직접 본인인증하며, 인증 DI와 계정 DI가 일치하면 별도 제출 서류 없이 등록됩니다.</li>
                    <li>기업 명의: 신청자 본인인증(DI 대조) 후 제출 — 통신서비스 이용증명원 + 사업자등록증 + 재직증명서</li>
                  </ul>
                </li>
                <li>
                  <b>유선 번호</b> — 최대 5회선
                  <ul class="sur-consent-sublist">
                    <li>본인 명의: 신청자 본인인증(DI 대조) 후 제출 — 통신서비스 이용증명원</li>
                    <li>기업 명의: 신청자 본인인증(DI 대조) 후 제출 — 통신서비스 이용증명원 + 사업자등록증 + 재직증명서</li>
                  </ul>
                </li>
                <li>본인인증 정보가 변경되는 경우 등록된 발신번호는 자동으로 사용이 제한됩니다.</li>
                <li>매장 폐점 시 로그인 및 SMS 관련 메뉴 접근이 제한됩니다.</li>
              </ul>
            </div>
            <div class="sur-consent-note">
              <p>문자 발송을 위해서는 발신번호를 별도로 등록해야 하며, 등록 과정에서 개인정보취급방침 및 불법스팸 방지·서비스 이용제한 정책에 대한 동의가 필요합니다.</p>
              <p>서비스의 안전한 운영 및 문자발송 시스템의 부정 이용 방지를 위하여 국외 IP 또는 VPN·프록시 등 우회접속을 통한 문자 발송을 차단합니다.</p>
            </div>

            </div>
          </div>

          <!-- 필수2: 개인정보취급방침 및 불법스팸 방지·서비스 이용제한 정책 -->
          <div class="sur-consent-item">
            <div class="sur-consent-row" ng-click="toggleConsent('c2')">
              <div class="sur-consent-row-left">
                <div class="sur-checkbox" ng-class="{'sur-checked': consent.c2}"><svg width="12" height="10" viewBox="0 0 12 10" fill="none"><path d="M1 5L4.2 8.2L11 1" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
                <div class="sur-consent-title">[필수] 개인정보취급방침 및 불법스팸 방지·서비스 이용제한 정책</div>
              </div>
              <div class="sur-consent-toggle" ng-click="$event.stopPropagation(); toggleDetail('c2')">자세히 <span>{{detailOpen.c2 ? '▴' : '▾'}}</span></div>
            </div>
            <div class="sur-consent-detail" ng-show="detailOpen.c2">

            <!-- 개인정보취급방침 -->
            <div class="sur-terms-sub">개인정보취급방침</div>
            <div class="sur-terms-scroll">
              <p class="sur-terms-p">링크(주)('www.lynk.co.kr' 이하 '링크(주)')는 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리방침을 두고 있습니다. 링크(주)는 개인정보처리방침을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다. 본 방침은 2016년 3월 17일부터 시행됩니다.</p>

              <div class="sur-terms-h">1. 개인정보의 처리 목적</div>
              <p class="sur-terms-p">'링크(주)'는 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적 이외의 용도로는 사용되지 않으며, 이용 목적이 변경될 시에는 사전동의를 구할 예정입니다.</p>
              <p class="sur-terms-p indent">(1) 홈페이지 회원가입 및 관리 : 회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지, 만14세 미만 아동 개인정보 수집 시 법정대리인 동의 여부 확인, 각종 고지·통지, 고충처리, 분쟁 조정을 위한 기록 보존 등을 목적으로 개인정보를 처리합니다.</p>
              <p class="sur-terms-p indent">(2) 민원사무 처리 : 민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락·통지, 처리결과 통보 등을 목적으로 개인정보를 처리합니다.</p>
              <p class="sur-terms-p indent">(3) 재화 또는 서비스 제공 : 물품배송, 서비스 제공, 청구서 발송, 콘텐츠 제공, 맞춤 서비스 제공, 본인인증, 요금결제·정산, 채권추심 등을 목적으로 개인정보를 처리합니다.</p>
              <p class="sur-terms-p indent">(4) 마케팅 및 광고에의 활용 : 신규 서비스(제품) 개발 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공, 인구통계학적 특성에 따른 서비스 제공 및 광고 게재, 서비스의 유효성 확인, 접속빈도 파악 또는 회원의 서비스 이용에 대한 통계 등을 목적으로 개인정보를 처리합니다.</p>

              <div class="sur-terms-h">2. 개인정보 파일 현황</div>
              <p class="sur-terms-p">● 개인정보 파일명 : 링크(주) 개인정보 동의서</p>
              <p class="sur-terms-p indent">- 개인정보 항목 : 이메일, 휴대전화번호, 자택주소, 자택전화번호, 비밀번호 질문과 답, 비밀번호, 로그인ID, 성별, 생년월일, 이름, 회사전화번호, 직책, 부서, 회사명, 직업, 주민등록번호, 신용카드정보, 은행계좌정보, 서비스 이용 기록, 접속 로그, 쿠키, 접속 IP 정보, 결제기록, 법정대리인 이름, 법정대리인 자택 전화번호, 법정대리인 자택 주소, 법정대리인 휴대전화번호</p>
              <p class="sur-terms-p indent">- 수집방법 : 홈페이지, 서면양식, 전화/팩스, 경품행사, 배송요청, 제휴사로부터 제공 받음, 생성정보 수집 툴을 통한 수집</p>
              <p class="sur-terms-p indent">- 보유근거 : ASP서비스 이용계약</p>
              <p class="sur-terms-p indent">- 보유기간 : 하기와 같음</p>
              <p class="sur-terms-p indent">- 관련법령 :</p>
              <p class="sur-terms-p indent2">· 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년</p>
              <p class="sur-terms-p indent2">· 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년</p>
              <p class="sur-terms-p indent2">· 대금결제 및 재화 등의 공급에 관한 기록 : 5년</p>
              <p class="sur-terms-p indent2">· 계약 또는 청약철회 등에 관한 기록 : 5년</p>
              <p class="sur-terms-p indent2">· 표시/광고에 관한 기록 : 6개월</p>

              <div class="sur-terms-h">3. 개인정보의 처리 및 보유 기간</div>
              <p class="sur-terms-p indent">(1) '링크(주)'는 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의 받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.</p>
              <p class="sur-terms-p indent">(2) 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.</p>
              <p class="sur-terms-p indent2">● 홈페이지 회원가입 및 관리 : [홈페이지 회원가입 및 관리]와 관련한 개인정보는 수집·이용에 관한 동의일로부터 위 이용목적을 위하여 보유·이용됩니다.</p>
              <p class="sur-terms-p indent2">- 보유근거 : 홈페이지 회원가입 및 관리</p>
              <p class="sur-terms-p indent2">- 관련법령 : 신용정보의 수집/처리 및 이용 등에 관한 기록 3년, 소비자의 불만 또는 분쟁처리에 관한 기록 3년, 대금결제 및 재화 등의 공급에 관한 기록 5년, 계약 또는 청약철회 등에 관한 기록 5년, 표시/광고에 관한 기록 6개월</p>
              <p class="sur-terms-p indent2">- 예외사유 : 채권·채무관계 잔존 시에는 해당 채권·채무관계 정산 시까지</p>

              <div class="sur-terms-h">4. 개인정보의 제3자 제공에 관한 사항</div>
              <p class="sur-terms-p indent">(1) '링크(주)'는 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</p>
              <p class="sur-terms-p indent">(2) '링크(주)'는 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.</p>
              <p class="sur-terms-p indent2">● 링크(주) 고객센터, 위탁관리 계약을 체결한 업체</p>
              <p class="sur-terms-p indent2">- 개인정보를 제공받는 자 : 링크(주) 고객센터, 위탁관리 계약을 체결한 업체</p>
              <p class="sur-terms-p indent2">- 제공받는 자의 개인정보 이용목적 : 사업자정보(대표명, 사업자등록번호, 소재지), 회사명, 회사전화번호, 직책, 이름, 생년월일, 성별, 휴대전화번호, 이메일, 자택주소, 자택전화번호, 주민등록번호, 신용카드정보, 은행계좌정보 등 결제정보</p>
              <p class="sur-terms-p indent2">- 제공받는 자의 보유·이용기간 : 서비스 해지 후 5년</p>

              <div class="sur-terms-h">5. 개인정보처리 위탁</div>
              <p class="sur-terms-p indent">(1) '링크(주)'는 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.</p>
              <p class="sur-terms-p indent2">● 이용계약 및 그 부대업무의 대행, 수수료정산</p>
              <p class="sur-terms-p indent2">- 위탁받는 자(수탁자) : 링크(주) 고객센터, 위탁관리 계약을 체결 업체</p>
              <p class="sur-terms-p indent2">- 위탁하는 업무의 내용 : 구매 및 요금 결제, 물품배송 또는 청구서 등 발송, 본인인증(금융거래, 금융서비스), 요금추심, 회원제 서비스 이용에 따른 본인확인, 불만처리 등 민원처리, 고지사항 전달, 신규 서비스(제품) 개발 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공, 영상정보처리기기 운영</p>
              <p class="sur-terms-p indent2">- 위탁기간 : 서비스 해지 후 5년</p>
              <p class="sur-terms-p indent">(2) '링크(주)'는 위탁계약 체결 시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적·관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리·감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.</p>
              <p class="sur-terms-p indent">(3) 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체 없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.</p>

              <div class="sur-terms-h">6. 정보주체의 권리·의무 및 그 행사방법</div>
              <p class="sur-terms-p">이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.</p>
              <p class="sur-terms-p indent">(1) 정보주체는 '링크(주)'에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.</p>
              <p class="sur-terms-p indent2">① 개인정보 열람요구  ② 오류 등이 있을 경우 정정 요구  ③ 삭제요구  ④ 처리정지 요구</p>
              <p class="sur-terms-p indent">(2) 제1항에 따른 권리 행사는 '링크(주)'에 대해 개인정보 보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 '링크(주)'는 이에 대해 지체 없이 조치하겠습니다.</p>
              <p class="sur-terms-p indent">(3) 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 '링크(주)'는 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.</p>
              <p class="sur-terms-p indent">(4) 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.</p>

              <div class="sur-terms-h">7. 처리하는 개인정보의 항목</div>
              <p class="sur-terms-p">'링크(주)'는 다음의 개인정보 항목을 처리하고 있습니다.</p>
              <p class="sur-terms-p indent">● 홈페이지 회원가입 및 관리</p>
              <p class="sur-terms-p indent2">- 필수항목 : 사업자정보(대표명, 사업자등록번호, 소재지), 회사명, 회사전화번호, 직책, 이름, 생년월일, 성별, 휴대전화번호, 이메일, 자택주소, 자택전화번호, 주민등록번호, 비밀번호 질문과 답, 비밀번호, 로그인ID</p>
              <p class="sur-terms-p indent2">- 선택항목 : 부서, 직업, 법정대리인 이름, 법정대리인 자택 전화번호, 법정대리인 자택 주소, 법정대리인 휴대전화번호, 신용카드정보, 은행계좌정보 등 결제정보</p>

              <div class="sur-terms-h">8. 개인정보의 파기</div>
              <p class="sur-terms-p">'링크(주)'는 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체 없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.</p>
              <p class="sur-terms-p indent">- 파기절차 : 이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.</p>
              <p class="sur-terms-p indent">- 파기기한 : 이용자의 개인정보는 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.</p>
              <p class="sur-terms-p indent">- 파기방법 :</p>
              <p class="sur-terms-p indent2">① 전자적 파일 형태의 정보 : 기록을 재생할 수 없는 기술적 방법을 사용합니다.</p>
              <p class="sur-terms-p indent2">② 종이에 출력된 개인정보 : 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.</p>

              <div class="sur-terms-h">9. 개인정보의 안전성 확보 조치</div>
              <p class="sur-terms-p">'링크(주)'는 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적·관리적 및 물리적 조치를 하고 있습니다.</p>
              <p class="sur-terms-p indent">(1) 정기적인 자체 감사 실시 : 개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.</p>
              <p class="sur-terms-p indent">(2) 개인정보 취급 직원의 최소화 및 교육 : 개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화하여 개인정보를 관리하는 대책을 시행하고 있습니다.</p>
              <p class="sur-terms-p indent">(3) 내부관리계획의 수립 및 시행 : 개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</p>
              <p class="sur-terms-p indent">(4) 해킹 등에 대비한 기술적 대책 : '링크(주)'는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신·점검을 하며, 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적·물리적으로 감시 및 차단하고 있습니다.</p>
              <p class="sur-terms-p indent">(5) 개인정보의 암호화 : 이용자의 개인정보 중 비밀번호는 암호화되어 저장 및 관리되고 있어 본인만이 알 수 있으며, 중요한 데이터는 파일 및 전송 데이터를 암호화하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.</p>
              <p class="sur-terms-p indent">(6) 접속기록의 보관 및 위변조 방지 : 개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관·관리하고 있으며, 접속 기록이 위변조 및 도난·분실되지 않도록 보안기능을 사용하고 있습니다.</p>
              <p class="sur-terms-p indent">(7) 개인정보에 대한 접근 제한 : 개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여·변경·말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며, 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</p>
              <p class="sur-terms-p indent">(8) 문서보안을 위한 잠금장치 사용 : 개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 안전한 장소에 보관하고 있습니다.</p>
              <p class="sur-terms-p indent">(9) 비인가자에 대한 출입 통제 : 개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립·운영하고 있습니다.</p>

              <div class="sur-terms-h">10. 개인정보 보호책임자</div>
              <p class="sur-terms-p">(1) '링크(주)'는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.</p>
              <p class="sur-terms-p indent">▶ 개인정보 보호책임자</p>
              <table class="sur-terms-tbl"><tbody>
                <tr><th>성명</th><td>윤한성</td></tr>
                <tr><th>직책</th><td>실장</td></tr>
                <tr><th>직급</th><td>이사</td></tr>
                <tr><th>연락처</th><td>Tel. 1544-5194 / E-mail. yhs@kcp.co.kr / Fax. 02-868-5194</td></tr>
              </tbody></table>
              <p class="sur-terms-p indent">▶ 개인정보 보호 담당부서</p>
              <table class="sur-terms-tbl"><tbody>
                <tr><th>부서명</th><td>CX팀</td></tr>
                <tr><th>담당자</th><td>김영덕</td></tr>
                <tr><th>연락처</th><td>Tel. 02-851-9635 / E-mail. ydkim.lk@lynk.co.kr / Fax. 02-868-5194</td></tr>
              </tbody></table>
              <p class="sur-terms-p">※ 개인정보 보호 담당부서로 연결됩니다.</p>
              <p class="sur-terms-p">(2) 정보주체께서는 '링크(주)'의 서비스(또는 사업)를 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. '링크(주)'는 정보주체의 문의에 대해 지체 없이 답변 및 처리해 드릴 것입니다.</p>

              <div class="sur-terms-h">11. 개인정보 처리방침 변경</div>
              <p class="sur-terms-p">이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가·삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.</p>

              <div class="sur-terms-h">12. 자동수집 장치의 설치·운용 및 그 거부에 관한 사항</div>
              <p class="sur-terms-p">회사는 이용자 개개인에게 개인화되고 맞춤화된 서비스를 제공하기 위해 이용자의 정보를 저장하고 수시로 불러오는 '쿠키(cookie)'를 사용합니다.</p>
              <p class="sur-terms-p indent">(1) 쿠키의 사용 목적 : 회원과 비회원의 접속 빈도나 방문 시간 등의 분석, 이용자의 취향과 관심분야의 파악 및 자취 추적, 각종 이벤트 참여 정도 및 방문 회수 파악 등을 통한 타겟 마케팅 및 개인 맞춤 서비스 제공</p>
              <p class="sur-terms-p indent">(2) 쿠키 설정 거부 방법 : 이용자는 쿠키 설치에 대해 거부할 수 있습니다. 단, 쿠키 설치를 거부하였을 경우 로그인이 필요한 일부 서비스의 이용이 어려울 수 있습니다.</p>
              <p class="sur-terms-p indent2">- 설정방법</p>
              <p class="sur-terms-p indent2">① IE 기준 : 웹 브라우저 상단의 도구 &gt; 인터넷 옵션 &gt; 개인정보 &gt; 사이트 차단</p>
              <p class="sur-terms-p indent2">② 크롬 기준 : 웹 브라우저 상단의 더보기 &gt; 설정 &gt; 하단에서 고급 &gt; '개인정보 및 보안'에서 사이트 설정 &gt; 쿠키 및 사이트 데이터 &gt; 타사 쿠키 차단</p>
            </div>

            <!-- 불법스팸 방지·서비스 이용제한 정책 -->
            <div class="sur-terms-sub">불법스팸 방지·서비스 이용제한 정책</div>
            <div class="sur-terms-scroll">
              <p class="sur-terms-p"><b>[ 불법스팸 방지·서비스 이용제한 정책 ]</b></p>
              <p class="sur-terms-p">정보통신망 이용촉진 및 정보보호 등에 관한 법률(정보통신망법) 제50조에 의거하여 모든 휴대폰 스팸 SMS 메시지에 대해서 전송을 원천적으로 금하고 있습니다.</p>

              <div class="sur-terms-h">1. 용어정리</div>
              <p class="sur-terms-p indent">ο 스팸 : 정보통신망을 통해 수신자의 명시적인 사전 동의 없이 일방적으로 전송되는 영리 목적의 광고성 정보로, 수신자의 의사에 반하여 전달되는 영리를 목적으로 하는 모든 유형의 상업성 정보 (정보통신망법 제50조 제1항)</p>
              <p class="sur-terms-p indent">ο (불법) 스팸 SMS : 정보통신망법을 위반하여 전송하는 영리목적의 광고성 정보 메시지. 사전에 수신동의를 하지 않은 고객에게 전송하는 SMS 및 야간시간(21시~8시)에 전송되는 모든 광고성 SMS</p>
              <p class="sur-terms-p indent">ο Opt-In : 사전에 수신을 동의한 고객에게만 광고 SMS를 발송할 수 있는 방식</p>

              <div class="sur-terms-h">2. 스팸 SMS 전송 정책·규제방안</div>
              <p class="sur-terms-p indent">- 모든 상업성 SMS는 Opt-In 방식, 즉 사전동의를 받은 고객에 대해서만 전송이 가능합니다.</p>
              <p class="sur-terms-p indent">- 야간 시간(오후 9시부터 그 다음날 오전 8시까지)의 광고성 정보 전송이 금지됩니다. 야간 시간에 광고성 정보를 전송하실 경우 별도의 추가 사전 동의를 받으셔야 합니다.</p>
              <p class="sur-terms-p indent">- 청소년에게 전송되는 모든 음란성 휴대폰 메시지는 일체 금지됩니다.</p>
              <p class="sur-terms-p indent">- 광고성 정보를 전송할 때에는 광고성 정보가 시작되는 부분에 '(광고)'를 표시하여야 하며, 전송자의 명칭 및 연락처를 기재하여야 합니다. 또한 수신 거부 또는 수신동의 철회 방식을 구체적으로 밝히셔야 하고, 수신 거부 또는 수신동의 철회 시 수신자가 비용을 부담하지 아니함을 반드시 표시하여야 합니다.</p>
              <p class="sur-terms-p indent">- 과거에 사용하셨으나 현재는 해지된 전화번호이거나 실제로 통화 연결이 불가능한 전화번호의 경우 발신번호가 차단될 수 있습니다.</p>
              <p class="sur-terms-p indent">- 본 정책을 위반할 시 회사는 서비스 이용을 제한·중단할 수 있으며, 정책 위반으로 인한 서비스 사용중지 시 회원의 사이버머니(캐쉬), 마일리지포인트는 현금으로 환불되지 않고 소멸됩니다.</p>
              <p class="sur-terms-p indent">- 본 정책을 위반할 경우 관리자는 정보통신망법에 따라 방송통신위원회 및 한국인터넷진흥원, 불법스팸대응센터에 스팸 전송자에 대한 신고 및 자료(성명, 주소, 주민등록번호, 이용기간, 연락처 등) 제공이 가능합니다.</p>
              <p class="sur-terms-p indent">- 휴대폰 문자광고는 "[광고]" 문구와 무료 수신거부번호 080번호를 꼭 기입하셔야 합니다.</p>

              <div class="sur-terms-h">3. 700, 060, 030 발송제한</div>
              <p class="sur-terms-p indent">700, 060, 030 번호를 회신번호 또는 메시지 내용에 사용하는 광고성 SMS의 경우에는 반드시 사전에 수신동의 여부를 확인할 수 있는 자료를 관리자에게 제출하여야 합니다.</p>
              <div class="sur-terms-h">4. 서비스 제한 및 역무제공 거부</div>
              <p class="sur-terms-p indent">- 회사는 회원이 본 정책 또는 정보통신망법 제50조 등 관계 법령을 위반하거나 위반이 의심되는 경우, 사전 통지 없이 해당 메시지의 발송을 제한하거나 발신번호 사용을 정지하고, 회원에 대한 서비스 제공을 거부·정지 또는 이용계약을 해지할 수 있습니다.</p>
              <p class="sur-terms-p indent">- 회사는 제한·거부·해지 등의 조치를 취한 경우 그 사유와 조치 내용을 회원에게 통지하며, 조치 결과를 관계 법령이 정한 기간 동안 보관합니다.</p>
              <p class="sur-terms-p indent">- 회원이 본 정책을 위반하여 회사 또는 제3자에게 손해가 발생한 경우 그에 대한 민사상·형사상 책임은 회원 본인에게 있으며, 회사는 관계 법령에 따라 방송통신위원회, 한국인터넷진흥원(불법스팸대응센터) 등에 관련 사실 및 자료를 신고·제공할 수 있습니다.</p>
              <div class="sur-terms-h">5. 문자 내 URL 검사 및 차단</div>
              <p class="sur-terms-p indent">문자에 포함된 URL은 악성여부 판정 대상이 될 수 있으며, 판정 결과에 따라 발송이 차단될 수 있습니다. 차단된 URL 관련 정보는 한국인터넷진흥원(KISA)에 제공될 수 있습니다.</p>
              <p class="sur-terms-p">모든 회원께서는 스팸 SMS 전송 정책·관리규정을 준수하여 주시기 바랍니다. 이용에 불편이 없도록 최선을 다하겠습니다.</p>
            </div>

            </div>
          </div>
        </div>

        <div class="sur-actions">
          <button class="sur-btn sur-btn-primary" ng-disabled="!isAllAgreed()" ng-click="goStep(2)">다음</button>
        </div>
      </div>

      <!-- ===================== STEP 2: 등록 화면 ===================== -->
      <div ng-show="step==2">
        <div class="sur-page-head">
          <div class="sur-page-title">SMS 사용 등록</div>
          <div class="sur-page-desc">본인인증을 완료하면 인증된 휴대폰 번호로 SMS 사용 등록이 진행됩니다.</div>
        </div>

        <div class="sur-stepper">
          <div class="sur-step sur-done"><div class="sur-step-circle">✓</div><div class="sur-step-label">약관 동의</div></div>
          <div class="sur-step-line sur-done"></div>
          <div class="sur-step sur-current"><div class="sur-step-circle">2</div><div class="sur-step-label">본인인증 · 등록</div></div>
          <div class="sur-step-line"></div>
          <div class="sur-step"><div class="sur-step-circle">3</div><div class="sur-step-label">완료</div></div>
        </div>

        <div class="sur-card">
          <div class="sur-card-head"><h2>계정 정보</h2></div>
          <div class="sur-card-body" style="padding-top:6px; padding-bottom:6px;">
            <div class="sur-field-row">
              <div class="sur-field-label">사용자 아이디</div>
              <div class="sur-field-value">{{userId}}</div>
            </div>
            <div class="sur-field-row">
              <div class="sur-field-label">사용자명</div>
              <div class="sur-field-value">{{userNm}}</div>
            </div>
          </div>
        </div>

        <div class="sur-card">
          <div class="sur-card-head">
            <h2>본인인증</h2>
            <span class="sur-badge sur-badge-required">필수</span>
          </div>
          <div class="sur-card-body">

            <!-- 인증 전 / 진행중 -->
            <div ng-show="verifyState=='before' || verifyState=='loading'">
              <div class="sur-verify-box">
                <div class="sur-verify-icon">
                  <svg width="20" height="20" viewBox="0 0 20 20" fill="none"><path d="M6 2h8a1 1 0 011 1v14a1 1 0 01-1 1H6a1 1 0 01-1-1V3a1 1 0 011-1z" stroke="#1B5EE3" stroke-width="1.5"/><path d="M9 15h2" stroke="#1B5EE3" stroke-width="1.5" stroke-linecap="round"/></svg>
                </div>
                <div class="sur-verify-text" ng-if="verifyState=='before'">SMS 수신·로그인에 사용할 휴대폰 번호 확인을 위해<br>본인인증이 필요합니다.</div>
                <div class="sur-verify-text" ng-if="verifyState=='loading'">본인인증 창을 확인해주세요…</div>
                <button class="sur-btn sur-btn-primary" ng-if="verifyState=='before'" ng-click="runVerify()">본인인증 시작</button>
              </div>
            </div>

            <!-- 중복 오류 -->
            <div class="sur-dup-error" ng-show="verifyState=='duplicate'">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="none" style="flex-shrink:0; margin-top:1px;"><circle cx="8" cy="8" r="7" stroke="#D3413C" stroke-width="1.5"/><path d="M8 4.5V9" stroke="#D3413C" stroke-width="1.5" stroke-linecap="round"/><circle cx="8" cy="11.2" r="0.9" fill="#D3413C"/></svg>
              <div style="flex:1;">
                <div>이미 다른 계정(<b>{{dupUserId}}</b>)에 등록된 본인인증 정보입니다. 한 사람은 하나의 SMS 사용 계정만 등록할 수 있습니다.</div>
                <button class="sur-btn sur-btn-outline sur-btn-sm" style="margin-top:10px;" ng-click="resetVerify()">다시 시도</button>
              </div>
            </div>

            <!-- 본인인증 실패 -->
            <div ng-show="verifyState=='fail'">
              <div class="sur-verify-fail-box">
                <div class="sur-verify-fail-icon">
                  <svg width="18" height="18" viewBox="0 0 18 18" fill="none"><path d="M5 5L13 13M13 5L5 13" stroke="#D3413C" stroke-width="1.8" stroke-linecap="round"/></svg>
                </div>
                <div class="sur-verify-fail-title">본인인증에 실패했습니다</div>
                <div class="sur-verify-fail-desc">인증 시간이 초과되었거나 인증 정보가 확인되지 않았습니다.<br>다시 시도해주세요.</div>
                <div class="sur-verify-fail-reason">사유: {{verifyFailReason}}</div>
                <button class="sur-btn sur-btn-primary" ng-click="resetVerify()">다시 인증하기</button>
              </div>
            </div>

            <!-- 인증 완료 -->
            <div ng-show="verifyState=='success'">
              <div class="sur-verified-result">
                <div class="sur-vi-icon"><svg width="11" height="9" viewBox="0 0 11 9" fill="none"><path d="M1 4.5L4 7.5L10 1" stroke="white" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
                <div style="flex:1;">
                  <div class="sur-verified-result-title">본인인증이 완료되었습니다</div>
                  <div class="sur-verified-result-desc">중복가입 확인 결과, 등록 가능한 정보입니다.</div>
                  <div class="sur-verified-info">
                    <div class="sur-verified-info-row"><span class="sur-k">성명</span><span class="sur-v">{{verifiedInfo.userNm}}</span></div>
                    <div class="sur-verified-info-row"><span class="sur-k">휴대폰</span><span class="sur-v">{{verifiedInfo.telNo}}</span></div>
                    <div class="sur-verified-info-row"><span class="sur-k">인증시각</span><span class="sur-v">{{verifiedInfo.verifyDt}}</span></div>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>

        <div class="sur-card">
          <div class="sur-card-body" style="display:flex; gap:10px; align-items:flex-start;">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none" style="flex-shrink:0; margin-top:1px;"><circle cx="8" cy="8" r="7" stroke="#1B5EE3" stroke-width="1.5"/><path d="M8 5v3.5" stroke="#1B5EE3" stroke-width="1.5" stroke-linecap="round"/><circle cx="8" cy="11" r="0.9" fill="#1B5EE3"/></svg>
            <div style="font-size:12.5px; color:var(--sur-ink-3); line-height:1.6;">발신번호는 위 인증된 휴대폰 번호로만 <b style="color:var(--sur-ink-2);">[휴대폰본인인증]</b> 하여 추가 등록할 수 있습니다.</div>
          </div>
        </div>

        <div class="sur-actions">
          <button class="sur-btn sur-btn-outline" ng-click="goStep(1)">이전</button>
        </div>
      </div>

      <!-- ===================== STEP 3: 완료 ===================== -->
      <div ng-show="step==3">
        <div class="sur-card">
          <div class="sur-done-wrap">
            <div class="sur-done-icon">
              <svg width="26" height="22" viewBox="0 0 26 22" fill="none"><path d="M2 11L9.5 18.5L24 2" stroke="#1C9A5B" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"/></svg>
            </div>
            <div class="sur-done-title">SMS 사용 등록이 완료되었습니다</div>
            <div class="sur-done-desc">다음 로그인부터 인증된 휴대폰 번호로 로그인 인증번호를 받게 됩니다.</div>

            <div class="sur-done-summary">
              <div class="sur-field-row">
                <div class="sur-field-label">사용자 아이디</div>
                <div class="sur-field-value">{{userId}}</div>
              </div>
              <div class="sur-field-row">
                <div class="sur-field-label">인증 휴대폰</div>
                <div class="sur-field-value">{{verifiedInfo.telNo}}</div>
              </div>
              <div class="sur-field-row">
                <div class="sur-field-label">등록일시</div>
                <div class="sur-field-value">{{verifiedInfo.verifyDt}}</div>
              </div>
            </div>

          </div>
        </div>

        <div class="sur-card">
          <div class="sur-card-body" style="display:flex; gap:10px; align-items:flex-start;">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none" style="flex-shrink:0; margin-top:1px;"><circle cx="8" cy="8" r="7" stroke="#1B5EE3" stroke-width="1.5"/><path d="M8 5v3.5" stroke="#1B5EE3" stroke-width="1.5" stroke-linecap="round"/><circle cx="8" cy="11" r="0.9" fill="#1B5EE3"/></svg>
            <div style="font-size:12.5px; color:var(--sur-ink-3); line-height:1.6;">발신번호는 위 인증된 휴대폰 번호로만 <b style="color:var(--sur-ink-2);">[휴대폰본인인증]</b> 하여 추가 등록할 수 있습니다.</div>
          </div>
          <div class="sur-delete-row">
            <button class="sur-btn-danger-outline" ng-click="deleteRegist()">SMS 사용 등록 삭제</button>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>

<script type="text/javascript">
    var suUserId = "${sessionScope.sessionInfo.userId}";
    var suUserNm = "${sessionScope.sessionInfo.userNm}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsUserRegist/smsUserRegist.js?ver=20260813.01" charset="utf-8"></script>
