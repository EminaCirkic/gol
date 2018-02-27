package com.company;


import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException{

        Ticks tick=new Ticks();
        Cell cell=new Cell();
        GameBoard gameBoard=new GameBoard();
        List<Cell> cells= new ArrayList<>();
        List<Cell> newgencells=new ArrayList<>();
        List<Ticks> generations=new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance());
        Scanner sc=new Scanner(System.in);

        args=validateInput(args,sc,df);

        cells=assignObjectValuesFromInput(args,tick,gameBoard,cells,cell);

        populateGameBoardWithLivingAndDeadCells(gameBoard,cells,newgencells);

        Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));


        if(tick.getTicks()==0){
            terminal.enterPrivateMode();
            updateScreen(terminal,gameBoard.getCells());
        } else{
            for (int i=0; i<tick.getTicks();i++) {
            gameBoard=updateNewGeneration(gameBoard);
            generations.add(new Ticks(tick.getTicks(),gameBoard));
            }
            terminal.enterPrivateMode();
            updateScreen(terminal,generations.get(tick.getTicks()-1).getGames().getCells());}
        }

    private static String[] validateInput(String[] args,Scanner sc,DecimalFormat df) throws ParseException {
            for(int x=0;x<args.length;x++){
                if( !args[x].matches("[0-9, /,]+")){
                    System.out.println("Please enter a number for the "+(x+1)+" character in command line");
                    args[x]=sc.nextLine();
                }else if (df.parse(args[x]).floatValue() < 0) {
                    System.out.println("Please enter a positive for the "+(x+1)+" number in command line");
                    args[x]=sc.nextLine();
                }
            }
            return args;
        }

    private static List<Cell> assignObjectValuesFromInput(String[] args,Ticks tick,GameBoard gameBoard,List<Cell> cells,Cell cell)  {

        if (args.length > 0)
            for (int x=0;x<args.length;x++) {

                if (x == 0)
                    tick.setTicks(Integer.parseInt(args[x]));
                if (x == 1)
                    gameBoard.setColumn(Integer.parseInt(args[x]));
                if (x == 2)
                    gameBoard.setRow(Integer.parseInt(args[x]));
                else if (x > 2) {
                    List<String> coordinates = Arrays.asList(args[x].split(","));
                    for (int i = 0; i < coordinates.size(); i++) {
                        if (i == 0)
                            cell.setX(Integer.parseInt(coordinates.get(i)));
                        else {
                            cell.setY(Integer.parseInt(coordinates.get(i)));
                            cells.add(new Cell(cell.getX(), cell.getY(), true, 'X', Terminal.Color.GREEN));
                        }
                    }
                }

            }

        return cells;

    }

    private static void populateGameBoardWithLivingAndDeadCells(GameBoard gameBoard, List<Cell> cells, List<Cell> newgencells) {

        //populate board with dead cells
        //iterate rows..y
        for(int j=0;j<gameBoard.getRow();j++) {
            //iterate  columns..x
            for (int i = 0; i < gameBoard.getColumn(); i++) {

                newgencells.add(new Cell(i,j,false,'.',Terminal.Color.RED));

            }
        }

        //replace dead cells with alive
        for (int x=0;x<newgencells.size();x++) {
            for (Cell alivecells:cells) {
                if (newgencells.get(x).getX() == alivecells.getX() && newgencells.get(x).getY() == alivecells.getY())
                    newgencells.set(x,new Cell(alivecells.getX(), alivecells.getY(), true, 'X', Terminal.Color.GREEN));
            }
        }

        gameBoard.setCells(newgencells);
    }

    private static GameBoard updateNewGeneration(GameBoard gameBoard){
        List<Cell> newcells=new ArrayList<>();
        for (int x=0;x<gameBoard.getCells().size();x++) {
            int neighbours= checkMyLivingNeighbours(gameBoard.getCells().get(x),gameBoard.getCells());
            newcells.add( newstate(neighbours,gameBoard.getCells().get(x)));
        }
        GameBoard newgameBoard=new GameBoard(gameBoard.getRow(),gameBoard.getColumn(),newcells);

        return newgameBoard;
    }

    private static Cell newstate(int neighbour,Cell cell){

        if(neighbour<=1){
            cell=new Cell(cell.getX(),cell.getY(),false,'.',Terminal.Color.RED); }
        else if(neighbour==2){
            cell=new Cell(cell.getX(),cell.getY(),cell.getStatus(),cell.getDisplaychar(),cell.getColor()); }
        else if(neighbour==3){
            cell=new Cell(cell.getX(),cell.getY(),true,'X',Terminal.Color.GREEN); }
        else{
            cell=new Cell(cell.getX(),cell.getY(),false,'.',Terminal.Color.RED);}

        return cell;

    }

    private static int checkMyLivingNeighbours(Cell cell,List<Cell> currentcells){

        int neighbours = 0;

        for (int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
            for (int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
                if(x!=cell.getX() || y!=cell.getY()){
                    for (Cell nowcell : currentcells) {
                        if (nowcell.getX() == x && nowcell.getY() == y) {
                            if (nowcell.getStatus()) {
                                neighbours++;
                            }
                        }
                    }
                }

            }
        }
        return neighbours;
    }

    private static void updateScreen(Terminal terminal, List<Cell> newbatchofcells) {
        terminal.clearScreen();

        for (Cell newcells:newbatchofcells) {
            terminal.moveCursor(newcells.getX(), newcells.getY());
            terminal.putCharacter(newcells.getDisplaychar());
        }

    }


}
