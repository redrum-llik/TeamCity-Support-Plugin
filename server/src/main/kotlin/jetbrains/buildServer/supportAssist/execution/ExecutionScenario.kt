package jetbrains.buildServer.supportAssist.execution

interface ExecutionScenario : Collection<ScenarioStep> {
    fun execute(): List<StepExecutionError>
}