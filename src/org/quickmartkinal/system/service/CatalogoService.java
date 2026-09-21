/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.service;

import java.util.List;
import org.quickmartkinal.system.model.Categoria;
import org.quickmartkinal.system.model.Producto;
import org.quickmartkinal.system.repository.CategoriaRepository;
import org.quickmartkinal.system.repository.ProductoRepository;

/**
 *
 * @author informatica
 */
public class CatalogoService {

    private final ProductoRepository productoRepository = new ProductoRepository();
    private final CategoriaRepository categoriaRepository = new CategoriaRepository();

    public List<Producto> listarProductos() {
        return productoRepository.listar();
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.listar();
    }

    public CatalogoStatus crearProducto(Producto producto) {
        boolean precioValido = producto.getPrecioVenta() > producto.getPrecioCosto();
        if (precioValido == false) {
            return CatalogoStatus.PRECIO_INVALIDO;
        }

        boolean codigoDuplicado = existeCodigo(producto.getCodigoBarras(), null);
        if (codigoDuplicado) {
            return CatalogoStatus.CODIGO_DUPLICADO;
        }

        boolean creado = productoRepository.crear(producto);
        if (creado == false) {
            return CatalogoStatus.OPERACION_FALLIDA;
        }
        return CatalogoStatus.PRODUCTO_GUARDADO;
    }

    public CatalogoStatus editarProducto(Producto producto) {
        boolean precioValido = producto.getPrecioVenta() > producto.getPrecioCosto();
        if (precioValido == false) {
            return CatalogoStatus.PRECIO_INVALIDO;
        }

        boolean codigoDuplicado = existeCodigo(producto.getCodigoBarras(), producto.getIdProducto());
        if (codigoDuplicado) {
            return CatalogoStatus.CODIGO_DUPLICADO;
        }

        boolean editado = productoRepository.editar(producto);
        if (editado == false) {
            return CatalogoStatus.OPERACION_FALLIDA;
        }
        return CatalogoStatus.PRODUCTO_ACTUALIZADO;
    }

    public CatalogoStatus eliminarProducto(String idProducto) {
        boolean eliminado = productoRepository.eliminar(idProducto);
        if (eliminado == false) {
            return CatalogoStatus.OPERACION_FALLIDA;
        }
        return CatalogoStatus.PRODUCTO_ELIMINADO;
    }

    /**
     * Revisa en memoria si ya existe un producto con ese codigo de barras
     * (excluyendo el propio producto cuando se esta editando).
     */
    private boolean existeCodigo(String codigo, String idProductoActual) {
        List<Producto> productos = productoRepository.listar();
        for (Producto producto : productos) {
            boolean mismoCodigo = producto.getCodigoBarras().equals(codigo);
            boolean esOtroProducto = producto.getIdProducto().equals(idProductoActual) == false;
            if (mismoCodigo && esOtroProducto) {
                return true;
            }
        }
        return false;
    }

}
