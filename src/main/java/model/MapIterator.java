package model;

public class MapIterator {

    private Tile currTile;

    public MapIterator(Tile firstTile){
        currTile = firstTile;
    }

    public boolean hasNext() {
        int currX = currTile.getX();
        int currY = currTile.getY();
        int mapWidth = currTile.getCurrGame().map[0].length;
        int mapHeight = currTile.getCurrGame().map.length;
        return ((currX < mapWidth) || (currY < mapHeight));
    }

    public Tile next() {
        if (currTile.getX() < currTile.getCurrGame().map[0].length)
            return currTile.getCurrGame().getTile(currTile.getX() + 1, currTile.getY());
        else {
            return currTile.getCurrGame().getTile(0, currTile.getY() + 1);
        }
    }
}
