package com.compass.mshistory.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Historico {

    @Id
    private String id;
    private Long userId;

    @DBRef
    private List<Compra> purchases = new ArrayList<>();

    public Historico(Long userId){
        this.userId = userId;
    }
}
