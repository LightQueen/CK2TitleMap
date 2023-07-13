package bo;

import java.util.EnumSet;

public enum Chapter {
    TITLE("title"),
    DYNASTY("dynasty"),
    CHARACTER("character");

    private final String status;

    Chapter(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    // 实现字符串转枚举的静态方法 传入 title
    public static Chapter fromStatus(String status) {
        if (status == null) {
            return null;
        }

        return EnumSet.allOf(Chapter.class).stream()
                .filter(s -> s.toString().equals(status))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + status));
    }

    // 实现字符串转枚举的静态方法 传入 TITLE
    public static Chapter fromName(String str){
        for (Chapter chapter : Chapter.values()) {
            if (chapter.equals(str)) {
                return chapter;
            }
        }
        return null;
    }
}
