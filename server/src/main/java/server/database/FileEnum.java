package server.database;

import static common.Constants.DATA_PATH;

public enum FileEnum {

    TOKENS_BY_USER_NAME(DATA_PATH + "tokensByUserName.json"),
    PLAYER_INFO_BY_TOKEN(DATA_PATH + "playerInfoByToken.json"),
    GAME_PATH_BY_UUID(DATA_PATH + "gamePathByToken.json");

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
