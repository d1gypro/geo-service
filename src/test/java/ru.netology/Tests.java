package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;


public class Tests {

    @Test
    void test_ru_ip () {
        String expectedIP = "172.255.222.111";
        String actualRu = "Добро пожаловать";


        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.255.222.111"))
                .thenReturn(new Location(null, Country.RUSSIA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, expectedIP);

        String expected = messageSender.send(headers);
        Assertions.assertEquals(expected, actualRu);

        Mockito.verify(geoService, Mockito.only()).byIp("172.255.222.111");
    }
    @Test
    void test_en_ip () {
        String expectedIP = "96.255.222.111";
        String actualEn = "Welcome";

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("96.255.222.111"))
                .thenReturn(new Location(null, Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, expectedIP);

        String expected = messageSender.send(headers);
        Assertions.assertEquals(expected, actualEn);
    }

    @Test
    void test_byIp() {
        Location actual = new Location("Moscow", Country.RUSSIA, "Lenina", 15);

        GeoServiceImpl geoService = new GeoServiceImpl();
        Location expected = geoService.byIp("172.0.32.11");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_locale() {
        String actual = "Добро пожаловать";

        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String expected = localizationService.locale(Country.RUSSIA);

        Assertions.assertEquals(expected, actual);
    }
}
