<<<<<<< HEAD
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
>>>>>>> develop
package org.quickmartkinal.system.utils;

<<<<<<< HEAD

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

=======
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.quickmartkinal.system.ClasePrincipal;
import org.quickmartkinal.system.controller.DashboardController;
import org.quickmartkinal.system.model.Usuario;

/**
 *
 * @author informatica
 */
public class ViewFactory {

    private static final String PATH_VIEWS = "/org/quickmartkinal/system/view/";
    private final AlertInformation alertInfo = new AlertInformation();

    public Scene loadFileFXML(String nameFile, int width, int height) {
        String pathOffFile = PATH_VIEWS + nameFile;
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            URL urlFile = ClasePrincipal.class.getResource(pathOffFile);
            loaderFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loaderFXML.setLocation(urlFile);

            return new Scene(loaderFXML.load(), width, height);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void loadScene(String nameFile) {
        Scene scene;
        try {
            switch (nameFile) {
                case "login" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("QUICKMART - INICIO DE SESION");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("loginViewMarket.fxml", 400, 500);
                }
                default -> scene = loadFileFXML("loginViewMarket.fxml", 400, 500);
            }

            SceneManager.getInstanciaSceneManager().changeScene(scene);

        } catch (NullPointerException nullPointerException) {
            System.out.println("Error al cargar la escena.");
        }
    }

    public void viewLogin() {
        loadScene("login");
    }

    public void viewDashboard(Usuario usuario) {
        try {
            String pathOffFile = PATH_VIEWS + "DashboardView.fxml";

            FXMLLoader loaderFXML = new FXMLLoader();
            URL urlFile = ClasePrincipal.class.getResource(pathOffFile);
            loaderFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loaderFXML.setLocation(urlFile);

            Parent root = loaderFXML.load();

            DashboardController controller = loaderFXML.getController();
            controller.setUsuario(usuario);

            Scene scene = new Scene(root, 700, 480);

            SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("QUICKMART - CATALOGO");
            SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(true);
            SceneManager.getInstanciaSceneManager().changeScene(scene);

        } catch (IOException e) {
            System.out.println("Error al cargar el Dashboard: " + e.getMessage());
            alertInfo.viewAlert("ERROR", "ERROR AL CARGAR", "ERROR AL ABRIR EL MODULO", "Ocurrio un error, intenta nuevamente.");
        }
    }
<<<<<<< HEAD
>>>>>>> 05be444258df5c84736a3d2340d725e86c358462
=======

>>>>>>> develop
}
