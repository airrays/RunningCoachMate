package coachingmateanalytics.coachingmate.common.utils;

import lombok.Data;

@Data
public class Paging {
    private Integer total;
    private Integer currentPage;
    private Integer pageSize;
}
