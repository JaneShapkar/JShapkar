//Test case for withdrawing an amount inserted by the user

//import goes here
import validCardInserted from '../helper functions/card-inserted-validation.groovy'
import printReceipt from '../helper functions/print-receipt.groovy'

//Test case starts here (what would be 'describe' in cypress)

def userInputPin
def userInputAmount

//Validate that ATM is on by calling a pseudo function isAtmOn
if (!isAtmOn()){
    //End test case by calling a pseudo function
    endTestCase('Test case terminated. ATM is off')
}

//Call function to make sure card is inserted and valid
validCardInserted()

//Validate that the PIN screen is open
//Verify that the label "Enter PIN number" is present on the display
verifyElementPresent('Enter PIN number')

//Enter correct PIN
def userCard = new getCardInfo()

//User inputs a PIN number
userInputPin = getUserInput(string.Pin)
def userPin = userCard.getProperty(cardPinNumber)

//User clicks Accept
getUserInput(signal.Accept)
if (userInputPin == userPin){
    //Pseudo function for opening another screen, navigateToUrl in Katalon, similar to url().should
    navigateTo('Select amount screen')

    //Verify that the screen is displayed. Test fails if element is not present
    verifyElementPresent('Different Amount') 
}


//!Select amount screen!
//User selects the Different Amount option from the screen
getUserInput(signal.AmountOption)
navigateTo('Different amount screen')

//Verify the two options available
verifyElementPresent('Correct')
verifyElementPresent('Incorrect')

//User inputs amount as string
userInputAmount = getUserInput(string.Amount)

//User clicks the Correct option
getUserInput(signal.Correct)
navigateTo('Print receipt screen')
verifyElementPresent('Yes')
verifyElementPresent('No')

//Check if the banknotes inside the ATM can add up to the entered amount
//Pseudo function enoughBanknotes() to make sure there are enough banknotes of a certain required type
if(userInputAmount % 100 == 0 && enoughBanknotes()){
    //Call a pseduo function to establish a connection to the bank
    connectToBank()
    //Get user's account info, once connection is established
    def userAccountInfo = new getAccountData() 
    if(userInputAmount <= userAccountInfo.getProperty(amountAvailable)){
        //Check if the user surpassed the daily limit
        if(userInputAmount <= userAccountInfo.getProperty(dailyLimit)){
            //Complete the transaction and subtract the userInputAmount from the availableAmount
            executeTransaction(userInputAmount, userCard.getProperty(cardSerialNumber), 
                                userCard.getProperty(cvvNumber))
            if (getUserInput(signal.Yes)){
                printReceipt(userInputAmount, userCard.getProperty(clientName),
                             userCard.getProperty(issuerBank), userCard.getProperty(cardType), 
                             userCard.getProperty(cardSerialNumber))
            }
            navigateTo('Transcation in progress screen')
            endConnectionToBank() //Closes the connection to the bank
            popCard() //Pop the card out of the card reader
            verifyElementPresent('Please take your card')
            checkIdleTime() //Check if the card was taken out of the card reader in the given time limit
            popCash() //Pop the cash out of the cash dispenser
            verifyElementPresent('Please take your cash')
            checkIdleTime() //Check if the money were taken from the cash dispenser in the given time limit
        }

    }
}
