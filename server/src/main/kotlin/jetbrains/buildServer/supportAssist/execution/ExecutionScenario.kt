package jetbrains.buildServer.supportAssist.execution

/**
 * Represents a scenario composed of a collection of [ScenarioStep] to be executed.
 * The ExecutionScenario interface declares a mandatory method [execute] that executes each scenario step and
 * returns a list of all [StepExecutionError] instances encountered in the process.
 */
interface ExecutionScenario : Collection<ScenarioStep> {
    fun execute(): List<StepExecutionError>
}