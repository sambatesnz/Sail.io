package seng302.Visualiser;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import seng302.Controllers.Coordinate;
import seng302.Controllers.TimeZoneWrapper;
import seng302.RaceObjects.Race;

/**
 * This is a work in progress
 * In future I would like to use this to take in the race dependency and calculate the utc based off that
 */
public class LocalTime extends Label {

  Race race;

  public LocalTime(String text, Race race) {
      super(text);
      this.race = race;
      setFont(new Font("Arial", 15));
      setVisible(true); //TODO This should be set to visible only when utc is set correctly

      setLayoutX(Coordinate.getWindowWidthX() - 110);
      setLayoutY(100);
      setText(getLocalTimeString());
  }

    public LocalTime(String s) {
        super(s);
    }


    private String getLocalTimeString(){
      int utc = race.getRegatta().getUtcOffset();
      TimeZoneWrapper timeZoneWrapper = new TimeZoneWrapper(utc);
      return timeZoneWrapper.getLocalTimeString();
    }
}
