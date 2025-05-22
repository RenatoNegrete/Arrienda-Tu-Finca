package com.javeriana.proyecto.proyecto.entidades;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario")
@Table(name = "users")
public abstract class User {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;

    private String contrasena;
    private String telefono;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;

    private int status;

}
