package id.aliqornan.thedict.model;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by qornanali on 27/03/18.
 */

public class Word implements Serializable {

    private int id;
    private String text;
    private boolean isEnglish;
    private String translation;

    public Word() {
    }

    public Word(String text, boolean isEnglish, String translation) {
        this.text = text;
        this.isEnglish = isEnglish;
        this.translation = translation;
    }

    public Word(int id, String text, boolean isEnglish, String translation) {
        this.id = id;
        this.text = text;
        this.isEnglish = isEnglish;
        this.translation = translation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setEnglish(boolean english) {
        isEnglish = english;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "Word{" +
                " text='" + text + '\'' +
                ", isEnglish=" + isEnglish +
                ", translation='" + translation + '\'' +
                '}';
    }


}
