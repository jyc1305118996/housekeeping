package com.haige.integration.convert;

import com.haige.integration.param.SubmitOrderParam;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * @author Archie
 * @date 2019/11/10 17:55
 */
public class WXPayConvertUtils {
    public static String convert(SubmitOrderParam submitOrderParam){
        StringWriter sb = new StringWriter ();
        try {
            JAXBContext context = JAXBContext.newInstance(submitOrderParam.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(submitOrderParam, sb);
        } catch (JAXBException e) {
            throw new RuntimeException("Bean映射XML失败");
        }
        String xml = sb.toString();
        return xml;
    }
}
