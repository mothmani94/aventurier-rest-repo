package com.aventurier.app.backend.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;

/***
 * 
 * @author motmani
 *
 */
@Service
public class ReadFileService {

    public Map readFileAsString() {
		 
		Map map = new Map();
		 
		String filePath = "carte.txt";
        ClassLoader classLoader = ReadFileService.class.getClassLoader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filePath)))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
            	map.getLines().add(line);
	            String[] ss = line.split("");
	            for (int i =0; i < ss.length; i++) {
	            	int index = ((lineNumber) * 20) + i;
	            	Point point = new Point(i, lineNumber);
	            	point.setIndex(index);
                    if( " ".equals(ss[i]) ) {
                    	point.setCrossable(true);
                    }else {
                    	point.setCrossable(false);
                    }
                    map.getAllMapPoints().add(point);
	            }
                lineNumber++;
            }
            return map;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	 }

}
