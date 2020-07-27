package com.company.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authority")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class Authority {

    @Id
    @Column(length = 50)
    private String name;
    // @ManyToMany(mappedBy = "authorities") // bi-directional relationship causes
    // an error on start up
}
