package com.aventurier.app.frontend.views.ccmp;

import java.util.List;

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
	private List<String> lines;
	private Div parentDiv;
    
    public CustomTextArea() {
		
    	addClassName("border-vertical-layout"); //border
    	setWidth("230px");
        
    	parentDiv = new Div();
    	add(resetDivStyle());
    }
		
	public void addTextFromLines(List<String> lines) {
		parentDiv.removeAll();
		int index = 0;
		for (String str : lines) {
			Div line = new Div();
            for (char c : str.toCharArray()) {
                String charAsString = Character.toString(c);
                if(" ".equals(charAsString)) {
                	charAsString = "-";
                }
                Span characterSpan = new Span(charAsString);
                characterSpan.setId(""+index);
                characterSpan.getElement().getStyle().set("white-space", "pre");
                line.add(characterSpan);
                index++;
            }
            parentDiv.add(line);
        }
		this.lines = lines;
	}
	
	/*TO DO improve func*/
	public void setColorAtIndex(int x, int y) {
    
		parentDiv.removeAll();
		resetDivStyle();
		
        int c = 0;
		for (String str : lines) {
			Div line = new Div();
			int k =0;
            for (char chr : str.toCharArray()) {
                String charAsString = Character.toString(chr);
                if(" ".equals(charAsString)) {
                	charAsString = "-";
                }
                Span characterSpan = new Span(charAsString);
                characterSpan.getElement().getStyle().set("white-space", "pre");
                line.add(characterSpan);
                
                if(x == k && y == c) {
                	characterSpan.setText("A");
                	characterSpan.getElement().getStyle().set("color", "red");
                	characterSpan.getElement().getStyle().set("font-size", "10px");
                }
                k++;
            }
            parentDiv.add(line);
            c++;
        }
               
    }
	
	public Div resetDivStyle() {
		parentDiv.setWidth("215px") ; 
		parentDiv.setHeight("520px"); // 460 
		parentDiv.getElement().getStyle().set("font-family", "monospace");
		return parentDiv;
	}

}

