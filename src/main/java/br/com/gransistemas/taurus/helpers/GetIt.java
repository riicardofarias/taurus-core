package br.com.gransistemas.taurus.helpers;

import eu.lestard.easydi.EasyDI;

public class GetIt {
    private static EasyDI easyDI = new EasyDI();

    public static <T> void register(Class<T> classType, T instance) {
        easyDI.bindInstance(classType, instance);
    }

    public static <T> T get(Class<T> requestedType){
        return easyDI.getInstance(requestedType);
    }
}
