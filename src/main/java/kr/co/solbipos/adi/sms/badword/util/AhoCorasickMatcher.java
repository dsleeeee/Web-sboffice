package kr.co.solbipos.adi.sms.badword.util;

import kr.co.solbipos.adi.sms.badword.service.BadwordVO;

import java.util.*;

/**
 * @Class Name : AhoCorasickMatcher.java
 * @Description : Aho-Corasick 다중 패턴 문자열 매칭 (순수 Java 구현 — 외부 라이브러리 미사용)
 *
 *  contains 타입 금칙어를 대상으로 트리를 빌드하고,
 *  단일 텍스트 스캔으로 모든 패턴을 동시에 탐색한다.
 *
 *  시간복잡도 : O(n + m)  n=텍스트길이, m=매치수
 *  공간복잡도 : O(SIGMA * k)  SIGMA=문자집합크기, k=전체 패턴 길이 합
 *
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.06.26  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.06.26
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class AhoCorasickMatcher {

    // ------------------------------------------------------------------ //
    // 내부 트리 노드
    // ------------------------------------------------------------------ //
    private static class Node {
        final Map<Character, Node> children = new HashMap<>();
        Node failure;
        BadwordVO output; // 이 노드에서 끝나는 패턴 (없으면 null)
    }

    private final Node root;

    // ------------------------------------------------------------------ //
    // 생성 — 트리 빌드 후 failure link 설정까지 완료
    // ------------------------------------------------------------------ //

    /**
     * @param keywords contains 타입 BadwordVO 목록
     *                 (keyword_normalized 가 있으면 그 값을, 없으면 keyword 를 TextNormalizer 로 정규화해서 사용)
     */
    public AhoCorasickMatcher(List<BadwordVO> keywords) {
        root = new Node();
        root.failure = root;

        for (BadwordVO bw : keywords) {
            insert(resolvePattern(bw), bw);
        }
        buildFailureLinks();
    }

    // ------------------------------------------------------------------ //
    // 검색 — 첫 번째 매치만 반환 (탐지 즉시 중단)
    // ------------------------------------------------------------------ //

    /**
     * normalizedText 에서 처음으로 매치된 금칙어를 반환한다.
     * 없으면 null.
     */
    public BadwordVO findFirst(String normalizedText) {
        Node cur = root;
        for (int i = 0; i < normalizedText.length(); i++) {
            char c = normalizedText.charAt(i);
            cur = step(cur, c);
            if (cur.output != null) {
                return cur.output;
            }
        }
        return null;
    }

    // ------------------------------------------------------------------ //
    // 트리 구축
    // ------------------------------------------------------------------ //

    private static String resolvePattern(BadwordVO bw) {
        if (bw.getKeywordNormalized() != null && !bw.getKeywordNormalized().isEmpty()) {
            return bw.getKeywordNormalized();
        }
        return TextNormalizer.normalize(bw.getKeyword());
    }

    private void insert(String pattern, BadwordVO bw) {
        if (pattern == null || pattern.isEmpty()) return;
        Node cur = root;
        for (char c : pattern.toCharArray()) {
            cur = cur.children.computeIfAbsent(c, k -> new Node());
        }
        if (cur.output == null) {
            cur.output = bw;
        }
    }

    /** BFS 로 failure link 와 output link 를 설정한다. */
    private void buildFailureLinks() {
        Queue<Node> queue = new ArrayDeque<>();

        // 루트의 직계 자식: failure = root
        for (Node child : root.children.values()) {
            child.failure = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Map.Entry<Character, Node> entry : cur.children.entrySet()) {
                char c = entry.getKey();
                Node child = entry.getValue();

                // failure link: cur의 failure 체인을 따라 c 로 갈 수 있는 최장 진정접미사 노드
                Node fail = cur.failure;
                while (fail != root && !fail.children.containsKey(c)) {
                    fail = fail.failure;
                }
                child.failure = fail.children.getOrDefault(c, root);
                if (child.failure == child) {
                    child.failure = root;
                }

                // output link: failure 노드가 출력을 가지면 이어받음
                if (child.output == null && child.failure.output != null) {
                    child.output = child.failure.output;
                }

                queue.add(child);
            }
        }
    }

    /** 현재 노드에서 문자 c 로 전이. 없으면 failure 체인을 따라간다. */
    private Node step(Node cur, char c) {
        while (cur != root && !cur.children.containsKey(c)) {
            cur = cur.failure;
        }
        return cur.children.getOrDefault(c, root);
    }
}
