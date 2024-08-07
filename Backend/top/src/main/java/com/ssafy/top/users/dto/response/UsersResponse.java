package com.ssafy.top.users.dto.response;

import com.ssafy.top.friends.domain.Relation;
import com.ssafy.top.users.domain.FriendsRelation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static com.ssafy.top.friends.domain.Relation.ACCEPTED;
import static com.ssafy.top.friends.domain.Relation.WAITING;
import static com.ssafy.top.users.domain.FriendsRelation.*;

@Schema(description = "유저 검색 응답 DTO")
@Data
public class UsersResponse {

    @Schema(description = "유저 ID(PK)")
    private Long userId;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "친구 관계 상태")
    private FriendsRelation relation;

    public UsersResponse(Object[] o) {
        this.userId  = (Long) o[0];
        this.nickname = (String) o[1];

        Relation relation = (Relation) o[2];

        if(relation == null) {
            this.relation = NOT_FRIENDS;
        } else if(relation.equals(ACCEPTED)) {
            this.relation = FRIENDS;
        } else if(relation.equals(WAITING)) {
            this.relation = WAITING_FRIEND_REQUEST;
        }
    }
}
