package smart.hub.mappings.api.models.request.payengine;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrowserInfo {

    private String acceptHeader = "brw-accept-header";
    private String ip = "192.168.0.1";
    private Boolean javaEnabled = true;
    private String language = "en-US";
    private Integer colorDepth = 8;
    private Integer screenHeight = 100;
    private Integer screenWidth = 100;
    private Integer timezone = 60;
    private String userAgent = "Chrome/71.0.3578.98";
    private String windowSize = "05";

}
