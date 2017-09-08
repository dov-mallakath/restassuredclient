package com.test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-08
 */

@Data
//@EqualsAndHashCode(exclude = {"userId","id"})
@Accessors(chain = true)
public class Article {

    private int id;
    private int userId;
    private String title;
    private String body;

}
