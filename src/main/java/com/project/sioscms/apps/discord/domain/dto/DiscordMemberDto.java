package com.project.sioscms.apps.discord.domain.dto;

import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

public class DiscordMemberDto {
    @Getter
    @Setter
    public static class Request extends CommonSearchDto {
        private Long id;    //고유 아이디
        private String userId;  //아이디
        private String username; //닉네임
        private String globalName;  //닉네임(전체)
        private String discriminator;   //역할
        private Boolean isDeleted;  //삭제여부
    }

    @Getter
    @Setter
    public static class Response{
        private Long id;    //고유 아이디
        private String userId;  //아이디
        private String username; //닉네임
        private String globalName;  //닉네임(전체)
        private String discriminator;   //역할
        private Boolean isDeleted;  //삭제여부
    }
}
