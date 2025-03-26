package com.javeriana.proyecto.proyecto.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime fechasolicitud;
    private LocalDate fechallegada;
    private LocalDate fechasalida;
    private int cantpersonas;
    private double valor;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arrendador", referencedColumnName = "id", nullable = false)
    private Arrendador arrendador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_finca", referencedColumnName = "id", nullable = false)
    private Finca finca;



}
