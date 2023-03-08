package shared.logging;

public enum LogCategory {

    SYSTEM("logs/System"),
    ROBOT("logs/Robot")
    ;

    private String folderName;

    LogCategory(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
