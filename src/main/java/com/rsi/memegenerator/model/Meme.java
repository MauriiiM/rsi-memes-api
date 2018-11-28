package com.rsi.memegenerator.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name= "image")
public class Meme {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="image_id", updatable = false, nullable = false)
    private long id;

    @Column(name="image_s3_url")
    private String s3url;

    @Column(name="image_upload_date")
    private Timestamp uploadDate;

    @Column(name="image_file_name")
    private String filename;

    private String[] tags;
}
