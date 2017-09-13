package com.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-11
 */

@Data
@Accessors(chain=true)
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "car")
public class Car {

    @XmlElement(name = "car_name")
    private String carName;
    @XmlElement(name = "car_speed")
    private Integer carSpeed;

    public Car(){

    }
}
