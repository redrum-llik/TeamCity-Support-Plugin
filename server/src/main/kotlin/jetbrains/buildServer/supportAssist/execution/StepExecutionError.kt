package jetbrains.buildServer.supportAssist.execution

interface StepExecutionError {
    fun getDescription(): String
    fun getSeverity(): Severity
}