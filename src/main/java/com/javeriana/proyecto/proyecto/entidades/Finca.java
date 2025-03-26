package com.javeriana.proyecto.proyecto.entidades;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@SQLDelete(sql = "UPDATE finca SET status = 1 WHERE id=?")
public class Finca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String tipoIngreso;
    private String descripcion;
    private int habitaciones;
    private int banos;
    private boolean mascotas;
    private boolean piscina;
    private boolean asador;
    private double valorNoche;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_administrador", referencedColumnName = "id", nullable = false)
    private Administrador administrador;

    @OneToMany(mappedBy = "finca", fetch = FetchType.LAZY)
    private List<Solicitud> solicitudes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id", nullable = false)
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio", referencedColumnName = "id", nullable = false)
    private Municipio municipio;

    @OneToOne
    @JoinColumn(name = "id_foto", referencedColumnName = "id")
    private Foto foto;

}
