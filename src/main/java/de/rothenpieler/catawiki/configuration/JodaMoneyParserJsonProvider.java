//package de.rothenpieler.catawiki.configuration;
//
//import org.joda.money.Money;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.ext.MessageBodyReader;
//import javax.ws.rs.ext.MessageBodyWriter;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//@Provider
//public class JodaMoneyParserJsonProvider  implements MessageBodyWriter<Object> {
//
//    private final MoneyConverter converter = new MoneyConverter();
//
//    @Override
//    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
//        if (!rawType.equals(Money.class)) return null;
//        return (ParamConverter<T>) converter;
//    }
//
//    @Override
//    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
//        return true;
//    }
//
//    @Override
//    public void writeTo(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
//
//    }
//
//    public class MoneyConverter implements ParamConverter<Money> {
//
//        public Money fromString(String value) {
//            if (value == null ||value.isEmpty()) return null; // change this for production
//            return Money.of(CurrencyUnit.EUR, Double.parseDouble(value));
//        }
//
//        public String toString(Money value) {
//            if (value == null) return "";
//            return value.getAmount().toString(); // change this for production
//        }
//
//    }
//}