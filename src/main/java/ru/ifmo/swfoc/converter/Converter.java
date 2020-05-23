package ru.ifmo.swfoc.converter;

public abstract class Converter {
    // converts xml name to uppercase and trims it
    protected String cnv(String str) {
        return str.trim().toUpperCase();
    }
}
