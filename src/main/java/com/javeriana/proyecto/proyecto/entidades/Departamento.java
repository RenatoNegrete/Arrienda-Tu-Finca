package com.javeriana.proyecto.proyecto.entidades;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE administrador SET status = 1 WHERE id=?")

public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private long id;
    private String nombre;
}
