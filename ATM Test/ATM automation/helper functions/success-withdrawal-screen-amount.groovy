//Test case for withdrawing an amount selected on the screen

//import goes here
import validCardInserted from '../helper functions/card-inserted-validation.groovy'
import printReceipt from '../helper functions/print-receipt.groovy'

//Test case starts here (what would be 'describe' in cypress)
def withdrawAmount(amountOnScreen){
    def userInputPin
    def userSelectedAmount = amountOnScreen

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

        //This should return a list of all the available options on the screen
        //In this case the amounts: 500, 1000, 1500, 2000, 5000, and the options: Different Amount, Check Balance
        List<signal> screenButtons = new List(find(getUserInput(signal)))
        //Verify that the screen and each option is displayed. Test fails if an option is not present
        for each (button in screenButtons){
        verifyElementPresent({$button})
        } 
    }


    //!Select amount screen!
    //User selects the option set as an argument when calling the test case
    getUserInput(signal.{$userSelectedAmount})
    
    //The print receipt screen opens
    navigateTo('Print receipt screen')
    verifyElementPresent('Yes')
    verifyElementPresent('No')

    //Check if the banknotes inside the ATM can add up to the entered amount
    //Pseudo function enoughBanknotes() to make sure there are enough banknotes of a certain required type
    if(enoughBanknotes()){
        //Call a pseduo function to establish a connection to the bank
        connectToBank()
        //Get user's account info, once connection is established
        def userAccountInfo = new getAccountData() 
        if(userSelectedAmount <= userAccountInfo.getProperty(amountAvailable)){
            //Check if the user surpassed the daily limit
            if(userSelectedAmount <= userAccountInfo.getProperty(dailyLimit)){
                //Complete the transaction and subtract the userInputAmount from the availableAmount
                executeTransaction(userSelectedAmount, userCard.getProperty(cardSerialNumber), 
                                    userCard.getProperty(cvvNumber))
                if (getUserInput(signal.Yes)){
                    printReceipt(userSelectedAmount, userCard.getProperty(clientName),
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
}