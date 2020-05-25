package ru.max.swfoc.ui;



import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import lombok.Data;
import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.GameEntities;
import ru.ifmo.swfoc.editor.model.FactionUnit;
import ru.ifmo.swfoc.editor.model.MCampaign;
import ru.ifmo.swfoc.editor.model.MPlanet;
import ru.max.swfoc.ui.menu.UIMenu;
@Data
public class Graphics implements IGraphics{
	private GameEntities gameEntities;
	private EditorCore editor;
	private Text filename;
	private Text xmlName;
	private Text name;
	private Text textId;
	private Text descriptionId;
	private Text campaignSet;
	private Text sortOrder;
	private Text description;
	private Text cameraShiftX;
	private Text cameraShiftY;
	private Text cameraDistance;
	private Text singlePlayerRebelStory;
	private Text singlePlayerEmpireStory;
	private Text singlePlayerUnderworldStory;
	private Text singlePlayerActivePlayer;
	private Combo isMultiplayer;
	private Combo supportsCustomSettings;
	private Combo showCompletedTab;
	private Combo isEdited;
//	private ScrolledComposite listOfPlanets;
	ScrolledComposite sfive;
	private ScrolledComposite sccampaigns;	//������� ��� listOfCampaigns ��� ����������� ���������
	private ScrolledComposite scrCamPlanets;	//������� ��� camPlanetsList ��� ����������� ���������
	private ScrolledComposite scHumanVictoryConditions;	//������� ��� humanVictoryConditions ��� ����������� ���������
	private ScrolledComposite scAiVictoryConditions;	//������� ��� aiVictoryConditions ��� ����������� ���������
	private ScrolledComposite scSpecialCaseProduction;	//������� ��� specialCaseProduction ��� ����������� ���������
	private ScrolledComposite scStartingForces;	//������� ��� startingForces ��� ����������� ���������
	private ScrolledComposite scAllPlanets;	//������� ��� allPlanets ��� ����������� ���������
	private ScrolledComposite scCamTradeRoutes;	//������� ��� camTradeRoutesList ��� ����������� ���������
	private ScrolledComposite scForProperties;	//������� ��� compositeForProperties ��� ����������� ���������
	private List humanVictoryConditions;	
	private List aiVictoryConditions;
	private List specialCaseProduction;
	private List startingForces;
	private List allPlanets;
	private List camPlanetsList;
	private List listOfCampaigns;
	private List camTradeRoutesList;
	private MCampaign campaign;
	private Composite compositeForProperties;	//����� ������ ������� ���� ��� ����������� ������� ��������
	Composite factionPropertiesComposite = null;	//��� ���� ������ compositeForProperties ������� ���������� ������� � ��������� � ���������� ����������
	Text[] aicontrol;		
	Text[] startingcred;
	Text[] startingtech;
	Text[] maxtech;
	Text[] homelocation;
	Text[] markupfiles;
	Label[] lfactions;
	
	public Graphics(EditorCore editor){
		this.gameEntities = editor.getGameEntitiesStore();
		this.editor = editor;
		
	}
	
	@Override
	public void launchUI(Display display, Shell shell) {
		UIMenu uimenu = new UIMenu(shell, this);
	
	//	UIToolMenu uitoolmenu = new UIToolMenu(shell);
		
	    shell.setLayout(new FillLayout());
		SashForm shellsash = new SashForm(shell, SWT.HORIZONTAL);
		
		Composite [] first3CompositesHorizontal = new Composite[3];	//������� ���� �� 3 �����, ������ ��� �������
		for(int i = 0; i < 3; i++) {
			first3CompositesHorizontal[i] = new Composite(shellsash, SWT.NONE);
			first3CompositesHorizontal[i].setBackground(new Color(display, 12*i, 20 * i, 10* i));
		}
		shellsash.setWeights(new int[] { 1, 3, 1 });	
		SashForm sashForFirstComposite = new SashForm(first3CompositesHorizontal[0], SWT.VERTICAL);
		first3CompositesHorizontal[0].setLayout(new FillLayout());
		Composite[] left2VerticalComposites = new Composite[2];	//������� ����� ����� �� ��� ������������, ������ ��������, ����� �������
		for(int i = 0; i < 2; i++) {
			left2VerticalComposites[i] = new Composite(sashForFirstComposite, SWT.NONE);
			left2VerticalComposites[i].setBackground(new Color(display, 100*i, 20 * i, 30* i));
		}
		sashForFirstComposite.setWeights(new int[] { 1, 1 });
		SashForm secondSashForSecondComposite = new SashForm(first3CompositesHorizontal[1], SWT.VERTICAL);
		first3CompositesHorizontal[1].setLayout(new FillLayout());
		Composite[] compositesOfSecondSash = new Composite[2];	//������� ���������� ����� �� ��� ������������, ������ ����� ������� ��������, �������� ���� � ���� ���������, ����� �������, ����� ����������� ���� ��� �������������� ��������
		for(int i = 0; i < 2; i++) {
			compositesOfSecondSash[i] = new Composite(secondSashForSecondComposite, SWT.NONE);
			compositesOfSecondSash[i].setBackground(new Color(display, 100*i+50, 50 * i, 30* i+100));
		}
		secondSashForSecondComposite.setWeights(new int[] { 4, 2 });
		SashForm sashForCenterTopComposite = new SashForm(compositesOfSecondSash[0], SWT.HORIZONTAL);
		compositesOfSecondSash[0].setLayout(new FillLayout());
		Composite[] compositesOfTopCenterSash = new Composite[2];	//������� ���������� ������� ����� �� ����� - ������� �������� � ������ ��� ����� ���������� �� ��������� ����, ����� � ���� ���������
		for(int i = 0; i < 2; i++) {
			compositesOfTopCenterSash[i] = new Composite(sashForCenterTopComposite, SWT.NONE);
			compositesOfTopCenterSash[i].setBackground(new Color(display, 100*i+50, 50 * i+40, 30* i+100));
		}
		sashForCenterTopComposite.setWeights(new int[] { 1, 2 });
		
		SashForm sashForTopCenterRightComposite = new SashForm(compositesOfTopCenterSash[1], SWT.VERTICAL);
		compositesOfTopCenterSash[1].setLayout(new FillLayout());
		Composite[] compositesVerticalOfTopCenterRight = new Composite[2];	//������� ���������� ������� ����� ��� �������� ������ ����� ����������� ������� �� ������ ��� ������ � ������� ��� ����������� ������� �� �������� ���� � �������������
		for(int i = 0; i < 2; i++) {
			compositesVerticalOfTopCenterRight[i] = new Composite(sashForTopCenterRightComposite, SWT.NONE);
			compositesVerticalOfTopCenterRight[i].setBackground(new Color(display, 200*i+50, 50 * i+40, 30* i+100));
		}
		sashForTopCenterRightComposite.setWeights(new int[] { 1, 2 });
		
		SashForm sashForMostLittle2HorizontalTopCenterComposites = new SashForm(compositesVerticalOfTopCenterRight[0], SWT.HORIZONTAL);
		compositesVerticalOfTopCenterRight[0].setLayout(new FillLayout());
		Composite[] mostLittleCompositesTopCenterRight = new Composite[2];	//�������� ���� � �������������
		for(int i = 0; i < 2; i++) {
			mostLittleCompositesTopCenterRight[i] = new Composite(sashForMostLittle2HorizontalTopCenterComposites, SWT.NONE);
			mostLittleCompositesTopCenterRight[i].setBackground(new Color(display, 200*i+50, 50 * i+100, 30* i+100));
		}
		
		left2VerticalComposites[0].setLayout(new GridLayout());
		Label lcam = new Label(left2VerticalComposites[0], SWT.NONE);
		lcam.setText("Campaigns:");
		
		sccampaigns = new ScrolledComposite(left2VerticalComposites[0], SWT.V_SCROLL | SWT.H_SCROLL);
		sccampaigns.setLayout(new GridLayout());
		sccampaigns.setBackground(new Color(display, 255, 0, 0));
		sccampaigns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		listOfCampaigns = new List(sccampaigns, SWT.SINGLE);
		listOfCampaigns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sccampaigns.setContent(listOfCampaigns);
		sccampaigns.setExpandHorizontal(true);
		sccampaigns.setExpandVertical(true);
		
		
		
		left2VerticalComposites[1].setLayout(new GridLayout());
		Label lplanets = new Label(left2VerticalComposites[1], SWT.NONE);
		lplanets.setText("Planets:");
		scAllPlanets = new ScrolledComposite(left2VerticalComposites[1], SWT.V_SCROLL | SWT.H_SCROLL);
		scAllPlanets.setLayout(new GridLayout());
		scAllPlanets.setBackground(new Color(display, 0, 250, 0));
		scAllPlanets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		allPlanets = new List(scAllPlanets, SWT.SINGLE);
		allPlanets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scAllPlanets.setContent(allPlanets);
		scAllPlanets.setExpandHorizontal(true);
		scAllPlanets.setExpandVertical(true);
		
		
		compositesOfTopCenterSash[0].setLayout(new GridLayout());
		Label lcamplanets = new Label(compositesOfTopCenterSash[0], SWT.NONE);
		lcamplanets.setText("Campaigns's planets:");
		scrCamPlanets = new ScrolledComposite(compositesOfTopCenterSash[0], SWT.V_SCROLL | SWT.H_SCROLL);
		scrCamPlanets.setLayout(new GridLayout());
		scrCamPlanets.setBackground(new Color(display, 0, 0, 250));
		scrCamPlanets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		camPlanetsList = new List(scrCamPlanets, SWT.SINGLE);
		camPlanetsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrCamPlanets.setContent(camPlanetsList);
		scrCamPlanets.setExpandHorizontal(true);
		scrCamPlanets.setExpandVertical(true);
		
		
		mostLittleCompositesTopCenterRight[0].setLayout(new GridLayout());
		Label lcamTrades = new Label(mostLittleCompositesTopCenterRight[0], SWT.NONE);
		lcamTrades.setText("Campaigns's trade routes:");
		scCamTradeRoutes = new ScrolledComposite(mostLittleCompositesTopCenterRight[0], SWT.V_SCROLL | SWT.H_SCROLL);
		scCamTradeRoutes.setLayout(new GridLayout());
		scCamTradeRoutes.setBackground(new Color(display, 0, 0, 250));
		scCamTradeRoutes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		camTradeRoutesList = new List(scCamTradeRoutes, SWT.SINGLE);
		camTradeRoutesList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scCamTradeRoutes.setContent(camTradeRoutesList);
		scCamTradeRoutes.setExpandHorizontal(true);
		scCamTradeRoutes.setExpandVertical(true);
		
		
		mostLittleCompositesTopCenterRight[1].setLayout(new GridLayout());
		Label lcamSpecProd = new Label(mostLittleCompositesTopCenterRight[1], SWT.NONE);
		lcamSpecProd.setText("Campaigns's special case production:");
		scSpecialCaseProduction = new ScrolledComposite(mostLittleCompositesTopCenterRight[1], SWT.V_SCROLL | SWT.H_SCROLL);
		scSpecialCaseProduction.setLayout(new GridLayout());
		scSpecialCaseProduction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		specialCaseProduction = new List(scSpecialCaseProduction, SWT.SINGLE);
		specialCaseProduction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scSpecialCaseProduction.setContent(specialCaseProduction);
		scSpecialCaseProduction.setExpandHorizontal(true);
		scSpecialCaseProduction.setExpandVertical(true);
		
		
		compositesVerticalOfTopCenterRight[1].setLayout(new GridLayout());
		Label lunits= new Label(compositesVerticalOfTopCenterRight[1], SWT.NONE);
		lunits.setText("Planet's units:");
		scStartingForces = new ScrolledComposite(compositesVerticalOfTopCenterRight[1], SWT.V_SCROLL | SWT.H_SCROLL);
		scStartingForces.setLayout(new GridLayout());
		scStartingForces.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		startingForces = new List(scStartingForces, SWT.SINGLE);
		startingForces.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scStartingForces.setContent(startingForces);
		scStartingForces.setExpandHorizontal(true);
		scStartingForces.setExpandVertical(true);
		
		
		
		first3CompositesHorizontal[2].setLayout(new FillLayout());
		scForProperties = new ScrolledComposite(first3CompositesHorizontal[2], SWT.V_SCROLL | SWT.H_SCROLL);
		scForProperties.setLayout(new GridLayout());
		scForProperties.setBackground(new Color(display, 240, 190, 140));
		compositeForProperties = new Composite(scForProperties, SWT.NONE);
		compositeForProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL));
		compositeForProperties.setLayout(new GridLayout(2, false));
		compositeForProperties.setBackground(new Color(display, 240, 190, 14));
		scForProperties.setContent(compositeForProperties);
		scForProperties.setExpandHorizontal(true);
		scForProperties.setExpandVertical(true);
		
		Label lisedited = new Label(compositeForProperties, SWT.NONE);
		lisedited.setText("Is Edited:");
		isEdited = new Combo(compositeForProperties, SWT.DROP_DOWN);
		isEdited.add("false");
		isEdited.add("true");
		GridData grForProperties = new GridData();
		grForProperties.widthHint = 300;
		
		Label lfname = new Label(compositeForProperties, SWT.NONE);
		lfname.setText("File Name:");
		filename = new Text(compositeForProperties, SWT.SINGLE);
		filename.setLayoutData(grForProperties);
		
		Label lxmlname = new Label(compositeForProperties, SWT.NONE);
		lxmlname.setText("Xml Name:");
		xmlName = new Text(compositeForProperties, SWT.SINGLE);
		xmlName.setLayoutData(grForProperties);
		
		Label ltextid = new Label(compositeForProperties, SWT.NONE);
		ltextid.setText("Text ID:");
		textId = new Text(compositeForProperties, SWT.SINGLE);
		textId.setLayoutData(grForProperties);
		
		Label lname = new Label(compositeForProperties, SWT.NONE);
		lname.setText("Name:");
		name = new Text(compositeForProperties, SWT.SINGLE);
		name.setLayoutData(grForProperties);
		
		Label lcs = new Label(compositeForProperties, SWT.NONE);
		lcs.setText("Campaign Set:");
		campaignSet = new Text(compositeForProperties, SWT.SINGLE);
		campaignSet.setLayoutData(grForProperties);
		
		Label lsortorder = new Label(compositeForProperties, SWT.NONE);
		lsortorder.setText("Sort Order:");
		sortOrder = new Text(compositeForProperties, SWT.SINGLE);
		sortOrder.setLayoutData(grForProperties);
		
		Label ldescid = new Label(compositeForProperties, SWT.NONE);
		ldescid.setText("Description ID:");
		descriptionId = new Text(compositeForProperties, SWT.SINGLE);
		descriptionId.setLayoutData(grForProperties);
		
		Label ldesc = new Label(compositeForProperties, SWT.NONE);
		ldesc.setText("Description:");
		description = new Text(compositeForProperties, SWT.SINGLE);
		description.setLayoutData(grForProperties);
		
		Label lcamX = new Label(compositeForProperties, SWT.NONE);
		lcamX.setText("Camera Shift X:");
		cameraShiftX = new Text(compositeForProperties, SWT.SINGLE);
		cameraShiftX.setLayoutData(grForProperties);
		
		Label lcamY = new Label(compositeForProperties, SWT.NONE);
		lcamY.setText("Camera Shift Y:");
		cameraShiftY = new Text(compositeForProperties, SWT.SINGLE);
		cameraShiftY.setLayoutData(grForProperties);
		
		Label lcamdis = new Label(compositeForProperties, SWT.NONE);
		lcamdis.setText("Camera Distance:");
		cameraDistance = new Text(compositeForProperties, SWT.SINGLE);
		cameraDistance.setLayoutData(grForProperties);
		
		Label lsprs = new Label(compositeForProperties, SWT.NONE);
		lsprs.setText("Singleplayer Rebel Story:");
		singlePlayerRebelStory = new Text(compositeForProperties, SWT.SINGLE);
		singlePlayerRebelStory.setLayoutData(grForProperties);
		
		Label lspes = new Label(compositeForProperties, SWT.NONE);
		lspes.setText("Singleplayer Empire Story:");
		singlePlayerEmpireStory = new Text(compositeForProperties, SWT.SINGLE);
		singlePlayerEmpireStory.setLayoutData(grForProperties);
		
		Label lspus = new Label(compositeForProperties, SWT.NONE);
		lspus.setText("Singleplayer Underworld Story:");
		singlePlayerUnderworldStory = new Text(compositeForProperties, SWT.SINGLE);
		singlePlayerUnderworldStory.setLayoutData(grForProperties);
		
		Label lspap = new Label(compositeForProperties, SWT.NONE);
		lspap.setText("Singleplayer Active Player:");
		singlePlayerActivePlayer = new Text(compositeForProperties, SWT.SINGLE);
		singlePlayerActivePlayer.setLayoutData(grForProperties);
		
		Label lismul = new Label(compositeForProperties, SWT.NONE);
		lismul.setText("Is Multiplayer:");
		isMultiplayer = new Combo(compositeForProperties, SWT.DROP_DOWN);
		isMultiplayer.add("false");
		isMultiplayer.add("true");
		
		Label lscs = new Label(compositeForProperties, SWT.NONE);
		lscs.setText("Supports Custom Settings:");
		supportsCustomSettings = new Combo(compositeForProperties, SWT.DROP_DOWN);
		supportsCustomSettings.add("false");
		supportsCustomSettings.add("true");
		
		
		Label lsct = new Label(compositeForProperties, SWT.NONE);
		lsct.setText("Show Completed Tab:");
		showCompletedTab = new Combo(compositeForProperties, SWT.DROP_DOWN);
		showCompletedTab.add("false");
		showCompletedTab.add("true");
		

		factionPropertiesComposite  = new Composite(compositeForProperties, SWT.NONE);
		factionPropertiesComposite.setBackground(new Color(display, 230, 200, 105));
		factionPropertiesComposite.setLayout(new GridLayout(7, false));
		GridData grfour = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		factionPropertiesComposite.setLayoutData(grfour);
		 aicontrol = new Text[gameEntities.getFactions().size()];
		 startingcred = new Text[gameEntities.getFactions().size()];
		 startingtech = new Text[gameEntities.getFactions().size()];
		 homelocation = new Text[gameEntities.getFactions().size()];
		 
		 maxtech = new Text[gameEntities.getFactions().size()];
		 markupfiles = new Text[gameEntities.getFactions().size()];
		 lfactions = new Label[gameEntities.getFactions().size()];
		 GridData grForTextProperties = new GridData();
		 grForTextProperties.widthHint = 130;
		 grForTextProperties.horizontalIndent = 0;
		 Label lfaction = new Label(factionPropertiesComposite, SWT.NONE);
			lfaction.setText("Faction");
			
			Label laiplayercontrol = new Label(factionPropertiesComposite, SWT.NONE);
			laiplayercontrol.setText("AI Player Control");
			
			Label lstartingcredits = new Label(factionPropertiesComposite, SWT.NONE);
			lstartingcredits.setText("Starting Credits");

			Label lstartingtech = new Label(factionPropertiesComposite, SWT.NONE);
			lstartingtech.setText("Starting Tech Lvl");
			
			Label lmaxtech = new Label(factionPropertiesComposite, SWT.NONE);
			lmaxtech.setText("Max Tech Lvl");
			
			Label lhomeloc = new Label(factionPropertiesComposite, SWT.NONE);
			lhomeloc.setText("Home Location");
			
			Label lmarkupfiles = new Label(factionPropertiesComposite, SWT.NONE);
			lmarkupfiles.setText("Matkup Files");
		 for(int i = 0; i < gameEntities.getFactions().size(); i++) {
			 lfactions[i] = new Label(factionPropertiesComposite, SWT.NONE);
			// laicontrol[i].setLayoutData(new GridData(SWT.CENTER, SWT.CENTER));
			 aicontrol[i] = new Text(factionPropertiesComposite, SWT.NONE);
			 aicontrol[i].setLayoutData(grForTextProperties);
			 startingcred[i] = new Text(factionPropertiesComposite, SWT.NONE);
			 startingtech[i] = new Text(factionPropertiesComposite, SWT.NONE);
			 maxtech[i] = new Text(factionPropertiesComposite, SWT.NONE);
			 homelocation[i] = new Text(factionPropertiesComposite, SWT.NONE);
			 homelocation[i].setLayoutData(grForTextProperties);
			 markupfiles[i] = new Text(factionPropertiesComposite, SWT.NONE);
			 markupfiles[i].setLayoutData(grForTextProperties);
			// aicontrol[i].setLayoutData(new GridData(SWT.CENTER, SWT.CENTER));
			 lfactions[i].setText(gameEntities.getFactions().get(i).getName());
		 }
	
		 
		 Label lhumanVictory = new Label(compositeForProperties, SWT.NONE);
		 lhumanVictory.setText("Human Victory Conditions:");
		 
		 scHumanVictoryConditions = new ScrolledComposite(compositeForProperties, SWT.V_SCROLL | SWT.H_SCROLL);
			scHumanVictoryConditions.setLayout(new GridLayout(1, false));
		    GridData gridd5 = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 2, 1);
		    gridd5.heightHint = 70;
		    gridd5.widthHint = 190;
		    scHumanVictoryConditions.setLayoutData(gridd5);
		    
		    Label laiVictory = new Label(compositeForProperties, SWT.NONE);
		    laiVictory.setText("AI Victory Conditions:");
		    scAiVictoryConditions = new ScrolledComposite(compositeForProperties, SWT.V_SCROLL | SWT.H_SCROLL);
		    scAiVictoryConditions.setLayout(new GridLayout(1, false));
		    scAiVictoryConditions.setLayoutData(gridd5);
		    
		    humanVictoryConditions = new List(scHumanVictoryConditions, SWT.SINGLE);
		    humanVictoryConditions.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		    aiVictoryConditions = new List(scAiVictoryConditions, SWT.SINGLE);
		    aiVictoryConditions.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		    scAiVictoryConditions.setExpandHorizontal(true);
		    scAiVictoryConditions.setExpandVertical(true);
		    scHumanVictoryConditions.setExpandHorizontal(true);
		    scHumanVictoryConditions.setExpandVertical(true);
		    scHumanVictoryConditions.setContent(humanVictoryConditions);
		    scAiVictoryConditions.setContent(aiVictoryConditions);
		
		
		
		
		scForProperties.setMinSize(compositeForProperties.computeSize(SWT.DEFAULT, SWT.DEFAULT)); 
		
		
		for(int i = 0; i < gameEntities.getCampaigns().size(); i++)
			if (gameEntities.getCampaigns().get(i).getName()!= null) listOfCampaigns.add(gameEntities.getCampaigns().get(i).getName()
					+ " {" + gameEntities.getCampaigns().get(i).getXmlName() + "}");
		sccampaigns.setMinSize(listOfCampaigns.computeSize(SWT.DEFAULT, SWT.DEFAULT)); 
		
		for(int i = 0; i < gameEntities.getPlanets().size(); i++)
			if (gameEntities.getPlanets().get(i).getName()!= null) allPlanets.add(gameEntities.getPlanets().get(i).getName()); else allPlanets.add("XML Name: {" + gameEntities.getPlanets().get(i).getXmlName() + "}");
		scAllPlanets.setMinSize(allPlanets.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		
		listOfCampaigns.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event)  {
		    	  
			        int[] selectedItems = listOfCampaigns.getSelectionIndices();
			        try {
			        String xmlNameText = listOfCampaigns.getItem(selectedItems[0]).substring(listOfCampaigns
			        		.getItem(selectedItems[0]).indexOf('{') + 1, listOfCampaigns.getItem(selectedItems[0]).indexOf('}'));
			       campaign = null;
			       
			        for(int i = 0; i < gameEntities.getCampaigns().size(); i++) {
			        	if (xmlNameText.equals(gameEntities.getCampaigns().get(i).getXmlName())) {
			        		campaign = gameEntities.getCampaigns().get(i);
			        	}
			        }
			        
			        		
			        if (campaign.isEdited()) {
			        	Boolean isEdit = campaign.isEdited();
	        			isEdited.select(isEdited.indexOf(isEdit.toString()));
	        		} else isEdited.deselectAll();
			        		if (campaign.getFileName() != null) {
			        			filename.setText(campaign.getFileName());
			        		} else filename.setText("");
			        		if (campaign.getXmlName() != null) {
			        			xmlName.setText(campaign.getXmlName());
			        		} else xmlName.setText("");
			        		if (campaign.getTextId() != null) {
			        			textId.setText(campaign.getTextId());
			        		} else textId.setText("");
			        		if (campaign.getName() != null) {
			        			name.setText(campaign.getName());
			        		} else name.setText("");
			        		if (campaign.getCampaignSet() != null) {
			        			campaignSet.setText(campaign.getCampaignSet());
			        		} else campaignSet.setText("");
			        		if (campaign.getSortOrder() != null) {
			        			sortOrder.setText(campaign.getSortOrder().toString());
			        		} else sortOrder.setText("");
			        		if (campaign.getDescriptionId() != null) {
			        			descriptionId.setText(campaign.getDescriptionId());
			        		} else descriptionId.setText("");
			        		if (campaign.getDescription() != null) {
			        			description.setText(campaign.getDescription());
			        		} else description.setText("");
			        		if (campaign.getCameraShiftX() != null) {
			        			cameraShiftX.setText(campaign.getCameraShiftX().toString());
			        		} else cameraShiftX.setText("");
			        		if (campaign.getCameraShiftY() != null) {
			        			cameraShiftY.setText(campaign.getCameraShiftY().toString());
			        		} else cameraShiftY.setText("");
			        		if (campaign.getCameraDistance() != null) {
			        			cameraDistance.setText(campaign.getCameraDistance().toString());
			        		} else cameraDistance.setText("");
			        		if (campaign.getSinglePlayerRebelStory() != null) {
			        			singlePlayerRebelStory.setText(campaign.getSinglePlayerRebelStory());
			        		} else singlePlayerRebelStory.setText("");
			        		if (campaign.getSinglePlayerEmpireStory() != null) {
			        			singlePlayerEmpireStory.setText(campaign.getSinglePlayerEmpireStory());
			        		} else singlePlayerEmpireStory.setText("");
			        		if (campaign.getSinglePlayerUnderworldStory() != null) {
			        			singlePlayerUnderworldStory.setText(campaign.getSinglePlayerUnderworldStory());
			        		} else singlePlayerUnderworldStory.setText("");
			        		if (campaign.getSinglePlayerActivePlayer() != null) {
			        			singlePlayerActivePlayer.setText(campaign.getSinglePlayerActivePlayer().getName());
			        		} else singlePlayerActivePlayer.setText("");
			        		if (campaign.getIsMultiplayer() != null) {
			        			isMultiplayer.select(isMultiplayer.indexOf(campaign.getIsMultiplayer().toString()));
			        		} else isMultiplayer.deselectAll();
			        		if (campaign.getSupportsCustomSettings() != null) {
			        			supportsCustomSettings.select(supportsCustomSettings.indexOf(campaign.getSupportsCustomSettings().toString()));
			        		} else supportsCustomSettings.deselectAll();
			        		if (campaign.getShowCompletedTab() != null) {
			        			showCompletedTab.select(showCompletedTab.indexOf(campaign.getShowCompletedTab().toString()));
			        		} else showCompletedTab.deselectAll();
			        		
			        		camPlanetsList.removeAll();
						    for (int k = 0; k < campaign.getLocations().size(); k++){
						    	if (campaign.getLocations().get(k).getName()!= null) camPlanetsList.add(campaign.getLocations().get(k).getName()); 
						    	else  camPlanetsList.add("XML Name: {" + campaign.getLocations().get(k).getXmlName() + "}");
						    }
						   
						    scrCamPlanets.setMinSize(camPlanetsList.computeSize(SWT.DEFAULT, SWT.DEFAULT));   
			        		
						   humanVictoryConditions.removeAll();
						    for (int k = 0; k < campaign.getHumanVictoryConditions().size(); k++){
						    	if (campaign.getHumanVictoryConditions().get(k)!= null) humanVictoryConditions.add(campaign.getHumanVictoryConditions().get(k));
						    }
						   
						    
						    scHumanVictoryConditions.setMinSize(humanVictoryConditions.computeSize(SWT.DEFAULT, SWT.DEFAULT));   
						    
						    aiVictoryConditions.removeAll();
						    for (int k = 0; k < campaign.getAiVictoryConditions().size(); k++){
						    	if (campaign.getAiVictoryConditions().get(k)!= null) aiVictoryConditions.add(campaign.getAiVictoryConditions().get(k));
						    }
						   
						   
						    scAiVictoryConditions.setMinSize(aiVictoryConditions.computeSize(SWT.DEFAULT, SWT.DEFAULT));   
						    
						    camTradeRoutesList.removeAll();
						    for (int k = 0; k < campaign.getTradeRoutes().size(); k++){
						    	if (campaign.getTradeRoutes().get(k)!= null) {
						    		if (campaign.getTradeRoutes().get(k).getName()!= null)
						    			camTradeRoutesList.add(campaign.getTradeRoutes().get(k).getName());
						    		else System.out.println("SHIT");
						    	}
						    }

						    scCamTradeRoutes.setMinSize(camTradeRoutesList.computeSize(SWT.DEFAULT, SWT.DEFAULT)); 
						    
						    
						   
					    	
					    		for(int i = 0; i < gameEntities.getFactions().size(); i++) {
					    			if (campaign.getAiPlayerControl().containsKey(gameEntities.getFactions().get(i))) 
					    				aicontrol[i].setText(campaign.getAiPlayerControl().get(gameEntities.getFactions().get(i)));
					    			else aicontrol[i].setText("");
					    			if (campaign.getStartingCredits().containsKey(gameEntities.getFactions().get(i))){
					    				startingcred[i].setText(campaign.getStartingCredits().get(gameEntities.getFactions().get(i)).toString());
					    			} else startingcred[i].setText("");
					    			if (campaign.getStartingTech().containsKey(gameEntities.getFactions().get(i))){
					    				startingtech[i].setText(campaign.getStartingTech().get(gameEntities.getFactions().get(i)).toString());
					    			} else startingtech[i].setText("");
					    			if (campaign.getMaxTech().containsKey(gameEntities.getFactions().get(i))){
					    				maxtech[i].setText(campaign.getMaxTech().get(gameEntities.getFactions().get(i)).toString());
					    			} else maxtech[i].setText("");
					    			if (campaign.getHomeLocations().containsKey(gameEntities.getFactions().get(i))){
					    				homelocation[i].setText(campaign.getHomeLocations().get(gameEntities.getFactions().get(i)).getName());
					    			} else homelocation[i].setText("");
					    			if (campaign.getMarkupFiles().containsKey(gameEntities.getFactions().get(i))){
					    				markupfiles[i].setText(campaign.getMarkupFiles().get(gameEntities.getFactions().get(i)));
					    			} else markupfiles[i].setText("");
						    	} 
						    
			        }catch(ArrayIndexOutOfBoundsException e) {
			        	System.out.println("����� �� ������� ������ ��������");
			        }
			        	
			        
			      
			      }
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
		
		allPlanets.addSelectionListener(new SelectionListener() {	//��� ������� �� ������� ������ ������
		      public void widgetSelected(SelectionEvent event)  {
		    	  int[] selectedItems = allPlanets.getSelectionIndices();
		    	  try {
		    	  String planetstring = allPlanets.getItem(selectedItems[0]);
		    	  MPlanet planet = null;
		    	  for(int i = 0; i < gameEntities.getPlanets().size(); i++) {
		    		 if (gameEntities.getPlanets().get(i).getName()!= null) if (gameEntities.getPlanets().get(i).getName().equals(planetstring))  planet = gameEntities.getPlanets().get(i);
		    	  }
		    	  specialCaseProduction.removeAll();
				    
				    	if (campaign.getSpecialCaseProduction().containsKey(planet)) {
				    		java.util.List<FactionUnit> fu = campaign.getSpecialCaseProduction().get(planet);
				    		for(int i = 0; i < fu.size(); i++) specialCaseProduction.add(fu.get(i).getOwner().getName() + " - " + fu.get(i).getUnit().getName());
				    	
				    }

				    scSpecialCaseProduction.setMinSize(specialCaseProduction.computeSize(SWT.DEFAULT, SWT.DEFAULT));   
				   
			    	  startingForces.removeAll();
					   
					    	if (campaign.getStartingForces().containsKey(planet)) {
					    		java.util.List<FactionUnit> fu = campaign.getStartingForces().get(planet);
					    		for(int i = 0; i < fu.size(); i++) startingForces.add(fu.get(i).getOwner().getName() + " - " + fu.get(i).getUnit().getName());
					   	
					   }
					    scStartingForces.setMinSize(startingForces.computeSize(SWT.DEFAULT, SWT.DEFAULT));  
		    	  } catch(ArrayIndexOutOfBoundsException e){
		    		  System.out.println("����� �� ������� ������ ������");
		    	  }
		   }

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		      });
		
		camPlanetsList.addSelectionListener(new SelectionListener() {	//��� ������� �� ������� ������ ���� ������
		      public void widgetSelected(SelectionEvent event)   {
		    	  int[] selectedItems = camPlanetsList.getSelectionIndices();
		    	 try {
		    	  String planetstring = camPlanetsList.getItem(selectedItems[0]);
		    	  MPlanet planet = null;
		    	  for(int i = 0; i < gameEntities.getPlanets().size(); i++) {
		    		 if (gameEntities.getPlanets().get(i).getName()!= null) if (gameEntities.getPlanets().get(i).getName().equals(planetstring))  planet = gameEntities.getPlanets().get(i);
		    	  }
		    	  specialCaseProduction.removeAll();
				  
				    	if (campaign.getSpecialCaseProduction().containsKey(planet)) {
				    		java.util.List<FactionUnit> fu = campaign.getSpecialCaseProduction().get(planet);
				    		for(int i = 0; i < fu.size(); i++) specialCaseProduction.add(fu.get(i).getOwner().getName() + " - " + fu.get(i).getUnit().getName());
				    	}
				    

				    scSpecialCaseProduction.setMinSize(specialCaseProduction.computeSize(SWT.DEFAULT, SWT.DEFAULT));   
				   
			    	  startingForces.removeAll();
					  
					    	if (campaign.getStartingForces().containsKey(planet)) {
					    		java.util.List<FactionUnit> fu = campaign.getStartingForces().get(planet);
					    		for(int i = 0; i < fu.size(); i++) startingForces.add(fu.get(i).getOwner().getName() + " - " + fu.get(i).getUnit().getName());
					   	
					    }
					    scStartingForces.setMinSize(startingForces.computeSize(SWT.DEFAULT, SWT.DEFAULT));  
		    	 }catch(ArrayIndexOutOfBoundsException e) {
		    		 System.out.println("����� �� ������� ������ ������ ��������");
		    	 }
		      }
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		      });
		
		
	
	}

}

