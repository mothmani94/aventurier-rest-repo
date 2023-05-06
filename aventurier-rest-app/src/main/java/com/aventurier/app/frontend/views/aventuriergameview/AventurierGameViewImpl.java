package com.aventurier.app.frontend.views.aventuriergameview;

import java.util.Optional;

import com.aventurier.app.backend.business.enums.CardinalDirectionsEnum;
import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;
import com.aventurier.app.frontend.views.MainLayout;
import com.aventurier.app.frontend.views.ccmp.CustomTextArea;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
@PageTitle(" The Adventurer game ")
@Route(value = "adventurer", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AventurierGameViewImpl extends HorizontalLayout  implements ComponentEventListener<ClickEvent<Button>> {

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

	private Button generateRandomMap;
 
    public AventurierGameViewImpl(AventurierGameController controller) {
    	
    	// init controller
       this.controller = controller;
       this.controller.setView(this);

       initViews();
      
       
       initData(this.controller.readFile());
    }
    

	public void initData(Map map) {
		this.map = map;	
		System.out.println("[map all popints] >>  "+ map.getAllMapPoints().size());
		System.out.println("[map uncrossable points] >> "+map.getUnCrossablePoints().size());
		System.out.println("[map crossable points] >> "+map.getCrossablePoints().size());
		
        mapHolderCustomTextArea.addTextFromLines(this.map.getLines());
		availableSpotsGrid.setItems(this.map.getCrossablePoints());
	}

	
	public void initViews() {
		
        /** Parent layout view, wraps part 1 and 2 **/
        HorizontalLayout parentLayoutWrapper = new HorizontalLayout();
        parentLayoutWrapper.add(buildViewPart1(), buildViewPart2());
        parentLayoutWrapper.setWidth("50%");
        parentLayoutWrapper.setAlignItems(Alignment.CENTER);
        
        add(parentLayoutWrapper);
        setJustifyContentMode(JustifyContentMode.CENTER);
	}
	
	/***
	 * Build Part-1 view: Map controllers
	 * @return
	 */
	public VerticalLayout buildViewPart1() {
		
		VerticalLayout mapControllersLayoutWrapper = new VerticalLayout();
        mapControllersLayoutWrapper.setMargin(false);
        mapControllersLayoutWrapper. setPadding(false);
        
        /** Controllers view PART-1 **/
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
        
        mapControllersLayoutWrapper.add(resetButtonWrapper,checkboxWrapper, availableSpotsGridWrapper, buttonsNSEWwrapper, textAreaWrapper, submitPositionButtonWrapper);
        mapControllersLayoutWrapper.setWidthFull();
        mapControllersLayoutWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        
        return mapControllersLayoutWrapper;

	}
	
	/***
	 * Build Part-2 view: Map and random map generator.
	 * @return
	 */
	public HorizontalLayout buildViewPart2() {
		 /** Map view PART-2 **/
	    HorizontalLayout mapHolderLayoutWrapper = new HorizontalLayout(buildMapHolder());
	    mapHolderLayoutWrapper.setWidthFull();
	    mapHolderLayoutWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
	    return mapHolderLayoutWrapper;
	}
	 
	  
	
	public Button addResetButton() {
		Button resetButton = new Button("Reset");
    	resetButton.addClickListener(event -> {
    		clearViews(false);
    	});
    	return resetButton;
	}
    
    public Checkbox addSwitchButtonLiveMode() {
    	
	   	liveModeSwitchButton = new Checkbox("Live mode");
	   	liveModeSwitchButton.setValue(false);
	   	liveModeSwitchButton.setIndeterminate(false);	  
	
	    // Add a value change listener
	   	liveModeSwitchButton.addValueChangeListener(event -> {
	          boolean isChecked = event.getValue();
	          isLiveMode = isChecked;
	          clearViews(true);
	          submitPositionButton.setEnabled(false);
	    });
	
	    return liveModeSwitchButton;
   }
    
    public VerticalLayout addAvaibleSpotsGrid() {
    	
    	VerticalLayout layout  = new VerticalLayout();
   
    	positionLabel = new Label("Choose a starting position");
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
            	positionLabel.setText( "Chosen position [" +   selectedItem.getX() + "," + selectedItem.getY() + "]");
            	gridSelectedPositionPoint = selectedItem;
                Notification.show("Chosen position [" + selectedItem.getX() + ", " + selectedItem.getY() + "]");
                mapHolderCustomTextArea.setColorAtIndex(selectedItem.getX(), selectedItem.getY());
            });
        });
        layout.add(availableSpotsGrid);
        
        layout.setAlignSelf(Alignment.CENTER, positionLabel);
        layout.setAlignSelf(Alignment.CENTER, availableSpotsGrid);
        layout.setAlignSelf(Alignment.CENTER, positionLabel);

        return layout;
    }
      	 
    
    public VerticalLayout addButtonsNSEW() {
        
    	Button northButton = new Button("Nord", VaadinIcon.ARROW_UP.create());
    	northButton.setId(""+CardinalDirectionsEnum.NORD.getCode() + "-button");
    	northButton.addClickListener(this);
    	
    	Button southButton = new Button("Sud", VaadinIcon.ARROW_DOWN.create());
    	southButton.setId(""+CardinalDirectionsEnum.SUD.getCode() + "-button");
    	southButton.addClickListener(this);
    	
    	Label label = new Label("Déplacer");
    	label.getElement().getStyle().set("padding-top", "10px");
    	label.getElement().getStyle().set("padding-left", "10px");
    	label.getElement().getStyle().set("padding-right", "10px");

        Button westButton = new Button("Ouest", VaadinIcon.ARROW_LEFT.create());
        westButton.setId(""+CardinalDirectionsEnum.OUEST.getCode() + "-button");
        westButton.addClickListener(this);
        
        Button eastButton = new Button("Est", VaadinIcon.ARROW_RIGHT.create());
        eastButton.setId(""+CardinalDirectionsEnum.EST.getCode() + "-button");
        eastButton.addClickListener(this);
        
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
    	
        submitPositionButton = new Button("Submit position");
        submitPositionButton.addClickListener(event ->  {
        	if (gridSelectedPositionPoint == null) {
        		Notification.show("Please choose a starting position !", 1000, Notification.Position.BOTTOM_END);
        		return;
        	}
        	if (nextPositionHolderTextArea.getValue().isEmpty()) {
        		Notification.show("Please choose a path to follow !", 1000, Notification.Position.BOTTOM_END);
        		return;
        	}
        	Optional<Point> pos = controller.resolvePosition(this.map, gridSelectedPositionPoint, nextPositionHolderTextArea.getValue());
        	if(pos.isPresent()) {
        		Notification.show("New position ("+pos.get().getX()+"," + pos.get().getY()+")", 1000, Notification.Position.BOTTOM_END);
        		submitPositionButton.setText("New position ["+pos.get().getX()+"," + pos.get().getY()+"]");
                mapHolderCustomTextArea.setColorAtIndex(pos.get().getX(), pos.get().getY());
        	}else {
        		Notification.show("Illegal path !", 1000, Notification.Position.MIDDLE);
        	}
        	
        });
        return submitPositionButton;

    }
    
    public VerticalLayout buildMapHolder() {
    	VerticalLayout layout = new VerticalLayout();
    	
    	Label label = new Label("Adventurer Map");
    	layout.add(label);
        layout.setAlignSelf(Alignment.CENTER, label);

    	mapHolderCustomTextArea = new CustomTextArea();
    	layout.add(mapHolderCustomTextArea);
        layout.setAlignSelf(Alignment.CENTER, mapHolderCustomTextArea);

    	
        generateRandomMap = new Button("Generate random map");
        layout.add(generateRandomMap);
        layout.setAlignSelf(Alignment.CENTER, generateRandomMap);


		return layout;
    }

	
   public void clearViews(boolean isFromSwitch) {
	   
		if(!isFromSwitch) {
			liveModeSwitchButton.setValue(false);
			submitPositionButton.setEnabled(false);
		}
    	positionLabel.setText("Choose a starting position");
		nextPositionHolderTextArea.clear();
		submitPositionButton.setText("Submit position");
		submitPositionButton.setEnabled(true);
		gridSelectedPositionPoint = null;
		availableSpotsGrid.deselectAll();
		mapHolderCustomTextArea.addTextFromLines(this.map.getLines());
    }

	public void cardinalButtonsCallBack(CardinalDirectionsEnum cardinalDirectionEnum) {
	    
	    if(gridSelectedPositionPoint == null) {
	        Notification.show("Please choose a starting position !", 1000, Notification.Position.BOTTOM_END);
	    	return;
	    }
	    
	    String newLabel = nextPositionHolderTextArea.getValue().isEmpty() ? cardinalDirectionEnum.getCode(): nextPositionHolderTextArea.getValue() 
	    	+ "-" + cardinalDirectionEnum.getCode();
	    nextPositionHolderTextArea.setValue(newLabel);
	
	    if(isLiveMode) {
	    	Optional<Point> pos = controller.resolvePosition(this.map, gridSelectedPositionPoint, cardinalDirectionEnum.getCode());
	    	if(pos.isPresent()) {
	    		gridSelectedPositionPoint = pos.get();
	    		Notification.show("New position ["+pos.get().getX()+"," + pos.get().getY()+"]", 1000, Notification.Position.BOTTOM_END);
	    		submitPositionButton.setText("New position ["+pos.get().getX()+"," + pos.get().getY()+"]");
	            mapHolderCustomTextArea.setColorAtIndex(pos.get().getX(), pos.get().getY());
	    	}else {
	    		Notification.show("Illegal path !", 1000, Notification.Position.MIDDLE);
	    	}
	    }
		    
	}


	@Override
	public void onComponentEvent(ClickEvent<Button> event) {
		System.out.println("component id >> " + event.getSource().getId().get());
		Optional<String> optionalComponentId = event.getSource().getId();
		if(optionalComponentId.isPresent()) {
			
			String componentId = optionalComponentId.get();
			switch (componentId) {
				case "N-button" -> cardinalButtonsCallBack(CardinalDirectionsEnum.NORD);
				case "S-button" -> cardinalButtonsCallBack(CardinalDirectionsEnum.SUD);
				case "E-button" -> cardinalButtonsCallBack(CardinalDirectionsEnum.EST);
				case "O-button" -> cardinalButtonsCallBack(CardinalDirectionsEnum.OUEST);
				default -> throw new IllegalArgumentException("Unexpected value: " + componentId);
			}
			
		}
	}



}
