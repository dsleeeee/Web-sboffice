package kr.co.solbipos.adi.sms.badword.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Class Name : TextNormalizer.java
 * @Description : 금칙어 탐지를 위한 메시지 텍스트 정규화 유틸 (KISA 가이드 기준)
 *                - 소문자 변환
 *                - 전각문자 → 반각문자 치환 (2단계 제거 전에 선처리)
 *                - 공백/특수문자 제거 (한글·영문·숫자만 유지)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.06.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.06.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class TextNormalizer {

    // 전각문자 → 반각문자 치환 맵 (Java 8 호환 — Map.of 미사용)
    private static final Map<Character, Character> SIMILAR_CHAR_MAP;
    static {
        SIMILAR_CHAR_MAP = new HashMap<Character, Character>();
        SIMILAR_CHAR_MAP.put('０', '0');
        SIMILAR_CHAR_MAP.put('１', '1');
        SIMILAR_CHAR_MAP.put('２', '2');
        SIMILAR_CHAR_MAP.put('３', '3');
        SIMILAR_CHAR_MAP.put('４', '4');
        SIMILAR_CHAR_MAP.put('５', '5');
        SIMILAR_CHAR_MAP.put('６', '6');
        SIMILAR_CHAR_MAP.put('７', '7');
        SIMILAR_CHAR_MAP.put('８', '8');
        SIMILAR_CHAR_MAP.put('９', '9');
        SIMILAR_CHAR_MAP.put('Ａ', 'a'); SIMILAR_CHAR_MAP.put('ａ', 'a');
        SIMILAR_CHAR_MAP.put('Ｂ', 'b'); SIMILAR_CHAR_MAP.put('ｂ', 'b');
        SIMILAR_CHAR_MAP.put('Ｃ', 'c'); SIMILAR_CHAR_MAP.put('ｃ', 'c');
        SIMILAR_CHAR_MAP.put('Ｄ', 'd'); SIMILAR_CHAR_MAP.put('ｄ', 'd');
        SIMILAR_CHAR_MAP.put('Ｅ', 'e'); SIMILAR_CHAR_MAP.put('ｅ', 'e');
        SIMILAR_CHAR_MAP.put('Ｆ', 'f'); SIMILAR_CHAR_MAP.put('ｆ', 'f');
        SIMILAR_CHAR_MAP.put('Ｇ', 'g'); SIMILAR_CHAR_MAP.put('ｇ', 'g');
        SIMILAR_CHAR_MAP.put('Ｈ', 'h'); SIMILAR_CHAR_MAP.put('ｈ', 'h');
        SIMILAR_CHAR_MAP.put('Ｉ', 'i'); SIMILAR_CHAR_MAP.put('ｉ', 'i');
        SIMILAR_CHAR_MAP.put('Ｊ', 'j'); SIMILAR_CHAR_MAP.put('ｊ', 'j');
        SIMILAR_CHAR_MAP.put('Ｋ', 'k'); SIMILAR_CHAR_MAP.put('ｋ', 'k');
        SIMILAR_CHAR_MAP.put('Ｌ', 'l'); SIMILAR_CHAR_MAP.put('ｌ', 'l');
        SIMILAR_CHAR_MAP.put('Ｍ', 'm'); SIMILAR_CHAR_MAP.put('ｍ', 'm');
        SIMILAR_CHAR_MAP.put('Ｎ', 'n'); SIMILAR_CHAR_MAP.put('ｎ', 'n');
        SIMILAR_CHAR_MAP.put('Ｏ', 'o'); SIMILAR_CHAR_MAP.put('ｏ', 'o');
        SIMILAR_CHAR_MAP.put('Ｐ', 'p'); SIMILAR_CHAR_MAP.put('ｐ', 'p');
        SIMILAR_CHAR_MAP.put('Ｑ', 'q'); SIMILAR_CHAR_MAP.put('ｑ', 'q');
        SIMILAR_CHAR_MAP.put('Ｒ', 'r'); SIMILAR_CHAR_MAP.put('ｒ', 'r');
        SIMILAR_CHAR_MAP.put('Ｓ', 's'); SIMILAR_CHAR_MAP.put('ｓ', 's');
        SIMILAR_CHAR_MAP.put('Ｔ', 't'); SIMILAR_CHAR_MAP.put('ｔ', 't');
        SIMILAR_CHAR_MAP.put('Ｕ', 'u'); SIMILAR_CHAR_MAP.put('ｕ', 'u');
        SIMILAR_CHAR_MAP.put('Ｖ', 'v'); SIMILAR_CHAR_MAP.put('ｖ', 'v');
        SIMILAR_CHAR_MAP.put('Ｗ', 'w'); SIMILAR_CHAR_MAP.put('ｗ', 'w');
        SIMILAR_CHAR_MAP.put('Ｘ', 'x'); SIMILAR_CHAR_MAP.put('ｘ', 'x');
        SIMILAR_CHAR_MAP.put('Ｙ', 'y'); SIMILAR_CHAR_MAP.put('ｙ', 'y');
        SIMILAR_CHAR_MAP.put('Ｚ', 'z'); SIMILAR_CHAR_MAP.put('ｚ', 'z');
    }

    private TextNormalizer() {}

    public static String normalize(String text) {
        if (text == null || text.isEmpty()) return "";

        // 1. 소문자 변환
        String result = text.toLowerCase();

        // 2-1. 다중문자 치환 (단일문자 맵 처리 전에 먼저 치환)
        result = result.replace("ＩＩ", "ü");

        // 2-2. 전각문자 → 반각문자 치환 (특수문자 제거 전에 먼저 처리해야 살아남음)
        StringBuilder sb = new StringBuilder(result.length());
        for (int i = 0; i < result.length(); i++) {
            char c = result.charAt(i);
            Character mapped = SIMILAR_CHAR_MAP.get(c);
            sb.append(mapped != null ? mapped : c);
        }
        result = sb.toString();

        // 3. 공백·특수문자 제거 (한글·영문·숫자만 유지)
        result = result.replaceAll("[^가-힣a-z0-9]", "");

        return result;
    }
}
