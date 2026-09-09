package org.quickmartkinal.system.utils;


import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.net.URL;
import javafx.fxml.JavaFXBuilderFactory;
import org.quickmartkinal.system.ClasePrincipal;


public class ViewFactory {

    private final String PATH_VIEWS="/org/quickmartkinal/system/view/";

    public Scene loadFileFXML(String nameFile, int width, int height){
        String pathOfFile = PATH_VIEWS + nameFile;

       try{
           FXMLLoader loadFXML = new FXMLLoader();
           URL urlFile =  ClasePrincipal.class.getResource(pathOfFile);
           loadFXML.setBuilderFactory( new JavaFXBuilderFactory() );
           loadFXML.setLocation(urlFile);

           return new Scene( loadFXML.load(), width, height);

       }catch (IOException e){
           throw new UncheckedIOException(e);
       }
    }

    public void loadScene( String nameFile){
        Scene scene = null;
        try {
            switch ( nameFile ) {
                case "login" -> {
                    SceneManager.getInstanciaSceneManager()
                            .getStagePrincipal().setTitle("Iniciar Sesion");
                    SceneManager.getInstanciaSceneManager()
                            .getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("loginView.fxml",400,500);
                }
                case "register" -> {
                       SceneManager.getInstanciaSceneManager()
                            .getStagePrincipal().setTitle("Registro de Usuario");
                    SceneManager.getInstanciaSceneManager()
                            .getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("RegisterView.fxml", 350, 400);
                }
                case "dashboard" -> {
                    SceneManager.getInstanciaSceneManager()
                            .getStagePrincipal().setTitle("Dashboard");
                    SceneManager.getInstanciaSceneManager()
                            .getStagePrincipal().setResizable(true);
                    scene = loadFileFXML("DashboardView.fxml", 800, 520);
                }
                default -> scene = loadFileFXML("",0,0);
            }

            SceneManager.getInstanciaSceneManager().chanceScene(scene);

        } catch (NullPointerException e) {
            System.out.println("Error load scene");
        }
    }


    public void viewRegister(){
        loadScene("register");
    }

    public void viewLogin(){
        loadScene("login");
    }

    public void viewDashboard(){
        loadScene("dashboard");
    }

}
