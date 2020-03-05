import validCardInserted
import incorrectPin

def incorrectPinx3(){

    def userStringInput
    def userSignalInput

    //Call function to make sure card is inserted and valid
    validCardInserted()

    const userCard = new getCardInfo() //Get info from the card
    def userPin = userCard.getProperty(cardPinNumber)

    for(i=2; i>=0; i--){
        userStringInput = getUserInput(string) //User entered a PIN
        userSignalInput = getUserInput(signal) //User clicked Accept

        incorrectPin(userSignalInput, userStringInput, i)
    }

}