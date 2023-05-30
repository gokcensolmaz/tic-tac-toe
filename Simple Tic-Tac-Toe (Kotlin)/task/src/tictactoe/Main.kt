package tictactoe

import kotlin.math.absoluteValue

const val LINE_COUNT = 3
var gameGrid = Array(3) { CharArray(3) { ' ' } }
fun main() {
    printBoard(gameGrid)
    var gameFinished = false
    var result = ""
    var turnX = true
    while (!gameFinished) {
        if(turnX){
            newMove(gameGrid,'X')
            turnX = !turnX
        }else{
            newMove(gameGrid, 'O')
            turnX = !turnX
        }
        printBoard(gameGrid)
        result = checkGame(gameGrid)
        if (result == "X wins" || result == "O wins") {
            gameFinished = true
            break
        }
    }
    println(result)


}

fun gameGrid(input: String): Array<CharArray> {
    val grid = Array(3) { CharArray(3) { ' ' } }
    var counter = 0
    for (row in 0 until 3) {
        for (col in 0 until 3) {
            if (counter < input.length) {
                grid[row][col] = input[counter]
            }
            counter++
        }
    }
    return grid
}

fun printBoard(grid: Array<CharArray>) {
    println("---------")
    for (row in grid) {
        println("| ${row.joinToString(" ")} |")
    }
    println("---------")
}

fun addSpace(char: Char): String {
    if (char == '_') {
        return "  "
    }
    return "$char "
}

fun checkGame(grid: Array<CharArray>): String {

    // check impossible
    var totalXCount = 0
    var totalOCount = 0
    var diagonalX = 0
    var diagonalO = 0
    var reverseDiagonalX = 0
    var reverseDiagonalO = 0
    var rowX: Int
    var rowO: Int
    var colX: Int
    var colO: Int
    var xWin = false
    var oWin = false
    var draw = false
    var notFinished = false
    var isImposibble = false
    for (i in 0 until LINE_COUNT) {//row check
        rowX = 0
        rowO = 0
        colX = 0
        colO = 0
        for (j in 0 until LINE_COUNT) {//column check

            // Row check
            if (grid[i][j].equals('X')) {
                totalXCount++
                rowX++
            } else if (grid[i][j].equals('O')) {
                totalOCount++
                rowO++
            }

            //Diagonal Check
            if (i == j) {
                if (grid[i][j].equals('X')) {
                    diagonalX++
                } else if (grid[i][j].equals('O')) {
                    diagonalO++
                }
            }
            if (i + j == 2) {
                if (grid[i][j].equals('X')) {
                    reverseDiagonalX++
                } else if (grid[i][j].equals('O')) {
                    reverseDiagonalO++
                }
            }
            //Column Check
            if (grid[j][i].equals('X')) {
                colX++
            } else if (grid[j][i].equals('O')) {
                colO++
            }

        }
        //Winner Check
        if (rowX == 3 || colX == 3 || diagonalX == 3 || reverseDiagonalX == 3) {
            xWin = true
        } else if (rowO == 3 || colO == 3 || diagonalO == 3 || reverseDiagonalO == 3) {
            oWin = true
        } else {
            if (totalOCount + totalXCount != 9) {
                notFinished = true
            } else {
                draw = true
            }
        }

    }

    //Imposibble Check
    if (((totalOCount - totalXCount).absoluteValue >= 2) || (xWin && oWin)) {
        return "Impossible"
    }

    return when (!isImposibble) {
        xWin -> "X wins"
        oWin -> "O wins"
        draw -> "Draw"
        notFinished -> "Game not finished"
        else -> "Something wrong"
    }
}

fun newMove(grid: Array<CharArray>, turn: Char) {
    var isValidCoord = false
    while (!isValidCoord) {
        val inputCoord = readLine()
        if (inputCoord != null && inputCoord.matches(Regex("\\d+\\s\\d+"))) {
            val (row, column) = inputCoord.split(" ").map { it.toInt() }
            if (row in 1..3 && column in 1..3) {
                if (grid[row - 1][column - 1] == 'X' || grid[row - 1][column - 1] == 'O') {
                    println("This cell is occupied! Choose another one!")
                } else {
                    grid[row - 1][column - 1] = turn
                    isValidCoord = true
                }
            } else {
                println("Coordinates should be from 1 to 3!")
            }
        } else {
            println("You should enter numbers!")
        }
    }
}

