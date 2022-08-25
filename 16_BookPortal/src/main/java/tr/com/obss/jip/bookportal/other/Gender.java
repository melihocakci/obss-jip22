package tr.com.obss.jip.bookportal.other;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Gender {
    MALE("male"),

    FEMALE("female"),

    OTHER("other");

    final String type;

    Gender(String type) {
        this.type = type;
    }

    @JsonCreator
    @JsonValue
    String json() {
        return type;
    }

}
