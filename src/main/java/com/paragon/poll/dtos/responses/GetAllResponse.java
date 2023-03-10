package com.paragon.poll.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllResponse {
    private List<GetResponse> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private boolean last;
}
