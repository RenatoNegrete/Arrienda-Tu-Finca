package com.javeriana.proyecto.proyecto.entidades;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "administrador")
public class Administrador extends User {

    @OneToMany(mappedBy = "administrador", fetch = FetchType.LAZY)
    private List<Finca> fincas = new ArrayList<>();

    @OneToMany(mappedBy = "administrador", fetch = FetchType.LAZY)
    private List<CalificacionAdministrador> calificaciones = new ArrayList<>();

}
