package com.rsi.memegenerator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MemeTag {
    @Id
    private int id;

    @Column(name="tag_name")
    private String tagName;

//    @Column(name="meme_uuid_ref")
//    private Meme memeUuidRef;
}
