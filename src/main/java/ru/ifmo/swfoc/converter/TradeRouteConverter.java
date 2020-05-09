package ru.ifmo.swfoc.converter;

import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.ifmo.swfoc.editor.model.MTradeRoute;
import ru.ifmo.swfoc.io.DATLoader;
import ru.ifmo.swfoc.xmltoobject.traderoute.TradeRoute;

import java.util.Map;

public class TradeRouteConverter {
    private DATLoader datLoader;
    private Map<String, MPlanet> planets;

    public TradeRouteConverter(DATLoader datLoader, Map<String, MPlanet> planets) {
        this.datLoader = datLoader;
        this.planets = planets;
    }

    public MTradeRoute toMTradeRoute(TradeRoute route) {
        String name = route.getName();
        MPlanet planetA = planets.get(route.getPointA().trim());
        MPlanet planetB = planets.get(route.getPointB().trim());
        return new MTradeRoute(name, planetA, planetB);
    }
}
