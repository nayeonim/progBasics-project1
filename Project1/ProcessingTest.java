/*
 * TODO: Questions...
 * 
 * 1. Can I import java util libraries and use their arrays instead?
 * 2. Do I need get/setters that arent gonna be used
 * 3. add pdf for resume
 * 
 */
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;


/**
 * This class represents my Personal Introduction page, written using the Processing library.
 * The page is a canvas that contains visual elements (title, background image, etc.) that enhances the view,
 * as well as a list of fact cards that the user can interact with, by clicking or hovering their mouse on the screen.
 * In order to signify the interactivity of fact cards, an initial animation is played once after the program starts.
 * During the animation, each of the cards gets flipped over to show the other side, 
 * and then flipped back down in increasing key order.
 * 
 * @author stefanieim
 */
public class ProcessingTest extends PApplet {
	//dimensional constants--------------------------------------
	private final static int CANVAS_LEFT_BUFFER = 50;
	private final static int CANVAS_TOP_BUFFER = 132;
	private final static int FACTCARD_DIMENSION = 100;
	private final static int FACTCARD_SPACING = 30;
	//animation constants----------------------------------------
	//the point at which the animation starts (i.e. 3 seconds)
	private final static int ANIMATION_START_TIME = 3000; 
	//the time in between this card being flipped and the next card being flipped (i.e. 10th of a second)
	private final static int ANIMATION_FACTCARD_INTERVAL = 100; 
	//on screen elements-----------------------------------------
	private PImage bgImg;
	private String title;
	private String subtitle;
	private List<FactCard> facts;
	private PImage linkedInLogo;
	//animation elements-----------------------------------------
	private boolean animationPlayed;
	private int cardToShow;
	private int nextTimeInterval;


	//SETUP------------------------------------------------------------------

	/**
	 * This function will set up the initial canvas by running once when the program starts.
	 */
	public void settings() {
		size(960, 540); // Set the canvas width and height
	}
	
	/**
	 * This function will set up all variables by running once when the program starts.
	 */
	public void setup() {
		//set header content settings
		this.title = "Stefanie Na Yeon Im";
		this.subtitle = "Project 1: Personal Introduction";

		//load the background image
		this.bgImg = loadImage("bg.png");
		this.linkedInLogo = loadImage("linkedInLogo.png");

		//load the facts
		this.facts = new ArrayList<FactCard>();
		loadFacts(12);

		//prepare to play initial animation;
		this.animationPlayed = false;
		this.cardToShow = 0;
		this.nextTimeInterval = millis() + ANIMATION_FACTCARD_INTERVAL;
	}

	/**
	 * Creates n number of fact cards and loads them to the facts list.
	 * 
	 * @param n the number of fact cards to create and load
	 */
	private void loadFacts(int n) {
		for (int i = 0; i < n; i++) { //for the number of times specified by given n
			//create a new fact card with a unique integer key 
			FactCard fact = new FactCard(i, 
					//while feeding in the spacing/dimension constants
					CANVAS_LEFT_BUFFER, CANVAS_TOP_BUFFER, FACTCARD_DIMENSION, FACTCARD_SPACING);
			//add the newly created card to the end of the facts list
			facts.add(fact);
		}
	}

	
	

	//DRAW------------------------------------------------------------------

	/**
	 * This method is executed repeatedly to draw onto the canvas at every frame.
	 */
	public void draw() {

		//1. draw the background image
		background(0);
		image(this.bgImg, 0, 0);

		//2. draw the title & subtitle
		fill(255);
		textAlign(LEFT);
		textSize(36);
		text(title, CANVAS_LEFT_BUFFER, 72);
		textSize(14);
		text(subtitle, CANVAS_LEFT_BUFFER, 102);

		//3. Initial Animation
		checkForAnimationCompletion(); //check if animation has been played

		//if next time interval has been reached, the animation start time has been passed, and the animation has not already been completed,
		if(millis() >= nextTimeInterval && millis() >= ANIMATION_START_TIME && !this.animationPlayed) {
			if (0 <= this.cardToShow && this.cardToShow <= 1) {      //first two cards
				facts.get(this.cardToShow).flip();                   //flip from Q to A
				this.cardToShow++;
			} 
			else if (2 <= this.cardToShow && this.cardToShow <= this.facts.size() - 1) {  //third card to last card
				facts.get(this.cardToShow).flip();                                        //flip from Q to A
				facts.get(this.cardToShow - 2).flip();                                    //flip 2 cards ago BACK from A to Q
				this.cardToShow++;
			} 
			else if (this.facts.size() <= this.cardToShow && this.cardToShow <= this.facts.size() + 1) { //last two cards being flipped back 
				facts.get(this.cardToShow - 2).flip();                                                   //flip 2 cards ago BACK from A to Q
				this.cardToShow++; 
			}
			nextTimeInterval += ANIMATION_FACTCARD_INTERVAL; //increment time
		}

		//4. draw the FactCards 
		for (FactCard f: facts) {  //for all fact cards contained in the facts list
			drawFactCardBox(f);    //draw box first
			drawFactCardText(f);   //then text (due to layering)
		}
	}

	/**
	 * Checks if the initial animation has been played and 
	 * updates the animationPlayed boolean field to reflect this.
	 */
	private void checkForAnimationCompletion() {
		if ((this.facts.size() + 1) < this.cardToShow) { //all cards have already been shown (flipped up then flipped back again)
			this.animationPlayed = true; 
		}
	}

	/**
	 * Draws the box container for a given fact card.
	 * 
	 * @param f the fact card to draw the box container for
	 */
	private void drawFactCardBox(FactCard f) {
		
		//LinkedIn button
		if (f.getKey() == 11) { 	
			if (this.isOverFact(11)) {        //hover state
				fill(color(107, 191, 239));   //LinkedIn blue (25% lighter)
			} else {                          //normal state
				fill(color(0, 114, 177));     //LinkedIn blue
			}
			stroke(color(0, 114, 177));       //white
		} 
		//All other fact cards
		else { 
			if (!f.isFlipped()) {     
				fill(color(0));       //black fill
				stroke(color(255));   //white outline
			} else {
				fill(color(255));     //white fill
				stroke(color(0));     //black outline
			}
		}
		
		//draw square box (same for all cards)
		square(f.getPosX(), f.getPosY(), f.getDimension());
		
		//LinkedIn button FactCard logo image
		if (f.getKey() == 11 && this.isOverFact(11)) {  //hover state
			image(this.linkedInLogo, 
					facts.get(11).getPosX() + (f.getDimension()/10), 
					facts.get(11).getPosY() + ((f.getDimension()/10) * 6));
		}
	}


	/**
	 * Draws the text for a given fact card.
	 * 
	 * @param f the fact card to draw the text for
	 */
	private void drawFactCardText(FactCard f) {
		String displayString; //string to be displayed

		//LinkedIn button
		if (f.getKey() == 11) {
			fill(255);                              //white text regardless of hover||normal
			if (this.isOverFact(11)) {
				displayString = f.getSideAString(); //hover state displays Answer side string
			} else {
				displayString = f.getSideQString(); //normal state displays Question side string
			}
		//All other fact cards
		} else {
			if (!f.isFlipped()) {                   //Question side
				fill(255);                          //white text
				displayString = f.getSideQString(); //Question side string
			}
			else {                                  //Answer side
				fill(0);                            //black text
				displayString = f.getSideAString(); //Answer side string
			}
		}
		
		textAlign(CENTER, CENTER);
		textSize(12);
		text(displayString, 
				(f.getPosX() + (FACTCARD_DIMENSION / 2)),
				(f.getPosY() + (FACTCARD_DIMENSION / 2)));
	}
	
	/**
	 * Executes commands for when a mouse click occurs while the program is running.
	 * - For the last fact card (LinkedIn Button), direct the user to my linkedIn page by opening the link in their browser
	 * - For the rest of the fact cards, flip to show the other side of the card.
	 * 
	 * @param event the mouse click
	 */
	public void mouseClicked(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();

		for (FactCard f: facts) {
			//if mouseX and Y are in boundaries of each fact card
			if (f.getPosX() <= mouseX && mouseX <= (f.getPosX() + FACTCARD_DIMENSION)
					&& f.getPosY() <= mouseY && mouseY <= (f.getPosY() + FACTCARD_DIMENSION)) {
				//LinkedIn button FactCard
				if (f.getKey() == 11) {
					link("https://www.linkedin.com/in/stefanie-im-b71b0117b/"); //open link in browser
				} 
				//All other fact cards
				else {
					f.flip(); //flip to show the other side
				}
			}
		}
	}

	/**
	 * Determines if the mouse's position is currently within the area of the fact card with the given key.
	 * 
	 * @param key the key of the fact card to be checked
	 * @return true if the mouse is hovering over the fact card with the given key
	 */
	public boolean isOverFact(int key) {
		return facts.get(key).getPosX() <= mouseX && mouseX <= (facts.get(key).getPosX() + FACTCARD_DIMENSION)       //check X
				&& facts.get(key).getPosY() <= mouseY && mouseY <= (facts.get(key).getPosY() + FACTCARD_DIMENSION);  //check Y
	}

	
	

	//MAIN------------------------------------------------------------------

	// Driver code
	public static void main(String[] args) {
		PApplet.main(new String[] {"--present", "ProcessingTest"});
	}
}