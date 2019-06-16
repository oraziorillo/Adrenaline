package server.database;

public enum FileEnum {

    TOKENS_BY_USER_NAME("src/main/java/server/database/files/tokensByUserName.json"),
    PLAYER_INFO_BY_TOKEN("src/main/java/server/database/files/playerInfoByToken.json"),
    GAME_INFO_BY_UUID("src/main/java/server/database/files/gameInfoByToken.json");

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
