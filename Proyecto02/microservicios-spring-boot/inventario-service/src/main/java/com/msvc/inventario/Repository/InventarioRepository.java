package com.msvc.inventario.Repository;

import com.msvc.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario,Long> {

  List<Inventario> findByCodigoSkuIn(List<String> codigoSku);
}
