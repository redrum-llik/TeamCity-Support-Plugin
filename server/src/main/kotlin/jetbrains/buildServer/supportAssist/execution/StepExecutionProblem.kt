package jetbrains.buildServer.supportAssist.execution

/**
 * Represents problems that occur during the execution of a [ScenarioStep].
 */
interface StepExecutionProblem {
    fun getDescription(): String
    fun getSeverity(): Severity
}