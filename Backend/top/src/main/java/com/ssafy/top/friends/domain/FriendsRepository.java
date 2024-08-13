package com.ssafy.top.friends.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendsRepository extends JpaRepository<Friends, FriendsPK> {
    @Modifying
    @Query("update Friends a set a.relation = :relation where a.friendsPK = :friendsPK ")
    void updateRelation(@Param("friendsPK") FriendsPK friendsPK, @Param("relation") Relation relation);
}
