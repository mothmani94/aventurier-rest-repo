package com.aventurier.app.frontend.views.ccmp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.aventurier.app.backend.business.model.Point;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/***
 * 
 * @author motmani
 *
 */
public class CustomTextArea extends VerticalLayout {
    
    private static final long serialVersionUID = -2896387779935200051L;
    private static String ADVENTURER_CHAR = "A";
    private static String CROSSABLE_POINT_CHAR = "-";
    private static String UNCROSSABLE_POINT_STYLE_COLOR = "#05c8ff";
    private static String CROSSABLE_POINT_STYLE_COLOR = "black";
    private static String DEFAULT_ALL_POINT_FONT_SIZE = "20px"; 
    private static String DEFAULT_ALL_POINT_FONT_WEIGHT = "bold";
    private static String CROSSABLE_POINT_FONT_WEIGHT = "normal";

	private Div parentDiv;
	private Span previousSpan;
    
    public CustomTextArea() {
		
    	addClassName("border-vertical-layout"); //border
    	setWidth("280px"); //230px
        
    	parentDiv = new Div();
    	parentDiv.setId("map-parent-div");
    	add(resetDivStyle());
    }
		
	public void addTextFromLines(List<String> lines) {
		parentDiv.removeAll();
		int index = 0;
		for (String str : lines) {
			Div line = new Div();
            for (char c : str.toCharArray()) {
                String charAsString = Character.toString(c);
                Span characterSpan = new Span();
                if(" ".equals(charAsString)) { // crossable point style
                	characterSpan.getElement().getStyle().set("color", CROSSABLE_POINT_STYLE_COLOR);
                	charAsString = "-";
                }else { // uncrossable point style
                	characterSpan.getElement().getStyle().set("color", UNCROSSABLE_POINT_STYLE_COLOR);
                }
            	characterSpan.getElement().getStyle().set("font-size", DEFAULT_ALL_POINT_FONT_SIZE); 
            	characterSpan.getElement().getStyle().set("font-weight", DEFAULT_ALL_POINT_FONT_WEIGHT); 
                characterSpan.setText(charAsString);
                characterSpan.setId(""+index);                
                characterSpan.getElement().getStyle().set("white-space", "pre");
                line.add(characterSpan);
                index++;
            }
            parentDiv.add(line);
        }
	}
	
	public void setColorAtIndex(Point currentPoint ) {
		
		// reset style for previous element/span
		if(previousSpan != null) {
			previousSpan.setText(CROSSABLE_POINT_CHAR);
			previousSpan.getElement().getStyle().set("color", CROSSABLE_POINT_STYLE_COLOR);
			previousSpan.getElement().getStyle().set("font-size", DEFAULT_ALL_POINT_FONT_SIZE); 
			previousSpan.getElement().getStyle().set("font-weight", DEFAULT_ALL_POINT_FONT_WEIGHT); 
		}
		
		List<Component> allChildren = new ArrayList<>();
		
		collectChildren(parentDiv, allChildren);
		
		allChildren.forEach(child -> {
		   
		    if(child instanceof Span) {
		    	Span span = (Span) child;
		    	if(span.getId().isPresent()) {
		    		String spanId = span.getId().get();
		    		
		    		// reset previous point style
		    		/*if(previousPoint != null && StringUtils.equals(spanId, ""+previousPoint.getIndex())) {
		    			span.setText(CROSSABLE_POINT_CHAR);
		    			span.getElement().getStyle().set("color", CROSSABLE_POINT_STYLE_COLOR);
	                	span.getElement().getStyle().set("font-size", DEFAULT_ALL_POINT_FONT_SIZE); 
		            	span.getElement().getStyle().set("font-weight", DEFAULT_ALL_POINT_FONT_WEIGHT); 
		    		}*/
		    		
		    		
		    		// change current point style
		    		if(StringUtils.equals(spanId, ""+currentPoint.getIndex())) {
		    					    			
		    			span.setText(ADVENTURER_CHAR);
		    			span.getElement().getStyle().set("color", "red");
		    			span.getElement().getStyle().set("font-size", "16px");
		            	span.getElement().getStyle().set("font-weight", CROSSABLE_POINT_FONT_WEIGHT); 
		            	
		    			previousSpan = span;

		    		}
		    	}
		    }
		});
    }
	
	private void collectChildren(Component parent, List<Component> allChildren) {
        parent.getChildren().forEach(child -> {
            allChildren.add(child);
            collectChildren(child, allChildren);
        });
    }
	
	public Div resetDivStyle() {
		parentDiv.removeAll();
		parentDiv.setWidth("300px") ; // 215px 
		parentDiv.setHeight("650px"); // 520 
		parentDiv.getElement().getStyle().set("font-family", "monospace");
		return parentDiv;
	}

}

