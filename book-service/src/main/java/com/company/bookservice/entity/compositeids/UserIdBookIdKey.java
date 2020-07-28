package com.company.bookservice.entity.compositeids;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdBookIdKey implements Serializable {
    private static final long serialVersionUID = -6480149796401177909L;
    private Long userId;
    private String bookId;
}