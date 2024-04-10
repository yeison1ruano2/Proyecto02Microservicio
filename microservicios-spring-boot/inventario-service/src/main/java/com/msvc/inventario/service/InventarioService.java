package com.msvc.inventario.service;


import com.msvc.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class InventarioService {
  @Autowired
  private InventarioRepository inventarioRepository;

  @Transactional(readOnly=true)
  public boolean isInStock(String codigoSku){
    return inventarioRepository.findByCodigoSku(codigoSku).isPresent();
  }
}
