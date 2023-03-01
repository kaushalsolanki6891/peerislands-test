package com.peerislands.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "TBL_HOBBIES")
@Data
@EqualsAndHashCode(exclude="employee")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "HOBBY")
    private String hobby;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

}