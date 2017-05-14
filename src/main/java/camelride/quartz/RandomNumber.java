package camelride.quartz;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: sazal
 * Date: 5/14/17.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RandomNumber {
    @XmlAttribute
    private String type;

    @XmlAttribute
    private int length;

    @XmlAttribute
    private int[] data;

    @XmlAttribute
    private boolean success;
}
