package com.elwynn94.lolcome.entity;

import java.io.Serializable;
import java.util.Objects;

public class FollowId implements Serializable {
    private Long followerId;
    private Long followedId;

    public FollowId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(followerId, followId.followerId) &&
                Objects.equals(followedId, followId.followedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followedId);
    }
}