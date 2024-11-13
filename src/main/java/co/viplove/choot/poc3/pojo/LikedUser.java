
package co.viplove.choot.poc3.pojo;

import lombok.Data;

import java.util.List;

@Data
public class LikedUser {

    public Long identity;
    public List<String> labels;
    public Properties__2 properties;
    public String elementId;

}
