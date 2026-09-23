/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.quickmartkinal.system.ClasePrincipal;
import org.quickmartkinal.system.controller.ComprobanteController;
import org.quickmartkinal.system.controller.InventarioVentasController;
import org.quickmartkinal.system.model.ComprobanteItem;
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
                case "register" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("QUICKMART - CREAR CUENTA");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("loginRegisterMarket.fxml", 350, 500);
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

    public void viewRegister() {
        loadScene("register");
    }

    public void viewCatalogo(Usuario usuario) {
        try {
            String pathOffFile = PATH_VIEWS + "CatalogoView.fxml";

            FXMLLoader loaderFXML = new FXMLLoader();
            URL urlFile = ClasePrincipal.class.getResource(pathOffFile);
            loaderFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loaderFXML.setLocation(urlFile);

            Parent root = loaderFXML.load();

            Scene scene = new Scene(root, 900, 550);

            SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("QUICKMART - CATALOGO DE PRODUCTOS");
            SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(true);
            SceneManager.getInstanciaSceneManager().changeScene(scene);

        } catch (IOException e) {
            System.out.println("Error al cargar el Catalogo: " + e.getMessage());
            alertInfo.viewAlert("ERROR", "ERROR AL CARGAR", "ERROR AL ABRIR EL MODULO", "Ocurrio un error, intenta nuevamente.");
        }
    }

  
    public void viewComprobante(List<ComprobanteItem> renglones) {
        try {
            String pathOffFile = PATH_VIEWS + "ComprobanteView.fxml";

            FXMLLoader loaderFXML = new FXMLLoader();
            URL urlFile = ClasePrincipal.class.getResource(pathOffFile);
            loaderFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loaderFXML.setLocation(urlFile);

            Parent root = loaderFXML.load();

            ComprobanteController controller = loaderFXML.getController();
            controller.cargarComprobante(renglones);

            Stage stageComprobante = new Stage();
            stageComprobante.setTitle("QUICKMART - COMPROBANTE DE VENTA");
            stageComprobante.initModality(Modality.APPLICATION_MODAL);
            stageComprobante.setScene(new Scene(root, 650, 500));
            stageComprobante.showAndWait();

        } catch (IOException e) {
            System.out.println("Error al cargar el comprobante: " + e.getMessage());
            alertInfo.viewAlert("ERROR", "ERROR AL CARGAR", "ERROR AL ABRIR EL MODULO", "Ocurrio un error, intenta nuevamente.");
        }
    }

    public void viewInventarioVentas(List<ComprobanteItem> renglones) {
        try {
            String pathOffFile = PATH_VIEWS + "InventarioVentasView.fxml";

            FXMLLoader loaderFXML = new FXMLLoader();
            URL urlFile = ClasePrincipal.class.getResource(pathOffFile);
            loaderFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loaderFXML.setLocation(urlFile);

            Parent root = loaderFXML.load();

            InventarioVentasController controller = loaderFXML.getController();
            controller.cargarInventarioVentas(renglones);

            Stage stageInventarioVentas = new Stage();
            stageInventarioVentas.setTitle("QUICKMART - INVENTARIO DE VENTAS");
            stageInventarioVentas.initModality(Modality.APPLICATION_MODAL);
            stageInventarioVentas.setScene(new Scene(root, 800, 500));
            stageInventarioVentas.showAndWait();

        } catch (IOException e) {
            System.out.println("Error al cargar el inventario de ventas: " + e.getMessage());
            alertInfo.viewAlert("ERROR", "ERROR AL CARGAR", "ERROR AL ABRIR EL MODULO", "Ocurrio un error, intenta nuevamente.");
        }
    }

}
