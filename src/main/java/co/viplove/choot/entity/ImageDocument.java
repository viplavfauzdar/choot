package co.viplove.choot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ImageDocument {

    @JsonProperty("_id")
    private Id id;
    private String filename;
    private Integer length;
    private Integer chunkSize;
    private UploadDate uploadDate;
    private Metadata metadata;

    @Data
    class Id {
        @JsonProperty("$oid")
        private String oid;
    }

    @Data
    public class UploadDate {
        @JsonProperty("$date")
        private String date;
    }

    @Data
    public class Metadata {
        @JsonProperty("content_type")
        private String contentType;
    }

}
