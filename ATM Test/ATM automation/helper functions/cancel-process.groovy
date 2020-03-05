def cancelProcess(){
    popCar() //Pop card out of card reader
    navigateTo('Start screen') //Display the start screen
    verifyElementPresent('Insert your credit card') //Verify that the start screen opened
}