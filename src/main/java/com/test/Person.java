package com.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-08
 */
@Data
@Accessors(chain=true)
@AllArgsConstructor
public class Person {

    private String firstName;
    private String lastName;
    private int id;
    private String email;

}
