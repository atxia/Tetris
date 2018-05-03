/**
 * Tetris class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class Tetris implements ArrowListener
{
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        tetris.play();
    }

    private BoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;
    private int n;
    public Tetris()
    {
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        activeTetrad = new Tetrad(grid);
        display.setArrowListener(this);
        
    }

    public void upPressed()
    {
        activeTetrad.rotate();
        display.showBlocks();
    }

    public void downPressed()
    {
        activeTetrad.translate(1,0);
        display.showBlocks();
    }

    public void leftPressed()
    {
        activeTetrad.translate(0,-1);
        display.showBlocks();
    }

    public void rightPressed()
    {
        activeTetrad.translate(0,1);
        display.showBlocks();
    }

    public void spacePressed()
    {
        while(activeTetrad.translate(1,0)){}
        n = 0;
        display.showBlocks();
    }

    public void play()
    {
        while (true)
        {
            //if(this.topRowsEmpty()){
                //break;
            //}
            if(activeTetrad.translate(1,0) == false){
                activeTetrad = new Tetrad(grid);
                n = 0;
            }
            try { Thread.sleep(n); } catch(Exception e) {}
            //Insert Exercise 3.2 code here
            //Insert Exercise 3.3 code here
            this.clearCompletedRows();
            n = 1000;
            display.showBlocks();
        }
    }

    //precondition:  0 <= row < number of rows
    //postcondition: Returns true if every cell in the
    //               given row is occupied;
    //               returns false otherwise.
    private boolean isCompletedRow(int row)
    {
        boolean full = true;
        for(int i = 0; i < grid.getNumCols(); i++){
            Location l = new Location(row, i);
            if(grid.get(l)==null){
                full = false;
            }
        }
        return full;    // replace this line
    }

    //precondition:  0 <= row < number of rows;
    //               given row is full of blocks
    //postcondition: Every block in the given row has been
    //               removed, and every block above row
    //               has been moved down one row.
    private void clearRow(int row)
    {
        for(int i = 0; i<grid.getNumCols(); i++){
            Location l = new Location(row, i);
            if(grid.get(l)!=null)
                grid.remove(l).removeSelfFromGrid();

        };    // replace this line
    }

    //postcondition: All completed rows have been cleared.
    private void clearCompletedRows()
    {
        for(int i = grid.getNumRows()-1; i > 0; i--){
            if(isCompletedRow(i)){
                clearRow(i);
                moveDownAbove(i);
            }

        }    // replace this line
    }
    private void moveDownAbove(int r){
        if(r<1){
            return;
        }
        List<Location> locs = grid.getRow(r);
        for(Location l: locs){
            Location x = new Location(l.getRow()+1, l.getCol());
            if(grid.isValid(x)&&grid.get(x)==null){
                grid.remove(l).moveTo(x);
            }
        }
        moveDownAbove(r-1);
    }
    

    //returns true if top two rows of the grid are empty (no blocks), false otherwise
    private boolean topRowsEmpty()
    {
        boolean top = true;
        boolean atop = true;
        for(int i = 0; i<grid.getNumCols(); i++){
            Location l = new Location(0, i);
            if(grid.get(l)!=null){
                top = false;
            }
        }
        for(int i = 0; i<grid.getNumCols(); i++){
            Location l = new Location(1, i);
            if(grid.get(l)!=null){
                atop = false;
            }
        }
        return top && atop;   // replace this line
    }

}