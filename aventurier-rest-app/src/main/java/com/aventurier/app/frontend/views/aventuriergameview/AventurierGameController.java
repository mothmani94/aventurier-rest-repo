package com.aventurier.app.frontend.views.aventuriergameview;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;
import com.aventurier.app.backend.business.service.AventurierGameFacadeService;

/***
 * 
 * @author motmani
 *
 */
@Controller
public class AventurierGameController {
	
	
	@Autowired
	AventurierGameFacadeService facade;

	AventurierGameViewImpl view;

	
	public Map readFile() {
		return facade.readFileAsString();
	}
	
	public Optional<Point> resolvePosition(Map map, Point selectedPosition, String pathToFollow) {
		return  facade.resolvePosition(map, selectedPosition,pathToFollow );
	}


	public AventurierGameViewImpl getView() {
		return view;
	}

	public void setView(AventurierGameViewImpl view) {
		this.view = view;
	}

	

	
	

}
