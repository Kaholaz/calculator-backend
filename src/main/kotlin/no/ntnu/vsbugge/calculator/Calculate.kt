package no.ntnu.vsbugge.calculator

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

// Models
data class Operation(val a: Double, val b: Double, val operator: Char)
data class Result(val result: Double)


// Services
/**
 * Service for performing arithmetic operations
 */
@Service
object ArithmeticService {
    fun calculate(operation: Operation): Result {
        return when (operation.operator) {
            '+' -> Result(operation.a + operation.b)
            '-' -> Result(operation.a - operation.b)
            '*' -> Result(operation.a * operation.b)
            '/' -> Result(operation.a / operation.b)
            else -> throw IllegalArgumentException("Unknown operator ${operation.operator}")
        }
    }
}

// Controllers
/**
 * Controller for the calculator
 */
@RestController
class CalculateController {
    private val logger = LoggerFactory.getLogger(CalculateController::class.java)

    /**
     * Calculate the result of an operation
     * @param operation The operation to calculate
     * @return The result of the operation
     */
    @PostMapping("/calculate")
    fun calculate(@RequestBody operation: Operation): Result {
        logger.info("Calculating ${operation.a} ${operation.operator} ${operation.b}")
        return ArithmeticService.calculate(operation)
    }
}

// Application
@SpringBootApplication
class CalculatorApplication

/**
 * Main function for the calculator
 */
fun main(args: Array<String>) {
    runApplication<CalculatorApplication>(*args)
}

// Configuration
@Configuration
class CorsConfiguration: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        // Very permissive CORS configuration
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
    }
}
