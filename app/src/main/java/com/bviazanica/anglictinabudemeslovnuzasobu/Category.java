package com.bviazanica.anglictinabudemeslovnuzasobu;

import java.util.ArrayList;

public class Category {
    public String category_id;
    public String category_sk;
    public String category_eng;
    public ArrayList<Phrase> strings;

    public Category(String category_id, String category_sk, String category_eng, ArrayList<Phrase> strings) {

        this.category_id = category_id;
        this.category_sk = category_sk;
        this.category_eng = category_eng;
        this.strings = strings;
    }

}
