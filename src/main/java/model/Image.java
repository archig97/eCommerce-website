package model;

import java.sql.Blob;

public class Image {

    private Long id;
    private String fileName;
    private String fileType;
    private Product product;
    private Blob image;
    private String downloadUrl;
}
