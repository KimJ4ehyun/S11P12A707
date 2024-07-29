package com.ssafy.top.bans.application;

import com.ssafy.top.bans.domain.Bans;
import com.ssafy.top.bans.domain.BansRepository;
import com.ssafy.top.bans.dto.request.BanAddRequest;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BansService {
    private final UsersRepository usersRepository;
    private final BansRepository bansRepository;

    public CommonResponseDto<List<String>> addBan(Long userId, BanAddRequest banAddRequest) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 소문자로 변환 + 공백 삭제
        String name = banAddRequest.getName().toLowerCase().replace(" ", "");

        // URL or App
        if(name.contains(".")) { // URL
            if(!isValidDomain(name)) {
                throw new CustomException(INVALID_DOMAIN);
            }
        }

        // 이미 사용자가 등록한 경우
        Optional<Bans> ban = bansRepository.findByUserIdAndName(userId, name);
        if(ban.isPresent()) {
            throw new CustomException(BAN_ALREADY_ADDED);
        }

        // 등록
        bansRepository.save(banAddRequest.toEntity(user));

        List<String> result = bansRepository.findByUser(userId);

        return new CommonResponseDto<>(result, "금지 목록에 추가되었습니다.", 201);
    }

    public static boolean isValidDomain(String domain) {
        // 도메인 이름의 정규 표현식
        String DOMAIN_REGEX = "^(?!-)[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\\.[A-Za-z]{2,})+$";

        // 정규 표현식 패턴 컴파일
        Pattern DOMAIN_PATTERN = Pattern.compile(DOMAIN_REGEX);

        // 각 항목의 길이 체크
        String[] parts = domain.split("\\.");
        for (String part : parts) {
            if (part.length() < 2 || part.length() > 63) {
                return false;
            }
        }

        // 정규 표현식으로 도메인 체크
        return DOMAIN_PATTERN.matcher(domain).matches();
    }
}
