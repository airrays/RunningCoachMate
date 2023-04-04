package coachingmateanalytics.coachingmate.common.utils;

import lombok.Data;

@Data
public class SearchRequest {
    private String tags;
    private String categories;
    private String keyword;
    private Paging paging;
    private String sorting;

}
