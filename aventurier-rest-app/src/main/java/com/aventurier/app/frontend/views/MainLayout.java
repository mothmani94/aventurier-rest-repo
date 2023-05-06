package com.aventurier.app.frontend.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;


/**
 * The main view is a top-level placeholder for other views.
 * 
 * @author motmani
 *
 */
public class MainLayout extends AppLayout  {
    
    private static final long serialVersionUID = -4748758151240177628L;
    private H2 viewTitle;
    
    public MainLayout() {
    	
        addHeaderContent();   
        
    }

    private void addHeaderContent() {
    	
        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, viewTitle);
    }

    
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
