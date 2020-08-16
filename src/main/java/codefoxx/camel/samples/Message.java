package codefoxx.camel.samples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String text;
}