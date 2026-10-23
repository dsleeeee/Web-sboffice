package kr.co.common.utils;

/**
 * SMS 발신번호 규격 검사
 * - 거짓으로 표시된 전화번호로 인한 이용자의 피해 예방 등에 관한 고시(과기정통부고시 제2026-48호) 발신번호 규격
 *   1. 기본: 전체 번호수 8~11자리
 *   2. 15YY·16YY·18YY 대표번호: 8자리
 *   3. 030·050으로 시작하는 번호: 12자리까지
 *   4. 112·1335 등 특수번호: 국가·지자체·공공기관만 가능 → 매장·본사 이용자 대상이므로 등록·발송 불가
 */
public final class SmsCallbackValidator {

    /** 검사 결과 */
    public enum Result {
        OK, EMPTY, NOT_DIGIT, LENGTH, REP_LENGTH, SPECIAL;

        public boolean isOk() { return this == OK; }
    }

    private SmsCallbackValidator() {}

    /** 공백·하이픈 제거 */
    public static String normalize(String callback) {
        return callback == null ? "" : callback.replaceAll("[\\s-]", "");
    }

    /** 발신번호 규격 검사 */
    public static Result validate(String callback) {
        String telNo = normalize(callback);

        if (telNo.isEmpty()) {
            return Result.EMPTY;
        }
        if (!telNo.matches("\\d+")) {
            return Result.NOT_DIGIT;
        }
        // 15YY·16YY·18YY 등 대표번호 : 8자리만
        if (telNo.matches("1[5-9]\\d+")) {
            return telNo.length() == 8 ? Result.OK : Result.REP_LENGTH;
        }
        // 030·050 : 12자리까지
        if (telNo.startsWith("030") || telNo.startsWith("050")) {
            return (telNo.length() >= 10 && telNo.length() <= 12) ? Result.OK : Result.LENGTH;
        }
        // 일반 번호 : 8~11자리
        if (telNo.startsWith("0")) {
            return (telNo.length() >= 8 && telNo.length() <= 11) ? Result.OK : Result.LENGTH;
        }
        // 112·1335 등 특수번호
        return Result.SPECIAL;
    }

    /** 발신번호 규격 통과 여부 */
    public static boolean isValid(String callback) {
        return validate(callback).isOk();
    }
}
