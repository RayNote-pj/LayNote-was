package com.project.lay_note_was.lay_note.service.implement;

import com.project.lay_note_was.lay_note.common.constant.ResponseMessage;
import com.project.lay_note_was.lay_note.dto.ResponseDto;
import com.project.lay_note_was.lay_note.dto.user.request.UserDeleteRequestDto;
import com.project.lay_note_was.lay_note.dto.user.request.UserUpdateRequestDto;
import com.project.lay_note_was.lay_note.dto.user.response.UserResponseDto;
import com.project.lay_note_was.lay_note.entity.user.User;
import com.project.lay_note_was.lay_note.repository.UserRepository;
import com.project.lay_note_was.lay_note.service.ImageService;
import com.project.lay_note_was.lay_note.service.UserService;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ImageService imageService;
    @Override
    public ResponseDto<UserResponseDto> getMyAccount(String userEmail) {
        try {
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));

            UserResponseDto data = new UserResponseDto(user);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<UserResponseDto> updateMyAccount(String userEmail, UserUpdateRequestDto dto) {
        try {
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));
            if (!user.getNickName().equals(dto.getNickName())) {
                if (userRepository.existsByNickName(dto.getNickName())) {
                    return ResponseDto.setFailed(ResponseMessage.DUPLICATED_NICKNAME);
                }
            }
            String imagePath = user.getProfileImageUrl();

            if (dto.getProfileImageUrl() != null && !dto.getProfileImageUrl().isEmpty()) {
                imagePath = imageService.convertImgFile(
                        dto.getProfileImageUrl(), "user-profile-image"
                );
            }
            if (!dto.getNickName().matches("^[A-Za-z가-힣0-9._]{2,14}$")) {
                return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "nickName");
            }
            if (!dto.getUserName().matches("^[A-Za-z가-힣]{2,16}$")) {
                return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "userName");
            }
            if (!dto.getUserPhone().matches("010\\d{8}$")) {
                return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "userPhone");
            }

            User response = user.toBuilder()
                    .userName(dto.getUserName())
                    .nickName(dto.getNickName())
                    .profileImageUrl(imagePath)
                    .userPhone(dto.getUserPhone())
                    .build();
            userRepository.save(response);

            UserResponseDto data = new UserResponseDto(response);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<Void> deleteUser(String userEmail, UserDeleteRequestDto dto) {
        try {
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));
            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
            }
            userRepository.delete(user);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
