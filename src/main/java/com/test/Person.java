package com.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-08
 */
@Data
@Accessors(chain=true)
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="user")
public class Person {

    @XmlElement(name = "firstName")
    @JsonProperty("first_name")
    private String firstNameElem;
    @XmlElement(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;
    @XmlElement(name = "id")
    private int id;
    @JsonProperty("email_field")
    @XmlElement(name = "email")
    private String email;

    public Person(){

    }
}
