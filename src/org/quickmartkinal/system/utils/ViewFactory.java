/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.utils;

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

    private final String PATH_VIEWS = "/org/quickmartkinal/system/view/";
    private final AlertInformation alertInfo = new AlertInformation();

    public Scene LoadFileFXML(String nameFile, int width, int height) {
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
        Scene scene = null;
        try {
            switch (nameFile) {
                case "login" -> {
                    SceneManager.getInstanciaScenerManager().getStagePrincipal().setTitle("QUICKMART - INICIO DE SESION");
                    SceneManager.getInstanciaScenerManager().getStagePrincipal().setResizable(false);
                    scene = LoadFileFXML("LoginView.fxml", 400, 500);
                }
                default -> scene = LoadFileFXML("LoginView.fxml", 400, 500);
            }

            SceneManager.getInstanciaScenerManager().changeScene(scene);

        } catch (NullPointerException objetoNulo) {
            System.out.println("Error load Scene");
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

            SceneManager.getInstanciaScenerManager().getStagePrincipal().setTitle("QUICKMART - CATALOGO");
            SceneManager.getInstanciaScenerManager().getStagePrincipal().setResizable(true);
            SceneManager.getInstanciaScenerManager().changeScene(scene);

        } catch (IOException e) {
            System.out.println("Error al cargar el Dashboard");
            System.out.println(e.getMessage());
            e.printStackTrace();
            alertInfo.viewAlert("ERROR", "ERROR AL CARGAR", "ERROR AL ABRIR EL MODULO", "Ocurrio un error, intenta nuevamente.");
        }
    }
}
