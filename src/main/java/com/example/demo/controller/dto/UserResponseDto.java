package com.example.demo.controller.dto;

import com.example.demo.repository.entity.Message;
import com.example.demo.repository.entity.Team;
import com.example.demo.repository.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {
    private Integer id;
    private String username;
    private String name;
    private String job;
    private String specialty;
    private TeamDto team;
    private List<MessageDto> messages;

    public static UserResponseDto from(User entity) {
        TeamDto teamDto = entity.getTeam() != null ? TeamDto.from(entity.getTeam()) : null;
        List<MessageDto> messageDtos = entity.getMessages() != null
                ? entity.getMessages().stream()
                        .map(MessageDto::from)
                        .toList()
                : List.of();

        return new UserResponseDto(
                entity.getId(),
                entity.getUsername(),
                entity.getName(),
                entity.getJob(),
                entity.getSpecialty(),
                teamDto,
                messageDtos
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TeamDto {
        private Integer id;
        private String name;

        public static TeamDto from(Team team) {
            return new TeamDto(team.getId(), team.getName());
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MessageDto {
        private Integer id;
        private String content;
        private LocalDateTime createdAt;

        public static MessageDto from(Message message) {
            return new MessageDto(
                    message.getId(),
                    message.getContent(),
                    message.getCreatedAt()
            );
        }
    }
}