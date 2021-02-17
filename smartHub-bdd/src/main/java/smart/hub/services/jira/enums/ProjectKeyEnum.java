package smart.hub.services.jira.enums;


public enum ProjectKeyEnum {
    DISE("12800");
    private String value;

    private ProjectKeyEnum(String value)
    { this.value = value; }

    public String getKey() {
        return value;
    }
}
