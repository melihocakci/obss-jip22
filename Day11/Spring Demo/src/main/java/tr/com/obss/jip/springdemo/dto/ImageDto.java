package tr.com.obss.jip.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
public class ImageDto {

    @NotNull
    private int width;

    @NotNull
    private int height;
}