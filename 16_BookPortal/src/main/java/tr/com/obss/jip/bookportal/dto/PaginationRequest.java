package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaginationRequest {
    private Integer size;

    private Integer page;

    private String sortField;

    private String sortOrder;

    private String searchParam;
}
