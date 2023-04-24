package com.aventurier.app.frontend.views.aventuriergameview;

import java.util.Optional;

import com.aventurier.app.backend.business.enums.CardinalDirectionsEnum;
import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;
import com.aventurier.app.backend.business.service.AventurierGameFacadeService;
import com.aventurier.app.frontend.views.MainLayout;
import com.aventurier.app.frontend.views.ccmp.CustomTextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

/***
 * 
 * @author motmani
 *
 */
@PageTitle("Aventurier")
@Route(value = "aventurier", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AventurierGameViewImpl extends HorizontalLayout {

    private static final long serialVersionUID = -1213258900267954384L;
    
	private AventurierGameController controller;

    private Map map;
	private TextArea nextPositionHolderTextArea;
	private Checkbox liveModeSwitchButton;
	private Button submitPositionButton;
	private Point gridSelectedPositionPoint;    
	private Grid<Point> availableSpotsGrid;
	private CustomTextArea mapHolderCustomTextArea;

	private Label positionLabel;

	private boolean isLiveMode;
 
    public AventurierGameViewImpl(AventurierGameController controller) {
    	
    	// init controller
       this.controller = controller;
       this.controller.setView(this);

       initViews();
      
       
       initData(this.controller.readFile());
    }
    

	public void initData(Map map) {
		this.map = map;	
		System.out.println("map all popints >> "+ map.getAllMapPoints().size());
		System.out.println("map uncrossable points"+map.getUnCrossablePoints().size());
		System.out.println("crossable "+map.getCrossablePoints().size());
		
        mapHolderCustomTextArea.addTextFromLines(this.map.getLines());
		availableSpotsGrid.setItems(this.map.getCrossablePoints());
	}

	
	public void initViews() {
		
		VerticalLayout mapControllersLayoutWrapper = new VerticalLayout();
        mapControllersLayoutWrapper.setMargin(false);
        mapControllersLayoutWrapper. setPadding(false);
        
        
        // Wrap each component in a HorizontalLayout with width set to full
        HorizontalLayout resetButtonWrapper = new HorizontalLayout(addResetButton());
        resetButtonWrapper.setWidthFull();
        resetButtonWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        
        HorizontalLayout checkboxWrapper = new HorizontalLayout(addSwitchButtonLiveMode());
        checkboxWrapper.setWidthFull();
        checkboxWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        
        HorizontalLayout availableSpotsGridWrapper = new HorizontalLayout(addAvaibleSpotsGrid());
        availableSpotsGridWrapper.setWidthFull();
        availableSpotsGridWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        
        HorizontalLayout buttonsNSEWwrapper = new HorizontalLayout(addButtonsNSEW());
        buttonsNSEWwrapper.setWidthFull();
        buttonsNSEWwrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        HorizontalLayout textAreaWrapper = new HorizontalLayout(addNextPositionHolderTextArea());
        textAreaWrapper.setWidthFull();
        textAreaWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        HorizontalLayout submitPositionButtonWrapper = new HorizontalLayout(addSubmitPositionButton());
        submitPositionButtonWrapper.setWidthFull();
        submitPositionButtonWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        
        // Add the wrapping HorizontalLayouts to the parent mapControllersLayoutWrapper
        mapControllersLayoutWrapper.add(resetButtonWrapper,checkboxWrapper, availableSpotsGridWrapper, buttonsNSEWwrapper, textAreaWrapper, submitPositionButtonWrapper);
        mapControllersLayoutWrapper.setWidthFull();
        mapControllersLayoutWrapper.setJustifyContentMode(JustifyContentMode.CENTER);

        HorizontalLayout mapHolderLayoutWrapper = new HorizontalLayout(buildMapHolder());
        mapHolderLayoutWrapper.setWidthFull();
        mapHolderLayoutWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        
        HorizontalLayout parentLayoutWrapper = new HorizontalLayout();
        parentLayoutWrapper.add(mapControllersLayoutWrapper, mapHolderLayoutWrapper);
        parentLayoutWrapper.setWidth("50%");
        parentLayoutWrapper.setAlignItems(Alignment.CENTER);
        
        
        add(parentLayoutWrapper);
        setJustifyContentMode(JustifyContentMode.CENTER);
	}
	
	public Button addResetButton() {
		Button resetButton = new Button("Réinitialiser");
    	resetButton.addClickListener(event -> {
    		clearViews(false);
    	});
    	return resetButton;
	}
    
    public Checkbox addSwitchButtonLiveMode() {
    	
	   	liveModeSwitchButton = new Checkbox("Mode direct");
	   	liveModeSwitchButton.setValue(false);
	   	liveModeSwitchButton.setIndeterminate(false);	   	
	
	    // Add a value change listener
	   	liveModeSwitchButton.addValueChangeListener(event -> {
	          boolean isChecked = event.getValue();
	          isLiveMode = isChecked;
	          clearViews(true);
	    });
	
	    return liveModeSwitchButton;
   }
    
    public VerticalLayout addAvaibleSpotsGrid() {
    	
    	VerticalLayout layout  = new VerticalLayout();
    	
    	String label = "Choisir une position de départ";
    	Label gridDescription = new Label(label);
    	layout.add(gridDescription);
   
    	positionLabel = new Label();
    	layout.add(positionLabel);

    	  // Create a Grid to display the positions
        availableSpotsGrid = new Grid<>(Point.class, false);
        availableSpotsGrid.removeAllColumns(); // Remove auto-generated columns
        availableSpotsGrid.setWidth("300px");
        availableSpotsGrid.setHeight("300px");
        availableSpotsGrid.setSelectionMode(SelectionMode.SINGLE);

        // Add the x and y columns with custom width
        availableSpotsGrid.addColumn(Point::getX).setHeader("x");
        availableSpotsGrid.addColumn(Point::getY).setHeader("y");
        // Add a selection listener to the Grid
        availableSpotsGrid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(selectedItem -> {
            	            	
                // Perform an action with the selected item
            	positionLabel.setText( "Position choisie : (" +   selectedItem.getX() + "," + selectedItem.getY() + ")");
            	gridSelectedPositionPoint = selectedItem;
                Notification.show("Position choisie : (" + selectedItem.getX() + ", " + selectedItem.getY() + ")");
                mapHolderCustomTextArea.setColorAtIndex(selectedItem.getX(), selectedItem.getY());
            });
        });
        layout.add(availableSpotsGrid);
        
        layout.setAlignSelf(Alignment.CENTER, gridDescription);
        layout.setAlignSelf(Alignment.CENTER, availableSpotsGrid);
        layout.setAlignSelf(Alignment.CENTER, positionLabel);

        return layout;
    }
      	 
    
    public VerticalLayout addButtonsNSEW() {
        
    	Button northButton = new Button("Nord", VaadinIcon.ARROW_UP.create());
    	northButton.addClickListener(event -> {
            
            if(gridSelectedPositionPoint == null) {
                Notification.show("Veuillez choisir une position de départ !", 1000, Notification.Position.BOTTOM_END);
            	return;
            }
            
            String newLabel = nextPositionHolderTextArea.getValue().isEmpty() ? CardinalDirectionsEnum.NORD.getCode(): nextPositionHolderTextArea.getValue() 
            	+ "-" + CardinalDirectionsEnum.NORD.getCode();
            nextPositionHolderTextArea.setValue(newLabel);

            if(isLiveMode) {
            	Optional<Point> pos = controller.resolvePosition(this.map, gridSelectedPositionPoint, CardinalDirectionsEnum.NORD.getCode());
            	if(pos.isPresent()) {
            		gridSelectedPositionPoint = pos.get();
            		Notification.show("Nouvelle position ("+pos.get().getX()+"," + pos.get().getY()+")", 1000, Notification.Position.BOTTOM_END);
            		submitPositionButton.setText("Nouvelle position ( "+pos.get().getX()+"," + pos.get().getY()+")");
                    mapHolderCustomTextArea.setColorAtIndex(pos.get().getX(), pos.get().getY());
            	}else {
            		Notification.show("Chemin illégal !", 1000, Notification.Position.MIDDLE);
            	}
            }
            
           
   		 // if live mode is ON
          //  this.controller.moveAventurier(CardinalDirectionsEnum.NORTH, grisSelectedPositionPoint);
        });
    	
    	Button southButton = new Button("Sud", VaadinIcon.ARROW_DOWN.create());
    	southButton.addClickListener(event -> {

    		if(gridSelectedPositionPoint == null) {
                 Notification.show("Veuillez choisir une position de départ !", 1000, Notification.Position.BOTTOM_END);
             	return;
             }
    		 String newLabel = nextPositionHolderTextArea.getValue().isEmpty() ? CardinalDirectionsEnum.SUD.getCode(): nextPositionHolderTextArea.getValue() 
             	+ "-" + CardinalDirectionsEnum.SUD.getCode();
             nextPositionHolderTextArea.setValue(newLabel);            
    		 
             if(isLiveMode) {
             	Optional<Point> pos = controller.resolvePosition(this.map, gridSelectedPositionPoint, CardinalDirectionsEnum.SUD.getCode());
             	if(pos.isPresent()) {
             		gridSelectedPositionPoint = pos.get();
             		Notification.show("Nouvelle position ("+pos.get().getX()+"," + pos.get().getY()+")", 1000, Notification.Position.BOTTOM_END);
             		submitPositionButton.setText("Nouvelle position ( "+pos.get().getX()+"," + pos.get().getY()+")");
                     mapHolderCustomTextArea.setColorAtIndex(pos.get().getX(), pos.get().getY());
             	}else {
             		Notification.show("Chemin illégal !", 1000, Notification.Position.MIDDLE);
             	}
             }
        });
    	Label label = new Label("Déplacer");
    	label.getElement().getStyle().set("padding-top", "10px");
    	label.getElement().getStyle().set("padding-left", "10px");
    	label.getElement().getStyle().set("padding-right", "10px");

        Button westButton = new Button("Ouest", VaadinIcon.ARROW_LEFT.create());
        westButton.addClickListener(event -> {

        	if(gridSelectedPositionPoint == null) {
                 Notification.show("Veuillez choisir une position de départ !", 1000, Notification.Position.BOTTOM_END);
             	return;
             }
    		 String newLabel = nextPositionHolderTextArea.getValue().isEmpty() ? CardinalDirectionsEnum.OUEST.getCode(): nextPositionHolderTextArea.getValue() 
              	+ "-" + CardinalDirectionsEnum.OUEST.getCode();
              nextPositionHolderTextArea.setValue(newLabel);       
    		 
              if(isLiveMode) {
               	Optional<Point> pos = controller.resolvePosition(this.map, gridSelectedPositionPoint, CardinalDirectionsEnum.OUEST.getCode());
               	if(pos.isPresent()) {
               		gridSelectedPositionPoint = pos.get();
               		Notification.show("Nouvelle position ("+pos.get().getX()+"," + pos.get().getY()+")", 1000, Notification.Position.BOTTOM_END);
               		submitPositionButton.setText("Nouvelle position ( "+pos.get().getX()+"," + pos.get().getY()+")");
                       mapHolderCustomTextArea.setColorAtIndex(pos.get().getX(), pos.get().getY());
               	}else {
               		Notification.show("Chemin illégal !", 1000, Notification.Position.MIDDLE);
               	}
               }
        });
        
        Button eastButton = new Button("Est", VaadinIcon.ARROW_RIGHT.create());
        eastButton.addClickListener(event -> {

        	if(gridSelectedPositionPoint == null) {
                 Notification.show("Veuillez choisir une position de départ !", 1000, Notification.Position.BOTTOM_END);
             	return;
             }
    		 String newLabel = nextPositionHolderTextArea.getValue().isEmpty() ? CardinalDirectionsEnum.EST.getCode(): nextPositionHolderTextArea.getValue() 
               	+ "-" + CardinalDirectionsEnum.EST.getCode();
               nextPositionHolderTextArea.setValue(newLabel);  
    		 
               if(isLiveMode) {
                  	Optional<Point> pos = controller.resolvePosition(this.map, gridSelectedPositionPoint, CardinalDirectionsEnum.EST.getCode());
                  	if(pos.isPresent()) {
                  		gridSelectedPositionPoint = pos.get();
                  		Notification.show("Nouvelle position ("+pos.get().getX()+"," + pos.get().getY()+")", 1000, Notification.Position.BOTTOM_END);
                  		submitPositionButton.setText("Nouvelle position ( "+pos.get().getX()+"," + pos.get().getY()+")");
                        mapHolderCustomTextArea.setColorAtIndex(pos.get().getX(), pos.get().getY());
                  	}else {
                  		Notification.show("Chemin illégal !", 1000, Notification.Position.MIDDLE);
                  	}
                  }
        });
  
        HorizontalLayout northButtonLayout = new HorizontalLayout();
        northButtonLayout.setAlignSelf(Alignment.CENTER, northButton);
        
        HorizontalLayout southButtonLayout = new HorizontalLayout();
        southButtonLayout.setAlignSelf(Alignment.CENTER, southButton);
        
        HorizontalLayout horizontalLayout = new HorizontalLayout(westButton, label, eastButton);
        horizontalLayout.setVerticalComponentAlignment(Alignment.CENTER, westButton, eastButton);

        VerticalLayout buttonLayout = new VerticalLayout(northButton, horizontalLayout, southButton);
        buttonLayout.setAlignSelf(Alignment.START, northButtonLayout);
        buttonLayout.setAlignSelf(Alignment.CENTER, horizontalLayout);
        buttonLayout.setAlignSelf(Alignment.END, southButtonLayout);
        return buttonLayout;
    }
    
    public TextArea addNextPositionHolderTextArea() {
    	nextPositionHolderTextArea = new TextArea();
    	nextPositionHolderTextArea.setWidth("200px") ; 
    	nextPositionHolderTextArea.getElement().getStyle().set("font-family", "monospace");
    	nextPositionHolderTextArea.setReadOnly(true);
        return nextPositionHolderTextArea;
    }
    
    
    public Button addSubmitPositionButton() {
    	
        submitPositionButton = new Button("Soumettre position");
        submitPositionButton.addClickListener(event ->  {
        	if (gridSelectedPositionPoint == null) {
        		Notification.show("Veuillez choisir une position de départ !", 1000, Notification.Position.BOTTOM_END);
        		return;
        	}
        	if (nextPositionHolderTextArea.getValue().isEmpty()) {
        		Notification.show("Veuillez choisir le chemin à suivre !", 1000, Notification.Position.BOTTOM_END);
        		return;
        	}
        	Optional<Point> pos = controller.resolvePosition(this.map, gridSelectedPositionPoint, nextPositionHolderTextArea.getValue());
        	if(pos.isPresent()) {
        		Notification.show("Nouvelle position ("+pos.get().getX()+"," + pos.get().getY()+")", 1000, Notification.Position.BOTTOM_END);
        		submitPositionButton.setText("Nouvelle position ( "+pos.get().getX()+"," + pos.get().getY()+")");
                mapHolderCustomTextArea.setColorAtIndex(pos.get().getX(), pos.get().getY());
        	}else {
        		Notification.show("Chemin illégal !", 1000, Notification.Position.MIDDLE);
        	}
        	
        });
        return submitPositionButton;

    }
    
    public CustomTextArea buildMapHolder() {
    	mapHolderCustomTextArea = new CustomTextArea();
		mapHolderCustomTextArea.getElement().getStyle().set("font-family", "monospace");    	
		return mapHolderCustomTextArea;
    }

	
   public void clearViews(boolean isFromSwitch) {
	if(!isFromSwitch) {
		liveModeSwitchButton.setValue(false);
	}
	nextPositionHolderTextArea.clear();
	submitPositionButton.setText("Soumettre position");
	positionLabel.setText("");
	gridSelectedPositionPoint = null;
	availableSpotsGrid.deselectAll();
	mapHolderCustomTextArea.addTextFromLines(this.map.getLines());
    }


}
