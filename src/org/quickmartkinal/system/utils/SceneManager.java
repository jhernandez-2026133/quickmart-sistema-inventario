package org.quickmartkinal.system.utils;

<<<<<<< HEAD
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SceneManager {
    private static SceneManager instanciaSceneManager;
    private Stage stagePrincipal;
    
    private SceneManager() {}
    
    public static SceneManager getInstanciaSceneManager(){
        if ( instanciaSceneManager == null )
             instanciaSceneManager = new SceneManager();
        return instanciaSceneManager;
    }
    
    public void chanceScene ( Scene scene ){
=======
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author informatica
 */
public class SceneManager {

    private static SceneManager instanciaSceneManager;
    private Stage stagePrincipal;

    private SceneManager() {
    }

    public static SceneManager getInstanciaScenerManager() {
        if (instanciaSceneManager == null) {
            instanciaSceneManager = new SceneManager();
        }
        return instanciaSceneManager;
    }

    public void changeScene(Scene scene) {
>>>>>>> 05be444258df5c84736a3d2340d725e86c358462
        try {
            stagePrincipal.setScene(scene);
            stagePrincipal.sizeToScene();
            stagePrincipal.show();
<<<<<<< HEAD
        } catch (NullPointerException objetoNulo) {
        }
    
=======

        } catch (NullPointerException objetoNulo) {
            //ALERT
        }
>>>>>>> 05be444258df5c84736a3d2340d725e86c358462
    }

    public Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }
<<<<<<< HEAD
    
=======

>>>>>>> 05be444258df5c84736a3d2340d725e86c358462
}
