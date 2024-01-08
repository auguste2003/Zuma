package controller;

import model.XYTupel;

public interface IZumaDeluxeController {

    void nextFrame();
    void newProjektil(float x , float y);
    void userInput(char a);


    int bekommtPreviewType();

}
