package com.ssafy.top.widgets.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import com.ssafy.top.widgets.domain.Widgets;
import com.ssafy.top.widgets.domain.WidgetsRepository;
import com.ssafy.top.widgets.dto.WidgetsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.top.global.exception.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class WidgetsService {
    private final UsersRepository usersRepository;
    private final WidgetsRepository widgetsRepository;

    @Transactional(readOnly = true)
    public CommonResponseDto<?> getWidgets(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<WidgetsDto> widgets = widgetsRepository.findAllByUserId(user.getId())
                .stream()
                .map(WidgetsDto::toDto)
                .toList();

        return new CommonResponseDto<>(widgets, "위젯 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> saveWidgets(String email, List<WidgetsDto> widgets) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        widgetsRepository.deleteAllByUserId(user.getId());

        List<Widgets> saveWidgets = widgets
                .stream()
                .map(widgetDto -> widgetDto.toEntity(user))
                .collect(Collectors.toList());

        widgetsRepository.saveAll(saveWidgets);

        return new CommonResponseDto<>("위젯 저장에 성공했습니다.", 201);
    }
}
