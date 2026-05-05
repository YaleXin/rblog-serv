package top.yalexin.rblog.entity;

import java.util.List;

public class KeywordsWrapper {
    private List<WordCloud> keywords;

    public KeywordsWrapper() {
    }

    public List<WordCloud> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<WordCloud> keywords) {
        this.keywords = keywords;
    }
}
