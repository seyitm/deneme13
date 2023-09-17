package com.example.deneme13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class borsaEndeks{
    public static HashMap<String, String> borsaveri(){
        long startTime = System.currentTimeMillis();
        long waitTime =5000; // 2 saniye (5000 milisaniye) beklemek için


            HashMap<String, String> sozlukforstocks = new HashMap<>();
            String content = ""; // HTML içeriğini saklamak için bir String

            try {
                //-------------------------BORSASTARTED-----------------------------------------
                // İstek gönderilecek URL'yi belirtin
                String urlStr = "https://uzmanpara.milliyet.com.tr/canli-borsa/bist-TUM-hisseleri/";
                // URL nesnesini oluşturun
                URL url = new URL(urlStr);
                // HttpURLConnection ile bağlantıyı kurun
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // GET isteği gönderin
                connection.setRequestMethod("GET");
                // Yanıtı alın
                int responseCode = connection.getResponseCode();
                // Yanıt kodunu kontrol edin
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Yanıtı okumak için bir BufferedReader kullanın
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    // HTML içeriğini String'e dönüştürün
                    content = stringBuilder.toString();

                    String[] stringArray = content.split("h_td_fiyat_id_");
                    // Dizi (array) elemanlarını bir ArrayList'e dönüştürün
                    List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));
                    // ArrayList'i yazdırın
                    // HTML içeriğini yazdır
                    for (int i = 3; i < 590; i++){
                        String mainstr = stringList.get(i).split("<")[0];
                        String stockname = mainstr.split("\"")[0];
                        String moneyofstock = mainstr.split(">")[1];
                        sozlukforstocks.put(stockname, moneyofstock.replace(",","."));
                    }
                    for (String kelime : sozlukforstocks.keySet()) {
                        String anlam = sozlukforstocks.get(kelime);
                      //  System.out.println(kelime + ": " + anlam);
                    }
                    //.println(stockwithprice.get(0));
                } else {
                    System.out.println("HTTP İstek Hatası: " + responseCode);
                }

                // Bağlantıyı kapatın
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                // InterruptedException yakalandığında işlem yapabilirsiniz
                e.printStackTrace();
            }
            //--------------------------------BORSAFINISHED-------------------------------------------
            // Bekleme sona erdiğinde buraya ulaşılır
            //------------------------------DOLARSTARTED-----------------------------------------

            String contentv2 = ""; // HTML içeriğini saklamak için bir String
            HashMap<String, String> sozlukfordollars = new HashMap<>();


            try {
                // İstek gönderilecek URL'yi belirtin
                String urlStrv2 = "https://canlidoviz.com/";

                // URL nesnesini oluşturun
                URL urlv2 = new URL(urlStrv2);

                // HttpURLConnection ile bağlantıyı kurun
                HttpURLConnection connectionv2 = (HttpURLConnection) urlv2.openConnection();

                // GET isteği gönderin
                connectionv2.setRequestMethod("GET");

                // Yanıtı alın
                int responseCodev2 = connectionv2.getResponseCode();

                // Yanıt kodunu kontrol edin
                if (responseCodev2 == HttpURLConnection.HTTP_OK) {
                    // Yanıtı okumak için bir BufferedReader kullanın
                    BufferedReader readerv2 = new BufferedReader(new InputStreamReader(connectionv2.getInputStream()));
                    String linev2;
                    StringBuilder stringBuilderv2 = new StringBuilder();

                    while ((linev2 = readerv2.readLine()) != null) {
                        stringBuilderv2.append(linev2);
                    }
                    readerv2.close();

                    // HTML içeriğini String'e dönüştürün
                    contentv2 = stringBuilderv2.toString();
                    Integer carry = 0;

                    // HTML içeriğini yazdırın
                    //System.out.println(content);
                    String[] stringArrayv2 = contentv2.split("<td class=\"canli text-mobile-value\" ");
                    for (String eleman : stringArrayv2) {
                        carry++;
                        if (carry > 34) break;


                        String[] elemansplitted = eleman.split("td class=\"text-title\"><a href=");
                        //System.out.println(elemansplitted[1]+"           elemanssplitted[1]");

                        String[] elemansplittedv1 = elemansplitted[1].split("</a></td>");
                        //System.out.println(elemansplittedv1[1]+"      elemansplittedv1[1]");
                        String[] forvaluesplitted = elemansplittedv1[1].split("\">");
                        //System.out.println(forvaluesplitted[1]+"           56.satır");
                        String myvalue = forvaluesplitted[1].split("<")[0];
                        //System.out.println(myvalue+"     myvalue");


                        String[] elemansplittedv2 = elemansplittedv1[0].split(">");

                        String mykey = elemansplittedv2[1];
                        if (mykey == "Tron") break;
                        //System.out.println(mykey);
                        sozlukfordollars.put(mykey, myvalue.replace(",","."));


                    }
                    for (String kelime : sozlukfordollars.keySet()) {
                        String anlam = sozlukfordollars.get(kelime);
                       // System.out.println(kelime + ": " + anlam);
                    }
                } else {
                    System.out.println("HTTP İstek Hatası: " + responseCodev2);
                }

                // Bağlantıyı kapatın
                connectionv2.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sozlukfordollars.putAll(sozlukforstocks);
            //------------------------------DOLARENDED-------------------------------------------
            long endTime = System.currentTimeMillis();
            if (endTime - startTime >= waitTime) {
                System.out.println("------------------------X seconds passed------------------------------------------");
                // 2 saniye boyunca döngüyü bekledikten sonra döngüyü sonlandır
            }
            return sozlukfordollars;


    }

}
