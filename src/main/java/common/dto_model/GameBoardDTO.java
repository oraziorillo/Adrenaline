package common.dto_model;

import java.util.List;

public class GameBoardDTO implements DTO {

    private int rows;
    private int columns;
    private List<SquareDTO> squares;


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<SquareDTO> getSquares() {
        return squares;
    }

    public void setSquares(List<SquareDTO> squares) {
        this.squares = squares;
    }

}
