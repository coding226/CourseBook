package com.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class JResponsePOJO {
    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("status")
    private boolean status;

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "JResponsePOJO{" +
                        "data = '" + data + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }

    public class DataItem{

        @SerializedName("details")
        private List<DetailsItem> details;

        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        public void setDetails(List<DetailsItem> details){
            this.details = details;
        }

        public List<DetailsItem> getDetails(){
            return details;
        }

        public void setId(int id){
            this.id = id;
        }

        public int getId(){
            return id;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public String getTitle(){
            return title;
        }

        @Override
        public String toString(){
            return
                    "DataItem{" +
                            "details = '" + details + '\'' +
                            ",id = '" + id + '\'' +
                            ",title = '" + title + '\'' +
                            "}";
        }

        public class DetailsItem{

            @SerializedName("topic")
            private String topic;

            @SerializedName("desc")
            private String desc;

            public void setTopic(String topic){
                this.topic = topic;
            }

            public String getTopic(){
                return topic;
            }

            public void setDesc(String desc){
                this.desc = desc;
            }

            public String getDesc(){
                return desc;
            }

            @Override
            public String toString(){
                return
                        "DetailsItem{" +
                                "topic = '" + topic + '\'' +
                                ",desc = '" + desc + '\'' +
                                "}";
            }
        }
    }
}