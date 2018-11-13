package com.rsi.memegenerator.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Meme {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false, nullable = false)
    private int uuid;

    @Column(name="meme_url")
    private String s3url;

    @Column
    private Date uploadDate;

    @Column
    private String filename;
    private File file;
}
