package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void print(String str){
        System.out.println(str);
    }

    public static List<Integer> findNonZero(double arr1[]) {
        List<Integer> indices = new ArrayList<>();

        for(int i = 0; i < arr1.length; i++) {
            if(arr1[i] > 0.0f) {
                indices.add(i);
            }
        }
        return indices;
    }


    public static List<Float> getFloatList(Object value) {
        float[] array = (float[]) value;
        List<Float> result = new ArrayList<Float>(array.length);
        for (float f : array) {
            result.add(Float.valueOf(f));
        }
        return result;
    }

    public static double getMean(double data[]){
        double sum = 0.0;
        for(double vaule : data){
            sum+=vaule;
        }
        return sum/data.length;
    }
    public static double getMean(List<Double> data){
        double sum = 0.0;
        for(double vaule : data){
            sum+=vaule;
        }
        return sum/data.size();
    }



    public static double[] getSlice(double data[], int start, int end){
        assert end > start;
        double slice[] = new double[end-start];
        for(int i = 0; i < slice.length; i++){
            slice[i] = data[start+i];
        }
        return slice;
    }


    public static List<Double> getSlice(List<Double>data, int start, int end){
        assert end > start;
        List<Double> slice = new ArrayList<>();
        for(int i = start; i < end; i++){
            slice.add(data.get(i));
        }
        return slice;
    }

    public static float[] getSlice(float data[], int start, int end){
        assert end > start;
        float slice[] = new float[end-start];
        for(int i = 0; i < data.length; i++){
            slice[i] = data[start+i];
        }
        return slice;
    }

    public static double[] constMul(double data[], double constant){
        double result[] = new double[data.length];
        for(int i = 0; i < data.length; i++){
            result[i] = data[i]*constant;
        }
        return result;
    }

    public static double[] constDiv(double data[], double constant){
        double result[] = new double[data.length];
        for(int i = 0; i < data.length; i++){
            result[i] = data[i]/constant;
        }
        return result;
    }

    public static double[] dot(double data1[], double data2[]){
        assert data1.length == data2.length;
        double result[] = new double[data1.length];
        for(int i = 0; i < data1.length; i++){
            result[i] = data1[i]*data2[i];
        }
        return result;
    }

    public static double[] vecSub(double data1[], double data2[]){
        assert data1.length == data2.length;
        double result[] = new double[data1.length];
        for(int i = 0; i < data1.length; i++){
            result[i] = data1[i] - data2[i];
        }
        return result;
    }

    public static double[] vecAdd(double data1[], double data2[]){
        assert data1.length == data2.length;
        double result[] = new double[data1.length];
        for(int i = 0; i < data1.length; i++){
            result[i] = data1[i] + data2[i];
        }
        return result;
    }

    public static double[] vecDiv(double data1[], double data2[]){
        assert data1.length == data2.length;
        double result[] = new double[data1.length];
        for(int i = 0; i < data1.length; i++){
            result[i] = data1[i] / data2[i];
        }
        return result;
    }

    public static List<Double>[] readCSVData(String file){
        FileReader filereader = null;
        List<String[]> allData = new ArrayList<>();
        try {
            filereader = new FileReader(makeResourcePath(file));
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(4).build();

            allData = csvReader.readAll();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int NUM_COLS = 1;
        List<Double> dataFrame[] = new List[NUM_COLS];
        for(int i = 0; i < dataFrame.length; i++){
            dataFrame[i] = new ArrayList<>();
        }

        for (String[] row : allData) {
            // 2798.5347985347985	3820.0	525935.6689453125	1.4153234E7	943922.4243164062
            String[] values = row[0].split("\t");
            if(values.length == 5){
                for (int i = 0; i < NUM_COLS; i++) { // take only the first column
                    dataFrame[i].add(Double.parseDouble(values[i]));
                }
            }
        }

        return dataFrame;
    }

    public static double[] linspace(int range){
        double a[] = new double[range];
        for (int i = 0; i < range; i++){
            a[i] = i+1;
        }
        return a;
    }

    public static List<Double> broadcastSub(List<Double> data, double value){
        List<Double> result = new ArrayList<>();
        for(Double dat: data){
            result.add(Math.abs(dat - value));
        }
        return result;
    }

    public static int argMin(List<Double> data){
        int minIdx = 0;
        Double minVal = 10000000.0;
        for(int i = 0; i < data.size(); i++){
            Double value = data.get(i);
            if( value > minVal ){
                minVal = value;
                minIdx = i;
            }
        }
        return minIdx;
    }


    public static int argMax(List<Double> data){
        int maxIdx = 0;
        Double maxVal = -10000000.0;
        for(int i = 0; i < data.size(); i++){
            Double value = data.get(i);
            if( value > maxVal ){
                maxVal = value;
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    private static String makeResourcePath(String template) {
        return App.class.getResource(template).getPath();
    }

}
