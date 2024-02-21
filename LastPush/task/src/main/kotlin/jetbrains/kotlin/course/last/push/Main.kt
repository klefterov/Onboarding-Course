package jetbrains.kotlin.course.last.push

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.FixedWidth

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }
            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun getPatternHeight(pattern : String) = pattern.lines().size

fun fillPatternRow(patternRow : String, patternWidth : Int) : String{
    require(patternRow.length <= patternWidth){
        throw IllegalStateException()
    }
    val newPattern = patternRow + separator.toString().repeat(patternWidth - patternRow.length)
    return newPattern
}

fun repeatHorizontally(pattern : String, n : Int, patternWidth: Int) : String{
    val rows = pattern.lines()
    val sbd : StringBuilder = StringBuilder()
    for(row in rows){
        sbd.append(fillPatternRow(row, patternWidth).repeat(n))
        sbd.append(newLineSymbol)
    }
    sbd.deleteAt(sbd.length-1)
    return sbd.toString()
}

fun dropTopFromLine(line : String, width: Int, patternHeight : Int, patternWidth: Int) : String{
    val lines = line.lines()

    if(lines.size == 1){
        return line
    }

    val result = StringBuilder()

    for(i in width until patternHeight){
        result.append(lines[i])
        result.append(newLineSymbol)
    }

    if(result.isNotEmpty()) result.deleteAt(result.length-1)
    return result.toString()
}

fun getSpaces(patternWidth: Int, patternHeight : Int) : String{
    val sbd = StringBuilder()
    repeat(patternHeight){
        sbd.append(" ".repeat(patternWidth))
        sbd.append(newLineSymbol)
    }
    sbd.deleteAt(sbd.length-1)
    return sbd.toString()
}

fun canvasGenerator(pattern : String, width : Int, height : Int) : String{
    val row = repeatHorizontally(pattern, width, getPatternWidth(pattern))
    val sbd = StringBuilder()
    sbd.append(row)
    val r2 = dropTopFromLine(row, 1, getPatternHeight(row), getPatternWidth(row))
    repeat(height-1){
        sbd.append(newLineSymbol)
        sbd.append(r2)
    }
    return sbd.toString()
}
fun canvasWithGapsGenerator(pattern : String, width : Int, height : Int) : String{
    if(width == 1){
        val patternWidth = getPatternWidth(pattern)
        val sbd = StringBuilder()
        val lines = pattern.lines()
        repeat(height){
            for(line in lines){
                sbd.append(fillPatternRow(line, patternWidth) )
                sbd.append(newLineSymbol)
            }
        }
        sbd.deleteAt(sbd.length-1)
        return sbd.toString()
    }
    val imageWidth = getPatternWidth(pattern)
    val newPattern = repeatHorizontally(pattern, width*height, 2*imageWidth)
    println(newPattern)
    val even = StringBuilder()
    val lines = newPattern.lines()
    for(line in lines){
        even.append(line.substring( IntRange(0, width*imageWidth-1) ))
        even.append(newLineSymbol)
    }
    even.deleteAt(even.length-1)
    val odd = StringBuilder()
    for(line in lines){
        odd.append(line.substring( IntRange(imageWidth, width*imageWidth-1+imageWidth)))
        odd.append(newLineSymbol)
    }
    odd.deleteAt(odd.length-1)
    println(even)
    println(odd)
    val evenString = even.toString()
    val oddString = odd.toString()
    val list = listOf(even.toString(), odd.toString())
    val ans = StringBuilder()
    for(i in 0 until height){
        for(line in list[i%2].lines()){
            ans.append(line)
            ans.append(newLineSymbol)
        }
    }
    ans.deleteAt(ans.length-1)
    if(ans.isEmpty()) return ""
    return ans.toString()
}

fun applyGenerator(pattern : String, generatorName : String, width : Int, height : Int) : String{
    val newPattern = pattern.trimIndent()
    when(generatorName){
        "canvas" -> return canvasGenerator(newPattern, width, height) + newLineSymbol
        "canvasGaps" -> return canvasWithGapsGenerator(newPattern, width, height) + newLineSymbol
        else -> error("Is not a possible filter")
    }
}
fun main() {
    // Uncomment this code on the last step of the game
    val candela = "○"
    print(repeatHorizontally(candela, 1, 1))
    val pattern = getPattern()
    val generatorName = chooseGenerator()
    println("Please input the width of the resulting picture:")
    val width = safeReadLine().toInt()
    println("Please input the height of the resulting picture:")
    val height = safeReadLine().toInt()

    println("The pattern:$newLineSymbol${pattern.trimIndent()}")

    println("The generated image:")
    println(applyGenerator(pattern, generatorName, width, height))
}
