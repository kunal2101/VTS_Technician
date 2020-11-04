package com.rtl.vts_technician.Pojo;

public class ExampleItem {
    private int imageResource;
    private String text1;
    private String text2;

    public ExampleItem(String text1, String text2) {

        this.text1 = text1;
        this.text2 = text2;
    }

    public ExampleItem(String text1) {
        this.text1 = text1;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
