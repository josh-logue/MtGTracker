# MtG Deck Tracker

This project is a tool assisting in playing the Trading Card Game (TCG) Magic: the Gathering (MtG).

It has two primary features:
  
  * Deck Search via Archidekt
  * Card search via Scryfall*
  
\*Scryfall REST requests have been sporadic and inconsistent from day-to-day; they worked fine one day and failed the next, and vice-versa

## Deck Search

This feature is accessed by entering an Archidekt account username in the field on the main page and pressing the appropriate button.

Archidekt is then queried and, assuming success, a list of the that user's **public** decks is shown.

Long pressing an item on this list will provide a new list of each card within the chosen deck.

Cards are listed in the form of \[Quantity in deck, Card Name, Card Type, Mana Cost]

Card types are the 5 permanent and 2 non-peranent types:

  * Artifact 
  * Creature
  * Enchantment
  * Planeswalker
  * Land
  * Sorcery
  * Instant

These same types can be filtered using the dropdown spinner at the top of the deck details view.

Mana Costs consist of the 5 colors of magic and colorless and are represented as:
  * {W} - White
  * {U} - Blue
  * {B} - Black
  * {R} - Red
  * {G} - Green
  * {C} - Colorless

Generic mana is represented by a numeral within brackets. Lands are listed with mana cost 0. This isn't technically wrong, leave me alone.

Selecting an individual card by long pressing it on this list will bring up a fragment display the card's details.

Pressing the \[x] in the top left corner of each of these views will take the user to the previous view.

## Card Search

This feature is accessed by entering a query within the field on the main page and pressing the appropriate button.

(Accepts regex, see Scryfall website for details).

Scryfall is queried and, assuming success, a fragment displaying the queried card's details is displayed.

Pressing the \[x] in the upper left corner will bring the user to the main activity.

## Notes

Card search will return the first result (i.e. the alphabetically first match to the input string).

There are thousands of cards and virtually any query with return something. For testing purposes try "leovold", "queen", or "ezuri" without quotes.

Deck searching can take an Archidekt username, for testing, try "pr0log" without quotes.

When long-pressing a deck or card name from a displayed list, there may be some delay while the query is requested.

## Todo
- [x] Comments
- [ ] Input validation
- [ ] Loading screen betwen long transitions
