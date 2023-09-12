package com.example.demo2.collection;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "companies")
public class CompanyCollection {
    @Id
    private String id;

    private String name;

    private Date createdAt;

    private Date updatedAt;
}
