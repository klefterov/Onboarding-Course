package jetbrains.kotlin.course.chat

fun main() {
    println("Hello! I'm glad to meet you, let me get to know you better! What is your name?")
    val firstAnswer: String? = readlnOrNull()
    println("Nice to meet you, $firstAnswer! My name is Kotlin Bot! I am a young programming language created in 2010. How old are you?")
    val secondAnswer: String? = readlnOrNull()
    println("$secondAnswer is great! I hope you successfully complete this course! Anyone can learn programming at any age!")

}
