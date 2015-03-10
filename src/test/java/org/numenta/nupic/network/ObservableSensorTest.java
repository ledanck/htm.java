package org.numenta.nupic.network;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.numenta.nupic.datagen.ResourceLocator;
import org.numenta.nupic.network.SensorParams.Keys;

import rx.Observable;



public class ObservableSensorTest {
    
    @SuppressWarnings("unchecked")
    private Observable<String> makeObservable() {
        File f = new File(ResourceLocator.path("rec-center-hourly.csv"));
        try {
            Observable<?> o = Observable.from(Files.lines(f.toPath(), Charset.forName("UTF-8")).toArray());
            return (Observable<String>)o;
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }


    @Test
    public void test() {
        Object[] n = { "some name", makeObservable() };
        SensorParams parms = SensorParams.create(Keys::path, n);
        Sensor<ObservableSensor<String[]>> sensor = Sensor.create(ObservableSensor::create, parms);
        long count = sensor.getStream().count();
        assertEquals(4391, count);
    }
    
    

}
