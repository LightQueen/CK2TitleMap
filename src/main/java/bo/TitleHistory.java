package bo;

import lombok.Data;

@Data
public class TitleHistory {
    private String title; // 名称
    private String s_d; // 开始
    private String e_d; // 结束，解析时由下一个 TitleHistory 得到
    private Integer hold_days; // =结束-开始 的天数
    private Integer expired_days; // =现在-结束 的天数
    private String holder;
    private Boolean isHolding; // 最后一个历史时需要标识 isHolding

    public Title getTitle(){
        return null;
    }

    public Character getCharacter(){
        return null;
    }
}
