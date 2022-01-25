package com.perseus.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="emails")
public class Email {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;

    @Column(unique=true)
    private String mail;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}