package com.aventurier.app.backend.business.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;

@Service
public class GenerateRandomMapService {
	
	public Map generateNewRandomMap() {
		Map map = new Map();
		
		int numRows = 20;
        int numCols = 20;
        int numHash = 187;  // '#'
        int numSpace = 213; // ' '
        
        List<Character> mapChars = new ArrayList<>(numHash + numSpace);

        for (int i = 0; i < numHash; i++) {
            mapChars.add('#');
        }
        for (int i = 0; i < numSpace; i++) {
            mapChars.add(' ');
        }

        Collections.shuffle(mapChars);


        int index = 0;
        for (int i = 0; i < numRows; i++) {
            StringBuilder newRow = new StringBuilder();
            for (int j = 0; j < numCols; j++) {
            	Character chr = mapChars.remove(mapChars.size() - 1);
            	System.out.println("mapChars.remove(mapChars.size() - 1) > ["+chr+"]");
            	Point point = new Point(j, i);
            	point.setIndex(index);
                if( " ".equals(chr.toString()) ) {
                	point.setCrossable(true);
                }else {
                	point.setCrossable(false);
                }
                map.getAllMapPoints().add(point);
                newRow.append(chr);
                index++;
            }
            System.out.println("newRow.toString() > ["+newRow.toString()+"]");
            map.getLines().add(newRow.toString());
        }

		
		return map;

	}
	
}
