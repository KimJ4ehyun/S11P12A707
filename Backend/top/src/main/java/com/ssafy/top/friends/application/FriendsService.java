package com.ssafy.top.friends.application;

import com.ssafy.top.friends.domain.Friends;
import com.ssafy.top.friends.domain.FriendsPK;
import com.ssafy.top.friends.domain.FriendsRepository;
import com.ssafy.top.friends.dto.response.FriendsResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ssafy.top.friends.domain.Relation.ACCEPTED;
import static com.ssafy.top.friends.domain.Relation.WAITING;
import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendsService {
    private final UsersRepository usersRepository;
    private final FriendsRepository friendsRepository;

    @Transactional(readOnly = true)
    public CommonResponseDto<List<FriendsResponse>> getFriends(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<FriendsResponse> result = usersRepository.findUsersByUserId(user.getId())
                .stream()
                .map(FriendsResponse::toDto)
                .toList();

        return new CommonResponseDto<>(result, "친구 목록 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> requestFriends(String email, Long friendId) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 존재하지 않는 사용자에게 친구 신청
        Users friend = usersRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 자기 자신에게 친구 신청
        if(user.getId().equals(friendId)) {
            throw new CustomException(FRIEND_SELF_REQUEST);
        }

        FriendsPK userToFriendPK = new FriendsPK(user.getId(), friendId);
        FriendsPK friendToUserPK = new FriendsPK(friendId, user.getId());

        Optional<Friends> userToFriend = friendsRepository.findById(userToFriendPK);
        Optional<Friends> friendToUser = friendsRepository.findById(friendToUserPK);

        if(userToFriend.isPresent()) {
            Friends existingUserToFriend = userToFriend.get();

            // 이미 친구 신청 중
            if(existingUserToFriend.getRelation().equals(WAITING)) {
                throw new CustomException(FRIEND_ALREADY_REQUESTING);
            }

            // 이미 친구
            if(existingUserToFriend.getRelation().equals(ACCEPTED)) {
                throw new CustomException(FRIEND_ALREADY_ADDED);
            }
        }
        
        // 나에게 친구 신청을 건 경우 -> 자동으로 친구
        if(friendToUser.isPresent()) {
            friendsRepository.updateRelation(friendToUserPK, ACCEPTED);
            friendsRepository.save(new Friends(userToFriendPK, user, friend, ACCEPTED));
        } else {
            friendsRepository.save(new Friends(userToFriendPK, user, friend, WAITING));
        }

        return new CommonResponseDto<>("친구 신청에 성공했습니다.", 201);
    }

    public CommonResponseDto<?> responseFriends(String email, Long friendId) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 존재하지 않는 사용자에게 친구 신청
        Users friend = usersRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        FriendsPK userToFriendPK = new FriendsPK(user.getId(), friendId);
        FriendsPK friendToUserPK = new FriendsPK(friendId, user.getId());

        Optional<Friends> friendToUser = friendsRepository.findById(friendToUserPK);

        // 존재하지 않는 친구 신청
        if(friendToUser.isEmpty()) {
            throw new CustomException(FRIEND_REQUEST_MISSING);
        }

        Friends existingFriendToUser = friendToUser.get();

        // 이미 친구
        if(existingFriendToUser.getRelation().equals(ACCEPTED)) {
            throw new CustomException(FRIEND_ALREADY_ADDED);
        }

        friendsRepository.updateRelation(friendToUserPK, ACCEPTED);
        friendsRepository.save(new Friends(userToFriendPK, user, friend, ACCEPTED));

        return new CommonResponseDto<>("친구 신청 수락에 성공했습니다.", 201);
    }

    public CommonResponseDto<?> declineFriends(String email, Long friendId) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 존재하지 않는 사용자에게 친구 신청
        Users friend = usersRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        FriendsPK friendToUserPK = new FriendsPK(friendId, user.getId());

        Optional<Friends> friendToUser = friendsRepository.findById(friendToUserPK);

        // 존재하지 않는 친구 신청
        if(friendToUser.isEmpty()) {
            throw new CustomException(FRIEND_REQUEST_MISSING);
        }

        Friends existingFriendToUser = friendToUser.get();

        // 이미 친구
        if(existingFriendToUser.getRelation().equals(ACCEPTED)) {
            throw new CustomException(FRIEND_ALREADY_ADDED);
        }

        friendsRepository.delete(existingFriendToUser);

        return new CommonResponseDto<>("친구 신청 거절에 성공했습니다.", 204);
    }

    public CommonResponseDto<?> cancelFriends(String email, Long friendId) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 존재하지 않는 사용자에게 친구 신청
        Users friend = usersRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        FriendsPK userToFriendPK = new FriendsPK(user.getId(), friendId);

        Optional<Friends> userToFriend = friendsRepository.findById(userToFriendPK);
        
        // 존재하지 않는 친구 신청
        if(userToFriend.isEmpty()) {
            throw new CustomException(FRIEND_REQUEST_MISSING);
        }
        
        Friends existingUserToFriend = userToFriend.get();
        
        if(existingUserToFriend.getRelation().equals(ACCEPTED)) {
            throw new CustomException(FRIEND_ALREADY_ADDED);
        }
        
        friendsRepository.delete(existingUserToFriend);

        return new CommonResponseDto<>("친구 신청 취소에 성공했습니다.", 204);
    }

    public CommonResponseDto<?> deleteFriends(String email, Long friendId) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 존재하지 않는 사용자와의 친구 삭제
        Users friend = usersRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        FriendsPK userToFriendPK = new FriendsPK(user.getId(), friendId);
        FriendsPK friendToUserPK = new FriendsPK(friendId, user.getId());

        Optional<Friends> userToFriend = friendsRepository.findById(userToFriendPK);
        Optional<Friends> friendToUser = friendsRepository.findById(friendToUserPK);

        if(userToFriend.isEmpty()) {
            throw new CustomException(BAD_REQUEST);
        }

        if(friendToUser.isEmpty()) {
            throw new CustomException(BAD_REQUEST);
        }

        Friends existingUserToFriend = userToFriend.get();
        Friends existingFriendToUser = friendToUser.get();

        friendsRepository.delete(existingUserToFriend);
        friendsRepository.delete(existingFriendToUser);

        return new CommonResponseDto<>("친구 삭제에 성공했습니다.", 204);
    }
}
