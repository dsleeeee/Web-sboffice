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
            // 이 위치에서 끝나는 패턴이 있는지 failure 체인으로 확인
            for (Node t = cur; t != root; t = t.failure) {
                if (t.output != null) {
                    return t.output;
                }
            }
        }
        return null;
    }

    /**
     * normalizedText 에서 매치된 금칙어를 "전부" 반환한다. (2026.08.26 다건 이력 저장 대응)
     *  - 각 위치에서 failure 체인을 따라가며 겹치는/포함된 패턴까지 모두 수집
     *  - 동일 금칙어가 여러 번 등장하면 "등장 횟수만큼" 리스트에 담긴다 (이력도 등장 횟수만큼 저장)
     *  - 반환 순서는 본문에서 발견된 순서
     * 없으면 빈 리스트.
     */
    public List<BadwordVO> findAll(String normalizedText) {
        List<BadwordVO> hits = new ArrayList<>();
        Node cur = root;
        for (int i = 0; i < normalizedText.length(); i++) {
            char c = normalizedText.charAt(i);
            cur = step(cur, c);
            // 현재 노드부터 failure 체인을 따라가며 이 위치에서 끝나는 패턴을 모두 수집
            // (노드마다 자기 패턴만 갖고 있으므로 한 위치에서 같은 패턴이 이중 집계되지 않음)
            for (Node t = cur; t != root; t = t.failure) {
                if (t.output != null) {
                    hits.add(t.output);
                }
            }
        }
        return hits;
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

                // (2026.08.26) output link 상속 제거
                //  - 기존: failure 노드의 출력을 자식이 이어받음 → 등장 횟수 집계 시
                //    같은 위치에서 동일 패턴이 이중 집계되는 문제 발생
                //  - 검색(findFirst/findAll)에서 failure 체인을 직접 따라가며 수집하므로
                //    상속 없이도 겹치는/포함된 패턴 탐지에 누락 없음

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
