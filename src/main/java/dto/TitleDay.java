package dto;

import lombok.Data;

@Data
public class TitleDay {
    private String title;
    private Integer hold_days;
    private Integer expired_days;
}
