package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponse {

    private List<?> list;

    private Long total;
}
