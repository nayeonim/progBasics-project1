
/**
 * This class represents a fact card containing information about me.
 * Each fact card has a Question side and an Answer side that the user can flip between, to hide or reveal facts.
 * Each fact card has a position on the canvas, onto which it is placed, specified by posX and posY values.
 * 
 * @author stefanieim
 */
public class FactCard {
	private int key;
	private boolean flipped; //true means Answer side is shown

	private String sideQStr;  //Question side
	private String sideAStr;  //Answer side
	
	private int dimension;

	private int posX;
	private int posY;

	
	/**
	 * Constructs a FactCard with the given parameters, which has: 
	 * - an integer key that acts as the ordered label of a card
	 * - a boolean value that indicates whether the card has been flipped to show the Answer side
	 * - String values for the question (a keyword for the fact) and answer (the fact itself)
	 * - and a position x and y, indicating where on a canvas the card is located.
	 * 
	 * When the FactCard is initially constructed, 
	 * - it is NOT flipped to show the keyword (i.e. is revealing the Question, not the Answer), 
	 * - its dimension is set to the given factCardDimension value,
	 * - its integer key is initialized to the given value, and
	 *      - its question/answer String values are initialized automatically, depending on the given integer key
	 *      - its positions X and Y are calculated and set automatically using the given dimension and spacing information, 
	 *        depending on the integer key of the card.
	 * 
	 * @param key the key of the FactCard to be constructed (used for identification of card and calculation of other fields)
	 * @param canvasLeftBuffer the space from the left edge of the canvas to the point where the leftmost fact card will be placed
	 * @param canvasTopBuffer the space from the top edge of the canvas to the point where the topmost fact card will be placed
	 * @param factCardDimension the length of a side of the square fact card
	 * @param factCardSpacing the space between each fact card when they are placed on the canvas as a grid
	 */
	public FactCard(int key, int canvasLeftBuffer, int canvasTopBuffer,
			int factCardDimension, int factCardSpacing) {
		this.key = key;
		this.flipped = false;

		this.sideQStr = getQuestionString(key);
		this.sideAStr = getAnswerString(key);;
		
		this.dimension = factCardDimension;

		int col = key % 4;
		int row = (int)  Math.ceil(key/4);
		this.posX = (col * (factCardDimension + factCardSpacing)) + canvasLeftBuffer;
		this.posY = (row * (factCardDimension + factCardSpacing)) + canvasTopBuffer;
	}
	
	/**
	 * Returns the integer key of the FactCard (0-based indexing).
	 * 
	 * @return the integer key of the card.
	 */
	public int getKey() {
		return key;
	}

	/**
	 * Returns whether the Answer side is facing up or not.
	 * If the Answer side is facing up, it means that the card has been clicked on to reveal a fact about me.
	 * 
	 * @return true if the Answer side is facing up and the fact is revealed.
	 */
	public boolean isFlipped() {
		return flipped;
	}

	/**
	 * Flips the card. Toggles between the front side (Question) and back side (Answer) facing up.
	 */
	public void flip() {
		flipped = !flipped;
	}
	
	/**
	 * Returns the question attached to the card (i.e. the keyword attached to the fact).
	 * 
	 * @return the string to be displayed on the Question side of the card
	 */
	public String getSideQString() {
		return sideQStr;
	}

	/**
	 * Returns the fact attached to the card (i.e. the actual fact about me).
	 * 
	 * @return the string to be displayed on the Answer side of the card
	 */
	public String getSideAString() {
		return sideAStr;
	}

	/**
	 * Returns the dimension of this fact card.
	 * @return the length of a side of this square fact card
	 */
	public int getDimension() {
		return this.dimension;
	}
	
	/**
	 * Returns the x value of the coordinates that the fact card will be drawn at, on a larger canvas.
	 * 
	 * @return the x coordinate of the fact card
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Returns the y value of the coordinates that the fact card will be drawn at, on a larger canvas.
	 * 
	 * @return the y coordinate of the fact card
	 */
	public int getPosY() {
		return posY;
	}
	
	/**
	 * Returns the string to be displayed on the Question side of a card, depending on the key of a card.
	 * 
	 * @param key the key of the card to get the Question string from
	 * @return the Question string of the card with the given key
	 * @throws IAE if a card does not exist at the given key
	 */
	private String getQuestionString(int key) {
		String qString;
		
		switch (key) {
		case 0:
			qString = "Hometown";
			break;
		case 1: 
			qString = "Birthday";
			break;
		case 2: 
			qString = "Places lived";
			break;
		case 3:
			qString = "Languages";
			break;
		case 4: 
			qString = "School";
			break;
		case 5: 
			qString = "Major";
			break;
		case 6:
			qString = "Minor";
			break;
		case 7: 
			qString = "Career";
			break;
		case 8: 
			qString = "Hobby";
			break;
		case 9:
			qString = "Favourite\n" + "Color" ;
			break;
		case 10: 
			qString = "Favourite\n" + "Artist";
			break;
		case 11: 
			qString = "LinkedIn";
			break;
		default:
			throw new IllegalArgumentException("Card does not exist at given key.");
		}
		
		return qString;
	}
	
	/**
	 * Returns the string to be displayed on the Answer side of a card, depending on the key of a card.
	 * 
	 * @param key the key of the card to get the Answer string from
	 * @return the Answer string of the card with the given key
	 * @throws IAE if a card does not exist at the given key
	 */
	private String getAnswerString(int key) {
		String qString;
		switch (key) {
		case 0:
			qString = "Seoul, S. Korea";
			break;
		case 1: 
			qString = "Oct 31, 1999\n" + "(Halloween!)";
			break;
		case 2: 
			qString = "Germany\n"
					+ "UK\n"
					+ "Korea\n"
					+ "US";
			break;
		case 3:
			qString = "Korean\n"
					+ "English\n"
					+ "Japanese";
			break;
		case 4: 
			qString = "Northeastern";
			break;
		case 5: 
			qString = "Business &\n" + "Design";
			break;
		case 6:
			qString = "Computer\n" + "Science";
			break;
		case 7: 
			qString = "Product & UX";
			break;
		case 8: 
			qString = "Surf!";
			break;
		case 9:
			qString = "Black" ;
			break;
		case 10: 
			qString = "MØ";
			break;
		case 11: 
			qString = "Click to open\n" + "in browser\n\n";
			break;
		default:
			throw new IllegalArgumentException("Card does not exist at given key.");
		}
		return qString;
	}

	@Override
	public boolean equals(Object o) {
		FactCard other;
		if (o instanceof FactCard) {
			other = (FactCard) o;
			return this.getKey() == other.getKey();
		} else { return false; }
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(this.key) * 1234;
	}
}

