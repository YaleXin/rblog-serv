package top.yalexin.rblog.entity;

public class WordCloud {
    private String word;
    private Integer weight;

    public WordCloud() {
    }

    public WordCloud(String word, Integer weight) {
        this.word = word;
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "WordCloud{" +
                "word='" + word + '\'' +
                ", weight=" + weight +
                '}';
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
