package common.enums;

public enum FileEnum {

    INCOMPLETE_GAMES_BY_UUID("database/incompleteGamesByUUID"),
    PLAYERS_BY_TOKEN("database/playersByToken"),
    USERS_BY_TOKEN("database/userNamesByToken");

    private String filePath;


    FileEnum(String filePath) {
        this.filePath = filePath;
    }

    public static FileEnum fromPath(String fileName) {
        for (FileEnum f : FileEnum.values())
            if (f.getFilePath().equals(fileName))
                return f;
        return null;
    }


    public String getFilePath() {
        return filePath;
    }
}
