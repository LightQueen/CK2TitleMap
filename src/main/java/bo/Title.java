package bo;

import lombok.Data;


// Cairo
// 645350行
@Data
public class Title extends CKO{
    private String name; // 名称 c_cairo d_cairo k_egypt e_arabia d_sunni k_iraq(未创建) d_baghdad c_karbala b_ainaltamur
    private String dynasty; // 因为religion特性，所以可能为空
    private String holder; // 持有人
    private String liege; // 实际上级
    private String de_jure_liege; // 法理上级
    //private String level; // 名称前缀 // 没用

    public Title getLiege() {
        return null;
    }

    public Character getHolder() {
        return null;
    }

    public Dynasty getDynasty() {
        // 可能需要通过 holder 查询
        return null;
    }
}
