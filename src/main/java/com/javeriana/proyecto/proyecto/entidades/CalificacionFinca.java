package com.javeriana.proyecto.proyecto.entidades;

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
@SQLDelete(sql = "UPDATE calificacionfinca SET status = 1 WHERE id=?")
public class CalificacionFinca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int puntuacion;
    private String comentario;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_finca", referencedColumnName = "id", nullable = false)
    private Finca finca;

}
