package co.viplove.choot.poc2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CopyPropTest {

    public static void main(String[] args) {
        CopyProp copyProp = new CopyProp();
        copyProp.setFname("John");

        CopyProp1 copyProp1 = new CopyProp1();
        copyProp1.setLname("Doe");
        copyProp1.setCopyProp(copyProp);

        log.info("CopyProp: {}", copyProp);
        log.info("CopyProp1: {}", copyProp1);
    }
}
