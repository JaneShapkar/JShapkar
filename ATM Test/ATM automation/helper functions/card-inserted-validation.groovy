def validCardInserted() {
    //Validate that a card is inserted in the card reader by calling a pseudo function cardInserted
    if (cardInserted()){
        def userCard = new getCardInfo()
    }

    //Assuming the ATM only works with debit and credit cards, validate the type of the card
    def typeOfCard = userCard.getProperty(cardType)
    if (typeOfCard!='Credit' && typeOfCard!='Debit'){
        //End test case by calling a pseudo function.
        //Pop-out credit card from card reader by calling a pseudo function popCard
        endTestCase('Test case terminated. Credit card type is not supported')
        popCard()
    }

    //Validate that the credit card is not expired
    //Pop-out credit card from card reader if expired, by calling popCard
    def isExpired = userCard.getProperty(expiryDate)
    if (isExpired < Date.today()){
        //End test case by calling a pseudo function.
        //Pop-out credit card from card reader by calling a pseudo function popCard
        endTestCase('Test case terminated. Credit card expired')
        popCard()
    }
}