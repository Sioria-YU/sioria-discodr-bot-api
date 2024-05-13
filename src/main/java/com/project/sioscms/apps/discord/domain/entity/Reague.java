package com.project.sioscms.apps.discord.domain.entity;

import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.discord.domain.dto.ReagueDto;
import com.project.sioscms.apps.discord.mapper.ReagueMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Reague extends CommonEntityWithIdAndDate {
    @NotNull
    @Comment("리그명")
    private String reagueName;

    @NotNull
    @Comment("리그 알림 제목")
    private String title;

    @Comment("리그 알림 설명")
    private String description;

    @Comment("리그 알림 색상")
    private String color;

    @Comment("리그 시작일")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate startDate;

    @Comment("리그 종료일")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate endDate;
    
    @Comment("리그 시간")
    @Convert(converter = Jsr310JpaConverters.LocalTimeConverter.class)
    private LocalTime reagueTime;

    @Comment("첨부파일(이미지)")
    @ManyToOne
    private AttachFileGroup attachFileGroup;

    @Comment("삭제여부")
    @ColumnDefault(value = "FALSE")
    private Boolean isDeleted;

    public ReagueDto.Response toResponse() { return ReagueMapper.mapper.toResponse(this); }
}
