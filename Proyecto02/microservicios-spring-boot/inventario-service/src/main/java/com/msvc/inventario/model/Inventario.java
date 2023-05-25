package com.msvc.inventario.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="inventario")
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codigoSku;

  private Integer cantidad;
}
