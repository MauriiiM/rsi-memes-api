package com.rsi.memegenerator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "tag")
public class MemeTag {
    @Id
    private int id;

    @Column(name="tag_string")
    private String tagString;

    @Column(name="tag_image_id_ref")
    private long imageRefId;
}
