package com.secmngsys.global.route.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IncrementRequest {

    @XmlElement(required = true)
    private int input;

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }
}