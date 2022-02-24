package org.efire.net.dto;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String isbn;
    private Long authorId;
}
