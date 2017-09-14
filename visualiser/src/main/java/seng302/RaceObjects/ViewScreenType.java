package seng302.RaceObjects;

public enum ViewScreenType {
    MENU(0),
    LOBBY(1),
    GAME(2),
    SCORE_SCREEN(3),
    MENU_ERROR(4),
    MENU_SERVER_CLOSED(5);


    private int viewScreenType;

    ViewScreenType(int viewScreenType){
        this.viewScreenType = viewScreenType;
    }

    public int getViewScreenType(){
        return this.viewScreenType;
    }
}
