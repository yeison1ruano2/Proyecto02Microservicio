package com.msvc.producto.productoservice.repository;

import com.msvc.producto.productoservice.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto,String> {
}
