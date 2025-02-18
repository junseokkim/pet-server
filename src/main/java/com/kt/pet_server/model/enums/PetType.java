package com.kt.pet_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetType {

    DOG("강아지"),
    CAT("고양이"),
    TURTLE("거북이"),
    BIRD("새"),
    RABBIT("토끼"),
    HAMSTER("햄스터"),
    FISH("물고기"),
    REPTILE("파충류"),
    OTHER("그 이외");

    private final String description;

}
