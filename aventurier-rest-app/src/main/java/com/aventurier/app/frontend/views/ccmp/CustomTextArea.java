package com.aventurier.app.frontend.views.ccmp;

import java.util.List;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

/***
 * 
 * @author motmani
 *
 */
public class CustomTextArea extends Div {
    
    private static final long serialVersionUID = -2896387779935200051L;
	private List<String> lines;
    
    public CustomTextArea() {
    	resetDivStyle();

    }
    
	public CustomTextArea(String text) {
        setText(text);
    }
		
	public void addTextFromLines(List<String> lines) {
		removeAll();
		int index = 0;
		for (String str : lines) {
			Div line = new Div();
            for (char c : str.toCharArray()) {
                String charAsString = Character.toString(c);
                Span characterSpan = new Span(charAsString);
                characterSpan.setId(""+index);
                characterSpan.getElement().getStyle().set("white-space", "pre");
                line.add(characterSpan);
                index++;
            }
            add(line);
        }
		this.lines = lines;
	}
	
	public void setColorAtIndex(int x, int y) {
    
		removeAll();
		resetDivStyle();
		
        int c = 0;
		for (String str : lines) {
			Div line = new Div();
			int k =0;
            for (char chr : str.toCharArray()) {
                String charAsString = Character.toString(chr);
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
            add(line);
            c++;
        }
               
    }
	
	public void resetDivStyle() {
		setWidth("215px") ; 
    	setHeight("520px"); // 460 
    	getElement().getStyle().set("font-family", "monospace");

	}

}

