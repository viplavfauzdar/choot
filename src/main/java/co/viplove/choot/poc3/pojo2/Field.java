package co.viplove.choot.poc3.pojo2;

import lombok.Data;

import java.util.List;

@Data
public class Field {

    public Identity identity;
    public List<String> labels;
    public Properties properties;
    public String elementId;

}
