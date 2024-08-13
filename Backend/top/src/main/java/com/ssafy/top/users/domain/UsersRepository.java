package com.ssafy.top.users.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findById(Long userId);

    @Query("SELECT COUNT(u) FROM Users u")
    int findTotalUserCount();

    @Query("select a.id, a.nickname, b.relation " +
            "from Users a " +
            "left join Friends b on b.friendsPK.userId = :userId and a.id = b.friendsPK.friendId " +
            "where a.id != :userId and a.nickname like '%' || :nickname || '%' " +
            "order by a.nickname")
    List<Object[]> findUsersByNickname(@Param("userId") Long userId, @Param("nickname") String nickname);

    @Query("select a " +
            "from Users a " +
            "join Friends b on a.id = b.friendsPK.friendId " +
            "where b.friendsPK.userId = :userId and b.relation = 'ACCEPTED' ")
    List<Users> findUsersByUserId(@Param("userId") Long userId);

    @Query("select a " +
            "from Users a " +
            "where a.nickname = :nickname ")
    Optional<Users> findUserByNickname(@Param("nickname") String nickname);
}
