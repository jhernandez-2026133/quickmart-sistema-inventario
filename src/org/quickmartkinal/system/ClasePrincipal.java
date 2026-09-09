/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.quickmartkinal.system;

import javafx.application.Application;
import javafx.stage.Stage;
import org.quickmartkinal.system.utils.SceneManager;
import org.quickmartkinal.system.utils.ViewFactory;

public class ClasePrincipal extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stageRoot) {
        SceneManager.getInstanciaScenerManager().setStagePrincipal(stageRoot);
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewLogin();
    }

}
