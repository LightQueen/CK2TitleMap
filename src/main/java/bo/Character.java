package bo;

import lombok.*;

import java.util.Arrays;
import java.util.List;

// 125401行

@Data
@NoArgsConstructor
public class Character extends CKO {
    private String code;

    // 个人信息
    private String fem; // yes

    private String dnt; // Dynasty

    private String fat; // Character

    private String mot; // Character

    private String b_d;
    private String d_d;

    // 成绩
    private String prs;
    private String piety;
    private String wealth;

    // 上级
    private String host; // Character

    // 生成
    private int broSeq;

    private List<String> titles;
    private List<String> realms; // 所有地

    private Integer score;

    private String level;

    //private List<Character> vassals; //藕合太高了 别用

    public Dynasty getDynasty() {
        return null;
    }

    public Character getFather(){
        return null;
    }

    public Character getMother(){
        return null;
    }

    public Character getHost(){
        return null;
    }


    public void refreshScore() {
        int score = 0;
        for (String title:getTitles()) {
            switch (title.substring(0,1)) {
                case "b": score+=1;break;
                case "c": score+=5;break;
                case "d": score+=10;break;
                case "k": score+=20;break;
                case "e": score+=50;break;
            }
        }
        this.score = score;
    }

    public Integer getScore(){
        if (score == null) {
            refreshScore();
        }
        return score;
    }


    private void refreshLevel() {
        int level = 0;
        List<String> levels = Arrays.asList(new String[]{"b", "c", "d", "k", "e"});
        for (String title : getTitles()) {
            level = Math.max(level, levels.indexOf(title.substring(0,1)));
            if(level == 4) {
                this.level = "e";
                return;
            }
        }
        this.level = levels.get(level);
    }

    public String getLevel(){
        if (level == null) {
            refreshLevel();
        }
        return level;
    }

}
