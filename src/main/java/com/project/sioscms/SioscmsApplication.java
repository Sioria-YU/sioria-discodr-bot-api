package com.project.sioscms;

import io.github.key_del_jeeinho.cacophony_lib.autoconfigure.UseCacophony;
import io.github.key_del_jeeinho.cacophony_lib.domain.event.events.chat.ChatEvent;
import io.github.key_del_jeeinho.cacophony_lib.domain.event.events.react.ReactEvent;
import io.github.key_del_jeeinho.cacophony_lib.domain.event.listeners.EventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static io.github.key_del_jeeinho.cacophony_lib.domain.entry.EntryEntry.*;
import static io.github.key_del_jeeinho.cacophony_lib.domain.flow.FlowEntry.when;

@EnableJpaAuditing
@UseCacophony
@SpringBootApplication
public class SioscmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SioscmsApplication.class, args);

        //discord example
        when(
                onDM().and().onJoin()
        ).doAction(
                event -> System.out.println(event.getClass().getSimpleName() + " 가 발생하였습니다!")
        ).complete();

        when(
                onChat()
        ).doAction(
                (EventListener<ChatEvent>) event -> System.out.println(event.getMessage())
        ).complete();

        when(
                onReact()
        ).doAction(
                (EventListener<ReactEvent>) event -> System.out.println("타입 : " + event.getEventType() + "이모지 : " + event.getEmote() + "채널 : " + event.getChannel().getId())
        ).complete();
    }

}
