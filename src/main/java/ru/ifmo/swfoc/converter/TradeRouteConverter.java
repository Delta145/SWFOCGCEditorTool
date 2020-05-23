package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.editor.model.MTradeRoute;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;

import java.util.Map;

public class TradeRouteConverter extends Converter {
    private final Map<String, MPlanet> planets;

    public TradeRouteConverter(DATLoader datLoader, Map<String, MPlanet> planets) {
        this.planets = planets;
    }

    public MTradeRoute toMTradeRoute(TradeRoute route) {
        String name = route.getName().toUpperCase();
        MPlanet planetA = planets.get(cnv(route.getPointA()));
        MPlanet planetB = planets.get(cnv(route.getPointB()));
        return new MTradeRoute(name, planetA, planetB);
    }
}
