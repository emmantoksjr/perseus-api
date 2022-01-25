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
@Table(name="phone_numbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;

    @Column(unique=true)
    private String number;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private User user;
}
