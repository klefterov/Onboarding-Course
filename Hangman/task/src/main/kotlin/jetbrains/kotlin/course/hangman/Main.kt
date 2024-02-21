package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"
fun isComplete(secret: String, currentGuess : String) : Boolean = (secret.filter{it != ' '} == currentGuess.filter { it != ' '})
// You will use this function later
fun generateNewUserWord(secret: String, guess : Char, currentUserWord : String): String {
    val builder : StringBuilder = StringBuilder()
    val userWord = currentUserWord.filter {it != ' '}
    for(i in secret.indices){
        if(secret[i] == guess){
            builder.append(secret[i])
        }
        else if(userWord[i] != '_'){
            builder.append(userWord[i])
        }
        else{
            builder.append('_')
        }
    }
    return builder.toList().joinToString(" ")
}

fun getHiddenSecret(wordLength: Int) : String {
    val str = StringBuilder()
    repeat(wordLength){
        str.append('_')
        str.append(' ')
    }
    return str.deleteAt(str.length - 1).toString()
}

fun isCorrectInput(userInput : String) : Boolean {
    if(userInput.length != 1){
        println("The length of your guess should be 1! Try again!")
        return false
    }
    if(!userInput.first().isLetter()){
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun safeUserInput() : Char{
    var input=""
    do {
        println("Please input your guess.")
        input = safeReadLine()
    }while (!isCorrectInput(input))
    return input.uppercase().first()
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String) : String{
    val newUserWord = generateNewUserWord(secret, guess, currentUserWord)
    if (newUserWord == currentUserWord){
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
    }
    else{
        println("Great! This letter is in the word! The current word is $newUserWord")
    }
    return newUserWord
}

fun playGame(secret: String, maxAttemptsCount: Int){
    var entries = 0
    var currentGuess = getHiddenSecret(secret.length)
    println("I guessed a word: $currentGuess")
    while (entries <= maxAttemptsCount){
        var input = safeUserInput()
        currentGuess = getRoundResults(secret, input, currentGuess)
        if(isWon(isComplete(secret, currentGuess), entries, maxAttemptsCount)){
            println("Congratulations! You guessed it!")
            return
        }
        if(isLost(isComplete(secret, currentGuess), entries, maxAttemptsCount)){
            println("Sorry, you lost! My word is $secret")
            return
        }
        entries++
    }
    println("Sorry, you lost! My word is $secret")
}

fun generateSecret() = words.random()
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts > maxAttemptsCount

fun main() {
    // Uncomment this code on the last step of the game

    println(getGameRules(wordLength, maxAttemptsCount))
    playGame(generateSecret(), maxAttemptsCount)
}
