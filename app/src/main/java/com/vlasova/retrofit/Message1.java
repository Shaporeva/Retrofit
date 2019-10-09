package com.vlasova.retrofit;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message1 {


        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("totalResults")
        @Expose
        private Integer totalResults;
        @SerializedName("articles")
        @Expose
        private List<Article> articles = null;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Message1 withStatus(String status) {
            this.status = status;
            return this;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

        public Message1 withTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
            return this;
        }

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }

        public Message1 withArticles(List<Article> articles) {
            this.articles = articles;
            return this;
        }
}
