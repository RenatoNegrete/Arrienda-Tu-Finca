package com.javeriana.proyecto.proyecto.entidades;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@SQLDelete(sql = "UPDATE usuario SET status = 1 WHERE id=?")

public class Arrendador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String apellido;
    private String contrasena;
    private String telefono;

    @Column(unique = true, nullable = false)
    private String email;
    
    private int status;

    @OneToMany(mappedBy = "arrendador", fetch = FetchType.LAZY)
    private List<Solicitud> solicitudes = new ArrayList<>();

}
