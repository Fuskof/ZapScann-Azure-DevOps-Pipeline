package smart.hub.services.jira.enums;


public enum IssueTypeKeyEnum {
    TASK("10103"),
    SUBTASK("10900"),
    EPIC("10000"),
    BUG("10203"),
    STORY("10001"),
    TEST("10500");
    private String value;

    private IssueTypeKeyEnum(String value)
    { this.value = value; }

    public String getKey() {
        return value;
    }
}
