package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuessFunctionalStyle(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { it.first == it.second }

    val commonLetters = "ABCDEF".sumBy { ch ->

        Math.min(secret.count { ch == it }, guess.count { ch == it })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}

fun main(args: Array<String>) {
    val result = Evaluation(rightPosition = 1, wrongPosition = 1)
    evaluateGuessFunctionalStyle("BCDF", "ACEB") eq result
    evaluateGuessFunctionalStyle("AAAF", "ABCA") eq result
    evaluateGuessFunctionalStyle("ABCA", "AAAF") eq result
}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}


fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = mutableListOf<Char>()
    var wrongPosition = mutableListOf<Char>()

    for ((indexOfLetterInGuess, letter) in guess.withIndex()) {
        val indexOfLetterInSecret = secret.indexOf(letter)
        if(indexOfLetterInSecret >= 0) {
            if(letter==secret.elementAt(indexOfLetterInGuess)) {
                rightPosition.add(letter)

                if(countOccurence(secret, letter) != countOccurence(guess, letter)) {
                    wrongPosition.remove(letter)
                }
            } else if(countOccurence(secret, letter) != countOccurence(wrongPosition, letter)
                    && !isLetterAlreadyGuessed(letter, secret, rightPosition)
                    && countOccurence(guess, letter)!=4) {
                wrongPosition.add(letter)
            }
        }
    }

    return Evaluation(rightPosition.size, wrongPosition.size)
}

fun isLetterAlreadyGuessed(letter:Char, secret:String, rightPositionList: Collection<Char>): Boolean {
    return countOccurence(secret, letter)==1 && rightPositionList.contains(letter)
}

fun countOccurence(s: String, ch: Char): Int {
    return s.filter { it == ch }.count()
}

fun countOccurence(c: Collection<Char>, ch: Char): Int {
    return c.filter { it == ch }.count()
}