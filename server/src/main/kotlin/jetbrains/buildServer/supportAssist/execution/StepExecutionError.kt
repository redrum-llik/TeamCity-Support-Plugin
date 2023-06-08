package jetbrains.buildServer.supportAssist.execution

/**
 * Represents errors that occur during the execution of a [ScenarioStep].
 */
interface StepExecutionError {
    fun getDescription(): String
    fun getSeverity(): Severity
}