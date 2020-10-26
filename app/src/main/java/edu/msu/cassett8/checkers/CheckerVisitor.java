package edu.msu.cassett8.checkers;

public class CheckerVisitor {
    private boolean isVisitedWhite;

    private boolean isVisitedGreen;

    public void visitedGreen(){
        isVisitedGreen=true;
    }
    public boolean checkVisitGreen()
    {
        return isVisitedGreen;
    }
    public void visitedWhite(){
        isVisitedWhite=true;
    }
    public boolean checkVisitWhite()
    {
        return isVisitedWhite;
    }
}
