package server.database;

public enum FileEnum {

    TOKENS_BY_USER_NAME("files/tokensByUserName.json"),
    PLAYER_INFO_BY_TOKEN("files/playerInfoByToken.json"),
    GAME_PATH_BY_UUID("files/gamePathByToken.json");

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
