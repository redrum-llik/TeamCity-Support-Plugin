package jetbrains.buildServer.supportAssist.execution.impl

import jetbrains.buildServer.supportAssist.execution.StepExecutionError
import jetbrains.buildServer.supportAssist.execution.Severity

class ErrorImpl(
    private val myDescription: String,
    private val mySeverity: Severity = Severity.ERROR
) : StepExecutionError {
    override fun getDescription(): String {
        return myDescription
    }

    override fun getSeverity(): Severity {
        return mySeverity
    }

    override fun toString(): String {
        return this.javaClass.simpleName +
                "[description='${myDescription}', severity='${mySeverity}']"
    }
}