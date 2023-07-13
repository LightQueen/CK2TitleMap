package bo;

import lombok.Data;

// 390行
@Data
public class Dynasty extends CKO {
    private String code;
    private String name; // 因为religion特性，所以允许空


}
