package me.bubbles.dailyrewards.util;

public enum Values {
    NO_PERMS("&cYou do not have permission to do that! &4%node%"),
    EPOCH_DAY(86400);
    private String name;
    private long num;
    Values(String name) {
        this.name=name;
    }

    Values(int num) {
        this.num=num;
    }
    public String getString() {
        if(name==null) {
            return String.valueOf(num);
        }
        return name;
    }

    public long getNum() {
        return num;
    }

}
