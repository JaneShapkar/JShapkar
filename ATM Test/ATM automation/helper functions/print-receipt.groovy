def printReceipt( withdrawalAmount, clientName, issuerBank, cardType, cardSerialNumber){

    def amount = withdrawalAmount
    def client = clientName
    def bank = issuerBank
    def type = cardType
    def cardNumber = cardSerialNumber
    def datetime = new Date().toLocalDateTime()

    //Call pseudo function mask() to mask the cardNumber and display only the last 4 digits
    cardNumber.mask()

    //Check if the ATM has enought paper with a pseudo function
    if(isPaper()){
        //Print the values on the paper with pseudo function
        print (issuerBank, cardType, clientName, cardSerialNumber, withdrawalAmount, datetime)
    }
    else {
        //Display a message on screen with pseudo function
        displayMessage('There is not enough paper')
    }
}