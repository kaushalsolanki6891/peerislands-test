package com.peerislands.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TBL_EMPLOYEES")
@Data
@EqualsAndHashCode(exclude = "nameAttributeInThisClassWithOneToMany")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "employee", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonManagedReference
    private Set<Hobby> hobbies;


    @Override
    public String toString() {
        return "EmployeeEntity [id=" + id + ", firstName=" + firstName +
                ", lastName=" + lastName + ", email=" + email + "]";
    }
}