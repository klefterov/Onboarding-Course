package jetbrains.kotlin.course.almost.done


fun trimPicture(image : String) = image.trimIndent()

fun applyBordersFilter(picture : String) : String{
    val m = getPictureWidth(picture)
    val list = trimPicture(picture).lines()
    val sbd = StringBuilder()
    sbd.append(borderSymbol.toString().repeat(m+4))
    sbd.append(newLineSymbol)
    list.forEach {
        sbd.append("$borderSymbol$separator")
        sbd.append(it)
        for(i in 0 until m - it.length){
            sbd.append("$separator")
        }
        sbd.append("$separator$borderSymbol")
        sbd.append(newLineSymbol)
    }
    sbd.append(borderSymbol.toString().repeat(m+4))
    return sbd.toString()
}

fun applySquaredFilter(picture : String) : String{
    val list : List<String> = applyBordersFilter(picture).split(newLineSymbol)
    val sbd : StringBuilder = StringBuilder()
    val m = list.first().length
    val n = list.size
    for(i in 0 until n*2 ){
        if(i == n){
            continue
        }
        for(j in 0 until m*2){
            sbd.append(list[i%n][j%m])
        }
        sbd.append(newLineSymbol)
    }
    return sbd.toString()
}

fun applyFilter(picture : String, filter : String) = when(filter){
    "borders" -> {
        applyBordersFilter(trimPicture(picture ) )
    }
    "squared" -> {
        picture.trimIndent()
        applySquaredFilter(trimPicture(picture))
    }
    else -> error("There is no such filter")
}

fun chooseFilter() : String{
    var input : String
    do{
        input = safeReadLine()
    }while(input !in listOf("borders", "squared"))
    return input
}

fun safeReadLine() = readlnOrNull() ?: error("error")

fun choosePicture() : String{
    do{
        println("Please choose a picture. The possible options are: ${allPictures()}")
        val input = safeReadLine()
        getPictureByName(input)?.let {
            return@choosePicture getPictureByName(input)!!
        }
    }while(true)
}

fun getPicture() : String{
    val ans : String
    while(true){
        println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
        val input = safeReadLine()
        when(input){
            "yes" -> {
                ans = choosePicture()
                break
            }
            "no" ->{
                println("Please input a custom picture (note that only single-line images are supported)")
                ans = safeReadLine()
                break
            }
        }
    }
    return ans
}

fun photoshop(){
    val picture = getPicture()
    val filter = chooseFilter()
    val ans = applyFilter(picture, filter)
    println("The old image:")
    println(picture)
    println("The transformed picture:")
    println(ans)
}

fun main() {
    // Uncomment this code on the last step of the game

    photoshop()
}