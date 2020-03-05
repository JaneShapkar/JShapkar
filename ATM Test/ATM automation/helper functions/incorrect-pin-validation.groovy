def incorrectPin(userSignalInput, userStringInput, n) {
     if (userSignalInput == 'Accept'){

        if(userStringInput != userPin){
            if(n=0){
                //Pseudo function to display a message on screen
                showMessage('Incorrect PIN. {$n} attempts remaining')
                takeCardIn() //Pseudo function to take the card inside the ATM
                navigateTo('Start screen')//Pseudo function for opening a screen on the display

            } else{    
                showMessage('Incorrect PIN. {$n} attempts remaining') 
                userStringInput.clear() //Reset the PIN so it's displayed as empty on the PIN screen
                navigateTo('Insert PIN screen', userStringInput) 
            }
        } else{
            //End test case by calling a pseudo function.
            //Pop-out credit card from card reader by calling a pseudo function popCard
            endTestCase('Test case terminated. Valid PIN entered')
            popCard()
        }
    } else if(userSignalInput == 'Cancel'){
        endTestCase('Test case terminated. Valid PIN entered')
        popCard()
        navigateTo('Insert PIN screen', userStringInput)
    }
}