package jetbrains.buildServer.supportAssist.execution

interface ScenarioStep {
    fun execute()

    fun isExecuted(): Boolean

    fun getErrors(): List<StepExecutionError>
}