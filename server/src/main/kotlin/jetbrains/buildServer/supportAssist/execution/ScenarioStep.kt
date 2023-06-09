package jetbrains.buildServer.supportAssist.execution

/**
 * Interface defining a scenario step.
 * A scenario step is an operation that can be executed as part of a [ExecutionScenario].
 * It provides methods to execute the step, check if it has already been executed,
 * and retrieve any errors that occurred during execution.
 */
interface ScenarioStep {
    fun execute()

    fun isExecuted(): Boolean

    fun getProblems(): List<StepExecutionProblem>

    fun hasErrorLevelProblems(): Boolean

    fun describe(): String
}