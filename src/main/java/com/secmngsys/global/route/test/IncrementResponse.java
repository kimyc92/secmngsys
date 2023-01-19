package com.secmngsys.global.route.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IncrementResponse {

    @XmlElement(required = true)
    private int result;

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}