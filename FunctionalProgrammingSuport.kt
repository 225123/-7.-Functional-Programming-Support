import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

data class Person(val name: String, val age: Int)

fun main() {
    // Print a simple message
    println("Hello, World!")

    // Create an instance of Person and print it
    val person = Person("John Doe", 30)
    println(person)

    // Example of lambda expression
    val square: (Int) -> Int = { number -> number * number }
    println("Square of 4 is ${square(4)}")

    // Example of higher-order function
    fun operateOnNumbers(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        return operation(a, b)
    }
    val sum = operateOnNumbers(3, 4) { x, y -> x + y }
    println("Sum of 3 and 4 is $sum")

    // Example of functional operations on collections
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    val evenNumbers = numbers.filter { it % 2 == 0 }
    val squaredNumbers = numbers.map { it * it }
    val sumOfNumbers = numbers.reduce { acc, number -> acc + number }

    println("Even numbers: $evenNumbers")
    println("Squared numbers: $squaredNumbers")
    println("Sum of numbers: $sumOfNumbers")

    // Start Ktor server
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello, World!", ContentType.Text.Plain)
            }
            get("/square/{number}") {
                val number = call.parameters["number"]?.toIntOrNull()
                if (number != null) {
                    val square = number * number
                    call.respondText("Square of $number is $square", ContentType.Text.Plain)
                } else {
                    call.respondText("Invalid number", ContentType.Text.Plain)
                }
            }
            get("/filter") {
                val evenNumbers = numbers.filter { it % 2 == 0 }
                call.respondText("Even numbers: $evenNumbers", ContentType.Text.Plain)
            }
            get("/map") {
                val squaredNumbers = numbers.map { it * it }
                call.respondText("Squared numbers: $squaredNumbers", ContentType.Text.Plain)
            }
            get("/reduce") {
                val sumOfNumbers = numbers.reduce { acc, number -> acc + number }
                call.respondText("Sum of numbers: $sumOfNumbers", ContentType.Text.Plain)
            }
        }
    }.start(wait = true)
}