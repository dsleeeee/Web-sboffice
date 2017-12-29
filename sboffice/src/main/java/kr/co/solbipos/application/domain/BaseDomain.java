package kr.co.solbipos.application.domain;

import java.io.Serializable;

public abstract class BaseDomain implements Serializable {
    private static final long serialVersionUID = -3606930451970509346L;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
